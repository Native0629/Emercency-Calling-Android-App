package com.siae.siaecajamarca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class adaptador3 extends BaseAdapter {

    private static LayoutInflater inflater =null;

    Context context;
    String [][] datos;

    public adaptador3(Context context, String[][] datos) {
        this.context = context;
        this.datos = datos;

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View  vista = inflater.inflate(R.layout.item_incidencias, null);
        TextView nom =(TextView) vista.findViewById(R.id.tipo);
        TextView fecha =(TextView) vista.findViewById(R.id.fecha);
        LinearLayout reg= vista.findViewById(R.id.reg);

        if(i%2==0){
            reg.setBackgroundResource(R.drawable.mascara_reporte1);

        }

        String est=datos[i][6];
        if(datos[i][3].equals("null")){
            nom.setText("No tipificado");
        }else{
            nom.setText(datos[i][3]);

        }
        fecha.setText(datos[i][4]);


        return vista;
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
