package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataAccess.DatabaseManager;
import logic.classes.Propiedad;

public class PropiedadDAO {
    public int agregarPropiedad(Propiedad propiedad) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSER INTO Propiedad (ubicación, descripción, estadoPropiedad, Agente_Usuario) VALUES (?, ?, ?, ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, propiedad.getUbicacion());
        pStatement.setString(2, propiedad.getDescripcion());
        pStatement.setString(3, propiedad.getEstadoPropiedad());
        pStatement.setString(4, propiedad.getUsuarioAgente());
        result = pStatement.executeUpdate();
        return result;
    }

    public int eliminarPropiedadPorId(Propiedad propiedad) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "DELETE FROM Propiedad where idPropiedad = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, propiedad.getIdPropiedad());
        result = pStatement.executeUpdate();
        return result;
    }
}
