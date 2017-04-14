package ptbr.gabrielsm.smpower.metodos;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.entity.MPlayer;

import ptbr.gabrielsm.smpower.Main;

public class Utils {

	public void verificarLocal(String local) {
		File fl = new File(local);
		if (fl.isDirectory() == false || !fl.exists()) {
			fl.mkdirs();
		}
		if (fl.getParentFile().isDirectory() == false || !fl.getParentFile().exists()) {
			fl.mkdirs();
		}
	}

	public void createConfig(String config) {
		File fl = new File("plugins/SmPower/" + config);
		verificarLocal("plugins/SmPower/");
		FileConfiguration cfg = UTF8.loadConfiguration(fl);
		if (!fl.exists()) {
			Main.plugin.saveResource(config, false);
		} else {
			try {
				cfg.load(fl);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public String getString(String local) {
		File fl = new File("plugins/SmPower/config.yml");
		FileConfiguration cfg = UTF8.loadConfiguration(fl);
		return cfg.getString(local).replace("&", "§");
	}

	public List<String> getLore(String local) {
		File fl = new File("plugins/SmPower/config.yml");
		FileConfiguration cfg = UTF8.loadConfiguration(fl);
		List<String> lore = cfg.getStringList(local);
		List<String> newLore = new ArrayList<String>();
		for (String s : lore) {
		    newLore.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return newLore;
	}

	public void addItemPower(CommandSender responsavel, Player p, int quantia, boolean powermax, boolean power) {
		ItemStack stack = new ItemStack(Material.NETHER_STAR);
		ItemMeta stackmeta = stack.getItemMeta();
		if (Main.plugin.getConfig().getBoolean("Configuracao.Anti-abusers") == true) {
			if (quantia >= Main.plugin.getConfig().getInt("Configuracao.Limite-por-comando")) {
				for (Player p3 : Bukkit.getOnlinePlayers()) {
					if (p3.hasPermission("smpower.admin") && p3.getName() != responsavel.getName()) {
						p3.sendMessage("§c[AVISO] O " + responsavel.getName() + " tentou pegar §a" + quantia
								+ " §cde nether star, verifique se não é um abuser!");
					}
				}
				return;
			}
			if (power == true) {
				stackmeta.setDisplayName(getString("Poder-adicional.Customizacao.Nome"));
				stackmeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
				stackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				stackmeta.setLore(getLore("Poder-adicional.Customizacao.Lore"));
				stack.setItemMeta(stackmeta);
			}
			if (powermax == true) {
				stack = new ItemStack(Material.NETHER_STAR);
				stackmeta = stack.getItemMeta();
				stackmeta.setDisplayName(getString("Poder-maximo.Customizacao.Nome"));
				stackmeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
				stackmeta.setLore(getLore("Poder-maximo.Customizacao.Lore"));
				stackmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				stack.setItemMeta(stackmeta);
			}
			for (int i = 0; i < quantia; i++) {
				p.getInventory().addItem(stack);
			}
		}
	}

	public void setMaxPower(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action action = e.getAction();
		ItemStack item = e.getItem();
		Plugin mpp = Main.plugin.getServer().getPluginManager().getPlugin("MassiveCore");
		Plugin fpp = Main.plugin.getServer().getPluginManager().getPlugin("Factions");
		Plugin hpp = Main.plugin.getServer().getPluginManager().getPlugin("HardFacs");
		if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if (item != null && item.getItemMeta() != null) {
				if (item.getType() == Material.NETHER_STAR) {
					e.setCancelled(true);
					if (mpp != null) {
						MPlayer mp = MPlayer.get(e.getPlayer());
						if (item.getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL)) {
							if (mp.getPowerBoost() + 10 >= Main.plugin.getConfig()
									.getDouble("Poder-maximo.Maximo-de-poder-maximo")) {
								p.sendMessage(getString("Poder-maximo.Mensagens.Limite-de-poder-maximo"));
								return;
							} else {
								p.sendMessage(getString("Poder-maximo.Mensagens.Item-usado-com-sucesso")
										.replace("[newmaxpower]", "" + (mp.getPowerBoost() + 1.0 + 10)));
								mp.setPowerBoost(mp.getPowerBoost() + 1.0);
								int quantia = 1;
								int nvquantia = item.getAmount() - quantia;
								if (nvquantia > 0) {
									item.setAmount(nvquantia);
									return;
								} else {
									p.getInventory().remove(item);
									quantia = -nvquantia;
									if (quantia == 0) {
										return;
									}
								}
							}
						} else if (item.getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE)) {
							double valor = Main.plugin.getConfig().getDouble("Poder-adicional.Valor");
							int quantia = 1;
							int nvquantia = item.getAmount() - quantia;
							if (mp.getPower() != mp.getPowerMax()) {
								mp.setPower(mp.getPower() + valor);
								DecimalFormat df = new DecimalFormat("0.##");
								String format = df.format(mp.getPower() + valor);
								p.sendMessage(getString("Poder-adicional.Mensagens.Item-usado-com-sucesso")
										.replace("[newpower]", format));
								if (nvquantia > 0) {
									item.setAmount(nvquantia);
									return;
								} else {
									p.getInventory().remove(item);
									quantia = -nvquantia;
									if (quantia == 0) {
										return;
									}
								}
							} else {
								p.sendMessage(getString("Poder-adicional.Mensagens.Powers-ja-completos"));
								return;
							}
						}
					}
					if (fpp != null) {
						FPlayer fp = FPlayers.i.get(e.getPlayer());
						if (item.getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL)) {
							if (fp.getPowerBoost() + 10 >= Main.plugin.getConfig()
									.getDouble("Poder-maximo.Maximo-de-poder-maximo")) {
								p.sendMessage(getString("Poder-maximo.Mensagens.Limite-de-poder-maximo"));
								return;
							} else {
								p.sendMessage(getString("Poder-maximo.Mensagens.Item-usado-com-sucesso")
										.replace("[newmaxpower]", "" + (fp.getPowerBoost() + 1.0 + 10)));
								fp.setPowerBoost(fp.getPowerBoost() + 1.0);
								int quantia = 1;
								int nvquantia = item.getAmount() - quantia;
								if (nvquantia > 0) {
									item.setAmount(nvquantia);
									return;
								} else {
									p.getInventory().remove(item);
									quantia = -nvquantia;
									if (quantia == 0) {
										return;
									}
								}
							}
						} else if (item.getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE)) {
							e.setCancelled(true);
							p.sendMessage("§cFunção indisponivel...");
						}
					}
					if (hpp != null) {
						FPlayer fp = FPlayers.i.get(e.getPlayer());
						if (item.getItemMeta().getEnchants().containsKey(Enchantment.DAMAGE_ALL)) {
							if (fp.getPowerBoost() + 10 >= Main.plugin.getConfig()
									.getDouble("Poder-maximo.Maximo-de-poder-maximo")) {
								p.sendMessage(getString("Poder-maximo.Mensagens.Limite-de-poder-maximo"));
								return;
							} else {
								p.sendMessage(getString("Poder-maximo.Mensagens.Item-usado-com-sucesso")
										.replace("[newmaxpower]", "" + (fp.getPowerBoost() + 1.0 + 10)));
								fp.setPowerBoost(fp.getPowerBoost() + 1.0);
								int quantia = 1;
								int nvquantia = item.getAmount() - quantia;
								if (nvquantia > 0) {
									item.setAmount(nvquantia);
									return;
								} else {
									p.getInventory().remove(item);
									quantia = -nvquantia;
									if (quantia == 0) {
										return;
									}
								}
							}
						} else if (item.getItemMeta().getEnchants().containsKey(Enchantment.ARROW_FIRE)) {
							e.setCancelled(true);
							p.sendMessage("§cFunção indisponivel...");
						}
					}
				}
			}
		}
	}

	public void error(String msg) {
		Main.plugin.getLogger().severe(msg);
	}
}
