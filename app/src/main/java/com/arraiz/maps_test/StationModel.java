package com.arraiz.maps_test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationModel {


    @SerializedName("anclajes_averiados")
    @Expose
    private String anclajesAveriados;

    @SerializedName("anclajes_libres")
    @Expose
    private String anclajesLibres;

    @SerializedName("anclajes_usados")
    @Expose
    private String anclajesUsados;

    @SerializedName("bicis_averiadas")
    @Expose
    private String bicisAveriadas;

    @SerializedName("bicis_libres")
    @Expose
    private String bicisLibres;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String lon;
    @SerializedName("nombre")
    @Expose
    private String nombre;


    public String getAnclajesAveriados() {
        return anclajesAveriados;
    }

    public void setAnclajesAveriados(String anclajesAveriados) {
        this.anclajesAveriados = anclajesAveriados;
    }

    public String getAnclajesLibres() {
        return anclajesLibres;
    }

    public void setAnclajesLibres(String anclajesLibres) {
        this.anclajesLibres = anclajesLibres;
    }

    public String getAnclajesUsados() {
        return anclajesUsados;
    }

    public void setAnclajesUsados(String anclajesUsados) {
        this.anclajesUsados = anclajesUsados;
    }

    public String getBicisAveriadas() {
        return bicisAveriadas;
    }

    public void setBicisAveriadas(String bicisAveriadas) {
        this.bicisAveriadas = bicisAveriadas;
    }

    public String getBicisLibres() {
        return bicisLibres;
    }

    public void setBicisLibres(String bicisLibres) {
        this.bicisLibres = bicisLibres;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {

        return "StationModel{" +
                "anclajesAveriados='" + anclajesAveriados + '\'' +
                ", anclajesLibres='" + anclajesLibres + '\'' +
                ", anclajesUsados='" + anclajesUsados + '\'' +
                ", bicisAveriadas='" + bicisAveriadas + '\'' +
                ", bicisLibres='" + bicisLibres + '\'' +
                ", estado='" + estado + '\'' +
                ", id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", nombre='" + nombre + '\'' +
                '}'+"\n";
    }
}
