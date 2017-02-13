package ptbr.gabrielsm.smpower.metodos;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ptbr.gabrielsm.smpower.Main;

public class Utils {


	public static void verificarLocal(String local){
		File fl = new File(local);
		if(fl.isDirectory() == false || !fl.exists()){
			fl.mkdirs();
		}if(fl.getParentFile().isDirectory() == false || !fl.getParentFile().exists()){
			fl.mkdirs();
		}
	}

	public static void createConfig(String config){
		File fl = new File("plugins/SmPower/"+config);
		verificarLocal("plugins/SmPower/");
		FileConfiguration cfg = UTF8.loadConfiguration(fl);
		if(!fl.exists()){
			Main.plugin.saveResource(config, false);
		}else{
			try {
				cfg.load(fl);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getString(String local){
		File fl = new File("plugins/SmPower/config.yml");
		FileConfiguration cfg = UTF8.loadConfiguration(fl);
		return cfg.getString(local).replace("&", "§");
	}
	public static List<String> getLore(String local){
		File fl = new File("plugins/SmPower/config.yml");
		FileConfiguration cfg = UTF8.loadConfiguration(fl);
		List<String> sts = cfg.getStringList(local);
		for(int i = 0; i < sts.size(); i++){
			String a = sts.get(i).replace("&", "§");
			sts.remove(i);
			sts.add(a);
		}
		return sts;
	}
	public static void addItemPower(CommandSender responsavel, Player p, int quantia, boolean powermax, boolean power){
		ItemStack stack = new ItemStack(Material.NETHER_STAR);
		ItemMeta stackmeta = stack.getItemMeta();
		if(Main.plugin.getConfig().getBoolean("Configuracao.Anti-abusers") == true){
			if(quantia >= Main.plugin.getConfig().getInt("Configuracao.Limite-por-comando")) {
				for(Player p3 : Bukkit.getOnlinePlayers()){
					if(p3.hasPermission("smpower.admin") && p3.getName() != responsavel.getName()){
						p3.sendMessage("§c[AVISO] O "+responsavel.getName() +" tentou pegar §a"+quantia+" §cde nether star, verifique se não é um abuser!");
					}
				}
				return;
			}
			if(power == true){
				stackmeta.setDisplayName(getString("Poder-adicional.Customizacao.Nome"));
				stackmeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
				stackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				stackmeta.setLore(getLore("Poder-adicional.Customizacao.Lore"));
				stack.setItemMeta(stackmeta);
			}
			if(powermax == true){
				stack = new ItemStack(Material.NETHER_STAR);
				stackmeta = stack.getItemMeta();
				stackmeta.setDisplayName(getString("Poder-maximo.Customizacao.Nome"));
				stackmeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
				stackmeta.setLore(getLore("Poder-maximo.Customizacao.Lore"));
				stackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				stack.setItemMeta(stackmeta);
			}
			for(int i = 0; i < quantia; i++){
				p.getInventory().addItem(stack);
			}
		}
	}
}
