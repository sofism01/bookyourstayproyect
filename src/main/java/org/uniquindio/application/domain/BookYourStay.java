package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.ArrayList;
import java.util.List;

public class BookYourStay {
    private Administrador administrador;
    private Cliente cliente;
    public static BookYourStay bookYourStay;
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
        administrador.setEmail("a");
        administrador.setContrasena("a");
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

    public List<Alojamiento> listarAlojamientos() {
        return alojamientos;

    }
    public ArrayList<String> listarTipos() {
        ArrayList<String> tipo = new ArrayList<>();
        tipo.add("Casa");
        tipo.add("Apartamento");
        tipo.add("Hotel");

        return tipo;
    }

    public void agreagrApartamento(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                   int capacidadMax, Image image, List<String> servicios, double costoMantenimiento) {

        List<Servicio> serviciosLista = servicios.stream().map( c -> Servicio.valueOf(c.toUpperCase()) ).toList();

        //Hacer las validaciones necesarias
        Alojamiento alojamiento = Apartamento.builder()
                .tipo(tipo)
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .precioPorNoche(precioPorNoche)
                .capacidadMax(capacidadMax)
                .imagen(image)
                .servicio(serviciosLista)
                .costoMantenimiento(costoMantenimiento)

                .build();

        alojamientos.add(alojamiento);

    }

    public void agreagrCasa(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                   int capacidadMax, Image image, List<String> servicio, double costoMantenimiento) {

    }

    public void agreagrHotel(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                   int capacidadMax, Image image, List<String> servicio) {

    }
}


