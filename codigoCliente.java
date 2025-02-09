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

			/*Para conectar con el servidor de cálculo*/
			Socket socket = new Socket("25.64.244.102", 5000); 
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println(datos);

			/*Para recibir el resulado final*/
			String respuesta = in.readLine();
			System.out.println("Suma total: " + respuesta);

			socket.close();
		}
	}