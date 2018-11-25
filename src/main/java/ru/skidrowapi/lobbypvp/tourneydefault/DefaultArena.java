package ru.skidrowapi.lobbypvp.tourneydefault;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import ru.skidrowapi.lobbypvp.Loader;
import ru.skidrowapi.lobbypvp.rewards.GiveReward;
import ru.skidrowapi.lobbypvp.SendMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DefaultArena {
    private Loader plugin;
    public DefaultArena(Loader instance) {
        plugin = instance;
        m=new SendMessage(plugin);
    }
    SendMessage m;

    private int   num1, num2;
    private double pitch1,pitch2,yaw1,yaw2,pitch,yaw,xa1, ya1, za1, xa2, ya2,xl,yl, zl, za2;
    private String worldarena;
    ArrayList<Player> listplayerlobby = new ArrayList<Player>();
    ArrayList<Player> listplayerarena = new ArrayList<Player>();


    public void tpLobby(Player p) {
        xl = plugin.getConfig().getDouble("pvparena.coordslobby.x");
        yl = plugin.getConfig().getDouble("pvparena.coordslobby.y");
        zl = plugin.getConfig().getDouble("pvparena.coordslobby.z");
        yaw = plugin.getConfig().getDouble("pvparena.coordslobby.yaw");
        pitch = plugin.getConfig().getDouble("pvparena.coordslobby.pitch");
        worldarena = plugin.getConfig().getString("pvparena.world");
        World defaultworld = plugin.getServer().getWorld(worldarena);
        defaultworld.setSpawnLocation((int)xl, (int)yl, (int)zl);
        p.teleport(defaultworld.getSpawnLocation());
        p.getLocation().setYaw((float) yaw);
        p.getLocation().setPitch((float) pitch);
        //сделать проверку есть ли игрок уже в листе
        listplayerlobby.add(p);
        m.HELLO_LOBBY(p);

    }

    public void checkTime(){
        if (listplayerlobby.size() >= plugin.getConfig().getInt("pvparena.minplayer")) {
            plugin.getServer().broadcastMessage(ChatColor.AQUA+"На арене default "+listplayerlobby.size()+" человек!");
            joinArena();
        }else backTP();
    }

    public void backTP(){
        for (Player p : listplayerlobby){
            p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
            m.PLAYER_IS_ONE(p);
        }
    }

    private void joinArena() {
        if(listplayerlobby.size()==1){
            GiveReward gw=new GiveReward(plugin);
            m.PLAYER_LAST_WIN(listplayerlobby.get(0));
            Player pwin=listplayerlobby.get(0);
            gw.getReward(pwin);
            pwin.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
            return;
        }
        Random random = new Random();
        num1 = random.nextInt(listplayerlobby.size());
        num2 = random.nextInt(listplayerlobby.size() - 1);
        if (num2 >= num1) num2++;
        if(listplayerlobby.size()==2){
            num1=0;
            num2=1;
        }
        Player p1=listplayerlobby.get(num1);
        Player p2=listplayerlobby.get(num2);
        worldarena = plugin.getConfig().getString("pvparena.world");
        World defaultworld = plugin.getServer().getWorld(worldarena);
        xa1 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.x");
        ya1 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.y");
        za1 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.z");
        yaw1 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.yaw");
        pitch1 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.pitch");

        xa2 = plugin.getConfig().getDouble("pvparena.coordsPlayer2.x");
        ya2 = plugin.getConfig().getDouble("pvparena.coordsPlayer2.y");
        za2 = plugin.getConfig().getDouble("pvparena.coordsPlayer2.z");
        yaw2 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.yaw");
        pitch2 = plugin.getConfig().getDouble("pvparena.coordsPlayer1.pitch");

        Location loc1=new Location(defaultworld,xa1,ya1,za1);
        loc1.setYaw((float) yaw1);
        loc1.setPitch((float) pitch1);
        p1.teleport(loc1);
        m.PLAYER1_VS_PLAYER2(p1,p2);

        Location loc2=new Location(defaultworld,xa2,ya2,za2);
        loc2.setYaw((float) yaw2);
        loc2.setPitch((float) pitch2);
        p2.teleport(loc2);
        m.PLAYER2_VS_PLAYER1(p1,p2);

        listplayerarena.add(p1);
        listplayerarena.add(p2);

        getInventory(p1,p2);
    }

    public void playerWin(Player p){
        if (listplayerarena.get(0)==p){
            Player p1=listplayerlobby.get(0);
            Player p2=listplayerlobby.get(1);
            listplayerlobby.remove(p1);
            listplayerarena.clear();
            m.PLAYER1_WIN(p1,p2);
            tpLobby(p1);
            tpLobby(p2);
        }else {
            Player p1=listplayerlobby.get(1);
            Player p2=listplayerlobby.get(0);
            listplayerlobby.remove(p1);
            listplayerarena.clear();
            m.PLAYER1_WIN(p1,p2);
            tpLobby(p1);
            tpLobby(p2);
        }
        joinArena();

    }

    public void leaveLobby(Player p,String s){
        if(listplayerlobby.size()==0){
            return;
        }
        for(Player player : listplayerlobby){
            if(player==p){
                listplayerlobby.remove(p);
                if(listplayerarena.size()>0&&listplayerarena.get(1)==p){
                    listplayerlobby.remove(p);
                }else if(listplayerarena.size()>0&&listplayerlobby.get(0)==p){
                    listplayerlobby.remove(p);
                }
                p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
                m.PLAYER_LEAVE_DEFAULT(p,s);
                break;
            }
        }

    }

    private void getInventory(Player p1, Player p2){
        List<Integer> slot = new ArrayList<Integer>();
        String name,material,enchantments;
        int lvl,slot_numbers,amount,time;
        name=("&6Fortune").replace("&", "§");
        p1.getInventory().clear(); p2.getInventory().clear();
        p1.getActivePotionEffects().clear(); p2.getActivePotionEffects().clear();

        material=plugin.getConfig().getString("pvparena.kits.armor.helmet.material");
        enchantments=plugin.getConfig().getString("pvparena.kits.armor.helmet.enchantments");
        lvl=plugin.getConfig().getInt("pvparena.kits.armor.helmet.lvl");
        ItemStack helmet = new ItemStack(Material.getMaterial(material), 1);
        helmet.addEnchantment(Enchantment.getByName(enchantments),lvl);
        ItemMeta imhelmet=helmet.getItemMeta();
        imhelmet.setDisplayName(name);
        helmet.setItemMeta(imhelmet);
        p1.getInventory().setHelmet(helmet);
        p2.getInventory().setHelmet(helmet);

        material=plugin.getConfig().getString("pvparena.kits.armor.chestplate.material");
        enchantments=plugin.getConfig().getString("pvparena.kits.armor.chestplate.enchantments");
        lvl=plugin.getConfig().getInt("pvparena.kits.armor.chestplate.lvl");
        ItemStack chestplate = new ItemStack(Material.getMaterial(material), 1);
        chestplate.addEnchantment(Enchantment.getByName(enchantments),lvl);
        ItemMeta imchestplate=chestplate.getItemMeta();
        imchestplate.setDisplayName(name);
        chestplate.setItemMeta(imchestplate);
        p1.getInventory().setChestplate(chestplate);
        p2.getInventory().setChestplate(chestplate);

        material=plugin.getConfig().getString("pvparena.kits.armor.leggings.material");
        enchantments=plugin.getConfig().getString("pvparena.kits.armor.leggings.enchantments");
        lvl=plugin.getConfig().getInt("pvparena.kits.armor.leggings.lvl");
        ItemStack leggings = new ItemStack(Material.getMaterial(material), 1);
        leggings.addEnchantment(Enchantment.getByName(enchantments),lvl);
        ItemMeta imleggings=leggings.getItemMeta();
        imleggings.setDisplayName(name);
        leggings.setItemMeta(imleggings);
        p1.getInventory().setLeggings(leggings);
        p2.getInventory().setLeggings(leggings);

        material=plugin.getConfig().getString("pvparena.kits.armor.boots.material");
        enchantments=plugin.getConfig().getString("pvparena.kits.armor.boots.enchantments");
        lvl=plugin.getConfig().getInt("pvparena.kits.armor.boots.lvl");
        ItemStack boots = new ItemStack(Material.getMaterial(material), 1);
        boots.addEnchantment(Enchantment.getByName(enchantments),lvl);
        ItemMeta imboots=boots.getItemMeta();
        imboots.setDisplayName(name);
        boots.setItemMeta(imboots);
        p1.getInventory().setBoots(boots);
        p2.getInventory().setBoots(boots);

        material=plugin.getConfig().getString("pvparena.kits.inventory.sword.material");
        enchantments=plugin.getConfig().getString("pvparena.kits.inventory.sword.enchantments");
        lvl=plugin.getConfig().getInt("pvparena.kits.inventory.sword.lvl");
        amount=plugin.getConfig().getInt("pvparena.kits.inventory.sword.amount");
        slot_numbers=plugin.getConfig().getInt("pvparena.kits.inventory.sword.slot-numbers");
        ItemStack sword = new ItemStack(Material.getMaterial(material), amount);
        sword.addEnchantment(Enchantment.getByName(enchantments),lvl);
        ItemMeta imsword=sword.getItemMeta();
        imsword.setDisplayName(name);
        sword.setItemMeta(imsword);
        p1.getInventory().setItem(slot_numbers,sword);
        p2.getInventory().setItem(slot_numbers,sword);

        enchantments=plugin.getConfig().getString("pvparena.kits.inventory.potion.enchantments");
        amount=plugin.getConfig().getInt("pvparena.kits.inventory.potion.amount");
        slot=plugin.getConfig().getIntegerList("pvparena.kits.inventory.potion.slot-numbers");
        Potion potion = new Potion(PotionType.getByEffect(PotionEffectType.getByName(enchantments)));
        ItemStack potionstack = potion.toItemStack(amount);
        ItemMeta impotion=potionstack.getItemMeta();
        impotion.setDisplayName(name);
        potionstack.setItemMeta(impotion);
        for(int slot_number : slot){
            p1.getInventory().setItem(slot_number,potionstack);
            p2.getInventory().setItem(slot_number,potionstack);
        }

        material=plugin.getConfig().getString("pvparena.kits.inventory.cooked_beef.material");
        amount=plugin.getConfig().getInt("pvparena.kits.inventory.cooked_beef.amount");
        slot_numbers=plugin.getConfig().getInt("pvparena.kits.inventory.cooked_beef.slot-numbers");
        ItemStack cooked_beef = new ItemStack(Material.getMaterial(material), amount);
        ItemMeta imcooked_beef=cooked_beef.getItemMeta();
        imcooked_beef.setDisplayName(name);
        cooked_beef.setItemMeta(imcooked_beef);
        p1.getInventory().setItem(slot_numbers,cooked_beef);
        p2.getInventory().setItem(slot_numbers,cooked_beef);

        material=plugin.getConfig().getString("pvparena.kits.effects.name");
        lvl=plugin.getConfig().getInt("pvparena.kits.effects.lvl");
        time=plugin.getConfig().getInt("pvparena.timefights")*20;
        p1.addPotionEffect(new PotionEffect(PotionEffectType.getByName(material), time, lvl));
        p2.addPotionEffect(new PotionEffect(PotionEffectType.getByName(material), time, lvl));

        p1.updateInventory();
        p2.updateInventory();
    }

}
