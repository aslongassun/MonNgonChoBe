package com.vmcop.simplefour.monanngon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public final class ImageAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public ImageAdapter(Context context, ArrayList<BeanPost> inBeanPostArrayList) {
        mInflater = LayoutInflater.from(context);
        
        for(BeanPost item : inBeanPostArrayList){
        	mItems.add(new Item(item.getTitle(),Util.getImageId(context, item.getImage_name())));
        }
        /*
        mItems.add(new Item("Red",       R.drawable.sample_0));
        mItems.add(new Item("Magenta",   R.drawable.sample_1));
        mItems.add(new Item("Dark Gray", R.drawable.sample_2));
        mItems.add(new Item("Gray",      R.drawable.sample_3));
        mItems.add(new Item("Green",     R.drawable.sample_4));
        mItems.add(new Item("Cyan1",      R.drawable.sample_5));
        mItems.add(new Item("Cyan2",      R.drawable.sample_6));
        mItems.add(new Item("Cyan3",      R.drawable.sample_7));
        mItems.add(new Item("Cyan4",      R.drawable.sample_6));
        mItems.add(new Item("Cyan5",      R.drawable.sample_5));
        */
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.image_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}