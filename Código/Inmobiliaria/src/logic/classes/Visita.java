package logic.classes;

import java.time.LocalDate;

public class Visita {
    private int idVisita;
    private LocalDate fecha;
    private String usuarioAgente;
    private String usuarioCliente;
    private int idPropiedad;
    
    public int getIdVisita() {
        return idVisita;
    }
    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
    public int getIdPropiedad() {
        return idPropiedad;
    }
    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }
}
