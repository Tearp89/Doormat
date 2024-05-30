package logic.DAOs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        propiedad.setUsuarioCliente(null);

        try {
            int result = propiedadDAO.agregarPropiedad(propiedad);
            assertEquals(1, result); 
        } catch (SQLException e) {
            fail("Excepción al agregar propiedad: " + e.getMessage());
        }
    }


    @Test
    public void TesteliminarPropiedadPorIdSuccess(){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        int resultadoEsperado = 1;
        int idPropiedad = 3;
        Propiedad propiedad = new Propiedad();
        propiedad.setIdPropiedad(idPropiedad);
        try{
            int resultado = propiedadDAO.eliminarPropiedadPorId(propiedad);
            assertEquals(resultadoEsperado, resultado);
        } catch (SQLException e){
            fail("Excepción al eliminar propiedad: " + e.getMessage());
            
        }

    }

    @Test
    public void TestActualizarPropiedadPoridPropiedadSuccess(){
        Propiedad propiedad = new Propiedad();
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        propiedad.setDireccion("Lomas verdes #23");
        propiedad.setDescripcion("Casa de 2 pisos acabado moderno.");
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
        propiedad.setResumen("Linda casa con mucho espacio");
        propiedad.setIdPropiedad(4);
        propiedad.setUsuarioCliente("cliente166");

        try {
            int result = propiedadDAO.actualizarPropiedadPorIdPropiedad(propiedad);
            assertEquals(1, result); 
        } catch (SQLException e) {
            fail("Excepción al agregar propiedad: " + e.getMessage());
        }
    }

    @Test
    public void TestBuscarPropiedadesDeUsuarioPorUsuarioClienteSuccess(){
        String nombreUsuario = "cliente166";
        try{
            PropiedadDAO propiedadDAO = new PropiedadDAO();
            int resultadoEsperado = 0;
            int resultado = propiedadDAO.buscarPropiedadesDeUsuarioPorUsuarioCliente(nombreUsuario).size();
            assertEquals(resultadoEsperado, resultado);

        } catch (SQLException e){
            e.printStackTrace();
        }
       
    }

    @Test
    public void TestCalificarPropiedad(){
        int idPropiedad = 4;
        int calificacion = 5;
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try{
            int resultadoEsperado = 1;
            int resultado = propiedadDAO.calificarPropiedad(calificacion, idPropiedad);
            assertEquals(resultadoEsperado, resultado);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

   
}
