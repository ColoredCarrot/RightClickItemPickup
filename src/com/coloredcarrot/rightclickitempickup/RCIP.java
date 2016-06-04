package com.coloredcarrot.rightclickitempickup;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class RCIP
{
	
	protected static RCIPPlugin plugin;
	
	public static RCIPPlugin getPlugin()
	{
		return plugin;
	}
	
	public static void info(String... msg)
	{
		for (String e : msg)
			plugin.getLogger().info(e);
	}
	
	public static void warning(String... msg)
	{
		for (String e : msg)
			plugin.getLogger().warning(e);
	}

	public static void log(Level level, String... msg)
	{
		for (String e : msg)
			plugin.getLogger().log(level, e);
	}
	
	public static String getVersion()
	{
		return plugin.getDescription().getVersion();
	}
	
	public static File getFolder()
	{
		return plugin.getDataFolder();
	}
	
	public static void shutdown(String msg)
	{
		log(Level.SEVERE, msg);
		plugin.getServer().getPluginManager().disablePlugin(plugin);
	}
	
	protected static void registerListeners(Listener... listeners)
	{
		for (Listener e : listeners)
			plugin.getServer().getPluginManager().registerEvents(e, plugin);
	}
	
	protected static void registerCommandExecutor(String name, CommandExecutor executor)
	{
		plugin.getCommand(name).setExecutor(executor);
	}
	
}
