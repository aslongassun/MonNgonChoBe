package com.vmcop.simplefour.monanngon.util;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vmcop.simplefour.monanngon.model.BeanPost;
import com.vmcop.simplefour.monanngon.R;

import java.util.ArrayList;
import java.util.List;

public final class ImageAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private final Typeface typeface_grid_monan;
    private final Context myContext;

    public ImageAdapter(Context context, ArrayList<BeanPost> inBeanPostArrayList, String imageFolder) {
        myContext = context;
        mInflater = LayoutInflater.from(context);
        typeface_grid_monan = Typeface.createFromAsset(mInflater.getContext().getAssets(), Util.CONS_FONT_GRID_MONAN);
        for(BeanPost item : inBeanPostArrayList){
        	mItems.add(new Item(Util.getTenMon(item.getTitle()) ,Util.getImageUrl(imageFolder, item.getImage_name())));
        }
    }

    public int getCount() {
        return mItems.size();
    }

    public Item getItem(int i) {
        return mItems.get(i);
    }

    public long getItemId(int i) {
        return i;
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

        Glide.with(myContext)
                .load(Uri.parse(item.imageUrl))
                .into(picture);

        return myView;
    }

    private static class Item {
        public final String name;
        public final String imageUrl;

        Item(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;

        }
    }
}