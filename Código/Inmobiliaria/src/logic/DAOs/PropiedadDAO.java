package logic.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.DatabaseManager;
import logic.classes.Propiedad;

public class PropiedadDAO {
    public int agregarPropiedad(Propiedad propiedad) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO propiedad (dirección, descripción, estadoPropiedad, Agente_Usuario, Ciudad, Zona, Tipo, Precio, NoHabitaciones, NoEstancias, NoBaños, Cochera, Tamaño, Resumen, PropiedadEn) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, propiedad.getDireccion());
        pStatement.setString(2, propiedad.getDescripcion());
        pStatement.setString(3, "Disponible");
        pStatement.setString(4, propiedad.getUsuarioAgente());
        pStatement.setString(5, propiedad.getCiudad());
        pStatement.setString(6, propiedad.getZona());
        pStatement.setString(7, propiedad.getTipoPropiedad());
        pStatement.setInt(8, propiedad.getPrecio());
        pStatement.setInt(9, propiedad.getNoHabitaciones());
        pStatement.setInt(10, propiedad.getNoEstancias());
        pStatement.setInt(11, propiedad.getNoBanios());
        pStatement.setInt(12, propiedad.getCochera());
        pStatement.setInt(13, propiedad.getTamanio());
        pStatement.setString(14, propiedad.getResumen());
        pStatement.setString(15, propiedad.getPropiedadEn());

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

    public int actualizarPropiedadPorIdPropiedad(Propiedad propiedad) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "UPDATE propiedad SET dirección = ?, descripción = ?, estadoPropiedad = ?, Ciudad = ?, Zona = ?, Tipo = ?, Precio = ?, NoHabitaciones = ?, NoEstancias = ?, NoBaños = ?, Cochera = ?, Tamaño = ?, Resumen = ?, PropiedadEn = ? WHERE idPropiedad = ?";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, propiedad.getDireccion());
        pStatement.setString(2, propiedad.getDescripcion());
        pStatement.setString(3, propiedad.getEstadoPropiedad());
        pStatement.setString(4, propiedad.getCiudad());
        pStatement.setString(5, propiedad.getZona());
        pStatement.setString(6, propiedad.getTipoPropiedad());
        pStatement.setInt(7, propiedad.getPrecio());
        pStatement.setInt(8, propiedad.getNoHabitaciones());
        pStatement.setInt(9, propiedad.getNoEstancias());
        pStatement.setInt(10, propiedad.getNoBanios());
        pStatement.setInt(11, propiedad.getCochera());
        pStatement.setInt(12, propiedad.getTamanio());
        pStatement.setString(13, propiedad.getResumen());
        pStatement.setInt(14, propiedad.getIdPropiedad());
        pStatement.setString(15, propiedad.getPropiedadEn());

        result = pStatement.executeUpdate();
        return result;
    }

    public ArrayList<Propiedad> buscarPropiedadesDeUsuarioPorUsuarioCliente(String nombreUsuario) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        Propiedad propiedad = new Propiedad();
        ArrayList<Propiedad> propiedades = new ArrayList<>();
        String query = "SELECT resumen, dirección, precio FROM Propiedad WHERE Cliente_UsuarioCliente = ?";
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, nombreUsuario);
        ResultSet resultSet = pStatement.executeQuery();
        while(resultSet.next()){
            String resumen = resultSet.getString("resumen");
            String direccion = resultSet.getString("dirección");
            int precio = resultSet.getInt("precio");
            propiedad = new Propiedad();
            propiedad.setResumen(resumen);
            propiedad.setDireccion(direccion);
            propiedad.setPrecio(precio);
            propiedades.add(propiedad);
        }
        return propiedades;
    }

    public int calificarPropiedad(int calificacion, int propiedadId) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "INSERT INTO CalificacionesPropiedad (calificacion, Propiedad_idPropiedad) VALUES = (?, ?)";
        int result = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, calificacion);
        pStatement.setInt(2, propiedadId);
        result = pStatement.executeUpdate();
        return result;
    }

    public double obtenerPromedioCalificacionesPropiedad(int propiedadId) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT AVG(calificacion) AS promedio FROM CalificacionesPropiedad WHERE Propiedad_idPropiedad = ?";
        double promedio = 0.0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, propiedadId);
        ResultSet resultSet = pStatement.executeQuery();
        if(resultSet.next()){
            promedio = resultSet.getDouble("promedio");
        }
        return promedio;
    }

    public int obtenerIdPropiedadPorDireccion(String direccion) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT idPropiedad FROM Propiedad WHERE dirección = ?";
        int id = 0;
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, direccion);
        ResultSet resultSet = pStatement.executeQuery();
        if(resultSet.next()){
            id = resultSet.getInt("idPropiedad");
        }
        return id;
    }

    public Propiedad obtenerPropiedadPorId(int idPropiedad) throws SQLException{
        DatabaseManager databaseManager = new DatabaseManager();
        String query = "SELECT dirección, descripción, estadoPropiedad, Agente_Usuario, Ciudad, Zona, Tipo, Precio, NoHabitaciones, NoEstancias, NoBaños, Cochera, Tamaño, Resumen, PropiedadEn FROM Propiedad WHERE idPropiedad = ?";
        Propiedad propiedad = new Propiedad();
        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setInt(1, idPropiedad); 
        ResultSet resultSet = pStatement.executeQuery();
        if(resultSet.next()){
            propiedad.setDireccion(resultSet.getString("dirección"));
            propiedad.setDescripcion(resultSet.getString("descripción"));
            propiedad.setEstadoPropiedad(resultSet.getString("estadoPropiedad"));
            propiedad.setUsuarioAgente(resultSet.getString("Agente_Usuario"));
            propiedad.setCiudad(resultSet.getString("Ciudad"));
            propiedad.setZona(resultSet.getString("Zona"));
            propiedad.setTipoPropiedad(resultSet.getString("Tipo"));
            propiedad.setPrecio(resultSet.getInt("Precio"));
            propiedad.setNoHabitaciones(resultSet.getInt("NoHabitaciones"));
            propiedad.setNoEstancias(resultSet.getInt("NoEstancias"));
            propiedad.setNoBanios(resultSet.getInt("NoBaños"));
            propiedad.setCochera(resultSet.getInt("Cochera"));
            propiedad.setTamanio(resultSet.getInt("Tamaño"));
            propiedad.setResumen(resultSet.getString("Resumen"));
            propiedad.setPropiedadEn(resultSet.getString("PropiedadEn"));
        }
        return propiedad;
    }
}
