package br.senai.sp.agenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    CadastroContatoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new CadastroContatoHelper(this);

        //tem uma intenção e quero pegála. na Main foi criada essa intent e com ela também está o contato
        Intent intent = getIntent();
        //o cadastro vem serealizado e preciso fazer a descerealização
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato!=null){
            helper.preencherFormulario(contato);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        final Contato contato = helper.getContato();

        ContatoDAO dao = new ContatoDAO(this);

        switch (item.getItemId()){
            case R.id.menu_salvar:


                if(helper.validar(this)==true) {
                    if(contato.getId()== 0){


                    dao.salvar(contato);
                    Toast.makeText(this, contato.getNome()+" gravado com sucesso!", Toast.LENGTH_LONG).show();
                }else{

                    dao.atualizar(contato);
                    Toast.makeText(this, contato.getNome()+" atualizado com sucesso!", Toast.LENGTH_LONG).show();
                }

                    dao.close();
                    finish();
                }

                break;

            case R.id.menu_deletar_cadastro:


                    if (contato.getId() != 0) {

                        new AlertDialog.Builder(this)
                                .setTitle("Deletando Contato")
                                .setMessage("Tem certeza que deseja deletar o contato " + contato.getNome() + "?")
                                .setPositiveButton("sim",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                ContatoDAO dao = new ContatoDAO(CadastroActivity.this);

                                                dao.excluir(contato);

                                                dao.close();
                                                finish();
                                                Toast.makeText(CadastroActivity.this, contato.getNome() + " removido com sucesso!", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                .setNegativeButton("não", null)
                                .show();

                    } else {
                        Toast.makeText(this, "Impossível remover um contato inexistente!", Toast.LENGTH_LONG).show();
                        dao.close();
                    }

                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
