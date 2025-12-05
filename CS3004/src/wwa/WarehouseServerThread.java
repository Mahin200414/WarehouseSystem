package wwa;

import java.net.*;
import java.io.*;

public class WarehouseServerThread extends Thread {

    private Socket socket;
    private SharedWarehouseState warehouseState;

    public WarehouseServerThread(Socket socket, SharedWarehouseState warehouseState) {
        this.socket = socket;
        this.warehouseState = warehouseState;
        System.out.println("Client connected from " + socket.getInetAddress());
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                out.println(process(line.trim()));
                if (line.equalsIgnoreCase("QUIT")) break;
            }

            socket.close();
            System.out.println("Connection closed.");
        
        } catch (IOException e) {
            System.err.println("Client handler error: " + e.getMessage());
        }
    }

    private String process(String r) {
        String[] p = r.split(",");
        return switch (p[0].toUpperCase()) {
            case "CHECK_STOCK"  -> warehouseState.checkStock();
            case "BUY_APPLES"   -> warehouseState.buyApples(Integer.parseInt(p[1]));
            case "BUY_ORANGES"  -> warehouseState.buyOranges(Integer.parseInt(p[1]));
            case "ADD_APPLES"   -> warehouseState.addApples(Integer.parseInt(p[1]));
            case "ADD_ORANGES"  -> warehouseState.addOranges(Integer.parseInt(p[1]));
            case "QUIT"         -> "OK,Closing connection";
            default             -> "ERROR,Unknown command";
        };
    }
}
