package br.senai.sp.agenda;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

class CadastroContatoHelper {

    EditText txtNome;
    EditText txtEndereco;
    EditText txtTelefone;
    EditText txtEmail;
    EditText txtEnderecoLinkedin;
    Contato contato;

    TextInputLayout validaTxtNome;
    TextInputLayout validaTxtEndereco;
    TextInputLayout validaTxtTelefone;
    TextInputLayout validaTxtEmail;
    TextInputLayout validaTxtEnderecoLinkedin;


    public CadastroContatoHelper(CadastroActivity activity){


        validaTxtNome = activity.findViewById(R.id.valida_txt_nome);
        validaTxtEmail = activity.findViewById(R.id.valida_txt_email);
        validaTxtEndereco = activity.findViewById(R.id.valida_txt_endereco);
        validaTxtTelefone = activity.findViewById(R.id.valida_txt_telefone);
        validaTxtEnderecoLinkedin = activity.findViewById(R.id.valida_txt_endereco_linkedin);

        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtEnderecoLinkedin = activity.findViewById(R.id.txt_enderec_linkedin);

        contato = new Contato();

    }

    public boolean validar(Context activity){
        boolean validado = true;

        if(txtNome.getText().toString().isEmpty()){
            validaTxtNome.setErrorEnabled(true);
            validaTxtNome.setError("Por favor digite seu nome");
            validado = false;
        }else{
            validaTxtNome.setErrorEnabled(false);
        }

        if(txtEndereco.getText().toString().isEmpty()){
            validaTxtEndereco.setErrorEnabled(true);
            validaTxtEndereco.setError("Por favor digite seu nome");
            validado = false;
        }else{
            validaTxtEndereco.setErrorEnabled(false);
        }

        if(txtTelefone.getText().toString().isEmpty()){
            validaTxtTelefone.setErrorEnabled(true);
            validaTxtTelefone.setError("Por favor digite seu nome");
            validado = false;
        }else{
            validaTxtTelefone.setErrorEnabled(false);
        }

        if(!txtEmail.getText().toString().matches("[a-zA-Z0-9._-]{1,}@[a-zA-Z0-9]{1,}.+")){
            validaTxtEmail.setErrorEnabled(true);
            validaTxtEmail.setError("exemplo@exemplo.com");
            validado = false;
        }else{
            validaTxtEmail.setErrorEnabled(false);
        }


        if(!txtEnderecoLinkedin.getText().toString().matches("[a-zA-Z0-9._-]{1,}@[a-zA-Z0-9]{1,}.+")){
            validaTxtEnderecoLinkedin.setErrorEnabled(true);
            validaTxtEnderecoLinkedin.setError("exemplo@exemplo.com");
            validado = false;
        }else{
            validaTxtEnderecoLinkedin.setErrorEnabled(false);
        }

        return validado;
    }

    public Contato getContato(){

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setEndereco_linkedin(txtEnderecoLinkedin.getText().toString());


        return contato;


    }

    public void preencherFormulario(Contato contato) {
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtEnderecoLinkedin.setText(contato.getEndereco_linkedin());

        this.contato = contato;
    }
}
