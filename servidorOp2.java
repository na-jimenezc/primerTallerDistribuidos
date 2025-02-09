import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorOp2 {
    public static void main(String[] args) throws IOException {
		/*Este utiliza el socket 7000, el otro el 6000)*/
        ServerSocket serverSocket = new ServerSocket(7000); 
        System.out.println("Iniciando el servidor de operacion 2...");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    /*Se reciben los datos del servidor de cálculo*/
                    String datos = in.readLine();
                    System.out.println("Datos recibidos del centro de calculo en el operador 2: " + datos);
					
					/*Como se recibe la primera parte calculada por el servidor de operaciones 1 y el resto del array,
					//para evitar errores se organiza*/
                    String[] partes = datos.split(",");
                    int sumaParcial = Integer.parseInt(partes[0]);
                    int[] arreglo = Arrays.stream(Arrays.copyOfRange(partes, 1, partes.length)).mapToInt(Integer::parseInt).toArray();
					
                    /*Se calcula la suma*/
                    int sumaTotal = sumaParcial + Arrays.stream(arreglo).sum();
                    System.out.println("Parte calculada en el servidor de operacion 2: " + sumaTotal);

                    /*Se envia el resultado*/
					out.println(sumaTotal);
                    System.out.println("Resultado enviado al servidor de calculo: " + sumaTotal);
					
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}