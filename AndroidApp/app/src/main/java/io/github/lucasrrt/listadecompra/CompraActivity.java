package io.github.lucasrrt.listadecompra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompraActivity extends AppCompatActivity {
    private ArrayList<JSONObject> shopCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shopCart = new ArrayList<JSONObject>();

        setContentView(R.layout.activity_compra);

        ListView view = (ListView) findViewById(R.id.output);

        AJAXCall.HTTPCallback<String> callback = (data)->{
            try {
                JSONArray array = new JSONArray(data);
                String[] stringArray = new String[array.length()];
                for( int t=0;t<array.length();t++ ){
                    stringArray[t] = array.getJSONObject(t).getString("nome");
                }
                view.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, stringArray));
                CompraActivity activity = this;
                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CompraFormDialog dialog = new CompraFormDialog(()->{
                            Toast.makeText(activity,"enviado",Toast.LENGTH_LONG).show();
                        },()->{
                            Toast.makeText(activity,"cancelado",Toast.LENGTH_LONG).show();
                        });
                        dialog.show(getSupportFragmentManager(),"acho que aqui vem titulo");
                    }
                });
            }catch (JSONException e){ }
        };
        AJAXCall.HTTPCallback<String> callbackError = (data)->{
            try {
                Toast.makeText(this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }catch (Exception e){ }
        };
        AJAXCall.get("http://192.168.0.21:4567/produtos",null,callback,callbackError);
    }



    private float computePrice(){
        float sum=0;
        for(JSONObject obj : shopCart){
            try {
                sum += obj.getLong("quantidade") * obj.getLong("preco");
            }catch (JSONException e){}
        }
        return sum;
    }

    public void finishShpping(View v) {
        Toast.makeText(this, "Finalizado", Toast.LENGTH_SHORT).show();
    }
}
