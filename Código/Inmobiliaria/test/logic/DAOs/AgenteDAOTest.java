package logic.DAOs;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.apache.log4j.jmx.Agent;
import org.junit.Test;

import logic.classes.Agente;

public class AgenteDAOTest {
    @Test
    public void testAgregarAgenteSuccess() throws SQLException {
        Agente agente = new Agente();
        agente.setUsuarioAgente("Agente2");
        agente.setContrasenia("testAgregarAgente");
        AgenteDAO agenteDAO = new AgenteDAO();
        int result = agenteDAO.agregarAgente(agente);
        assertEquals(1, result); 
    }


    @Test
    public void testAgendarVisita() throws SQLException{
        Agente agente = new Agente();
        agente.setUsuarioAgente("Agente prueba");
        int resultadoEsperado = 0;
        AgenteDAO agenteDAO = new AgenteDAO();
        int resultado = agenteDAO.eliminarAgentePorUsuario(agente);
        assertEquals(resultadoEsperado, resultado);
    }


    @Test
    public void testCalificarAgente() throws SQLException{
        Agente agente = new Agente();
        agente.setValoracion(5);
        AgenteDAO gAgenteDAO = new AgenteDAO();
        int resultadoEsperado = 1;
        int resultado = gAgenteDAO.calificarAgente(agente);
        assertEquals(resultadoEsperado, resultado);
    }

    
}
