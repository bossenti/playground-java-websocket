import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static java.lang.Thread.sleep;

public class ExampleServer extends WebSocketServer {

    public ExampleServer(int port) {
        super(new InetSocketAddress(port));
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //conn.send("Welcome!"); //This method sends a message to the new client
        //broadcast("New connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected.");
        System.out.println(handshake.getResourceDescriptor());
        System.out.println(handshake);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(conn + ": " + message);
        broadcast(message);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
    }

    public static void main(String [] args) {
        ExampleServer server = new ExampleServer(3333);
        server.setReuseAddr(true);
        server.start();

        for (int i = 0; i<600; i++) {
            server.broadcast("{ \"test\": " + i + "}");
            System.out.println("Broadcasted message " + i);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
