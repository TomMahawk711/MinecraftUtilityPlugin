package at.hackertreffreutte.mup.blockreplenisher;

import at.hackertreffreutte.mup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockReplenisher implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        //placed last item
        if(placedLastItem(event)){
            //TODO
            //workaround fix later otherwise shovel will be removed when making a path
            if(event.getItemInHand().getMaxStackSize() == 1){
                return;
            }

            removePlacedItems(event);
            replenishItem(event);
        }
    }

    private static void replenishItem(BlockPlaceEvent event) {
        final PlayerInventory inventory = event.getPlayer().getInventory();
        Material placedMaterial = event.getItemInHand().getType();

        for(ItemStack item : inventory.getContents()){

            if(item != null && (item.getType().equals(placedMaterial))){
                Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), () -> replenishHand(event, inventory, item) ,1L);
                break;
            }
        }
    }

    private static void replenishHand(BlockPlaceEvent event, PlayerInventory inventory, ItemStack item) {

        if(event.getHand() == EquipmentSlot.HAND){
            inventory.setItemInMainHand(item.clone());
            item.setAmount(0);

        }else if(event.getHand() == EquipmentSlot.OFF_HAND){
            inventory.setItemInOffHand(item.clone());
            item.setAmount(0);
        }
    }

    private static boolean placedLastItem(BlockPlaceEvent event) {
        return event.getItemInHand().getAmount() == 1;
    }

    private static void removePlacedItems(BlockPlaceEvent event) {
        if(event.getHand() == EquipmentSlot.HAND){
            event.getItemInHand().setAmount(0);
        }else{
            event.getPlayer().getEquipment().getItemInOffHand().setAmount(0);
        }
    }
}
