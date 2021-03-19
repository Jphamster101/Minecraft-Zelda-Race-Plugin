package me.Johnny.PersistentTeamDataType.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
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
		
		if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.GERUDO)) {
			player.sendMessage(ChatColor.YELLOW + "Violets are blue...");
			//CHANGE CHARACTER SKIN
			
//			SkinGrabber.changeSkin(player, "YELLOW");
			
//			createScoreboard(player, "BLUE");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.RITO)) {
			player.sendMessage(ChatColor.AQUA + "RITO RACE");
			//CHANGE CHARACTER SKIN
//			SkinGrabber.changeSkin(player, "RED");
			
//			ItemStack elytra= new ItemStack(Material.ELYTRA);
//			ItemMeta meta = elytra.getItemMeta();
//			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
//			meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
//			meta.setUnbreakable(true);
//			player.getInventory().setChestplate(elytra);
			
			
			
//			player.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
//			createScoreboard(player, "RED");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.KOROK)) {
			player.sendMessage(ChatColor.GREEN + "KOROK RACE");
			//CHANGE CHARACTER SKIN
//			SkinGrabber.changeSkin(player, "RED");
//			createScoreboard(player, "RED");
		}
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.GORON)) {
			player.sendMessage(ChatColor.RED + "GORON RACE");
			//CHANGE CHARACTER SKIN
//			SkinGrabber.changeSkin(player, "RED");
//			createScoreboard(player, "RED");
		}
		
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.ZORA)) {
			player.sendMessage(ChatColor.BLUE + "ZORA RACE");
			//CHANGE CHARACTER SKIN
			SkinGrabber.changeSkin(player, "WHITE");
//			createScoreboard(player, "NEUTRAL");
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
	
	
	/*Gerudo -------------------------------------------------*/
	
	/*Rito -------------------------------------------------*/
	
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
						meta.setUnbreakable(true);
						elytra.setItemMeta(meta);
						p.getInventory().setChestplate(elytra);
					}
			}
		}
	}, 20L);

	}
	
	/*Korok -------------------------------------------------*/
	
	@EventHandler
	public void onCrouch(PlayerToggleSneakEvent event) {
		Player player = (Player) event.getPlayer();
		
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		String teamOfPlayer = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
		if (teamOfPlayer.equals("KOROK")) {
			player.setInvisible(!player.isSneaking());
			player.setCollidable(player.isSneaking());
		}
		
//		if (player.isSneaking()) {
//			player.setInvisible(isEnabled());
////			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20, 50, false, false));
//		}
//		if (player.isSprinting()) {
//			player.setWalkSpeed(1);
//			player.sendMessage(ChatColor.BLUE + "I AM SPEED");
//		}
//		else {
//			player.setWalkSpeed((float) 0.2);
//		}
	}
	
	/*Goron -------------------------------------------------*/
	
	/*Zora -------------------------------------------------*/
	
	/*-------------------------------------------------*/
	
	public void createScoreboard(Player player, String displayName) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("NAME", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Team gerudo = board.registerNewTeam("GERUDO");
		Team rito = board.registerNewTeam("RITO");
		Team korok = board.registerNewTeam("KOROK");
		Team goron = board.registerNewTeam("GORON");
		Team zora = board.registerNewTeam("ZORA");
		Team neutral = board.registerNewTeam("NEUTRAL");
		
		
		gerudo.setPrefix(ChatColor.YELLOW + "[GERUDO]" + ChatColor.WHITE);
		rito.setPrefix(ChatColor.AQUA+ "[RITO]" + ChatColor.WHITE);
		korok.setPrefix(ChatColor.GREEN+ "[KOROK]" + ChatColor.WHITE);
		goron.setPrefix(ChatColor.RED+ "[GORON]" + ChatColor.WHITE);
		zora.setPrefix(ChatColor.BLUE+ "[ZORA]" + ChatColor.WHITE);
		neutral.setPrefix(ChatColor.WHITE+ "[NEUTRAL]" + ChatColor.WHITE);
		
		Score score = objective.getScore("Players:");
		score.setScore(Bukkit.getOnlinePlayers().size());
		
		player.setScoreboard(board);
		
		if (displayName.equalsIgnoreCase("GERUDO")) {
			objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + displayName);
			gerudo.addPlayer(player);
//			SkinGrabber.changeSkin(player, "BLUE");
		}	
		else if (displayName.equalsIgnoreCase("RITO")) {
			objective.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + displayName);
			rito.addPlayer(player);
//			SkinGrabber.changeSkin(player, "RED");
		}
		else if (displayName.equalsIgnoreCase("KOROK")) {
			objective.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + displayName);
			korok.addPlayer(player);
//			SkinGrabber.changeSkin(player, "RED");
		}
		else if (displayName.equalsIgnoreCase("GORON")) {
			objective.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + displayName);
			goron.addPlayer(player);
//			SkinGrabber.changeSkin(player, "RED");
		}
		else if (displayName.equalsIgnoreCase("ZORA")) {
			objective.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + displayName);
			zora.addPlayer(player);
//			SkinGrabber.changeSkin(player, "NEUTRAL");
		}
		else if (displayName.equalsIgnoreCase("NEUTRAL")) {
			objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + displayName);
			neutral.addPlayer(player);
//			SkinGrabber.changeSkin(player, "NEUTRAL");
		}
		
		
	}
	
	public void onPlayerSprint(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
	}
}
