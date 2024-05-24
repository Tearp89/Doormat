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
        pStatement.setString(3, "Disponible");
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
        pStatement.setString(14, propiedad.getPropiedadEn());
        pStatement.setInt(15, propiedad.getIdPropiedad()); 

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

    public ArrayList<Propiedad> buscarPropiedadesFiltradas(boolean rentar, boolean comprar, boolean departamento, boolean cuarto, boolean casa,
    boolean xalapa, boolean orizaba, boolean veracruz, boolean centro, boolean orillas, boolean residencial, int precioMin, int precioMax,
    int numHabitaciones, int numEstancias, int numBanios, int cochera) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        ArrayList<Propiedad> propiedades = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM propiedad WHERE 1=1");

        if (rentar) queryBuilder.append(" AND PropiedadEn = 'Renta'");
        if (comprar) queryBuilder.append(" AND PropiedadEn = 'Venta'");
        if (departamento) queryBuilder.append(" AND Tipo = 'Departamento'");
        if (cuarto) queryBuilder.append(" AND Tipo = 'Cuarto'");
        if (casa) queryBuilder.append(" AND Tipo = 'Casa'");
        if (xalapa) queryBuilder.append(" AND Ciudad = 'Xalapa'");
        if (orizaba) queryBuilder.append(" AND Ciudad = 'Orizaba'");
        if (veracruz) queryBuilder.append(" AND Ciudad = 'Veracruz'");
        if (centro) queryBuilder.append(" AND Zona = 'Centro'");
        if (orillas) queryBuilder.append(" AND Zona = 'Orilla'");
        if (residencial) queryBuilder.append(" AND Zona = 'Residencial'");
        queryBuilder.append(" AND Precio BETWEEN ? AND ?");
        if (numHabitaciones > 0) queryBuilder.append(" AND NoHabitaciones >= ?");
        if (numEstancias > 0) queryBuilder.append(" AND NoEstancias >= ?");
        if (numBanios > 0) queryBuilder.append(" AND NoBaños >= ?");
        if (cochera > 0) queryBuilder.append(" AND Cochera >= ?");

        Connection connection = databaseManager.getConnection();
        PreparedStatement pStatement = connection.prepareStatement(queryBuilder.toString());

        int paramIndex = 1;
        pStatement.setInt(paramIndex++, precioMin);
        pStatement.setInt(paramIndex++, precioMax);
        if (numHabitaciones > 0) pStatement.setInt(paramIndex++, numHabitaciones);
        if (numEstancias > 0) pStatement.setInt(paramIndex++, numEstancias);
        if (numBanios > 0) pStatement.setInt(paramIndex++, numBanios);
        if (cochera > 0) pStatement.setInt(paramIndex++, cochera);

        ResultSet resultSet = pStatement.executeQuery();
        while (resultSet.next()) {
            Propiedad propiedad = new Propiedad();
            propiedad.setIdPropiedad(resultSet.getInt("idPropiedad"));
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
            propiedades.add(propiedad);
        }
        return propiedades;
    }
}
