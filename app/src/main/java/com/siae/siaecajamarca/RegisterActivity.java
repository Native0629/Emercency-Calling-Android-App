package com.siae.siaecajamarca;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity  implements modal_regis_contra.ExampleDialogListener{
    EditText celular,dni,correo, txtnom, codigov;
    LinearLayout nombres,codigo;
    String coss="";
    TextView bloginn;
    private CircleImageView circleImageView;
    private LoginButton loginButton;
    private Button fb_reg;
    private CallbackManager callbackManager;
    globales d = new globales();
    String dni1, dni2;
    public static String first_n, last_n,email_n,id_n, url_imgn, origen_n=null;
    String error="",msn="";
    String token="";
    String flag="";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final globales d = new globales();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        correo = findViewById(R.id.txtcorreo);
        dni = findViewById(R.id.txtdni);
        celular = findViewById(R.id.txtcelular);
        bloginn = (TextView) findViewById(R.id.tvLogin);
        circleImageView = findViewById(R.id.img);
        txtnom= findViewById(R.id.txtnombres);
        //txtape = findViewById(R.id.txtapellidos);
        codigo= findViewById(R.id.codigo);
        nombres= findViewById(R.id.nom_fb);
       // apellidos= findViewById(R.id.ape_fb);
        fb_reg= (Button) findViewById(R.id.fb);
        codigov= findViewById(R.id.codigov);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, 0);
        nombres.setLayoutParams(lp1);
        codigo.setLayoutParams(lp1);


        //apellidos.setLayoutParams(lp1);

        // callbackManager= CallbackManager.Factory.create();
        //loginButton.setReadPermissions(Arrays.asList( "email","public_profile"));

        //callbackManager= CallbackManager.Factory.create();

        Intent i= getIntent();
               first_n=i.getStringExtra("first_name");
               last_n=i.getStringExtra("last_name");
               email_n=i.getStringExtra("email");
               id_n=i.getStringExtra("idorigen");
               url_imgn=i.getStringExtra("url_img");
               origen_n=i.getStringExtra("origen");
        //Toast.makeText(this, ""+first_n+last_n+email_n+id_n+url_imgn+origen_n, Toast.LENGTH_SHORT).show();
        //Intent o= getIntent();
