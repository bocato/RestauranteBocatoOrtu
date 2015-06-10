package comp.ufu.restaurante.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.database.FoodDatabase;
import comp.ufu.restaurante.model.Order;
import comp.ufu.restaurante.tools.AlertDialogManager;
import comp.ufu.restaurante.tools.SessionManager;
import comp.ufu.restaurante.util.FoodListViewArrayAdapter;

public class MainScreen extends Activity {

	// Alert Dialog Manager
	private AlertDialogManager alert = new AlertDialogManager();

	// Session Manager Class
	private SessionManager session;

	// Button Logout
	private Button btnLogout, btnUpdate, btnCloseOrder;

	// food_listview
	private ListView listViewEntradas, listViewCarnes, listViewPorcoes, listViewBebidas, listViewCardapio;

	// orders
	private Order myCurrentOrder;

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

		// set my current order
		myCurrentOrder = new Order(table);

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

		btnUpdate = (Button) findViewById(R.id.btn_update); // TODO: Implement

		// Cardapio
		inflateListViewLayouts();

		// Button logout
		btnCloseOrder = (Button) findViewById(R.id.btn_close_order);

	}

	private void inflateListViewLayouts(){
		listViewCardapio = (ListView) findViewById(R.id.list_cardapio);
		listViewCardapio.setAdapter(new FoodListViewArrayAdapter(this, FoodDatabase.getInstance().getCardapio(), myCurrentOrder));
	}

}
