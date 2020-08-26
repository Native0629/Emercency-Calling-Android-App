package com.siae.siaecajamarca;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.siae.siaecajamarca.R.layout.fragment_reportar;


public class ReportarFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View rootView;
    public String ru;

    public ReportarFragment() {
        // Required empty public constructor
    }

    TextView Latitude, Longitude;

    LinearLayout enviar, floatingActionButtonAudio, floatingActionButtonVideo, span;
    ImageView closemp4, closemp3, play1, play2, borrar1, borrar2;

    globales nm = new globales();
    String rutavideo = "";
    String rutaaudio = "";
    SessionManager sessionManager;

    SessionManager_video sessionManager_video;
    EditText mesagge;
    TextView fila1, fila2;
    ImageView imp2, imp5, microfono;
    private MediaRecorder grabacion;
    MediaRecorder mediaRecorder;
    private String archivoSalida = null;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static String grabo = null;
    String audioenvio = "", videoEnvio = "";

    private Context cntx = null;
    String latitudd = "", longituddd = "";
    LocationManager locationManager;
    double longitudeBest2, latitudeBest2;
    Spinner spi;
    HashMap<String, String> user;
    String categoria;
    MediaPlayer m;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cntx = this.getActivity();
        rootView = inflater.inflate(fragment_reportar, container, false);
        mesagge = rootView.findViewById(R.id.mensaje);
        Latitude = (TextView) rootView.findViewById(R.id.TLatitud);
        Longitude = (TextView) rootView.findViewById(R.id.TLongitud);
        microfono = (ImageView) rootView.findViewById(R.id.micor1);
        play1 = rootView.findViewById(R.id.play1);
        play2 = rootView.findViewById(R.id.play2);
        borrar1 = rootView.findViewById(R.id.borrar1);
        borrar2 = rootView.findViewById(R.id.borrar2);

        play1.setEnabled(false);
        play2.setEnabled(false);
        borrar1.setEnabled(false);
        borrar2.setEnabled(false);
        play2.setImageDrawable(getResources().getDrawable(R.drawable.play_off_64));
        borrar2.setImageDrawable(getResources().getDrawable(R.drawable.borrar_off_64));

        //Toast.makeText(cntx, "he creado nuevamente", Toast.LENGTH_SHORT).show();
        sessionManager_video = new SessionManager_video(getActivity().getApplicationContext());
        HashMap<String, String> r = sessionManager_video.getUserDetail_l();


        ru = r.get(sessionManager_video.RVIDEO);
        rutavideo = r.get(sessionManager_video.RlVIDEO);
        //Toast.makeText(cntx, ""+r.get(sessionManager_video.RlVIDEO), Toast.LENGTH_SHORT).show();
        if (!ru.equals("")) {
            play2.setEnabled(true);
            borrar2.setEnabled(true);
            play2.setImageDrawable(getResources().getDrawable(R.drawable.play_on_64));
            borrar2.setImageDrawable(getResources().getDrawable(R.drawable.borrar_on_64));
        }
        //fila1 = (TextView) rootView.findViewById(R.id.fila1);
        //fila2 = (TextView) rootView.findViewById(R.id.fila2);
        //imp2 = (ImageView) rootView.findViewById(R.id.imageView2);
        //imp5 = (ImageView) rootView.findViewById(R.id.imageView5);
        spi = rootView.findViewById(R.id.cate);
        //span = (LinearLayout) rootView.findViewById(R.id.xpandible);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        user = sessionManager.getUserDetail();

        //imp2.setVisibility(View.INVISIBLE);
        //imp5.setVisibility(View.INVISIBLE);
        //span.setVisibility(View.INVISIBLE);
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        spi.setOnItemSelectedListener(this);

        //final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        /*if (!gpsEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS2);
