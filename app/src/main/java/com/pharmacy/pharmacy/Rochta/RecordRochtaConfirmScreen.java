package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pharmacy.pharmacy.R;

import java.io.IOException;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class RecordRochtaConfirmScreen extends Activity  implements View.OnClickListener{
    ImageButton playrecord;
    Button send;
    EditText notes;
    MediaPlayer mediaPlayer ;
    boolean showingFirstPlay=true;
    String data;
    MediaRecorder mediaRecorder ;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_rochta_confirm);
        playrecord=(ImageButton)findViewById(R.id.playrecord);
        send=(Button)findViewById(R.id.send);
        notes=(EditText)findViewById(R.id.notes);
        data= getIntent().getExtras().getString("record");
        playrecord.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playrecord: {
                if (showingFirstPlay == true) {
                    playRecord();
                    playrecord.setBackgroundResource(R.drawable.pause);
                    showingFirstPlay = false;
                } else {
                    stopPlayRecord();
                    playrecord.setBackgroundResource(R.drawable.stop);
                    showingFirstPlay = true;
                }

                break;
            }
            case R.id.send:
                break;
        }
    }
    public void playRecord(){


        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(data);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(RecordRochtaConfirmScreen.this, "Recording Playing",
                Toast.LENGTH_LONG).show();
    }
    public void stopPlayRecord() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            MediaRecorderReady();
            Toast.makeText(RecordRochtaConfirmScreen.this, "Recording Playing Stop",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(data);
    }
}
