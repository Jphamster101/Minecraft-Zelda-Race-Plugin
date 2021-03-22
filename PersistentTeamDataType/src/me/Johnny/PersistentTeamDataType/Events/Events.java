package me.Johnny.PersistentTeamDataType.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Johnny.PersistentTeamDataType.Main;
import me.Johnny.PersistentTeamDataType.TeamCommands;
import me.Johnny.PersistentTeamDataType.TeamDataType;
import me.Johnny.PersistentTeamDataType.Models.TeamType;

public class Events implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		//Grab player
		Player player = event.getPlayer();
		
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
		
		// When player joins for the 1st time, the "team" that they default to is neutral
		if (!player.getPersistentDataContainer().has(key, new TeamDataType())) {
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
			player.sendMessage(ChatColor.BOLD + "Welcome " + player.getDisplayName() + "!");
			player.sendMessage(ChatColor.BOLD + "You are currently not on a team. But you can join one of the teams below:");
			new TeamCommands().showTeams(map, cc, player);
		}
		
		/* How this team assignment code works
		 * Get the player's persistence data container for 'teamType'
		 * If it is of a certain type, them change their skin accordingly
		 * Go through all players and generate a scoreboard that
		 * reflects their team type => createScoreboard(Player player, String team)
		 * */
		
		//Take into account all players and put them into their respective teams using the board variable	
		// At this point board should have all the teams and members in their respective teams
		
		int counter = 0;
		// Create a scoreboard for each player with data from 'board'. Objective should be unique to each player
		for (Player dude: Bukkit.getOnlinePlayers()) {
			counter++;
			Bukkit.broadcastMessage(ChatColor.WHITE + "" + counter + " iteration(s)");
			String teamOfPlayer = dude.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			createScoreboard(dude, teamOfPlayer);
		}
		
		// Create a scoreboard for each player based on their team type
//		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
//			String teamName = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
//			Bukkit.broadcastMessage(player.getDisplayName());
//			objective.setDisplayName(teamName);
			
//			createScoreboard(player, teamName, board);
//			player.setScoreboard(board);
//			Bukkit.broadcastMessage(teamColor);
//		}
	}
	
	/*---------------------- Gerudo ---------------------------*/
	
	/*----------------------- Rito --------------------------*/
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), task -> {
		
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			String teamColor = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
				if (teamColor.equals("RITO")) {
						
						try {
							p.getInventory().getChestplate().equals(new ItemStack(Material.ELYTRA));
						} catch (Exception e) {
							ItemStack elytra= new ItemStack(Material.ELYTRA);
							ItemMeta meta = elytra.getItemMeta();
							meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
							meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
							elytra.setItemMeta(meta);
							p.getInventory().setChestplate(elytra);
						}
				}
			}
		}, 20L);

	}
	
	/*---------------------- Korok ---------------------------*/
	// Abilities: Invisibility and not collidable when crouching
	
	@EventHandler
	public void onCrouch(PlayerToggleSneakEvent event) {
		Player player = (Player) event.getPlayer();
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		String teamOfPlayer = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
		if (teamOfPlayer.equals("KOROK")) {
			player.setInvisible(!player.isSneaking());
			player.setCollidable(player.isSneaking());
		}
	}
	
	/*----------------------- Goron --------------------------*/
	
	/*----------------------- Zora ---------------------------*/
	
	/*----------------------- Neutral --------------------------*/
	
	// HELPER FUNCTIONS
	
	@SuppressWarnings("deprecation")
	public void createScoreboard(Player player, String displayName) {
		
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
}
