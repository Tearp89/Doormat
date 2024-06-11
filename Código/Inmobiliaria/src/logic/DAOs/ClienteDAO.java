package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccess.DatabaseManager;
import logic.classes.Cliente;

public class ClienteDAO {
    public int agregarCliente(Cliente cliente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO Cliente (usuarioCliente,correoElectrónico, nombre,contraseña, preferencias) VALUES (?, ?, ?, SHA2(?, 256), ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, cliente.getUsuarioCliente());
        pStatement.setString(2, cliente.getCorreo());
        pStatement.setString(3, cliente.getnombre());
        pStatement.setString(4, cliente.getContrasenia());
        pStatement.setString(5, cliente.getPreferencias());
        result = pStatement.executeUpdate();
        return result;
    }

    public int eliminarClientePorCorreo(Cliente cliente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "DELETE FROM Cliente where correoElectrónico = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, cliente.getCorreo());
        result = pStatement.executeUpdate();
        return result;
    }

    public boolean correoDisponible(String correo) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT COUNT(*) FROM Cliente WHERE correoElectrónico = ?";
        boolean isAvailable = false;

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseManager.getConnection();
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, correo);
            resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isAvailable = (count == 0);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return isAvailable;
    }
}
