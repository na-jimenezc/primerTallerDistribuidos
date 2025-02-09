import java.io.*;
	import java.net.*;
	import java.util.Scanner;

<<<<<<< HEAD

=======
>>>>>>> dcac7d5 (Subiendo todos los cambios realizados)
	public class codigoCliente {
		public static void main(String[] args) throws IOException {
			
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Sumador de digitos de un vector ");
			System.out.println("\nIngrese el vector separando los números por comas (,):");
			String input = scanner.nextLine();
			
<<<<<<< HEAD
			String datos = input.replace(",", "");
=======
			String datos = input.replace(",","");
>>>>>>> dcac7d5 (Subiendo todos los cambios realizados)
			
			/*Para conectar con el servidor de cálculo*/
			Socket socket = new Socket("10.0.10.159", 5000); 
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println(datos);

			/*Para recibir el resulado final*/
			String respuesta = in.readLine();
			System.out.println("Suma total: " + respuesta);

			socket.close();
		}
	}