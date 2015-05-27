package comp.ufu.restaurante.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.tools.AlertDialogManager;
import comp.ufu.restaurante.tools.SessionManager;

public class MainScreen extends Activity {

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Session Manager Class
	SessionManager session;

	// Button Logout
	Button btnLogout, btnUpdate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Session class instance
		session = new SessionManager(getApplicationContext());

		TextView lblName = (TextView) findViewById(R.id.label_name);
		TextView lblEmail = (TextView) findViewById(R.id.label_table);

		// Button logout
		btnLogout = (Button) findViewById(R.id.btn_logout);

		Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


		/**
		 * Call this function whenever you want to check user login
		 * This will redirect user to LoginActivity is he is not
		 * logged in
		 * */
		session.checkLogin();

		// get user data from session
		HashMap<String, String> user = session.getUserDetails();

		// name
		String name = user.get(SessionManager.KEY_NAME);

		// email
		String table = user.get(SessionManager.KEY_TABLE);

		// displaying user data
		lblName.setText(Html.fromHtml("Nome: <b>" + name + "</b>"));
		lblEmail.setText(Html.fromHtml("Número da Mesa: <b>" + table + "</b>"));


		/**
		 * Logout button click event
		 * */
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Clear the session data
				// This will clear all session data and
				// redirect user to LoginActivity
				session.logoutUser();
			}
		});

		btnUpdate = (Button) findViewById(R.id.btn_update);
	}

}
