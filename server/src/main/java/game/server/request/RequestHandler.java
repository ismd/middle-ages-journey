package game.server.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import game.Online;
import game.character.Character;
import game.server.response.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;

/**
 * @author ismd
 */
public class RequestHandler implements Runnable {

    private final WebSocket ws;
    private final String message;
    
    public static Map<WebSocket, Character> characters = new HashMap<>();

    public RequestHandler(WebSocket ws, String message) {
        this.ws = ws;
        this.message = message;
    }

    @Override
    public void run() {
        Gson gson = new Gson();

        Request request;

        try {
            request = gson.fromJson(message, Request.class);
        } catch (JsonSyntaxException e) {
            Response response = new Response(false, "Неверный формат запроса");
            String json = gson.toJson(response);

            System.out.println("Sending: " + json);
            ws.send(json);
            return;
        }

        try {
            Response response = new RequestRouter().executeRequest(request.setWs(ws));

            if (request.getController().startsWith("Admin")) {
                gson = new GsonBuilder()
                        .create();
            } else {
                gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
            }

            String json = gson.toJson(response.setIdCallback(request.getIdCallback()));

            System.out.println("Sending: " + json);
            ws.send(json);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, e);
        } catch (WebsocketNotConnectedException e) {
            Online.removeUser(Online.users.get(ws));
        }
    }
}
