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
    static public void http(String method,String path, HashMap<String,String> params,HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        AsyncTask task = new AsyncTask() {
            private String str;
            private int code;
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.connect();

                    code = conn.getResponseCode();

                    InputStream is = conn.getInputStream();
                    str = convertStreamToString(is);


                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if(code == 200) {
                    callback.call(str);
                } else {
                    callbackError.call(str);
                }
            }
        };
        task.execute();

    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    static public void get(String path, HashMap<String,String> params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("GET",path,params,callback, callbackError);
    }
    static public void post(String path, HashMap<String,String> params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("POST",path,params,callback, callbackError);
    }
    static public void put(String path, HashMap<String,String> params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("PUT",path,params,callback, callbackError);
    }
    static public void delete(String path, HashMap<String,String> params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("DELETE",path,params,callback, callbackError);
    }
}
