using UnityEngine;
using System.Collections;
using System.IO;

public class RecordController : MonoBehaviour {
	private ScreenRecorder mScreenRecorder;
	private WebCamTexture mCamera = null;
	private GameObject plane;
	private bool saveImage = false;
	private long count = 0;
	private string imagePrefix = "image-";
	// Use this for initialization
	void Start () {
		GameObject.Find ("debug").GetComponent<TextMesh>().text = "HI";
		mScreenRecorder = new ScreenRecorder ();
		mCamera = new WebCamTexture ();
		plane = GameObject.FindWithTag ("CameraView");
		plane.GetComponent<Renderer>().material.mainTexture = mCamera;
		mCamera.Play ();
		Settings.checkPath ();

	}
	
	// Update is called once per frame
	void Update () {
		
	}

	void FixedUpdate() {
		if (saveImage) {
			Texture2D image = new Texture2D(mCamera.width, mCamera.height);
			image.SetPixels (mCamera.GetPixels ());
			image.Apply ();
			System.IO.File.WriteAllBytes (Settings.TEMP_IMAGE_PATH + imagePrefix + count + ".png", image.EncodeToPNG());
			count++;
		}
	}


	void OnGUI() {
		if (GUI.Button (new Rect (0, 0, 400, 200), "record")) {
			count = 0;
			saveImage = true;
		}
		if (GUI.Button (new Rect (500, 0, 400, 200), "toVideo")) {
			saveImage = false;
			mScreenRecorder.encodeVideo (imagePrefix);
		}
	}
}
