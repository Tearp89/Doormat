package logic.DAOs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Test;

import logic.classes.Propiedad;

public class PropiedadDAOTest {
    
    @Test
    public void testAgregarPropiedadSuccess() {
        Propiedad propiedad = new Propiedad();
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        propiedad.setDireccion("Lomas verdes #23");
        propiedad.setDescripcion("Casa de 2 pisos color, con 3 recamaras y 2 baños completos");
        propiedad.setEstadoPropiedad("Disponible");
        propiedad.setUsuarioAgente("Agente de Prueba");
        propiedad.setCiudad("Xalapa");
        propiedad.setZona("Zona trancas");
        propiedad.setTipoPropiedad("Casa");
        propiedad.setPrecio(1000);
        propiedad.setNoHabitaciones(3);
        propiedad.setNoEstancias(2);
        propiedad.setNoBanios(2);
        propiedad.setCochera(1);
        propiedad.setTamanio(150);
        propiedad.setResumen("Resumen de prueba");

        try {
            int result = propiedadDAO.agregarPropiedad(propiedad);
            assertEquals(1, result); 
        } catch (SQLException e) {
            fail("Excepción al agregar propiedad: " + e.getMessage());
        }
    }
}
