package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.*;
import org.bukkit.entity.Player;
import ru.skidrowapi.lobbypvp.Loader;
import ru.skidrowapi.lobbypvp.executor.Executor;

import java.util.ArrayList;

public class DefaultArena {
    public DefaultArena(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;
    private Integer i=1,xl,yl,zl;
    private Double xp,yp,zp,j;
    private String worldarena;
    private Boolean join;
    private ArrayList<String> playerjoin = new ArrayList<String>();


public void tplobby(Player p){
    xl=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("x");
    yl=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("y");
    zl=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("z");
    worldarena=plugin.getConfig().getConfigurationSection("pvparena").getString("world");
    if(plugin.getServer().getWorld(worldarena)==null){
        plugin.getServer().createWorld(new WorldCreator(worldarena));
        plugin.getLogger().info("Create new world-"+worldarena);
    }
    getListplayerjoin();
    World defaultworld= plugin.getServer().getWorld(worldarena);
    defaultworld.setSpawnLocation(xl,yl,zl);
    p.teleport(defaultworld.getSpawnLocation());
    playerjoin.add(p.getName());
    p.sendMessage(ChatColor.BLUE+"Вы перемещенны в лобби!");
    i=i+1;
    Executor executor=new Executor(plugin);
    join=executor.getjoin();
    p.sendMessage("join-"+join);
    if(join==true){
        iventstart();
    }

  }

  public void iventstart(){
      for(String p : playerjoin){
          Player player= (Player) plugin.getServer().getOfflinePlayer(p);
          j=j+1;
          zaparen(j,player);
      }

  }

  public void zaparen(Double j,Player p){
      xp=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1"+j).getDouble("x");
      yp=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1"+j).getDouble("y");
      zp=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1"+j).getDouble("z");
      p.getLocation().setX(xp);p.getLocation().setY(yp);p.getLocation().setZ(zp);
      p.sendMessage(ChatColor.BLUE+"Вы перемещенны на арену №"+j);



  }







public ArrayList<String> getListplayerjoin(){
    return playerjoin;
}
}
