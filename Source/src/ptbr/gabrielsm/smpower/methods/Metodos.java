package ptbr.gabrielsm.smpower.methods;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import ptbr.gabrielsm.smpower.Main;

public class Metodos {
	
	static Main pl = Main.pl;
	
	public static void addItemPower(CommandSender responsavel, Player p, int quantia, boolean powermax, boolean power){
		ItemStack stack = new ItemStack(Material.NETHER_STAR);
		ItemMeta stackmeta = stack.getItemMeta();
		if(pl.getConfig().getBoolean("Configuracao.Anti-abusers") == true){
			if(quantia >= pl.getConfig().getInt("Configuracao.Limite-por-comando")) {
				for(Player p3 : Bukkit.getOnlinePlayers()){
					if(p3.hasPermission("smpower.admin") && p3.getName() != responsavel.getName()){
						p3.sendMessage("§c[AVISO] O "+responsavel.getName() +" tentou pegar §a"+quantia+" §cde nether star, verifique se não é um abuser!");
					}
				}
				return;
			}
			if(power == true){
				stackmeta.setDisplayName(getString("Poder-adicional.Customizacao.Nome", "config.yml"));
				stackmeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
				stackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				stack.setItemMeta(stackmeta);
			}
			if(powermax == true){
				stack = new ItemStack(Material.NETHER_STAR);
				stackmeta = stack.getItemMeta();
				stackmeta.setDisplayName(getString("Poder-maximo.Customizacao.Nome", "config.yml"));
				stackmeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
				stackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				stack.setItemMeta(stackmeta);
			}
			for(int i = 0; i < quantia; i++){
				p.getInventory().addItem(stack);
			}

		}
	}
	public static void removeItems(PlayerInventory inv, Material tipo, int quantia) {
		for (ItemStack stack : inv.getContents()) {
			if(stack.getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL) && stack.getType() == Material.NETHER_STAR){
				if (stack != null && stack.getType() == tipo) {
					int nvquantia= stack.getAmount() - quantia;
					if (nvquantia > 0) {
						stack.setAmount(nvquantia);
						break;
					} else {
						inv.remove(stack);
						quantia = -nvquantia;
						if (quantia == 0) {
							break;
						}
					}
				}
			}
			if (stack.getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE) && stack.getType().equals(Material.NETHER_STAR)){
				if (stack != null && stack.getType() == tipo) {
					int nvquantia= stack.getAmount() - quantia;
					if (nvquantia > 0) {
						stack.setAmount(nvquantia);
						break;
					} else {
						inv.remove(stack);
						quantia = -nvquantia;
						if (quantia == 0) {
							break;
						}
					}
				}
			}
		}
	}
	public static String getString(String local, String config){
		File file = new File("plugins/SmPower/"+config);
		if(file.exists()){
			return Main.pl.getConfig().getString(local).replace("&", "§");
		}else{
			System.out.print("[ERROR]Config "+file.getName()+" não encontrada");
			return "ERROR";
		}
	}
	public static List<String> getList(String local, String config){
		File file = new File("plugins/SmPower/"+config);
		if(file.exists()){
			return Main.pl.getConfig().getStringList(local);
		}else{			
			System.out.print("[ERROR] Config "+file.getName()+" não encontrada");
			return null;
		}
	}
}
