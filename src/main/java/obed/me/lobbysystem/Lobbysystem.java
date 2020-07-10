package obed.me.lobbysystem;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import obed.me.lobbysystem.Command.LobbyCreate;
import obed.me.lobbysystem.Command.Lobby;
import obed.me.lobbysystem.Command.LobbyReload;
import obed.me.lobbysystem.Command.LobbyRemove;
import obed.me.lobbysystem.ConfigManager.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Lobbysystem extends Plugin {
    public static ConfigurationProvider cp = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private ConfigManager config;
    private static Lobbysystem instance;
    public int time;
    public boolean runnable;
    public static List<String> lobbys = new ArrayList<String>();
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
        lobbys.clear();
        List<String> lobbys = config.getConfig().getStringList("config.lobbys");
        for(String servers : lobbys){
            if(Lobbysystem.getInstance().getProxy().getServers().containsKey(servers)){
                this.lobbys.add(servers);
            }
        }
    }
    public void saveLobbysinConfig(){
        File file = new File(getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            cgf.set("config.lobbys", lobbys);
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
        if(lobbys.size() <=0){
            return null;
        }else{
            int ran = random.nextInt(lobbys.size());
            return getInstance().getProxy().getServerInfo(lobbys.get(ran));
        }
    }
    private void loadCommands(){
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyCreate("lobbycreate"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Lobby("lobby"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyReload("lobbyreload"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyRemove("lobbyremove"));
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

}
