package wwa;

import java.net.*;
import java.io.*;

public class WarehouseServer {

    public static void main(String[] args) {
        int port = 5050;
        int connections = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            SharedWarehouseState warehouseState = new SharedWarehouseState(1000, 1000);
            System.out.println("WarehouseServer started on port " + port);
            System.out.println("Waiting for clients...\n");

            while (true) {
                Socket socket = serverSocket.accept();
                connections++;
                System.out.println("Client " + connections + " connected.");
                new WarehouseServerThread(socket, warehouseState).start();
            }

        } catch (IOException e) {
            System.err.println("Server failed: " + e.getMessage());
        }
    }
}
