package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccess.DatabaseManager;

public class LoginDAO {

    public boolean validarAgente(String nombreUsuario, String contraseña){
        DatabaseManager dbManager = new DatabaseManager();
        String query = "SELECT COUNT(*) AS count FROM Agente WHERE usuarioAgente = ? AND contraseña = SHA2(?, 256)";
        try{
            Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, contraseña);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count == 1;
                }
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean validarCliente(String usuarioCliente, String contraseñaCliente){
        DatabaseManager dbManager = new DatabaseManager();
        String query = "SELECT COUNT(*) AS count FROM Cliente WHERE usuarioCliente = ? AND contraseña = SHA2(?, 256)";
        try{
            Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuarioCliente);
            preparedStatement.setString(2, contraseñaCliente);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count == 1;
                }
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean validarClientePorCorreo(String correoPropietario) {
        DatabaseManager dbManager = new DatabaseManager();
        String query = "SELECT * FROM clientes WHERE CorreoPropietario = ?";

        try {
            Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, correoPropietario);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    int count = rs.getInt("count");
                    return count == 1;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
