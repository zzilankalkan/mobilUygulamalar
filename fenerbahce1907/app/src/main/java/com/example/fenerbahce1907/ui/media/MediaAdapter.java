package com.example.fenerbahce1907.ui.media;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageMedia);

        // ➕ Resme tıklanınca büyük göster
        holder.imageMedia.setOnClickListener(v -> {
            Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(R.layout.dialog_fullscreen_image);

            ImageView fullscreenImage = dialog.findViewById(R.id.fullscreenImage);
            ImageButton closeButton = dialog.findViewById(R.id.buttonClose);

            Glide.with(v.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(fullscreenImage);

            closeButton.setOnClickListener(view1 -> dialog.dismiss());
            dialog.show();
        });

        // 🔒 Kilit ekranı yap butonu
        holder.buttonSetLockScreen.setOnClickListener(v -> {
            Glide.with(v.getContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                WallpaperManager manager = WallpaperManager.getInstance(v.getContext());
                                try {
                                    manager.setBitmap(resource, null, true, WallpaperManager.FLAG_LOCK);
                                    Toast.makeText(v.getContext(), "Kilit ekranı başarıyla ayarlandı", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(v.getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(v.getContext(), "Android 7.0 ve üzeri desteklenir", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMedia;
        ImageButton buttonSetLockScreen;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMedia = itemView.findViewById(R.id.imageMedia);
            buttonSetLockScreen = itemView.findViewById(R.id.buttonSetLockScreen);
        }
    }
}
