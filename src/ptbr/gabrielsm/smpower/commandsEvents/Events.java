package ptbr.gabrielsm.smpower.commandsEvents;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;

import ptbr.gabrielsm.smpower.Main;
import ptbr.gabrielsm.smpower.metodos.Utils;

public class Events implements Listener {

	Utils u = new Utils();
	
	@EventHandler
	public void onCheck(PlayerInteractEvent e){
		u.setMaxPower(e);
	}

	@EventHandler
	public void renameCancel(InventoryClickEvent e){
		if(e.getInventory() instanceof AnvilInventory){
			if(Main.plugin.getConfig().getBoolean("Configuracao.Anti-rename") == true){
				if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null){
					if(e.getCurrentItem().getType().equals(Material.NETHER_STAR)){
						if(e.getCurrentItem().getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL)){
							e.setCancelled(true);
							e.getWhoClicked().sendMessage(u.getString("Configuracao.Nao-pode-renomear"));
						}
						if(e.getCurrentItem().getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE)){
							e.setCancelled(true);
							e.getWhoClicked().sendMessage(u.getString("Configuracao.Nao-pode-renomear"));
						}
					}
				}
			}
		}
	}
}
