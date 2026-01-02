package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.commands.CreateZone;
import dev.madtechs.creativeZone.commands.GoToOverworld;
import dev.madtechs.creativeZone.commands.PullChunks;

import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CreativeZone extends JavaPlugin implements Listener {
    private static CreativeZone instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        instance = this;

        getCommand("makeZone").setExecutor(new CreateZone());
        getCommand("goToOverworld").setExecutor(new GoToOverworld());
        getCommand("pullChunks").setExecutor(new PullChunks());

    }

    public static CreativeZone getInstance() {
        return instance;
    }
}
