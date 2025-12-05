package wwa;


import java.net.*;
import java.io.*;


public class CustomerAClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5050;   


    public static void main(String[] args) {
        System.out.println("Customer A client starting...");
        System.out.println("Connected to warehouse as Customer A.");
        System.out.println("Allowed commands:");
        System.out.println("  CHECK_STOCK");
        System.out.println("  BUY_APPLES,<number>");
        System.out.println("  BUY_ORANGES,<number>");
        System.out.println("  QUIT");
        System.out.println();

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in))) {

            String command;

            while (true) {
                System.out.print("CustomerA> ");
                command = console.readLine();
                if (command == null) {
                    break; 
                }
                command = command.trim();
                if (command.isEmpty()) {
                    continue; 
                }

                
                out.println(command);

                
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println("Server: " + response);

                if (command.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Customer A error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Customer A client terminated.");
    }
}
