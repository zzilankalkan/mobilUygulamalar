package com.example.fenerbahce1907.ui.media;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.app.Dialog;
import android.widget.ImageButton;




import com.bumptech.glide.Glide;
import com.example.fenerbahce1907.R;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private final List<MediaItem> mediaList;

    public MediaAdapter(List<MediaItem> mediaList) {
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        String imageUrl = mediaList.get(position).getImageUrl();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.loading) // Yüklenirken
                .error(R.drawable.error) // Yüklenemezse
                .into(holder.imageMedia);

        // ➕ Resme tıklanınca büyük göster
        holder.imageMedia.setOnClickListener(v -> {
            Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_fullscreen_image);

            ImageView fullscreenImage = dialog.findViewById(R.id.fullscreenImage);
            ImageButton closeButton = dialog.findViewById(R.id.buttonClose); // 🔺

            Glide.with(v.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(fullscreenImage);

            // 🔺 X butonuna tıklanınca kapansın
            closeButton.setOnClickListener(view1 -> dialog.dismiss());

            dialog.show();
        });



    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMedia;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMedia = itemView.findViewById(R.id.imageMedia);
        }
    }
}
