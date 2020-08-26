package com.siae.siaecajamarca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager_video {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN="IS_LOGIN";

    public static final String RVIDEO="RVIDEO";
    public static final String RlVIDEO="RLVIDEO";

    public SessionManager_video(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }
    public void createSession_video(String nvideo,String nlvideo){
        editor.putBoolean(LOGIN,true);


        editor.putString(RVIDEO, nvideo);
        editor.putString(RlVIDEO, nlvideo);
        editor.apply();
    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if(!this.isLoggin()){
            Intent i =new Intent(context,MainActivity.class);
            context.startActivity(i);
            ((MainActivity)context).finish();
        }
    }

    public HashMap<String,String>getUserDetail_l(){
        HashMap<String, String>userl=new HashMap<>();
        //user.put(NAME,sharedPreferences.getString(NAME,"no"));

        userl.put(RVIDEO,sharedPreferences.getString(RVIDEO,""));
        userl.put(RlVIDEO, sharedPreferences.getString(RlVIDEO, ""));
        return userl;
    }

   public void logout(){
        editor.clear();
        editor.commit();
        Intent i =new Intent(context,StarterActivity.class);
        context.startActivity(i);
        ((MainActivity)context).finish();
    }
}
