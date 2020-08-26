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

public class ValidacionActivity extends AppCompatActivity {
    EditText nombre,apepat,apemat,nombre2,apepat2,apemat2;
    TextView tv_name1;
    LinearLayout registrar;
    SessionManager sessionManager;
    public  static  String dniEnvio,celular,correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_validacion);
        final globales d= new globales();

        Intent i=getIntent();
        celular=i.getStringExtra("celular");
        dniEnvio=i.getStringExtra("dni");
        correo=i.getStringExtra("correo");

        sessionManager = new SessionManager(this);

        nombre=(EditText) findViewById(R.id.eTxNombre);
        apepat=(EditText) findViewById(R.id.eTxapePat);
        apemat=(EditText) findViewById(R.id.etxapeMat);

        tv_name1=(TextView) findViewById(R.id.tv_name);
        tv_name1.setText(celular);

        registrar=(LinearLayout) findViewById(R.id.btnRegistar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombre.getText().toString().equals("") || apepat.getText().toString().equals("") || apemat.getText().toString().equals("") || nombre.getText().toString().equals("null")){
                    Toast.makeText(getApplicationContext(), "No puede Grabar con los campos Vacios", Toast.LENGTH_SHORT).show();
                }else{
                    String nom=nombre.getText().toString();
                    nom.trim();
                    String apepata=apepat.getText().toString();
                    apepata.trim();
                    String apemata=apemat.getText().toString();
                    apemata.trim();

                    if(validarletras(nom)!=0 || nom.equals("null")){
                        Toast.makeText(getApplicationContext(), "El nombre no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                            //nombre.setError("asdasdasdasda");
                    }else if(validarletras(apepata)!=0 || apepata.equals("null")){
                        Toast.makeText(getApplicationContext(), "El Apellido Paterno no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                    }else if(validarletras(apemata)!=0 || apemata.equals("null")){
                        Toast.makeText(getApplicationContext(), "El Apellido Materno no puede contener caracteres especiales", Toast.LENGTH_SHORT).show();
                    }else{
                        final ProgressDialog progress = new ProgressDialog(ValidacionActivity.this);
                        progress.setTitle("Comprobando");
                        progress.show();
                        Runnable progressRunnable = new Runnable() {

                            @Override
                            public void run() {
                                progress.cancel();
                                 ejecutarServicio(d.url+"update");
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 2000);
                    }
                }
            }
        });
    }
    public int validarletras(String texto){
        int rpta=0;
        for(int i=0;i<texto.length();i++){
            char caracter = texto.toUpperCase().charAt(i);
            int valorASCII = (int)caracter;
            if (valorASCII != 165  && valorASCII !=32 &&(valorASCII < 65 || valorASCII > 90)){
                rpta=1;
            }
        }
        return rpta;
    }
    private void ejecutarServicio(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        try{
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("nro_doc_ide",dniEnvio);
        parametros.put("nombre",nombre.getText().toString());
        parametros.put("ape_pat",apepat.getText().toString());
        parametros.put("ape_mat",apemat.getText().toString());

        nombre2=(EditText) findViewById(R.id.eTxNombre);
        apepat2=(EditText) findViewById(R.id.eTxapePat);
        apemat2=(EditText) findViewById(R.id.etxapeMat);
        sessionManager.createSession(correo,apepat2.getText().toString(),apemat2.getText().toString(), dniEnvio, nombre2.getText().toString(), celular,"","","","","","", "1");
        JSONObject jsonObj = new JSONObject(parametros);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            JSONObject r=response;
                            String error=r.getString("error");
                            String msn=r.getString("msg");
                            Toast.makeText(getApplicationContext(), "" + msn, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(ValidacionActivity.this, MainActivity.class);

                            startActivity(i);
                            finish();
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Bienvenido al sistema S.I.A.E. ", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                            }
                        });
        queue.add(jsonObjRequest);

    }catch (Exception e){
            e.printStackTrace();
        }
    }
}