*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categoria, R.layout.spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spi.setAdapter(adapter);

        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO}, 1000);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS2);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListenerGPS2);
        }

        microfono.setImageDrawable(getResources().getDrawable(R.drawable.fragmentreportar_micro));

        mesagge.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String dni = user.get(sessionManager.DNI);
                    String celular = user.get(sessionManager.CELULAR);
                    String email = user.get(sessionManager.EMAIL);
                    String nombre = user.get(sessionManager.NOMBRES);
                    String ape = user.get(sessionManager.APE_PAT);
                    String ama = user.get(sessionManager.APE_MAT);
                    if (!dni.equals("") && !celular.equals("")) {
                        sessionManager.createSession(email, ape, ama, dni, nombre, celular, mesagge.getText().toString(), "", "", "", "", "", "");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mesagge.setText(user.get(sessionManager.MENSAJE));
        enviar = (LinearLayout) rootView.findViewById(R.id.enviarAlerta);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                final globales d = new globales();

                String mesaje = mesagge.getText().toString();

                if (mesaje.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Debe ingresa un texto", Toast.LENGTH_SHORT).show();
                } else {
                    ejecutarServicio(d.url2);

                    String dni = user.get(sessionManager.DNI);
                    String celular = user.get(sessionManager.CELULAR);
                    String email = user.get(sessionManager.EMAIL);
                    String nombre = user.get(sessionManager.NOMBRES);
                    String ape = user.get(sessionManager.APE_PAT);
                    String ama = user.get(sessionManager.APE_MAT);
                    String tipo = user.get(sessionManager.TIPO);
                    mesagge.setText("");
                    sessionManager.createSession(email, ape, ama, dni, nombre, celular, "", "", "", "", "", "", tipo);
                    sessionManager_video.createSession_video("", "");

                }

            }

        });

        /*closemp4 = (ImageView) rootView.findViewById(R.id.imageView2);
        closemp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close1(v);
            }
        });*/

        /*
        closemp3 = (ImageView) rootView.findViewById(R.id.imageView5);
        closemp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close2(v);
            }
        });
*/
        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                //builder.setTitle("Video ");
                builder.setCancelable(true);
                final View dialoView = inflater.inflate(R.layout.video_muestra, null);
                builder.setView(dialoView);
                final VideoView vid = (VideoView) dialoView.findViewById(R.id.vi);
                vid.setVideoPath(rutavideo);
                vid.setMediaController(new MediaController(getContext()));
                vid.start();
                vid.requestFocus();

                builder.show();
                builder.create();
            }

        });
        borrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar2.setImageDrawable(getResources().getDrawable(R.drawable.borrar_off_64));
                play2.setImageDrawable(getResources().getDrawable(R.drawable.play_off_64));
                Toast.makeText(getActivity().getApplicationContext(), "Borrando video...", Toast.LENGTH_SHORT).show();
                play2.setEnabled(false);
                borrar2.setEnabled(false);
                ru = "";
                rutavideo = "";
                sessionManager_video.createSession_video("", "");
            }
        });

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(archivoSalida);
                m = MediaPlayer.create(getContext(), uri);

                m.start();
                Toast.makeText(cntx, "Reproduciendo...", Toast.LENGTH_SHORT).show();
            }
        });
        borrar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar1.setImageDrawable(getResources().getDrawable(R.drawable.borrar_off_64));
                play1.setImageDrawable(getResources().getDrawable(R.drawable.play_off_64));
                archivoSalida = null;
                rutaaudio = "";
                audioenvio = "";
                play1.setEnabled(false);
                borrar1.setEnabled(false);
                Toast.makeText(getActivity().getApplicationContext(), "Borrando Audio...", Toast.LENGTH_SHORT).show();

            }
        });
        floatingActionButtonAudio = (LinearLayout) rootView.findViewById(R.id.floatingaudio);
        floatingActionButtonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!audioenvio.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Ya existe un audio existente, elimine el anterior", Toast.LENGTH_SHORT).show();

                } else {
                    Recorder(v);
                }

                /*if (fila1.getText().equals("• AUDIO GRABADO") || fila2.getText().equals("• AUDIO GRABADO")) {
                                    } else {

                }*/
            }

        });

        floatingActionButtonVideo = (LinearLayout) rootView.findViewById(R.id.floatingvideo);
        floatingActionButtonVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!ru.equals("")) {

                    Toast.makeText(getActivity().getApplicationContext(), "Ya existe un video existente, elimine el anterior", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(getActivity(), CameraActivity.class);
                    startActivity(i);

                }

                /*if (fila1.getText().equals("• VIDEO GRABADO") || fila2.getText().equals("• VIDEO GRABADO")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Ya existe un video existente, elimine el anterior", Toast.LENGTH_SHORT).show();
                } else {


                    sessionManager_video = new SessionManager_video(getActivity().getApplicationContext());

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (fila1.getText().equals("")) {
                                fila1.setText("• VIDEO GRABADO");
                                imp2.setVisibility(View.VISIBLE);
                            } else if (fila1.getText().equals("• AUDIO GRABADO")) {
                                fila2.setText("• VIDEO GRABADO");
                                imp5.setVisibility(View.VISIBLE);
                            }
                            span.setVisibility(View.VISIBLE);
                        }

                        //}
                    }, 1000);
                }*/
            }

        });

        return rootView;

    }

    public void close1(View view) {

        if (fila1.getText().equals("• AUDIO GRABADO")) {
            fila1.setText("");
            imp2.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity().getApplicationContext(), "Borrando Audio...", Toast.LENGTH_SHORT).show();
            archivoSalida = null;
            rutaaudio = "";
            floatingActionButtonAudio.setEnabled(true);
        }

        if (fila1.getText().equals("• VIDEO GRABADO") && fila2.getText().equals("")) {
            fila1.setText("");
            Toast.makeText(getActivity().getApplicationContext(), "Borrando Video...", Toast.LENGTH_SHORT).show();
            imp2.setVisibility(View.INVISIBLE);
            imp2.setVisibility(View.INVISIBLE);
            videoEnvio = null;
            sessionManager_video.createSession_video("", "");
            floatingActionButtonVideo.setEnabled(true);

        }

        if (fila1.getText().equals("• VIDEO GRABADO") || (fila1.getText().equals("• VIDEO GRABADO") && fila2.getText().equals("• AUDIO GRABADO"))) {
            videoEnvio = null;
            sessionManager_video.createSession_video("", "");
            fila2.setText("");
            fila1.setText("• AUDIO GRABADO");
            floatingActionButtonVideo.setEnabled(true);

            imp2.setVisibility(View.VISIBLE);
            imp5.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity().getApplicationContext(), "Borrando Video...", Toast.LENGTH_SHORT).show();
        }
        if (fila1.getText().equals("") && fila2.getText().equals("• VIDEO GRABADO")) {
            fila1.setText("• VIDEO GRABADO");
            fila2.setText("");
            videoEnvio = null;
            sessionManager_video.createSession_video("", "");
            imp2.setVisibility(View.VISIBLE);
            imp5.setVisibility(View.INVISIBLE);
            floatingActionButtonVideo.setEnabled(true);
        }

        if (fila1.getText().equals("• AUDIO GRABADO") && fila2.getText().equals("• VIDEO GRABADO")) {
            fila1.setText("• VIDEO GRABADO");
            fila2.setText("");
            audioenvio = null;
            rutaaudio = "";
            floatingActionButtonAudio.setEnabled(true);

            imp2.setVisibility(View.VISIBLE);
            imp5.setVisibility(View.INVISIBLE);
        }
        if (fila1.getText().equals("") && fila2.getText().equals("")) {
            span.setVisibility(View.INVISIBLE);
        }
    }

    public void close2(View view) {
        fila2.setText("");
        imp5.setVisibility(View.INVISIBLE);
        if (fila2.getText().equals("• AUDIO GRABADO")) {
            Toast.makeText(getActivity().getApplicationContext(), "Borrando Audio ...", Toast.LENGTH_SHORT).show();
            archivoSalida = null;
            rutaaudio = "";
        } else if (fila2.getText().equals("• VIDEO GRABADO")) {
            Toast.makeText(getActivity().getApplicationContext(), "Borrando Video...", Toast.LENGTH_SHORT).show();
            archivoSalida = null;
            rutaaudio = "";
        }
        if (fila1.getText().equals("") && fila2.getText().equals("• AUDIO GRABADO")) {
            fila1.setText("• AUDIO GRABADO");
            fila2.setText("");
            imp5.setVisibility(View.VISIBLE);
            imp2.setVisibility(View.INVISIBLE);
        }
    }

    public void Recorder(View view) {
        String audio;
        if (grabacion == null) {
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record2.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);
            grabo = archivoSalida;
            try {
                grabacion.prepare();
                grabacion.start();
                microfono.setImageDrawable(getResources().getDrawable(R.drawable.rtauio));
                Toast.makeText(getActivity().getApplicationContext(), "Grabando audio...", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
            }
        } else if (grabacion != null) {
            Toast.makeText(getActivity().getApplicationContext(), "Grabación Exitosa...", Toast.LENGTH_SHORT).show();
            grabacion.stop();
            grabacion.release();
            grabacion = null;

            if (grabo != null) {

                audio = archivoSalida;
            } else {
                audio = "";
            }
            File fileaudio;
            byte[] bytes = new byte[0];
            if (audio.equals(null)) {
                audioenvio = "";
            } else {
                fileaudio = new File(audio);
                try {
                    bytes = FileUtils.readFileToByteArray(fileaudio);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                audioenvio = Base64.encodeToString(bytes, 0);

                play1.setImageDrawable(getResources().getDrawable(R.drawable.play_on_64));
                borrar1.setImageDrawable(getResources().getDrawable(R.drawable.borrar_on_64));
                borrar1.setEnabled(true);
                play1.setEnabled(true);
                final globales d = new globales();
                audio(d.url4);
            }

/*
            if (fila1.getText().equals("")) {
                fila1.setText("• AUDIO GRABADO");
                imp2.setVisibility(View.VISIBLE);
            } else if (fila1.getText().equals("• VIDEO GRABADO")) {
                fila2.setText("• AUDIO GRABADO");
                imp5.setVisibility(View.VISIBLE);
            }
*/

            microfono.setImageDrawable(getResources().getDrawable(R.drawable.fragmentreportar_micro));

            //span.setVisibility(View.VISIBLE);

        }

    }

    private void audio(String url) {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        try {
            Map<String, String> parametros = new HashMap<String, String>();

            parametros.put("audio", audioenvio);

            JSONObject jsonObj = new JSONObject(parametros);

            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle("Cargando");
            progress.setMessage("Adjuntando audio");
            progress.show();

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject r = response;
                                String error = r.getString("error");
                                rutaaudio = r.getString("msg");

                                if (error.equals("false")) {
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            progress.cancel();

                                        }
                                    }, 2500);
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
                                    progress.cancel();
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

    private final LocationListener locationListenerGPS2 = new LocationListener() {

        public void onLocationChanged(Location location) {
            longitudeBest2 = location.getLongitude();
            latitudeBest2 = location.getLatitude();

            final Handler j = new Handler();

            j.post(new Runnable() {
                @Override
                public void run() {
                    latitudd = latitudeBest2 + "";
                    longituddd = longitudeBest2 + "";
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

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void ejecutarServicio(String url) {


        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        try {
            sessionManager = new SessionManager(getActivity().getApplicationContext());
            HashMap<String, String> user = sessionManager.getUserDetail();

            String dni = user.get(sessionManager.DNI);
            String celular = user.get(sessionManager.CELULAR);
            String mens = user.get(sessionManager.MENSAJE);

            sessionManager_video = new SessionManager_video(getActivity().getApplicationContext());
            HashMap<String, String> r = sessionManager_video.getUserDetail_l();
            ru = r.get(sessionManager_video.RVIDEO);
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("latitud", latitudd);
            parametros.put("longitud", longituddd);
            parametros.put("mensaje", mens);
            parametros.put("dni", dni);
            parametros.put("audio", rutaaudio);
            parametros.put("video", ru);
            parametros.put("celular", celular);
            if (categoria.equals("4")) {
                parametros.put("categoria", "0");
            } else {
                parametros.put("categoria", categoria);
            }

            Toast.makeText(getActivity().getApplicationContext(), "Mensaje Enviado ....", Toast.LENGTH_SHORT).show();

            JSONObject jsonObj = new JSONObject(parametros);

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject r = response;
                                String error = r.getString("error");
                                final String msn = r.getString("msg");
                                final ProgressDialog progress = new ProgressDialog(getContext());
                                progress.setTitle("Cargando");
                                progress.setMessage("Enviando alerta.");
                                progress.show();

                                if (error.equals("false")) {
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            progress.cancel();
                                            Intent i = new Intent(getActivity(), MainActivity.class);
                                            //i.putExtra("tipo", "1");

                                            startActivity(i);

                                        }
                                    }, 2000);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoria = String.valueOf(i + 4);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
