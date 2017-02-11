package ptbr.gabrielsm.smpower.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ptbr.gabrielsm.smpower.methods.Metodos;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender.hasPermission("smpower.commands")){
			if(cmd.getName().equalsIgnoreCase("smpower")){
				if(args.length < 3) {
					sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7]");
					sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] [§e§lplayer§7§l]");
					sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] all");
					sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l]");
					sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] [§e§lplayer§7§l]");
					sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] all");
				}
				
				if(args.length == 3) {
					if(!(sender instanceof Player)){
						sender.sendMessage(Metodos.getString("Configuration.Apenas-players", "config.yml"));
						return true;
					}else{
						Player p = (Player)sender;
						if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max")) {
							if(args[2].matches(".*\\d.*") == true){
								int quantia = Integer.valueOf(args[2]);
								p.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso", "config.yml").replace("%quantia%", String.valueOf(quantia)));
								Metodos.addItemPower(sender, p, quantia, true, false);
							}else{
								p.sendMessage(Metodos.getString("Configuracao.Apenas-numeros", "config.yml"));
								return true;
							}
						}else
						if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power")) {
							if(args[2].matches(".*\\d.*") == true){
								int quantia = Integer.valueOf(args[2]);
								p.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso", "config.yml").replace("%quantia%", String.valueOf(quantia)));
								Metodos.addItemPower(sender, p, quantia, false, true);
							}else{
								p.sendMessage(Metodos.getString("Configuracao.Apenas-numeros", "config.yml"));
								return true;

							}
						}else{
							sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7]");
							sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] [§e§lplayer§7§l]");
							sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] all");
							sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l]");
							sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] [§e§lplayer§7§l]");
							sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] all");
						}
					}
				}
				if(args.length == 4){
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max") && !args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							Player target = Bukkit.getPlayer(args[3]);
							if(target != null ){
								sender.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso-ao-player", "config.yml").replace("%quantia%", String.valueOf(quantia)).replace("%alvo%", target.getName()));
								target.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Item-recebido-mensagem", "config.yml").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
								Metodos.addItemPower(sender, target, quantia, true, false);
							}else{
								sender.sendMessage(Metodos.getString("Configuracao.Player-offline", "config.yml"));
							}
						}else{
							sender.sendMessage(Metodos.getString("Configuracao.Apenas-numeros", "config.yml"));
							return true;
						}
					}else
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max") && args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							if(Bukkit.getOnlinePlayers().size() != 0){
								for(Player p2 : Bukkit.getOnlinePlayers()){
									p2.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Item-recebido-mensagem", "config.yml").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
									Metodos.addItemPower(sender, p2, quantia, true, false);
								}
								sender.sendMessage(Metodos.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso-todos", "config.yml").replace("%quantia%", String.valueOf(quantia)));
							}else{
								sender.sendMessage(Metodos.getString("Configuracao.Nenhum-player-online", "config.yml"));
								return true;
							}
						}else{
							sender.sendMessage(Metodos.getString("Configuracao.Apenas-numeros", "config.yml"));
							return true;
						}
					}else
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power") && !args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							Player target = Bukkit.getPlayer(args[3]);
							if(target != null ){
								sender.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso-ao-player", "config.yml").replace("%quantia%", String.valueOf(quantia)).replace("%alvo%", target.getName()));
								target.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Item-recebido-mensagem", "config.yml").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
								Metodos.addItemPower(sender, target, quantia, false, true);
							}else{
								sender.sendMessage(Metodos.getString("Configuracao.Player-offline", "config.yml"));
							}
						}else{
							sender.sendMessage(Metodos.getString("Configuracao.Apenas-numeros", "config.yml"));
							return true;
						}
					}else
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power") && args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							if(Bukkit.getOnlinePlayers().size() != 0){
								for(Player p2 : Bukkit.getOnlinePlayers()){
									p2.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Item-recebido-mensagem", "config.yml").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
									Metodos.addItemPower(sender, p2, quantia, false, true);
								}
							}else{
								sender.sendMessage(Metodos.getString("Configuracao.Nenhum-player-online", "config.yml"));
								return true;
							}
							sender.sendMessage(Metodos.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso-todos", "config.yml").replace("%quantia%", String.valueOf(quantia)));
						}else{
							sender.sendMessage(Metodos.getString("Configuracao.Apenas-numeros", "config.yml"));
							return true;
						}
					}else{
						sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7]");
						sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] [§e§lplayer§7§l]");
						sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] all");
						sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l]");
						sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] [§e§lplayer§7§l]");
						sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] all");
					}
				}
			}
		}else{
			sender.sendMessage(Metodos.getString("Configuracao.Sem-permissao", "config.yml"));
		}
		return false;
	}

}
