	import java.io.*;
	import java.net.*;
	import java.util.*;
	import java.util.Scanner;


	public class centroDeCalculo {
		public static void main(String[] args) throws IOException {
			ServerSocket serverSocket = new ServerSocket(5000);
			System.out.println("Servidor de cálculo iniciado...");

			while (true) {
				Socket clienteSocket = serverSocket.accept();
				new Thread(() -> {
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
						PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);

						/*Se reciben los datos del cliente y se convierten los datos en una arreglo*/
						String datosCliente = in.readLine();
						System.out.println("Datos recibidos del cliente: " + datosCliente);
						int[] arreglo = Arrays.stream(datosCliente.split(",")).mapToInt(Integer::parseInt).toArray();
											  
						/*Para que un operador haga una parte, y el otro la otra, se propone dividir el arreglo en 
						//la mitad, y en caso de que sea impar a un operador le toca hacer uno más*/
						int mitad = (arreglo.length + 1)/2; 
						int[] parte1 = Arrays.copyOfRange(arreglo, 0, mitad);
						int[] parte2 = Arrays.copyOfRange(arreglo, mitad, arreglo.length);
						
						
						/*Acá está el manejo de errores, que proponemos que es que el centro de cálculo pueda
						calcular cada mitad por si mismo, se hace con una función auxiliar más abajo*/
						int suma1 = sumarArreglo(parte1); 
						int suma2 = sumarArreglo(parte2); 
						
						int sumaTotal=0;
						
						/*Intento para enviar parte parcial a1 al operador 1, se hace a prueba de fallos
						//al igualar los valores con la suma parcial*/
						int sumaParcial = suma1;
						try {
							/*Se envia la parte 1 al ServidorOperacion1 y se obtiene su suma parcial*/
							sumaParcial = enviarParte("192.168.1.30", 6000, parte1);
							System.out.println("Suma parcial recibida de servidor de operacion 1: " + sumaParcial);
						} catch (IOException e) {
							System.out.println("El servidor de operacion 1 no responde.Se usa la suma local: " + suma1);
						}
						
						/*Intento para enviar parte parcial del operador 1 al operador 2*/
						try {
							sumaTotal = enviarParcial("192.168.1.40", 7000, sumaParcial, parte2);
							System.out.println("Suma total recibida del servidor de operacion 2: " + sumaTotal);
						} catch (IOException e) {
							System.out.println("El servidor de operacion 2 no responde.Se usa la suma local: " + suma2);
							/*En caso de que no funcione se usa la suma total*/
							sumaTotal = suma1 + suma2;
						}

						/*Se envia el resultado al cliente y se cierra el socket*/
						out.println(sumaTotal);
						System.out.println("Suma total enviada al cliente: " + sumaTotal);

						clienteSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
			}
		}

	/*Función auxiliar para enviar la parte 1 del arreglo*/
    private static int enviarParte(String ip, int puerto, int[] datos) throws IOException {
        try (Socket socket = new Socket(ip, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            /*Se envian los datos como cadena*/
            out.println(Arrays.toString(datos).replaceAll("[\\[\\] ]", ""));
            return Integer.parseInt(in.readLine());
        }
    }
	
	/*Función auxiliar para enviar la suma parcial y la segunda parte del arreglo al servidor operador 2*/
    private static int enviarParcial(String ip, int puerto, int sumaParcial, int[] datos) throws IOException {
        try (Socket socket = new Socket(ip, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            /*Se envia la suma parcial y los datos como cadena*/
            String datosEnviar = sumaParcial + "," + Arrays.toString(datos).replaceAll("[\\[\\] ]", "");
            out.println(datosEnviar);
            return Integer.parseInt(in.readLine());
        }
    }

		
		/*Función auxiliar para que el centro de cálculo calcule por si misma la suma de 
		cada mitad del array*/
		private static int sumarArreglo(int[] arreglo) {
			return Arrays.stream(arreglo).sum();
		}
	}