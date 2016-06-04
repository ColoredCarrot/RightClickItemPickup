package com.coloredcarrot.rightclickitempickup.executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.coloredcarrot.rightclickitempickup.data.Wrapper;

public class RCIPExecutor
implements CommandExecutor
{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		if (args.length != 1/* && args.length != 2*/)
			return false;
		
		if (args.length == 1)
		{
			
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "Only a player can perform that action!");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (args[0].equalsIgnoreCase("enable"))
			{
				
				if (!player.hasPermission("rcip.enable"))
				{
					player.sendMessage(cmd.getPermissionMessage());
					return true;
				}
				
				Wrapper.setDirectPickupEnabled(player, false);
				
				player.sendMessage(ChatColor.GREEN + "Set item pick up mode to right-clicking");
				
			}
			else if (args[0].equalsIgnoreCase("disable"))
			{
				
				if (!player.hasPermission("rcip.disable"))
				{
					player.sendMessage(cmd.getPermissionMessage());
					return true;
				}
				
				Wrapper.setDirectPickupEnabled(player, true);
				
				player.sendMessage(ChatColor.GREEN + "Set item pick up mode to default");
				
			}
			else if (args[0].equalsIgnoreCase("toggle"))
			{
				
				if (Wrapper.hasDirectPickupEnabled(player))
					player.performCommand("rcip enable");
				else
					player.performCommand("rcip disable");
				
			}
			else
				sender.sendMessage(ChatColor.RED + "Valid actions: enable, disable, toggle");
			
		}
		
		return true;
		
	}

}
