package com.siae.siaecajamarca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class ExpLVAdapter extends BaseExpandableListAdapter {

private ArrayList<String> listCategoria;
private Map<String,ArrayList<String>> mapchild;
private Context context;

    public ExpLVAdapter(ArrayList<String> listCategoria, Map<String, ArrayList<String>> mapchild, Context context) {
        this.listCategoria = listCategoria;
        this.mapchild = mapchild;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return listCategoria.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mapchild.get(listCategoria.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listCategoria.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mapchild.get(listCategoria.get(i)).get(i1) ;

    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            String titulocategoria= (String) getGroup(i);
            view = LayoutInflater.from(context).inflate(R.layout.elv_group, null);
        TextView tvgroup= (TextView) view.findViewById(R.id.tv_group);
        tvgroup.setText(titulocategoria);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            String item= (String) getChild(i, i1);
            view= LayoutInflater.from(context).inflate(R.layout.elv_child, null);
            TextView tvchild=view.findViewById(R.id.tv_child);
             tvchild.setText(item);


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
