package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.skidrowapi.lobbypvp.Loader;

public class DefaultListener implements Listener {

    public DefaultListener(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;

    @EventHandler
    void onPlayerDead(PlayerDeathEvent e){
        Player p =e.getEntity();
        DefaultArena da=new DefaultArena(plugin);
        da.playerwin(p);
    }

}
