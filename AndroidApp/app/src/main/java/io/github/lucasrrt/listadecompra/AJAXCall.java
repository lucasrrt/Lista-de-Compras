package io.github.lucasrrt.listadecompra;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Exchanger;

/**
 * Created by bruno on 19/11/16.
 */

public class AJAXCall {
    interface HTTPCallback<T>{
        public void call(T t);
    }
    static public void http(String method,String path, HashMap<String,String> params,HTTPCallback<String> callback){
        AsyncTask task = new AsyncTask() {
            private String str;
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    str = convertStreamToString(is);


                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                callback.call(str);
            }
        };
        task.execute();

    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    static public void get(String path, HashMap<String,String> params, HTTPCallback<String> callback){
        http("GET",path,params,callback);
    }
    static public void post(String path, HashMap<String,String> params, HTTPCallback<String> callback){
        http("POST",path,params,callback);
    }
    static public void put(String path, HashMap<String,String> params, HTTPCallback<String> callback){
        http("PUT",path,params,callback);
    }
    static public void delete(String path, HashMap<String,String> params, HTTPCallback<String> callback){
        http("DELETE",path,params,callback);
    }
}
