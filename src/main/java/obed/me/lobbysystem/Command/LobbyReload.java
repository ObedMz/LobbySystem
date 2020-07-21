package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyReload extends Command {
    public LobbyReload(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.reload")|| pp.hasPermission("lobby.*")){
                Lobbysystem.getInstance().reloadConfig();

                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.reload"));
            } else{
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
