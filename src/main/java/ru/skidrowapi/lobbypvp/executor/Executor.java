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
        m=new sendMessage(plugin);
    }
    sendMessage m;
    private Loader plugin;
    private int timestart;
    private double xl,yl,zl,yaw,pitch;
    private String worldarena;
    private boolean join,invent;

    DefaultArena da=null;


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
        if ((args[0].equalsIgnoreCase("start"))&&(args[1].equalsIgnoreCase("default"))&&p.hasPermission("pvpadmin")) {
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

        }else if ((args[0].equalsIgnoreCase("world"))&&(args[1].equalsIgnoreCase("default"))&&p.hasPermission("pvpadmin")) {
            World defaultworld = plugin.getServer().getWorld(worldarena);
            p.teleport(defaultworld.getSpawnLocation());
        }else if((args[0].equalsIgnoreCase("leave"))&&(args[1].equalsIgnoreCase("default"))){
            s="(по собственному желанию)";
            da.leaveLobby(p,s);
        }else if ((args[0].equalsIgnoreCase("setlobby"))&&(args[1].equalsIgnoreCase("default"))&&p.hasPermission("pvpadmin")) {
            worldarena=plugin.getConfig().getString("pvparena.world");
            World defaultworld= plugin.getServer().getWorld(worldarena);
            if(p.getLocation().getWorld()==defaultworld){
                xl=p.getLocation().getX();
                yl=p.getLocation().getY();
                zl=p.getLocation().getZ();
                yaw=p.getLocation().getYaw();
                pitch=p.getLocation().getPitch();
                plugin.getConfig().set("pvparena.coordslobby.yaw",yaw);
                plugin.getConfig().set("pvparena.coordslobby.pitch",pitch);
                plugin.getConfig().set("pvparena.coordslobby.x",xl);
                plugin.getConfig().set("pvparena.coordslobby.y",yl);
                plugin.getConfig().set("pvparena.coordslobby.z",zl);
                plugin.saveConfig();
                m.NEW_SPAWN_LOBBY_DEFAULT(p);
            }else m.GOTO_DEFAULT_WORLD(p);
        }else if((args[0].equalsIgnoreCase("setspawn1"))&&(args[1].equalsIgnoreCase("default"))&&p.hasPermission("pvpadmin")){
            worldarena=plugin.getConfig().getString("pvparena.world");
            World defaultworld= plugin.getServer().getWorld(worldarena);
            if(p.getLocation().getWorld()==defaultworld){
                xl=p.getLocation().getX();
                yl=p.getLocation().getY();
                zl=p.getLocation().getZ();
                yaw=p.getLocation().getYaw();
                pitch=p.getLocation().getPitch();
                plugin.getConfig().set("pvparena.coordsPlayer1.yaw",yaw);
                plugin.getConfig().set("pvparena.coordsPlayer1.pitch",pitch);
                plugin.getConfig().set("pvparena.coordsPlayer1.x",xl);
                plugin.getConfig().set("pvparena.coordsPlayer1.y",yl);
                plugin.getConfig().set("pvparena.coordsPlayer1.z",zl);
                plugin.saveConfig();
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                m.SET_POINT_PLAYER1(p);
            }else m.GOTO_DEFAULT_WORLD(p);
        }else if((args[0].equalsIgnoreCase("setspawn2"))&&(args[1].equalsIgnoreCase("default"))&&p.hasPermission("pvpadmin")) {
            worldarena = plugin.getConfig().getString("pvparena.world");
            World defaultworld = plugin.getServer().getWorld(worldarena);
            if (p.getLocation().getWorld() == defaultworld) {
                xl = p.getLocation().getX();
                yl = p.getLocation().getY();
                zl = p.getLocation().getZ();
                yaw=p.getLocation().getYaw();
                pitch=p.getLocation().getPitch();
                plugin.getConfig().set("pvparena.coordsPlayer2.yaw",yaw);
                plugin.getConfig().set("pvparena.coordsPlayer2.pitch",pitch);
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
