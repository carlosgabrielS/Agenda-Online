package br.senai.sp.agenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
        private ListView listaContatos;
        private Button btNovoContato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNovoContato = findViewById(R.id.bt_novo_contato);

        listaContatos = findViewById(R.id.lista_contatos);




        btNovoContato.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent abrirContato = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(abrirContato);
            }
        });


        registerForContextMenu(listaContatos);
        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contato contato = (Contato) listaContatos.getItemAtPosition(position);

                //criar intenção de chamar o aplicativo
                Intent cadastro = new Intent(MainActivity.this, CadastroActivity.class);

                cadastro.putExtra("contato", contato);

                //para abrir é preciso da seguinte linha
                startActivity(cadastro);

               // Toast.makeText(MainActivity.this,String.valueOf(position) , Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_lista_contato, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);
        new AlertDialog.Builder(this)
                .setTitle("Deletando Contato")
                .setMessage("Tem certeza que deseja deletar o contato " + contato.getNome()+"?")
                .setPositiveButton("sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ContatoDAO dao = new ContatoDAO(MainActivity.this);

                                dao.excluir(contato);

                                dao.close();

//                                Toast.makeText(MainActivity.this, contato.getNome(), Toast.LENGTH_LONG).show();

                                carregarLista();

                            }
                        })
                .setNegativeButton("não", null)
                .show();


        //chamo o dao.exluir e passo o contato, como o list view feito no adapter
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        carregarLista();
        super.onResume();
    }

    private void carregarLista(){
        // *** VETOR DE CONTATO

        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contato = dao.getContatos();
        dao.close();
        // abrir o banco de dados
        // rodar um query de consulta
        //retirbar um arrayList
//****



        //Criação do adapter para carregar o vetor dentro da ListView
        ArrayAdapter<Contato> adapterContatos = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contato);
        listaContatos.setAdapter(adapterContatos);
    }
}
