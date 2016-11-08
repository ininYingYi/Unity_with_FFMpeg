using System;
using System.Runtime.InteropServices;
using UnityEngine;
using System.Collections;
using System.Diagnostics;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;


public class ScreenRecorder {

	private AndroidJavaObject unityActivity = null;
	private AndroidJavaObject captureObject = null;

	// Use this for initialization
	public ScreenRecorder () {
		try{

			using (AndroidJavaClass unityPlayerActivityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer")) {
				unityActivity = unityPlayerActivityClass.GetStatic<AndroidJavaObject>("currentActivity");
			}
			AndroidJavaObject surfaceView = new AndroidJavaObject("android.view.SurfaceView");

			AndroidJavaClass captureClass = new AndroidJavaClass ("com.example.ffmpegcodec.FFMpegCodec");
			if (captureClass != null) {
				captureObject = captureClass.CallStatic<AndroidJavaObject>("instance");
				captureObject.Call("setContext", unityActivity);
				captureObject.Call("loadFFMpegBinary");
				captureObject.Call("listDevices");
			}

		}catch(Exception ex){
			UnityEngine.Debug.Log(ex);
			//GameObject.Find ("debug").GetComponent<TextMesh>().text = ex.ToString();
		}
	}

	public void encodeVideo(String imagePrefix) {
		captureObject.Call ("encodeVideo", Settings.TEMP_IMAGE_PATH, imagePrefix);
	}

}