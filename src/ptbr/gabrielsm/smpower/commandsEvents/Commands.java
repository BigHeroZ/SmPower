package ptbr.gabrielsm.smpower.commandsEvents;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ptbr.gabrielsm.smpower.metodos.Utils;


public class Commands implements CommandExecutor {

	Utils u = new Utils();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if(sender.hasPermission("smpower.commands")){
			if(cmd.getName().equalsIgnoreCase("smpower")){
				if(args.length < 3) {if(sender.hasPermission("smpower.commands")){
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
								sender.sendMessage(u.getString("Configuration.Apenas-players"));
								return true;
							}else{
								Player p = (Player)sender;
								if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max")) {
									if(args[2].matches(".*\\d.*") == true){
										int quantia = Integer.valueOf(args[2]);
										p.sendMessage(u.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso").replace("%quantia%", String.valueOf(quantia)));
										u.addItemPower(sender, p, quantia, true, false);
									}else{
										p.sendMessage(u.getString("Configuracao.Apenas-numeros"));
										return true;
									}
								}else
								if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power")) {
									if(args[2].matches(".*\\d.*") == true){
										int quantia = Integer.valueOf(args[2]);
										p.sendMessage(u.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso").replace("%quantia%", String.valueOf(quantia)));
										u.addItemPower(sender, p, quantia, false, true);
									}else{
										p.sendMessage(u.getString("Configuracao.Apenas-numeros"));
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
										sender.sendMessage(u.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso-ao-player").replace("%quantia%", String.valueOf(quantia)).replace("%alvo%", target.getName()));
										target.sendMessage(u.getString("Poder-maximo.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
										u.addItemPower(sender, target, quantia, true, false);
									}else{
										sender.sendMessage(u.getString("Configuracao.Player-offline"));
									}
								}else{
									sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
									return true;
								}
							}else
							if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max") && args[3].equalsIgnoreCase("all")) {
								if(args[2].matches(".*\\d.*") == true){
									int quantia = Integer.valueOf(args[2]);
									if(Bukkit.getOnlinePlayers().size() != 0){
										for(Player p2 : Bukkit.getOnlinePlayers()){
											p2.sendMessage(u.getString("Poder-maximo.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
											u.addItemPower(sender, p2, quantia, true, false);
										}
										sender.sendMessage(u.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso-todos").replace("%quantia%", String.valueOf(quantia)));
									}else{
										sender.sendMessage(u.getString("Configuracao.Nenhum-player-online"));
										return true;
									}
								}else{
									sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
									return true;
								}
							}else
							if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power") && !args[3].equalsIgnoreCase("all")) {
								if(args[2].matches(".*\\d.*") == true){
									int quantia = Integer.valueOf(args[2]);
									Player target = Bukkit.getPlayer(args[3]);
									if(target != null ){
										sender.sendMessage(u.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso-ao-player").replace("%quantia%", String.valueOf(quantia)).replace("%alvo%", target.getName()));
										target.sendMessage(u.getString("Poder-adicional.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
										u.addItemPower(sender, target, quantia, false, true);
									}else{
										sender.sendMessage(u.getString("Configuracao.Player-offline"));
									}
								}else{
									sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
									return true;
								}
							}else
							if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power") && args[3].equalsIgnoreCase("all")) {
								if(args[2].matches(".*\\d.*") == true){
									int quantia = Integer.valueOf(args[2]);
									if(Bukkit.getOnlinePlayers().size() != 0){
										for(Player p2 : Bukkit.getOnlinePlayers()){
											p2.sendMessage(u.getString("Poder-adicional.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
											u.addItemPower(sender, p2, quantia, false, true);
										}
									}else{
										sender.sendMessage(u.getString("Configuracao.Nenhum-player-online"));
										return true;
									}
									sender.sendMessage(u.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso-todos").replace("%quantia%", String.valueOf(quantia)));
								}else{
									sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
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
					sender.sendMessage(u.getString("Configuracao.Sem-permissao"));
				}
					sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7]");
					sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] [§e§lplayer§7§l]");
					sender.sendMessage("§2+§l§7 /smpower give max [§e§lquantia§7§l] all");
					sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l]");
					sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] [§e§lplayer§7§l]");
					sender.sendMessage("§2+§l§7 /smpower give power [§e§lquantia§7§l] all");
				}
				
				if(args.length == 3) {
					if(!(sender instanceof Player)){
						sender.sendMessage(u.getString("Configuration.Apenas-players"));
						return true;
					}else{
						Player p = (Player)sender;
						if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max")) {
							if(args[2].matches(".*\\d.*") == true){
								int quantia = Integer.valueOf(args[2]);
								p.sendMessage(u.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso").replace("%quantia%", String.valueOf(quantia)));
								u.addItemPower(sender, p, quantia, true, false);
							}else{
								p.sendMessage(u.getString("Configuracao.Apenas-numeros"));
								return true;
							}
						}else
						if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power")) {
							if(args[2].matches(".*\\d.*") == true){
								int quantia = Integer.valueOf(args[2]);
								p.sendMessage(u.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso").replace("%quantia%", String.valueOf(quantia)));
								u.addItemPower(sender, p, quantia, false, true);
							}else{
								p.sendMessage(u.getString("Configuracao.Apenas-numeros"));
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
								sender.sendMessage(u.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso-ao-player").replace("%quantia%", String.valueOf(quantia)).replace("%alvo%", target.getName()));
								target.sendMessage(u.getString("Poder-maximo.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
								u.addItemPower(sender, target, quantia, true, false);
							}else{
								sender.sendMessage(u.getString("Configuracao.Player-offline"));
							}
						}else{
							sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
							return true;
						}
					}else
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("max") && args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							if(Bukkit.getOnlinePlayers().size() != 0){
								for(Player p2 : Bukkit.getOnlinePlayers()){
									p2.sendMessage(u.getString("Poder-maximo.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
									u.addItemPower(sender, p2, quantia, true, false);
								}
								sender.sendMessage(u.getString("Poder-maximo.Mensagens.Item-dado-com-sucesso-todos").replace("%quantia%", String.valueOf(quantia)));
							}else{
								sender.sendMessage(u.getString("Configuracao.Nenhum-player-online"));
								return true;
							}
						}else{
							sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
							return true;
						}
					}else
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power") && !args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							Player target = Bukkit.getPlayer(args[3]);
							if(target != null ){
								sender.sendMessage(u.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso-ao-player").replace("%quantia%", String.valueOf(quantia)).replace("%alvo%", target.getName()));
								target.sendMessage(u.getString("Poder-adicional.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
								u.addItemPower(sender, target, quantia, false, true);
							}else{
								sender.sendMessage(u.getString("Configuracao.Player-offline"));
							}
						}else{
							sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
							return true;
						}
					}else
					if(args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("power") && args[3].equalsIgnoreCase("all")) {
						if(args[2].matches(".*\\d.*") == true){
							int quantia = Integer.valueOf(args[2]);
							if(Bukkit.getOnlinePlayers().size() != 0){
								for(Player p2 : Bukkit.getOnlinePlayers()){
									p2.sendMessage(u.getString("Poder-adicional.Mensagens.Item-recebido-mensagem").replace("%quantia%", String.valueOf(quantia)).replace("%admin%", sender.getName()));
									u.addItemPower(sender, p2, quantia, false, true);
								}
							}else{
								sender.sendMessage(u.getString("Configuracao.Nenhum-player-online"));
								return true;
							}
							sender.sendMessage(u.getString("Poder-adicional.Mensagens.Item-dado-com-sucesso-todos").replace("%quantia%", String.valueOf(quantia)));
						}else{
							sender.sendMessage(u.getString("Configuracao.Apenas-numeros"));
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
			sender.sendMessage(u.getString("Configuracao.Sem-permissao"));
		}
		return false;
	}
}
