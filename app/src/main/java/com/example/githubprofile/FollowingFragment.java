package com.example.githubprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FollowingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_SECTION_USERNAME = "username";
    private ArrayList<Profile> Profiles = new ArrayList<>();
    private static final String TAG = "";
    String username;
    // TODO: Rename and change types of parameters

    private RecyclerView rvFrgFollowingProfile;
    public FollowingFragment() {
        // Required empty public constructor
    }

    public static FollowingFragment newInstance(String username) {

        FollowingFragment fragment = new FollowingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
       }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = this.getArguments().getString(ARG_SECTION_USERNAME);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFrgFollowingProfile = view.findViewById(R.id.rv_frg_following_profile);
        rvFrgFollowingProfile.setHasFixedSize(true);

        getListProfiles(username);
        Log.d(TAG, "onViewCreated: onview fragment"+username);
    }

    private void getListProfiles(String username){
        Log.d(TAG, "getListProfiles: fragment "+username);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+username+"/following";

        client.addHeader("User-Agent","request");
        client.addHeader("Authorization", "token 043342da33813c9600cf2e6e5efd743f131a8ec9");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);

                Log.d(TAG, result);
                try {
                    Log.d(TAG, "onSuccess: fragmen api berhasil");
                    JSONArray items = new JSONArray(result);

                    for (int i = 0; i < items.length(); i++ ) {
                        JSONObject item = items.getJSONObject(i);
                        Profile profile = new Profile();
                        profile.setUsername(item.getString("login"));
                        profile.setImgavatar(item.getString("avatar_url"));
                        Profiles.add(profile);
                    }
                    showFrgFollowingRecylerList();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String respBody = new String(responseBody);
                Log.d(TAG, "onFailure: fragmen gagal api "+respBody);
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
                Toast.makeText(getActivity(), errorMessage,Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void showFrgFollowingRecylerList() {
        Log.d(TAG, "showRecylerList: list fragmen following data");
        rvFrgFollowingProfile.setLayoutManager(new LinearLayoutManager(getActivity()));
        FragmentRecyclerAdapter fragmentRecyclerAdapter = new FragmentRecyclerAdapter(Profiles);
        fragmentRecyclerAdapter.notifyDataSetChanged();
        rvFrgFollowingProfile.setAdapter(fragmentRecyclerAdapter);
    }


}