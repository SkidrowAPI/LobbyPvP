package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import ru.skidrowapi.lobbypvp.Loader;

public class DefaultListener implements Listener {

    public DefaultListener(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;
    DefaultArena da = new DefaultArena(plugin);

    @EventHandler
    void onPlayerDead(PlayerDeathEvent e){
        Player p =e.getEntity().getPlayer();
        da.playerWin(p);
    }

    @EventHandler
    void onTeleportWorld(PlayerTeleportEvent e){
        String worldarena,cause;
        World wk=e.getTo().getWorld();
        worldarena = plugin.getConfig().getString("pvparena.world");
        World defaultworld = plugin.getServer().getWorld(worldarena);
        if (defaultworld==wk) {
            Player p = e.getPlayer();
            cause=e.getEventName();

            da.leaveLobby(p,cause);
        }
    }

    @EventHandler
    void onPlayerQuitEvent(PlayerQuitEvent e){
        String worldarena,cause;
        World wk=e.getPlayer().getWorld();
        worldarena = plugin.getConfig().getString("pvparena.world");
        World defaultworld = plugin.getServer().getWorld(worldarena);
        if (defaultworld==wk) {
            Player p = e.getPlayer();
            cause=e.getEventName();
            da.leaveLobby(p,cause);
        }
    }

}
