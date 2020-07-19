package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyTP extends Command {
    public LobbyTP(String name, String permission) {
        super(name, permission, "lobbytp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (pp.hasPermission(this.getPermission())) {
                if(args.length <=0){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.teleport.argument"));
                    return;
                }
                ProxiedPlayer target = Lobbysystem.getInstance().getProxy().getPlayer(args[0]);
                if(target == null){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.teleport.error"));
                    return;
                }
                ServerInfo nsv = Lobbysystem.getRandomLobby();
                if(Lobbysystem.getInstance().getLobbys().contains(pp.getServer().getInfo().getName())){
                    target.connect(pp.getServer().getInfo());
                }else {
                    target.connect(nsv);
                }
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.teleport.sucess")
                .replaceAll("%player%" , target.getName())
                .replaceAll("%server%", nsv.getName()));
                target.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.teleport.player"));

                return;
            }
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));


        }
    }
}
