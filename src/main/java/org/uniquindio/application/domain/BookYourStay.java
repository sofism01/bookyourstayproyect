package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;

import java.util.*;

import static org.simplejavamail.api.mailer.config.TransportStrategy.SMTP_TLS;

public class BookYourStay {
    private Administrador administrador;
    public static BookYourStay bookYourStay;
    private ArrayList<Alojamiento> alojamientos;
    private ArrayList<Persona> personas;
    private Set<String> emailsRegistrados = new HashSet<>();
    private List<Billetera> billeteras;

    //singleton
    public static BookYourStay getInstance() {
        if (bookYourStay == null) {
            bookYourStay = new BookYourStay();
        }
        return bookYourStay;

    }

    private BookYourStay() {
        iniciarApp();
        crearAlojamientosPrueba();
    }

    private void crearAlojamientosPrueba(){

        alojamientos.add(
                Casa.builder()
                        .id(UUID.randomUUID().toString())
                        .nombre("Casa en Cartagena")
                        .imagen(Main.class.getResource("/imagen/img_2.png").toString())
                        .precioPorNoche(360000)
                        .capacidadMax(1)
                        .servicio(new ArrayList<>())
                        .ciudad(Ciudad.CARTAGENA)
                        .build()
        );

        alojamientos.add(
                Apartamento.builder()
                        .id(UUID.randomUUID().toString())
                        .nombre("Apartamento en Armenia")
                        .imagen(Main.class.getResource("/imagen/img_3.png").toString())
                        .ciudad(Ciudad.ARMENIA)
                        .capacidadMax(1)
                        .servicio(new ArrayList<>())
                        .precioPorNoche(160000)
                        .build()
        );

        alojamientos.add(
                Hotel.builder()
                        .id(UUID.randomUUID().toString())
                        .nombre("Habitación de hotel en Salento")
                        .imagen(Main.class.getResource("/imagen/img_4.png").toString())
                        .precioPorNoche(360000)
                        .capacidadMax(1)
                        .servicio(new ArrayList<>())
                        .ciudad(Ciudad.ARMENIA)
                        .build()
        );

        alojamientos.add(
                Hotel.builder()
                        .id(UUID.randomUUID().toString())
                        .nombre("Habitación test en Salento")
                        .imagen(Main.class.getResource("/imagen/img_5.png").toString())
                        .precioPorNoche(100)
                        .capacidadMax(1)
                        .servicio(new ArrayList<>())
                        .ciudad(Ciudad.ARMENIA)
                        .build()
        );


    }

    private void iniciarApp() {
        personas = new ArrayList<>();
        administrador = new Administrador();
        alojamientos = new ArrayList<>();
        administrador.setEmail("a");
        administrador.setContrasena("a");
        personas.add(administrador);
    }


