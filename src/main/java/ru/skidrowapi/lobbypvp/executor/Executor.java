package ru.skidrowapi.lobbypvp.executor;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.skidrowapi.lobbypvp.Loader;
import ru.skidrowapi.lobbypvp.sendMessage;
import ru.skidrowapi.lobbypvp.tourneydefault.DefaultArena;


public class Executor implements CommandExecutor {
    public Executor(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;

    private int timestart;
    private Double xl,yl,zl;
    private String worldarena;
    private boolean join,vr,invent;
    DefaultArena da=null;
sendMessage m=new sendMessage(plugin);



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().warning("Only for player's");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            if (p.isOp()) {
                m.PLAYER_COMMAND_HASPERM(p);
            }
            m.PLAYER_COMMAND_NOTHASPERM(p);

        }else
        if ((args[0].equalsIgnoreCase("start"))&&(args[1].equalsIgnoreCase("default"))) {
            if(p.isOp()==false){
                m.DONT_HAS_PEEMISSION(p);
                return true;
            }
            da=new DefaultArena(plugin);
            join=true; timestart=plugin.getConfig().getConfigurationSection("pvparena").getInt("timestart");
            m.START_DEFAULT_ARENA();
            final DefaultArena finalDa = da;
            new BukkitRunnable() {
                @Override
                public void run() {
                    join=false;
                    finalDa.checkTime();
                    m.STOP_DEFAULT_ARENA();
                }
            }.runTaskLater(this.plugin, 20*timestart);
        }else
        if ((args[0].equalsIgnoreCase("join"))&&(args[1].equalsIgnoreCase("default"))) {
            if(join==true){
                Inventory inv = p.getInventory();
                p.getActivePotionEffects().clear();
                for (int i = 0; i < inv.getSize(); i++) {
                    ItemStack is = inv.getItem(i);
                    if ((is==null)||(is.getType()== Material.AIR)) {
                    invent=true;
                    p.getActivePotionEffects().clear();
                    }else{
                        invent=false;
                        m.CLEAR_INVENTORY(p);
                        return true;
                    }
                }
                da.tpLobby(p);
            }else{
               m.STOP_CONNECT(p);
                return true;
            }

        }else if ((args[0].equalsIgnoreCase("world"))&&(args[1].equalsIgnoreCase("default"))) {
            World defaultworld = plugin.getServer().getWorld(worldarena);
            p.teleport(defaultworld.getSpawnLocation());
        }else if((args[0].equalsIgnoreCase("leave"))&&(args[1].equalsIgnoreCase("default"))){
            s="(по собственному желанию)";
            da.leaveLobby(p,s);
        }else if ((args[0].equalsIgnoreCase("setlobby"))&&(args[1].equalsIgnoreCase("default"))) {
            worldarena=plugin.getConfig().getConfigurationSection("pvparena").getString("world");
            World defaultworld= plugin.getServer().getWorld(worldarena);
            if(p.getLocation().getWorld()==defaultworld){
                xl=p.getLocation().getX();
                yl=p.getLocation().getY();
                zl=p.getLocation().getZ();
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").set("x",xl);
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").set("y",yl);
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").set("z",zl);
                plugin.saveConfig();
                m.NEW_SPAWN_LOBBY_DEFAULT(p);
            }else m.GOTO_DEFAULT_WORLD(p);
        }else if((args[0].equalsIgnoreCase("setspawn1"))&&(args[1].equalsIgnoreCase("default"))){
            worldarena=plugin.getConfig().getConfigurationSection("pvparena").getString("world");
            World defaultworld= plugin.getServer().getWorld(worldarena);
            if(p.getLocation().getWorld()==defaultworld){
                xl=p.getLocation().getX();
                yl=p.getLocation().getY();
                zl=p.getLocation().getZ();
                plugin.getConfig().set("pvparena.coordsPlayer1.x",xl);
                plugin.getConfig().set("pvparena.coordsPlayer1.y",yl);
                plugin.getConfig().set("pvparena.coordsPlayer1.z",zl);
                plugin.saveConfig();
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                m.SET_POINT_PLAYER1(p);
            }else m.GOTO_DEFAULT_WORLD(p);
        }else if((args[0].equalsIgnoreCase("setspawn2"))&&(args[1].equalsIgnoreCase("default"))) {
            worldarena = plugin.getConfig().getConfigurationSection("pvparena").getString("world");
            World defaultworld = plugin.getServer().getWorld(worldarena);
            if (p.getLocation().getWorld() == defaultworld) {
                xl = p.getLocation().getX();
                yl = p.getLocation().getY();
                zl = p.getLocation().getZ();
                plugin.getConfig().set("pvparena.coordsPlayer2.x",xl);
                plugin.getConfig().set("pvparena.coordsPlayer2.y",yl);
                plugin.getConfig().set("pvparena.coordsPlayer2.z",zl);
                plugin.saveConfig();
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                m.SET_POINT_PLAYER2(p);
            } else m.GOTO_DEFAULT_WORLD(p);
        }

        return true;
    }

}
