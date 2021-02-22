package com.example.metro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainAdabtor extends BaseExpandableListAdapter {
    static String SelectedStation;
    ArrayList<String> lines;
    HashMap<String, List<MetroStations>> Lines_stations;
    ArrayList<TextView> clickedStations = new ArrayList<>();
    MainActivity mainActivityDelegate;


    public MainAdabtor(ArrayList<String> lines, HashMap<String, List<MetroStations>> lines_stations, String value) {
        this.lines = lines;
        Lines_stations = lines_stations;
        value = SelectedStation;
    }

    @Override
    public int getGroupCount() {
        return lines.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Lines_stations.get(lines.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lines.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Lines_stations.get(lines.get(groupPosition)).get(childPosition).name;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        String sGropup = String.valueOf(getGroup(groupPosition));
        textView.setText(sGropup);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.BLUE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        String sChild = String.valueOf(getChild(groupPosition, childPosition));
        textView.setText(sChild);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < clickedStations.size(); i++) {
                    TextView textView1 = clickedStations.get(i);
                    textView1.setBackgroundColor(Color.WHITE);
                    textView1.setTextColor(Color.GRAY
                    );
                }

                textView.setBackgroundColor(Color.GRAY);
                textView.setTextColor(Color.WHITE);
                clickedStations.add(textView);
                mainActivityDelegate.setTExt(sChild);



            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
