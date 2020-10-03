package com.example.githubprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProfileNew extends AppCompatActivity {

    private ArrayList<Profile> Profiles = new ArrayList<>();
    public static final String EXTRA_PROFILE = "extra_profile";
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView tvDtlName, tvDtlRepository, tvDtlFollowers, tvDtlFollowing;
    CircleImageView imgDtlAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_profile_new);
        Profile profile = getIntent().getParcelableExtra(EXTRA_PROFILE);
        Log.d(TAG, "pemanggilan Detail " +profile);
        DetailProfileSectionsPagerAdapter detailProfileSectionsPagerAdapter =
                new DetailProfileSectionsPagerAdapter(this, getSupportFragmentManager());
        detailProfileSectionsPagerAdapter.username = profile.getUsername();
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(detailProfileSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

         tvDtlName = findViewById(R.id.tv_dtl_name_new);
         tvDtlRepository = findViewById(R.id.tv_dtl_repository_new);
         tvDtlFollowers = findViewById(R.id.tv_dtl_followers_new);
         tvDtlFollowing = findViewById(R.id.tv_dtl_following_new);
         imgDtlAvatar = findViewById(R.id.img_dtl_avatar_new);

        getListProfiles(profile.getUsername());
        Log.d(TAG, "detail oncreate " +profile.getUsername());

        getSupportActionBar().setElevation(0);


    }

    public void getListProfiles(String username){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+username;
        client.addHeader("User-Agent","request");
        client.addHeader("Authorization", "token 043342da33813c9600cf2e6e5efd743f131a8ec9");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    Log.d(TAG, "Detail sukses");
                        JSONObject item = new JSONObject(result);
                        String APIUsername = item.getString("login");
                        String APIRepo = item.getString("public_repos");
                        String APIFollowers = item.getString("followers");
                        String APIFollowing = item.getString("following");
                        String APIimgAvatar = item.getString("avatar_url");
                        tvDtlName.setText(APIUsername);
                        tvDtlRepository.setText(APIRepo);
                        tvDtlFollowers.setText(APIFollowers);
                        tvDtlFollowing.setText(APIFollowing);
                        Glide.with(getApplicationContext()).load(APIimgAvatar).into(imgDtlAvatar);
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
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
                Toast.makeText(DetailProfileNew.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }


}