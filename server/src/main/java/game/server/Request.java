package game.server;

import game.character.Character;
import java.util.Map;
import org.java_websocket.WebSocket;

/**
 * @author ismd
 */
public class Request {

    private String controller;
    private String action;
    private Map<String, Object> args;
    private int idCallback;
    private WebSocket ws;

    Request(String controller, String action, Map<String, Object> args) {
        this.controller = controller;
        this.action = action;
        this.args = args;
    }

    public String getController() {
        return controller;
    }

    public String getAction() {
        return action;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public int getIdCallback() {
        return idCallback;
    }

    public Request setWs(WebSocket ws) {
        this.ws = ws;
        return this;
    }

    public WebSocket getWs() {
        return ws;
    }
}
