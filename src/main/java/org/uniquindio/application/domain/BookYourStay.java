package org.uniquindio.application.domain;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.uniquindio.application.Main;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Servicio;
import org.uniquindio.application.enums.Tipo;
import org.uniquindio.application.utils.Constantes;
import org.uniquindio.application.utils.Persistencia;

import java.io.IOException;
import java.io.Serializable;
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
    private Cliente clienteActual;

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
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
        this.reservas = (ArrayList<Reserva>) leerDatosReserva();
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
        reservas = new ArrayList<>();
        administrador.setEmail("a");
        administrador.setContrasena("a");
        personas.add(administrador);
        //guardarDatosUsuario(personas);
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
        guardarDatosUsuario(personas);

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
                guardarDatosAlojamiento(alojamientos);
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
                return (List<Persona>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void guardarDatosReserva(List<Reserva> reservas) {
        try {
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
            throw new Exception("Fechas inválidas.") ;
        }

        // Validar capacidad
        if (numeroPersonas > alojamiento.getCapacidadMax()) {
            throw new Exception("La cantidad de huéspedes excede la capacidad del alojamiento.") ;
        }

        // Validar disponibilidad (no se deben cruzar fechas con reservas existentes)
        for (Reserva r : alojamiento.getReservas()) {
            if (fechasSeCruzan(ingreso, salida, r.getFechaIngreso(), r.getFechaSalida())) {
                throw new Exception ("El alojamiento no está disponible en esas fechas.");
            }
        }

        // Calcular costo
        long noches = ChronoUnit.DAYS.between(ingreso, salida);
        float costoTotal = noches * alojamiento.getPrecioPorNoche();

        if (alojamiento instanceof Casa || alojamiento instanceof Apartamento) {
            costoTotal += 50000; // costo adicional fijo de aseo
        }

        // Validar saldo en la billetera
        Billetera billetera = buscarBilleteraDe(cliente);
        if (billetera == null || billetera.consultarSaldo() < costoTotal) {
            throw new Exception("Fondos insuficientes en la billetera.") ;
        }

        // Descontar dinero y registrar reserva
        billetera.retirar(costoTotal);
        Reserva nuevaReserva = new Reserva(ingreso, salida, cliente, alojamiento, numeroPersonas);
        alojamiento.getReservas().add(nuevaReserva);
        reservas.add(nuevaReserva);

        return "Reserva realizada exitosamente. Total pagado: $" + String.format("%.2f", costoTotal);
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


}


