package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyCreate extends Command {
    public LobbyCreate(String name, String permission) {
        super(name, permission , "lobbycreate");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(pp.hasPermission(this.getPermission())){
                ServerInfo sv = pp.getServer().getInfo();
                if(Lobbysystem.getInstance().getLobbys().contains(sv.getName())){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.islobby"));
                    return;
                }
                Lobbysystem.getInstance().getLobbys().add(sv.getName());
                Lobbysystem.getInstance().saveLobbysinConfig();
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.create"));
            } else {
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
            }
        }
    }
}
