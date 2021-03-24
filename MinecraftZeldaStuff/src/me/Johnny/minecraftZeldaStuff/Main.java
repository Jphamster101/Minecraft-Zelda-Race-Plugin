package me.Johnny.minecraftZeldaStuff;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.Johnny.minecraftZeldaStuff.Events.Events;

//Referenced Sources:
// https://www.youtube.com/watch?v=3OLSfOkgPMw (Custom PersistentDataContainer)
// https://www.youtube.com/watch?v=wz8oKcPUzHY (PersistentDataContainer in General)


public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	@Override
	public void onEnable() {
		//Links Events.class to this Main file
		getServer().getPluginManager().registerEvents(new Events(), this);
		plugin = this;
		
		TeamCommands tc = new TeamCommands();
		TabAutoComplete tac = new TabAutoComplete();
		getCommand("test").setExecutor(tc);
		getCommand("assign").setExecutor(tc);
		getCommand("assign").setTabCompleter(tac);
		getCommand("whichteam").setExecutor(tc);
		getCommand("teams").setExecutor(tc);
		getCommand("squad").setExecutor(tc);
		getCommand("sb").setExecutor(tc);
		getCommand("heal").setExecutor(tc);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static Main getPlugin() {
		return plugin;
	}
}
