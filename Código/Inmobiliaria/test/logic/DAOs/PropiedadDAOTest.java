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
        propiedad.setDireccion("Dirección de prueba");
        propiedad.setDescripcion("Descripción de prueba");
        propiedad.setCiudad("Ciudad de prueba");
        propiedad.setZona("Zona de prueba");
        propiedad.setTipoPropiedad("Renta");
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

    @Test
    public void testEditarPropiedadSucces() {
        System.out.println("editarPropiedad");
        Propiedad propiedad = new Propiedad();

        propiedad.setDescripcion("Descripción de prueba editada");
        propiedad.setCiudad("Ciudad de prueba editada");
        propiedad.setZona("Zona de prueba editada");
        propiedad.setPrecio(1500);

    }
}
