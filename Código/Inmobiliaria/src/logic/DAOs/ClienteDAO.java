package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataAccess.DatabaseManager;
import logic.classes.Cliente;

public class ClienteDAO {
    public int agregarCliente(Cliente cliente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO Cliente (usuarioCliente, correoElectrónico, contraseña, preferencias) VALUES (?, ?, SHA2(?, 256), ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, cliente.getUsuarioCliente());
        pStatement.setString(2, cliente.getCorreo());
        pStatement.setString(3, cliente.getContrasenia());
        pStatement.setString(4, cliente.getPreferencias());
        result = pStatement.executeUpdate();
        return result;
    }

    public int eliminarClientePorUsuario(Cliente cliente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "DELETE FROM Cliente where usuarioCliente = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, cliente.getUsuarioCliente());
        result = pStatement.executeUpdate();
        return result;
    }
}
