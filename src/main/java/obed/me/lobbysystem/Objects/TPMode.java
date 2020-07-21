package obed.me.lobbysystem.Objects;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import obed.me.lobbysystem.LobbyPlayer;
import obed.me.lobbysystem.Lobbysystem;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Collections.*;

public class TPMode {
    public static void tpRandomly(ProxiedPlayer pp) {
        ServerInfo sv = Lobbysystem.getRandomLobby();
        if(sv == null) {
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.error"));
            return;
        }
        if(Lobbysystem.getInstance().getLobbys().contains(pp.getServer().getInfo().getName())){
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.already"));
            return;
        }
        if(pp.getServer().getInfo() != sv){
            if(!Lobbysystem.getInstance().isRunnable()){
                pp.connect(sv);
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.tp"));
                return;
            }
            LobbyPlayer lbp = LobbyPlayer.getLobbyPlayer(pp);
            if(lbp.isWaiting()){
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.cancel"));
                lbp.setWaiting(false);
                lbp.getTask().cancel();
                return;
            }
            lbp.setWaiting(true);
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.waiting").replaceAll("%time%", Integer.toString(Lobbysystem.getInstance().time)));
            lbp.transportWithRunnable(sv);
            return;
        }
        pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.already"));
    }

    public static void tpLessPlayers(ProxiedPlayer pp) {
        if(Lobbysystem.getInstance().getLobbys().contains(pp.getServer().getInfo().getName())){
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.already"));
            return;
        }
        ArrayList<ServerInfo>lobbylist = new ArrayList<ServerInfo>();
        for(String sv : Lobbysystem.getInstance().getLobbys()){
            lobbylist.add(Lobbysystem.getInstance().getProxy().getServerInfo(sv));
        }
        lobbylist.sort(new ShortUtil());
        for(ServerInfo sv : lobbylist){
            pp.sendMessage(sv.getName() + "===" + Integer.toString(sv.getPlayers().size()));
        }
        /// getting the server
        ServerInfo sv = lobbylist.get(0);
        if(sv == null) {
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.error"));
            return;
        }
        if(pp.getServer().getInfo() != sv){
            if(!Lobbysystem.getInstance().isRunnable()){
                pp.connect(sv);
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.tp"));
                return;
            }
            LobbyPlayer lbp = LobbyPlayer.getLobbyPlayer(pp);
            if(lbp.isWaiting()){
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.cancel"));
                lbp.setWaiting(false);
                lbp.getTask().cancel();
                return;
            }
            lbp.setWaiting(true);
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.waiting").replaceAll("%time%", Integer.toString(Lobbysystem.getInstance().time)));
            lbp.transportWithRunnable(sv);
            return;
        }
        pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.already"));


    }
}
