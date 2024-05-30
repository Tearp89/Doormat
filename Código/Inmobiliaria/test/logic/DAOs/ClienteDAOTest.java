package logic.DAOs;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import logic.classes.Cliente;

public class ClienteDAOTest {

    @Test
    public void TestAgregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setContrasenia("contraseña");
        cliente.setCorreo("clinete@gmai.com");
        cliente.setUsuarioCliente("cliente166");
        cliente.setPreferencias("Casas");
        try{
            ClienteDAO clienteDAO = new ClienteDAO();
            int resultadoEsperado = 1;
            int resultado = clienteDAO.agregarCliente(cliente);
            assertEquals(resultadoEsperado, resultado);
        } catch (SQLException e){
            e.printStackTrace();
        }   
        
        
    }

    @Test
    public void eliminarClientePorUsuario(){
        Cliente cliente = new Cliente();
        cliente.setUsuarioCliente("cliente1");
        try{
            int resultadoEsperado = 1;
            ClienteDAO clienteDAO = new ClienteDAO();
            int resultado = clienteDAO.eliminarClientePorUsuario(cliente);
            assertEquals(resultadoEsperado, resultado);
        } catch (SQLException e){
            e.printStackTrace();
        }
       
    }
}
