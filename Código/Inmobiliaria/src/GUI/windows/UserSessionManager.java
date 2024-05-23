package GUI.windows;

import logic.classes.Agente;
import logic.classes.Cliente;

public class UserSessionManager {
    private static UserSessionManager instance;
    private Agente usuarioAgente;
    private Cliente usuarioCliente;

    private UserSessionManager(){}

    public static UserSessionManager getInstance(){
        if(instance == null){
            instance = new UserSessionManager();
        }
        return instance;
    }

    public void loginAgente(Agente agente){
        this.usuarioAgente = agente;
    }

    public void loginCliente(Cliente cliente){
        this.usuarioCliente = cliente;
    }

    public void logoutAgente(){
        this.usuarioAgente = null;
    }

    public void logoutCliente(){
        this.usuarioCliente = null;
    }
    
    public boolean isAgenteLogIn(){
        return usuarioAgente != null;
    }

    public boolean isClienteLogIn(){
        return usuarioCliente != null;
    }

    public Agente getAgenteData(){
        return usuarioAgente;
    }

    public Cliente getClienteData(){
        return usuarioCliente;
    }
}
