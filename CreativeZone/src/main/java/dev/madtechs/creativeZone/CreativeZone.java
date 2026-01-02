package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.commands.CreateZone;
import dev.madtechs.creativeZone.commands.DeleteZone;
import dev.madtechs.creativeZone.commands.GetAllowedPlayers;
import dev.madtechs.creativeZone.commands.GoToOverworld;
import dev.madtechs.creativeZone.commands.GoToZone;
import dev.madtechs.creativeZone.commands.PullChunks;
import dev.madtechs.creativeZone.commands.AllowPlayer;
import dev.madtechs.creativeZone.dataControl.Control;

import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CreativeZone extends JavaPlugin implements Listener {
    private static CreativeZone instance;
    private static Control control;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        instance = this;
        control = new Control();

        getCommand("createZone").setExecutor(new CreateZone());
        getCommand("pullChunks").setExecutor(new PullChunks());
        getCommand("deleteZone").setExecutor(new DeleteZone());
        getCommand("goToOverworld").setExecutor(new GoToOverworld());
        getCommand("goToZone").setExecutor(new GoToZone());
        getCommand("allowPlayer").setExecutor(new AllowPlayer());
        getCommand("listAllowedPlayers").setExecutor(new GetAllowedPlayers());
    }

    public static CreativeZone getInstance() {
        return instance;
    }

    public static Control getControl() {
        return control;
    }
}
