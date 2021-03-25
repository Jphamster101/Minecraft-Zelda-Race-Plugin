package me.Johnny.minecraftZeldaStuff.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;

//import org.bukkit.ChatColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Johnny.minecraftZeldaStuff.Housekeeping;
import me.Johnny.minecraftZeldaStuff.Main;
import me.Johnny.minecraftZeldaStuff.RainCheck;
import me.Johnny.minecraftZeldaStuff.TeamCommands;
import me.Johnny.minecraftZeldaStuff.TeamDataType;
import me.Johnny.minecraftZeldaStuff.Models.TeamType;

public class Events implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		watchingForRain(player);
		
		List<String> tn = new ArrayList<String>();
		tn.add("GERUDO");
		tn.add("RITO");
		tn.add("KOROK");
		tn.add("GORON");
		tn.add("ZORA");
		tn.add("NEUTRAL");
		
		
//		Initialization and housekeeping stuff
		Housekeeping hk = new Housekeeping();
		HashMap<String, Integer> map = hk.getTeamNameHashMap();
		List<ChatColor> cc = hk.getChatColorList();
	
		NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
		
		// When player joins for the 1st time, the "team" that they default to is neutral
		if (!player.getPersistentDataContainer().has(key, new TeamDataType())) {
			Bukkit.broadcastMessage("initPlayerteam:");
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
			player.sendMessage(ChatColor.BOLD + "You are currently not on a team. But you can join one of the teams below:");
			
			//Welcome message
			String initPlayerteam = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			player.sendMessage("Welcome " + cc.get(map.get(initPlayerteam)) + ChatColor.BOLD + player.getDisplayName() + "!");
			new TeamCommands().showTeams(map, cc, player);
		}
		
		else if (!tn.contains(player.getPersistentDataContainer().get(key, new TeamDataType()).toString())) {
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
		}
		
		else if (player.getPersistentDataContainer().get(key, new TeamDataType()).toString().equals(null)) {
			player.getPersistentDataContainer().set(key, new TeamDataType(), TeamType.NEUTRAL);
		}
		else {
			//Welcome message
			String initPlayerteam = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			player.sendMessage("Welcome " + cc.get(map.get(initPlayerteam)) + ChatColor.BOLD + player.getDisplayName() + "!");
		}
		
		if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.GORON)) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
		}	
		
		//Take into account all players and put them into their respective teams using the board variable	
		// At this point board should have all the teams and members in their respective teams
		
		// Create a scoreboard for each player with data from 'board'. Objective should be unique to each player
		for (Player dude: Bukkit.getOnlinePlayers()) {
			String teamOfPlayer = dude.getPersistentDataContainer().get(key, new TeamDataType()).toString();
			createScoreboard(dude, teamOfPlayer);
		}
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
						giveElytra(p);
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
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
	    NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
	    String teamColor = player.getPersistentDataContainer().get(key, new TeamDataType()).toString();
		if (teamColor.equals("ZORA")) {
		    Material m = e.getPlayer().getLocation().getBlock().getType();
		    Block above = player.getEyeLocation().add(0,0.0,0).getBlock();
		    if (m == Material.WATER) {
		    	if (above.getType() == Material.WATER) {
		    		player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, Integer.MAX_VALUE, 0, false, false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 0, false, false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 19, false, false));
		    	}
		    	else {
		    		player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
		    		player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
		    		player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		    	}
		        // player is under water
		    }
		}
		
		/*--- Rito ---*/
		
		else if (teamColor.equals("RITO")) {
	    	if (player.isInWater()) {
	    		makeSlow(player);
		    	clipWings(player);
	    	}   	
		}
	}
	
	/*----------------------- Neutral --------------------------*/
	
	
	
	// -----------------------------------------------------------------Helper Functions--------------------------------------------------------------------
	
	public void clearChestPlate(Player player) {
		ItemStack nothing = new ItemStack(Material.AIR);
		player.getInventory().setChestplate(nothing);
	}
	
	@SuppressWarnings("deprecation")
	public void rainDetection() {
	    for (Player p : Bukkit.getOnlinePlayers()) {
	      if (p.getWorld().hasStorm()) {
	        World w = p.getWorld();
	        Biome b = w.getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
	        int highest = w.getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
	        if (RainCheck.doesBiomePrecipitate(b) && p.getLocation().getBlockY() >= highest) {
	        	Bukkit.broadcastMessage("Rain Rain...");
	        	makeSlow(p);
		    	clipWings(p);
	        }
	        else {
	        	Bukkit.broadcastMessage("Lovely day...");
	        }
	      } 
	    } 
	  }
	
	public void watchingForRain(Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "teamType");
				if (player.getPersistentDataContainer().get(key, new TeamDataType()).equals(TeamType.RITO)) {
					rainDetection();
				}
				else {
					Bukkit.broadcastMessage("Stopped watching for rain due to team change");
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 5);
	}
	
	public void giveElytra(Player p) {
		ItemStack elytra= new ItemStack(Material.ELYTRA);
		ItemMeta meta = elytra.getItemMeta();
		meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
		meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
		elytra.setItemMeta(meta);
		p.getInventory().setChestplate(elytra);
	}
	
	public void makeSlow(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 400, 2, false, false));
	}
	
	public void clipWings(Player p) {
		if (p.hasPotionEffect(PotionEffectType.SLOW)) {
			clearChestPlate(p);
			
			ItemStack elytra= new ItemStack(Material.ELYTRA);
			ItemMeta meta = elytra.getItemMeta();
			meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
			meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
			meta.setUnbreakable(true);
			elytra.setItemMeta(meta);
			
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), task -> {
				p.getInventory().setChestplate(elytra);
			}, 400L);
		}
	}
	
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
		
		
		// TO RE-IMPLEMENT SCOREBOARD FEATURE, UNCOMMENT THE 2 LINES OF CODE BELOW
		// SAME CHANGE NEEDS TO BE MADE IN THE TEAMCOMMANDS.JAVA createScoreboard FUNCTION
//		Score score = objective.getScore("Players:");
//		score.setScore(Bukkit.getOnlinePlayers().size());
		
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
