package com.example.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity {
	
	/*Name Widgets*/
	Button signUp;
	EditText username, password, passwordConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		/*Initialize widgets*/
		signUp = (Button) findViewById(R.id.signUpComplete);
		username = (EditText) findViewById(R.id.newUsername);
		password = (EditText) findViewById(R.id.newPassword);
		passwordConfirm = (EditText) findViewById(R.id.newPasswordConfirm);
		
		/*Complete sign up*/
		signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*Check that passwords match and username is unique
				 * if yes, return a success message
				 * if no, return error message
				 * */

				/*Open login screen*/
				Intent openStream = new Intent(SignUp.this, PictureStream.class);
				startActivity(openStream);
				
			}
		});
		
		
	}

}
