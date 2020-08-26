package com.siae.siaecajamarca;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

public class Sugerencias extends Fragment {

    private View rootview;
    Button envia;
    EditText msn;
    SessionManager sessionManager;
    String correo = "", incidente_id = "", flag = "", usuario = "";

    public Sugerencias() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_sugerencias, container, false);
        Bundle d = getArguments();
        //Toast.makeText(getContext(), ""+d, Toast.LENGTH_SHORT).show();
        if (d != null) {
            flag = d.getString("flag");
            incidente_id = d.getString("incidente_id");
            //msn.setText("falta cuerpo de mensjae");
        }

        msn = rootview.findViewById(R.id.mensaje);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        correo = user.get(sessionManager.EMAIL);
        usuario = user.get(sessionManager.NOMBRES);
        usuario = usuario + " " + user.get(sessionManager.APE_PAT);
        usuario = usuario + " " + user.get(sessionManager.APE_MAT);

        envia = rootview.findViewById(R.id.enviogmail);
        envia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msn.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "No puede Grabar con los campos Vacios", Toast.LENGTH_SHORT).show();

                } else {

                    if (validarletras(msn.getText().toString().trim()) != 0) {
                        Toast.makeText(getContext(), "El nombre no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                    } else {
                        if (flag.equals("1")){

                            sendMail_incidente();

                        }else{
                                sendMail();
                        }
                        mostrarDialog();

                    }
                }


            }
        });

        return rootview;
    }

    private void sendMail() {

        String mensaje = msn.getText().toString().trim();

        JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(),"siaempcajamarca@gmail.com","Sugerencia del Usuario: "+usuario,Html.fromHtml(""+mensaje));
        msn.setText("");
        javaMailAPI.execute();

    }

    private void sendMail_incidente() {

        String inc = incidente_id.trim();
        String mensajes = msn.getText().toString().trim();

        JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(),
                "siaempcajamarca@gmail.com",
                "Alerta de Incidente nro°" + inc + " - Usuario:" + usuario,
                Html.fromHtml(""+mensajes)
        );
        msn.setText("");
        javaMailAPI.execute();

    }

    public int validarletras(String texto) {
        int rpta = 0;
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.toUpperCase().charAt(i);
            int valorASCII = (int) caracter;
            if (valorASCII != 165 && valorASCII != 32 && (valorASCII < 65 || valorASCII > 90)) {
                rpta = 1;
            }
        }
        return rpta;
    }

    public void mostrarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sugerencia Enviada");
        builder.setMessage("Se ha enviado con exito su sugerencia, gracias ayudarnos a crecer.").
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //sessionManager.logout();
                        //AccessToken.setCurrentAccessToken(null);

                    }
                }).show();

    }
}
