package comp.ufu.restaurante;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainScreen extends Activity{
	Button signInButton;
	TextView nameTextView, tableTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {
			nameTextView = (TextView) findViewById(R.id.register_name);
			tableTextView = (TextView) findViewById(R.id.register_table);
			signInButton = (Button) findViewById(R.id.sign_in_btn);
		} catch (Exception e) {
			Log.e("some error", "" + e);
		}

		signInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent add_user = new Intent(MainScreen.this, UserRegisterLogin.class);
//				add_user.putExtra("called", "add");
//				add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(add_user);
//				finish();
				showToast("Sign In Clicked");
			}
		});

	}

	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
