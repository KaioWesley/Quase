package br.senai.sp.jandira.forca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView txtPalavra;
    TextView txtErro;
    TextView txtAcerto;
    TextView txtCategoria;
    String palavra = "";

    static final  String categorias[] = {
            "ANIMAL",
            "FILMES",
            "CIDADES",
            "FRUTA"
    };
    static final String[][] palavras = {{
        //ANIMAIS
            "COBRA",
            "COALA",
            "AGUIA",
            "PASSARINHO",
            "TUCANO",
            "URSO",
            "LOBO",
            "TIGRE",
            "LIGRE",
            "HUMANO"
    },{ //FILMES
            "CINDERELA",
            "FROZEN",
            "MATRIX",
            "AMANHECER",
            "ORDET",
            "ANABELLE",
            "AVATAR",
            "ALLADIN",
            "CARROS",
            "CLICK"

    },{ //CIDADES
            "ITAPEVI",
            "BARUERI",
            "CARAGUATATUBA",
            "CARAGUAQUECETUBA",
            "CARAPICUIBA",
            "OSASCO",
            "UBERLANDIA",
            "CURITIBA",
            "SALVADOR",
            "CARAPICUIBA"
    },{ //FRUTAS
            "BANANA",
            "LARANJA",
            "MARACUJA",
            "UVA"
    }
    };

    static final int VITORIA = 0;
    static final int DERROTA = 1;

    int numeroCategoria = escolherCategoria();
    String categoriaEscolhida = categorias[numeroCategoria];
    String palavraEscolhida = escolherPalavra(numeroCategoria);

    int acertosTotal = palavraEscolhida.length();
    int errosTotal = (int) Math.ceil(palavraEscolhida.length() / 1.7);

    int quantidaDeErros = 0;
    int quantidaDeAcertos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPalavra = findViewById(R.id.txtPalavraJogo);
        txtErro = findViewById(R.id.txtErros);
        txtAcerto = findViewById(R.id.txtAcertos);
        txtCategoria = findViewById(R.id.txtCategoria);

        txtCategoria.setText(categoriaEscolhida);

        for(int i=0; i <= palavraEscolhida.length() - 1; i++){
            if(i == palavraEscolhida.length() - 1){
                palavra += "_";
            }else{
                palavra += "_ ";
            }
        }
        txtPalavra.setText(palavra);

        atualizarQuantidaDeAcertos();
        atualizarQuantidaDeErros();
    }

    public void acaobotao(View v) {
        char letraEscolhida = v.getTag().toString().charAt(0);
        v.setEnabled(false);

        boolean erro = true;

        for(int i = 0; i <= palavraEscolhida.length() - 1; i++){
            if(letraEscolhida == palavraEscolhida.charAt(i)){
                StringBuilder novaPalavra = new StringBuilder(palavra);
                novaPalavra.setCharAt(i * 2, letraEscolhida);
                palavra = novaPalavra.toString();
                txtPalavra.setText(palavra);
                quantidaDeAcertos += 1;
                erro = false;
            }
        }
        if(erro){
            v.setBackgroundColor(getResources().getColor(R.color.desativadovermelho));
            quantidaDeErros += 1;
            atualizarQuantidaDeErros();
        }else{
            v.setBackgroundColor(getResources().getColor(R.color.desativadoverde));
            atualizarQuantidaDeAcertos();
        }
        if(quantidaDeErros >= errosTotal){
            gameOver(DERROTA);
        }else if(quantidaDeAcertos >= acertosTotal){
            gameOver(VITORIA);
        }
    }

    public void reiniciar(View v){
        recreate();
    }

    private void gameOver(int condicaoVitoria){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);

        if(condicaoVitoria == VITORIA){
            alert.setTitle("PARABÉNS");
            alert.setMessage("VOCÊ GANHOU");
        }else{
            alert.setTitle("QUE PENA");
            alert.setMessage("ERA '" + palavraEscolhida + "'.");
        }
        alert.setNegativeButton("DESISTIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setPositiveButton("COMEÇAR DE NOVO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });

        alert.create().show();
    }

    private void atualizarQuantidaDeAcertos(){
        txtAcerto.setText("Acertos: " + quantidaDeAcertos);
    }

    private void atualizarQuantidaDeErros(){
        txtErro.setText("Erros: " + quantidaDeErros + "/" + errosTotal);
    }


    private int escolherCategoria(){
        int min = 0;
        int max = categorias.length - 1;

        return new Random().nextInt((max - min) + 1) + min;
    }
    private String escolherPalavra(int numeroCategoria){
        int min = 0;
        int max = palavras[numeroCategoria].length - 1;
        final int numeroPalavra = new Random().nextInt((max - min) + 1) + min;

        return palavras[numeroCategoria][numeroPalavra];
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}