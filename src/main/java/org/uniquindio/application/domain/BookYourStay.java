package org.uniquindio.application.domain;

import org.uniquindio.application.domain.interfaces.Persona;

import java.util.ArrayList;

public class BookYourStay {
    private Administrador administrador;
    private Cliente cliente;
    private static BookYourStay bookYourStay;
    public ArrayList<Alojamiento> alojamientos;
    private ArrayList<Persona> personas;


//singleton
    public static BookYourStay getInstance() {
        if (bookYourStay == null) {
            bookYourStay = new BookYourStay();
        }
        return bookYourStay;

    }

    private BookYourStay(){

        iniciarApp();
    }

    private void iniciarApp() {
        personas = new ArrayList<>();
        administrador = new Administrador();
        cliente = new Cliente();
        alojamientos = new ArrayList<>();
        administrador.setEmail("Lau@bookyourstay.com");
        administrador.setContrasena("12345678");
        personas.add(administrador);
    }





    public static String iniciarSesion(String email, String contrasena) {

        return getInstance().login(email, contrasena);
    }

    public String login(String email, String contrasena) {

        if (administrador.getEmail().equals(email) && administrador.getContrasena().equals(contrasena)) {
            return "ADMINISTRADOR";

        }
        return null;
    }

}


