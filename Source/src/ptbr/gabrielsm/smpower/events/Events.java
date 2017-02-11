package ptbr.gabrielsm.smpower.events;

import java.text.DecimalFormat;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ptbr.gabrielsm.smpower.Main;
import ptbr.gabrielsm.smpower.methods.Metodos;

import com.massivecraft.factions.entity.MPlayer;

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
					if(power+10 >= Main.pl.getConfig().getDouble("Poder-maximo.Maximo-de-poder-maximo")) {
						p1.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Limite-de-poder-maximo", "config.yml"));
						return;
					}else{
						p1.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Item-usado-com-sucesso", "config.yml").replace("[newmaxpower]", ""+(newpower+10)));
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
						double valor = Main.pl.getConfig().getDouble("Poder-adicional.Valor");
						double newpower = power + valor;
						int quantia = 1;
						if(power != maxpower) {
							p.setPower(newpower);
							DecimalFormat df = new DecimalFormat("0.#");
							df.format(newpower);
							p1.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Item-usado-com-sucesso", "config.yml").replace("[newpower]", df.format(newpower)));
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
							p1.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Powers-ja-completos", "config.yml"));
							return;
						}
					}
				}
			}
		}
	}
}
