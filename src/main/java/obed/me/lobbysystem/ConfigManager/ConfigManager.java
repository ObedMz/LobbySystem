package obed.me.lobbysystem.ConfigManager;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import obed.me.lobbysystem.Lobbysystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    Configuration config;
    Configuration message;

    public Configuration getConfig(){
        if(config == null){
            reloadConfig();
        }
        return this.config;
    }

    public Configuration getMessage(){
        if(message == null){
            reloadMessage();
        }
        return this.message;
    }

    public void reloadMessage() {
        try{
            this.message = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Lobbysystem.getInstance().getDataFolder(), "message.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void reloadConfig() {
        try{
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Lobbysystem.getInstance().getDataFolder(), "config.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void registerMessage() {
            File file = new File(Lobbysystem.getInstance().getDataFolder(), "message.yml");
            if(!file.exists()){
                try {
                    InputStream in = Lobbysystem.getInstance().getResourceAsStream("message.yml");
                    Files.copy(in, file.toPath());
                    this.message = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Lobbysystem.getInstance().getDataFolder(), "message.yml"));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

    }
    public void registerConfig() {
        if(!Lobbysystem.getInstance().getDataFolder().exists()){
            Lobbysystem.getInstance().getDataFolder().mkdir();

            File file = new File(Lobbysystem.getInstance().getDataFolder(), "config.yml");
            if(!file.exists()){
                try {
                    InputStream in = Lobbysystem.getInstance().getResourceAsStream("config.yml");
                    Files.copy(in, file.toPath());
                    this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Lobbysystem.getInstance().getDataFolder(), "config.yml"));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
