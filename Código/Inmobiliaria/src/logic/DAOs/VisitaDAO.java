package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccess.DatabaseManager;
import logic.classes.Visita;

public class VisitaDAO {
    public int agregarVisita(Visita visita) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO Visita (fecha, Agente_Usuario, Cliente_usuarioCliente, Propiedad_idPropiedad) VALUES (?, ?, ?, ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setObject(1, visita.getFecha());
        pStatement.setString(2, visita.getUsuarioAgente());
        pStatement.setString(3, visita.getUsuarioCliente());
        pStatement.setInt(4, visita.getIdPropiedad());
        result = pStatement.executeUpdate();
        return result;
    }

    public int reagendarVisita(Visita visita) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "UPDATE Visita set fecha = ? WHERE idVisita = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setObject(1, visita.getFecha());
        pStatement.setInt(2, visita.getIdPropiedad());
        result = pStatement.executeUpdate();
        return result;
    }

    public boolean tieneVisita(int idPropiedad) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT COUNT(*) as count FROM visita where Propiedad_idPropiedad = ?";
        Connection connection = databaseManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idPropiedad);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            return count == 1; 
        }
        return false; 
    }
}
