package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccess.DatabaseManager;
import logic.classes.Agente;

public class AgenteDAO {
    public int agregarAgente(Agente agente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO Agente (usuarioAgente, contraseña) VALUES (?, SHA2(?, 256))";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, agente.getUsuarioAgente());
        pStatement.setString(2, agente.getContrasenia());

        result = pStatement.executeUpdate();
        return result;
    }

    public int eliminarAgentePorUsuario(Agente agente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "DELETE FROM Agente where usuarioAgente = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, agente.getUsuarioAgente());
        result = pStatement.executeUpdate();
        return result;
    }

    public int calificarAgente(Agente agente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "UPDATE Agente set valoración = ? WHERE usuarioAgente = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, agente.getValoracion());
        result = pStatement.executeUpdate();
        return result;
    }

    public int calificarAgente(int calificacion, String usuarioAgente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO CalificacionesAgente (calificación, Agente_usuarioAgente) VALUES (?, ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, calificacion);
        pStatement.setString(2, usuarioAgente);
        result = pStatement.executeUpdate();
        return result;
    }

    public double obtenerPromedioCalificacionesPropiedad(String usuarioAgente) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT AVG(calificacion) AS promedio FROM CalificacionesAgente WHERE Agente_usuarioAgente = ?";
        double promedio = 0.0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, usuarioAgente);
        ResultSet resultSet = pStatement.executeQuery();
        if(resultSet.next()){
            promedio = resultSet.getDouble("promedio");
        }
        return promedio;
    }
}
