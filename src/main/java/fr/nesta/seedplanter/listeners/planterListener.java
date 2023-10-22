package fr.nesta.seedplanter.listeners;

import fr.nesta.seedplanter.CustomEnchants;
import fr.nesta.seedplanter.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
public class planterListener implements Listener {
    private Main main;

    public planterListener(Main main) {
        this.main = main;
    }

    @EventHandler()
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.SEEDPLANTER));

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        List<Material> seeds = new ArrayList<>();
        seeds.add(Material.BEETROOT_SEEDS);
        seeds.add(Material.WHEAT_SEEDS);
        seeds.add(Material.CARROT);
        seeds.add(Material.POTATO);

        if (block.getType() != Material.FARMLAND) return;

        if (player.getInventory().getItem(0) == null) return;
        if (!seeds.contains(player.getInventory().getItem(0).getType())) return;

        if (block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ()).getType() != Material.AIR) return;

        Location loc = block.getLocation();
        Block[] blocks = getBlocks(block);

        for (Block b : blocks) {
            if (b.getType() == Material.FARMLAND && block.getWorld().getBlockAt(b.getX(), b.getY()+1, b.getZ()).getType() == Material.AIR) {

                block.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ()).setType(getMaterial(player.getInventory().getItem(0).getType()));
                player.getInventory().getItem(0).setAmount(player.getInventory().getItem(0).getAmount()-1);
                if (player.getInventory().getItem(0) == null) return;
            }
        }



    }

    private Material getMaterial(Material m) {
        switch (m) {
            case BEETROOT_SEEDS:
                return Material.BEETROOTS;
            case WHEAT_SEEDS:
                return Material.WHEAT;
            case CARROT:
                return Material.CARROTS;
            case POTATO:
                return Material.POTATOES;
            default:
                return null;
        }
    }
    private Block[] getBlocks(Block center) {
        Block[] blocks = new Block[9];

        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();


        blocks[0] = center;
        blocks[1] = center.getWorld().getBlockAt(x-1, y, z-1);
        blocks[2] = center.getWorld().getBlockAt(x-1, y, z);
        blocks[3] = center.getWorld().getBlockAt(x-1, y, z+1);

        blocks[4] = center.getWorld().getBlockAt(x, y, z-1);
        blocks[5] = center.getWorld().getBlockAt(x, y, z+1);

        blocks[6] = center.getWorld().getBlockAt(x+1, y, z-1);
        blocks[7] = center.getWorld().getBlockAt(x+1, y, z);
        blocks[8] = center.getWorld().getBlockAt(x+1, y, z+1);

        return blocks;
    }
}
