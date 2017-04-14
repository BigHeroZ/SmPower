package ptbr.gabrielsm.smpower;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ptbr.gabrielsm.smpower.commandsEvents.Commands;
import ptbr.gabrielsm.smpower.commandsEvents.Events;
import ptbr.gabrielsm.smpower.metodos.Utils;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	Utils u = new Utils();
	
	public Main getMain(){
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		u.createConfig("config.yml");
		getLogger().info("Plugin iniciado com sucesso!");
		checkPlugins();
		getCommand("smpower").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Events(), this);
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
	}
	void checkPlugins(){
		Plugin fm = getServer().getPluginManager().getPlugin("MassiveCore");
		Plugin fh = getServer().getPluginManager().getPlugin("HardFacs");
		Plugin fo = getServer().getPluginManager().getPlugin("Factions");
		if(fm == null && fh == null && fo == null){
			u.error("§cNenhum plugin de factions encontrado, este plugin terá mau funcionamento!");
		}
	}
}
