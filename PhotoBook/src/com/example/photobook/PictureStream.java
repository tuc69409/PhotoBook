package com.example.photobook;




import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.example.photobook.util.API;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;



/*Dynamic picture feed*/
public class PictureStream extends Activity {
	
	ImageView newImage;
	GridLayout imageStream;
	File photoStorage, photo;
	Uri imageUri;
	
	String photoString;
	
	
	
	int TAKE_PICTURE_REQUEST_CODE = 123456;
	int userID = 1999;
	
	
	/*Create menu with new photo option, logout, and refresh?*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_picture:
			/*Take new picture*/
			takePicture();
			return true;
		case R.id.signout:
			/*Remove active from database to prevent multiples from log in??*/
			Intent signOut = new Intent(PictureStream.this, StartScreen.class);
			startActivity(signOut);
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageStream = (GridLayout) findViewById(R.id.imageStream);
		
		/*Check for local directory of photos, if not create one*/
		photoStorage = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
		if(!photoStorage.isDirectory()){
			photoStorage.mkdir();
		}
		
		demo();
		
		//loadStream();
	}
	
	private void loadStream(){

		
		
		/*Create thread for JSON array creation*/
//		Thread JSON = new Thread(){
//			@Override
//			public void run(){
//				try {
//					JSONArray streamArray = API.photoBooktoJson(PictureStream.this, userID);
//					
//					Message msg = Message.obtain();
//					msg.obj = streamArray;
//					
//					streamHandler.sendMessage(msg);
//				} catch (Exception e) {
//				}
//			}
//		};
//		JSON.start();
	}
	
	/*Handler for JSON thread*/
//	Handler streamHandler = new Handler(new Handler.Callback() {
//		
//		@Override
//		public boolean handleMessage(Message msg) {
//			
//			
//			JSONArray streamArray = (JSONArray) msg.obj;
//			if (streamArray != null) {
//				imageStream.removeAllViews();
//				for (int i = 0; i < streamArray.length(); i++){
//					try {
//						imageStream.addView(getPhotoViewer(streamArray.getJSONObject(i)));
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			return false;
//		}
//	});
	
	private View getPhotoViewer(final JSONObject photoObject){
		//Switch to stream??
		GridLayout photoLayout = new GridLayout(this);
		ImageView photoImageView = new ImageView(this);
		GridLayout.LayoutParams lp = new GridLayout.LayoutParams(); 
		photoImageView.setLayoutParams(lp);
		try {
			ImageLoader.getInstance().displayImage(photoObject.getString("image_url"), photoImageView);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		photoLayout.addView(photoImageView);
		//set on click listener for picture viewer, one for each or one to tell which picture clicked
		photoImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openPictureViewer();
			}
		});
		
		return photoLayout;
	}
	
	/*Take a picture*/
	private void takePicture(){
	
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		photo = new File(photoStorage, String.valueOf(System.currentTimeMillis()) + ".jpg");
		takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		
		imageUri = Uri.fromFile(photo);
		
		startActivityForResult(takePicture, TAKE_PICTURE_REQUEST_CODE);
	}
	
	/*When picture is returned, open picture editor*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE_REQUEST_CODE) {
			Intent openPictureEditor = new Intent(PictureStream.this, PictureEditor.class);
			String photoUri = imageUri.toString();
			openPictureEditor.putExtra("photoUri", photoUri);
	
			startActivity(openPictureEditor);
			
		}
	
	}
	
	private void openPictureViewer(){
	//send photo uri as intent to picture viwer
	
	//FOR DEMO ONLY	
		Intent openViewer = new Intent(PictureStream.this, PictureViewer.class);
		
		//FOR DEMO//
		openViewer.putExtra("photoString", photoString);
		
		
		startActivity(openViewer);
		
	}
	
	
	//FOR DEMO ONLY
	private void demo(){
		
		
		
		//Get new photoString
		photoString = getIntent().getStringExtra("photoString");
		
		
		if(photoString != null)
		{/*Initialize image loader*/
		ImageLoader imageLoader;
		DisplayImageOptions displayOptions;
		
		imageLoader = ImageLoader.getInstance();
		
		displayOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		File cacheDir = StorageUtils.getCacheDirectory(this);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .threadPoolSize(3)
        .threadPriority(Thread.NORM_PRIORITY - 1)
        .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 2 MBs
        .discCache(new UnlimitedDiscCache(cacheDir))
        .discCacheSize(50 * 1024 * 1024) // 50 MBs
        .defaultDisplayImageOptions(displayOptions)
        .build();
		ImageLoader.getInstance().init(config);
		
		newImage = new ImageView(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			600, 600);
		
		newImage.setLayoutParams(lp);
		
		ImageLoader.getInstance().displayImage(photoString, newImage);
		imageStream.addView(newImage);
		
		//set on click listener for picture viewer, one for each or one to tell which picture clicked
		newImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openPictureViewer();
			}
		});
		}
	}
	
	

	
}
