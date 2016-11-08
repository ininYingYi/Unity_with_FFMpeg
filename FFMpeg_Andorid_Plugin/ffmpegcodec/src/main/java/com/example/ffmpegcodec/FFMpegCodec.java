package com.example.ffmpegcodec;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.IOException;

/**
 * Created by YingYi on 2016/11/6.
 */


public class FFMpegCodec {
    private static FFmpeg ffmpeg;
    private static Context context;
    private static FFMpegCodec INSTANCE = null;

    public FFMpegCodec() {
        INSTANCE = this;
    }

    public static FFMpegCodec instance() {
        if (INSTANCE == null) {
            INSTANCE = new FFMpegCodec();
        }
        return INSTANCE;
    }

    public void setContext(Context ctx) {
        this.context = ctx;
    }

    public void loadFFMpegBinary() {
        try {
            if (ffmpeg == null) {

                ffmpeg = FFmpeg.getInstance(context);
            }
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    Log.e("FFMPEG", "ffmpeg : NOT correct Loaded");
                }

                @Override
                public void onSuccess() {
                    Log.e("FFMPEG", "ffmpeg : correct Loaded");
                }
            });
        } catch (FFmpegNotSupportedException e) {

        } catch (Exception e) {

        }
    }

    public void encodeVideo(String imagePath, String fileName) {
        String[] cmd = new String[9];
        cmd[0] = "-i";
        cmd[1] = imagePath + fileName + "%d.png";
        cmd[2] = "-c:v";
        cmd[3] = "libx264";
        cmd[4] = "-r";
        cmd[5] = "30";
        cmd[6] = "-pix_fmt";
        cmd[7] = "yuv420p";
        cmd[8] = imagePath + "video.mp4";
        this.execFFmpegCommand(cmd);
    }

    public void listDevices() {
        String[] cmd = new String[1];
        cmd[0] = "-devices";
        this.execFFmpegCommand(cmd);
    }

    public void execFFmpegCommand(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.e("FFMPEG", "FAILED with output : " + s);
                }

                @Override
                public void onSuccess(String s) {
                    Log.e("FFMPEG", "SUCCESS with output : " + s);
                }

                @Override
                public void onProgress(String s) {
                    Log.e("FFMPEG", "Started command : ffmpeg " + command);
                    Log.e("FFMPEG", "progress : " + s);
                }

                @Override
                public void onStart() {
                    Log.e("FFMPEG", "Started command : ffmpeg " + command);

                }

                @Override
                public void onFinish() {
                    Log.e("FFMPEG", "Finished command : ffmpeg " + command);

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
        }
    }
}