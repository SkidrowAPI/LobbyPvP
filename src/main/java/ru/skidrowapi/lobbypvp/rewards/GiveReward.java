package ru.skidrowapi.lobbypvp.rewards;

import org.bukkit.entity.Player;
import ru.skidrowapi.lobbypvp.Loader;
import ru.skidrowapi.lobbypvp.Message;
import ru.skidrowapi.lobbypvp.modules.VaultLoader;

public class GiveReward {
    private int reward;
    private Loader plugin;
    public GiveReward(Loader instance) {
        plugin = instance;
    }

    public void getReward(Player p){
        Message m=new Message(plugin);
        reward = plugin.getConfig().getInt("vault.reward-amount");
        if(plugin.isVaultEnabled()){

            VaultLoader.economy.depositPlayer(p, reward);
            m.GIVE_REWARD(p,reward);

        }else m.IS_REWARD_DISABLE();

    }

}
