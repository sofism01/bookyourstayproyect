package org.uniquindio.application;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.uniquindio.application.domain.Alojamiento;
import org.uniquindio.application.domain.BookYourStay;
import org.uniquindio.application.domain.interfaces.Persona;
import org.uniquindio.application.enums.Ciudad;
import org.uniquindio.application.enums.Tipo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AlojamientosTest {

    BookYourStay bookYourStay = BookYourStay.getInstance();

    @Test
    public void testIniciarSesionExitosa() {
        Persona resultado = bookYourStay.iniciarSesion("a", "a");
        assertNotNull(resultado);
        assertEquals("a", resultado.getEmail());
    }

    @Test
    public void testIniciarSesionFallida() {
        Persona resultado = bookYourStay.iniciarSesion("no existe", "123");
        assertNull(resultado);
    }

    @Test
    public void testListarAlojamientos() {
        List<Alojamiento> lista = bookYourStay.listarAlojamientos();
        assertEquals(4, lista.size());
    }

    @Test
    public void testBuscarPorCiudadArmenia() {
        List<Alojamiento> resultado = bookYourStay.buscarPorCiudad(Ciudad.ARMENIA);
        assertEquals(3, resultado.size());
    }

    @Test
    public void testBuscarPorTipoHotel() {
        List<Alojamiento> resultado = bookYourStay.buscarPorTipo(Tipo.HOTEL);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testAgregarCasaExitosa() throws Exception {
        int antes = bookYourStay.listarAlojamientos().size();

        bookYourStay.agreagrCasa(
                Tipo.CASA,
                "Casa Bonita",
                Ciudad.BOGOTA,
                "Espaciosa casa",
                250000,
                4,
                "ruta/imagen.png",
                List.of("WIFI", "PISCINA"),
                5000
        );

        int despues = bookYourStay.listarAlojamientos().size();
        assertEquals(antes + 1, despues);
    }

    @Test
    public void testAgregarApartamentoConCamposInvalidos() {
        try {
            bookYourStay.agreagrApartamento(
                    null, "", null, "", 0, 0, null, new ArrayList<>(), 0
            );
            fail("Se esperaba una excepción por campos vacíos.");
        } catch (Exception e) {
            assertEquals("Todos los campos son necesarios", e.getMessage());
        }
    }


}
