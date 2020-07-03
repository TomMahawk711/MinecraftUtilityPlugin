package hackertreffreutte.mup.BlockReplenisher;

import hackertreffreutte.mup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BlockReplenisher implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        //placed last item
        if(event.getItemInHand().getAmount() == 1){


            PlayerInventory inv = event.getPlayer().getInventory();

            //get material of placed item
            Material placedMaterial = event.getItemInHand().getType();

            //remove placed item
            event.getItemInHand().setAmount(0);

            for(int i= 0; i < inv.getContents().length; i++){
                ItemStack item = inv.getItem(i);

                if(item != null){
                    if(item.getType().equals(placedMaterial)){
                        //found same item like that that was placed

                        //replenish the item (move the item from the inventory to the main hand)
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                inv.setItemInMainHand(item.clone());
                                item.setAmount(0);
                            }
                        },1L);

                        break;
                    }
                }
            }
        }
    }
}
