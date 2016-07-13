package mx.places.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class Place implements Serializable{

    private int idCat;

    @SerializedName("fnId")
    private int id;

    @SerializedName("fcNombre")
    private String name;

    @SerializedName("fcHorario")
    private String schedule;

    @SerializedName("fncalificacion")
    private String ranking;

    @SerializedName("fcDireccion")
    private String address;

    @SerializedName("fcCoordenadas")
    private String coordinates;

    private String distance;

    @SerializedName("fcTelefono1")
    private String tel1;

    @SerializedName("fcTelefono2")
    private String tel2;

    @SerializedName("fcTelefono3")
    private String tel3;

    @SerializedName("fcTelefono4")
    private String tel4;

    @SerializedName("fcResponsable1")
    private String resp1;

    @SerializedName("fcResponsable2")
    private String resp2;

    public Place () {}

    public Place(String name, String schedule, String distance, String ranking) {
        this.name = name;
        this.schedule = schedule;
        this.distance = distance;
        this.ranking = ranking;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
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

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getTel4() {
        return tel4;
    }

    public void setTel4(String tel4) {
        this.tel4 = tel4;
    }

    public String getResp1() {
        return resp1;
    }

    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    public String getResp2() {
        return resp2;
    }

    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    public String getAllRespon(){
        StringBuilder builder = new StringBuilder();

        if (getResp1()!= null) {
            builder.append(getResp1() + " ");
        }
        if (getResp2() != null) {
            builder.append(getResp2());
        }
        System.out.println("getAllRespon :" + builder.toString());
        return builder.toString();
    }

    public String getAllPhones(){
        StringBuilder stringBuilder = new StringBuilder();

        if (getTel1()!= null) {
            stringBuilder.append(getTel1() + " ");
        }

        if ( getTel2()!= null) {
            stringBuilder.append(getTel2() + " ");
        }
        if ( getTel3()!= null) {
            stringBuilder.append(getTel3() + " ");
        }
        if (getTel4()!= null) {
            stringBuilder.append(getTel4());
        }
        System.out.println("getAllPhones :" + stringBuilder.toString());
        return stringBuilder.toString();
    }
}
