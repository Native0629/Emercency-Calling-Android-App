package com.siae.siaecajamarca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.siae.siaecajamarca.R.layout.fragment_mis_incidendias;


public class MisIncidendiasFragment extends Fragment {
    private View rootView;
    ListView lv1, lv2, lv3;
    TextView t1, t2, t3;
    LinearLayout lis1, lis2, lis3, lis, liss, lisss;
    private CircleImageView circleImageView;
    SessionManager sessionManager;
    TextView nombre, dni, cel;
    String e1 = "0", e2 = "0", e3 = "0";
    public static String url_imgn;
    String a, b, z;
    ExpandableListView expLV;
    ExpLVAdapterinci adapter;
    ArrayList<String> Listcategoria;

//    Map<String, ArrayList<String>> mapchild;
    //Map<String, Arrays[][]> mapchild;

    String[][] p1 = new String[2][2];


    public MisIncidendiasFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(fragment_mis_incidendias, container, false);

        lv1 = rootView.findViewById(R.id.list1);
        lv2 = rootView.findViewById(R.id.list2);
        lv3 = rootView.findViewById(R.id.list3);

        //Listcategoria= new ArrayList<>();

        //mapchild= new HashMap<>();

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        nombre = rootView.findViewById(R.id.usuario);
        dni = rootView.findViewById(R.id.dni);
        cel = rootView.findViewById(R.id.cel);
        circleImageView = rootView.findViewById(R.id.img_user);

        nombre.setText(user.get(sessionManager.NOMBRES) + " " + user.get(sessionManager.APE_PAT));
        dni.setText(user.get(sessionManager.DNI));
        cel.setText(user.get(sessionManager.CELULAR));
        url_imgn = user.get(sessionManager.IMGFB);

        lis1 = rootView.findViewById(R.id.lis1);
        lis2 = rootView.findViewById(R.id.lis2);
        lis3 = rootView.findViewById(R.id.lis3);

        lis = rootView.findViewById(R.id.lis);
        liss = rootView.findViewById(R.id.liss);
        lisss = rootView.findViewById(R.id.lisss);

        t1 = rootView.findViewById(R.id.tipo);
        t2 = rootView.findViewById(R.id.tipo2);
        t3 = rootView.findViewById(R.id.tipo3);

        final globales d = new globales();

        if (url_imgn.equals("")) {
            Glide.with(getActivity()).load(R.drawable.perfil).into(circleImageView);
        } else {
            Glide.with(getActivity()).load(url_imgn).into(circleImageView);

        }

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, 0);
        lis.setLayoutParams(lp1);
        liss.setLayoutParams(lp1);
        lisss.setLayoutParams(lp1);

        ejecutarServicio(d.url + "reporte");
        ejecutarServicio2(d.url + "reporte");
        ejecutarServicio3(d.url + "reporte");
  /*  t1.setText(e1);
    t2.setText(e2);
    t3.setText(e3);*/

        lis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ejecutarServicio(d.url + "loginn");

                LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, 0);
                lis.setLayoutParams(p1);
                liss.setLayoutParams(lp1);
                lisss.setLayoutParams(lp1);

            }
        });
        lis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ejecutarServicio2(d.url + "loginn");
                LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, 0);
                lis.setLayoutParams(lp1);
                liss.setLayoutParams(p1);
                lisss.setLayoutParams(lp1);


            }
        });
        lis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ejecutarServicio3(d.url + "loginn");
                LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, 0);
                lis.setLayoutParams(lp1);
                liss.setLayoutParams(lp1);
                lisss.setLayoutParams(p1);

            }
        });
/*
        p1[0][0]="hola";
        p1[0][1]="hola2";
        p1[1][0]="hola3";
        p1[1][1]="hola4";
*/

        //p1[0][1]="hola2";



       /* cargardatos();

                  in1= rootView.findViewById(R.id.in1);
        lpp = (LinearLayout) rootView.findViewById(R.id.lp);
        lv1 = rootView.findViewById(R.id.list1);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user=sessionManager.getUserDetail();
        nombre = rootView.findViewById(R.id.usuario);
        dni = rootView.findViewById(R.id.dni);
        cel = rootView.findViewById(R.id.cel);
        circleImageView = rootView.findViewById(R.id.img_user);

        nombre.setText(user.get(sessionManager.NOMBRES)+" "+user.get(sessionManager.APE_PAT));
        dni.setText(user.get(sessionManager.DNI));
        cel.setText(user.get(sessionManager.CELULAR));

        Glide.with(getActivity()).load(R.drawable.perfil).into(circleImageView);*/
        //        final globales d = new globales();
