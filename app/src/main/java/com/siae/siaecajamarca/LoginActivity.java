package com.siae.siaecajamarca;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final long MIN_TIME = 1000;
    SessionManager sessionManager;
    LinearLayout btnRegister, login, lforgot;
    EditText celular, dni;
    String latitudl="",longituddl="";
    double longitudelo, latitudelo;
    LocationManager locationManager;
    private Button fb ;
    private LoginButton loginButton;

    private CallbackManager callbackManager;
    globales d = new globales();
    String firts_name = "";
    String last_name = "";
    String email = "";
    String id = "";
    String url_img = "";

@RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        //final globales d = new globales();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        Intent i = getIntent();
        sessionManager = new SessionManager(this);
        celular = (EditText) findViewById(R.id.mobphone);
        dni = (EditText) findViewById(R.id.usrusr);
        lforgot = (LinearLayout) findViewById(R.id.forgot);
        celular.setText(i.getStringExtra("celular"));
        fb = (Button) findViewById(R.id.fb);

        loginButton = findViewById(R.id.login_button);
        callbackManager= CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));

        callbackManager= CallbackManager.Factory.create();

/*

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

*/

        if(ContextCompat.checkSelfPermission(getApplication().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                   != PackageManager.PERMISSION_GRANTED
                   && ContextCompat.checkSelfPermission(getApplication().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                   != PackageManager.PERMISSION_GRANTED) {

               requestPermissions( new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET,
                       Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                       Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO}, 1000);

           }

/*
           else {
               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPSlo);
           }
*/
        lforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Validacion_contraActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        btnRegister = (LinearLayout) findViewById(R.id.Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });

        login = (LinearLayout) findViewById(R.id.tvLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

/*
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity( getApplicationContext(), 0, i, 0);
                NotificationManager notif=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle("prueba2").setContentText("prueba2").
                        setContentTitle("prueba2").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal).setContentIntent(pendingIntent).build();
                //Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(1, notify);


                Toast.makeText(LoginActivity.this, "hola", Toast.LENGTH_SHORT).show();
*/


/*
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"2")
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .setContentTitle("hola22")
                        .setContentText("hola prueba 2222")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(2, builder.build());
*/

                if (dni.getText().toString().equals("") || celular.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext() , "Ingrese el número de celular y contraseña.", Toast.LENGTH_SHORT).show();
                } else {
                    int cantDni = dni.getText().toString().length();
                    int cantelefono = celular.getText().toString().length();
                    int primerDigitoTelefono = Integer.parseInt(celular.getText().toString().substring(0, 1));
                    if (cantDni == 0) {

                        Toast.makeText(getApplicationContext(), "Debe completar el campo contraseña.", Toast.LENGTH_SHORT).show();
                    } else if (cantelefono != 9) {
                        Toast.makeText(getApplicationContext(), "El celular solo pude tener 9 digitos.", Toast.LENGTH_SHORT).show();
                    } else if (primerDigitoTelefono != 9) {
                        Toast.makeText(getApplicationContext(), "El celular debe iniciar con el numero 9.", Toast.LENGTH_SHORT).show();
                    } else {


                        ejecutarServicio(d.url + "login");
                    }
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private final LocationListener locationListenerGPSlo = new LocationListener() {

        public void onLocationChanged(Location location) {
            longitudelo = location.getLongitude();
            latitudelo = location.getLatitude();

            final Handler j = new Handler();

            j.post(new Runnable() {
                @Override
                public void run() {
                    latitudl= latitudelo+"";
                    longituddl=longitudelo+"";
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

    private void ejecutarServicio(final String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        try {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("telefono", celular.getText().toString());
        parametros.put("nro_doc_ide", dni.getText().toString());
        final JSONObject jsonObj = new JSONObject(parametros);
        final JsonObjectRequest jsonObjRequest = new JsonObjectRequest
        (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String codigo = "";
                        try {

                            JSONObject a = response;
                            int count = a.getInt("count");

                            if (count == 0) {
                                final ProgressDialog progress = new ProgressDialog(LoginActivity.this);

                                progress.setTitle("Comprobando");
                                progress.setMessage("Usuario para Iniciar la Sesion");
                                progress.show();
                                Runnable progressRunnable = new Runnable() {

                                    @Override
                                    public void run() {
                                        progress.cancel();
                                        celular.setText("");
                                        dni.setText("");
                                        Toast.makeText(getApplicationContext(), "Usuario erroneo ", Toast.LENGTH_SHORT).show();
                                    }
                                };
                                Handler pdCanceller = new Handler();
                                pdCanceller.postDelayed(progressRunnable, 2000);
                            } else {
                                //String nombre= a.getJSONObject("data").getString("name");
                                final String email = a.getJSONObject("data").getString("email");
                                String ape_pat = a.getJSONObject("data").getString("ape_pat");
                                String ape_mat = a.getJSONObject("data").getString("ape_mat");
                                final String dni2 = a.getJSONObject("data").getString("dni");
                                String nombres = a.getJSONObject("data").getString("nombres");
                                String celu = a.getJSONObject("data").getString("celular");
                                final String tipo = a.getJSONObject("data").getString("tipo");


                                if (nombres.equals("null")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle("Observación");
                                    builder.setMessage("Para finalizar con el registro porfavor completar los siguientes datos: Nombre y Apellidos").
                                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(LoginActivity.this, ValidacionActivity.class);
                                                    i.putExtra("dni", dni2);
                                                    i.putExtra("celular", celular.getText().toString());
                                                    i.putExtra("correo", email);

                                                    startActivity(i);
                                                }
                                            }).show();
                                } else {
                                    if (email.equals("null")) {
                                        sessionManager.createSession("", ape_pat, ape_mat, dni2, nombres, celu, "", "", "","","","", "");
                                    } else {
                                        sessionManager.createSession(email, ape_pat, ape_mat, dni2, nombres, celu, "", "", "","","","",tipo);
                                    }
                                    final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                                    progress.setTitle("Comprobando");
                                    progress.setMessage("Iniciando Sesión ...");
                                    progress.show();

                                    Runnable progressRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            progress.cancel();
                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                            i.putExtra("tipo", tipo);
                                            startActivity(i);
                                            finish();
                                            Toast.makeText(getApplicationContext(), "Bienvenido al sistema S.I.A.E. ", Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 2000);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication().getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();

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

    AccessTokenTracker TokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
                if(currentAccessToken==null){
                    //txtcorreo.setText("");
                    //txtnombre.setText("");
                    //circleImageView.setImageResource(0);
                    Toast.makeText(getApplicationContext(), "Cerraste sesión", Toast.LENGTH_SHORT).show();
                }else {
                    LoadUserProfile(currentAccessToken);
                    //AccessToken.setCurrentAccessToken(null);
                }

        }
    };

    private void LoadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    firts_name = object.getString("first_name");
                    last_name = object.getString("last_name");

                    email = object.getString("email");
                    id = object.getString("id");
                    url_img = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    Toast.makeText(LoginActivity.this, ""+id+" "+firts_name+" "+last_name, Toast.LENGTH_SHORT).show();
                    ejecutarServiciofb(d.url+"loginfb");
                    //dni.setText(id);
                    //txtcorreo.setText(email);
                    //txtnombre.setText(firts_name+" "+last_name);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    //Glide.with(LoginActivity.this).load(url_img).into(circleImageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters= new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void ejecutarServiciofb(final String url) {

        RequestQueue queue = Volley.newRequestQueue(this);
        try {
            Map<String, String> parametros = new HashMap<String, String>();

            parametros.put("nombres", firts_name+" "+last_name);
            parametros.put("email", email);
            parametros.put("origen", "fb");
            parametros.put("idorigen", id);
            final JSONObject jsonObj = new JSONObject(parametros);
            final JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            try {

                                JSONObject a = response;
                                int count = a.getInt("count");

                                if (count == 0) {
                                    final ProgressDialog progress = new ProgressDialog(LoginActivity.this);

                                    progress.setTitle("Comprobando");
                                    progress.setMessage("Usuario para Iniciar la Sesion");
                                    progress.show();
                                    Runnable progressRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            progress.cancel();
                                            //celular.setText("");
                                            //dni.setText("55");
                                            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                                            i.putExtra("first_name", firts_name);
                                            i.putExtra("last_name", last_name);
                                            i.putExtra("email", email);
                                            i.putExtra("idorigen", id);
                                            i.putExtra("url_img", url_img);
                                            i.putExtra("origen", "fb");
                                            startActivity(i);


                                        }
                                    };
                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 2000);
                                } else {
                                    String dnifb = a.getJSONObject("data").getString("dni");
                                    String cel = a.getJSONObject("data").getString("celular");

                                    Toast.makeText(LoginActivity.this, "Bienvenido al sistema S.I.A.E. ", Toast.LENGTH_SHORT).show();
                                    sessionManager.createSession(email, last_name, last_name,dnifb , firts_name,cel, "", "", "","" ,"" ,url_img, "");
                                    Intent i= new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);


                                    //String nombre= a.getJSONObject("data").getString("name");
                                    /*final String email = a.getJSONObject("data").getString("email");
                                    String ape_pat = a.getJSONObject("data").getString("ape_pat");
                                    String ape_mat = a.getJSONObject("data").getString("ape_mat");
                                    String dni2 = a.getJSONObject("data").getString("dni");
                                    String nombres = a.getJSONObject("data").getString("nombres");
                                    String celu = a.getJSONObject("data").getString("celular");

                                    if (nombres.equals("null")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("Observación");
                                        builder.setMessage("Para finalizar con el registro porfavor completar los siguientes datos: Nombre y Apellidos").
                                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent i = new Intent(LoginActivity.this, ValidacionActivity.class);
                                                        i.putExtra("dni", dni.getText().toString());
                                                        i.putExtra("celular", celular.getText().toString());
                                                        i.putExtra("correo", email);

                                                        startActivity(i);
                                                    }
                                                }).show();
                                    } else {
                                        if (email.equals("null")) {
                                            sessionManager.createSession("", ape_pat, ape_mat, dni2, nombres, celu, "", "", "","","","");
                                        } else {
                                            sessionManager.createSession(email, ape_pat, ape_mat, dni2, nombres, celu, "", "", "","","","");
                                        }
                                        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                                        progress.setTitle("Comprobando");
                                        progress.setMessage("Iniciando Sesión ...");
                                        progress.show();

                                        Runnable progressRunnable = new Runnable() {

                                            @Override
                                            public void run() {
                                                progress.cancel();
                                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(i);
                                                finish();
                                                Toast.makeText(getApplicationContext(), "Bienvenido al sistema S.I.A.E. ", Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        Handler pdCanceller = new Handler();
                                        pdCanceller.postDelayed(progressRunnable, 2000);
                                    }
                                }*/
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplication().getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();

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


    public void onClickFacebook(View view) {
        if(view==fb){


            loginButton.performClick();

            //Intent i= new Intent(LoginActivity.this, MainActivity.class);
            //startActivity(i);


        }
    }

}
