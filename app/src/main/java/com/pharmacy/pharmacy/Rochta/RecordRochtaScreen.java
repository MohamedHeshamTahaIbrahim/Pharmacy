package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pharmacy.pharmacy.R;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class RecordRochtaScreen extends Activity implements View.OnClickListener {
    ImageButton startRecord,playrecord;
    Button confirm;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    boolean showingFirstRecord = true,showingFirstPlay=true;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_rochta);
        startRecord=(ImageButton)findViewById(R.id.startRecord);
        playrecord=(ImageButton)findViewById(R.id.playrecord);
        confirm=(Button)findViewById(R.id.confirm);
        startRecord.setOnClickListener(this);
        playrecord.setOnClickListener(this);
        confirm.setOnClickListener(this);
        random = new Random();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startRecord: {
                if (showingFirstRecord == true) {
                    startRecord();
                    startRecord.setBackgroundResource(R.drawable.microphone_start);
                    showingFirstRecord = false;
                } else {
                    stopRecord();
                    startRecord.setBackgroundResource(R.drawable.microphone_stop);
                    showingFirstRecord = true;
                }
                break;
            }
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
            case R.id.confirm:
                Intent intent=new Intent(RecordRochtaScreen.this,RecordRochtaConfirmScreen.class);
                intent.putExtra("record",AudioSavePathInDevice);
                startActivity(intent);
                break;
        }
    }
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(RecordRochtaScreen.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                  /*  if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }*/
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    public void startRecord(){
        if(checkPermission()) {

            AudioSavePathInDevice =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";

            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            Toast.makeText(RecordRochtaScreen.this, "Recording started",
                    Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
    }
    public void stopRecord(){
        mediaRecorder.stop();
        Toast.makeText(RecordRochtaScreen.this, "Recording Completed",
                Toast.LENGTH_LONG).show();
    }
    public void playRecord(){


        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(RecordRochtaScreen.this, "Recording Playing",
                Toast.LENGTH_LONG).show();
    }
    public void stopPlayRecord(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            MediaRecorderReady();
            Toast.makeText(RecordRochtaScreen.this, "Recording Playing Stop",
                    Toast.LENGTH_LONG).show();
        }
    }
}
