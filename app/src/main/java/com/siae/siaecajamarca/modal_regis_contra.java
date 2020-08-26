package com.siae.siaecajamarca;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class modal_regis_contra extends AppCompatDialogFragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        final globales d = new globales();

        LayoutInflater inflater =  getActivity().getLayoutInflater();
         final View view = inflater.inflate(R.layout.dialogo_register , null);

        builder.setView(view)
                .setTitle("Ingrese su nueva Contraseña")
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        editTextPassword.setError("no coinciden las contraseñas");
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        //ejecutarServicio2(d.url + "update_pass");
                        //Toast.makeText(getContext(), ""+username+" "+password, Toast.LENGTH_SHORT).show();

                        listener.applyTexts(username, password);
                    }
                });

        editTextUsername = view.findViewById(R.id.dni1);
        editTextPassword = view.findViewById(R.id.dni2);

        return builder.create();

    }
/*
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

*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }
}
