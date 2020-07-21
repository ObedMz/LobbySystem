package obed.me.lobbysystem.Command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import obed.me.lobbysystem.LobbyPlayer;
import obed.me.lobbysystem.Lobbysystem;
import obed.me.lobbysystem.Objects.TPMode;
import obed.me.lobbysystem.Objects.Type;


public class Lobby extends Command {
    public Lobby(String name, String permission, String[] aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer pp = (ProxiedPlayer) sender;
            if(!pp.hasPermission(this.getPermission())){
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.nopermission"));
                return;
            }
            if(Lobbysystem.getInstance().getRlobbys().contains(pp.getServer().getInfo().getName())){
                pp.sendMessage(Lobbysystem.getInstance().getMessage("message.lobby.denied"));
                return;
            }
            if(Lobbysystem.getInstance().getMode() == Type.RANDOM) {
                TPMode.tpRandomly(pp);
            }else {
                TPMode.tpLessPlayers(pp);
            }

        }
    }

}
