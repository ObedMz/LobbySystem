package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyRemove extends Command {
    public LobbyRemove(String name) {
        super(name, "lobby.remove", "lobbydelete");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission("lobby.remove")){
                ServerInfo sv = pp.getServer().getInfo();
                if(Lobbysystem.lobbys.contains(sv.getName())){
                    Lobbysystem.lobbys.remove(sv.getName());
                    Lobbysystem.getInstance().saveLobbysinConfig();
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobbyremove"));
                }else{
                    pp.sendMessage(ChatColor.RED + "Este servidor no esta establecido como lobby.");
                }
            } else {
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
