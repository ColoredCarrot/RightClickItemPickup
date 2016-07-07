package com.coloredcarrot.rightclickitempickup.listeners;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import com.coloredcarrot.rightclickitempickup.mc_version_manager.MCVersionManager;
import com.coloredcarrot.rightclickitempickup.nms.NMSCodeLibrary;

public class PlayerInteractListener
implements Listener
{
	
	public static final List<Material> AIR_BLOCKS = Arrays.asList(Material.AIR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.TRAP_DOOR, Material.WOOD_DOOR, Material.WOODEN_DOOR, Material.RAILS, Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, Material.POWERED_RAIL, Material.ANVIL, Material.BANNER, Material.BED, Material.BED_BLOCK, Material.CROPS, Material.FENCE, Material.FENCE_GATE, Material.ACACIA_FENCE, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE, Material.DARK_OAK_FENCE_GATE, Material.IRON_FENCE, Material.JUNGLE_FENCE, Material.JUNGLE_FENCE_GATE, Material.NETHER_FENCE, Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE, Material.BREWING_STAND, Material.BRICK_STAIRS, Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS, Material.WATER, Material.WATER_LILY, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA, Material.CAKE_BLOCK, Material.CARPET, Material.CHEST, Material.ENDER_CHEST, Material.TRAPPED_CHEST);

	public PlayerInteractListener()
	{
		if (MCVersionManager.getCriticalVersion() == MCVersionManager.MCCriticalVersion.MC_1_9_L)
			AIR_BLOCKS.addAll(Arrays.asList(Material.BEETROOT_SEEDS, Material.BEETROOT, Material.BEETROOT_BLOCK, Material.CHORUS_FLOWER, Material.CHORUS_PLANT));
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getPlayer() != null && !Wrapper.hasDirectPickupEnabled(event.getPlayer()) && event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR && event.getBlockFace() != null && event.getBlockFace().equals(BlockFace.UP))
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
