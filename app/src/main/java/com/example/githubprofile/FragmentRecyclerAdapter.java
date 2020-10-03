package com.example.githubprofile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FragmentRecyclerAdapter extends RecyclerView.Adapter<FragmentRecyclerAdapter.FragmenViewHolder> {
    private ArrayList<Profile> Profiles;

    public FragmentRecyclerAdapter(ArrayList<Profile> Profiles) {
        this.Profiles = Profiles;
    }

    @NonNull
    @Override
    public FragmenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile, viewGroup, false);
        return new FragmenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmenViewHolder holder, int position) {
        Profile profile = Profiles.get(position);
        Glide.with(holder.itemView.getContext())
                .load(profile.getImgavatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgAvatar);

        holder.tvUsername.setText(profile.getUsername());
    }

    @Override
    public int getItemCount() {
        return Profiles.size();
    }

    public class FragmenViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUsername;
        public FragmenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvUsername = itemView.findViewById(R.id.tv_username);
        }
    }
}
