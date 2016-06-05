package com.coloredcarrot.rightclickitempickup;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.coloredcarrot.rightclickitempickup.cfg.Configs;
import com.coloredcarrot.rightclickitempickup.data.InvalidConfigurationException;
import com.coloredcarrot.rightclickitempickup.data.Wrapper;
import com.coloredcarrot.rightclickitempickup.executors.RCIPExecutor;
import com.coloredcarrot.rightclickitempickup.listeners.PlayerInteractListener;
import com.coloredcarrot.rightclickitempickup.listeners.PlayerPickupItemListener;
import com.coloredcarrot.rightclickitempickup.nms.NMS;
import com.coloredcarrot.rightclickitempickup.nms.NMSSetupResponse;

public class RCIPPlugin
extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		
		RCIP.plugin = this;
		
		// Connect NMS
		NMSSetupResponse nmsSetupResponse = NMS.setup();
		if (nmsSetupResponse.isCompatible())
			getLogger().info("[VersionManager] Hooked server version " + nmsSetupResponse.getVersion());
		else
		{
			getLogger().warning("[VersionManager] Your server version (" + (nmsSetupResponse.getVersion() == null ? "UNKNOWN" : nmsSetupResponse.getVersion()) + ") is not compatible with RightClickItemPickup!");
			getLogger().warning("[VersionManager] It is highly recommended that you upgrade the server and the RCIP plugin itself, because otherwise, some functions of this plugin may not work as intended or not at all.");
		}
		
		// Load direct-pickup data
		try { Wrapper.loadData(); }
		catch (IOException e)
		{
			e.printStackTrace();
			RCIP.shutdown("An exception occured while trying to load data.yml, the plugin will be disabled.");
			return;
		}
		catch (InvalidConfigurationException e)
		{
			RCIP.log(Level.SEVERE, "Config load error: " + e.getMessage());
			RCIP.shutdown("An exception occured while trying to load data.yml, the plugin will be disabled.");
			return;
		}
		
		// Register listeners
		RCIP.registerListeners(new PlayerPickupItemListener(), new PlayerInteractListener());
		
		// Register command executors
		RCIP.registerCommandExecutor("rcip", new RCIPExecutor());
		
		// Load configs
		Configs.loadAll();
		
		RCIP.info("Enabled RightClickItemPickup v" + RCIP.getVersion());
		
	}
	
	@Override
	public void onDisable()
	{
		
		// Save data.yml
		try { Wrapper.saveData(); }
		catch (IOException e) { e.printStackTrace(); }
		
		RCIP.info("Disabled RightClickItemPickup v" + RCIP.getVersion());
		
		RCIP.plugin = null;
		
	}
	
}
