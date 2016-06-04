package com.coloredcarrot.rightclickitempickup.data;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.coloredcarrot.rightclickitempickup.RCIP;

public final class Wrapper
{
	
	private static File dataFile;
	
	private static Set<UUID> data;
	
	public static void load()
			throws IOException, InvalidConfigurationException
	{
		loadData();
	}
	
	public static void save()
			throws IOException
	{
		saveData();
	}
	
	public static void loadData()
			throws IOException, InvalidConfigurationException
	{
		
		if (dataFile == null)
			dataFile = new File(RCIP.getFolder(), "data.yml");
		
		if (!dataFile.exists())
		{
			RCIP.getFolder().mkdirs();
			dataFile.createNewFile();
		}
		
		data = new HashSet<UUID>();
		
		FileConfiguration yaml = YamlConfiguration.loadConfiguration(dataFile);
		
		if (yaml.isConfigurationSection("direct-pickup") && !yaml.getConfigurationSection("direct-pickup").getKeys(false).isEmpty())
			for (String key : yaml.getConfigurationSection("direct-pickup").getKeys(false))
				if (!yaml.isBoolean("direct-pickup." + key))
					throw new InvalidConfigurationException(dataFile, "A boolean value was expected at direct-pickup." + key);
				else if (yaml.getBoolean("direct-pickup." + key))
					try { data.add(UUID.fromString(key)); }
					catch (IllegalArgumentException e) { throw new InvalidConfigurationException(dataFile, "\"" + key + "\" is not a valid UUID"); }
		
	}
	
	public static void saveData()
			throws IOException
	{
		
		if (dataFile == null)
			dataFile = new File(RCIP.getFolder(), "data.yml");
		
		if (dataFile.exists())
			dataFile.delete();
		
		RCIP.getFolder().mkdirs();
		dataFile.createNewFile();
		
		if (data == null)
			return;
		
		FileConfiguration yaml = YamlConfiguration.loadConfiguration(dataFile);
		
		if (!data.isEmpty())
			for (UUID uuid : data)
				if (uuid != null)
					yaml.set("direct-pickup." + uuid.toString(), true);
		
		yaml.save(dataFile);
		
	}
	
	public static boolean hasDirectPickupEnabled(OfflinePlayer player)
	{
		return hasDirectPickupEnabled(player.getUniqueId());
	}
	
	public static boolean hasDirectPickupEnabled(UUID player)
	{
		return data != null && data.contains(player);
	}
	
	public static void setDirectPickupEnabled(OfflinePlayer player, boolean enabled)
	{
		setDirectPickupEnabled(player.getUniqueId(), enabled);
	}
	
	public static void setDirectPickupEnabled(UUID uuid, boolean enabled)
	{
		
		if (enabled)
			data.add(uuid);
		else
			data.remove(uuid);
		
	}

	private Wrapper()
	{
		throw new UnsupportedOperationException();
	}
	
}
