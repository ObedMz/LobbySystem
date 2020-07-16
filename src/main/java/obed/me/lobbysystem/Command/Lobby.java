package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import obed.me.lobbysystem.LobbyPlayer;
import obed.me.lobbysystem.Lobbysystem;

import java.util.concurrent.TimeUnit;


public class Lobby extends Command {
    public Lobby(String name, String permission, String[] aliases) {
        super(name, permission, aliases);
    }
    private ScheduledTask task;
    private Integer time = 0;
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(!pp.hasPermission(this.getPermission())){
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
                return;
            }
            ServerInfo sv = Lobbysystem.getRandomLobby();
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

}
