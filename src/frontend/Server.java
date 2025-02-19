package frontend;

import backend.Answer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static int portServer = 1239;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(portServer);
            System.out.println("Server Running On Port " + portServer);

            while (true) {  // Keep listening for new clients
                Socket s = ss.accept(); // Accept a new client
                System.out.println("Client Connected: " + s.getInetAddress());

                // Handle the client in a new thread
                new ClientHandler(s).start();
            }
        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Server Error", e);
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            String message;
            while (true) {
                message = dis.readUTF();
                if (message.equalsIgnoreCase("/quit")) {
                    System.out.println("Client Disconnected: " + socket.getInetAddress());
                    break; // Exit loop and close client connection
                }

                Answer answer = new Answer();
                ArrayList<Answer> list = answer.filterAsk(message);
                String response;

                if (list.isEmpty()) {
                    response = "Bot: I don't understand";
                } else {
                    response = "Bot: " + answer.cariLur(list.get(0).getCategory());
                }

                System.out.println("Client: " + message);
                System.out.println(response);

                dos.writeUTF(response);
            }

            // Close streams and socket
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, "Client Error", e);
        }
    }
}
