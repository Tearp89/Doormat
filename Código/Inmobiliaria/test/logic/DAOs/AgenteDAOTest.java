package logic.DAOs;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import logic.classes.Agente;

public class AgenteDAOTest {
    @Test
    public void testAgregarAgenteSuccess() throws SQLException {
        Agente agente = new Agente();
        agente.setUsuarioAgente("Agente de Prueba");
        agente.setContrasenia("testAgregarAgente");
        AgenteDAO agenteDAO = new AgenteDAO();
        int result = agenteDAO.agregarAgente(agente);
        assertEquals(1, result); 
    }
}
