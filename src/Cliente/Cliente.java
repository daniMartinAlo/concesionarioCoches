package Cliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Servidor.ClientesInterfazRMI;
import Servidor.Coche;
import Servidor.ConcesionarioInterfaceRMI;

public class Cliente {
	public static void main(String[] args) {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("192.168.1.54", 5055);
			String URL = "rmi://192.168.1.54:5055/ServicioOperaciones";
			ClientesInterfazRMI clientes = (ClientesInterfazRMI) registry.lookup(URL);
			ConcesionarioInterfaceRMI concesionarioResponsable = (ConcesionarioInterfaceRMI) registry.lookup(URL); 
			
			System.out.println("************* CONCESIONARIO DE COCHES *************");
			boolean reg = false;
			int opcion = 0;
			Coche vehiculo;
			String tipoUsuario;
			Scanner scan = new Scanner(System.in);
			ArrayList<Coche> coches = new ArrayList<Coche>();
			
			do {
				System.out.println("");
				System.out.println("******** MENÚ ********");
				System.out.println("1 - REGISTRARSE");
				System.out.println("2 - LOGUEARSE");
				System.out.println("3 - SALIR");
				System.out.println("¿Que desea hacer?");
				opcion = Integer.parseInt(scan.nextLine());
				/**
				 * Switch que permite al usuario registrarse o loguearse
				 */
				switch (opcion) {
					case 1:	
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//Permite registrar un usuario solicitando un usuario y una contraseña por teclado
						System.out.println("");
						System.out.println("***** MENÚ DE REGISTRO *****");
						System.out.print("Inserte su nombre de usuario: ");
						String nombre = scan.nextLine();
						System.out.print("Inserte contraseña: ");
						String pass = scan.nextLine();
						
						String tipo = null;
						do {
							//Solicita el tipo de usuario y se repite hasta que este sea correcto
							System.out.println("**TIPO DE USUARIO**");
							System.out.println(" 1 - Administrador");
							System.out.println(" 2 - Cliente");
							System.out.println("Seleccione: ");
							tipo = scan.nextLine();
							if(tipo.equals("1")) {
								tipo = "Administrador";
							}else if(tipo.equals("2")) {
								tipo = "Cliente";
							}else {
								System.out.println("Ese tipo no es correcto");
								tipo = null;
							}
						}while (tipo==null);
						//Se llama a la funcion registrarUsuario
						reg = concesionarioResponsable.registrarUsuario(nombre, pass, tipo);
						//Se comprueba si el usuario se ha registrado con exito
						if(reg) {
							System.out.println("Usuario creado con exito");
						}else {
							System.out.println("Error. Ese usuario ya existe");
							reg=true;
						}

						System.out.println("");
						break;
					case 2:
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//Permite al usuario loguearse solicitando su usuario y contraseña
						System.out.println("");
						System.out.println("***** MENÚ DE LOGIN *****");
						System.out.print("Inserte su nombre de usuario: ");
						String name = scan.nextLine();
						System.out.print("Inserte contraseña: ");
						String contra = scan.nextLine();
						//Llamada a la funcion loguearUsuario
						tipoUsuario = concesionarioResponsable.loguearUsuario(name, contra);
						//Si devuelve null, el usuario no esta registrado y le devuelve al menú principal
						if(tipoUsuario == null) {
							System.out.println("Deberá registrarse primero");
						}else {
							//Si esta logueado correctamente se ve que tipo de usuario es
							if(tipoUsuario.equals("Administrador")) {
								//Si es administrador se le da la bienvenida y se muestra su menú con un bucle while que finalizará cuando este pulse salir
								System.out.println("Credenciales correctas, Bienvenid@ Admin");
								try {
									TimeUnit.SECONDS.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								//Aqui empieza el menu para los administradores ----------------------------------------------------------------------------------------------------------------------------------
								opcion = 0;
								/**
								 * Aqui comienza el menú para los administradores
								 */
								do {
									System.out.println("");
									System.out.println("******** MENÚ ********");
									System.out.println("1 - VER TODOS LOS COCHES");
									System.out.println("2 - AÑADIR UN NUEVO COCHE");
									System.out.println("3 - AÑADIR UN NUEVO MOTOR");
									System.out.println("4 - EDITAR UN COCHE");
									System.out.println("5 - ELIMINAR UN COCHE");
									System.out.println("6 - SALIR");
									System.out.println("¿Que desea hacer?");
									opcion = Integer.parseInt(scan.nextLine());
									
									switch (opcion) { 
									    case 1:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("LISTADO DE LOS VEHÍCULOS DEL CONCESIONARIO:");
									    	System.out.println("--------------------------------------------------");
									    	//Llamada a la funcion que lista todos los coches y devuelve un ArrayList con ellos
									    	coches = concesionarioResponsable.listarCoches();
									    	
									    	System.out.println("");
									    	System.out.println("[Mostrando todos los resultados]");
									    	System.out.println("");
									    	//Si el array esta vacio lo indica y sino lo recorre mostrando los registros 
									    	if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado vehículos");
									    	}else {
									    		for(Coche coche : coches) {
										    		System.out.println(coche.toString()); 
										    	}
									    	}
									    	break;
									    case 2:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    //Creamos un objeto coche usando el constructor vacio para poder añadir los datos posteriormente
										    vehiculo = new Coche();
										    
										    System.out.println("");
									    	System.out.println("AÑADIENDO UN NUEVO VEHÍCULO:");
									    	System.out.println("--------------------------------------------------");
									    	System.out.print("INSERTE UNA MATRÍCULA:");
									    	vehiculo.setMatricula(scan.nextLine());
									    	System.out.print("INSERTE UNA MARCA:");
									    	vehiculo.setMarca(scan.nextLine());
									    	System.out.print("INSERTE UN MODELO:");
									    	vehiculo.setModelo(scan.nextLine());
									    	System.out.print("INSERTE AÑO DE FABRICACIÓN:");
									    	//Si nos salta una excepcion NumberFormatException significa que ha introducido un dato que no es numérico y por tanto no se inserta
									    	try {
									    		vehiculo.setAnio(Integer.parseInt(scan.nextLine()));
									    	}catch(NumberFormatException ex) {
									    		System.out.println("No se ha introducido un año correcto");
									    		break;
									    	}
									    	System.out.print("INSERTE COLOR:");
									    	vehiculo.setColor(scan.nextLine());
									    	System.out.print("INSERTE PRECIO:");
									    	try {
									    		vehiculo.setPrecio(Float.parseFloat(scan.nextLine()));
											}catch(NumberFormatException ex) {
									    		System.out.println("No se ha introducido un precio correcto");
									    		break;
									    	}
									    	System.out.print("SELECCIONE ETIQUETA:");
									    	vehiculo.setEtiqueta(scan.nextLine());
									    	System.out.print("CODIGO DE MOTOR:");
									    	vehiculo.setCodMotor(scan.nextLine());
									    	
									    	System.out.println("");
									    	System.out.println(concesionarioResponsable.addCoche(vehiculo));
									    	
									    	break;
									    case 3:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    vehiculo = new Coche();
										    
										    System.out.println("");
									    	System.out.println("AÑADIENDO UN NUEVO MOTOR:");
									    	System.out.println("--------------------------------------------------");
									    	System.out.print("INSERTE CODIGO DE MOTOR:");
									    	vehiculo.setCodMotor(scan.nextLine());
									    	System.out.println("** TIPO DE COMBUSTIBLE **");
									    	System.out.println("1 - Gasolina");
									    	System.out.println("2 - Diesel");
									    	System.out.println("3 - GLP");
									    	System.out.println("4 - GNC");
									    	System.out.println("5 - Eléctrico");
									    	System.out.println("6 - Hidrógeno");
									    	System.out.print("Seleccione: ");
									    	int opcionCombustible = Integer.parseInt(scan.nextLine());
									    	if(opcionCombustible>=1 && opcionCombustible<=6) {
									    		switch(opcionCombustible) {
									    			case 1:
									    				vehiculo.setCombustible("Gasolina");
									    				break;
									    			case 2:
									    				vehiculo.setCombustible("Diesel");
									    				break;
									    			case 3:
									    				vehiculo.setCombustible("GLP");
									    				break;
									    			case 4:
									    				vehiculo.setCombustible("GNC");
									    				break;
									    			case 5:
									    				vehiculo.setCombustible("Electrico");
									    				break;
									    			case 6:
									    				vehiculo.setCombustible("Hidrogeno");
									    				break;
									    		}
									    	}else {
									    		System.out.println("Esa opcion no es correcta");
									    		break;
									    	}
									    		
									    	System.out.print("INSERTE CILINDRADA:");
									    	try {
									    		vehiculo.setCilindrada(Integer.parseInt(scan.nextLine()));
									    	}catch(NumberFormatException ex) {
									    		System.out.println("Eso no es un formato correcto");
									    		break;
									    	}
									    	System.out.print("INSERTE POTENCIA:");
									    	try {
									    		vehiculo.setPotencia(Integer.parseInt(scan.nextLine()));
											}catch(NumberFormatException ex) {
									    		System.out.println("Eso no es un formato correcto");
									    		break;
									    	}
									    	System.out.print("INSERTE NÚMERO DE CILINDROS:");
									    	try {
									    		vehiculo.setnCilindros(Integer.parseInt(scan.nextLine()));
											}catch(NumberFormatException ex) {
									    		System.out.println("Eso no es un formato correcto");
									    		break;
									    	}
									    	System.out.print("INSERTE NÚMERO DE VALVULAS:");
									    	try {
									    		vehiculo.setnValvulas(Integer.parseInt(scan.nextLine()));
											}catch(NumberFormatException ex) {
									    		System.out.println("Eso no es un formato correcto");
									    		break;
									    	}
									    	
									    	System.out.println("");
									    	if(concesionarioResponsable.addMotor(vehiculo)) {
									    		System.out.println("Motor añadido CORRECTAMENTE");
									    	}else {
									    		System.out.println("Error al añadir el motor. Puede que ya exista ese motor");
									    	}
									    	
									    	break;
									    case 4:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("*** EDITANDO UN VEHÍCULO ***");
									    	System.out.println("--------------------------------------------------");
									    	
									    	vehiculo = new Coche();
									    	
										    System.out.print("INSERTE LA MATRÍCULA DEL VEHÍCULO QUE DESEA EDITAR: ");
										    String mat = scan.nextLine();
										    coches = clientes.buscarCoche(mat);
										    
										    if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado resultados para esa Matrícula");
									    	}else {
									    		System.out.println("");
											    System.out.println("MATRÍCULA ACTUAL: "+coches.get(0).getMatricula());
											    System.out.print("NUEVA MATRÍCULA: ");
											    vehiculo.setMatricula(scan.nextLine());
											    System.out.println("MARCA ACTUAL: "+coches.get(0).getMarca());
											    System.out.print("NUEVA MARCA: ");
											    vehiculo.setMarca(scan.nextLine());
											    System.out.println("MODELO ACTUAL: "+coches.get(0).getModelo());
											    System.out.print("NUEVO MODELO: ");
											    vehiculo.setModelo(scan.nextLine());
											    System.out.println("AÑO DE MATRICULACIÓN ACTUAL: "+coches.get(0).getAnio());
											    System.out.print("NUEVO AÑO: ");
											    try {
											    	vehiculo.setAnio(Integer.parseInt(scan.nextLine()));
											    }catch(NumberFormatException ex){
											    	System.out.println("Año incorrecto");
											    	break;
											    }
											    System.out.println("COLOR ACTUAL: "+coches.get(0).getColor());
											    System.out.print("NUEVO COLOR: ");
											    vehiculo.setColor(scan.nextLine());
											    System.out.println("PRECIO ACTUAL: "+coches.get(0).getPrecio());
											    System.out.print("NUEVO PRECIO: ");
											    try {
											    	vehiculo.setPrecio(Float.parseFloat(scan.nextLine()));
											    }catch(NumberFormatException ex){
											    	System.out.println("Precio incorrecto");
											    	break;
											    }
											    System.out.println("ETIQUETA ACTUAL: "+coches.get(0).getEtiqueta());
											    System.out.print("NUEVA ETIQUETA: ");
											    vehiculo.setEtiqueta(scan.nextLine());											    
											
											    if(concesionarioResponsable.editarCoche(mat, vehiculo)) {
										    		System.out.println("COCHE EDITADO CORRECTAMENTE");
										    	}else {
										    		System.out.println("Error al editar el coche.");
										    	}
									    	}
										    break;
									    case 5:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("*** ELIMINANDO UN VEHÍCULO ***");
									    	System.out.println("--------------------------------------------------");
										    System.out.print("INSERTE LA MATRÍCULA DEL VEHÍCULO QUE DESEA ELIMINAR: ");
										    String matriculaEliminar = scan.nextLine();
										    System.out.println("¿ESTA SEGURO DE QUE QUIERE ELIMINARLO? [S|N]");
										    if(scan.nextLine().equalsIgnoreCase("s")) {
										    	if(concesionarioResponsable.eliminarCoche(matriculaEliminar)) {
										    		System.out.println("COCHE ELIMINADO CON ÉXITO");
										    	}else {
										    		System.out.println("ERROR AL ELIMINAR EL COCHE");
										    	}
										    }else {
										    	System.out.println("ELIMINACIÓN CANCELADA");
										    }
									    	break;
									    case 6:
									    	System.out.println("Cerrando sesion de administrador... Gracias");
									    	break;
									    default:
									    	System.out.println("Opcion incorrecta, intentelo de nuevo");
									}
									try {
										TimeUnit.SECONDS.sleep(2);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}while(opcion!=6);
							}else if (tipoUsuario.equals("Cliente")) {
								System.out.println("Credenciales correctas, Bienvenid@");
								try {
									TimeUnit.SECONDS.sleep(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								//Aqui comienzan los clientes -------------------------------------------------------------------------------------------------------------------------------
								opcion = 0;
								/**
								 * Aqui comienza el menú para los clientes
								 */
								do {
									System.out.println("");
									System.out.println("******** MENÚ ********");
									System.out.println("1 - BUSCAR POR MATRÍCULA");
									System.out.println("2 - BUSCAR POR ETIQUETA MEDIOAMBIENTAL");
									System.out.println("3 - BUSCAR POR PRECIO");
									System.out.println("4 - BUSCAR POR TIPO DE COMBUSTIBLE");
									System.out.println("5 - BUSCAR POR POTENCIA DEL MOTOR");
									System.out.println("6 - VER TODOS LOS COCHES DISPONIBLES");
									System.out.println("7 - SALIR");
									System.out.println("¿Que desea hacer?");
									opcion = Integer.parseInt(scan.nextLine());
									
									switch (opcion) { 
									    case 1:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("BUSQUEDA DE VEHICULOS POR MATRÍCULA:");
									    	System.out.println("------------------------------------");
									    	System.out.print("INSERTE UNA MATRÍCULA:");
									    	String matricula = scan.nextLine();
									    	
									    	coches = clientes.buscarCoche(matricula);
									    	
									    	System.out.println("");
									    	System.out.println("[Mostrando resultados para la matrícula "+matricula.toUpperCase()+"]");
									    	System.out.println("");
									    	if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado resultados para esa Matrícula");
									    	}else {
									    		for(Coche coche : coches) {
										    		System.out.println(coche.toString()); 
										    	}
									    	}
									    	break;
									    case 2:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("BUSQUEDA DE VEHICULOS POR ETIQUETA MEDIOAMBIENTAL:");
									    	System.out.println("--------------------------------------------------");
									    	System.out.print("INSERTE UNA ETIQUETA [A - B - C - ECO - 0]:");
									    	String etiqueta = scan.nextLine();
									    	
									    	coches = clientes.buscarEtiqueta(etiqueta);
									    	
									    	System.out.println("");
									    	System.out.println("[Mostrando resultados para Etiqueta "+etiqueta.toUpperCase()+"]");
									    	System.out.println("");
									    	if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado vehículos con esa etiqueta medioambiental");
									    	}else {
									    		for(Coche coche : coches) {
										    		System.out.println(coche.toString()); 
										    	}
									    	}
									    	break;
									    case 3:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("BUSQUEDA DE VEHICULOS POR PRECIO:");
									    	System.out.println("--------------------------------------------------");
									    	System.out.print("INSERTE PRECIO MÍNIMO: ");
									    	float p1 = Float.parseFloat(scan.nextLine()); 
									    	System.out.print("INSERTE PRECIO MÁXIMO: ");
									    	float p2 = Float.parseFloat(scan.nextLine());
									    	
									    	coches = clientes.buscarPrecio(p1, p2);
									    	
									    	System.out.println("");
									    	System.out.println("[Mostrando resultados para rango de precio ("+p1+" - "+p2+")]");
									    	System.out.println("");
									    	if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado vehículos en ese rango de precios");
									    	}else {
									    		for(Coche coche : coches) {
										    		System.out.println(coche.toString()); 
										    	}
									    	}
									    	break;
									    case 4:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("BUSQUEDA DE VEHICULOS POR COMBUSTIBLE:");
									    	System.out.println("--------------------------------------------------");
									    	System.out.print("INSERTE UN TIPO DE COMBUSTIBLE: ");
									    	String combustible = scan.nextLine();
									    	
									    	coches = clientes.buscarCombustible(combustible);
									    	
									    	System.out.println("");
									    	System.out.println("[Mostrando resultados para combustible "+combustible.toUpperCase()+"]");
									    	System.out.println("");
									    	if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado vehículos con ese combustible");
									    	}else {
									    		for(Coche coche : coches) {
										    		System.out.println(coche.toString()); 
										    	}
									    	}
									    	break;
									    case 5:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("BUSQUEDA DE VEHICULOS POR POTENCIA:");
									    	System.out.println("--------------------------------------------------");
									    	System.out.print("INSERTE POTENCIA MÍNIMA: ");
									    	try {
									    		int potencia = Integer.parseInt(scan.nextLine());
									    		
									    		coches = clientes.buscarPotencia(potencia);
										    	
										    	System.out.println("");
										    	System.out.println("[Mostrando resultados para potencia igual o superior a "+potencia+"]");
										    	System.out.println("");
										    	if(coches.isEmpty()) {
										    		System.out.println("No se han encontrado vehículos con esa potencia");
										    	}else {
										    		for(Coche coche : coches) {
											    		System.out.println(coche.toString()); 
											    	}
										    	}
									    	}catch(NumberFormatException e) {
									    		System.out.println("Potencia introducida incorrecta");
									    	}
									    	break;
									    case 6:
									    	//Esperar 1 segundo para aparecer las opciones
										    try {
												TimeUnit.SECONDS.sleep(1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										    System.out.println("");
									    	System.out.println("LISTADO DE LOS VEHÍCULOS DEL CONCESIONARIO:");
									    	System.out.println("--------------------------------------------------");
									    	
									    	coches = concesionarioResponsable.listarCoches();
									    	
									    	System.out.println("");
									    	System.out.println("[Mostrando todos los resultados]");
									    	System.out.println("");
									    	if(coches.isEmpty()) {
									    		System.out.println("No se han encontrado vehículos");
									    	}else {
									    		for(Coche coche : coches) {
										    		System.out.println(coche.toString()); 
										    	}
									    	}
									    	break;
									    case 7:
									    	System.out.println("GRACIAS POR VISITARNOS. VUELVA PRONTO!!");
									    	break;
									    default:
									    	System.out.println("Opcion incorrecta, intentelo de nuevo");
									}
									try {
										TimeUnit.SECONDS.sleep(2);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}while(opcion!=7);
							}
						}

						reg=true;
						break;
					case 3:
						System.out.println("Saliendo del programa...");
						reg=false;
						break;
					default:
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("Opcion no encontrada");
						System.out.println("");
						reg=true;
				}
			}while(reg==true);
					
			
		} catch (RemoteException | NotBoundException e) {
			System.out.println(e.getMessage());
		}
	}
}