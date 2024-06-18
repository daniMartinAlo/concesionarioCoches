package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ConcesionarioInterfaceRMI extends Remote {
	
	public ArrayList<Coche> listarCoches() throws RemoteException;
	
	public String addCoche(Coche car) throws RemoteException;
	
	public boolean addMotor(Coche motor) throws RemoteException;
	
	public boolean eliminarCoche(String matricula) throws RemoteException;

	public boolean registrarUsuario(String user, String pass, String tipo) throws RemoteException;
	
	public String loguearUsuario(String user, String pass) throws RemoteException;

	boolean editarCoche(String matricula, Coche car) throws RemoteException;
}
