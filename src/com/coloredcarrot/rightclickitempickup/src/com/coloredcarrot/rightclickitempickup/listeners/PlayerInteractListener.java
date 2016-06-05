package com.coloredcarrot.rightclickitempickup.listeners;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.coloredcarrot.rightclickitempickup.cfg.Configs;
import com.coloredcarrot.rightclickitempickup.data.Wrapper;
import com.coloredcarrot.rightclickitempickup.nms.NMSCodeLibrary;

public class PlayerInteractListener
implements Listener
{

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getPlayer() != null && !Wrapper.hasDirectPickupEnabled(event.getPlayer()) && event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR && event.getBlockFace() != null && event.getBlockFace().equals(BlockFace.UP))
		{
			
			// Pickup item on clicked block
			
			Block iblock = event.getClickedBlock().getLocation().add(0, 1, 0).getBlock();
			
			if (iblock == null || iblock.getType() != Material.AIR)
				return;
			
			Collection<Entity> entities = event.getPlayer().getWorld().getNearbyEntities(iblock.getLocation(), 0.9d, 0.9d, 0.9d);
			
			boolean foundItem = false;
			
			if (!entities.isEmpty())
				for (Entity e : entities)
					if (e instanceof Item)
					{
						
						foundItem = true;
						
						if (event.getPlayer().getInventory().addItem(((Item) e).getItemStack()).size() > 0)
							// Inventory full
							event.getPlayer().sendMessage(Configs.LANG.getString("inv-full", true));
						else
						{
							e.teleport(e.getLocation().subtract(0, event.getPlayer().getWorld().getMaxHeight() + 50, 0), TeleportCause.PLUGIN);
							//TODO Need to make this version compatible:
							//event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
							NMSCodeLibrary.playEntitySound(event.getPlayer(), event.getPlayer().getLocation(), "ITEM_PICKUP", 1.0f, 1.0f);
						}
						
					}
		
			event.setCancelled(foundItem);
			
		}
		
	}
	
}
