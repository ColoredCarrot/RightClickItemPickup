package com.coloredcarrot.rightclickitempickup.mc_version_manager;

import org.bukkit.Material;

import java.util.Arrays;

public class MCVersionManager
{

	private static MCCriticalVersion version;
	
	public static void detectVersion()
	{
		
		try
		{
			Arrays.asList(Material.BEETROOT_SEEDS);
			version = MCCriticalVersion.MC_1_9_L;
		}
		catch (NoSuchFieldError e) { version = MCCriticalVersion.MC_1_8_E; }
		
	}
	
	public static MCCriticalVersion getCriticalVersion()
	{
		return version;
	}
	
	public static enum MCCriticalVersion
	{
		
		MC_1_8_E,
		MC_1_9_L
		
	}
	
}
