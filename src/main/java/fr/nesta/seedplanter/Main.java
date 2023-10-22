package fr.nesta.seedplanter;

import fr.nesta.seedplanter.commands.SeedPlanterCommand;
import fr.nesta.seedplanter.listeners.planterListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    public static List<Material> materialEnchants = new ArrayList<Material>();
    @Override
    public void onEnable() {
        getLogger().info("Plugin is enabled !");
        saveDefaultConfig();

        for (Object o : getConfig().getList("SeedPlanter.items")) {
            materialEnchants.add(Material.getMaterial(o.toString()));
            getLogger().info(materialEnchants.toString());
        }

        CustomEnchants.register();

        getCommand("SeedPlanter").setExecutor((CommandExecutor) new SeedPlanterCommand(this));
        Bukkit.getPluginManager().registerEvents((Listener) new planterListener(this), (Plugin) this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is disabled !");
    }
}