//        String flag2= o.getStringExtra("lat");
//        if(flag2.equals("1")){
//
//
//        }
    //ejecutarsserviciofb(d.url + "loginfbs");

               if(Objects.equals(origen_n, "fb")){
                   lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                   nombres.setLayoutParams(lp1);
                   //apellidos.setLayoutParams(lp1);
                   txtnom.setText(first_n+" "+last_n);
                   correo.setText(email_n);

                       //txtape.setText(last_n);
                   Glide.with(RegisterActivity.this).load(url_imgn).into(circleImageView);
               }
                bloginn.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View v) {
                        if(dni.getText().toString().equals("") || celular.getText().toString().equals("") || correo.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(), "Debes ingresar todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    int cantDni=dni.getText().toString().length();
                    int cantelefono=celular.getText().toString().length();
                    int primerDigitoTelefono=Integer.parseInt(celular.getText().toString().substring(0,1));
                    String co=correo.getText().toString();
                    String cos=co.trim();
                    coss=cos.toLowerCase();
                    if(cantDni!=8){
                        Toast.makeText(getApplicationContext(), "El dni solo puede contener 8 caracteres.", Toast.LENGTH_SHORT).show();
                    }else if(cantelefono!=9){
                        Toast.makeText(getApplicationContext(), "El celular solo pude tener 9 digitos.", Toast.LENGTH_SHORT).show();
                    }else if(primerDigitoTelefono!=9){
                        Toast.makeText(getApplicationContext(), "El celular debe iniciar con el numero 9.", Toast.LENGTH_SHORT).show();
                    }else if(!isEmailValid(coss)){
                        Toast.makeText(getApplicationContext(),"Debe de ingresar un formato correcto de Correo Electronico", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        if(Objects.equals(origen_n, "fb")){
                            ejecutarsserviciofb(d.url + "createfb");
                           // Toast.makeText(RegisterActivity.this, "sss"+origen_n, Toast.LENGTH_SHORT).show();
                        }else{

                            if(msn.equals("") || codigov.getText().toString().equals("")){
                                ejecutarServicioverificador(d.url + "update_pass");

                            }else if(!msn.equals("")){
//                                if(bloginn.getText().toString().equals("Registrate")){
                                    if (msn.equals("no validado") && flag.equals("1")) {
                                        //openDialog();
                                        ejecutarServicio(d.url + "create");

/*
                                        if(!dni1.equals("") && !dni2.equals("")) {
                                            ejecutarServicio(d.url + "create");
                                        }
*/

                                    }

  //                              }
                                if (codigov.getText().toString().equals("" + token)){
                                    bloginn.setText("REGISTRATE");
                                    int view = 0;
                                    Snackbar.make(v, "El codigo de verificación coincide.", Snackbar.LENGTH_LONG)
                                            .show();
                                        flag="1";
                                        codigov.setEnabled(false);

                                }else{
                                    Toast.makeText(RegisterActivity.this, "Codigo de verificación no coincide", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        //Descomentar cuando tengamos el endpoint

                    }
                }
            }
        });
    }

    public String token(){

        token= String.valueOf(Math.random());
        token= token.substring(token.length()-8);
        token= token.substring(0, 3)+token.substring(3, 6);
    return token;
    }
    private void ejecutarServicioverificador(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);
        try {
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("correo",coss);
            parametros.put("nro_doc_ide",dni.getText().toString());
            parametros.put("telefono",celular.getText().toString());
            parametros.put("factor","2");

            //parametros.put("pass", dni.getText().toString());
            JSONObject jsonObj = new JSONObject(parametros);
            JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>()
                    {

                        @Override
                        public void onResponse(JSONObject response)
                        {
                            try {
                                JSONObject r=response;
                                error = r.getString("error");
                                msn = r.getString("msg");
                                final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                                progress.setTitle("Registrando");
                                progress.setMessage("Verificando si sus datos no se encuentren registrados");
                                progress.show();

                                if (error.equals("true") && msn.equals("validado")) {
                                    Runnable progressRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.cancel();
                                        }
                                    };
                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 500);
                                    Toast.makeText(RegisterActivity.this, "Ud. Ya se encuentra registrado", Toast.LENGTH_LONG).show();

                                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                    i.putExtra("celular",celular.getText().toString());
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(RegisterActivity.this, "Ud. Ya se encuentra registrado", Toast.LENGTH_LONG).show();

                                } else if (error.equals("false") && msn.equals("no validado")) {
                                    correo.setEnabled(false);
                                    dni.setEnabled(false);
                                    celular.setEnabled(false);

                                    token();
                                    sendMail();
                                    Runnable progressRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.cancel();
                                        }
                                    };

                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 1000);
                                    Toast.makeText(RegisterActivity.this, "Se ha enviado el codigo de validación a tu correo", Toast.LENGTH_SHORT).show();

                                    LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        codigo.setLayoutParams(p1);



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

                                    Toast.makeText(getApplicationContext(), "error en la conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendMail() {

        //String mensaje=msn.getText().toString().trim();
        JavaMailAPI javaMailAPI = new JavaMailAPI(getApplicationContext(),
                correo.getText().toString(),
                "Código de verificacion SIAE - Cajamarca",
                Html.fromHtml("<h1>Estimado(a) ciudadano: <br>Gracias por registrarte en el aplicativo móvil SIAE - Cajamarca, para finalizar con el <br> registro debes ingresar el siguiente código de verificación en el formulario de registro <br>del aplicativo móvil: <br><br>"
                        +token+"<br>Atentamente<br>SIAE Cajamarca</h1>")

        );
        //msn.setText("");
        javaMailAPI.execute();

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



    //ejecuta el envio hacia el servidor para crear el usuario
    private void ejecutarServicio(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);
        try {
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("correo",coss);
        parametros.put("nro_doc_ide",dni.getText().toString());
        parametros.put("telefono",celular.getText().toString());
        parametros.put("pass", dni.getText().toString());
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
                            final String msn=r.getString("msg");

                            final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                            progress.setTitle("Registrando");
                            progress.setMessage("Verificando si sus datos no se encuentren registrados");
                            progress.show();

                            if(error.equals("true")){

                                Runnable progressRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        progress.cancel();
                                        Toast.makeText(getApplicationContext(),""+msn, Toast.LENGTH_LONG).show();
                                    }
                                };
                                Handler pdCanceller = new Handler();
                                pdCanceller.postDelayed(progressRunnable, 2000);
                                //Toast.makeText(RegisterActivity.this, "ha entrado aqui", Toast.LENGTH_SHORT).show();

                                mostrarDialog();
                                //openDialog();

                            }else{
                                progress.cancel();
                                Toast.makeText(getApplicationContext(), msn, Toast.LENGTH_LONG).show();
                                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                                i.putExtra("celular",celular.getText().toString());
                                startActivity(i);
                                finish();
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

                                Toast.makeText(getApplicationContext(), "error en la conexión", Toast.LENGTH_SHORT).show();
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
    public void openDialog() {
        modal_regis_contra exampleDialog = new modal_regis_contra();
        exampleDialog.show(getSupportFragmentManager(), "dialogo");


    }


    public void mostrarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        if(Objects.equals(origen_n, "fb")){
            builder.setTitle("Registro Exitoso");
//        builder.setMessage("Estimado ciudadano, recuerda que:")

            builder.setMessage(Html.fromHtml( "<font size=1>Estimado ciudadano, recuerda que: <br><br>"+
                    "Tu usuario es tu celular: "+celular.getText().toString() +"<br><br>"+"Tu clave alterna es tu dni: </font>" +
                    dni.getText().toString())).
                    setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i=new Intent(RegisterActivity.this, MainActivity.class);
                            //.putExtra("celular",celular.getText().toString());

                            startActivity(i);
                            finish();
                        }
                    }).show();


        }else {

            builder.setTitle("Registro Exitoso");
//        builder.setMessage("Estimado ciudadano, recuerda que:")

            builder.setMessage(Html.fromHtml( "<font size=1>Estimado ciudadano, recuerda que: <br><br>"+
                    "Tu usuario es tu celular: "+celular.getText().toString() +"<br><br>"+"Tu clave es: </font>" +
                    dni.getText().toString())).
                    setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i=new Intent(RegisterActivity.this, LoginActivity.class);
                            i.putExtra("celular",celular.getText().toString());

                            startActivity(i);
                            finish();
                        }
                    }).show();
        }


    }
    public void ejecutarsserviciofb(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);
        try {
            Map<String, String> parametros = new HashMap<String, String>();

            String ape_pat[]=last_n.split(" ");
            Toast.makeText(this, "ss"+ape_pat[0]+" "+ape_pat[1],Toast.LENGTH_SHORT).show();
            parametros.put("nro_doc_ide",dni.getText().toString());
            parametros.put("telefono",celular.getText().toString());
            parametros.put("correo",email_n);
            parametros.put("nombres",first_n);
            parametros.put("ape_pat",ape_pat[0]);
            parametros.put("ape_mat",ape_pat[1]);
            parametros.put("idorigen",id_n);
            parametros.put("origen",origen_n);
            parametros.put("pass",dni.getText().toString());


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
                                final String msn=r.getString("msg");

                                final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                                progress.setTitle("Registrando");
                                progress.setMessage("Verificando si sus datos no se encuentren registrados");
                                progress.show();

                                if(error.equals("true")){

                                    Runnable progressRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            progress.cancel();
                                            Toast.makeText(getApplicationContext(),""+msn, Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    Handler pdCanceller = new Handler();
                                    pdCanceller.postDelayed(progressRunnable, 2000);

                                    //
                                    mostrarDialog();
                                    //
                                }else{
                                    progress.cancel();
                                    Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                                    i.putExtra("celular",celular.getText().toString());
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(getApplicationContext(), msn, Toast.LENGTH_SHORT).show();
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

                                    Toast.makeText(getApplicationContext(), "error en la conexión", Toast.LENGTH_SHORT).show();
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
    public void applyTexts(String username, String password) {

        dni1=username;
        dni2=password;
        if(!dni1.equals("") && !dni2.equals("")) {
            //Toast.makeText(this, "Vamos a crear"+" "+dni1, Toast.LENGTH_SHORT).show();
            ejecutarServicio(d.url + "create");
            mostrarDialog();
        }
    }


}
