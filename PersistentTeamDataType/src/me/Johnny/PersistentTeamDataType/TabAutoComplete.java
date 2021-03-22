package me.Johnny.PersistentTeamDataType;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.md_5.bungee.api.ChatColor;

public class TabAutoComplete implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (args.length == 1) {
			List<String> teamNames = new ArrayList<>();
			teamNames.add(ChatColor.YELLOW + "GERUDO");
			teamNames.add(ChatColor.AQUA + "RITO");
			teamNames.add(ChatColor.GREEN+ "KOROK");
			teamNames.add(ChatColor.RED + "GORON");
			teamNames.add(ChatColor.BLUE + "ZORA");
			teamNames.add(ChatColor.WHITE + "NEUTRAL");
			
			return teamNames;
		}
		
		
		
		
		
		return null;
	}
}