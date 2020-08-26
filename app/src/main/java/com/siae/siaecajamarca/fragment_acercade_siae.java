package com.siae.siaecajamarca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;


public class fragment_acercade_siae extends Fragment {

    View rootview;
    Button manual;

    TextView nombre;
    TextView link;
    private SessionManager sessionManager;

    public fragment_acercade_siae() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_fragment_acercade_siae, container, false);

        manual = rootview.findViewById(R.id.manual);
        nombre= rootview.findViewById(R.id.nombre);
        link= rootview.findViewById(R.id.link);
        sessionManager = new SessionManager(getContext());


        HashMap<String, String> user = sessionManager.getUserDetail();
        String name = user.get(SessionManager.NOMBRES) + " " + user.get(SessionManager.APE_PAT);

        nombre.setText("Estimado "+name+" :");


        link.setText(Html.fromHtml("<p style='text-align: justify;'>Ponemos a su disposición el manual de usuario, una guía rápida y" +
                " práctica para conocer las funcionalidades del aplicativo móvil SIAE Cajamarca," +
                " en el encontrará temas como: reportar incidencias, SOS, notificaciones " +
                "para el ciudadano y más. También contamos con una sección de preguntas " +
                "frecuentes la cual puedes revisar <strong>aquí</strong>.</p>"));

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmento = new preguntas_frecuentes();
                //fragmento.setArguments(datosAEnviar);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_content, fragmento);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://95.217.44.43/siae/manual/SIAE_manual_aplicativo_app.pdf")));

/*
                Intent i=new Intent(getActivity(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity( getContext(), 0, i, 0);
                NotificationManager notif=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getContext()).setContentTitle("prueba2").setContentText("prueba2").
                        setContentTitle("prueba2").setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal).setContentIntent(pendingIntent).build();
                //Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(1, notify);
*/

                //descargar();

            }
        });


        return rootview;
    }

/*
    private void descargar() {
        try {

            URL url = new URL("http://95.217.44.43/siae/manual/SIAE_manual_aplicativo_app.pdf");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            //String Path = Environment.getExternalStorageDirectory() + "/download/";
            String Path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" ;

            Log.v("PdfManager", "PATH: " + Path);
            File file = new File(Path);
            file.mkdirs();
            FileOutputStream fos = new FileOutputStream("siae.pdf");

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[702];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            Log.d("PdfManager", "Error: " + e);
        }
        Log.v("PdfManager", "Check: ");
    }
*/


}
