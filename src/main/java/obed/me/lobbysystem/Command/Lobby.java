package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import obed.me.lobbysystem.ConfigManager.ConfigManager;
import obed.me.lobbysystem.LobbyPlayer;
import obed.me.lobbysystem.Lobbysystem;
import sun.security.krb5.Config;

import java.util.concurrent.TimeUnit;


public class Lobby extends Command {
    public Lobby(String name) {
        super(name, "lobby.use", "lobby", "hub", "salir", "leave");
    }
    private ScheduledTask task;
    private ConfigManager config;
    private Integer time = 0;
    @Override
    public void execute(CommandSender sender, String[] args) {
        config = new ConfigManager();
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(!pp.hasPermission("lobby.use")){
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
                return;
            }
            ServerInfo sv = Lobbysystem.getRandomLobby();
            if(sv != null){
                if(pp.getServer().getInfo() != sv){
                    if(!Lobbysystem.getInstance().runnable){
                        pp.connect(sv);
                        pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbytp"));
                        return;
                    }
                    LobbyPlayer lbp = LobbyPlayer.getLobbyPlayer(pp);
                    if(lbp.isWaiting()){
                        pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbycancel"));
                        lbp.setWaiting(false);
                        task.cancel();
                        return;
                    }
                    lbp.setWaiting(true);
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbywaiting").replaceAll("%time%", Integer.toString(Lobbysystem.getInstance().time)));
                    task = Lobbysystem.getInstance().getProxy().getScheduler().schedule((Plugin) Lobbysystem.getInstance(), () ->{
                        if(time >= Lobbysystem.getInstance().time){
                            pp.connect(sv);
                            lbp.setWaiting(false);
                            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbytp"));
                            time = 0;
                            task.cancel();
                        }
                        time++;
                    },0,1, TimeUnit.SECONDS);
                    return;
                }
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbyalready"));
            }else{
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbyerror"));
            }


        }
    }

}
