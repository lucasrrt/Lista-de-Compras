package io.github.lucasrrt.listadecompra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AJAXCall.HTTPCallback<String> callback = (data)-> System.out.println(data);
        AJAXCall.http("GET","http://paperx.com.br/",null,callback);

    }
}
