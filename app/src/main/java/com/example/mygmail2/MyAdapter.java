package com.example.mygmail2;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {

    List<MyModel> items;
    ItemClickListener listener;

    public MyAdapter(List<MyModel> items, ItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MyModel item = items.get(position);
        holder.textAvatar.setText(item.getTextAvatar());
        holder.textUsername.setText(item.getUsername());
        holder.textTitle.setText(item.getTitle());
        holder.textMessage.setText(item.getMessage());
        holder.textTime.setText(item.getTime());
        holder.imageStar.setImageResource(item.getImageStar());

        //Generating Random Color
        int randomAndroidColor = holder.androidColors[new Random().nextInt(holder.androidColors.length)];
        Drawable background = holder.textAvatar.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(randomAndroidColor);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(randomAndroidColor);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(randomAndroidColor);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView textAvatar;
        TextView textUsername;
        TextView textTitle;
        TextView textMessage;
        TextView textTime;
        ImageView imageStar;
        int[] androidColors;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnCreateContextMenuListener(this);

            textAvatar = itemView.findViewById(R.id.txt_avatar);
            textUsername = itemView.findViewById(R.id.txt_username);
            textTitle = itemView.findViewById(R.id.txt_title);
            textMessage = itemView.findViewById(R.id.txt_message);
            textTime = itemView.findViewById(R.id.txt_time);
            imageStar = itemView.findViewById(R.id.img_star);
            androidColors = itemView.getResources().getIntArray(R.array.androidcolors);

            imageStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.processImageStarClick(getAdapterPosition());
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 101, 0, "Delete");
            menu.add(0, 101, 0, "Reply");
        }

        
    }

    public interface ItemClickListener {
        void processImageStarClick(int position);
    }
}
