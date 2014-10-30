package com.example.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
				
				/*Open sign up form*/
				Intent openSignUp = new Intent(StartScreen.this, SignUp.class);
				startActivity(openSignUp);
			}
		});
		 
		 
		 /*Login button clicked*/
		 login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					/*Get username and password from user input*/
					String usernameS, passwordS;
					usernameS = username.getText().toString();
					passwordS = password.getText().toString();
					
					/*if(username in database & password is correct){
					 
					 	Start picture book if success*/
						Intent openPictureBook = new Intent(StartScreen.this, PictureStream.class);
						startActivity(openPictureBook);
						
					/*else if(username not in database){*/
						Toast.makeText(StartScreen.this, "Username does not exist", Toast.LENGTH_LONG).show();
					/*else if(password incorrect)*/
						Toast.makeText(StartScreen.this, "Password incorrect", Toast.LENGTH_LONG).show();
					
			
				}
			});
		
		
	}
	
	

}
