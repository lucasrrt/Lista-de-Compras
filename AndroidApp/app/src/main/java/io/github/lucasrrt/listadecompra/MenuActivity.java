package io.github.lucasrrt.listadecompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {
    String userId;
    TextView print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        print = (TextView)findViewById(R.id.print);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId = null;
            } else {
                userId= extras.getString("ID");
            }
        } else {
            userId= (String) savedInstanceState.getSerializable("ID");
        }
        HelloUser();
    }

    public void HelloUser(){
        AJAXCall.HTTPCallback<String> callback = (data)->{
            try {
                JSONArray array = new JSONArray(data);
                String userLogin = array.getJSONObject(0).getString("login");
                print.setText("Ola usuário "+userLogin);

            }catch(Exception e){ }
        };

        AJAXCall.HTTPCallback<String> callbackError = (data)->{
            try {
                Toast.makeText(this, "Erro na conexão", Toast.LENGTH_SHORT).show();
            }catch(Exception e){ }
        };

        String url = "http://192.168.0.23:4567/usuarios/"+userId;
        AJAXCall.get(url,null,callback, callbackError);
    }

    public void iniciarCompra(View view){
        MenuActivity menuActivity = this;
        View container = LayoutInflater.from(this).inflate(R.layout.dialog_iniciar_compra,null);
        DatePicker datePicker = (DatePicker) container.findViewById(R.id.datePicker);
        EditText mercado = (EditText)container.findViewById(R.id.mercado);
        new AlertDialog.Builder(this)
                .setTitle("Iniciar compra")
                .setView(container)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Toast.makeText(menuActivity,datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(menuActivity, "Adicionado: "+mercado.getText().toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(menuActivity, CompraActivity.class);
                            intent.putExtra("userId", userId);
                            intent.putExtra("mercado", mercado.getText().toString());
                            intent.putExtra("date", datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());

                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(menuActivity, "Erro", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).show();
    }

    public void verRelatorio(View view){

    }

}
