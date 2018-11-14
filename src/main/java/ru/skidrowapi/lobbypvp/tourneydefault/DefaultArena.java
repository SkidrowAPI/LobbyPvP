package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.*;
import org.bukkit.entity.Player;
import ru.skidrowapi.lobbypvp.Loader;
import ru.skidrowapi.lobbypvp.executor.Executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class DefaultArena {
    public DefaultArena(Loader instance) {
        plugin = instance;
    }

    private Loader plugin;
    private Integer xl, yl, zl, k = 0, num1, num2;
    private Double xa1, ya1, za1, xa2, ya2, za2;
    private String worldarena;
    private Boolean join;
    ArrayList<Player> listplayerdefault = new ArrayList<Player>();
    ArrayList<Player> listarenadefault = new ArrayList<Player>();


    public void tplobby(Player p) {
        xl = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("x");
        yl = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("y");
        zl = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("z");
        worldarena = plugin.getConfig().getConfigurationSection("pvparena").getString("world");
        if (plugin.getServer().getWorld(worldarena) == null) {
            plugin.getServer().createWorld(new WorldCreator(worldarena));
            plugin.getLogger().info("Create new world-" + worldarena);
        }
        World defaultworld = plugin.getServer().getWorld(worldarena);
        defaultworld.setSpawnLocation(xl, yl, zl);
        p.teleport(defaultworld.getSpawnLocation());
        k++;
        listplayerdefault.add(k, p);
        Executor executor = new Executor(plugin);
        join = executor.getjoin();
        if ((join == false) && (listplayerdefault.size() >= plugin.getConfig().getConfigurationSection("pvparena").getInt("minplayer"))) {
            plugin.getServer().broadcastMessage("Бои на арене default запущенны!");
            Random rnd = new Random(System.currentTimeMillis());
            int num1 = 1 + rnd.nextInt(listplayerdefault.size());
            int num2 = 1 + rnd.nextInt(listplayerdefault.size());
            teleparena(listplayerdefault.get(num1), listplayerdefault.get(num2));

        }


        p.sendMessage(ChatColor.BLUE + "Вы перемещенны в лобби!");

    }


    public void teleparena(Player p1, Player p2) {
        xa1 = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").getDouble("x");
        ya1 = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").getDouble("y");
        za1 = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").getDouble("z");
        p1.getLocation().setX(xa1);
        p1.getLocation().setY(ya1);
        p1.getLocation().setZ(ya1);
        p1.sendMessage(ChatColor.BLUE + "Вы перемещенны на арену!");
        p1.sendMessage(ChatColor.YELLOW + "Противник-" + p2.getName());

        xa2 = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").getDouble("x");
        ya2 = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").getDouble("y");
        za2 = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").getDouble("z");
        p2.getLocation().setX(xa2);
        p2.getLocation().setY(ya2);
        p2.getLocation().setZ(ya2);
        p2.sendMessage(ChatColor.BLUE + "Вы перемещенны на арену!");
        p2.sendMessage(ChatColor.YELLOW + "Противник-" + p1.getName());
        listarenadefault.add(p1);
        listarenadefault.add(p2);
        setlistarenadefault();
        listarenadefault.removeAll(Collections.singleton(true));
    }

    public void playerwin(Player p) {
        setlistarenadefault();
        setlistplayerdefault();
        listarenadefault.remove(p);
        listplayerdefault.remove(p);
        plugin.getServer().broadcastMessage("В арене default победил" + listarenadefault.get(1));

    }

    public ArrayList<Player> setlistplayerdefault() {
        return listplayerdefault;
    }

    public ArrayList<Player> setlistarenadefault() {
        return listarenadefault;
    }
}
