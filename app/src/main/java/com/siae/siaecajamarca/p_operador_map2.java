package com.siae.siaecajamarca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;


public class p_operador_map2 extends Fragment {
    private View rootview;
    public WebView miVisorWeb;
    public p_operador_map2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_p_operador_map2, container, false);
        miVisorWeb = (WebView) rootview.findViewById(R.id.mapa);
        miVisorWeb.setWebViewClient(new WebViewClient());
        miVisorWeb.getSettings().setJavaScriptEnabled(true);
        miVisorWeb.setVerticalScrollBarEnabled(true);
        miVisorWeb.loadUrl("http://siae.colaboraccion.pe/app_reportes.php?dni=11121314");
       // miVisorWeb.loadUrl("http://192.168.4.8/siae__/analista.php");

        return rootview;
    }


}
