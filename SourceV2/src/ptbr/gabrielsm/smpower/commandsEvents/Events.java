package ptbr.gabrielsm.smpower.commandsEvents;

import java.text.DecimalFormat;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.entity.MPlayer;

import ptbr.gabrielsm.smpower.Main;
import ptbr.gabrielsm.smpower.metodos.Utils;

public class Events implements Listener {

	@EventHandler
	public void onCheck(PlayerInteractEvent e){
		MPlayer  p = MPlayer.get(e.getPlayer());
		Player p1 = e.getPlayer();
		ItemStack stack = e.getItem();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(e.getItem() != null && e.getItem().getItemMeta() != null){
				if(e.getItem().getType() == Material.NETHER_STAR){
					e.setCancelled(true);
					if(stack.getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL)){
						double power = p.getPowerBoost();
						double valor = 1.0;
						double newpower = power + valor;
						if(power+10 >= Main.plugin.getConfig().getDouble("Poder-maximo.Maximo-de-poder-maximo")) {
							p1.sendMessage(Utils.getString("Poder-maximo.Mensagens.Limite-de-poder-maximo"));
							return;
						}else{
							p1.sendMessage(Utils.getString("Poder-maximo.Mensagens.Item-usado-com-sucesso").replace("[newmaxpower]", ""+(newpower+10)));
							p.setPowerBoost(newpower);
							int quantia = 1;
							int nvquantia= stack.getAmount() - quantia;
							if (nvquantia > 0) {
								stack.setAmount(nvquantia);
								return;
							} else {
								p1.getInventory().remove(stack);
								quantia = -nvquantia;
								if (quantia == 0) {
									return;
								}
							}
						}
					}else
						if(stack.getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE)){
							double power = p.getPower();
							double maxpower= p.getPowerMax();
							double valor = Main.plugin.getConfig().getDouble("Poder-adicional.Valor");
							double newpower = power + valor;
							int quantia = 1;
							if(power != maxpower) {
								p.setPower(newpower);
								DecimalFormat df = new DecimalFormat("0.##");
								df.format(newpower);
								p1.sendMessage(Utils.getString("Poder-adicional.Mensagens.Item-usado-com-sucesso").replace("[newpower]", df.format(newpower)));
								int nvquantia= stack.getAmount() - quantia;
								if (nvquantia > 0) {
									stack.setAmount(nvquantia);
									return;
								} else {
									p1.getInventory().remove(stack);
									quantia = -nvquantia;
									if (quantia == 0) {
										return;
									}
								}
							}else{
								p1.sendMessage(Utils.getString("Poder-adicional.Mensagens.Powers-ja-completos"));
								return;
							}
						}
				}
			}
		}
	}

	@EventHandler
	public void renameCancel(InventoryClickEvent e){
		if(e.getInventory() instanceof AnvilInventory){
			if(Main.plugin.getConfig().getBoolean("Configuracao.Anti-rename") == true){
				if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null){
					if(e.getCurrentItem().getType().equals(Material.NETHER_STAR)){
						if(e.getCurrentItem().getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL)){
							e.setCancelled(true);
							e.getWhoClicked().sendMessage(Utils.getString("Configuracao.Nao-pode-renomear"));
						}
						if(e.getCurrentItem().getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE)){
							e.setCancelled(true);
							e.getWhoClicked().sendMessage(Utils.getString("Configuracao.Nao-pode-renomear"));
						}
					}
				}
			}
		}
	}
}
