package me.Johnny.PersistentTeamDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import org.bukkit.ChatColor;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Johnny.PersistentTeamDataType.Events.Events;
import me.Johnny.PersistentTeamDataType.Models.TeamType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class TeamCommands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			// Initialization and housekeeping stuff
			HashMap<String, Integer> map = new HashMap<>();
			map.put("GERUDO", 0);
			map.put("RITO", 1);
			map.put("KOROK", 2);
			map.put("GORON", 3);
			map.put("ZORA", 4);
			map.put("NEUTRAL", 5);
			
			List<ChatColor> cc = new ArrayList<ChatColor>();
			cc.add(ChatColor.YELLOW);
			cc.add(ChatColor.AQUA);
			cc.add(ChatColor.GREEN);
			cc.add(ChatColor.RED);
			cc.add(ChatColor.BLUE);
			cc.add(ChatColor.WHITE);
			
			NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
			
			Events e = new Events();
			
			// Test command for sanity purposes
			if (label.equalsIgnoreCase("test")) {
				player.sendMessage("Could you be the one?");
			}
			
			// Feature that allows for users to hover/click on the team they would like to join
			if (label.equalsIgnoreCase("teams")) {
				player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Select a Team Shown Below:");
				showTeams(map, cc, player);
			}
			
			if (label.equalsIgnoreCase("squad")) {
				String teamOfPlayer = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
				List<String> team_members = new ArrayList<String>();
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getPersistentDataContainer().get(key, new TeamDataType()).toString().equals(teamOfPlayer)) {
						team_members.add(p.getDisplayName());
					}
				}
				player.sendMessage(cc.get(map.get(teamOfPlayer)) +  "" + ChatColor.BOLD + teamOfPlayer + " members:");
				player.sendMessage(ChatColor.WHITE + "" + team_members);
			}
			
			
			// Team assignment code
			if (label.equalsIgnoreCase("assign")) {
				
				//----------------------------------------------
				
				clearChestPlate(player);
				
//				for (Player p: Bukkit.getOnlinePlayers()) {
//					String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
//					updateScoreboard(p, teamOfPlayer);
//				}
				
				if (args.length > 0) {
					// JOIN THE GERUDO TEAM
					if (args[0].equalsIgnoreCase("gerudo")) {
						teamAssignment(player, ChatColor.YELLOW, TeamType.GERUDO);
						
						for (Player p: Bukkit.getOnlinePlayers()) {
							String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
							updateScoreboard(p, teamOfPlayer);
						}
						
//						e.createScoreboard(player, "GERUDO");
						return true;
					}
					// JOIN THE RITO TEAM
					else if (args[0].equalsIgnoreCase("rito")) {
						teamAssignment(player, ChatColor.AQUA, TeamType.RITO);
//						e.createScoreboard(player, "RITO");
						Bukkit.broadcastMessage(ChatColor.GREEN + "[Server]" + ChatColor.GOLD + "YOU SWITCHED TEAMS!");
						ItemStack elytra= new ItemStack(Material.ELYTRA);
						ItemMeta meta = elytra.getItemMeta();
						meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
						meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
						meta.setUnbreakable(true);
						elytra.setItemMeta(meta);
						player.getInventory().setChestplate(elytra);
						player.sendMessage("WINGS GIVEN!");
						
						for (Player p: Bukkit.getOnlinePlayers()) {
							String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
							updateScoreboard(p, teamOfPlayer);
						}
						return true;
					}
					// JOIN THE KOROK TEAM
					else if (args[0].equalsIgnoreCase("korok")) {
						teamAssignment(player, ChatColor.GREEN, TeamType.KOROK);
						
						for (Player p: Bukkit.getOnlinePlayers()) {
							String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
							updateScoreboard(p, teamOfPlayer);
						}
						
//						e.createScoreboard(player, "KOROK");
						
						// DOES NOT PROPERLY WORK!!!
						// Create a scoreboard for each player based on their team type
//						NamespacedKey key = new NamespacedKey(Main.getPlugin(), "teamType");
//						for (Player p: Bukkit.getServer().getOnlinePlayers()) {
//							String teamColor = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
//							Bukkit.broadcastMessage(p.getDisplayName());
//							createScoreboard(p, teamColor);
//						}
						return true;
					}
					// JOIN THE GORON TEAM
					else if (args[0].equalsIgnoreCase("goron")) {
						teamAssignment(player, ChatColor.RED, TeamType.GORON);
						
						for (Player p: Bukkit.getOnlinePlayers()) {
							String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
							updateScoreboard(p, teamOfPlayer);
						}
						
//						e.createScoreboard(player, "GORON");
						return true;
					}
					// JOIN THE ZORA TEAM
					else if (args[0].equalsIgnoreCase("zora")) {
						teamAssignment(player, ChatColor.BLUE, TeamType.ZORA);
						
						for (Player p: Bukkit.getOnlinePlayers()) {
							String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
							updateScoreboard(p, teamOfPlayer);
						}
						
//						e.createScoreboard(player, "ZORA");
						return true;
					}
					// JOIN THE NEUTRAL TEAM
					else if (args[0].equalsIgnoreCase("neutral")) {
						teamAssignment(player, ChatColor.WHITE, TeamType.NEUTRAL);
						
						for (Player p: Bukkit.getOnlinePlayers()) {
							String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
							updateScoreboard(p, teamOfPlayer);
						}
						
//						e.createScoreboard(player, "NEUTRAL");
						return true;
					}
					else {
						player.sendMessage(ChatColor.WHITE + args[0] + " is an invalid team name...");
						return true;
					}
				}
				else {
					player.sendMessage(ChatColor.RED + "You must enter a team to get assigned to /assign <TEAM NAME>");
					return true;
				}
			}
			
			if (label.equals("sb")) {
				player.sendMessage(ChatColor.WHITE + "" + player.getScoreboard());
			}
			
			
			// Command that allows user to check what team they are on
			if (label.equalsIgnoreCase("whichteam")) {
				if (args.length == 0) {
					PersistentDataContainer data = player.getPersistentDataContainer();
					NamespacedKey keyValue = new NamespacedKey(Main.getPlugin(), "teamType");
					String team = data.get(keyValue, new TeamDataType()).toString();
					player.sendMessage(ChatColor.GRAY + "You are on the : " + cc.get(map.get(team)) + ChatColor.BOLD + data.get(keyValue, new TeamDataType()) + ChatColor.WHITE +" team!");
					return true;
				}
				else {
					checkTeam(player, args[0]);
				}
			}
		}
		return false;
	}
	
	public void showTeams(HashMap<String, Integer> map, List<ChatColor> cc, Player player) {
		for (String teamName: map.keySet()) {
			TextComponent message = new TextComponent(teamName);
			message.setColor(cc.get(map.get(teamName)));
			message.setBold(true);
			message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/assign " + teamName));
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click here to join the " + teamName + ChatColor.WHITE + " team")));
			player.spigot().sendMessage(message);
		}
	}
	
	// Helper Function
	
	@SuppressWarnings("deprecation")
	public void updateScoreboard(Player player, String displayName) {
		Bukkit.broadcastMessage("Updating...");
		// Create a separate scoreboard from the above completed
		// Add the same teams and add the same players to each team
		// Assign that specific scoreboard with the unique objective to that player
		
		ScoreboardManager mngr = Bukkit.getScoreboardManager();
		Scoreboard f_board = mngr.getNewScoreboard();
		
		Team gerudo = f_board.registerNewTeam("GERUDO");
		Team rito = f_board.registerNewTeam("RITO");
		Team korok = f_board.registerNewTeam("KOROK");
		Team goron = f_board.registerNewTeam("GORON");
		Team zora = f_board.registerNewTeam("ZORA");
		Team neutral = f_board.registerNewTeam("NEUTRAL");
		Objective objective = f_board.registerNewObjective("RACE", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		gerudo.setPrefix(ChatColor.YELLOW + "[GERUDO]" + ChatColor.WHITE);
		rito.setPrefix(ChatColor.AQUA+ "[RITO]" + ChatColor.WHITE);
		korok.setPrefix(ChatColor.GREEN+ "[KOROK]" + ChatColor.WHITE);
		goron.setPrefix(ChatColor.RED+ "[GORON]" + ChatColor.WHITE);
		zora.setPrefix(ChatColor.BLUE+ "[ZORA]" + ChatColor.WHITE);
		neutral.setPrefix(ChatColor.WHITE+ "[NEUTRAL]" + ChatColor.WHITE);
		
		Score score = objective.getScore("Players:");
		score.setScore(Bukkit.getOnlinePlayers().size());
		
//		 Initialization and housekeeping stuff
		HashMap<String, Integer> map = new HashMap<>();
		map.put("GERUDO", 0);
		map.put("RITO", 1);
		map.put("KOROK", 2);
		map.put("GORON", 3);
		map.put("ZORA", 4);
		map.put("NEUTRAL", 5);
		
		List<ChatColor> cc = new ArrayList<ChatColor>();
		cc.add(ChatColor.YELLOW);
		cc.add(ChatColor.AQUA);
		cc.add(ChatColor.GREEN);
		cc.add(ChatColor.RED);
		cc.add(ChatColor.BLUE);
		cc.add(ChatColor.WHITE);
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		for (Player p: Bukkit.getOnlinePlayers()) {
			
			String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			if (teamOfPlayer.equalsIgnoreCase("GERUDO")) {
				gerudo.addPlayer(p);
				Bukkit.broadcastMessage(ChatColor.WHITE + teamOfPlayer);
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}	
			else if (teamOfPlayer.equalsIgnoreCase("RITO")) {
				rito.addPlayer(p);
				p.sendMessage("R TIME");
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
			else if (teamOfPlayer.equalsIgnoreCase("KOROK")) {
				korok.addPlayer(p);
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
			else if (teamOfPlayer.equalsIgnoreCase("GORON")) {
				goron.addPlayer(p);
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
			else if (teamOfPlayer.equalsIgnoreCase("ZORA")) {
				zora.addPlayer(p);
				Bukkit.broadcastMessage(ChatColor.WHITE + teamOfPlayer);
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
			else if (teamOfPlayer.equalsIgnoreCase("NEUTRAL")) {
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
		}		
		player.setScoreboard(f_board);
	}
	
	
	public void teamAssignment(Player player, ChatColor color, TeamType team) {
		PersistentDataContainer data = player.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		if (data.has(key, new TeamDataType())) {
			data.set(key, new TeamDataType(), team);
			player.sendMessage(ChatColor.GRAY + "You are now on the : " + color + ChatColor.BOLD + data.get(new NamespacedKey(Main.getPlugin(), "teamType"), new TeamDataType()) + ChatColor.RESET + " team!");
		}
	}
	
	public void clearChestPlate(Player player) {
		ItemStack nothing = new ItemStack(Material.AIR);
		player.getInventory().setChestplate(nothing);
	}
	
	public void checkTeam(Player you, String playerInQ) {
		// Initialization and housekeeping stuff
		HashMap<String, Integer> map = new HashMap<>();
		map.put("GERUDO", 0);
		map.put("RITO", 1);
		map.put("KOROK", 2);
		map.put("GORON", 3);
		map.put("ZORA", 4);
		map.put("NEUTRAL", 5);
		
		List<ChatColor> cc = new ArrayList<ChatColor>();
		cc.add(ChatColor.YELLOW);
		cc.add(ChatColor.AQUA);
		cc.add(ChatColor.GREEN);
		cc.add(ChatColor.RED);
		cc.add(ChatColor.BLUE);
		cc.add(ChatColor.WHITE);
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getDisplayName().equals(playerInQ)) {
				String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
				you.sendMessage(ChatColor.WHITE + p.getDisplayName() + " is on Team " + cc.get(map.get(teamOfPlayer)) + ChatColor.BOLD + teamOfPlayer);
			}
		}
	}
}
