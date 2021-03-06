package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyRemove extends Command {
    public LobbyRemove(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.remove")||pp.hasPermission("lobby.*")) {
                if(args.length <=0){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.remove.arguments"));
                    return;
                }
                ServerInfo sv = Lobbysystem.getInstance().getProxy().getServerInfo(args[0]);
                if(Lobbysystem.getInstance().getLobbys().contains(sv.getName())){
                    Lobbysystem.getInstance().getLobbys().remove(sv.getName());
                    Lobbysystem.getInstance().saveLobbysinConfig();
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.remove.message"));
                }else{
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.remove.error"));
                }

            }else{
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }

        }
    }
}
