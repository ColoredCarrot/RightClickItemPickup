package com.coloredcarrot.rightclickitempickup.nms;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class NMSCodeLibrary
{

	public static void playEntitySound(Player player, Location location, String soundName, float volume, float pitch)
	{
		player.playSound(location, Sound.valueOf(computeEntitySoundEnumName(soundName)), volume, pitch);
	}

	private static String computeEntitySoundEnumName(String soundName)
	{
		
		try { Sound.valueOf(soundName); return soundName; }
		catch (IllegalArgumentException e) { return "ENTITY_" + soundName; }
		
	}
	
}
