package com.siae.siaecajamarca;

public class globales {
    String ip="192.168.4.8";
    String ip2="95.217.44.43";

    String url = "http://"+ip2+":8000/api/ciudadano/";
    String url2 = "http://"+ip2+"/socket_sae/incidentes/emit_incidente.php?";
    String url3 = "http://"+ip2+"/socket_sae/sos/emit_sos.php?";

    String url4 = "http://"+ip2+"/socket_sae/multimedia/emit_audio.php";
    String url5 = "http://"+ip2+"/socket_sae/multimedia/emit_video.php";


    String ambulancia = "(076)341245";
    String bomberos = "(076)366787";
    String serenazgos = "(076)361711";
    String nvide = "";

    public globales() {
    }

    public String getNvide() {
        return nvide;
    }

    public void setNvide(String nvide) {
        this.nvide = nvide;
    }
}

