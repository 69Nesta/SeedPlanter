package fr.nesta.seedplanter.commands;

import fr.nesta.seedplanter.CustomEnchants;
import fr.nesta.seedplanter.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static fr.nesta.seedplanter.Main.materialEnchants;

public class SeedPlanterCommand implements CommandExecutor {
    private Main main;

    public SeedPlanterCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("SeedPlanter")) {
                if(!materialEnchants.contains(p.getInventory().getItemInMainHand().getType())) {
                    p.sendMessage(this.main.getConfig().getString("SeedPlanter.wrongItem")
                            .replace("%prefix%", this.main.getConfig().getString("prefix"))
                            .replace("&", "§"));
                    return true;
                }
                ItemStack it = p.getInventory().getItemInMainHand().clone();

                if (!it.containsEnchantment(CustomEnchants.SEEDPLANTER)) {
                    it.addUnsafeEnchantment(CustomEnchants.SEEDPLANTER, 1);

                    ItemMeta meta = it.getItemMeta();
                    List<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.GRAY + this.main.getConfig().getString("SeedPlanter.lore").replace('&', '§'));
                    if (meta.hasLore())
                        for (String l : meta.getLore())
                            lore.add(l);
                    meta.setLore(lore);
                    it.setItemMeta(meta);

                    p.getInventory().setItemInMainHand(it);
                    p.sendMessage(this.main.getConfig().getString("SeedPlanter.activate.done")
                            .replace("%prefix%", this.main.getConfig().getString("prefix"))
                            .replace("&", "§"));


                } else if(it.containsEnchantment(CustomEnchants.SEEDPLANTER)) {
                    it.removeEnchantment(CustomEnchants.SEEDPLANTER);

                    ItemMeta meta = it.getItemMeta();
                    List<String> lore = new ArrayList<String>();
                    if (meta.hasLore())
                        for (String l : meta.getLore())
                            lore.add(l);

                    lore.remove(ChatColor.GRAY + this.main.getConfig().getString("SeedPlanter.lore").replace('&', '§'));

                    meta.setLore(lore);
                    it.setItemMeta(meta);

                    p.getInventory().setItemInMainHand(it);
                    p.sendMessage(this.main.getConfig().getString("SeedPlanter.remove.done")
                            .replace("%prefix%", this.main.getConfig().getString("prefix"))
                            .replace("&", "§"));
                } else {
                    p.sendMessage(this.main.getConfig().getString("SeedPlanter.error")
                            .replace("%prefix%", this.main.getConfig().getString("prefix"))
                            .replace("&", "§"));
                }
            }
        }
        return true;
    }
}
