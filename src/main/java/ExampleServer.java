import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

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
        //Iterator<String> httpHeaders = handshake.iterateHttpFields();
        System.out.println(handshake.getFieldValue("auth"));
        //while (httpHeaders.hasNext()) {
        //    System.out.println(httpHeaders.next());
        //}
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

            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("test", i);

            server.broadcast(jsonMessage.toString());
            System.out.println("Broadcasted message " + jsonMessage);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
