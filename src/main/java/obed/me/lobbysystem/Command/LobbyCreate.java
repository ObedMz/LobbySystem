package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyCreate extends Command {
    public LobbyCreate(String name){
        super(name, "lobby.create", "setlobby", "setauth", "sethub");

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.create")){
               ServerInfo sv = pp.getServer().getInfo();
                Lobbysystem.lobbys.add(sv.getName());
                Lobbysystem.getInstance().saveLobbysinConfig();
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbycreate"));
            } else {
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
