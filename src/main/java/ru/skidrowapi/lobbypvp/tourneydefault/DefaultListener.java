package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import ru.skidrowapi.lobbypvp.Loader;

public class DefaultListener implements Listener {

    public DefaultListener(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;

//    @EventHandler
//    void onPlayerDead(PlayerDeathEvent e){
//        Player p =e.getEntity();
//        DefaultArena da=new DefaultArena(plugin);
//        da.playerwin(p);
//    }

    @EventHandler
    void onTeleportWorld(PlayerTeleportEvent e){
        World wk=e.getTo().getWorld();
        Player p=e.getPlayer();
        DefaultArena da=new DefaultArena(plugin);
        da.removeLobby(wk,p);
    }

}
