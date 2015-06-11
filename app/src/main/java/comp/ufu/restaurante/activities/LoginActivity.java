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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                }else{
                    // user didn't entered username or table
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login falhou...", "Entre com Nome e Mesa.", false);
                }

            }
        });
    }
}