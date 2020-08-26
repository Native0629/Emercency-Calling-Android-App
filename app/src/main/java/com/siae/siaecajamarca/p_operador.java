package com.siae.siaecajamarca;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class p_operador extends Fragment {
    BarChart barChart;
    BarChart barChart2;
    BarChart barChart3;
    BarChart barChart4;

    String[] estado;
    ArrayList<BarEntry> barEntries00 = new ArrayList<>();
    ArrayList<BarEntry> barEntries10 = new ArrayList<>();
    ArrayList<BarEntry> barEntries20 = new ArrayList<>();
    ArrayList<BarEntry> barEntries30 = new ArrayList<>();
    private View rootview;

    public p_operador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_p_operador, container, false);
        barChart = rootview.findViewById(R.id.comp_linea_x_dia);
        barChart2 = rootview.findViewById(R.id.comp_linea_x_semana);
        barChart3 = rootview.findViewById(R.id.comp_linea_x_mes);
        barChart4 = rootview.findViewById(R.id.comp_cat_x_grupo_dia);

        final globales d = new globales();

        Map<String, String> parametros0 = new HashMap<String, String>();
        parametros0.put("method", "comp_linea_x_dia");
        Map<String, String> parametros1 = new HashMap<String, String>();
        parametros1.put("method", "comp_linea_x_sem");
        Map<String, String> parametros2 = new HashMap<String, String>();
        parametros2.put("method", "comp_linea_x_mes");

        Map<String, String> parametros3 = new HashMap<String, String>();
        parametros3.put("method", "comp_cat_x_grupo_dia");

        JSONObject jsonObj0 = new JSONObject(parametros0);
        JSONObject jsonObj1 = new JSONObject(parametros1);
        JSONObject jsonObj2 = new JSONObject(parametros2);
        JSONObject jsonObj3 = new JSONObject(parametros3);


        ejecutarServicio(d.url + "findById", jsonObj0);
        ejecutarServicio(d.url + "findById", jsonObj1);
        ejecutarServicio(d.url + "findById", jsonObj2);
        ejecutarServicio(d.url + "findById", jsonObj3);

        return rootview;
    }

    public void pinta(String f1, String f2, Map<String, Integer> parametros) {


        BarDataSet barDataSet1 = new BarDataSet(bar_Entries1(parametros.get("p1"), parametros.get("p2")), "Pendientes");
        barDataSet1.setColor(Color.argb(255, 100, 149, 237));
        BarDataSet barDataSet2 = new BarDataSet(bar_Entries2(parametros.get("t1"), parametros.get("t2")), "En proceso");
        barDataSet2.setColor(Color.argb(255, 51, 51, 51));
        BarDataSet barDataSet3 = new BarDataSet(bar_Entries3(parametros.get("c1"), parametros.get("c2")), "Concluidos");
        barDataSet3.setColor(Color.argb(255, 51, 255, 102));
        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3);
        barChart.setData(data);

        String[] estado = new String[]{f1, f2};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(estado));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        barChart.setTouchEnabled(true);

        float barspace = 0.05f;
        float groupspace = 0.25f;
        data.setBarWidth(0.2f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupspace, barspace) * 2);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0, groupspace, barspace);
        barChart.invalidate();


    }

    public void pinta2(String f1, String f2, Map<String, Integer> parametros) {


        BarDataSet barDataSet1 = new BarDataSet(bar_Entries1(parametros.get("p1"), parametros.get("p2")), "Pendientes");
        barDataSet1.setColor(Color.argb(255, 100, 149, 237));
        BarDataSet barDataSet2 = new BarDataSet(bar_Entries2(parametros.get("t1"), parametros.get("t2")), "En proceso");
        barDataSet2.setColor(Color.argb(255, 51, 51, 51));
        BarDataSet barDataSet3 = new BarDataSet(bar_Entries3(parametros.get("c1"), parametros.get("c2")), "Concluidos");
        barDataSet3.setColor(Color.argb(255, 51, 255, 102));


        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3);
        barChart2.setData(data);

        String[] estado = new String[]{f1, f2};
        XAxis xAxis = barChart2.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(estado));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart2.setDragEnabled(true);
        barChart2.setVisibleXRangeMaximum(3);
        barChart2.setTouchEnabled(true);

        float barspace = 0.05f;
        float groupspace = 0.25f;
        data.setBarWidth(0.2f);
        barChart2.getXAxis().setAxisMinimum(0);
        barChart2.getXAxis().setAxisMaximum(0 + barChart2.getBarData().getGroupWidth(groupspace, barspace) * 2);
        barChart2.getAxisLeft().setAxisMinimum(0);
        barChart2.groupBars(0, groupspace, barspace);
        barChart2.invalidate();

    }

    public void pinta3(String f1, String f2, Map<String, Integer> parametros) {

        BarDataSet barDataSet1 = new BarDataSet(bar_Entries1(parametros.get("p1"), parametros.get("p2")), "Pendientes");
        barDataSet1.setColor(Color.argb(255, 100, 149, 237));
        BarDataSet barDataSet2 = new BarDataSet(bar_Entries2(parametros.get("t1"), parametros.get("t2")), "En proceso");
        barDataSet2.setColor(Color.argb(255, 51, 51, 51));
        BarDataSet barDataSet3 = new BarDataSet(bar_Entries3(parametros.get("c1"), parametros.get("c2")), "Concluidos");
        barDataSet3.setColor(Color.argb(255, 51, 255, 102));

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3);
        barChart3.setData(data);

        String[] estado = new String[]{f1, f2};
        XAxis xAxis = barChart3.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(estado));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart3.setDragEnabled(true);
        barChart3.setVisibleXRangeMaximum(3);
        barChart3.setTouchEnabled(true);

        float barspace = 0.05f;
        float groupspace = 0.25f;
        data.setBarWidth(0.2f);
        barChart3.getXAxis().setAxisMinimum(0);
        barChart3.getXAxis().setAxisMaximum(0 + barChart3.getBarData().getGroupWidth(groupspace, barspace) * 2);
        barChart3.getAxisLeft().setAxisMinimum(0);
        barChart3.groupBars(0, groupspace, barspace);
        barChart3.invalidate();

    }
    public void pinta4(String f1, String f2,String f3,String f4, String f5, Map<String, Integer> parametros) {

        BarDataSet barDataSet1 = new BarDataSet(bar_Entries4(parametros.get("p1"), parametros.get("p2"), parametros.get("p3"), parametros.get("p4"), parametros.get("p5")), "Día hábil");
        barDataSet1.setColor(Color.argb(255, 100, 149, 237));
        BarDataSet barDataSet2 = new BarDataSet(bar_Entries5(parametros.get("t1"), parametros.get("t2"), parametros.get("t3"), parametros.get("t4"), parametros.get("t5")), "Sábado");
        barDataSet2.setColor(Color.argb(255, 51, 51, 51));
        BarDataSet barDataSet3 = new BarDataSet(bar_Entries6(parametros.get("c1"), parametros.get("c2"), parametros.get("c3"), parametros.get("c4"), parametros.get("c5")), "Domingo");
        barDataSet3.setColor(Color.argb(255, 51, 255, 102));

        BarData data = new BarData(barDataSet1, barDataSet2, barDataSet3);
        barChart4.setData(data);

        String[] estado = new String[]{f1, f2,f3,f4,f5};
        XAxis xAxis = barChart4.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(estado));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart4.setDragEnabled(true);
        barChart4.setVisibleXRangeMaximum(3);
        barChart4.setTouchEnabled(true);


        float barspace = 0.05f;
        float groupspace = 0.25f;
        data.setBarWidth(0.2f);
        barChart4.getXAxis().setAxisMinimum(0);
        barChart4.getXAxis().setAxisMaximum(0 + barChart4.getBarData().getGroupWidth(groupspace, barspace) * 5);
        barChart4.getAxisLeft().setAxisMinimum(0);
        barChart4.groupBars(0, groupspace, barspace);
        barChart4.invalidate();


    }

    private ArrayList<BarEntry> bar_Entries1(int p1, int p2) {
        ArrayList<BarEntry> barEntries0 = new ArrayList<>();
        barEntries0.add(new BarEntry(1, p1));
        barEntries0.add(new BarEntry(2, p2));
//        barEntries0.add(new BarEntry(3, 400));
        return barEntries0;
    }

    private ArrayList<BarEntry> bar_Entries2(int t1, int t2) {
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        barEntries1.add(new BarEntry(1, t1));
        barEntries1.add(new BarEntry(2, t2));
        //    barEntries1.add(new BarEntry(3, 400));
        return barEntries1;
    }

    private ArrayList<BarEntry> bar_Entries3(int c1, int c2) {
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
        barEntries2.add(new BarEntry(1, c1));
        barEntries2.add(new BarEntry(2, c2));
        //  barEntries2.add(new BarEntry(3, 400));
        return barEntries2;
    }
    private ArrayList<BarEntry> bar_Entries4(int p1, int p2,int p3,int p4,int p5) {
        ArrayList<BarEntry> barEntries4 = new ArrayList<>();
        barEntries4.add(new BarEntry(1, p1));
        barEntries4.add(new BarEntry(2, p2));
        barEntries4.add(new BarEntry(3, p3));
        barEntries4.add(new BarEntry(4, p4));
        barEntries4.add(new BarEntry(5, p5));
        return barEntries4;
    }
    private ArrayList<BarEntry> bar_Entries5(int t1, int t2,int t3,int t4,int t5) {
        ArrayList<BarEntry> barEntries4 = new ArrayList<>();
        barEntries4.add(new BarEntry(1, t1));
        barEntries4.add(new BarEntry(2, t2));
        barEntries4.add(new BarEntry(3, t3));
        barEntries4.add(new BarEntry(4, t4));
        barEntries4.add(new BarEntry(5, t5));
        return barEntries4;
    }
    private ArrayList<BarEntry> bar_Entries6(int c1, int c2,int c3,int c4,int c5) {
        ArrayList<BarEntry> barEntries4 = new ArrayList<>();
        barEntries4.add(new BarEntry(1, c1));
        barEntries4.add(new BarEntry(2, c2));
        barEntries4.add(new BarEntry(3, c3));
        barEntries4.add(new BarEntry(4, c4));
        barEntries4.add(new BarEntry(5, c5));
        return barEntries4;
    }
