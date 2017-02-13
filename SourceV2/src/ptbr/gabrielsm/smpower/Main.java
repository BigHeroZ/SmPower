package ptbr.gabrielsm.smpower;

import org.bukkit.plugin.java.JavaPlugin;

import ptbr.gabrielsm.smpower.commandsEvents.Commands;
import ptbr.gabrielsm.smpower.commandsEvents.Events;
import ptbr.gabrielsm.smpower.metodos.Utils;

public class Main extends JavaPlugin {
	
	public static Main plugin;

	public Main getMain(){
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		Utils.createConfig("config.yml");
		getLogger().info("Plugin iniciado com sucesso!");
		getCommand("smpower").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Events(), this);
	}
	
	@Override
	public void onDisable() {
	}
	
}
