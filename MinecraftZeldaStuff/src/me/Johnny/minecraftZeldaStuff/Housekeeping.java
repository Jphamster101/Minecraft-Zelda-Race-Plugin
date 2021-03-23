package me.Johnny.minecraftZeldaStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

public class Housekeeping {
	public HashMap<String, Integer> getTeamNameHashMap() {
//		 Initialization and housekeeping stuff
		HashMap<String, Integer> map = new HashMap<>();
		map.put("GERUDO", 0);
		map.put("RITO", 1);
		map.put("KOROK", 2);
		map.put("GORON", 3);
		map.put("ZORA", 4);
		map.put("NEUTRAL", 5);
		return map;
	}
	
	public List<ChatColor> getChatColorList() {
		List<ChatColor> chatColorList = new ArrayList<ChatColor>();
		chatColorList.add(ChatColor.YELLOW);
		chatColorList.add(ChatColor.AQUA);
		chatColorList.add(ChatColor.GREEN);
		chatColorList.add(ChatColor.RED);
		chatColorList.add(ChatColor.BLUE);
		chatColorList.add(ChatColor.WHITE);
		return chatColorList;
	}
}
