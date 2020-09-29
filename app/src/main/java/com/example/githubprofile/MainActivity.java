package com.example.githubprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity{
    private RecyclerView rvProfile;


    private ArrayList<Profile> Profiles = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProfile = findViewById(R.id.rv_profile);
        rvProfile.setHasFixedSize(true);

        progressBar= findViewById(R.id.progressBar);

        getListProfiles();

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null ) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("query",query);
                    Profiles.clear();
                    getSearchListProfiles(query);
//
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });

        }
        return true;
    }

    private void getSearchListProfiles(final String username){

        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/search/users?q="+ username;
//        client.addHeader("Authorization", "token f870bb864a2edeb5d3b1eaa7ba7d001c9b9dcbce");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    Log.d(TAG, "onSuccess: api berhasil");
                    JSONObject responeObject = new JSONObject(result);
                    JSONArray items = responeObject.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++ ) {
                        JSONObject item = items.getJSONObject(i);
                        Profile profile = new Profile();
                        profile.setUsername(item.getString("login"));
                        profile.setImgavatar(item.getString("avatar_url"));
                        Profiles.add(profile);
                    }

                showRecylerList();

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                String respBody = new String(responseBody);
                Log.d(TAG, "onFailure: gagal api "+respBody);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void getListProfiles(){
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users?q=akmalfauzi19";
//        client.addHeader("Authorization", "token f870bb864a2edeb5d3b1eaa7ba7d001c9b9dcbce");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    Log.d(TAG, "onSuccess: api berhasil");
// objek pertama adalah array.. bukan objek json
//JSONObject responeObject = new JSONObject(result);
                    JSONArray items = new JSONArray(result);

                    for (int i = 0; i < items.length(); i++ ) {
                        JSONObject item = items.getJSONObject(i);
                        Profile profile = new Profile();
// String api_userName = item.getString("login");
// String api_Avatar = item.getString("avatar_url");
                        profile.setUsername(item.getString("login"));
                        profile.setImgavatar(item.getString("avatar_url"));
// profile.setUsername(api_userName);
// profile.setImgavatar(api_Avatar);
                        Profiles.add(profile);
                    }

                    showRecylerList();

                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                String respBody = new String(responseBody);
                Log.d(TAG, "onFailure: gagal api "+respBody);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }




    private void showRecylerList() {
        Log.d(TAG, "showRecylerList: list data");
        rvProfile.setLayoutManager(new LinearLayoutManager(this));
        ProfileRecyclerAdapter profileRecyclerAdapter = new ProfileRecyclerAdapter(Profiles);
        profileRecyclerAdapter.notifyDataSetChanged();
        rvProfile.setAdapter(profileRecyclerAdapter);
        profileRecyclerAdapter.setOnItemClickCallback(new ProfileRecyclerAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Profile data) {
                showSelectedHero(data);
        }
        });
    }

    private void showSelectedHero(Profile profile) {
        Toast.makeText(this, "Kamu memilih " + profile.getUsername(), Toast.LENGTH_SHORT).show();
        Intent detailIntent = new Intent(MainActivity.this, DetailProfile.class);
//        data
        getListProfiles();

        detailIntent.putExtra(DetailProfile.EXTRA_PROFILE, profile);
        startActivity(detailIntent);
    }



 }