package com.siae.siaecajamarca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN="IS_LOGIN";

    public static final String EMAIL="EMAIL";
    public static final String APE_PAT="APE_PAT";
    public static final String APE_MAT="APE_MAT";
    public static final String DNI="DNI";
    public static final String NOMBRES = "NOMBRES";
    public static final String CELULAR = "CELULAR";
    public static final String MENSAJE = "MENSAJE";
    public static final String RAUDIO = "RAUDIO";
    public static final String RVIDEO = "RVIDEO";
    public static final String FB="FB";
    public static final String IDFB="IDFB";
    public static final String IMGFB="IMGFB";
    public  static final String TIPO="TIPO";


    public SessionManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }
    public void createSession(String email, String ape_pat, String ape_mat, String dni, String nombres, String celular, String mensaje, String raudio, String rvideo, String fb, String idfb, String imgfb, String tipo){
        editor.putBoolean(LOGIN,true);
        //editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(APE_PAT,ape_pat);
        editor.putString(APE_MAT,ape_mat);
        editor.putString(DNI,dni);
        editor.putString(NOMBRES, nombres);
        editor.putString(CELULAR, celular);
        editor.putString(MENSAJE, mensaje);
        editor.putString(RAUDIO, raudio);
        editor.putString(RAUDIO, rvideo);
        editor.putString(FB, fb);
        editor.putString(IDFB, idfb);
        editor.putString(IMGFB, imgfb);
        editor.putString(TIPO, tipo);

        editor.apply();
    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if(!this.isLoggin()){
            Intent i =new Intent(context,MainActivity.class);
            //context.startActivity(i);
            //((MainActivity)context).finish();
        }
    }

    public HashMap<String,String>getUserDetail(){
        HashMap<String, String>user=new HashMap<>();
        //user.put(NAME,sharedPreferences.getString(NAME,"no"));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(APE_PAT,sharedPreferences.getString(APE_PAT,null));
        user.put(APE_MAT,sharedPreferences.getString(APE_MAT,null));
        user.put(DNI,sharedPreferences.getString(DNI,""));
        user.put(NOMBRES,sharedPreferences.getString(NOMBRES,"no"));
        user.put(CELULAR,sharedPreferences.getString(CELULAR,""));
        user.put(MENSAJE, sharedPreferences.getString(MENSAJE, ""));
        user.put(RAUDIO, sharedPreferences.getString(RAUDIO, ""));
        user.put(RVIDEO, sharedPreferences.getString(RVIDEO, ""));
        user.put(FB, sharedPreferences.getString(FB, ""));
        user.put(IDFB, sharedPreferences.getString(IDFB, ""));
        user.put(IMGFB, sharedPreferences.getString(IMGFB, ""));
        user.put(TIPO,sharedPreferences.getString(TIPO, ""));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context,StarterActivity.class);
        context.startActivity(i);
        ((MainActivity)context).finish();
    }
}
