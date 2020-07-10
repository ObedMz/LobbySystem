package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyReload extends Command {
    public LobbyReload(String name) {
        super(name, "lobby.reload", "lobbyreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.reload")){
                Lobbysystem.getInstance().reloadConfig();
                Lobbysystem.getInstance().saveMessage();
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbyreload"));
            } else{
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
