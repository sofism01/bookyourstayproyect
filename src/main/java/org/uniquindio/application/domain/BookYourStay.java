package org.uniquindio.application.domain;

import java.util.ArrayList;

public class BookYourStay {
    private Administrador administrador;
    private Cliente cliente;
    private static BookYourStay bookYourStay;
    private ArrayList<Alojamiento> alojamientos;



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
        administrador = new Administrador();
        cliente = new Cliente();
        alojamientos = new ArrayList<>();
        administrador.
    }





    public static String iniciarSesion(String email, String contrasena) {

        return getInstance().login(email, contrasena);
    }

    public String login(String email, String contrasena) {

        if (cliente.getEmail().equals(email) && cliente.getContrasena().equals(contrasena)) {
            return "CLIENTE";

        }
        return null;
    }
}


