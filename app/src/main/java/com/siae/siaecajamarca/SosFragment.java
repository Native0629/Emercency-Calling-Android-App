package com.siae.siaecajamarca;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SosFragment extends Fragment {
    private View rootView;
    TextView Latitud, Longitud;
    //String latitude,longitude;
    //SessionManager_local sl;

    String lati = "";
    String loni = "";
    private static final long MIN_TIME = 1000;
    LinearLayout ctxAmbulancia, ctxBomberos, ctxPolicias, sos, lbar;
    //myDialogFragment2 m;
    //ImageView sos2;
    globales n = new globales();
    ProgressBar pb;
    int gps = 0;
    String latitu = "", longitud = "";

    SessionManager sessionManager;
    int i = 0;
    LocationManager locationManager;
    double longitudeBest, latitudeBest;

    String vgps="1";
    public SosFragment() {
    }

    @Override
    public void onPause() {
        super.onPause();
        /*locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions( new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO}, 1000);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);
        }*/
        gps();
        //Toast.makeText(getContext(), "hola estoy en pause", Toast.LENGTH_SHORT).show();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sos, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ctxAmbulancia = (LinearLayout) rootView.findViewById(R.id.ambulancia);
        ctxBomberos = (LinearLayout) rootView.findViewById(R.id.bomberos);
        ctxPolicias = (LinearLayout) rootView.findViewById(R.id.policia);
        /*Latitud = (TextView) rootView.findViewById(R.id.TLatitud);
        Longitud = (TextView) rootView.findViewById(R.id.TLongitud);*/
        sos = (LinearLayout) rootView.findViewById(R.id.sos);
        pb = (ProgressBar) rootView.findViewById(R.id.barprog);
        lbar = (LinearLayout) rootView.findViewById(R.id.lbarprog);
        // sos2=(ImageView) rootView.findViewById(R.id.sos2);
        final globales d = new globales();
        //iniciarLocalizacion();
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
       /* if(!checkIfLocationOpened()){
            iniciarLocalizacion();
        }*/
        //Toast.makeText(getContext(), "1VEZ ", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "2VEZ ", Toast.LENGTH_SHORT).show();

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions( new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO}, 1000);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS);
        }

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vgps="1";
                // m=new myDialogFragment2();
                // m.show(getFragmentManager(),"");
                final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!gpsEnabled) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    vgps="0";

                } else {
                    /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }*/
                    if(vgps.equals("1")){

                    final ProgressDialog progress = new ProgressDialog(getContext());
                    progress.setTitle("Cargando");
                    progress.setMessage("Enviando SOS");
                    progress.show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //m.dismiss();
                            progress.cancel();
                            //Intent i=new Intent(getActivity(),MainActivity.class);
                            //startActivity(i);
                            //Toast.makeText(getActivity().getApplicationContext(), "lat "+latitud.getText().toString()+"  "+"long"+longitud.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }, 2000);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //m.dismiss();
                            ejecutarServicio(d.url3);
                            //Intent i=new Intent(getActivity(),MainActivity.class);
                            //startActivity(i);
                            //Toast.makeText(getActivity().getApplicationContext(), "lat "+latitud.getText().toString()+"  "+"long"+longitud.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }, 1000);


                    //Toast.makeText(getActivity().getApplicationContext(), "Enviando Mensaje de Alerta Inmediata", Toast.LENGTH_SHORT).show();

                    //alerta(v);

                    }
                }
            }
        });
        //sos.setOnClickListener(this);
        ctxAmbulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Llamando a la ambulancia", Toast.LENGTH_SHORT).show();
                llamar(n.ambulancia);
            }
        });



        ctxBomberos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "llamando a los Bomberos", Toast.LENGTH_SHORT).show();
                llamar(n.bomberos);
            }
        });

        ctxPolicias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "llamando a la Serenos", Toast.LENGTH_SHORT).show();
                llamar(n.serenazgos);

            }
        });



        return rootView;
    }


public void gps(){
    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

        requestPermissions( new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO}, 1000);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS);

    }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS);

}
    private final LocationListener locationListenerGPS = new LocationListener() {

        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            final Handler h = new Handler();

            h.post(new Runnable() {
                @Override
                public void run() {
                    latitu= latitudeBest+"";
                    longitud=longitudeBest+"";
                    //Toast.makeText(getContext(), longitudeBest + "", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), latitudeBest + "", Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void ejecutarServicio(String url) {

        gps();
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        String dni = user.get(sessionManager.DNI);
        String celular = user.get(sessionManager.CELULAR);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        try {
            Map<String, String> parametros = new HashMap<String, String>();

            parametros.put("latitud", latitu);
            parametros.put("longitud", longitud);
            parametros.put("dni", dni);
            parametros.put("celular", celular);
            JSONObject jsonObj = new JSONObject(parametros);
            //Toast.makeText(getActivity().getApplicationContext(), "lat "+la+"  "+"long"+lo, Toast.LENGTH_SHORT).show();


            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject r = response;
                                String error = r.getString("error");
                                final String msn = r.getString("msg");

                                if (error.equals("false")) {
                                    Toast.makeText(getActivity().getApplicationContext(), msn, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity().getApplicationContext(), "Mensaje Enviado ....", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                                }
                            });

            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*public int alerta(View v){
        int a=0;
        //sos.isClickable();
        //final Handler h = new Handler();
       // pb.setVisibility(View.VISIBLE);
       //sos.setClickable(false);
        //sos2.setVisibility(View.VISIBLE);
        //sos2.setEnabled(false);
        Thread r = new Thread(new Runnable(){
            @Override
            public void run() {

                    while(i<=100){

                     //h.post(new Runnable() {
                       // @Override
                        //public void run() {
                        if(i==100){
                            pb.setProgress(0);

                            //sos.setClickable(true);
                            //sos.setEnabled(true);
                        }else {

                            pb.setProgress(i);}
                        //}
                    //});
                    try {

                        i++;
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                    }

        } );
        i=0;
        r.start();
         return  a=1;
    }
*/
   /* public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]grantResults) {
        if(requestCode == 1000) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarLocalizacion();
                return;
            }
        }
    }*/
    public void llamar(String tel) {
        try {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
