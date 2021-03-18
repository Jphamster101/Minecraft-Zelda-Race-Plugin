package me.Johnny.PersistentTeamDataType.Events;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Johnny.PersistentTeamDataType.Main;
import me.Johnny.PersistentTeamDataType.TeamDataType;
import me.Johnny.PersistentTeamDataType.Models.TeamType;

public class Events implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		//Grab player
		Player player = event.getPlayer();
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		
		if (!player.getPersistentDataContainer().has(key, new TeamDataType())) {
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
		}
		
		if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.BLUE)) {
			player.sendMessage(ChatColor.BLUE + "Violets are blue...");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.RED)) {
			player.sendMessage(ChatColor.RED + "Roses are red...");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.NEUTRAL)) {
			player.sendMessage(ChatColor.WHITE + "Stuck in the middle...");
		}
		else {
			player.sendMessage("You are not a part of a team yet. Use the command /teams to see a list of joinable teams");
		}
		
	}
}
