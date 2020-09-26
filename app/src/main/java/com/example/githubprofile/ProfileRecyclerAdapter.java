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

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ProfileViewHolder> {
    private ArrayList<Profile> Profiles;
    private OnItemClickCallback onItemClickCallback;

    public ProfileRecyclerAdapter(ArrayList<Profile> Profiles) {
        this.Profiles = Profiles;
    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile, viewGroup, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileViewHolder holder, int position) {

        Profile profile = Profiles.get(position);
        Glide.with(holder.itemView.getContext())
                .load(profile.getImgavatar())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgAvatar);

        holder.tvUsername.setText(profile.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(Profiles.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return Profiles.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView  tvUsername;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvUsername = itemView.findViewById(R.id.tv_username);

        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(Profile data);
    }
}
