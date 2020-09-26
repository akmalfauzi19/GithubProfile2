package com.example.githubprofile;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileModel {
    private static final String TAG = MainActivity.class.getSimpleName();

    private void getListProfiles1(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users?q=akmalfauzi19";
        client.addHeader("Authorization", "token f870bb864a2edeb5d3b1eaa7ba7d001c9b9dcbce");
        client.addHeader("User-Agent", "request");

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                ArrayList<Profile> listProfile = new ArrayList<>();
                String result = new String(responseBody);
                Log.d(TAG, result);
                Log.d(TAG, "onSuccess: api gan");
                try {
                    JSONObject responeObject = new JSONObject(result);
                    JSONArray items = responeObject.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++ ) {
                        JSONObject item = items.getJSONObject(i);
                        Profile profile = new Profile();
                        String api_userName = item.getString("login");
                        String api_Avatar = item.getString("avatar_url");

                        profile.setUsername(api_userName);
                        profile.setImgavatar(api_Avatar);
                        listProfile.add(profile);
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: gagal api");
            }
        });
    }
}
