package com.konachy.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Achat implements Parcelable {
    public  static String LIST_ACHAT = "listAchats";

    private String nom ;
    private String quantite ;
    private float prix;
    private long date;

    public Achat() {
        prix = 0;
    }

    public Achat(String nom, String quantite, float prix, long date) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
        this.date = date;
    }

    protected Achat(Parcel in) {
        nom = in.readString();
        quantite = in.readString();
        prix = in.readFloat();
        date = in.readLong();
    }

    public static final Creator<Achat> CREATOR = new Creator<Achat>() {
        @Override
        public Achat createFromParcel(Parcel in) {
            return new Achat(in);
        }

        @Override
        public Achat[] newArray(int size) {
            return new Achat[size];
        }
    };

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public String getQuantite() {
        return quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public boolean achaValide(){
        if(!nom.equals("")&&prix!=0)return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achat)) return false;
        Achat achat = (Achat) o;
        return Float.compare(achat.getPrix(), getPrix()) == 0 &&
                getDate() == achat.getDate() &&
                getNom().equals(achat.getNom()) &&
                getQuantite().equals(achat.getQuantite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNom(), getQuantite(), getPrix(), getDate());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(quantite);
        parcel.writeFloat(prix);
        parcel.writeLong(date);
    }
}
