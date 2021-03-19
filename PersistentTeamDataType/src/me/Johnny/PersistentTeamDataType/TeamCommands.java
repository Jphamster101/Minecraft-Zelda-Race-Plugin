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
			
			Events e = new Events();
			
			// Test command for sanity purposes
			if (label.equalsIgnoreCase("test")) {
				player.sendMessage("Could you be the one?");
			}
			
			// Feature that allows for users to hover/click on the team they would like to join
			if (label.equalsIgnoreCase("teams")) {
				player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Select a Team Shown Below:");
				for (String teamName: map.keySet()) {
					TextComponent message = new TextComponent(teamName);
					message.setColor(cc.get(map.get(teamName)));
					message.setBold(true);
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/assign " + teamName));
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click here to join the " + teamName + ChatColor.WHITE + " team")));
					player.spigot().sendMessage(message);
				}
			}
			
			// Team assignment code
			if (label.equalsIgnoreCase("assign")) {
				if (args.length > 0) {
					// JOIN THE BLUE TEAM
					if (args[0].equalsIgnoreCase("gerudo")) {
						teamAssignment(player, ChatColor.YELLOW, TeamType.GERUDO);
						e.createScoreboard(player, "GERUDO");
//						createScoreboard(player, "BLUE");
					}
					// JOIN THE RED TEAM
					else if (args[0].equalsIgnoreCase("rito")) {
						teamAssignment(player, ChatColor.AQUA, TeamType.RITO);
						e.createScoreboard(player, "RITO");
						ScoreboardManager manager = Bukkit.getScoreboardManager();
						Scoreboard board = manager.getNewScoreboard();
						Bukkit.broadcastMessage(ChatColor.GREEN + "[Server]" + ChatColor.GOLD + "YOU SWITCHED TEAMS!");
						ItemStack elytra= new ItemStack(Material.ELYTRA);
						ItemMeta meta = elytra.getItemMeta();
						meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
						meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
						meta.setUnbreakable(true);
						elytra.setItemMeta(meta);
						player.getInventory().setChestplate(elytra);
						player.sendMessage("SUCCESS!");
//						Player player = event.getPlayer();
//						if (board.getPlayerTeam(player).equals("RITO")) {
//							ItemStack elytra= new ItemStack(Material.ELYTRA);
//							ItemMeta meta = elytra.getItemMeta();
//							meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
//							meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
//							meta.setUnbreakable(true);
//							player.getInventory().setChestplate(elytra);
//							player.sendMessage("SUCCESS!");
//						}
//						else {
//							player.sendMessage("NOPE");
//						}
//						createScoreboard(player, "RED");
					}
					// JOIN THE NEUTRAL TEAM
					else if (args[0].equalsIgnoreCase("korok")) {
						teamAssignment(player, ChatColor.GREEN, TeamType.KOROK);
						e.createScoreboard(player, "KOROK");
//						createScoreboard(player, "NEUTRAL");
					}
					// JOIN THE NEUTRAL TEAM
					else if (args[0].equalsIgnoreCase("goron")) {
						teamAssignment(player, ChatColor.RED, TeamType.GORON);
						e.createScoreboard(player, "GORON");
//						createScoreboard(player, "NEUTRAL");
					}
					// JOIN THE NEUTRAL TEAM
					else if (args[0].equalsIgnoreCase("zora")) {
						teamAssignment(player, ChatColor.BLUE, TeamType.ZORA);
						e.createScoreboard(player, "ZORA");
//						createScoreboard(player, "NEUTRAL");
					}
					// JOIN THE NEUTRAL TEAM
					else if (args[0].equalsIgnoreCase("neutral")) {
						teamAssignment(player, ChatColor.WHITE, TeamType.NEUTRAL);
						e.createScoreboard(player, "NEUTRAL");
//						createScoreboard(player, "NEUTRAL");
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
			// Command that allows user to check what team they are on
			if (label.equalsIgnoreCase("whichteam")) {
				PersistentDataContainer data = player.getPersistentDataContainer();
				NamespacedKey keyValue = new NamespacedKey(Main.getPlugin(), "teamType");
				String team = data.get(keyValue, new TeamDataType()).toString();
				player.sendMessage(ChatColor.GRAY + "You are on the : " + cc.get(map.get(team)) + ChatColor.BOLD + data.get(keyValue, new TeamDataType()) + ChatColor.WHITE +" team!");
			}
		}
		return false;
	}
	// Helper Function
	public void teamAssignment(Player player, ChatColor color, TeamType team) {
		PersistentDataContainer data = player.getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		if (data.has(key, new TeamDataType())) {
			data.set(key, new TeamDataType(), team);
			player.sendMessage(ChatColor.GRAY + "You are now on the : " + color + ChatColor.BOLD + data.get(new NamespacedKey(Main.getPlugin(), "teamType"), new TeamDataType()) + ChatColor.RESET + " team!");
		}
		else {
			player.sendMessage(ChatColor.GREEN + "Uh oh");
		}
	}
	
//	public void updateTeamNameScoreboard(Player player, String displayName) {
//		ScoreboardManager manager = Bukkit.getScoreboardManager();
//		Scoreboard board = manager.getNewScoreboard();
//		Objective objective = board.registerNewObjective("NAME", "dummy");
//		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//		
//		Team red = board.registerNewTeam("RED");
//		Team blue = board.registerNewTeam("BLUE");
//		Team neutral= board.registerNewTeam("NEUTRAL");
//		
//		red.setPrefix(ChatColor.RED + "[RED] " + ChatColor.WHITE);
//		blue.setPrefix(ChatColor.BLUE+ "[BLUE] " + ChatColor.WHITE);
//		neutral.setPrefix(ChatColor.WHITE+ "[NEUTRAL] " + ChatColor.WHITE);
//		
//		Score score = objective.getScore("Players:");
//		score.setScore(Bukkit.getOnlinePlayers().size());
//		player.setScoreboard(board);
//		
//		if (displayName.equalsIgnoreCase("blue")) {
//			objective.setDisplayName(ChatColor.BLUE + "AZUL");
//			blue.addPlayer(player);
//		}
//		else if (displayName.equalsIgnoreCase("red")) {
//			objective.setDisplayName(ChatColor.RED + displayName.toUpperCase());
//			red.addPlayer(player);
//		}
//		else if (displayName.equalsIgnoreCase("neutral")) {
//			objective.setDisplayName(ChatColor.WHITE + displayName.toUpperCase());
//			neutral.addPlayer(player);
//		}
//	}
}
