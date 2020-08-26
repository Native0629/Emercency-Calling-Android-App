package com.siae.siaecajamarca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class fragment_noti extends Fragment {
    View rootview;
    ListView lnotificaf;
    adaptadornoti adaptadornoti;
    SessionManager sessionManager;
    ArrayList<enotifica> enotificas;

    public fragment_noti() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            rootview =inflater.inflate(R.layout.fragment_fragment_noti, container, false);
            lnotificaf = rootview.findViewById(R.id.lnotifica);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        final globales d = new globales();

        enotificas= new ArrayList<>();
        ejecutarServicio(d.url+"notifica");
        return rootview;

    }
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

                                    JSONArray d = r.getJSONArray("data_noti");
                                //Toast.makeText(getContext(), ""+d, Toast.LENGTH_SHORT).show();
                                    //final String[][] nomb = new String[d.length()][9];
                                    //int m = 0;
                                    for (int n = 0; n < d.length(); n++) {
                                      /*  nomb[n][m] = d.getJSONObject(n).getString("incidente_id");
                                        nomb[n][m + 1] = d.getJSONObject(n).getString("latitud");
                                        nomb[n][m + 2] = d.getJSONObject(n).getString("longitud");
                                        nomb[n][m + 3] = d.getJSONObject(n).getString("video");
                                        nomb[n][m + 4] = d.getJSONObject(n).getString("clasificacion");
                                        nomb[n][m + 5] = d.getJSONObject(n).getString("fecha_registro");
                                        nomb[n][m + 6] = d.getJSONObject(n).getString("incidencia_descripcion");
                                        nomb[n][m + 7] = d.getJSONObject(n).getString("estado_atencion");
                                        nomb[n][m + 8] = d.getJSONObject(n).getString("canal_comunicacion_id");*/

                                        enotificas.add(new enotifica("hola", d.getJSONObject(n).getString("titulo_notifi"),d.getJSONObject(n).getString("descripcion_notifi")));

                                    }
                                    adaptadornoti = new adaptadornoti(getContext(), enotificas);
                                    lnotificaf.setAdapter(adaptadornoti);

                                    //lv1.setAdapter(new adaptador(getContext(), nomb));
                                    /*lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                    });*/


                                //}
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
