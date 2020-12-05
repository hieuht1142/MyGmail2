package com.example.mygmail2;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class EmailAdapter extends BaseAdapter {

    Context context;
    List<MyModel> items;
    ItemClickListener listener;

    public EmailAdapter(Context context, List<MyModel> items, ItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textAvatar = convertView.findViewById(R.id.txt_avatar);
            viewHolder.textUsername = convertView.findViewById(R.id.txt_username);
            viewHolder.textTitle = convertView.findViewById(R.id.txt_title);
            viewHolder.textMessage = convertView.findViewById(R.id.txt_message);
            viewHolder.textTime = convertView.findViewById(R.id.txt_time);
            viewHolder.imageStar = convertView.findViewById(R.id.img_star);

            convertView.setTag(viewHolder);

            int[] androidColors;
            androidColors = convertView.getResources().getIntArray(R.array.androidcolors);

            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            Drawable background = viewHolder.textAvatar.getBackground();
            if (background instanceof ShapeDrawable) {
                ((ShapeDrawable)background).getPaint().setColor(randomAndroidColor);
            } else if (background instanceof GradientDrawable) {
                ((GradientDrawable)background).setColor(randomAndroidColor);
            } else if (background instanceof ColorDrawable) {
                ((ColorDrawable)background).setColor(randomAndroidColor);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyModel item = items.get(position);
        viewHolder.textAvatar.setText(item.getTextAvatar());
        viewHolder.textUsername.setText(item.getUsername());
        viewHolder.textTitle.setText(item.getTitle());
        viewHolder.textMessage.setText(item.getMessage());
        viewHolder.textTime.setText(item.getTime());
        viewHolder.imageStar.setImageResource(item.getImageStar());



        viewHolder.imageStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.processImageStarClick(position);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView textAvatar;
        TextView textUsername;
        TextView textTitle;
        TextView textMessage;
        TextView textTime;
        ImageView imageStar;

    }

    public interface ItemClickListener {
        void processImageStarClick(int position);
    }
}
