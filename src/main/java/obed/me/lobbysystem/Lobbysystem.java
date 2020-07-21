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
import obed.me.lobbysystem.Events.Event;
import obed.me.lobbysystem.Objects.Type;

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
    private static List<String> Rlobbys = new ArrayList<String>();
    private Type mode;

    @Override
    public void onEnable() {
      instance = this;
      getProxy().registerChannel("BungeeCord");
      loadConfig();
      loadDirectory();
        //loading lobbies
        loadDeniedServers();
        loadLobbysfromConfig();
        runnable = Boolean.parseBoolean(config.getConfig().getString("config.countdown"));
        time = Integer.parseInt(config.getConfig().getString("config.time"));
        loadCommands();
        String modo = config.getConfig().getString("config.tp_mode");
        switch (modo.toUpperCase()){
            case "RANDOM":
                setMode(Type.RANDOM);
                break;
            case "LESSPLAYERS":
                setMode(Type.LESSPLAYERS);
                break;
            default:
                getProxy().getConsole().sendMessage(ChatColor.RED + "An error has ocurred trying to set the mode in config.yml -> tp_mode. Check it!");
                getProxy().stop();
        }

        getProxy().getPluginManager().registerListener(this, new Event());
        getProxy().getConsole().sendMessage(ChatColor.GREEN + "[Lobby System] loaded correctly.");
        getProxy().getConsole().sendMessage(ChatColor.GREEN + "[Lobby System] " + getLobbys().size()+ " lobbys has been added.");
    }

    public void loadConfig() {
        config = new ConfigManager();
        config.registerConfig();
        config.registerMessage();
    }

    public void loadDeniedServers(){
        Rlobbys.clear();
        List<String> lobbys = config.getConfig().getStringList("config.commands.lobby.denied_servers");
        for(String servers : lobbys){
            if(Lobbysystem.getInstance().getProxy().getServers().containsKey(servers)){
                Rlobbys.add(servers);
            }
        }
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

    private void saveMessage(){
        File file = new File(getDataFolder(), "message.yml");
        try {
            Configuration cgf = cp.load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "message.yml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveConfig(){
        File file = new File(getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig(){
        saveConfig();
        saveMessage();
        config.reloadConfig();
        config.reloadMessage();
        loadLobbysfromConfig();
        loadDeniedServers();
        runnable = Boolean.parseBoolean(config.getConfig().getString("config.countdown"));
        time = Integer.parseInt(config.getConfig().getString("config.time"));
        String modo = config.getConfig().getString("config.tp_mode");
        switch (modo.toUpperCase()){
            case "RANDOM":
                setMode(Type.RANDOM);
                break;
            case "LESSPLAYERS":
                setMode(Type.LESSPLAYERS);
                break;
            default:
                getProxy().getConsole().sendMessage(ChatColor.RED + "An error has ocurred trying to set the mode in config.yml -> tp_mode. Check it!");
                getProxy().stop();
        }
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
    public List<String> getLobbys(){
        return Globbys;
    }
    public List<String> getRlobbys() {
        return Rlobbys;
    }

    private void loadCommands(){

        try {
            String name = config.getConfig().getString("config.commands.lobby.command");
            String permission = config.getConfig().getString("config.commands.lobby.permission");
            String[] aliases = config.getConfig().getStringList("config.commands.lobby.aliases").toArray(new String[0]);
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new Lobby(name,permission, aliases));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyCreate("lobbycreate"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyList("lobbylist"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyTransport("lobbytransport"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyReload("lobbyreload"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyRemove("lobbyremove"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyServer("lobbyserver"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyTP("lobbytp"));
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyMode("lobbymode"));

        }catch (Exception e){
            e.printStackTrace();
            getProxy().getConsole().sendMessage(ChatColor.RED + "An error has ocurred loading commands from config.yml, fixed it");
            getProxy().stop();
        }

    }
    public String getMessage(String path){
       return ChatColor.translateAlternateColorCodes('&', config.getMessage().getString("message.prefix") + config.getMessage().getString(path));
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

    public Type getMode() {
        return mode;
    }

    public void setMode(Type mode) {
        this.mode = mode;
    }
}
