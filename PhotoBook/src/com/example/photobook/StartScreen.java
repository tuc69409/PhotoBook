package com.example.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartScreen extends Activity {
	
	
	/* OVERAL TO DO:
	 
	set left margins of each widget so that they are relative to background image and
	work for all screens
	
	*/
	
	/*Name Widgets*/
	Button signUp, login;
	EditText username, password;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		
		/*Initialize widgets*/
		signUp = (Button) findViewById(R.id.signUp);
		login = (Button) findViewById(R.id.login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		
		
		 /*Sign up button clicked*/
		 signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openSignUp = new Intent(StartScreen.this, SignUp.class);
				startActivity(openSignUp);
				
			}
		});
		 
		 
		 /*Login button clicked*/
		 login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					/*Check for username/password match is valid
					 * Return error message if no match
					 */
					
					/*Start picture book*/
					Intent openPictureBook = new Intent(StartScreen.this, PictureStream.class);
					startActivity(openPictureBook);
					
				}
			});
		
		
	}
	
	

}
