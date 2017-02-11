package ptbr.gabrielsm.smpower;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import ptbr.gabrielsm.smpower.commands.Commands;
import ptbr.gabrielsm.smpower.events.Events;

public class Main extends JavaPlugin{
	
	public static Main pl;
	ConsoleCommandSender c = Bukkit.getConsoleSender();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new Events(), this);
		pl = this;
		c.sendMessage("§f+------===[§a§lSmPower§f]===------+");
		c.sendMessage("§ePlugin iniciado com sucesso!");
		c.sendMessage("§f+------===[§a§lSmPower§f]===------+");
		getCommand("smpower").setExecutor(new Commands());
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
		super.onDisable();
	}

}
