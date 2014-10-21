package com.example.photobook;

import android.app.Activity;
import android.os.Bundle;

public class PictureViewer extends Activity {
	
	/*Opens up clicked picture and displays information recorded
	 * - maybe has option to edit caption, send to social media
	 * - needs back button to return to main viewer
	 */
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_viewer);
	}

}
