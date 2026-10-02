package dev.madtechs.creativeZone;

import dev.madtechs.creativeZone.commands.CreateZone;
import dev.madtechs.creativeZone.commands.DeleteZone;
import dev.madtechs.creativeZone.commands.GetAllowedPlayers;
import dev.madtechs.creativeZone.commands.LeaveZone;
import dev.madtechs.creativeZone.commands.GoToZone;
import dev.madtechs.creativeZone.commands.PullChunks;
import dev.madtechs.creativeZone.commands.ReturnToZone;
import dev.madtechs.creativeZone.commands.AllowPlayer;
import dev.madtechs.creativeZone.dataControl.Control;
import dev.madtechs.creativeZone.eventListeners.Death;
import dev.madtechs.creativeZone.eventListeners.PlayerJoinLeave;
import dev.madtechs.creativeZone.eventListeners.PlayerTeleport;
import dev.madtechs.creativeZone.eventListeners.ZoneChunkListener;
import dev.madtechs.creativeZone.eventListeners.ZonePortal;
import dev.madtechs.creativeZone.worldStateControl.ZoneGuard;

import org.bukkit.plugin.java.JavaPlugin;

public class CreativeZone extends JavaPlugin {
    private static CreativeZone instance;
    private static Control control;

    private final ZoneGuard guard = new ZoneGuard();

    @SuppressWarnings("null")
    @Override
    public void onEnable() {
        instance = this;
        control = new Control();

        // Command Registers
        getCommand("createZone").setExecutor(new CreateZone());
        getCommand("pullChunks").setExecutor(new PullChunks());
        getCommand("deleteZone").setExecutor(new DeleteZone(guard, this));
        getCommand("leaveZone").setExecutor(new LeaveZone());
        getCommand("goToZone").setExecutor(new GoToZone());
        getCommand("returnToZone").setExecutor(new ReturnToZone());
        getCommand("allowPlayer").setExecutor(new AllowPlayer());
        getCommand("listAllowedPlayers").setExecutor(new GetAllowedPlayers());

        // Event listeners registers
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(), this);
        getServer().getPluginManager().registerEvents(new ZoneChunkListener(), this);
        getServer().getPluginManager().registerEvents(new ZonePortal(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);

        // Waits 1 minute to run the task and then runs the task every 1 minute
        // Bukkit.getScheduler().runTaskTimer(this, () -> ZoneUnloader.checkToUnload(guard), 20L * 60, 20L * 60);
    }

    public static CreativeZone getInstance() {
        return instance;
    }

    public static Control getControl() {
        return control;
    }
}
