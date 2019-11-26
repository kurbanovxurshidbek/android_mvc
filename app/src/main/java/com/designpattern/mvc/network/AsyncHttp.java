package com.designpattern.mvc.network;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttp {

    private static boolean IS_TESTER = true;
    private static String DEPLOYMENT_SERVER = "http://localhost:8080/";
    private static String DEVELOPMENT_SERVER = "http://localhost:8080/";

    private static AsyncHttpClient client = getHttpClient();

    /**
     * AsyncHttp Requests
     */

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String serverUrl = getServer(url);
        client.get(serverUrl, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        String serverUrl = getServer(url);
        Log.d("@@@", "@@@serverUrl " + serverUrl);
        //fireToast(context, serverUrl + "?" + params.toString());
        client.post(serverUrl, params, responseHandler);
    }

    /**
     * AsyncHttp Methods
     */

    public static String getServer(String url) {
        String serverUrl = "";

        if (IS_TESTER) {
            serverUrl = DEVELOPMENT_SERVER + url;
        } else {
            serverUrl = DEPLOYMENT_SERVER + url;
        }
        Log.d("@@@", "serverUrl " + serverUrl);
        return serverUrl;
    }

    public static AsyncHttpClient getHttpClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-type", "application/json;charset=utf-8");

        client.setTimeout(20000);
        client.setConnectTimeout(20000);
        client.setResponseTimeout(20000);
        client.setMaxRetriesAndTimeout(3, 15000);
        return client;
    }

    /**
     * AsyncHttp Api's
     */

    public static String API_USER_LOGIN = "api/login";


    /**
     * AsyncHttp Params
     */

    public static RequestParams paramsUserLogin(String email, String password) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        Log.d("@@@", "@@@params: " + params.toString());
        return params;
    }

}
