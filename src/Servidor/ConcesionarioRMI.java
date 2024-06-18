package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConexionBBDD.Conexion;

public class ConcesionarioRMI extends UnicastRemoteObject implements ConcesionarioInterfaceRMI, ClientesInterfazRMI {
	private static final long serialVersionUID = 3561748608659509904L;
	Conexion sql = new Conexion();
	Connection con = (Connection) sql.conectarMySQL();
	private ArrayList<Coche> coches = new ArrayList<Coche>();

	protected ConcesionarioRMI() throws RemoteException {
		super();

	}
	/**
	 * Esta funcion permite ejectar una query en la base de datos y devuelve un arraylist con el resultado de la consulta, esta funcion es utilizada por otras funciones
	 */
	public ArrayList<Coche> datosCoches(String query) throws RemoteException {
		// Conseguir recuperar todos los elementos de la consulta e imprimirlos por
		// pantalla
		try {
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String matricula = (rs.getString("matricula"));
				String marca = (rs.getString("marca"));
				String modelo = (rs.getString("modelo"));
				int anio = (rs.getInt("anio"));
				String color = (rs.getString("color"));
				float precio = (rs.getFloat("precio"));
				String etiqueta = (rs.getString("etiqueta"));
				String codMotor = (rs.getString("codMotor"));
				// Tabla motor
				String combus = (rs.getString("combustible"));
				int cc = (rs.getInt("cilindrada"));
				int pot = (rs.getInt("potencia"));
				int nCil = (rs.getInt("nCilindros"));
				int nVal = (rs.getInt("nValvulas"));

				coches.add(new Coche(matricula, marca, modelo, anio, color, precio, etiqueta, codMotor, combus, cc, pot,
						nCil, nVal));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coches;
	}

	/**
	 * Esta funcion recive una matricula y llama a la funcion datosCoches que ejecuta la consulta y nos devuelve la solución
	 */
	@Override
	public ArrayList<Coche> buscarCoche(String placa) throws RemoteException {
		// Vaciamos el ArrayList
		coches.clear();
		// Sentencia que busca las matriculas en la BBDD (El INNER JOIN nos permite
		// mostrar los daos de dos tablas unidas por clave foranea)
		String query = "SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where matricula = '"+ placa +"';";

		coches = datosCoches(query);
		return coches;
	}

	/**
	 * Funcion que recibe una etiqueta medioambiental y manda la consulta a la funcion datosCoches
	 */
	@Override
	public ArrayList<Coche> buscarEtiqueta(String etiqueta) throws RemoteException {
		// Vaciamos el ArrayList
		coches.clear();
		// Sentencia que busca los coches dependiendo de su etiqueta medioabiental
		String query = "SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where etiqueta = '"+ etiqueta +"';";

		coches = datosCoches(query);
		return coches;
	}

	/**
	 * Funcion que recibe dos cantidades de dinero y muestra los vehiculos comprendidos entre ellas
	 */
	@Override
	public ArrayList<Coche> buscarPrecio(float p1, float p2) throws RemoteException {
		// Vaciamos el ArrayList
		coches.clear();
		// Sentencia que busca los vehiculos por un rango de precio dado por el usuario
		String query = "SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo WHERE precio BETWEEN "+ p1 +" AND "+ p2 +";";

		coches = datosCoches(query);
		return coches;
	}

	/**
	 * Funcion que recibe el tipo de combustible y devuelve un ArrayList con los coches que lo utilicen
	 */
	@Override
	public ArrayList<Coche> buscarCombustible(String combustible) throws RemoteException {
		// Vaciamos el ArrayList
		coches.clear();
		// Sentencia que busca por combustible
		String query = "SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where motor.combustible = '"+ combustible +"';";

		coches = datosCoches(query);
		return coches;
	}

	/**
	 * Funcion que permite buscar los coches por potencia
	 */
	@Override
	public ArrayList<Coche> buscarPotencia(int potencia) throws RemoteException {
		// Vaciamos el ArrayList
		coches.clear();
		// Sentencia que busca los coches cuya potencia es superior o igual a la seleccionada por el usuario
		String query = "SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where motor.potencia >= '"+ potencia +"';";

		coches = datosCoches(query);
		return coches;
	}

	/**
	 * Funcion que lista todos los coches
	 */
	@Override
	public ArrayList<Coche> listarCoches() throws RemoteException {
		// Vaciamos el ArrayList
		coches.clear();
		// Sentencia que lista todos los vehiculos
		String query = "SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo;";

		coches = datosCoches(query);
		return coches;
	}

	//A estos metodos solamente podran acceder los empleados del concesionario 
	/**
	 * Fncion que evalua si existe el motor en la base de datos
	 * @param query
	 * @return
	 */
	public boolean existeMotor(String query) {
		try {
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * Funcion que permite añadir un objeto coche en la base de datos
	 */
	@Override
	public String addCoche(Coche car) throws RemoteException {
			        
	    String query = "INSERT INTO coches(matricula, marca, modelo, anio, color, precio, etiqueta, codMotor) VALUES(?,?,?,?,?,?,?,?)";
	      
	    boolean existeMotor = existeMotor("select codigo from motor where codigo='"+car.getCodMotor()+"'");
	    
	    if(existeMotor) {
	    	try {
	            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
	            
	            ps.setString(1, car.getMatricula());
	            ps.setString(2, car.getMarca());
	            ps.setString(3, car.getModelo());
	            ps.setInt(4, car.getAnio());
	            ps.setString(5, car.getColor());
	            ps.setFloat(6, car.getPrecio());
	            ps.setString(7, car.getEtiqueta());
	            ps.setString(8, car.getCodMotor());
	            
	            ps.executeUpdate();
	                
	            ps.close();
		    } catch(SQLException e) {
		    	e.printStackTrace();
		    }
	    	return "Coche añadido correctamente";
	    }else {
	    	return "Error, ese codigo de motor no se encuentra en la Base de Datos";
	    }
	}

	/**
	 * Funcion que permite editar un coche de la base de datos, se le pasa la matricula y los nuevos datos
	 */
	@Override
	public boolean editarCoche(String matricula, Coche car) throws RemoteException {
		String query = "UPDATE coches SET matricula=?, marca=?, modelo=?, anio=?, color=?, precio=?, etiqueta=? WHERE matricula = '"+matricula+"';";
        
        try {
	    	PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
        	
        	ps.setString(1, car.getMatricula());
            ps.setString(2, car.getMarca());
            ps.setString(3, car.getModelo());
            ps.setInt(4, car.getAnio());
            ps.setString(5, car.getColor());
            ps.setFloat(6, car.getPrecio());
            ps.setString(7, car.getEtiqueta());
	    	
	    	ps.executeUpdate();
	        
	        ps.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    	return false;
	    }
       return true;
	}

	/**
	 * Funcion que permite eliminar un coche de la base de datos
	 */
	@Override
	public boolean eliminarCoche(String matricula) throws RemoteException {
		String query = "DELETE FROM coches WHERE matricula='"+matricula+"';";

	    try {
	    	PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
	    	ps.executeUpdate();
	        
	        ps.close();
	        return true;
	    } catch (Exception e) {
	        System.out.println("Error");
	    }
	    return false;
	}
	
	/**
	 * Funcion que permite añadir un nuevo motor en la base de datos
	 */
	@Override
	public boolean addMotor(Coche motor) throws RemoteException {
		 String query = "INSERT INTO motor(codigo, combustible, cilindrada, potencia, nCilindros, nValvulas) VALUES(?,?,?,?,?,?)";
	      
		 boolean existeMotor = existeMotor("select codigo from motor where codigo='"+motor.getCodMotor()+"'");
		 
		 if(!existeMotor) {
			 try {
		            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
		            
		            ps.setString(1, motor.getCodMotor());
		            ps.setString(2, motor.getCombustible());
		            ps.setInt(3, motor.getCilindrada());
		            ps.setInt(4, motor.getPotencia());
		            ps.setInt(5, motor.getnCilindros());
		            ps.setInt(6, motor.getnValvulas());
		            
		            ps.executeUpdate();
		                
		            ps.close();	            
		            return true;
			    } catch(SQLException e) {
			    	e.printStackTrace();
			    }
		 }
		 return false;
		 
	}

	/**
	 * Funcion que realiza una consulta a la base de datos solicitando todos los nombres de estos
	 * @param nombre
	 * @return
	 * @throws RemoteException
	 */
	//Registro y login de usuario
	public boolean mostrarUsuarios(String nombre) throws RemoteException {
		try {
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select nombreUsuario from usuarios where nombreUsuario='"+nombre+"';");
			
			if (rs.next()) {
				return false;
			}else {
				return true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * Funcion que permite registrar un usuario en la base de datos
	 */
	@Override
	public boolean registrarUsuario(String user, String pass, String tipo) throws RemoteException {
	
		if(mostrarUsuarios(user)) {
			String query = "insert into usuarios(nombreUsuario, contrasenia, tipoUsuario) values(?,SHA1(?), ?);";
			
			try {
				PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
		         
	            ps.setString(1, user);
	            ps.setString(2, pass);
	            ps.setString(3, tipo);
	         
	            ps.executeUpdate();
	            
	            ps.close();
		        } catch(SQLException e) {
		            e.printStackTrace();
		            return false;
		        }
			
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Fncion que permite a un usuario loguearse
	 */
	@Override
	public String loguearUsuario(String user, String pass) throws RemoteException {
		String query = "select tipoUsuario from usuarios where nombreUsuario='"+user+"' and contrasenia = SHA('"+pass+"');";
		
		try {
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				return rs.getString("tipoUsuario");	
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
