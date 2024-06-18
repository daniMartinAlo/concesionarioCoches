package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientesInterfazRMI extends Remote{
	public ArrayList<Coche> buscarCoche(String matricula) throws RemoteException;

	public ArrayList<Coche> buscarEtiqueta(String etiqueta) throws RemoteException;
	
	public ArrayList<Coche> buscarPrecio(float p1, float p2) throws RemoteException;
	
	public ArrayList<Coche> buscarCombustible(String combustible) throws RemoteException;
	
	public ArrayList<Coche> buscarPotencia(int combustible) throws RemoteException;
}
