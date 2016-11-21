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
				JSONArray array = new JSONArray(data);
				intent = new Intent(this,MenuActivity.class);
				String strName = array.getJSONObject(0).getString("id");
				intent.putExtra("ID", strName);
                startActivity(intent);
                finish();
			}catch(Exception e){ }
		};
		AJAXCall.HTTPCallback<String> callbackError = (data)->{
			System.out.println("###################");
			try {
				System.out.println("###################");
				Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
				System.out.println("###################");
			}catch(Exception e){ }
			System.out.println("###################");
		};
		String url = "http://192.168.0.21:4567/auth?login="+login.getText().toString()+"&senha="+senha.getText().toString();
		AJAXCall.get(url,null,callback, callbackError);



	}
}
