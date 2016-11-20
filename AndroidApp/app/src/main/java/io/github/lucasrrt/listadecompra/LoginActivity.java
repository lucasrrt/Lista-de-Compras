package io.github.lucasrrt.listadecompra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
	EditText login;
	EditText senha;
    Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		login = (EditText)findViewById(R.id.login);
		senha = (EditText)findViewById(R.id.senha);

	}

	public void SignIn(View view){
		AJAXCall.HTTPCallback<String> callback = (data)->{
			try {
                intent = new Intent(this,MenuActivity.class);
                startActivity(intent);
                finish();
			}catch(Exception e){ }
		};
		String url = "http://192.168.0.23:4567/auth?login="+login.getText().toString()+"&senha="+senha.getText().toString();
		AJAXCall.get(url,null,callback);



	}
}
