package com.siae.siaecajamarca;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class p_operador_map extends AppCompatActivity {
    WebView webView;

    String url = "http://192.168.4.8/siae__/app_reportes.php";
    WebView miVisorWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_operador_map);
        miVisorWeb = (WebView) findViewById(R.id.mapa);
        miVisorWeb.setWebViewClient(new WebViewClient());
        miVisorWeb.getSettings().setJavaScriptEnabled(true);
        miVisorWeb.setVerticalScrollBarEnabled(true);
        miVisorWeb.loadUrl("http://siae.colaboraccion.pe/app_reportes.php?dni=11121314");

        // = (WebView) findViewById(R.id.mapa);

//        WebView myWebView = (WebView) findViewById(R.id.webview);
//        myWebView.loadUrl("http://www.example.com");

       // webView.loadUrl(url);

    }
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_mis_incidendias, container, false);
//        sessionManager = new SessionManager(getActivity().getApplicationContext());
//        user=sessionManager.getUserDetail();
///*
//        listView =(ListView)rootView. findViewById(R.id.listView);
//        MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(), mTitle, mDescription, images);
//        listView.setAdapter(adapter);
//*/
//        // View view = inflater.inflate(R.layout.fragment_web_view_fragment15, container, false);
//        String dni=user.get(sessionManager.DNI);
//        miVisorWeb = (WebView) rootView.findViewById(R.id.web);
//        miVisorWeb.setWebViewClient(new WebViewClient());
//        miVisorWeb.getSettings().setJavaScriptEnabled(true);
//        miVisorWeb.setVerticalScrollBarEnabled(true);
//        miVisorWeb.loadUrl("http://siae.colaboraccion.pe/app_reportes.php?dni="+dni);
//        return rootView;
//    }
}
