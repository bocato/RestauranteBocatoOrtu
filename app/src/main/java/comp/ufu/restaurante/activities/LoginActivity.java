package comp.ufu.restaurante.activities;

/**
 * Created by bocato on 5/26/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.database.FoodDatabase;
import comp.ufu.restaurante.model.Order;
import comp.ufu.restaurante.model.User;
import comp.ufu.restaurante.tools.AlertDialogManager;
import comp.ufu.restaurante.tools.SessionManager;

public class LoginActivity extends Activity {

    // name, table holder
    private String name = null;
    private String table = null;

    // Email, table edittext
    private EditText txtName, txtTable;

    // login / register button
    private Button btnLogin;

    // Alert Dialog Manager
    private AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    private SessionManager session;

    // myCurrentOerder
    private Order myCurrentOrder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLogin.setText("Atualizar Dados");

            txtName = (EditText) findViewById(R.id.txtName);
            txtName.setText(name);

            txtTable = (EditText) findViewById(R.id.txtTable);
            txtTable.setText(table);
        }
        else{
            myCurrentOrder = new Order(table);
        }

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Name, Table input text
        txtName = (EditText) findViewById(R.id.txtName);
        txtTable = (EditText) findViewById(R.id.txtTable);

        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get Name, Table from EditText
                name = txtName.getText().toString();
                table = txtTable.getText().toString();

                // Check if Name, Table is filled
                if(name.trim().length() > 0 && table.trim().length() > 0){
                    if(name.equals("teste") && table.equals("0000")){

                        // Creating user login session
                        session.createLoginSession("teste", "0000");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainScreen.class);
                        startActivity(i);
                        finish();

                    }
                    else{
                        if(btnLogin.getText().equals("Atualizar Dados")){
                            // Creating user login session
                            session.createLoginSession(name, table);

                            // Staring MainActivity
                            Intent i = new Intent(getApplicationContext(), MainScreen.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            final String _name = name, _table = table;
                            AlertDialog.Builder adb = new AlertDialog.Builder(LoginActivity.this);
                            adb.setTitle("Registrar?");
                            adb.setMessage("Gostaria de registrar este Nome e Mesa?");
                            adb.setNegativeButton("Não", null);
                            adb.setPositiveButton("Sim",
                                    new AlertDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // Creating user login session
                                            session.createLoginSession(_name, _table);

                                            // Staring MainActivity
                                            Intent i = new Intent(getApplicationContext(), MainScreen.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                            adb.show();
                        }
                    }
                }else{
                    // user didn't entered username or table
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login falhou...", "Entre com Nome e Mesa.", false);
                }

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
        }
        myOrder.setFoodOrdered(foodOrdered);

        return myOrder;
    }
}