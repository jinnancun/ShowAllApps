package com.example.itdev1.showallapps;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by i.t.Dev1 on 29/07/2015.
 * TODO CONTROLLER
 */
public class GridItemAdapter extends BaseAdapter {
    private Context context;
    private List<ResolveInfo> resInfo;

    public GridItemAdapter(Context c, List<ResolveInfo> res)
    {
        context = c;
        resInfo = res;
    }

    @Override
    public int getCount()
    {
        return resInfo.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(context).inflate(R.layout.application_layout, null);

        ResolveInfo res = resInfo.get(position);
        ImageView appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
        TextView appTitle = (TextView) convertView.findViewById(R.id.app_title);
        appIcon.setImageDrawable(res.loadIcon(context.getPackageManager()));
        appTitle.setText(res.loadLabel(context.getPackageManager()).toString());

        return convertView;
    }
}