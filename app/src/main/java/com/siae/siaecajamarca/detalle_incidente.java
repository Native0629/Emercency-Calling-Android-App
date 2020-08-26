package com.siae.siaecajamarca;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class detalle_incidente extends Fragment {
    private View rootView;
    VideoView videos;
    TextView tip, est, fech, desc, video, coorde, origen,inci;
    SessionManager sessionManager;
    TextView nombre, dni, cel;
    LinearLayout tiene;
    ImageView map;
    private CircleImageView circleImageView;
    public static String url_imgn;

        /*private GoogleMap googleMap;
    private MapView mapView;
    private boolean mapsSupported = true;*/

    public detalle_incidente() {
        // Required empty public constructor
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getActivity());

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initializeMap();
    }
*/
    /*private void initializeMap() {
        if (googleMap == null && mapsSupported) {
            mapView = (MapView) getActivity().findViewById(R.id.mapView;
            googleMap = mapView.getMap();
            //setup markers etc...
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detalle_incidente, container, false);

        Bundle d = getArguments();
        final String incidente_id = d.getString("incidente_id");
        String latitud = d.getString("latitud");
        String longitud = d.getString("longitud");
        String video = d.getString("video");
        String clasificacion = d.getString("clasificacion");
        String fecha_registro = d.getString("fecha_registro");
        String incidencia_descripcion = d.getString("incidencia_descripcion");
        String estado_atencion = d.getString("estado_atencion");
        String canal_comunicacion = d.getString("canal_comunicacion_id");
        String a = d.getString("a");
        String b= d.getString("b");
        String z= d.getString("z");

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        nombre = rootView.findViewById(R.id.usuario);
        dni = rootView.findViewById(R.id.dni);
        cel = rootView.findViewById(R.id.cel);
        map = rootView.findViewById(R.id.mapa);
        circleImageView = rootView.findViewById(R.id.img_user);
        tiene = rootView.findViewById(R.id.tien);
        nombre.setText(user.get(sessionManager.NOMBRES) + " " + user.get(sessionManager.APE_PAT));
        dni.setText(user.get(sessionManager.DNI));
        cel.setText(user.get(sessionManager.CELULAR));
        url_imgn = user.get(sessionManager.IMGFB);

        tip = rootView.findViewById(R.id.tipo);
        est = rootView.findViewById(R.id.estado);
        fech = rootView.findViewById(R.id.fecha);
        desc = rootView.findViewById(R.id.desc);
        coorde = rootView.findViewById(R.id.coord);
        origen = rootView.findViewById(R.id.origen);
        inci = rootView.findViewById(R.id.nro_inc);

        inci.setText(incidente_id);
        if (url_imgn.equals("")) {
            Glide.with(getActivity()).load(R.drawable.perfil).into(circleImageView);
        } else {
            Glide.with(getActivity()).load(url_imgn).into(circleImageView);

        }
        if (estado_atencion.equals("P")) {
            est.setText(a);
        } else if (estado_atencion.equals("T")) {
            est.setText(b);
        } else if (estado_atencion.equals("C")) {
            est.setText(z);
        }
        if (clasificacion.equals("null")) {
            tip.setText("No tipificado");
        } else {
            tip.setText(clasificacion);
        }


        String latt = latitud.substring(0, 8);
        String lonn = longitud.substring(0, 8);
/*
        Uri uri= Uri.parse("http://siae.colaboraccion.pe/php/static_maps/map.php?basemap=streets&width=500&height=300&zoom=15&marker%5b%5d=lat:-11.897775;lng:-77.067563;icon:large-red-cutout&attribution=none");

        map.setImageURI(uri);
*/
        coorde.setText(latt + "," + lonn);
        new GetImageToURL().execute("http://siae.colaboraccion.pe/php/static_maps/map.php?basemap=streets&width=500&height=300&zoom=16&marker%5b%5d=lat:"+latt+";lng:"+lonn+";icon:large-red-cutout&attribution=none");

        if (canal_comunicacion.equals("1")) {
            origen.setText("PBX");
        } else if (canal_comunicacion.equals("2")) {
            origen.setText("Alerta APP");
        } else if (canal_comunicacion.equals("3")) {
            origen.setText("Alerta S.O.S");
        }

        //tip.setText(clasificacion);

        fech.setText(fecha_registro);
        desc.setText(incidencia_descripcion);

/*
        String path="http://95.217.44.43/siae_adjuntos_tmp/12-04-2020/5e934e76633e6.mp4";
        videos.setVideoURI(Uri.parse(path));
        videos.start();
*/
        tiene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datosAEnviar = new Bundle();

                datosAEnviar.putString("flag", "1");
                datosAEnviar.putString("incidente_id", incidente_id);

                Fragment fragmento = new Sugerencias();

                fragmento.setArguments(datosAEnviar);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_content, fragmento);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

            }
        });

        return rootView;

    }

    private class GetImageToURL extends AsyncTask< String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String...params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap myBitMap) {
            map.setImageBitmap(myBitMap);
        }
    }
}
