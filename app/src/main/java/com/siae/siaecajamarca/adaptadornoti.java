package com.siae.siaecajamarca;

import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadornoti extends BaseAdapter {
private Context context;
private ArrayList<enotifica> listait;

    public adaptadornoti(Context context, ArrayList<enotifica> listait) {
        this.context = context;
        this.listait = listait;
    }

    @Override
    public int getCount() {
        return listait.size();
    }

    @Override
    public Object getItem(int i) {
        return listait.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =LayoutInflater.from(context).inflate(R.layout.lnotificaitem, null);
        enotifica item= (enotifica) getItem(i);

        TextView titu= view.findViewById(R.id.ltitu);
        TextView desc= view.findViewById(R.id.ldesc);
        titu.setText(item.getTitulo());
        desc.setText(item.getDescripcion());

        return view;
    }
}
