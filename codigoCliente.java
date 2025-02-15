    import java.io.*;
	import java.net.*;
	import java.util.Scanner;

	public class codigoCliente {
		public static void main(String[] args) throws IOException {
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Sumador de digitos de un vector ");
			System.out.println("\nIngrese el vector separando los números por comas (,):");
			String input = scanner.nextLine();
		
			String datos = input;


			while(true){
				try {
					/*Intentar conectar con el servidor de cálculo*/

					/*Se pone la ip y el socket*/
					Socket socket = new Socket("25.64.244.102", 5000);
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
					/*Enviar datos*/
					out.println(datos);
	
					/*Se recibe el resultado final*/
					String respuesta = in.readLine();
					System.out.println("Suma total: "+respuesta);
	
					/*Se cierra la conexión cerrando el socket y se sale del while*/
					socket.close();
					break;

				}catch(IOException e){
					System.out.println("El centro de cálculo no se encuentra disponible en este momento.");
					System.out.println("¿Quiere seguir intentando? (s/n)");

					String opcion = scanner.nextLine().trim().toLowerCase();
					
					if (!opcion.equals("s")) {
						System.out.println("Intento finalizado, no fue posible establecer la conexión con el centro de cálculo");
						break;
					}
				}
			}

			scanner.close();

		}
	}