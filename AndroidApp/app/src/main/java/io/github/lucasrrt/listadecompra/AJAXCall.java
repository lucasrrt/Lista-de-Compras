package io.github.lucasrrt.listadecompra;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Exchanger;

/**
 * Created by bruno on 19/11/16.
 */

public class AJAXCall {
    interface HTTPCallback<T>{
        public void call(T t);
    }
    static public void http(String method,String path, JSONObject params,HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        AsyncTask task = new AsyncTask() {
            private String str;
            private int code;
            @Override
            protected Object doInBackground(Object[] o) {
                try {
                    String newPath = path;
                    if (params!= null){
                        newPath+="?";
                        Iterator<?> keys = params.keys();
                        while(keys.hasNext()){
                            String key = (String)keys.next();
                            newPath+=URLEncoder.encode(key)+"="+URLEncoder.encode(params.getString(key));
                            if(keys.hasNext())
                                newPath+="&";
                        }
                    }
                    URL url = new URL(newPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.setRequestProperty("Content-Type","application/json");
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
    static public void get(String path, JSONObject params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("GET",path,params,callback, callbackError);
    }
    static public void post(String path, JSONObject params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("POST",path,params,callback, callbackError);
    }
    static public void put(String path, JSONObject params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("PUT",path,params,callback, callbackError);
    }
    static public void delete(String path, JSONObject params, HTTPCallback<String> callback, HTTPCallback<String> callbackError){
        http("DELETE",path,params,callback, callbackError);
    }
}
