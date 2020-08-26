package com.siae.siaecajamarca;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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


public class MisDatosFragment extends Fragment  {
    private View rootView;

    EditText txtapePatA,txtapeMatA,txtCorreoA,txtcelularA,txtNombre,txtdni;
    SessionManager sessionManager;
    Button btnRegi,cambiar_contra;
    String tipo="";
    String dninew="",dni11="",dni22="",factor="";

    public MisDatosFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final globales d = new globales();
        rootView = inflater.inflate(R.layout.fragment_mis_datos, container, false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin();
        HashMap<String, String> user=sessionManager.getUserDetail();

        txtNombre=(EditText) rootView.findViewById(R.id.txtNombre);
        txtapePatA=(EditText) rootView.findViewById(R.id.txtapePat);
        txtapeMatA=(EditText) rootView.findViewById(R.id.txtapeMat);
        txtdni=(EditText) rootView.findViewById(R.id.txtdni);
        txtcelularA=(EditText) rootView.findViewById(R.id.txtcelular);
        txtCorreoA=(EditText) rootView.findViewById(R.id.txtCorreo);
        btnRegi= rootView.findViewById(R.id.btnRegistar);
        cambiar_contra= rootView.findViewById(R.id.btncambiar);
        txtNombre.setText(user.get(sessionManager.NOMBRES));
        txtapePatA.setText(user.get(sessionManager.APE_PAT));
        txtapeMatA.setText(user.get(sessionManager.APE_MAT));
        txtCorreoA.setText(user.get(sessionManager.EMAIL));
        txtcelularA.setText(user.get(sessionManager.CELULAR));
        txtdni.setText(user.get(sessionManager.DNI));
        tipo=user.get(sessionManager.TIPO);

        txtcelularA.setEnabled(false);
        txtCorreoA.setEnabled(false);

        txtdni.setEnabled(false);
        txtcelularA.setEnabled(false);
        txtCorreoA.setEnabled(false);

        cambiar_contra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                builder.setTitle("Ingrese la nueva contraseña");
                builder.setCancelable(false);
                final View  dialoView= inflater.inflate(R.layout.dialogo, null);
                builder.setView(dialoView);
                final EditText dni1=(EditText) dialoView.findViewById(R.id.dni1);
                final EditText dni2=(EditText) dialoView.findViewById(R.id.dni2);
                final TextView actualiza=(TextView) dialoView.findViewById(R.id.actualizar);
                final TextView error=(TextView) dialoView.findViewById(R.id.error);
               actualiza.setOnClickListener( new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        factor = "0";
                        int cantDni1 = dni1.getText().toString().length();
                        int cantDni2 = dni2.getText().toString().length();
                        if (cantDni1 <=0 && cantDni2 <= 0) {
                            Toast.makeText(getContext(), "Debe completar ambos campos.", Toast.LENGTH_SHORT).show();
                        } else {
                            dni11 = dni1.getText().toString();
                            dni22 = dni2.getText().toString();

                            if (dni11.equals(dni22)) {
                                dninew = dni11;
                                error.setVisibility(View.INVISIBLE);
                                final ProgressDialog progress = new ProgressDialog(getContext());
                                progress.setTitle("Actualizando");
                                progress.show();
                                Runnable progressRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.cancel();
                                        ejecutarServicio2(d.url + "update_pass");

                                        Intent i= new Intent(getContext(),MainActivity.class);
                                        startActivity(i);
                                    }
                                };
                                Handler pdCanceller = new Handler();
                                pdCanceller.postDelayed(progressRunnable, 1000);

                            } else {
                                builder.setTitle("");
                                dni2.setError("Las contraseñas no coinciden");
                               dni2.requestFocus();
                                Toast.makeText(getContext(), "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();

                                builder.create();
                            }

                        }
                    }
                });

                builder.setNegativeButton("Cancelar ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancelaste la actualización", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                builder.create();

            }
        });
        btnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNombre.setEnabled(true);
                txtapePatA.setEnabled(true);
                txtapeMatA.setEnabled(true);
                if(txtNombre.getText().toString().equals("") || txtapePatA.getText().toString().equals("") || txtapeMatA.getText().toString().equals("")){
                    Toast.makeText(getContext(), "No puede Grabar con los campos Vacios", Toast.LENGTH_SHORT).show();
                }else{
                    String nom=txtNombre.getText().toString();
                    nom.trim();
                    String apepata=txtapePatA.getText().toString();
                    apepata.trim();
                    String apemata=txtapeMatA.getText().toString();
                    apemata.trim();

                    if(validarletras(nom)!=0 || txtNombre.getText().toString().equals("null")){
                        Toast.makeText(getContext(), "El nombre no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                    }else if(validarletras(apepata)!=0 || txtapePatA.getText().toString().equals("null")){
                        Toast.makeText(getContext(), "El Apellido Paterno no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                    }else if(validarletras(apemata)!=0 || txtapeMatA.getText().toString().equals("null")){
                        Toast.makeText(getContext(), "El Apellido Materno no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                    }else{

                        ejecutarServicio(d.url+"update?");
                    }
                }
            }
        });

        return  rootView;
    }
    public int validarletras(String texto){
        int rpta=0;
        for(int i=0;i<texto.length();i++){
            char caracter = texto.toUpperCase().charAt(i);
            int valorASCII = (int)caracter;
            if (valorASCII != 165 && valorASCII !=32 && (valorASCII < 65 || valorASCII > 90)){
                rpta=1;
            }
        }
        return rpta;
    }

    private void ejecutarServicio(final String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        try {
            Map<String, String> parametros = new HashMap<String, String>();

            parametros.put("nro_doc_ide", txtdni.getText().toString());
            parametros.put("nombre", txtNombre.getText().toString());
            parametros.put("ape_pat", txtapePatA.getText().toString());
            parametros.put("ape_mat", txtapeMatA.getText().toString());

            JSONObject jsonObj = new JSONObject(parametros);
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject r = response;
                                String msn = r.getString("msg");
                                Toast.makeText(getContext(), "" + msn, Toast.LENGTH_SHORT).show();
                                txtNombre = (EditText) rootView.findViewById(R.id.txtNombre);
                                txtapePatA = (EditText) rootView.findViewById(R.id.txtapePat);
                                txtapeMatA = (EditText) rootView.findViewById(R.id.txtapeMat);
                                txtdni = (EditText) rootView.findViewById(R.id.txtdni);
                                txtcelularA = (EditText) rootView.findViewById(R.id.txtcelular);
                                txtCorreoA = (EditText) rootView.findViewById(R.id.txtCorreo);
                                String ape = txtapePatA.getText().toString();
                                ape.trim();
                                String mat = txtapeMatA.getText().toString();
                                mat.trim();

                                String nom = txtNombre.getText().toString();
                                String nomb = nom.trim();

                                sessionManager.createSession(txtCorreoA.getText().toString(), ape, mat, txtdni.getText().toString(), nomb, txtcelularA.getText().toString(), "", "", "","","","", tipo);

                                Intent i = new Intent(getContext(), MainActivity.class);

                        

                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void ejecutarServicio2(final String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
     try{
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo",txtCorreoA.getText().toString());
        parametros.put("telefono",txtcelularA.getText().toString());
        parametros.put("nro_doc_ide",dninew);
        parametros.put("factor",factor);

        JSONObject jsonObj = new JSONObject(parametros);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            JSONObject r = response;
                            String error = r.getString("error");
                            String msn = r.getString("msg");
                            if (error.equals("true") && factor.equals("0")) {
                                Toast.makeText(getContext(), msn, Toast.LENGTH_SHORT).show();

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(getActivity(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
                jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(jsonObjRequest);
        }catch (Exception e){
        e.printStackTrace();
    }

}

}
