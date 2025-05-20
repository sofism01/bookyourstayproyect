package org.uniquindio.application.domain;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.uniquindio.application.Main;
import org.uniquindio.application.controllers.CrearReservaController;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;
import org.uniquindio.application.utils.Constantes;
import org.uniquindio.application.utils.Persistencia;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.simplejavamail.api.mailer.config.TransportStrategy.SMTP_TLS;
@Getter
@AllArgsConstructor
public class BookYourStay implements Serializable {
    private Administrador administrador;
    public static BookYourStay bookYourStay;
    private ArrayList<Alojamiento> alojamientos;
    private ArrayList<Persona> personas;
    private Set<String> emailsRegistrados = new HashSet<>();
    private List<Billetera> billeteras;
    private List<Reserva> reservas;
    private List<Oferta> ofertas;
    private List<Resena> resenas;
    private Cliente clienteActual;
    private Alojamiento alojamientoActual;

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    public Alojamiento getAlojamientoActual() {
        return alojamientoActual;
    }

    public void setAlojamientoActual(Alojamiento alojamientoActual) {
        this.alojamientoActual = alojamientoActual;
    }

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
        this.alojamientos = (ArrayList<Alojamiento>) leerDatosAlojamiento();
        this.personas = (ArrayList<Persona>) leerDatosUsuario();
        this.reservas = (List<Reserva>) leerDatosReserva();
        this.ofertas = (List<Oferta>) leerDatosOferta();
        this.resenas = (List<Resena>) leerDatosResena();
        sincronizarBilleteras();
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

    private void sincronizarBilleteras() {
        if (billeteras == null) {
            billeteras = new ArrayList<>();
        }
        billeteras.clear();
        for (Persona p : personas) {
            if (p instanceof Cliente) {
                Cliente c = (Cliente) p;
                if (c.getBilletera() == null) {
                    c.setBilletera(new Billetera(c));
                }
                billeteras.add(c.getBilletera());
            }
        }
    }

    private void iniciarApp() {
        personas = new ArrayList<>();
        administrador = new Administrador();
        alojamientos = new ArrayList<>();
        reservas = new ArrayList<>();
        administrador.setEmail("a");
        administrador.setContrasena("a");
        personas.add(administrador);
        //guardarDatosUsuario(personas);
    }


