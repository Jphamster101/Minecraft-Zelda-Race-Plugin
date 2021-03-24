package me.Johnny.minecraftZeldaStuff;

import org.bukkit.block.Biome;

public class RainCheck {
	final static String[] noPrecipitate = new String[] { "BADLANDS", "SAVANNA", "DESERT" };
	
	public static boolean doesBiomePrecipitate(Biome b) {
	    for (int i = 0; i < noPrecipitate.length; i++) {
	      if (b.name().contains(noPrecipitate[i]))
	        return false; 
	    } 
	    return true;
	  }
}
