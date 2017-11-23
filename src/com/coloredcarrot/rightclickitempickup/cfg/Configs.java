package com.coloredcarrot.rightclickitempickup.cfg;

import com.coloredcarrot.rightclickitempickup.RCIP;
import com.coloredcarrot.rightclickitempickup.enumerations.PickupMode;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Configs
{

	CONFIG("config.yml",
			Arrays.asList("default-pickup-mode"),
			Arrays.asList(PickupMode.RIGHT_CLICK)),
	
	LANG("lang.yml",
			Arrays.asList("only-player", "set-right-click", "set-default", "valid-actions", "inv-full"),
			Arrays.asList("&cOnly a player can perform that action!", "&aSet item pick up mode to right-clicking", "&aSet item pick up mode to default", "&cValid actions: &oenable, disable, toggle", "&cYour inventory is full!"));
	
	private final String name;
	private File file;
	private FileConfiguration yaml;
	private Map<String, Object> defaults;
	
	private Configs(String name)
	{
		this.name = name;
	}
	
	private <T> Configs(String name, List<String> defaultPaths, List<T> defaultValues)
	{
		
		this.name = name;
		
		defaults = new HashMap<String, Object>();
		
		for (int i = 0; i < defaultPaths.size(); i++)
			defaults.put(defaultPaths.get(i), defaultValues.get(i));
		
	}
	
	private void load()
	{
		
		if (RCIP.getPlugin().getResource(name) == null)
			throw new IllegalArgumentException("The resource " + name + " doesn't exist in the plugin jar file");
		
		file = new File(RCIP.getFolder(), name);
		
		if (!file.exists())
			RCIP.getPlugin().saveResource(name, false);
		
		yaml = YamlConfiguration.loadConfiguration(file);
		
	}
	
	public PickupMode getPickupMode(String path)
	{
		
		try { return PickupMode.valueOf(yaml.getString(path).toUpperCase()); }
		catch (IllegalArgumentException | NullPointerException e) { return (PickupMode) defaults.get(path); }
		
	}
	
	public String getString(String path, boolean format)
	{
		
		if (!yaml.isString(path))
			return (String) defaults.get(path);
		
		return format ? ChatColor.translateAlternateColorCodes('&', yaml.getString(path)) : yaml.getString(path);
		
	}
	
	public static void loadAll()
	{
		
		RCIP.getFolder().mkdirs();
		
		for (Configs cfg : values())
			cfg.load();
		
	}
	
}
