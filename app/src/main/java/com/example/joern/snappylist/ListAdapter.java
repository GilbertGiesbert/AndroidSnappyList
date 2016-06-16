package com.example.joern.snappylist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joern on 16.06.2016.
 */
public class ListAdapter extends ArrayAdapter<ListItem> {

    private Context context;
    private int itemLayoutId;
    private List<ListItem> itemList;


    public ListAdapter(Context context, int itemLayoutId, List<ListItem> itemList) {
        super(context, itemLayoutId, itemList);

        this.context = context;
        this.itemLayoutId = itemLayoutId;
        this.itemList = itemList;
    }

    public class ViewHolder{
        public TextView textView;
        public ImageView imageView;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public ListItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(itemLayoutId, null);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_text);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        final ListItem item = itemList.get(position);
        if (item != null) {

            String imageViewUrl = (String) viewHolder.imageView.getTag();
            boolean contentChanged = imageViewUrl == null || ( ! imageViewUrl.equals(item.getImageUrl()));
            if( contentChanged ){

                viewHolder.textView.setText(item.getText());

                // reset image instantly
                Drawable placeholder = ResourcesCompat.getDrawable(context.getResources(), R.mipmap.ic_launcher, null);
                viewHolder.imageView.setImageDrawable(placeholder);

                // set correct image async'ly
                viewHolder.imageView.setTag(item.getImageUrl());
                new AsyncImageDownloadTask(viewHolder.imageView).execute(item.getImageUrl());

            }
        }

        return convertView;
    }
}
