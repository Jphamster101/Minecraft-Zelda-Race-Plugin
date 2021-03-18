package me.Johnny.PersistentTeamDataType.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Johnny.PersistentTeamDataType.Main;
import me.Johnny.PersistentTeamDataType.SkinGrabber;
import me.Johnny.PersistentTeamDataType.TeamDataType;
import me.Johnny.PersistentTeamDataType.Models.TeamType;

public class Events implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		//Grab player
		Player player = event.getPlayer();
		
		// Create score board for player
		
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		
		// When player joins, the "team" that they default to is neutral
		if (!player.getPersistentDataContainer().has(key, new TeamDataType())) {
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
		}
		
		/* How this team assignment code works
		 * Get the player's persistence data container for 'teamType'
		 * If it is of a certain type, them change their skin accordingly
		 * Go through all players and generate a scoreboard that
		 * reflects their team type => createScoreboard(Player player, String team)
		 * */
		
		if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.BLUE)) {
			player.sendMessage(ChatColor.BLUE + "Violets are blue...");
			//CHANGE CHARACTER SKIN
			SkinGrabber.changeSkin(player, "BLUE");
			
//			createScoreboard(player, "BLUE");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.RED)) {
			player.sendMessage(ChatColor.RED + "Roses are red...");
			//CHANGE CHARACTER SKIN
			SkinGrabber.changeSkin(player, "RED");
//			createScoreboard(player, "RED");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.NEUTRAL)) {
			player.sendMessage(ChatColor.WHITE + "Stuck in the middle...");
			//CHANGE CHARACTER SKIN
			SkinGrabber.changeSkin(player, "WHITE");
//			createScoreboard(player, "NEUTRAL");
		}
		else {
			player.sendMessage("Don't know why you're here. You should have defaulted to a NEUTRAL TEAM");
		}
		
		// Create a scoreboard for each player based on their team type
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			String teamColor = p.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			createScoreboard(p, teamColor);
		}
		
	}
	public void createScoreboard(Player player, String displayName) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("NAME", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Team red = board.registerNewTeam("RED");
		Team blue = board.registerNewTeam("BLUE");
		Team neutral= board.registerNewTeam("NEUTRAL");
		
		red.setPrefix(ChatColor.RED + "[RED]" + ChatColor.WHITE);
		blue.setPrefix(ChatColor.BLUE+ "[BLUE]" + ChatColor.WHITE);
		neutral.setPrefix(ChatColor.WHITE+ "[NEUTRAL]" + ChatColor.WHITE);
		
		Score score = objective.getScore("Players:");
		score.setScore(Bukkit.getOnlinePlayers().size());
		
		player.setScoreboard(board);
		
		if (displayName.equalsIgnoreCase("blue")) {
			objective.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + displayName);
			blue.addPlayer(player);
			SkinGrabber.changeSkin(player, "BLUE");
		}	
		else if (displayName.equalsIgnoreCase("red")) {
			objective.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + displayName);
			red.addPlayer(player);
			SkinGrabber.changeSkin(player, "RED");
		}
		else if (displayName.equalsIgnoreCase("neutral")) {
			objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + displayName);
			neutral.addPlayer(player);
			SkinGrabber.changeSkin(player, "NEUTRAL");
		}
		
		
	}
}
