package com.example.photobook;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/*Once picture is taken, this screen will display picture and information. It will allow user to 
 * write a caption and then save the picture. User clicks save to send all information to database. User
 * can also cancel to return to the camera to take a different picture.
 */

public class PictureEditor extends Activity{

	/*User enters caption*/
	
	/*Clicks save to start service to fetch info and send to database*/
	
//	LocationManager to get GPS info
	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	
	public void onLocationChanged(Location location){
		
		/*Get altitude*/
		 location.getAltitude();
		 
		 /*Get location*/
		 location.getLatitude();
		 location.getLongitude();
		
	 }
	
}
