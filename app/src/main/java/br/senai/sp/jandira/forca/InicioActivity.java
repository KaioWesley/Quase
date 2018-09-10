package br.senai.sp.jandira.forca;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 17259206 on 03/09/2018.
 */

public class InicioActivity extends AppCompatActivity{

    MediaPlayer mediaPlayer;

    public void iniciarJogo(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        mediaPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mediaPlayer = MediaPlayer.create(this, R.raw.circus);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    protected void onResume() {
        super.onResume();
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        //pausar a musica
        mediaPlayer.pause();
    }

    @Override
    public void onBackPressed() {
        finish();
        mediaPlayer.stop();
    }
}
