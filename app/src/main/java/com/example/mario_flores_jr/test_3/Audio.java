package com.example.mario_flores_jr.test_3;

import android.Manifest;

import android.content.Context;

import android.content.pm.PackageManager;

import android.media.MediaPlayer;

import android.media.MediaRecorder;

import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;

import android.widget.LinearLayout;



import java.io.IOException;



public class Audio extends AppCompatActivity {



    Button grabarAudio, reproducirAudio;



    private static final String LOG_TAG = "AudioRecordTest";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;



    private static String mFileName = null;



    private RecordButton mRecordButton = null;

    private MediaRecorder mRecorder = null;



    private PlayButton   mPlayButton = null;

    private MediaPlayer mPlayer = null;



    // Requesting permission to RECORD_AUDIO

    private boolean permissionToRecordAccepted = false;

    private String [] permissions = {Manifest.permission.RECORD_AUDIO};





    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case REQUEST_RECORD_AUDIO_PERMISSION:

                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                break;

        }

        if (!permissionToRecordAccepted ) finish();



    }



    private void onRecord(boolean start) {

        if (start) {

            startRecording();

        } else {

            stopRecording();

        }

    }



    private void onPlay(boolean start) {

        if (start) {

            startPlaying();

        } else {

            stopPlaying();

        }

    }



    private void startPlaying() {

        mPlayer = new MediaPlayer();

        try {

            mPlayer.setDataSource(mFileName);

            mPlayer.prepare();

            mPlayer.start();

        } catch (IOException e) {

            Log.e(LOG_TAG, "prepare() failed");

        }

    }



    private void stopPlaying() {

        mPlayer.release();

        mPlayer = null;

    }



    private void startRecording() {

        mRecorder = new MediaRecorder();

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mRecorder.setOutputFile(mFileName);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);



        try {

            mRecorder.prepare();

        } catch (IOException e) {

            Log.e(LOG_TAG, "prepare() failed");

        }



        mRecorder.start();

    }



    private void stopRecording() {

        mRecorder.stop();

        mRecorder.release();

        mRecorder = null;

    }



    class RecordButton extends android.support.v7.widget.AppCompatButton {

        boolean mStartRecording = true;



        OnClickListener clicker = new OnClickListener() {

            public void onClick(View v) {

                onRecord(mStartRecording);

                if (mStartRecording) {

                    setText("Detener Grabación");

                } else {

                    setText("Comenzar Grabación");

                }

                mStartRecording = !mStartRecording;

            }

        };



        public RecordButton(Context ctx) {

            super(ctx);

            setText("Comenzar Grabación");

            setOnClickListener(clicker);

        }

    }



    class PlayButton extends android.support.v7.widget.AppCompatButton {

        boolean mStartPlaying = true;



        OnClickListener clicker = new OnClickListener() {

            public void onClick(View v) {

                onPlay(mStartPlaying);

                if (mStartPlaying) {

                    setText("Detener");

                } else {

                    setText("Reproducir");

                }

                mStartPlaying = !mStartPlaying;

            }

        };



        public PlayButton(Context ctx) {

            super(ctx);

            setText("Reproducir");

            setOnClickListener(clicker);

        }

    }



    @Override

    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);

        setContentView(R.layout.activity_audio);



        // Record to the external cache directory for visibility

        mFileName = getExternalCacheDir().getAbsolutePath();

        mFileName += "/audiorecording.3gp";



        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);



        LinearLayout ll = new LinearLayout(this);

        ll.setVerticalGravity(16);

        mRecordButton = new RecordButton(this);

        ll.addView(mRecordButton,

                new LinearLayout.LayoutParams(

                        ViewGroup.LayoutParams.MATCH_PARENT,

                        ViewGroup.LayoutParams.WRAP_CONTENT,

                        17));

        mPlayButton = new PlayButton(this);

        ll.addView(mPlayButton,

                new LinearLayout.LayoutParams(

                        ViewGroup.LayoutParams.MATCH_PARENT,

                        ViewGroup.LayoutParams.WRAP_CONTENT,

                        16));

        setContentView(ll);

    }



    @Override

    public void onStop() {

        super.onStop();

        if (mRecorder != null) {

            mRecorder.release();

            mRecorder = null;

        }



        if (mPlayer != null) {

            mPlayer.release();

            mPlayer = null;

        }

    }



    @Override

    public void onDestroy() {

        super.onDestroy();

        finish();

    }



}