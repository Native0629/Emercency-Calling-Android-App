package com.siae.siaecajamarca;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

public class servicionoti extends Service {
    private Socket msocket;
    SessionManager sessionManager;
    String noti_id;
    String estado;
    String tipo;
    String tipou = "1";
    String titulo;
    String mensaje;
    String fecha_lanza;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public static int NOTIFICACION_ID = 0;
    private PendingIntent pendingIntent;


    {
        try {
            msocket = IO.socket("http://95.217.44.43:5000");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        msocket.connect();
        msocket.on("connect user", onNewUser);
        msocket.on("notificacion", onNewMessage);
//        msocket.on("chat message", onNewMessage);
//        msocket.on("on typing", onTyping);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       //Toast.makeText(this, "se inicio servcios", Toast.LENGTH_SHORT).show();

        {
            try {
                msocket = IO.socket("http://95.217.44.43:5000");
             } catch (URISyntaxException e) {
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {

//                    JSONObject dateee = (JSONObject) args[0];
                    JSONObject dataa = (JSONObject) args[0];
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
                    //Toast.makeText(MainActivity.this, ""+args.length, Toast.LENGTH_SHORT).show();
               ///   Toast.makeText(getBaseContext(), "aqui llegooo", Toast.LENGTH_SHORT).show();
                }
        //    });
        //}
    };
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {

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
//                }
//            });
        }
    };

    private void setPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
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
}
