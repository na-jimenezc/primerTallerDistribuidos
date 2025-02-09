import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorOperacion1 {
    public static void main(String[] args) throws IOException {
		/*Este utiliza el socket 6000, el otro el 7000)*/
        ServerSocket serverSocket = new ServerSocket(6000); 
        System.out.println("Iniciando el servidor de operacion 1...");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    /*Se reciben los datos del servidor de cálculo*/
                    String datos = in.readLine();
                    System.out.println("Datos recibidos del centro de calculo en el operador 1: " + datos);
					
					/*Se ajusta el formato a un array de enteros*/
                    int[] arreglo = Arrays.stream(datos.split(",")).mapToInt(Integer::parseInt).toArray();
					
                    /*Se calcula la suma*/
                    int sumaParcial = Arrays.stream(arreglo).sum();
                    System.out.println("Suma calculada en el operador 1 " + sumaParcial);

                    /*Se envia el resultado*/
					out.println(sumaParcial);
                    System.out.println("Resultado enviado al servidor de calculo: " + sumaParcial);
					
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}