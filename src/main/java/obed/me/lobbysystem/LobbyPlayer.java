package obed.me.lobbysystem;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class LobbyPlayer {
    private ProxiedPlayer pp;
    private boolean waiting = false;
    public static HashMap<ProxiedPlayer, LobbyPlayer> lobbyPlayer = new HashMap<ProxiedPlayer, LobbyPlayer>();

    public LobbyPlayer(ProxiedPlayer pp) {
        lobbyPlayer.put(pp, this);
    }

    public static LobbyPlayer getLobbyPlayer(ProxiedPlayer pp){
        if(lobbyPlayer.containsKey(pp)){
            return lobbyPlayer.get(pp);
        }
        return new LobbyPlayer(pp);
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
}
