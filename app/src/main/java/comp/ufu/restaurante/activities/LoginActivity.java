package comp.ufu.restaurante.activities;

/**
 * Created by bocato on 5/26/15.
 */

import android.app.Activity;
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

    // Email, table edittext
    EditText txtName, txtTable;

    // login / register button
    Button btnLogin, btnRegister;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Name, Table input text
        txtName = (EditText) findViewById(R.id.txtName);
        txtTable = (EditText) findViewById(R.id.txtTable);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, table from EditText
                String username = txtName.getText().toString();
                String table = txtTable.getText().toString();

                // Check if username, table is filled
                if (username.trim().length() > 0 && table.trim().length() > 0) {
                    // For testing puspose username, table is checked with sample data
                    // username = test
                    // table = test
                    if (username.equals("Teste") && table.equals("Mesa 1")) {

                        // Creating user login session
                        session.createLoginSession("Teste", "Mesa 1");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainScreen.class);
                        startActivity(i);
                        finish();

                    } else if (userExistsInSessionManager(new User(username, table))) {
                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainScreen.class);
                        startActivity(i);
                        finish();

                        // username / table doesn't match
                        //alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                } else {
                    // user didn't entered username or table
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and table", false);
                }

            }
        });

        // Register Button
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username, table from EditText
                String name = txtName.getText().toString();
                String table = txtTable.getText().toString();

                if (name.trim().length() > 0 && table.trim().length() > 0) {
                    // Creating user login session
                    session.createLoginSession(name, table);

                    // Staring MainActivity
                    Intent i = new Intent(getApplicationContext(), MainScreen.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private boolean userExistsInSessionManager(User user){
        Map<String, ?> userData = session.getSharedPreferences().getAll();
        if(userData.get("name").equals(user.getName()) && userData.get("table").equals(user.getTable())){
            return true;
        }
        return false;
    }
}