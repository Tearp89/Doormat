package logic.classes;

public class Propiedad {
    private int idPropiedad; 
    private String ubicacion;
    private String descripcion;
    private String estadoPropiedad;
    private String usuarioAgente;
    private String usuarioCliente;
    private String etiquetas;

    public String getEtiquetas() {
        return etiquetas;
    }
    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }
    public int getIdPropiedad() {
        return idPropiedad;
    }
    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEstadoPropiedad() {
        return estadoPropiedad;
    }
    public void setEstadoPropiedad(String estadoPropiedad) {
        this.estadoPropiedad = estadoPropiedad;
    }
    public String getUsuarioAgente() {
        return usuarioAgente;
    }
    public void setUsuarioAgente(String usuarioAgente) {
        this.usuarioAgente = usuarioAgente;
    }
    public String getUsuarioCliente() {
        return usuarioCliente;
    }
    public void setUsuarioCliente(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }
}
