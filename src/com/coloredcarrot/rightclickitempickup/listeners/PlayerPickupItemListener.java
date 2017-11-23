package com.coloredcarrot.rightclickitempickup.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.coloredcarrot.rightclickitempickup.data.Wrapper;

public class PlayerPickupItemListener
implements Listener
{

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		
		if (event.getItem() != null && !event.getItem().getType().equals(Material.AIR))
			event.setCancelled(!Wrapper.hasDirectPickupEnabled(event.getPlayer()));
		
	}
	
}
