package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.Lobbysystem;

public class LobbyServer extends Command {
    public LobbyServer(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if (pp.hasPermission("lobby.server")|| pp.hasPermission("lobby.*")) {
                if (args.length <= 0) {
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.server.arguments"));
                    return;
                }
                ServerInfo sv = Lobbysystem.getInstance().getProxy().getServerInfo(args[0]);
                if(sv == null){
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.server.error"));
                    return;
                }
                if(Lobbysystem.getInstance().getLobbys().contains(sv.getName())){
                   pp.connect(sv);
                   pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.server.sucess").replaceAll("%server%", sv.getName()));
                }else {
                    pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.server.error"));
                }

                return;
            }
            pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));

        }
    }
}
