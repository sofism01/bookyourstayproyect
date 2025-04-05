package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookYourStay {
    private Administrador administrador;
    private Cliente cliente;
    public static BookYourStay bookYourStay;
    public ArrayList<Alojamiento> alojamientos;
    private ArrayList<Persona> personas;
    private Set<String> emailsRegistrados = new HashSet<>();

    //singleton
    public static BookYourStay getInstance() {
        if (bookYourStay == null) {
            bookYourStay = new BookYourStay();
        }
        return bookYourStay;

    }

    private BookYourStay() {

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

        List<Servicio> serviciosLista = servicios.stream().map(c -> Servicio.valueOf(c.toUpperCase())).toList();

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

    public void registrarCliente(String cedula, String nombre, String apellido, String telefono,
                                 String email, String contrasena) throws Exception {

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            throw new Exception("Todos los campos son necesarios");
        }

        if (telefono.length() != 10) {
            throw new Exception("El número de teléfono debe tener exactamente 10 dígitos");
        }

        for (char c : telefono.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new Exception("El número de teléfono solo debe contener dígitos");
            }
        }
        if (!email.contains("@") || email.indexOf("@") == 0 || email.lastIndexOf(".")
                < email.indexOf("@") + 2 || email.endsWith(".")) {
            throw new Exception("El email no tiene un formato válido");
        }

        // Validar que el email no esté repetido (no sea existente)

        if (emailsRegistrados.contains(email)) {
            throw new Exception("El número de teléfono ya está registrado");

    }
        if (!contrasena.matches(".*[A-Z].*")) {
            throw new Exception("La contraseña debe contener al menos una letra mayúscula.");
        }

        if (!contrasena.matches(".*\\d.*")) {
            throw new Exception("La contraseña debe contener al menos un número.");
        }
    }


    public Persona listarPersonas() {
        return (Persona) personas;

    }
}


