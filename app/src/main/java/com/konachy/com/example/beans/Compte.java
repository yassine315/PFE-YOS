package com.konachy.com.example.beans;

public class Compte {

    private static String nom ;
    private static String prenom ;
    private static int id ;
    private static String password ;
    private  static String phone ;
    private static String email;
    private static String statut;

    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String ID = "id_molhanot";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String EMAIL = "EMAIL";
    public static final String STATUT = "statut";


    public static final String DB = "molhanot.db";


    @Override
    public  int hashCode() {
        return super.hashCode();
    }




    public static void initialiser(String nomP, String prenomP, int idP, String passwordP, String phoneP, String statutP , String emailP) {
        nom = nomP;
        prenom = prenomP;
        id = idP;
        password = passwordP;
        phone = phoneP;
        statut = statutP;
        email = emailP;

    }

    public static boolean isEmpty(){
       return  !(nom != "null" && prenom != "null" && password != "null" && phone!= "null");
    }

    public static String getEmail() {
        return email;
    }


    public static String getNom() {
        return nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static int getId() {
        return id;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getStatut() {
        return statut;
    }



}
