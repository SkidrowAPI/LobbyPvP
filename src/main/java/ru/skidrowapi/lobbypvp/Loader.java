package ru.skidrowapi.lobbypvp;


import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import ru.skidrowapi.lobbypvp.executor.Executor;
import ru.skidrowapi.lobbypvp.modules.VaultLoader;
import ru.skidrowapi.lobbypvp.tourneydefault.DefaultListener;

public class Loader extends JavaPlugin {
    Loader plugin=this;
    private String worldarena;
    private Boolean vaultEnabled;


    @Override
    public void onEnable(){
        createWorld();
        Executor executor=new Executor(plugin);
        getCommand("pvp").setExecutor(executor);
        getServer().getPluginManager().registerEvents(new DefaultListener(this),this);
        vaultEnabled = getConfig().getBoolean("vault.enabled");

        if(isVaultEnabled()){

            VaultLoader VaultLoader = new VaultLoader();
            if(VaultLoader.setupEconomy()){
                plugin.getLogger().info("debag");
            }else plugin.getLogger().info("no debag");
        }else {
            getLogger().info("Vault disabled!");
        }

    }

    @Override
    public void onDisable(){

    }

    public void createWorld(){
        worldarena = plugin.getConfig().getString("pvparena.world");
        if (plugin.getServer().getWorld(worldarena) == null) {
            plugin.getServer().createWorld(new WorldCreator(worldarena));
            plugin.getLogger().info("Create new world-" + worldarena);
        }
    }

    public Boolean isVaultEnabled(){
        return vaultEnabled;
    }

//создать enum если стадия1-закрыта, подключится не можем
//если стадия2-открытая, можем подключится
//сделать динамическое всё
//сделать через конфиг киты
// сделать методы:
// 1. тп в лобби
// 2. тп на арену(там берутся 2 игрока)
// 3.смотрим кто выигрывается(через ивент)
// 4. если игроки в листе есть, запускаем тп на арену
}
