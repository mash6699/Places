package mx.places.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class Place implements Serializable{

    @SerializedName("fnId")
    private int id;
    @SerializedName("fcNombre")
    private String name;
    @SerializedName("fcHorario")
    private String schedule;
    @SerializedName("fcDireccion")
    private String address;
    @SerializedName("fcCoordenadas")
    private String coordinates;

    @SerializedName("")
    private String distance;
    @SerializedName("")
    private String ranking;




    public Place () {}

    public Place(String name, String schedule, String distance, String ranking) {
        this.name = name;
        this.schedule = schedule;
        this.distance = distance;
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
