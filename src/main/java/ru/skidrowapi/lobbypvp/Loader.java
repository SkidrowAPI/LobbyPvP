package ru.skidrowapi.lobbypvp;


import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import ru.skidrowapi.lobbypvp.executor.Executor;

public class Loader extends JavaPlugin {
    Loader plugin=this;
    private String worldarena;


    @Override
    public void onEnable(){
        createWorld();
        Executor executor=new Executor(plugin);
        getCommand("pvp").setExecutor(executor);
    }

    @Override
    public void onDisable(){

    }

    public void createWorld(){
        worldarena = plugin.getConfig().getConfigurationSection("pvparena").getString("world");
        if (plugin.getServer().getWorld(worldarena) == null) {
            plugin.getServer().createWorld(new WorldCreator(worldarena));
            plugin.getLogger().info("Create new world-" + worldarena);
        }
    }

//    во время указанного времени игроки могут заходить в лобби и их ники записываются в лист
//    после указанного времени войти нельзя, и берутся 2 рандом чела с листа и передаются в метод арены
//    в методе их тп на позиции и выдаётся броня, создаётся ещё один лист, в котором они двое
//    если умирает 1 из 2 вызывается ивент и передаётся умерший игрок, в метод победитель
//    в методе берётся из листа с лобби удаляется умерший, тот остаётся. вызывается бродкаст что в бое победил  такой-то
//    и опять запускается метод арена в котором опять берутся 2 чела. и так до тех пор пока в лист.size() не будет 1
}
