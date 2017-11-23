package com.coloredcarrot.rightclickitempickup.listeners;

import com.coloredcarrot.rightclickitempickup.cfg.Configs;
import com.coloredcarrot.rightclickitempickup.data.Wrapper;
import com.coloredcarrot.rightclickitempickup.mc_version_manager.MCVersionManager;
import com.coloredcarrot.rightclickitempickup.nms.NMSCodeLibrary;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.bukkit.Material.*;

public class PlayerInteractListener
implements Listener
{
	
	public static final List<Material> AIR_BLOCKS = new ArrayList<>(Arrays
			.asList(AIR, ACACIA_DOOR, BIRCH_DOOR, DARK_OAK_DOOR, IRON_DOOR, JUNGLE_DOOR, SPRUCE_DOOR, TRAP_DOOR, WOOD_DOOR, WOODEN_DOOR, RAILS, ACTIVATOR_RAIL, DETECTOR_RAIL, POWERED_RAIL, ANVIL, BANNER, BED, BED_BLOCK, CROPS, FENCE, FENCE_GATE, ACACIA_FENCE, ACACIA_FENCE_GATE, BIRCH_FENCE, BIRCH_FENCE_GATE, DARK_OAK_FENCE, DARK_OAK_FENCE_GATE, IRON_FENCE, JUNGLE_FENCE, JUNGLE_FENCE_GATE, NETHER_FENCE, SPRUCE_FENCE, SPRUCE_FENCE_GATE, BREWING_STAND, BRICK_STAIRS, ACACIA_STAIRS, BIRCH_WOOD_STAIRS, COBBLESTONE_STAIRS, DARK_OAK_STAIRS, JUNGLE_WOOD_STAIRS, NETHER_BRICK_STAIRS, QUARTZ_STAIRS, RED_SANDSTONE_STAIRS, SANDSTONE_STAIRS, SMOOTH_STAIRS, SPRUCE_WOOD_STAIRS, WOOD_STAIRS, WATER, WATER_LILY, STATIONARY_WATER, LAVA, STATIONARY_LAVA, CAKE_BLOCK, CARPET, CHEST, ENDER_CHEST, TRAPPED_CHEST));

	public PlayerInteractListener()
	{
		if (MCVersionManager.getCriticalVersion() == MCVersionManager.MCCriticalVersion.MC_1_9_L)
			AIR_BLOCKS.addAll(Arrays.asList(BEETROOT_SEEDS, BEETROOT, BEETROOT_BLOCK, CHORUS_FLOWER, CHORUS_PLANT));
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getPlayer() != null && !Wrapper.hasDirectPickupEnabled(event.getPlayer()) && event.getClickedBlock() != null && event.getClickedBlock().getType() != AIR && event.getBlockFace() != null && event.getBlockFace().equals(BlockFace.UP))
		{
			
			// Pickup item on clicked block
			
			Block iblock = event.getClickedBlock().getLocation().add(0, 1, 0).getBlock();
			
			if (iblock == null || !AIR_BLOCKS.contains(iblock.getType()))
				return;
			
			// radius
			double distance = iblock.getLocation().distance(event.getPlayer().getLocation());
			if (distance > 1.5)
				return;
			
			Collection<Entity> entities = event.getPlayer().getWorld().getNearbyEntities(iblock.getLocation(), 0.9d, 0.9d, 0.9d);
			
			boolean foundItem = false;
			
			if (!entities.isEmpty())
				for (Entity e : entities)
					if (e instanceof Item)
					{
						
						foundItem = true;
						
						if (event.getPlayer().getInventory().addItem(((Item) e).getItemStack()).size() > 0)
						{
							// Inventory full
							event.getPlayer().sendMessage(Configs.LANG.getString("inv-full", true));
							break;
						}
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
