package Servidor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
	public static void main(String[] args) {
		String host;
		int puerto = 5055;
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("No se ha podido obtener la dirección IP");
			return;
		}
		try {
			// Creamos registro de los objetos remotos
			Registry registro = LocateRegistry.createRegistry(puerto);
			// Crear un objeto de tipo OperacionesImplementacion 
			ConcesionarioRMI concesionario = new ConcesionarioRMI();
			String URL = "rmi://" + host + ":" + puerto + "/ServicioOperaciones";
			// Registramos el nuevo objeto,
			registro.rebind(URL, concesionario);
		} catch (RemoteException e) {
			System.out.println("No se ha podido registrar el servicio");
			System.out.println(e.getMessage());
		}
	}
}
