import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;

import static spark.Spark.*;

@WebSocket
public class ServerWebSocketHandler {
    private static String data;
    private static String key;

    public static void main(String[] args) {
        port(3000);
        //get("/", (req, res) -> "Hello World");
        data = "asd";
        key = "asd";

        Spark.staticFileLocation("/public"); //index.html is served at localhost:4567 (default port)
        Spark.port(3000);
        Spark.webSocket("/chat", ServerWebSocketHandler.class);
        Spark.init();
    }

    private String sender, msg;

    private static ServerDriver serverDriver;

    public ServerWebSocketHandler() {
        serverDriver = new ServerDriver(data, key);
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + Chat.nextUserNumber++;
        Chat.userUsernameMap.put(user, username);
        Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = Chat.userUsernameMap.get(user);
        Chat.userUsernameMap.remove(user);
        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Chat.broadcastMessage(sender = Chat.userUsernameMap.get(user), msg = message);
    }
}