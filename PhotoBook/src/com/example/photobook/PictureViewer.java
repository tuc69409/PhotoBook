package com.example.photobook;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PictureViewer extends Activity {
	
	/*Opens up clicked picture and displays information recorded
	 * - maybe has option to edit caption, send to social media
	 * - needs back button to return to main viewer
	 */
	
	FrameLayout picture;
	String temperatureData, locationData, timeStampData, altitudeData;
	TextView temperature, location, timeStamp, altitude;
	
	/*Create menu with back button*/
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.viewer, menu);
			return true;
		}
		
		/*User clicked back; return to stream*/
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.return_to_Stream:
				Intent openStream = new Intent(PictureViewer.this, PictureStream.class);
				startActivity(openStream);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
		
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_viewer);
	
		/*Initialize widgets*/
		temperature = (TextView)findViewById(R.id.temperature);
		location = (TextView)findViewById(R.id.location);
		timeStamp = (TextView)findViewById(R.id.timeStamp);
		altitude = (TextView)findViewById(R.id.altitude);
		
		/*Initialize layout*/
		picture = (FrameLayout)findViewById(R.id.pictureViewer);
		
		/*Get clicked picture from intent and display*/
//		picture.setBackground(background);
		
		/*Get picture information from database and display*/
		
		
	}

}
