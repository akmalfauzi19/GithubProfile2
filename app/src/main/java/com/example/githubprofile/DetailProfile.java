package com.example.githubprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailProfile extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_PROFILE = "extra_profile";

    Button btnDtlFollow, btnDtlFollowing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);

        TextView tvDtlUsername = findViewById(R.id.tv_dtl_username);
        TextView tvDtlName = findViewById(R.id.tv_dtl_name);
        TextView tvDtlLocation = findViewById(R.id.tv_dtl_location);
        TextView tvDtlCompany = findViewById(R.id.tv_dtl_company);
        TextView tvDtlRepository = findViewById(R.id.tv_dtl_repository);
        TextView tvDtlFollowers = findViewById(R.id.tv_dtl_followers);
        TextView tvDtlFollowing = findViewById(R.id.tv_dtl_following);
        CircleImageView imgDtlAvatar = findViewById(R.id.img_dtl_avatar);
         btnDtlFollow = findViewById(R.id.btn_dtl_follow);
         btnDtlFollowing = findViewById(R.id.btn_dtl_following);

        Profile profile = getIntent().getParcelableExtra(EXTRA_PROFILE);
        tvDtlUsername.setText(profile.getName());
        tvDtlName.setText(profile.getName());
        tvDtlLocation.setText(profile.getLocation());
        tvDtlCompany.setText(profile.getCompany());
        tvDtlRepository.setText(profile.getRepository());
        tvDtlFollowers.setText(profile.getFollowers());
        tvDtlFollowing.setText(profile.getFollowing());
        imgDtlAvatar.setImageResource(profile.getAvatar());

        btnDtlFollow.setOnClickListener(this);
        btnDtlFollowing.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dtl_follow) {
            visible();
        } else if (v.getId() == R.id.btn_dtl_following) {
            gone();
        }
    }
    private void visible() {
        btnDtlFollowing.setVisibility(View.VISIBLE);
        btnDtlFollow.setVisibility(View.GONE);
    }
    private void gone() {
        btnDtlFollowing.setVisibility(View.GONE);
        btnDtlFollow.setVisibility(View.VISIBLE);
    }
}