    public Persona iniciarSesion(String email, String contrasena) {

        for (Persona p : personas){
            if (p.getContrasena().equals(contrasena) && p.getEmail().equals(email)){
                return p;
            }
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
                                   int capacidadMax, String image, List<String> servicios, double costoMantenimiento) throws Exception {

        List<Servicio> serviciosLista = servicios.stream().map(c -> Servicio.valueOf(c.toUpperCase())).toList();


        //Hacer las validaciones necesarias
        if (tipo == null || nombre.isEmpty() || ciudad == null || descripcion.isEmpty() || precioPorNoche == 0 || capacidadMax == 0 ||
                image == null || costoMantenimiento == 0) {
            throw new Exception("Todos los campos son necesarios");
        }

        Alojamiento alojamiento = Apartamento.builder()
                .id(UUID.randomUUID().toString())
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
                            int capacidadMax, String image, List<String> servicios, double costoAseo) throws Exception {
        List<Servicio> serviciosLista = servicios.stream().map(c -> Servicio.valueOf(c.toUpperCase())).toList();

        //Hacer las validaciones necesarias
        if (tipo == null || nombre.isEmpty() || ciudad == null || descripcion.isEmpty() || precioPorNoche == 0 || capacidadMax == 0 ||
                image == null || costoAseo == 0) {
            throw new Exception("Todos los campos son necesarios");
        }

        Alojamiento alojamiento = Casa.builder()
                .id(UUID.randomUUID().toString())
                .tipo(tipo)
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .precioPorNoche(precioPorNoche)
                .capacidadMax(capacidadMax)
                .imagen(image)
                .servicio(serviciosLista)
                .costoAseo(costoAseo)
                .build();

        alojamientos.add(alojamiento);

    }

    public void agreagrHotel(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, String image, List<String> servicios, List<Habitacion> habitaciones) throws Exception {
        List<Servicio> serviciosLista = servicios.stream().map(c -> Servicio.valueOf(c.toUpperCase())).toList();

        //Hacer las validaciones necesarias
        if (tipo == null || nombre.isEmpty() || ciudad == null || descripcion.isEmpty() ||
                image == null || habitaciones.isEmpty()) {
            throw new Exception("Todos los campos son necesarios");
        }

        Alojamiento alojamiento = Hotel.builder()
                .id(UUID.randomUUID().toString())
                .tipo(tipo)
                .nombre(nombre)
                .ciudad(ciudad)
                .habitaciones(habitaciones)
                .descripcion(descripcion)
                .imagen(image)
                .servicio(serviciosLista)
                .build();

        alojamientos.add(alojamiento);

    }

    public String enviarCorreoBienvenida(String nombre, String emailUser) throws Exception {

        String codigo = bookYourStay.crearCodigoPersona();
        Email email = EmailBuilder.startingBlank()
                .from("bookYourStay", "bookyourstay7@gmail.com")
                .to(emailUser)
                .withSubject("¡Bienvenido/a a Book Your Stay!")
                .withPlainText("Hola " + nombre + ",\n\nGracias por registrarte en Book Your Stay.\n¡Esperamos que tengas una excelente experiencia!, tu codigo de verificación es: "
                        + codigo)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "bookyourstay7@gmail.com", "oxabhvzdordfhzvx")
                .withTransportStrategy(SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }

        return codigo;
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

        Cliente clienteNuevo = Cliente.builder()
                .cedula(cedula)
                .nombre(nombre)
                .apellido(apellido)
                .telefono(telefono)
                .email(email)
                .contrasena(contrasena)
                .build();

        String codigo = enviarCorreoBienvenida(nombre, email);
        clienteNuevo.setCodigoActivacion(codigo);

        personas.add(clienteNuevo);

    }

    public Persona activarCuenta(String email, String password, String codigo){

        //hacer las validaciones
        Persona persona = iniciarSesion(email, password);
        if(persona instanceof Cliente){
            if(((Cliente) persona).getCodigoActivacion().equals(codigo)){
                return persona;
            }
        }

        return null;
    }

    public ArrayList<Persona> listarPersonas() {
        return personas;
    }

    public String crearCodigoPersona() {
        String numero = generarNumeroAleatorio();
        while (buscarPersona(emailsRegistrados.toString()) != null) {
            numero = generarNumeroAleatorio();
        }
        return numero;
    }

    public String generarNumeroAleatorio() {
        String numero = "";
        for (int i = 0; i < 5; i++) {
            numero += "" + ((int) (Math.random() * 10));
        }
        return numero;
    }

    public Persona buscarPersona(String emailsRegistrados) {
        return personas.stream()
                .filter(billetera -> billetera.getEmail().equals(emailsRegistrados))
                .findFirst()
                .orElse(null);
    }

    public void eliminarAlojamiento(String id) {
        for (int i = 0; i < alojamientos.size(); i++) {
            if (alojamientos.get(i).getId().equals(id)) {
                alojamientos.remove(i);
            }
        }
    }

    public void editarAlojamiento(String id, String nombre, Ciudad ciudad, String descripcion, double precioPorNoche,
                                  int capacidadMax, String imagen) {

        for (int i = 0; i < alojamientos.size(); i++) {

            if (alojamientos.get(i).getId().equals(id)) {

                Alojamiento alojamientoGuardado = alojamientos.get(i);
                alojamientoGuardado.setNombre(nombre);
                alojamientoGuardado.setCiudad(ciudad);
                alojamientoGuardado.setDescripcion(descripcion);
                alojamientoGuardado.setPrecioPorNoche(precioPorNoche);
                alojamientoGuardado.setCapacidadMax(capacidadMax);
                alojamientoGuardado.setImagen(imagen);
                //alojamientoGuardado.setServicio(servicio);


                //Actualiza el alojamiento en la lista de alojamientos
                alojamientos.set(i, alojamientoGuardado);
                break;
            }

        }
    }

    public List<Alojamiento> buscarPorCiudad(Ciudad ciudad){

        List<Alojamiento> resultado = new ArrayList<>();

        for(Alojamiento alojamiento :alojamientos){
            if(alojamiento.getCiudad().equals(ciudad)){
                resultado.add(alojamiento);
            }
        }

        return resultado;
    }

}


