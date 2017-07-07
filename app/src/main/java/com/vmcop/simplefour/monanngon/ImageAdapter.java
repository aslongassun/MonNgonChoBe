package com.vmcop.simplefour.monanngon;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public final class ImageAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private final Typeface typeface_grid_monan;
    private final Context myContext;

    public ImageAdapter(Context context, ArrayList<BeanPost> inBeanPostArrayList) {
        myContext = context;
        mInflater = LayoutInflater.from(context);
        typeface_grid_monan = Typeface.createFromAsset(mInflater.getContext().getAssets(), Util.CONS_FONT_GRID_MONAN);
        for(BeanPost item : inBeanPostArrayList){
        	mItems.add(new Item(Util.getTenMon(item.getTitle()) ,Util.getImageId(context, item.getImage_name())));
        }
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
        View myView = view;
        ImageView picture;
        TextView name;

        if (myView == null) {
            myView = mInflater.inflate(R.layout.image_item, viewGroup, false);
            myView.setTag(R.id.picture, myView.findViewById(R.id.picture));
            myView.setTag(R.id.text, myView.findViewById(R.id.text));
        }

        picture = (ImageView) myView.getTag(R.id.picture);
        name = (TextView) myView.getTag(R.id.text);
        name.setTypeface(typeface_grid_monan);
        Item item = getItem(i);
        name.setText(item.name);

        //picture.setImageResource(item.drawableId);
        Glide.with(myContext)
                .load(item.drawableId)
                .into(picture);

        return myView;
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