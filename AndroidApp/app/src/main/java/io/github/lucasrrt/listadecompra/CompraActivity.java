package io.github.lucasrrt.listadecompra;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompraActivity extends AppCompatActivity {
    private ArrayList<JSONObject> shopCart;
    TextView sumCount;
    String market;
    String userId;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shopCart = new ArrayList<JSONObject>();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                market = null;
                userId = null;
                date = null;
            } else {
                market = extras.getString("mercado");
                userId = extras.getString("userId");
                date = extras.getString("date");
            }
        } else {
            market = (String) savedInstanceState.getSerializable("mercado");
            userId = (String) savedInstanceState.getSerializable("userId");
            date = (String) savedInstanceState.getSerializable("date");
        }


        setContentView(R.layout.activity_compra);
        sumCount = (TextView)findViewById(R.id.sum_count);

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
                        View container = LayoutInflater.from(activity).inflate(R.layout.dialog_compra,null);
                        EditText priceInput = (EditText)container.findViewById(R.id.price);
                        EditText quantityInput = (EditText)container.findViewById(R.id.quantity);
                        new AlertDialog.Builder(activity)
                                .setTitle("Confirmação de compra")
                                .setMessage("aqui tem uma mensagem que não sei pra que serve")
                                .setView(container)
                                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            JSONObject compra = new JSONObject();
                                            compra.put("preco",priceInput.getText().toString());
                                            compra.put("quantidade",quantityInput.getText().toString());
                                            compra.put("mercado",market);
                                            compra.put("produto_id",array.getJSONObject(position).getString("id"));
                                            compra.put("usuario_id",userId);
                                            compra.put("data",date);
                                            compra.put("mercado",market);
                                            shopCart.add(compra);
                                            computePrice();
                                            Toast.makeText(activity, "Adicionado", Toast.LENGTH_SHORT).show();
                                        }catch (JSONException e){
                                            Toast.makeText(activity, "Erro", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).show();
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
       sumCount.setText("Total: R$"+sum);
        return sum;
    }

    public void finishList(View v) {
        AJAXCall.HTTPCallback<String> callback = (b)->{
            Toast.makeText(this, "Finalizado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        };
        AJAXCall.HTTPCallback<String> callbackError = (b)->{

            Toast.makeText(this, "Erro ao finalizar a compra", Toast.LENGTH_SHORT).show();
        };
        for(int t=0;t < shopCart.size() ; t++) {
            AJAXCall.post("http://192.168.0.21:4567/compras",shopCart.get(t),callback,callbackError);
        }

    }

    public void addItem(View v){
        EditText quantityEditText = (EditText)findViewById(R.id.quantity);
        EditText priceEditText = (EditText)findViewById(R.id.price);

        float price = Float.parseFloat(priceEditText.getText().toString());
        float quantity = Float.parseFloat(quantityEditText.getText().toString());

        Toast.makeText(this, (price*quantity)+"", Toast.LENGTH_SHORT).show();
    }
}
