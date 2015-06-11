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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.database.FoodDatabase;
import comp.ufu.restaurante.database.sqlite.OrderOperations;
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
	private Button btnLogout, btnUpdate, btnCloseOrder, btnListOrders;

	// food_listview
	private ListView listViewCardapio;

	// orders
	private Order myCurrentOrder;

	// my info
	private String name, table;

	// flag
	private boolean hasOrderedSomething;

	private OrderOperations orderDbOperations = null;

	public MainScreen(){}

	public MainScreen(ListView listViewCardapio, Order myCurrentOrder) {
		this.listViewCardapio = listViewCardapio;
		this.myCurrentOrder = myCurrentOrder;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		// get intent data
		if(getIntent().hasExtra("name") && getIntent().hasExtra("table") && getIntent().hasExtra("current_order")){
			String name_extra = getIntent().getExtras().getString("name");
			String table_extra = getIntent().getExtras().getString("table");
			String currentOrderString_extra = getIntent().getExtras().getString("current_order");

			if(name_extra != null && table_extra != null && currentOrderString_extra != null){
				name = name_extra;
				table = table_extra;
				myCurrentOrder = parseOrder(table_extra, currentOrderString_extra);
			}
		}
		else{
			myCurrentOrder = new Order(table);
		}

		// Session class instance
		session = new SessionManager(getApplicationContext());

		final TextView lblName = (TextView) findViewById(R.id.label_name);
		final TextView lblTable = (TextView) findViewById(R.id.label_table);

		// start database
		orderDbOperations = new OrderOperations(this);
		try {
			orderDbOperations.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				i.putExtra("table", table);
				i.putExtra("name", name);
				i.putExtra("current_order", myCurrentOrder.getOrders());
				startActivity(i);
				finish();
			}
		});

		// Cardapio
		inflateListViewLayouts();

		btnListOrders = (Button) findViewById(R.id.btn_list_orders);
		btnListOrders.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Order> orders = (ArrayList<Order>) orderDbOperations.getAllOrders();
				AlertDialog.Builder adb = new AlertDialog.Builder(MainScreen.this);
				adb.setTitle("Pedidos no Banco de Dados");

				String ordersText = "";
				for (Order order : orders) {
					ordersText += "\nID: " + order.getId() + "\n";
					ordersText += "Mesa: " + order.getTable() + "\n";
					ordersText += "Consumo:";
					for (int i = 0; i < order.getFoodOrdered().length; i++) {
						if (order.getFoodOrdered()[i] > 0) {
							ordersText += "\n\t(" + order.getFoodOrdered()[i] + ") " + FoodDatabase.getInstance().getCardapio().get(i).getName();
						}
					}
					ordersText += "\nTotal: " + Math.floor(order.getTotalSpent()) + "\n";
				}
				adb.setMessage(ordersText);
				adb.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				adb.show();
			}
		});

		// Button logout
		btnCloseOrder = (Button) findViewById(R.id.btn_close_order);
		btnCloseOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		orderDbOperations.close();
	}

	private void inflateListViewLayouts(){
		listViewCardapio = (ListView) findViewById(R.id.list_cardapio);
		listViewCardapio.setAdapter(new FoodListViewArrayAdapter(this, FoodDatabase.getInstance().getCardapio(), myCurrentOrder));
	}

	public Order getMyCurrentOrder() {
		return myCurrentOrder;
	}

	public AlertDialogManager getAlert() {
		return alert;
	}

	public void setAlert(AlertDialogManager alert) {
		this.alert = alert;
	}

	public SessionManager getSession() {
		return session;
	}

	public void setSession(SessionManager session) {
		this.session = session;
	}

	public Button getBtnLogout() {
		return btnLogout;
	}

	public void setBtnLogout(Button btnLogout) {
		this.btnLogout = btnLogout;
	}

	public Button getBtnUpdate() {
		return btnUpdate;
	}

	public void setBtnUpdate(Button btnUpdate) {
		this.btnUpdate = btnUpdate;
	}

	public Button getBtnCloseOrder() {
		return btnCloseOrder;
	}

	public void setBtnCloseOrder(Button btnCloseOrder) {
		this.btnCloseOrder = btnCloseOrder;
	}

	public Button getBtnListOrders() {
		return btnListOrders;
	}

	public void setBtnListOrders(Button btnListOrders) {
		this.btnListOrders = btnListOrders;
	}

	public ListView getListViewCardapio() {
		return listViewCardapio;
	}

	public void setListViewCardapio(ListView listViewCardapio) {
		this.listViewCardapio = listViewCardapio;
	}

	public void setMyCurrentOrder(Order myCurrentOrder) {
		this.myCurrentOrder = myCurrentOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public OrderOperations getOrderDbOperations() {
		return orderDbOperations;
	}

	public void setOrderDbOperations(OrderOperations orderDbOperations) {
		this.orderDbOperations = orderDbOperations;
	}

	private Order parseOrder(String table, String orders){
		Order myOrder = new Order();
		myOrder.setTable(table);
		int[] foodOrdered = new int[FoodDatabase.getInstance().getCardapio().size()];

		String[] split1 = orders.split("-");
		for (int i = 0; i < split1.length; i++){
			String[] split2 = split1[i].split("#");
			int id = Integer.parseInt(split2[0]);
			int quantity = Integer.parseInt(split2[1]);
			foodOrdered[id] = quantity;
		}
		myOrder.setFoodOrdered(foodOrdered);

		return myOrder;
	}
}
