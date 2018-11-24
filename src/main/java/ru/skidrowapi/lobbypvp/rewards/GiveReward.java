package ru.skidrowapi.lobbypvp.rewards;

import org.bukkit.entity.Player;
import ru.skidrowapi.lobbypvp.Loader;
import ru.skidrowapi.lobbypvp.modules.VaultLoader;
import ru.skidrowapi.lobbypvp.sendMessage;

public class GiveReward {
    private int reward;
    private Loader plugin;
    public GiveReward(Loader instance) {
        plugin = instance;
    }

    public void getReward(Player p){
        sendMessage m=new sendMessage(plugin);
        reward = plugin.getConfig().getInt("vault.reward-amount");
        if(plugin.isVaultEnabled()){

            VaultLoader.economy.depositPlayer(p, reward);
            m.GIVE_REWARD(p,reward);

        }else m.IS_REWARD_DISABLE();

    }

}