    public Persona iniciarSesion(String email, String contrasena) {
        if (email == null || contrasena == null) {
            return null;
        }

        String emailLowerCase = email.toLowerCase();
        if (emailLowerCase.equals(administrador.getEmail()) && contrasena.equals(administrador.getContrasena())) {
            return administrador;
        }
        for (Persona p : personas) {
            if (p.getEmail().equalsIgnoreCase(emailLowerCase) && p.getContrasena().equals(contrasena)) {
                if (p instanceof Cliente && !((Cliente) p).isActivo()) {
                    // Si el cliente no está activado, devolver null
                    return null;
                }
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

    public void agreagrApartamento(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, float precioPorNoche,
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
        guardarDatosAlojamiento(alojamientos);
    }

    public void agreagrCasa(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, float precioPorNoche,
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
        guardarDatosAlojamiento(alojamientos);

    }

    public void agreagrHotel(Tipo tipo, String nombre, Ciudad ciudad, String descripcion, String image, List<String> servicios, List<Habitacion> habitaciones) throws Exception {
        List<Servicio> serviciosLista = servicios.stream().map(c -> Servicio.valueOf(c.toUpperCase())).toList();

        //Hacer las validaciones necesarias
        if (habitaciones == null) {
            habitaciones = new ArrayList<>();
        }
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
        guardarDatosAlojamiento(alojamientos);

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

    public String enviarCodigoRecuperacion(String emailUser) throws Exception {
        String codigo = bookYourStay.crearCodigoRecuperacion();
        Email email = EmailBuilder.startingBlank()
                .from("bookYourStay", "bookyourstay7@gmail.com")
                .to(emailUser)
                .withSubject("Recuperación de contraseña - Book Your Stay")
                .withPlainText("Hola " + ",\n\nHas solicitado recuperar tu contraseña.\nTu código de recuperación es: " + codigo)
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

    public void enviarCorreoFactura(String emailUser, Factura factura) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crearReserva.fxml"));
        Parent root = loader.load();
        CrearReservaController crearReservaController = loader.getController();

        crearReservaController.generarQR(factura.getCodigoFactura());
        Path qrPath = Path.of("src/main/resources/qr/qr_" + factura.getCodigoFactura() + ".png");

        // Determinar si aplica alguna oferta y el porcentaje de descuento
        String descuentoAplicado = "No aplica descuento.";
        for (Oferta oferta : ofertas) {
            if (!factura.getFechaFactura().isBefore(oferta.getFechaInicio()) && !factura.getFechaFactura().isAfter(oferta.getFechaFin())) {
                descuentoAplicado = "Descuento aplicado: " + oferta.getDescuento();
                break;
            }
        }

        String cuerpo = "📄 Detalles de su Factura\n\n"
                + "Código de factura: " + factura.getCodigoFactura() + "\n"
                + "Fecha de emisión: " + factura.getFechaFactura() + "\n"
                + "Subtotal: $" + factura.getSubtotal() + "\n"
                + "Total: $" + factura.getTotal() + "\n"
                + descuentoAplicado + "\n\n"
                + "¡Gracias por usar Book Your Stay!";

        Email email = EmailBuilder.startingBlank()
                .from("Book Your Stay", "bookyourstay7@gmail.com")
                .to(emailUser)
                .withSubject("📄 Factura de su reserva - Book Your Stay")
                .withPlainText(cuerpo)
                .withHTMLText("<html><body>" + cuerpo.replace("\n", "<br>") + "<br><img src='cid:facturaQR'></body></html>")
                .withEmbeddedImage("facturaQR", java.nio.file.Files.readAllBytes(qrPath), "image/png")
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "bookyourstay7@gmail.com", "oxabhvzdordfhzvx")
                .withTransportStrategy(SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }
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

        // Validar que el email no esté repetido
        if (emailsRegistrados.contains(email.toLowerCase())) {
            throw new Exception("El email ya está registrado");
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
                .email(email.toLowerCase())
                .contrasena(contrasena)
                .build();

        // Crear billetera si no existe
        if (clienteNuevo.getBilletera() == null) {
            clienteNuevo.setBilletera(new Billetera(clienteNuevo));
        }

        String codigo = enviarCorreoBienvenida(nombre, email);
        clienteNuevo.setCodigoActivacion(codigo);
        clienteNuevo.setActivo(false);

        // Agregar el email a la lista de registrados
        emailsRegistrados.add(email.toLowerCase());
        personas.add(clienteNuevo);
        
        // Agregar billetera a la lista global
        if (billeteras == null) {
            billeteras = new ArrayList<>();
        }
        billeteras.add(clienteNuevo.getBilletera());

        // Guardar tanto las personas como los emails registrados
        guardarDatosUsuario(personas);
    }

    public Persona activarCuenta(String email, String password, String codigo) {
        if (email == null || password == null || codigo == null) {
            return null;
        }

        String emailLowerCase = email.toLowerCase();
        for (Persona p : personas) {
            if (p instanceof Cliente && 
                p.getEmail().equalsIgnoreCase(emailLowerCase) && 
                p.getContrasena().equals(password)) {
                
                Cliente cliente = (Cliente) p;
                if (cliente.getCodigoActivacion().equals(codigo)) {
                    cliente.setActivo(true);
                    guardarDatosUsuario(personas); // Guardar el cambio de estado
                    return cliente;
                }
                break; // Si el código no coincide, no seguir buscando
            }
        }

        return null;
    }

    public ArrayList<Persona> listarPersonas() {
        return personas;
    }

    public List<Oferta> listarOfertas() {
        return ofertas;
    }

    public String crearCodigoPersona() {
        String numero = generarNumeroAleatorio();
        while (buscarPersona(emailsRegistrados.toString()) != null) {
            numero = generarNumeroAleatorio();
        }
        return numero;
    }

    public String crearCodigoRecuperacion() {
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
                guardarDatosAlojamiento(alojamientos);
            }
        }
    }

    public void eliminarUsuario(String cedula) {
        // Leer la lista más reciente de usuarios
        List<Persona> personasActualizadas = leerDatosUsuario();
        for (int i = 0; i < personasActualizadas.size(); i++) {
            if (personasActualizadas.get(i).getCedula().equals(cedula)) {
                // Eliminar billetera asociada si es Cliente
                if (personasActualizadas.get(i) instanceof Cliente) {
                    Cliente c = (Cliente) personasActualizadas.get(i);
                    if (billeteras != null) {
                        billeteras.remove(c.getBilletera());
                    }
                }
                personasActualizadas.remove(i);
                break;
            }
        }
        // Guardar la lista actualizada
        guardarDatosUsuario(personasActualizadas);
        // Actualizar la lista en memoria
        this.personas = new ArrayList<>(personasActualizadas);
    }

    public void eliminarOferta(String id) {
        for (int i = 0; i < ofertas.size(); i++) {
            if (ofertas.get(i).getId().equals(id)) {
                ofertas.remove(i);
                guardarDatosOferta(ofertas);
            }
        }
    }

    public void eliminarReserva(String id) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdReserva().equals(id)) {
                reservas.remove(i);
                guardarDatosReserva(reservas);
            }
        }
    }

    public void editarAlojamiento(String id, String nombre, Ciudad ciudad, String descripcion, float precioPorNoche,
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
                guardarDatosAlojamiento(alojamientos);
                break;
            }

        }
    }

    public void editarOferta(String descuento, LocalDate fechaInicio, LocalDate fechaFin, String id) {

        for (int i = 0; i < ofertas.size(); i++) {

            if (ofertas.get(i).getId().equals(id)) {

                Oferta ofertaGuardada = ofertas.get(i);
                ofertaGuardada.setDescuento(descuento);
                ofertaGuardada.setFechaInicio(fechaInicio);
                ofertaGuardada.setFechaFin(fechaFin);

                //Actualiza oferta en la lista de ofertas
                ofertas.set(i, ofertaGuardada);
                guardarDatosOferta(ofertas);
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

    public List<Alojamiento> buscarPorTipo(Tipo tipo){

        List<Alojamiento> resultado = new ArrayList<>();

        for(Alojamiento alojamiento :alojamientos){
            if(alojamiento.getTipo().equals(tipo)){
                resultado.add(alojamiento);
            }
        }

        return resultado;
    }

    public void guardarDatosAlojamiento(List<Alojamiento> alojamientos) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ALOJAMIENTOS, alojamientos);
        } catch (Exception e) {
            System.err.println("Error guardando alojamientos: " + e.getMessage());
        }
    }


    public List<Alojamiento> leerDatosAlojamiento() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_ALOJAMIENTOS);
            if (datos != null) {
                return (List<Alojamiento>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando alojamientos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void guardarDatosUsuario(List<Persona> personas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_USUARIOS, personas);
        } catch (Exception e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }
    }


    public List<Persona> leerDatosUsuario() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_USUARIOS);
            if (datos != null) {
                List<Persona> personasCargadas = (List<Persona>) datos;
                // Sincronizar billeteras al cargar
                for (Persona p : personasCargadas) {
                    if (p instanceof Cliente) {
                        Cliente c = (Cliente) p;
                        if (c.getBilletera() == null) {
                            c.setBilletera(new Billetera(c));
                        }
                    }
                }
                return personasCargadas;
            }
        } catch (Exception e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void guardarDatosReserva(List<Reserva> reservas) {
        try {
            // Imprimir en consola las reservas de la lista
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
            Persistencia.serializarObjeto(Constantes.RUTA_RESERVAS, reservas);
        } catch (Exception e) {
            System.err.println("Error guardando reservas: " + e.getMessage());
        }
    }


    public List<Reserva> leerDatosReserva() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESERVAS);
            if (datos != null) {
                return (List<Reserva>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando reservas: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void guardarDatosOferta(List<Oferta> ofertas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_OFERTAS, ofertas);
        } catch (Exception e) {
            System.err.println("Error guardando ofertas: " + e.getMessage());
        }
    }


    public List<Oferta> leerDatosOferta() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_OFERTAS);
            if (datos != null) {
                return (List<Oferta>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando ofertas: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void guardarDatosResena(List<Resena> resenas) {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_RESENAS, resenas);
        } catch (Exception e) {
            System.err.println("Error guardando reseñas: " + e.getMessage());
        }
    }


    public List<Resena> leerDatosResena() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESENAS);
            if (datos != null) {
                return (List<Resena>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando resenas: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    //metodo para calcular la ocupacion porcentual de cada alojamiento
    public List<Double> calcularOcupacionPorcentualPorTipo() {
        double nochesCasa = 0;
        double nochesApartamento = 0;
        double nochesHotel = 0;
        double nochesTotales = 0;

        for (Alojamiento alojamiento : alojamientos) {
            List<Reserva> reservas = alojamiento.getReservas();
            if (reservas == null) continue;  // <-- Aquí validas que no sea null

            for (Reserva reserva : reservas) {
                long noches = ChronoUnit.DAYS.between(
                        reserva.getFechaIngreso(),
                        reserva.getFechaSalida()
                );
                if (noches <= 0) continue;

                nochesTotales += noches;

                if (alojamiento instanceof Casa) {
                    nochesCasa += noches;
                } else if (alojamiento instanceof Apartamento) {
                    nochesApartamento += noches;
                } else if (alojamiento instanceof Hotel) {
                    nochesHotel += noches;
                }
            }
        }

        if (nochesTotales == 0) {
            return Arrays.asList(0.0, 0.0, 0.0);
        }

        double porcentajeCasa = (nochesCasa * 100.0) / nochesTotales;
        double porcentajeApartamento = (nochesApartamento * 100.0) / nochesTotales;
        double porcentajeHotel = (nochesHotel * 100.0) / nochesTotales;

        return Arrays.asList(porcentajeCasa, porcentajeApartamento, porcentajeHotel);
    }



    //metood para calcular las ganancias totales de una reserva
    public double calcularGananciasTotalesPorReserva() {
        double totalGanancias = 0.0;

        for (Alojamiento alojamiento : alojamientos) {
            for (Reserva reserva : alojamiento.getReservas()) {
                long noches = ChronoUnit.DAYS.between(
                        reserva.getFechaIngreso(),
                        reserva.getFechaSalida()
                );
                if (noches <= 0) continue;

                int personas = reserva.getNumeroPersonas(); // Asegúrate de que este método/campo exista
                double gananciaReserva = noches * alojamiento.getPrecioPorNoche() * personas;

                // Costo adicional por aseo para Casa o Apartamento
                if (alojamiento instanceof Casa || alojamiento instanceof Apartamento) {
                    gananciaReserva += 50000; // Puedes ajustar esto según tu modelo
                }

                totalGanancias += gananciaReserva;
            }
        }

        return totalGanancias;
    }

//metodo para listar losalojamintos mas populares en cada ciudad
    public Map<Ciudad, List<Alojamiento>> listarAlojamientosMasPopularesPorCiudad() {
        Map<Ciudad, List<Alojamiento>> popularesPorCiudad = new HashMap<>();

        for (Alojamiento alojamiento : alojamientos) {
            Ciudad ciudad = alojamiento.getCiudad();

            popularesPorCiudad
                    .computeIfAbsent(ciudad, k -> new ArrayList<>())
                    .add(alojamiento);
        }

        // Ordenar alojamientos de cada ciudad por número de reservas (descendente)
        for (List<Alojamiento> lista : popularesPorCiudad.values()) {
            lista.sort((a1, a2) -> Integer.compare(
                    a2.getReservas().size(),
                    a1.getReservas().size()
            ));
        }

        return popularesPorCiudad;
    }

    //metodo para lsitar alojamientos mas populares por numero de reservas
    public List<Alojamiento> listarAlojamientosPopulares() {
        if (alojamientos == null) {
            return new ArrayList<>();
        }
        return alojamientos.stream()
                .sorted((a1, a2) -> {
                    int r1 = a1.getReservas() != null ? a1.getReservas().size() : 0;
                    int r2 = a2.getReservas() != null ? a2.getReservas().size() : 0;
                    return Integer.compare(r2, r1); // Orden descendente
                })
                .collect(Collectors.toList());
    }

    public List<Alojamiento> listarAlojamientosMejorPrecio() {
        if (alojamientos == null) {
            return new ArrayList<>();
        }
        return alojamientos.stream()
                .sorted(Comparator.comparingDouble(Alojamiento::getPrecioPorNoche))
                .collect(Collectors.toList());
    }

    //metodo para listar los alojamientos mas rentables
    public Map<String, Double> listarTiposAlojamientoMasRentables() {
        Map<String, Double> gananciasPorTipo = new HashMap<>();

        for (Alojamiento alojamiento : alojamientos) {
            List<Reserva> reservas = alojamiento.getReservas();
            if (reservas == null) {
                continue; // Evita NullPointerException si la lista de reservas es null
            }

            for (Reserva reserva : reservas) {
                long noches = ChronoUnit.DAYS.between(reserva.getFechaIngreso(), reserva.getFechaSalida());
                if (noches <= 0) continue;

                int personas = reserva.getNumeroPersonas(); // Asegúrate de que este método exista
                double ganancia = noches * alojamiento.getPrecioPorNoche() * personas;

                // Suma extra para casas y apartamentos
                if (alojamiento instanceof Casa || alojamiento instanceof Apartamento) {
                    ganancia += 50000;
                }

                // Determinar tipo de alojamiento como texto
                String tipo = alojamiento instanceof Casa ? "Casa"
                        : alojamiento instanceof Apartamento ? "Apartamento"
                        : alojamiento instanceof Hotel ? "Hotel"
                        : "Otro";

                // Acumular la ganancia por tipo
                gananciasPorTipo.put(tipo, gananciasPorTipo.getOrDefault(tipo, 0.0) + ganancia);
            }
        }

        // Ordenar el mapa por valor descendente (más rentables primero)
        return gananciasPorTipo.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    //metodo para realizar reserva con las validaciones necesarias
    public String realizarReserva(Cliente cliente, Alojamiento alojamiento, LocalDate ingreso, LocalDate salida, int numeroPersonas) throws Exception {
        // Validación de fechas
        if (ingreso == null || salida == null || !ingreso.isBefore(salida)) {
            throw new Exception("Fechas inválidas.");
        }

        // Validar capacidad
        if (numeroPersonas > alojamiento.getCapacidadMax()) {
            throw new Exception("La cantidad de huéspedes excede la capacidad del alojamiento.");
        }

        // Inicializar la lista de reservas si es nula
        if (alojamiento.getReservas() == null) {
            alojamiento.setReservas(new ArrayList<>());
        }

        // Validar disponibilidad (no se deben cruzar fechas con reservas existentes)
        for (Reserva r : alojamiento.getReservas()) {
            if (fechasSeCruzan(ingreso, salida, r.getFechaIngreso(), r.getFechaSalida())) {
                throw new Exception("El alojamiento no está disponible en esas fechas.");
            }
        }

        long noches = ChronoUnit.DAYS.between(ingreso, salida);
        float totalBase = noches * alojamiento.getPrecioPorNoche();
        float costoTotal = calcularTotalConOferta(alojamiento, ingreso, salida, totalBase);

        // Sumar costos adicionales si es Casa o Apartamento
        if (alojamiento instanceof Casa) {
            costoTotal += (float) ((Casa) alojamiento).getCostoAseo();
        } else if (alojamiento instanceof Apartamento) {
            costoTotal += (float) ((Apartamento) alojamiento).getCostoMantenimiento();
        }

        // Validar saldo en la billetera
        if (cliente.getBilletera().getSaldo() < costoTotal) {
            throw new Exception("Saldo insuficiente en la billetera.");
        }

        // Descontar saldo
        cliente.getBilletera().setSaldo(cliente.getBilletera().getSaldo() - costoTotal);

        // Crear la reserva
        Reserva reserva = Reserva.builder()
                .cliente(cliente)
                .alojamiento(alojamiento)
                .fechaIngreso(ingreso)
                .fechaSalida(salida)
                .numeroPersonas(numeroPersonas)
                .costoTotal(costoTotal)
                .build();

        // Agregar la reserva al alojamiento y a la lista global
        cliente.getReservas().add(reserva);
        reservas.add(reserva);

        // Guardar datos
        guardarDatosReserva(reservas);
        guardarDatosUsuario(personas);

        // Generar factura y enviarla por correo
        Factura factura = new Factura(reserva.getCostoTotal(), reserva.getCostoTotal());
        enviarCorreoFactura(cliente.getEmail(), factura);

        return "Reserva realizada exitosamente. Se ha enviado la factura a su correo.";
    }

    private boolean fechasSeCruzan(LocalDate inicio1, LocalDate fin1, LocalDate inicio2, LocalDate fin2) {
        return !inicio1.isAfter(fin2.minusDays(1)) && !fin1.minusDays(1).isBefore(inicio2);
    }

    private Billetera buscarBilleteraDe(Persona persona) {
        for (Billetera b : billeteras) {
            if (b.getCliente().equals(persona)) {
                return b;
            }
        }
        return null;
    }

    public void agregarOferta(String porcentaje, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        if (porcentaje == null|| fechaInicio == null || fechaFin == null) {
            throw new Exception("Complete todos los campos.");
        }

        Oferta nuevaOferta = new Oferta(porcentaje, fechaInicio, fechaFin);
        ofertas.add(nuevaOferta);
        guardarDatosOferta(ofertas);
    }

    private float calcularTotalConOferta(Alojamiento alojamiento, LocalDate fechaIngreso, LocalDate fechaSalida, float totalBase) {
        for (Oferta oferta : ofertas) {
            if (oferta.getIdAlojamiento() != null
                    && oferta.getIdAlojamiento().equals(alojamiento.getId())
                    && oferta.getDescuento() != null
                    && !fechaSalida.isBefore(oferta.getFechaInicio())
                    && !fechaIngreso.isAfter(oferta.getFechaFin())) {
                String descuentoStr = oferta.getDescuento().replace("%", "");
                float descuento = Float.parseFloat(descuentoStr) / 100f;
                return totalBase * (1 - descuento);
            }
        }
        return totalBase;
    }

    public void agregarResena(Alojamiento alojamiento, int calificacion, String comentario) throws Exception {
        if (alojamiento == null) {
            throw new Exception("Alojamiento no válido.");
        }
        if (calificacion < 1 || calificacion > 5) {
            throw new Exception("La calificación debe estar entre 1 y 5.");
        }
        if (comentario == null || comentario.isBlank()) {
            throw new Exception("El comentario no puede estar vacío.");
        }

        if(calificacion == 0){
            throw new Exception("Agregue una calificación");
        }

        if (alojamiento.getResenas() == null) {
            alojamiento.setResenas(new ArrayList<>());
        }
        Resena resena = new Resena(comentario, calificacion, alojamiento);
        alojamiento.getResenas().add(resena);
        resenas.add(resena);
        guardarDatosResena(resenas);
    }

}


