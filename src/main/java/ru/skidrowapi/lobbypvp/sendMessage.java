package ru.skidrowapi.lobbypvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SendMessage {
    public SendMessage(Loader instance) {
        plugin = instance;
    }
    private Loader plugin;

    public void DONT_HAS_PERMISSION(Player p){
        p.sendMessage(ChatColor.RED+"Недостаточно прав!");
    }

    public void START_DEFAULT_ARENA(){
        plugin.getServer().broadcastMessage(ChatColor.YELLOW+"Запустилась арена"+ChatColor.AQUA+" default "+ChatColor.YELLOW+", запущен таймер на подключение к лобби!");
    }

    public void STOP_DEFAULT_ARENA(){
        plugin.getServer().broadcastMessage(ChatColor.YELLOW+"Теперь подключится к арене"+ChatColor.AQUA+" default "+ChatColor.YELLOW+"не получится, таймер остановился!");
    }

    public void CLEAR_INVENTORY(Player p){
        p.sendMessage(ChatColor.RED+"Очистите полностью инвентарь!");
    }

    public void STOP_CONNECT(Player p){
        p.sendMessage(ChatColor.RED+"Время для подключения вышло.");
    }

    public void NEW_SPAWN_LOBBY_DEFAULT(Player p){
        p.sendMessage(ChatColor.RED+"Новое лобби для default создано!");
    }

    public void GOTO_DEFAULT_WORLD(Player p){
        p.sendMessage(ChatColor.RED + "Перейдите в мир "+plugin.getConfig().get("pvparena.world")+".");
    }

    public void SET_POINT_PLAYER1(Player p){
        p.sendMessage(ChatColor.RED+"Новая точка для игрока №1 в мире "+ plugin.getConfig().get("pvparena.world")+" создана!");
    }

    public void SET_POINT_PLAYER2(Player p){
        p.sendMessage(ChatColor.RED + "Новая точка для игрока №2 в мире " + plugin.getConfig().get("pvparena.world") + " создана!");
    }

    public void PLAYER_COMMAND_HASPERM(Player p){
        p.sendMessage(ChatColor.YELLOW + "/pvp start <arena>" + ChatColor.AQUA + " - включает турниры (default или sumo)");
        p.sendMessage(ChatColor.YELLOW + "/pvp world <arena>" + ChatColor.AQUA + " - войти в мир (default или sumo)");
        p.sendMessage(ChatColor.YELLOW + "/pvp setlobby <arena>" + ChatColor.AQUA + " - устанавливает новый спавн лобби (там где вы стоите!)");
        p.sendMessage(ChatColor.YELLOW + "/pvp setspawn1 <arena>" + ChatColor.AQUA + " - устанавливает новую точку для игроков на арене (там где вы стоите!)");
        p.sendMessage(ChatColor.YELLOW + "/pvp setspawn2 <arena>" + ChatColor.AQUA + " - устанавливает новую точку для игроков на арене (там где вы стоите!)");
    }

    public void PLAYER_COMMAND_NOTHASPERM(Player p){
        p.sendMessage(ChatColor.YELLOW + "/pvp join <arena>" + ChatColor.AQUA + " - войти в турнир (default или sumo)");
        p.sendMessage(ChatColor.YELLOW + "/pvp leave <arena>" + ChatColor.AQUA + " - выйти из турнира (default или sumo)");

    }

    public void PLAYER1_VS_PLAYER2(Player p1,Player p2){
        p1.sendMessage(ChatColor.YELLOW + "Ваш противник - " + p2.getName());
    }

    public void PLAYER2_VS_PLAYER1(Player p1,Player p2){
        p2.sendMessage(ChatColor.YELLOW + "Ваш противник - " + p1.getName());
    }

    public void PLAYER1_WIN(Player p1, Player p2){
        p2.sendMessage(ChatColor.YELLOW+"Ты победил!");
        p1.sendMessage(ChatColor.YELLOW+"Ты проиграл!");
        plugin.getServer().broadcastMessage("Победил-"+p2.getName()+"! Проиграл-"+p1.getName());
    }

    public void PLAYER_LEAVE_DEFAULT(Player p,String s){
        p.sendMessage(ChatColor.RED+"Вы покинули "+s+". Вы телепортированны на spawn!");
    }

    public void HELLO_LOBBY(Player p){
        p.sendMessage(ChatColor.YELLOW + "Вы в лобби!");
    }

    public void PLAYER_IS_ONE(Player p){
        p.sendMessage(ChatColor.YELLOW+"Нехватает игроков, дуэли не состоятся :(");
    }

    public void GIVE_REWARD(Player p, int reward){
        p.sendMessage(ChatColor.YELLOW+"Вам начисленно за победу - "+reward);
    }

    public void IS_REWARD_DISABLE(){
        plugin.getLogger().info("Vault is off, no rewards can be issued!");
    }

    public void PLAYER_LAST_WIN(Player p){
        p.sendMessage(ChatColor.YELLOW+"Поздравляю Вы победили!");
    }

    public void COMMAND_EXIST(Player p){
        p.sendMessage(ChatColor.RED+"Команда не найдена или не существует!");
    }

    public void PLAYER_IN_LOBBY_EXIST(Player p){
        p.sendMessage(ChatColor.RED+"Вы уже состоите в лобби!");
    }
}
