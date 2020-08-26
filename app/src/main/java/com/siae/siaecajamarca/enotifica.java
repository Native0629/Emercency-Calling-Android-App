package com.siae.siaecajamarca;

public class enotifica {

private String tipos;
private String titulo;
private String descripcion;

    public enotifica(String tipos, String titulo, String descripcion) {
        this.tipos = tipos;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTipos() {
        return tipos;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