//
//    private ArrayList<BarEntry> bar_Entries10() {
//        ArrayList<BarEntry> barEntries0 = new ArrayList<>();
//        barEntries0.add(new BarEntry(1, 55));
//        barEntries0.add(new BarEntry(2, 30));
////        barEntries0.add(new BarEntry(3, 400));
//        return barEntries0;
//    }
//
//    private ArrayList<BarEntry> bar_Entries20() {
//        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
//        barEntries1.add(new BarEntry(1, 50));
//        barEntries1.add(new BarEntry(2, 15));
//        //    barEntries1.add(new BarEntry(3, 400));
//        return barEntries1;
//    }
//
//    private ArrayList<BarEntry> bar_Entries30() {
//        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
//        barEntries2.add(new BarEntry(1, 50));
//        barEntries2.add(new BarEntry(2, 18));
//        //  barEntries2.add(new BarEntry(3, 400));
//        return barEntries2;
//    }
//
//    private ArrayList<BarEntry> bar_Entries100() {
//        ArrayList<BarEntry> barEntries0 = new ArrayList<>();
//        barEntries0.add(new BarEntry(1, 185));
//        barEntries0.add(new BarEntry(2, 85));
////        barEntries0.add(new BarEntry(3, 400));
//        return barEntries0;
//    }
//
//    private ArrayList<BarEntry> bar_Entries200() {
//        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
//        barEntries1.add(new BarEntry(1, 211));
//        barEntries1.add(new BarEntry(2, 65));
//        //    barEntries1.add(new BarEntry(3, 400));
//        return barEntries1;
//    }
//
//    private ArrayList<BarEntry> bar_Entries300() {
//        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
//        barEntries2.add(new BarEntry(1, 192));
//        barEntries2.add(new BarEntry(2, 68));
//        //  barEntries2.add(new BarEntry(3, 400));
//        return barEntries2;
//    }


    public void ejecutarServicio(final String url, final JSONObject param) {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        try {
//            sessionManager = new SessionManager(getActivity().getApplicationContext());
            //          HashMap<String, String> user = sessionManager.getUserDetail();

            //        Map<String, String> parametros = new HashMap<String, String>();
            //      parametros.put("nro_doc_ide", user.get(sessionManager.DNI));
            //parametros.put("nro_doc_ide","" );

            //JSONObject jsonObj = new JSONObject(parametros);
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, param, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String f1, f2,f3,f4,f5;

                            try {
                                JSONObject r = response;

                                if (param.getString("method").equals("comp_linea_x_dia") ) {
                                    f1 = r.getJSONObject("xaxis").getJSONArray("categories").getString(0).substring(0, 5);
                                    f2 = r.getJSONObject("xaxis").getJSONArray("categories").getString(1).substring(0, 5);
                                    int p1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(0));
                                    int p2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(1));
                                    int t1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(0));
                                    int t2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(1));
                                    int c1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(0));
                                    int c2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(1));

                                    Map<String, Integer> parametros = new HashMap<String, Integer>();

                                    parametros.put("p1", p1);
                                    parametros.put("p2", p2);
                                    parametros.put("t1", t1);
                                    parametros.put("t2", t2);
                                    parametros.put("c1", c1);
                                    parametros.put("c2", c2);
                                    pinta(f1, f2, parametros);
                                } else if(param.getString("method").equals("comp_linea_x_sem")){
                                    f1 = r.getJSONObject("xaxis").getJSONArray("categories").getString(0).substring(0, 3);
                                    f2 = r.getJSONObject("xaxis").getJSONArray("categories").getString(1).substring(0, 3);
                                    int p1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(0));
                                    int p2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(1));
                                    int t1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(0));
                                    int t2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(1));
                                    int c1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(0));
                                    int c2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(1));

                                    Map<String, Integer> parametros = new HashMap<String, Integer>();

                                    parametros.put("p1", p1);
                                    parametros.put("p2", p2);
                                    parametros.put("t1", t1);
                                    parametros.put("t2", t2);
                                    parametros.put("c1", c1);
                                    parametros.put("c2", c2);
                                    pinta2(f1, f2, parametros);
                                }else if(param.getString("method").equals("comp_linea_x_mes")){
                                    f1 = r.getJSONObject("xaxis").getJSONArray("categories").getString(0).substring(0, 5);
                                    f2 = r.getJSONObject("xaxis").getJSONArray("categories").getString(1).substring(0, 5);
                                    int p1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(0));
                                    int p2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(1));
                                    int t1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(0));
                                    int t2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(1));
                                    int c1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(0));
                                    int c2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(1));

                                    Map<String, Integer> parametros = new HashMap<String, Integer>();

                                    parametros.put("p1", p1);
                                    parametros.put("p2", p2);
                                    parametros.put("t1", t1);
                                    parametros.put("t2", t2);
                                    parametros.put("c1", c1);
                                    parametros.put("c2", c2);
                                    pinta3(f1, f2, parametros);
                                }else if(param.getString("method").equals("comp_cat_x_grupo_dia")){
                                    f1 = r.getJSONObject("xaxis").getJSONArray("categories").getString(0);
                                    f2 = r.getJSONObject("xaxis").getJSONArray("categories").getString(1);
                                    f3 = r.getJSONObject("xaxis").getJSONArray("categories").getString(2);
                                    f4 = r.getJSONObject("xaxis").getJSONArray("categories").getString(3);
                                    f5 = r.getJSONObject("xaxis").getJSONArray("categories").getString(4);

                                    int p1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(0));
                                    int p2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(1));
                                    int p3 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(2));
                                    int p4 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(3));
                                    int p5 = Integer.parseInt(r.getJSONArray("series").getJSONObject(0).getJSONArray("data").getString(4));

                                    int t1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(0));
                                    int t2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(1));
                                    int t3 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(2));
                                    int t4 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(3));
                                    int t5 = Integer.parseInt(r.getJSONArray("series").getJSONObject(1).getJSONArray("data").getString(4));

                                    int c1 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(0));
                                    int c2 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(1));
                                    int c3 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(2));
                                    int c4 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(3));
                                    int c5 = Integer.parseInt(r.getJSONArray("series").getJSONObject(2).getJSONArray("data").getString(4));

                                    Map<String, Integer> parametros = new HashMap<String, Integer>();

                                    parametros.put("p1", p1);
                                    parametros.put("p2", p2);
                                    parametros.put("p3", p3);
                                    parametros.put("p4", p4);
                                    parametros.put("p5", p5);

                                    parametros.put("t1", t1);
                                    parametros.put("t2", t2);
                                    parametros.put("t3", t3);
                                    parametros.put("t4", t4);
                                    parametros.put("t5", t5);

                                    parametros.put("c1", c1);
                                    parametros.put("c2", c2);
                                    parametros.put("c3", c3);
                                    parametros.put("c4", c4);
                                    parametros.put("c5", c5);

                                    pinta4(f1, f2,f3,f4,f5, parametros);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getContext(), "error en la conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
