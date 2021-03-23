package me.Johnny.minecraftZeldaStuff.Events;

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
import org.bukkit.event.player.PlayerQuitEvent;
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

import me.Johnny.minecraftZeldaStuff.Housekeeping;
import me.Johnny.minecraftZeldaStuff.Main;
import me.Johnny.minecraftZeldaStuff.TeamCommands;
import me.Johnny.minecraftZeldaStuff.TeamDataType;
import me.Johnny.minecraftZeldaStuff.Models.TeamType;

public class Events implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
//		Initialization and housekeeping stuff
		Housekeeping hk = new Housekeeping();
		HashMap<String, Integer> map = hk.getTeamNameHashMap();
		List<ChatColor> cc = hk.getChatColorList();
	
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		
		String initPlayerteam = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
		
		//Welcome message
		player.sendMessage("Welcome " + cc.get(map.get(initPlayerteam)) + ChatColor.BOLD + player.getDisplayName() + "!");
		
		// When player joins for the 1st time, the "team" that they default to is neutral
		if (!player.getPersistentDataContainer().has(key, new TeamDataType())) {
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
//			player.sendMessage(ChatColor.BOLD + "Welcome " + player.getDisplayName() + "!");
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
		
		// Create a scoreboard for each player with data from 'board'. Objective should be unique to each player
		for (Player dude: Bukkit.getOnlinePlayers()) {
			String teamOfPlayer = dude.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			createScoreboard(dude, teamOfPlayer);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), task -> {
			
			int numOfPlayers = Bukkit.getOnlinePlayers().size();
			
			if (numOfPlayers == 0) {
				Bukkit.broadcastMessage(ChatColor.WHITE + "And then there was no one");
			} 
			
			if (numOfPlayers == 1) {
				Bukkit.broadcastMessage(ChatColor.WHITE + "And then there was " + numOfPlayers + "...");
			}
			
			else {
				Bukkit.broadcastMessage(ChatColor.WHITE + "And then there were " + numOfPlayers + "...");
			}
			
			for (Player dude: Bukkit.getOnlinePlayers()) {
				String teamOfPlayer = dude.getPersistentDataContainer().get(key, new TeamDataType()).toString();
				createScoreboard(dude, teamOfPlayer);
			}
		}, 20L);
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
		
//		Bukkit.broadcastMessage(ChatColor.GOLD + "Score: " + Bukkit.getOnlinePlayers().size());
		
//		Initialization and housekeeping stuff
		Housekeeping hk = new Housekeeping();
		HashMap<String, Integer> map = hk.getTeamNameHashMap();
		List<ChatColor> cc = hk.getChatColorList();
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		for (Player p: Bukkit.getOnlinePlayers()) {
			
			String teamOfPlayer = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			if (teamOfPlayer.equalsIgnoreCase("GERUDO")) {
				gerudo.addPlayer(p);
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}	
			else if (teamOfPlayer.equalsIgnoreCase("RITO")) {
				rito.addPlayer(p);
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
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
			else if (teamOfPlayer.equalsIgnoreCase("NEUTRAL")) {
				objective.setDisplayName(cc.get(map.get(displayName.toUpperCase())) + "" + ChatColor.BOLD + displayName);
			}
		}		
		player.setScoreboard(f_board);
	}
}
