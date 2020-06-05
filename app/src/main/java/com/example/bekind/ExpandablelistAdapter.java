package com.example.bekind;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v7.widget.DrawableUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandablelistAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> listDataHeader;
    HashMap<String,List<String>> listHashMap;


    public ExpandablelistAdapter(Context context, List<String> listDataHeader, HashMap<String,List<String>> listHashMap){
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;

        }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return listHashMap.get(listDataHeader.get(groupPosition)).size();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group,null);
        }

        TextView ibListheader = (TextView)convertView.findViewById(R.id.lbListHeader);
        ibListheader.setText(headerTitle);
        ImageView listimage = (ImageView)convertView.findViewById(R.id.ibimageView);

        if(headerTitle.equals("MATERIALS")){
            listimage.setImageResource(R.drawable.materials);
        }
        if(headerTitle.equals("SERVICES")){
            listimage.setImageResource(R.drawable.services);
        }
        if(headerTitle.equals("MONEY")){
            listimage.setImageResource(R.drawable.money);
        }
        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = (String)getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
            }
            TextView txtListChild = (TextView)convertView.findViewById(R.id.lbListItem);
            txtListChild.setText(childText);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.iBimageView);
            //MATERIALS
            if(childText.equals("Donate item")){
//                imageView.setImageResource(R.drawable.mobilemoney1);
            }

            //SERVICES
            if(childText.equals("Volunteer")){
    //                imageView.setImageResource(R.drawable.mobilemoney1);
            }

//            //MONEY
            if(childText.equals("MTN Mobile Money")){
                imageView.setImageResource(R.drawable.mobilemoney1);
            }

            if(childText.equals("Mastercard")){
                imageView.setImageResource(R.drawable.mastercard);
            }
            if(childText.equals("PayPal")){
                imageView.setImageResource(R.drawable.paypal);
            }
            return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}
