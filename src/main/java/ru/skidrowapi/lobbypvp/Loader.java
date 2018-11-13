package ru.skidrowapi.lobbypvp;


import org.bukkit.plugin.java.JavaPlugin;
import ru.skidrowapi.lobbypvp.executor.Executor;

public class Loader extends JavaPlugin {
    Loader plugin=this;

    @Override
    public void onEnable(){
        Executor executor=new Executor(plugin);
        getCommand("pvp").setExecutor(executor);
    }

    @Override
    public void onDisable(){

    }
}
