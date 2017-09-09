package com.prizmcore.edgetasking;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class ABArrayAdapter extends ArrayAdapter<Adapter> {
        private ArrayList<Adapter> items;
        private int rsrc;
        
        public ABArrayAdapter(Context ctx, int rsrcId, ArrayList<Adapter> data) {
            super(ctx, rsrcId, data);
            this.items = data;
            this.rsrc = rsrcId;
        }

        static class ViewHolder {
            ImageView appimage;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder;
            final Adapter e = items.get(position);
            if (v == null) {
                holder = new ViewHolder();
                LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(rsrc, null);
                assert v != null;
                holder.appimage = (ImageView)v.findViewById(R.id.imageView);
                holder.appimage.setImageDrawable(e.getappimage());
                holder.appimage.setFocusable(false);
                v.setTag(holder);

                holder.appimage.setOnClickListener(new View.OnClickListener() {
                    String a = e.getapppackage();
                    @Override
                    public void onClick(View v) {
                        Log.d("something",a);
                        Intent LaunchIntent = getContext().getPackageManager().getLaunchIntentForPackage(e.getapppackage());
                        getContext().startActivity(LaunchIntent);

                    }
                });
            } 


            return v;
        }

}
