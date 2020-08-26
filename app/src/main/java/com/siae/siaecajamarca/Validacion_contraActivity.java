package com.siae.siaecajamarca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion_contraActivity extends AppCompatActivity {

    EditText correo, celular, dni, contra1, contra2;
    LinearLayout con1, con2, vali_cambiar;
    TextView vali_pass_cambio;
    String factor = "", dni1 = "", dni2 = "";
    int cantDni1, cantDni2;
    String dninew = "";
    String coss="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_validacion_contra);
        final globales d = new globales();

        Intent i = getIntent();

        correo = (EditText) findViewById(R.id.Correo);
        celular = (EditText) findViewById(R.id.Celular);
        dni = (EditText) findViewById(R.id.Dni);

        contra1 = (EditText) findViewById(R.id.contra1);
        contra2 = (EditText) findViewById(R.id.contra2);
        con1 = (LinearLayout) findViewById(R.id.con1);
        con2 = (LinearLayout) findViewById(R.id.con2);
        vali_cambiar = (LinearLayout) findViewById(R.id.btnValidar);
        vali_pass_cambio = (TextView) findViewById(R.id.vali_cambio);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, 0);
        con1.setLayoutParams(lp1);
        con2.setLayoutParams(lp1);

        vali_cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correo.getText().toString().equals("") || celular.getText().toString().equals("") || dni.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    int cantDni = dni.getText().toString().length();
                    int cantelefono = celular.getText().toString().length();
                    int primerDigitoTelefono = Integer.parseInt(celular.getText().toString().substring(0, 1));
                    String co = correo.getText().toString();

                    String cos=co.trim();
                            coss=cos.toLowerCase();
                    if (cantDni != 8) {
                        Toast.makeText(getApplicationContext(), "El dni solo puede contener 8 caracteres.", Toast.LENGTH_SHORT).show();
                    } else if (cantelefono != 9) {
                        Toast.makeText(getApplicationContext(), "El celular solo pude tener 9 digitos.", Toast.LENGTH_SHORT).show();
                    } else if (primerDigitoTelefono != 9) {
                        Toast.makeText(getApplicationContext(), "El celular debe iniciar con el numero 9.", Toast.LENGTH_SHORT).show();
                    } else if (!isEmailValid(coss)) {
                        Toast.makeText(getApplicationContext(), "Debe de ingresar un formato correcto de Correo Electronico", Toast.LENGTH_SHORT).show();
                    } else {

                        if (vali_pass_cambio.getText().equals("VALIDAR")) {

                            factor = "1";
                            final ProgressDialog progress = new ProgressDialog(Validacion_contraActivity.this);
                            progress.setTitle("Validando");
                            progress.setMessage("Estamos validando sus datos");
                            progress.show();
                            Runnable progressRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    progress.cancel();
                                    ejecutarServicio(d.url + "update_pass");

                                }
                            };
                            Handler pdCanceller = new Handler();
                            pdCanceller.postDelayed(progressRunnable, 1000);

                        }
                        if (vali_pass_cambio.getText().equals("CAMBIAR CONTRASEÑA")) {

                            factor = "0";
//                            cantDni1 = contra1.getText().toString().length();
//                            cantDni2 = contra2.getText().toString().length();
//                            if (cantDni1 != 8 && cantDni2 != 8) {
//                                Toast.makeText(getApplicationContext(), "El dni solo puede contener 8 caracteres.", Toast.LENGTH_SHORT).show();
//                            } else {
                                dni1 = contra1.getText().toString();
                                dni2 = contra2.getText().toString();

                                if (dni1.equals(dni2)) {
                                        if(!dni1.equals("") && !dni2.equals("")){
                                            dninew = dni1;
                                            final ProgressDialog progress = new ProgressDialog(Validacion_contraActivity.this);
                                            progress.setTitle("Actualizando");
                                            progress.show();
                                            Runnable progressRunnable = new Runnable() {
                                                @Override
                                                public void run() {
                                                    progress.cancel();
                                                    ejecutarServicio(d.url + "update_pass");

                                                }
                                            };
                                            Handler pdCanceller = new Handler();
                                            pdCanceller.postDelayed(progressRunnable, 1000);

                                        }else{
                                            Toast.makeText(Validacion_contraActivity.this, "Ingrese las contraseñas.", Toast.LENGTH_SHORT).show();

                                        }

                                } else {
                                    Toast.makeText(Validacion_contraActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                               // }

                            }
                        }

                    }

                }
            }
        });

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    private void ejecutarServicio(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        try{
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo", coss);
        parametros.put("telefono", celular.getText().toString());
        if (factor.equals("1")) {
            parametros.put("nro_doc_ide", dni.getText().toString());
        } else if (factor.equals("0")) {
            parametros.put("nro_doc_ide", dninew);
        }
        parametros.put("factor", factor);

        JSONObject jsonObj = new JSONObject(parametros);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject r = response;
                            String error = r.getString("error");
                            String msn = r.getString("msg");

                            if (error.equals("true") && msn.equals("validado")) {
                                vali_pass_cambio.setTextSize(20);
                                vali_pass_cambio.setText("CAMBIAR CONTRASEÑA");

                                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                con1.setLayoutParams(lp1);
                                con2.setLayoutParams(lp1);
                                correo.setEnabled(false);
                                celular.setEnabled(false);
                                dni.setEnabled(false);
                                Toast.makeText(Validacion_contraActivity.this, "Usuario encontrado", Toast.LENGTH_SHORT).show();
                            } else if (error.equals("false") && msn.equals("no validado")) {
                                Toast.makeText(Validacion_contraActivity.this, "Ingrese los datos correctos", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Validacion_contraActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                            }
                            if (error.equals("true") && factor.equals("0")) {
                                Toast.makeText(getApplicationContext(), msn, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Validacion_contraActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                       } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);
    }catch (Exception e){
            e.printStackTrace();
        }
}
}