//        ejecutarServicio(d.url + "loginn");
            /*lpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarServicio(d.url+"loginn");
            }
        });*/


        return rootView;
    }

  /*  private void cargardatos(){

//        ArrayList<String> p1= new ArrayList<>();

        //ArrayList<String> p2= new ArrayList<>();
        //ArrayList<String> p3= new ArrayList<>();

        Listcategoria.add("Pendientes(0)");
        Listcategoria.add("En proceso(0)");
        Listcategoria.add("Atendidas (0)");

*/
  /*
        p1.add(".1");
        p2.add(".2");
        p3.add(".3");
*//*

        //mapchild.put(Listcategoria.get(0),p1);
  *//*      mapchild.put(Listcategoria.get(1),p2);
        mapchild.put(Listcategoria.get(2),p3);
*//*
  adapter = new ExpLVAdapterinci(Listcategoria,p1 , getContext());

        expLV.setAdapter(adapter);



    }*/


    private void ejecutarServicio(final String url) {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        try {
            sessionManager = new SessionManager(getActivity().getApplicationContext());
            HashMap<String, String> user = sessionManager.getUserDetail();

            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("nro_doc_ide", user.get(sessionManager.DNI));
            //parametros.put("nro_doc_ide","" );

            JSONObject jsonObj = new JSONObject(parametros);
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject r = response;
                                int no = r.getInt("count");
                                JSONArray s = r.getJSONArray("nestados");

                                a = s.getJSONObject(0).getString("valor");
                                b = s.getJSONObject(1).getString("valor");
                                z = s.getJSONObject(2).getString("valor");
                                t1.setText(a + " (0)");
                                t2.setText(b + " (0)");
                                t3.setText(z + " (0)");
                                JSONArray c = r.getJSONArray("cantidad");
                                for (int x = 0; x < c.length(); x++) {
                                    if (c.getJSONObject(x).getString("estado_atencion").equals("P")) {
                                        e1 = c.getJSONObject(x).getString("cantidad");
                                        t1.setText(a + " (" + e1 + ")");
                                    } else if (c.getJSONObject(x).getString("estado_atencion").equals("T")) {
                                        e2 = c.getJSONObject(x).getString("cantidad");
                                        t2.setText(b + " (" + e2 + ")");
                                    } else if (c.getJSONObject(x).getString("estado_atencion").equals("C")) {
                                        e3 = c.getJSONObject(x).getString("cantidad");
                                        t3.setText(z + " (" + e3 + ")");

                                    }
                                }


                                if (no == 0) {
                                    Toast.makeText(getContext(), "No hay incidencias para mostrar", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONArray d = r.getJSONArray("data1");
                                    final String[][] nomb = new String[d.length()][9];
                                    int m = 0;
                                    for (int n = 0; n < d.length(); n++) {
                                        nomb[n][m] = d.getJSONObject(n).getString("incidente_id");
                                        nomb[n][m + 1] = d.getJSONObject(n).getString("latitud");
                                        nomb[n][m + 2] = d.getJSONObject(n).getString("longitud");
                                        nomb[n][m + 3] = d.getJSONObject(n).getString("video");
                                        nomb[n][m + 4] = d.getJSONObject(n).getString("clasificacion");
                                        nomb[n][m + 5] = d.getJSONObject(n).getString("fecha_registro");
                                        nomb[n][m + 6] = d.getJSONObject(n).getString("incidencia_descripcion");
                                        nomb[n][m + 7] = d.getJSONObject(n).getString("estado_atencion");
                                        nomb[n][m + 8] = d.getJSONObject(n).getString("canal_comunicacion_id");

                                    }

                                    lv1.setAdapter(new adaptador(getContext(), nomb));
                                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                            Bundle datosAEnviar = new Bundle();

                                            datosAEnviar.putString("incidente_id", nomb[(int) lv1.getItemAtPosition(i)][0]);
                                            datosAEnviar.putString("latitud", nomb[(int) lv1.getItemAtPosition(i)][1]);
                                            datosAEnviar.putString("longitud", nomb[(int) lv1.getItemAtPosition(i)][2]);
                                            datosAEnviar.putString("video", nomb[(int) lv1.getItemAtPosition(i)][3]);
                                            datosAEnviar.putString("clasificacion", nomb[(int) lv1.getItemAtPosition(i)][4]);
                                            datosAEnviar.putString("fecha_registro", nomb[(int) lv1.getItemAtPosition(i)][5]);
                                            datosAEnviar.putString("incidencia_descripcion", nomb[(int) lv1.getItemAtPosition(i)][6]);
                                            datosAEnviar.putString("estado_atencion", nomb[(int) lv1.getItemAtPosition(i)][7]);
                                            datosAEnviar.putString("canal_comunicacion_id", nomb[(int) lv1.getItemAtPosition(i)][8]);


                                            datosAEnviar.putString("a", a);
                                            datosAEnviar.putString("b", b);
                                            datosAEnviar.putString("z", z);


                                            Fragment fragmento = new detalle_incidente();

                                            fragmento.setArguments(datosAEnviar);

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_content, fragmento);
                                            fragmentTransaction.addToBackStack(null);

                                            fragmentTransaction.commit();

                                            //Toast.makeText(getContext(), ""+lv1.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                                //Toast.makeText(getContext(), ""+d.length(), Toast.LENGTH_LONG).show();

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

    private void ejecutarServicio2(final String url) {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        try {
            sessionManager = new SessionManager(getActivity().getApplicationContext());
            HashMap<String, String> user = sessionManager.getUserDetail();

            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("nro_doc_ide", user.get(sessionManager.DNI));
            //parametros.put("nro_doc_ide","" );

            JSONObject jsonObj = new JSONObject(parametros);
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject r = response;


                                int no = r.getInt("count");
//                                t2.setText("En proceso (0)");
                                JSONArray c = r.getJSONArray("cantidad");
  /*                              e2 = c.getJSONObject(1).getString("estado_atencion");
                                if (e2.equals("P")) {
                                    e2=c.getJSONObject(1).getString("cantidad");
                                    t2.setText("En proceso ("+e2+")");
                                }
*/
                                if (no == 0) {
                                    Toast.makeText(getContext(), "No hay incidencias para mostrar", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONArray d = r.getJSONArray("data2");
                                    final String[][] nomb = new String[d.length()][9];
                                    int m = 0;
                                    for (int n = 0; n < d.length(); n++) {

                                        nomb[n][m] = d.getJSONObject(n).getString("incidente_id");
                                        nomb[n][m + 1] = d.getJSONObject(n).getString("latitud");
                                        nomb[n][m + 2] = d.getJSONObject(n).getString("longitud");
                                        nomb[n][m + 3] = d.getJSONObject(n).getString("video");
                                        nomb[n][m + 4] = d.getJSONObject(n).getString("clasificacion");
                                        nomb[n][m + 5] = d.getJSONObject(n).getString("fecha_registro");
                                        nomb[n][m + 6] = d.getJSONObject(n).getString("incidencia_descripcion");
                                        nomb[n][m + 7] = d.getJSONObject(n).getString("estado_atencion");
                                        nomb[n][m + 8] = d.getJSONObject(n).getString("canal_comunicacion_id");

                                    }

                                    lv2.setAdapter(new adaptador2(getContext(), nomb));
                                    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                            Bundle datosAEnviar = new Bundle();
                                            datosAEnviar.putString("incidente_id", nomb[(int) lv1.getItemAtPosition(i)][0]);
                                            datosAEnviar.putString("latitud", nomb[(int) lv1.getItemAtPosition(i)][1]);
                                            datosAEnviar.putString("longitud", nomb[(int) lv1.getItemAtPosition(i)][2]);
                                            datosAEnviar.putString("video", nomb[(int) lv1.getItemAtPosition(i)][3]);
                                            datosAEnviar.putString("clasificacion", nomb[(int) lv1.getItemAtPosition(i)][4]);
                                            datosAEnviar.putString("fecha_registro", nomb[(int) lv1.getItemAtPosition(i)][5]);
                                            datosAEnviar.putString("incidencia_descripcion", nomb[(int) lv1.getItemAtPosition(i)][6]);
                                            datosAEnviar.putString("estado_atencion", nomb[(int) lv1.getItemAtPosition(i)][7]);
                                            datosAEnviar.putString("canal_comunicacion_id", nomb[(int) lv1.getItemAtPosition(i)][8]);

                                            datosAEnviar.putString("a", a);
                                            datosAEnviar.putString("b", b);
                                            datosAEnviar.putString("z", z);

                                            Fragment fragmento = new detalle_incidente();

                                            fragmento.setArguments(datosAEnviar);

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_content, fragmento);
                                            fragmentTransaction.addToBackStack(null);

                                            fragmentTransaction.commit();

                                            //Toast.makeText(getContext(), ""+lv1.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                                //Toast.makeText(getContext(), ""+d.length(), Toast.LENGTH_LONG).show();

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

    private void ejecutarServicio3(final String url) {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        try {
            sessionManager = new SessionManager(getActivity().getApplicationContext());
            HashMap<String, String> user = sessionManager.getUserDetail();

            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("nro_doc_ide", user.get(sessionManager.DNI));
            //parametros.put("nro_doc_ide","" );

            JSONObject jsonObj = new JSONObject(parametros);
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject r = response;
                                int no = r.getInt("count");
                                //                              t3.setText("Atendidas  (0)");

                                JSONArray c = r.getJSONArray("cantidad");
    /*                            e3 = c.getJSONObject(2).getString("estado_atencion");
                                if (e3.equals("C")) {
                                    e3=c.getJSONObject(2).getString("cantidad");
                                    t3.setText("Atendidas  ("+e3+")");
                                }
*/
                                if (no == 0) {
                                    Toast.makeText(getContext(), "No hay incidencias para mostrar", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONArray d = r.getJSONArray("data3");
                                    final String[][] nomb = new String[d.length()][9];
                                    int m = 0;
                                    for (int n = 0; n < d.length(); n++) {

                                        nomb[n][m] = d.getJSONObject(n).getString("incidente_id");
                                        nomb[n][m + 1] = d.getJSONObject(n).getString("latitud");
                                        nomb[n][m + 2] = d.getJSONObject(n).getString("longitud");
                                        nomb[n][m + 3] = d.getJSONObject(n).getString("video");
                                        nomb[n][m + 4] = d.getJSONObject(n).getString("clasificacion");
                                        nomb[n][m + 5] = d.getJSONObject(n).getString("fecha_registro");
                                        nomb[n][m + 6] = d.getJSONObject(n).getString("incidencia_descripcion");
                                        nomb[n][m + 7] = d.getJSONObject(n).getString("estado_atencion");
                                        nomb[n][m + 8] = d.getJSONObject(n).getString("canal_comunicacion_id");

                                    }

                                    lv3.setAdapter(new adaptador3(getContext(), nomb));
                                    lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                            Bundle datosAEnviar = new Bundle();
                                            datosAEnviar.putString("incidente_id", nomb[(int) lv1.getItemAtPosition(i)][0]);
                                            datosAEnviar.putString("latitud", nomb[(int) lv1.getItemAtPosition(i)][1]);
                                            datosAEnviar.putString("longitud", nomb[(int) lv1.getItemAtPosition(i)][2]);
                                            datosAEnviar.putString("video", nomb[(int) lv1.getItemAtPosition(i)][3]);
                                            datosAEnviar.putString("clasificacion", nomb[(int) lv1.getItemAtPosition(i)][4]);
                                            datosAEnviar.putString("fecha_registro", nomb[(int) lv1.getItemAtPosition(i)][5]);
                                            datosAEnviar.putString("incidencia_descripcion", nomb[(int) lv1.getItemAtPosition(i)][6]);
                                            datosAEnviar.putString("estado_atencion", nomb[(int) lv1.getItemAtPosition(i)][7]);
                                            datosAEnviar.putString("canal_comunicacion_id", nomb[(int) lv1.getItemAtPosition(i)][8]);


                                            datosAEnviar.putString("a", a);
                                            datosAEnviar.putString("b", b);
                                            datosAEnviar.putString("z", z);

                                            Fragment fragmento = new detalle_incidente();

                                            fragmento.setArguments(datosAEnviar);

                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_content, fragmento);
                                            fragmentTransaction.addToBackStack(null);

                                            fragmentTransaction.commit();

                                            //Toast.makeText(getContext(), ""+lv1.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                                //Toast.makeText(getContext(), ""+d.length(), Toast.LENGTH_LONG).show();

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
