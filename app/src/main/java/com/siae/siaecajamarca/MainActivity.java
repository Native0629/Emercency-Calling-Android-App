package com.siae.siaecajamarca;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private final static String CHANNEL_ID = "NOTIFICACION";
    public static int NOTIFICACION_ID = 0;
    public String flag = "0";
    public String flag2 = "0";
    String tipo;
    String tipou = "1";
    String titulo;
    String mensaje;
    String noti_id;
    String estado;
    String fecha_lanza;
    SessionManager sessionManager;
    SessionManager_video sessionManager_video;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;
    private Socket msocket;
    private Context Context=this;


    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

//                    JSONObject dateee = (JSONObject) args[0];
                    JSONObject dataa = (JSONObject) args[0];
                    //Toast.makeText(MainActivity.this, ""+args.length, Toast.LENGTH_SHORT).show();

                    try {
                        sessionManager = new SessionManager(getBaseContext());

                        HashMap<String, String> user = sessionManager.getUserDetail();
                        String dniu = user.get(SessionManager.DNI);

                        noti_id = dataa.getString("notificacion_id");
                        estado = dataa.getString("estado");

                        if (!estado.equals("P")) {
                            JSONObject confirma = new JSONObject();
                            confirma.put("notifica_id", noti_id);
                            confirma.put("nro_dni", dniu);
                            msocket.emit("confinoti", confirma);
                            //msocket.disconnect();
                            //Toast.makeText(MainActivity.this, ""+estado, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };
    private Boolean hasConnection = false;
    private boolean startTyping = false;
    private int time = 2;
    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.i(TAG, "handleMessage: typing stopped " + startTyping);
            if (time == 0) {
                setTitle("SocketIO");
                //Log.i(TAG, "handleMessage: typing stopped time is " + time);
                startTyping = false;
                time = 2;
            }

        }
    };
    private PendingIntent pendingIntent;
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];


                    try {
                        tipo = data.getString("tipo");
                        titulo = data.getString("titulo");
                        mensaje = data.getString("descripcion");
                        fecha_lanza = data.getString("fecha_lanzamiento");
                        //msocket.emit("confinoti",titulo);
                        //Toast.makeText(MainActivity.this, ""+tipo+titulo+mensaje, Toast.LENGTH_SHORT).show();

                        //Toast.makeText(getApplication(), ""+data, Toast.LENGTH_SHORT).show();

                        setPendingIntent();
                        createNotificationChannel();
                        createNotification();

/*
                        MessageFormat format = new MessageFormat(id, username, message);
                        Log.i(TAG, "run:4 ");
                        messageAdapter.add(format);
                        Log.i(TAG, "run:5 ");
*/

                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_content, new MisDatosFragment());
                    fragmentTransaction.commit();
                    sessionManager_video.createSession_video("", "");
                    return true;
                case R.id.navigationMyCourses:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_content, new SosFragment());
                    fragmentTransaction.commit();
                    sessionManager_video.createSession_video("", "");
                    return true;
                case R.id.navigationSearch:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_content, new ReportarFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigationMenu:
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    sessionManager_video.createSession_video("", "");
                    return true;
            }
            return false;
        }
    };

//    {
//        try {
//            msocket = IO.socket("http://192.168.4.10:5000");
//        } catch (URISyntaxException e) {
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getBaseContext());
            startService(new Intent(Context,servicionoti.class) );
        HashMap<String, String> usertipo = sessionManager.getUserDetail();


        Intent i = getIntent();
        flag = i.getStringExtra("flag");
        Intent o = getIntent();
        flag2 = o.getStringExtra("lat");
        //Intent l = getIntent();
        tipou = usertipo.get(SessionManager.TIPO);
      //  Toast.makeText(this, "" + tipou, Toast.LENGTH_SHORT).show();
        sessionManager_video = new SessionManager_video(getBaseContext());


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }
        if (hasConnection) {

        } else {
            if (Objects.equals(flag2, "1")) {
                //Toast.makeText(this, "ha entrado aqui", Toast.LENGTH_SHORT).show();
            } else {
              //  Socket a = msocket.connect();
                //Toast.makeText(this, "" + a, Toast.LENGTH_SHORT).show();
               // msocket.on("connectw user", onNewUser);
                //msocket.on("notificacion", onNewMessage);
            }


        }
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String name = user.get(SessionManager.NOMBRES) + " " + user.get(SessionManager.APE_PAT);
        String correo = user.get(SessionManager.EMAIL);

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        android.view.Menu item = navigationView.getMenu();
        MenuItem datos = item.findItem(R.id.nav_camera);
        MenuItem incidencias = item.findItem(R.id.nav_gallery);
        MenuItem frecuentes = item.findItem(R.id.nav_preguntas);
        MenuItem sugerencias = item.findItem(R.id.nav_sugerencias);
        MenuItem notifica = item.findItem(R.id.nav_notifica);
        MenuItem estadistica = item.findItem(R.id.nav_o_estadistica);
        MenuItem mapadelito = item.findItem(R.id.nav_o_mapa);
        MenuItem acerca = item.findItem(R.id.nav_acerca);
        if (!Objects.equals(tipou, "1")){
            incidencias.setVisible(false);
            frecuentes.setVisible(false);
            sugerencias.setVisible(false);

        }else{
            estadistica.setVisible(false);
            mapadelito.setVisible(false);

        }
        TextView navUsername = headerView.findViewById(R.id.txtIdNav);
        TextView navCorreo = headerView.findViewById(R.id.txtCorreoNav);


        navCorreo.setText(correo);
        navUsername.setText(name);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        if (Objects.equals(flag, "1")) {

            bottomNavigationView.setSelectedItemId(R.id.navigationSearch);

            flag = "";
        } else if (Objects.equals(flag2, "1")) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment_content, new fragment_noti()).commit();
        } else {
            sessionManager_video.createSession_video("", "");

            bottomNavigationView.setSelectedItemId(R.id.navigationMyCourses);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new SosFragment());
            fragmentTransaction.commit();
            sessionManager_video.createSession_video("", "");

            mostrarDialog();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_action_info);
        builder.setContentTitle(titulo);
        builder.setContentText(mensaje);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
        NOTIFICACION_ID++;
    }

    private void setPendingIntent() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("lat", "1");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        //flag2="1";
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
        /*fragmentTransaction.replace(R.id.fragment_content, new fragment_noti());
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();*/
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cerrar) {
            mostrarDialog();
        } else if (id == R.id.nav_gallery) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new MisIncidendiasFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_camera) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new MisDatosFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_preguntas) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new preguntas_frecuentes());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_sugerencias) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new Sugerencias());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_acerca) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new fragment_acercade_siae());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_notifica) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new fragment_noti());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_o_estadistica) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new p_operador());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_o_mapa) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new p_operador_map2());
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mostrarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Seguro que desea cerrar la aplicación?").
                setNegativeButton("No", null).
                setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.logout();
                        AccessToken.setCurrentAccessToken(null);

                    }
                }).show();

    }




}
