package com.nakul.meesho.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class VolleyHelper {
    private static VolleyHelper mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static final String TAG = "VolleyHelper";

    private VolleyHelper(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }


    public static synchronized VolleyHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyHelper(context);
            VolleyLog.DEBUG = false;
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(mCtx);

        }
        return mRequestQueue;
    }

    public static JSONObject getRepoIssues(int pageIndex, String repoName, String ownerName) {
        String response = "";
        JSONObject responseJson = new JSONObject();
        boolean error = false;
        try {
            RequestFuture<String> future = RequestFuture.newFuture();
            StringRequest request = new StringRequest(Request.Method.GET, "https://api.github.com/repos/" + ownerName + "/" + repoName + "/pulls?state=open&" +
                    "per_page=10&page="+(pageIndex+1), future, future) {


                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Accept-Charset", "utf-8");
                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };
            Volley.newRequestQueue(mCtx).add(request);

            response = future.get();

        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException", e);
            error = true;
            response = e.getMessage();
        } catch (ExecutionException e) {
            if (e.getCause().toString().contains("NoConnectionError")) {
                Log.e(TAG, e.getMessage());
            } else if (e.getCause().toString().contains("AuthFailureError")) {
                Log.e(TAG, e.getMessage());
            } else if (e.getCause().toString().contains("NetworkError")) {
                Log.e(TAG, e.getMessage());
            } else if (e.getCause().toString().contains("ParseError")) {
                Log.e(TAG, e.getMessage());
            } else if (e.getCause().toString().contains("ServerError")) {
                Log.e(TAG, e.getMessage());
            } else if (e.getCause().toString().contains("TimeoutError")) {
                Log.e(TAG, e.getMessage());
            }
            error = true;
            response = e.getMessage();
        } finally {
            try {
                if (error) {
                    responseJson.put("error", response);
                } else {
                    responseJson.put("result", new JSONArray(response));
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return responseJson;
    }

}
