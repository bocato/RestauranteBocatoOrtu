package comp.ufu.restaurante.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
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

	// my info
	private String name, table;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Session class instance
		session = new SessionManager(getApplicationContext());

		final TextView lblName = (TextView) findViewById(R.id.label_name);
		final TextView lblTable = (TextView) findViewById(R.id.label_table);

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
		name = user.get(SessionManager.KEY_NAME);

		// email
		table = user.get(SessionManager.KEY_TABLE);

		// displaying user data
		lblName.setText(Html.fromHtml("Nome: <b>" + name + "</b>"));
		lblTable.setText(Html.fromHtml("Número da Mesa: <b>" + table + "</b>"));

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

		// TODO: Fazer Funcionar!
		btnUpdate = (Button) findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "Clicou em Atualizar Dados!", Toast.LENGTH_LONG).show();
				AlertDialog.Builder adb = new AlertDialog.Builder(MainScreen.this);
				adb.setView(getLayoutInflater().inflate(R.layout.edit_userdata_dialog, null));
				TextView editNameTextView = (TextView) findViewById(R.id.edit_name_txt);
				TextView editTableTextView = (TextView) findViewById(R.id.edit_table_txt);
				if(editNameTextView != null && editTableTextView != null){
					editNameTextView.setText(name);
					editTableTextView.setText(table);
				}

				adb.setTitle("Editar [Nome] e [Mesa]?");
				adb.setNegativeButton("Fechar", null);
				adb.setPositiveButton("Salvar",
						new AlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								// Creating user login session
								TextView editNameTextView = (TextView) findViewById(R.id.edit_name_txt);
								TextView editTableTextView = (TextView) findViewById(R.id.edit_table_txt);
								if(editNameTextView != null && editTableTextView != null){
									name = editNameTextView.getText().toString();
									table = editTableTextView.getText().toString();
								}

								session.getSharedPreferences().edit().putString(SessionManager.KEY_NAME, name).commit();
								session.getSharedPreferences().edit().putString(SessionManager.KEY_TABLE, table).commit();

								// update data
								lblName.setText(Html.fromHtml("Nome: <b>" + name + "</b>"));
								lblTable.setText(Html.fromHtml("Número da Mesa: <b>" + table + "</b>"));
							}
						});
				adb.show();
			}
		});

		// Cardapio
		inflateListViewLayouts();

		// Button logout
		btnCloseOrder = (Button) findViewById(R.id.btn_close_order);
		btnCloseOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "Clicou em Finalizar!\n"+myCurrentOrder.getOrders(), Toast.LENGTH_LONG).show();

				// Starting FinalizeOrderActivity
				Intent i = new Intent(getApplicationContext(), FinalizeOrderActivity.class);
				i.putExtra("table", table);
				i.putExtra("name", name);
				i.putExtra("current_order", myCurrentOrder.getOrders());
				startActivity(i);
				finish();
			}
		});

	}

	private void inflateListViewLayouts(){
		listViewCardapio = (ListView) findViewById(R.id.list_cardapio);
		listViewCardapio.setAdapter(new FoodListViewArrayAdapter(this, FoodDatabase.getInstance().getCardapio(), myCurrentOrder));
	}

	public Order getMyCurrentOrder() {
		return myCurrentOrder;
	}
}
