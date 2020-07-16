package obed.me.lobbysystem;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import obed.me.lobbysystem.Command.*;
import obed.me.lobbysystem.ConfigManager.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Lobbysystem extends Plugin {
    private ConfigurationProvider cp = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private ConfigManager config;
    private static Lobbysystem instance;
    public int time;
    private boolean runnable;
    private static List<String> Globbys = new ArrayList<String>();
    @Override
    public void onEnable() {
      instance = this;
      config = new ConfigManager();
      config.registerConfig();
      config.registerMessage();
      loadDirectory();
        //loading lobbies
        loadLobbysfromConfig();
        runnable = Boolean.parseBoolean(config.getConfig().getString("config.countdown"));
        time = Integer.parseInt(config.getConfig().getString("config.time"));
        loadCommands();
    }
    public void loadLobbysfromConfig(){
        Globbys.clear();
        List<String> lobbys = config.getConfig().getStringList("config.lobbys");
        for(String servers : lobbys){
            if(Lobbysystem.getInstance().getProxy().getServers().containsKey(servers)){
                Globbys.add(servers);
            }
        }
    }
    public void saveLobbysinConfig(){
        File file = new File(getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            cgf.set("config.lobbys", Globbys);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "config.yml"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveMessage(){
        File file = new File(getDataFolder(), "message.yml");
        try {
            Configuration cgf = cp.load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "message.yml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig(){
        config.reloadConfig();
        loadLobbysfromConfig();
    }

    public static ServerInfo getRandomLobby(){
        Random random = new Random();
        if(Globbys.size() <=0){
            return null;
        }else{
            int ran = random.nextInt(Globbys.size());
            return getInstance().getProxy().getServerInfo(Globbys.get(ran));
        }
    }
    public static List<String> getLobbys(){
        return Globbys;
    }
    private void loadCommands(){

        try {
            String name = config.getConfig().getString("config.command.lobby.command");
            String permission = config.getConfig().getString("config.command.lobby.permission");
            String[] aliases = config.getConfig().getStringList("config.command.lobby.aliases").toArray(new String[0]);
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new Lobby(name,permission, aliases));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyCreate("lobbycreate", "lobby.create"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyList("lobbylist", "lobby.list"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyTransport("lobbylist", "lobby.transport"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyReload("lobbyreload", "lobby.reload"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyRemove("lobbyremove", "lobby.remove"));
        }catch (Exception e){
            e.printStackTrace();
            getProxy().getConsole().sendMessage(ChatColor.RED + "An error has ocurred loading commands from config.yml, fixed it");
            getProxy().stop();
        }

    }
    public String getMessage(String path){
       return ChatColor.translateAlternateColorCodes('&', config.getMessage().getString(path));
    }

    private void loadDirectory() {
        File dir = new File(getDataFolder().toString());
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public static Lobbysystem getInstance(){
        return instance;
    }

    public boolean isRunnable() {
        return runnable;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }
}
