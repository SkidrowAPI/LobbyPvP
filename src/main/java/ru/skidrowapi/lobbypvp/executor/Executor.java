package ru.skidrowapi.lobbypvp.executor;

import org.bukkit.ChatColor;
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
import ru.skidrowapi.lobbypvp.tourneydefault.DefaultArena;

public class Executor implements CommandExecutor {
    public Executor(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;

    private int time,timestart;
    private Double xl,yl,zl;
    private String worldarena;
    private boolean join,vr,invent;




    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().warning("Only for player's");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            if (p.isOp()) {
                p.sendMessage(ChatColor.YELLOW + "/pvp start <arena>" + ChatColor.AQUA + " - включает турниры(default или sumo)");
                p.sendMessage(ChatColor.YELLOW + "/pvp world <arena>" + ChatColor.AQUA + " - войти в мир(default или sumo)");
                p.sendMessage(ChatColor.YELLOW + "/pvp setlobby <arena>" + ChatColor.AQUA + " - устанавливает новый спавн лобби (там где вы стоите!)");
                p.sendMessage(ChatColor.YELLOW + "/pvp setspawn1 <arena>" + ChatColor.AQUA + " - устанавливает новую точку для игроков на арене (там где вы стоите!)");
                p.sendMessage(ChatColor.YELLOW + "/pvp setspawn2 <arena>" + ChatColor.AQUA + " - устанавливает новую точку для игроков на арене (там где вы стоите!)");

            }
            p.sendMessage(ChatColor.YELLOW + "/pvp join <arena>" + ChatColor.AQUA + " - войти в турнир(default или sumo)");

        }else
        if ((args[0].equalsIgnoreCase("start"))&&(args[1].equalsIgnoreCase("default"))) {
            if(p.isOp()==false){p.sendMessage(ChatColor.RED+"Недостаточно прав!");
                return true;
            }
            join=true; timestart=plugin.getConfig().getConfigurationSection("pvparena").getInt("timestart");
            plugin.getServer().broadcastMessage(ChatColor.BLUE+"Запустилась арена"+ChatColor.DARK_BLUE+" default "+ChatColor.BLUE+", запущен таймер на подключение к лобби!");
            new BukkitRunnable() {
                @Override
                public void run() {
                    join=false;
                    plugin.getServer().broadcastMessage(ChatColor.BLUE+"Теперь подключится к арене"+ChatColor.DARK_BLUE+" default "+ChatColor.BLUE+"не получится, таймер остановился!");

                }
            }.runTaskLater(this.plugin, 20*timestart);
        }else
        if ((args[0].equalsIgnoreCase("join"))&&(args[1].equalsIgnoreCase("default"))) {
            if(join==true){
                Inventory inv = p.getInventory();
                for (int i = 0; i < inv.getSize(); i++) {
                    ItemStack is = inv.getItem(i);
                    if ((is==null)||(is.getType()== Material.AIR)) {
                    invent=true;
                    }else{
                        invent=false;
                        p.sendMessage(ChatColor.RED+"Очистите полностью инвентарь!");
                        return true;
                    }
                }
                DefaultArena da=new DefaultArena(plugin);
                da.tpLobby(p);
            }else{
                p.sendMessage(ChatColor.RED+"Время для подключения вышло.");
                return true;
            }

        }else if ((args[0].equalsIgnoreCase("world"))&&(args[1].equalsIgnoreCase("default"))) {
            World defaultworld = plugin.getServer().getWorld(worldarena);
            p.teleport(defaultworld.getSpawnLocation());
        }else if((args[0].equalsIgnoreCase("leave"))&&(args[1].equalsIgnoreCase("default"))){
            DefaultArena da=new DefaultArena(plugin);
            da.removeLobby(p.getWorld(),p);
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
                p.sendMessage(ChatColor.RED+"Новое лобби для default создано!");
            }else p.sendMessage(ChatColor.RED + "Перейдите в мир "+plugin.getConfig().getConfigurationSection("pvparena").get("world")+".");
        }else if((args[0].equalsIgnoreCase("setspawn1"))||(args[1].equalsIgnoreCase("default"))){
            worldarena=plugin.getConfig().getConfigurationSection("pvparena").getString("world");
            World defaultworld= plugin.getServer().getWorld(worldarena);
            if(p.getLocation().getWorld()==defaultworld){
                xl=p.getLocation().getX();
                yl=p.getLocation().getY();
                zl=p.getLocation().getZ();
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").set("x",xl);
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").set("y",yl);
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").set("z",zl);
                plugin.saveConfig();
                p.sendMessage(ChatColor.RED+"Новая точка для игрока№1 в мире"+ plugin.getConfig().getConfigurationSection("pvparena").get("world")+" создана!");
            }else p.sendMessage(ChatColor.RED + "Перейдите в мир "+plugin.getConfig().getConfigurationSection("pvparena").get("world")+".");
        }else if((args[0].equalsIgnoreCase("setspawn2"))||(args[1].equalsIgnoreCase("default"))) {
            worldarena = plugin.getConfig().getConfigurationSection("pvparena").getString("world");
            World defaultworld = plugin.getServer().getWorld(worldarena);
            if (p.getLocation().getWorld() == defaultworld) {
                xl = p.getLocation().getX();
                yl = p.getLocation().getY();
                zl = p.getLocation().getZ();
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").set("x", xl);
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").set("y", yl);
                plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").set("z", zl);
                plugin.saveConfig();
                p.sendMessage(ChatColor.RED + "Новая точка для игрока№2 в мире" + plugin.getConfig().getConfigurationSection("pvparena").get("world") + " создана!");
            } else p.sendMessage(ChatColor.RED + "Перейдите в мир "+plugin.getConfig().getConfigurationSection("pvparena").get("world")+".");
        }

        return true;
    }

}
