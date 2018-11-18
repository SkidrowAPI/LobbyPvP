package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import ru.skidrowapi.lobbypvp.Loader;


import java.util.ArrayList;


public class DefaultArena {
    public DefaultArena(Loader instance) {
        plugin = instance;
    }

    private Loader plugin;
    private int xl, yl, zl, j = 0,minplayer, num1, num2,min,max;
    private Double xa1, ya1, za1, xa2, ya2, za2;
    private String worldarena;
    private Boolean join;
    ArrayList<Player> listplayerlobby = new ArrayList<Player>();


    public void tpLobby(Player p) {
        xl = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("x");
        yl = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("y");
        zl = plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordslobby").getInt("z");
        worldarena = plugin.getConfig().getConfigurationSection("pvparena").getString("world");
        World defaultworld = plugin.getServer().getWorld(worldarena);
        defaultworld.setSpawnLocation(xl, yl, zl);
        p.teleport(defaultworld.getSpawnLocation());
        getJ();
        j++;
        listplayerlobby.add(j,p);
        p.sendMessage(ChatColor.BLUE+"Вы в лобби! Ваш личный номер - "+j);
        kolPlayer();
    }

    private void kolPlayer(){
        getListPlayerLobby();
        minplayer=plugin.getConfig().getConfigurationSection("pvparena").getInt("minplayer");
        if(listplayerlobby.size()>=minplayer){
            randomPlayer();
        }
    }

    private void randomPlayer(){
        min=1;
        getListPlayerLobby();
        max=listplayerlobby.size();


    num1= (int) (min + (Math.random() * (max - min)));
    num2= (int) (min + (Math.random() * (max - min)));
    Player p1=listplayerlobby.get(num1);
    Player p2=listplayerlobby.get(num1);
    ArenaDefault(p1,p2);

    }

    private void ArenaDefault(Player p1,Player p2){
        xa1=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").getDouble("x");
        ya1=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").getDouble("y");
        za1=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer1").getDouble("z");

        xa2=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").getDouble("x");
        ya2=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").getDouble("y");
        za2=plugin.getConfig().getConfigurationSection("pvparena").getConfigurationSection("coordsPlayer2").getDouble("z");

        p1.getLocation().setX(xa1);
        p1.getLocation().setY(ya1);
        p1.getLocation().setZ(za1);
        p1.sendMessage(ChatColor.YELLOW+"Ваш противник - "+p2.getName());

        p2.getLocation().setX(xa2);
        p2.getLocation().setY(ya2);
        p2.getLocation().setZ(za2);
        p2.sendMessage(ChatColor.YELLOW+"Ваш противник - "+p1.getName());

        ItemStack helmet=new ItemStack(Material.DIAMOND_HELMET,1);
        EnchantmentStorageMeta metahelmet= (EnchantmentStorageMeta) helmet.getItemMeta();
        metahelmet.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,true);
        p1.getInventory().setHelmet(helmet);
        p2.getInventory().setHelmet(helmet);

        ItemStack chestplate=new ItemStack(Material.DIAMOND_CHESTPLATE ,1);
        EnchantmentStorageMeta metachestplate= (EnchantmentStorageMeta) chestplate.getItemMeta();
        metachestplate.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,true);
        p1.getInventory().setChestplate(chestplate);
        p2.getInventory().setChestplate(chestplate);

        ItemStack leggings=new ItemStack(Material.DIAMOND_LEGGINGS ,1);
        EnchantmentStorageMeta metaleggings= (EnchantmentStorageMeta) leggings.getItemMeta();
        metaleggings.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1,true);
        p1.getInventory().setLeggings(leggings);
        p2.getInventory().setLeggings(leggings);

        ItemStack sword=new ItemStack(Material.DIAMOND_SWORD  ,1);
        EnchantmentStorageMeta metasword= (EnchantmentStorageMeta) sword.getItemMeta();
        metasword.addStoredEnchant(Enchantment.DAMAGE_ALL,1,true);
        p1.getInventory().setItem(0,sword);
        p2.getInventory().setItem(0,sword);

        Potion potion=new Potion(PotionType.getByEffect(PotionEffectType.FIRE_RESISTANCE));
        ItemStack potionstack = potion.toItemStack(1);
        p1.getInventory().setItem(1-7,potionstack);
        p2.getInventory().setItem(1-7,potionstack);

        ItemStack cooked=new ItemStack(Material.COOKED_BEEF,16);
        p1.getInventory().setItem(8,cooked);
        p2.getInventory().setItem(8,cooked);

        p1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 2));
        p2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 2));

        p1.updateInventory();
        p2.updateInventory();
    }

    public void removeLobby(World wk,Player p){
        World defaultworld = plugin.getServer().getWorld(worldarena);
        if(defaultworld.getName()!=wk.getName()){
            getListPlayerLobby();
            try {
                listplayerlobby.remove(p);
                getJ();
                j--;
            }catch (Exception e){
                e.printStackTrace();
            }
            p.sendMessage(ChatColor.RED+"Вы вышли из лобби, вы не учавствуете в боях!");
        }
    }

    public ArrayList<Player> getListPlayerLobby(){
        return listplayerlobby;
    }
    public int getJ(){
        return j;
    }


}
