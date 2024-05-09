import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader inputConsole;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");

            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";
            while (!line.equals("exit")) {
                line = inputConsole.readLine();
                out.println(line);
                System.out.println(in.readLine());
            }

            socket.close();
            inputConsole.close();
            out.close();
        } catch (UnknownHostException u) {
            System.out.println("Unknown host " + u.getMessage());
        } catch (IOException i) {
            System.out.println("Unexpected exception " + i.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
    }
}