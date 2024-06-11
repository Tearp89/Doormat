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

    public int eliminarClientePorUsuario(Cliente cliente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "DELETE FROM Cliente where usuarioCliente = ?";
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

    public int actualizarClienteSinContrasenia(Cliente cliente, String usuarioActual) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "UPDATE Cliente SET usuarioCliente = ?, correoElectrónico = ?, nombre = ? WHERE usuarioCliente = ?";
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, cliente.getUsuarioCliente());
        pStatement.setString(2, cliente.getCorreo());
        pStatement.setString(3, cliente.getNombre());
        pStatement.setString(4, usuarioActual);
        int result = pStatement.executeUpdate();
        connection.close();
        return result;
    }
    
    
    public int actualizarClienteConContrasenia(Cliente cliente, String contraseñaVieja, String usuarioActual) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "UPDATE Cliente SET usuarioCliente = ?, correoElectrónico = ?, nombre = ?, contraseña = SHA2(?, 256) " +
                       "WHERE usuarioCliente = ? ";
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, cliente.getUsuarioCliente());
        pStatement.setString(2, cliente.getCorreo());
        pStatement.setString(3, cliente.getNombre());
        pStatement.setString(4, cliente.getContrasenia());
        pStatement.setString(5, usuarioActual);
        int result = pStatement.executeUpdate();
        connection.close();
        return result;
    }
    
    

    

    public String obtenerNombreClientePorUsuario(String usuario) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT nombre FROM Cliente WHERE usuarioCliente = ?";
        String nombre = null;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, usuario);
        ResultSet resultSet = pStatement.executeQuery();
        if (resultSet.next()) {
            nombre = resultSet.getString("nombre");
        }
        return nombre;
    }
    
    public String obtenerCorreoClientePorUsuario(String usuario) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT correoElectrónico FROM Cliente WHERE usuarioCliente = ?";
        String correo = null;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, usuario);
        ResultSet resultSet = pStatement.executeQuery();
        if (resultSet.next()) {
            correo = resultSet.getString("correoElectrónico");
        }
        return correo;
    }
    
    public String obtenerUsuarioClientePorUsuario(String correo) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT usuarioCliente FROM Cliente WHERE correoElectrónico = ?";
        String usuarioCliente = null;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, correo);
        ResultSet resultSet = pStatement.executeQuery();
        if (resultSet.next()) {
            usuarioCliente = resultSet.getString("usuarioCliente");
        }
        return usuarioCliente;
    }

    public boolean validarContrasenia(String usuario, String contraseniaVieja) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT COUNT(*) FROM Cliente WHERE usuarioCliente = ? AND contraseña = SHA2(?, 256)";
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, usuario);
        pStatement.setString(2, contraseniaVieja);
        ResultSet resultSet = pStatement.executeQuery();
        boolean valid = false;
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            valid = true;
        }
        connection.close();
        return valid;
    }
    

    public boolean correoRepetido(String correo, String usuarioActual) {
        DatabaseManager dbManager = new DatabaseManager();
        String query = "SELECT COUNT(*) AS count FROM Cliente WHERE correoElectrónico = ? AND usuarioCliente != ?";
        try {
            Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, usuarioActual);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    


}
