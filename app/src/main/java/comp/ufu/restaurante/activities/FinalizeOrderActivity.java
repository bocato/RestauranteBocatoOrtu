package comp.ufu.restaurante.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.database.FoodDatabase;
import comp.ufu.restaurante.database.sqlite.OrderOperations;
import comp.ufu.restaurante.model.Food;
import comp.ufu.restaurante.model.Order;
import comp.ufu.restaurante.tools.AlertDialogManager;
import comp.ufu.restaurante.tools.SessionManager;
import comp.ufu.restaurante.util.FoodListViewArrayAdapter;
import comp.ufu.restaurante.util.OrderListViewArrayAdapter;

public class FinalizeOrderActivity extends Activity {

	// Alert Dialog Manager
	private AlertDialogManager alert = new AlertDialogManager();

	// Session Manager Class
	private SessionManager session;

	// Button Logout
	private Button btnFinalizeOrder, bntRefreshValue, btnListOrders;

	// food_listview
	private ListView listViewCardapio;

	// textview
	private TextView textViewTotal;

	// orders
	private Order myCurrentOrder;

	// food ordered
	private ArrayList<Food> foodOrdered;

	// database
	private OrderOperations orderDbOperations = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finalize_order);

		orderDbOperations = new OrderOperations(this);
		try {
			orderDbOperations.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// getting data
		String name = getIntent().getExtras().getString("name");
		String table = getIntent().getExtras().getString("table");
		String currentOrderString = getIntent().getExtras().getString("current_order");

		inflateListViewLayouts(table, currentOrderString);

		textViewTotal = (TextView) findViewById(R.id.text_view_total_value);
		textViewTotal.setText("" + Math.floor(Math.floor(myCurrentOrder.getTotalSpent())));

		// Button
		btnListOrders = (Button) findViewById(R.id.btn_list_orders);
		btnListOrders.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<Order> orders = (ArrayList<Order>) orderDbOperations.getAllOrders();
				AlertDialog.Builder adb = new AlertDialog.Builder(FinalizeOrderActivity.this);
				adb.setTitle("Pedidos no Banco de Dados");

				String ordersText = "";
				for (Order order: orders){
					ordersText+="\nID: "+order.getId()+"\n";
					ordersText+="Mesa: "+order.getTable()+"\n";
					ordersText+="Consumo:";
					for (int i = 0; i < order.getFoodOrdered().length; i++){
						if(order.getFoodOrdered()[i] > 0){
							ordersText+="\n\t("+order.getFoodOrdered()[i]+") "+FoodDatabase.getInstance().getCardapio().get(i).getName();
						}
					}
					ordersText+="\nTotal: "+Math.floor(order.getTotalSpent())+"\n";
				}
				adb.setMessage(ordersText);
				adb.setNegativeButton("Fechar",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				adb.show();
			}
		});

		bntRefreshValue = (Button) findViewById(R.id.btn_refresh_value);
		bntRefreshValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView textViewTotal = (TextView) findViewById(R.id.text_view_total_value);
				textViewTotal.setText("" +  Math.floor(myCurrentOrder.getTotalSpent()));
			}
		});

		// Button
		btnFinalizeOrder = (Button) findViewById(R.id.btn_finalize_order);
		btnFinalizeOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "Clicou em Finalizar!\n" + myCurrentOrder.getOrders(), Toast.LENGTH_LONG).show();
				Order saved = orderDbOperations.addOrder(myCurrentOrder);
				Toast.makeText(getApplicationContext(), "Pedido da mesa "+ saved.getTable() + " salvo com sucesso!", Toast.LENGTH_LONG).show();
				System.out.println("Pedido da mesa"+saved.getTable()+ "salvo com sucesso!");
			}
		});
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
			//System.out.println(i+" ~ foodOrdered["+id+"] = "+quantity);
		}

		myOrder.setFoodOrdered(foodOrdered);

		//System.out.println("Orders = " + myOrder.getOrders());

		return myOrder;
	}

	private ArrayList<Food> parseFoodOrdered() {
		ArrayList<Food> myFood = new ArrayList<>();

		for (int i = 0; i < myCurrentOrder.getFoodOrdered().length; i++){
			int id = i;
			Food aFood = FoodDatabase.getInstance().getCardapio().get(id);
			//System.out.println(aFood.getName()+" = "+myCurrentOrder.getFoodOrdered()[id]);
			if(myCurrentOrder.getFoodOrdered()[id] > 0){
				myFood.add(aFood);
			}
		}
		return myFood;
	}

	private void inflateListViewLayouts(String table, String currentOrderString){
		// parse data
		myCurrentOrder = parseOrder(table, currentOrderString);
		foodOrdered = parseFoodOrdered();

		// set up the listview
		listViewCardapio = (ListView) findViewById(R.id.list_food_ordered);
		listViewCardapio.setAdapter(new OrderListViewArrayAdapter(this, foodOrdered, myCurrentOrder));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		orderDbOperations.close();
	}
}
