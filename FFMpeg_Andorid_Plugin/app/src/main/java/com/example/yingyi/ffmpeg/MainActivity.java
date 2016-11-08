package com.example.yingyi.ffmpeg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ffmpegcodec.FFMpegCodec;
import com.example.test.Test;

/**
 * Created by YingYi on 2016/11/6.
 */

public class MainActivity extends AppCompatActivity {

    FFMpegCodec ffmpeg;
    Test mTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button execCommandButton = (Button) findViewById(R.id.execCommandButton);

        ffmpeg = FFMpegCodec.instance();


        execCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is a Toast!!", Toast.LENGTH_SHORT).show();
                ffmpeg.setContext(MainActivity.this);
                ffmpeg.loadFFMpegBinary();
                /*String[] cmd = new String[1];
                cmd[0] = "-version";
                ffmpeg.execFFmpegCommand(cmd);*/
                ffmpeg.listDevices();
            }
        });


    }
}