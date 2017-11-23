package com.coloredcarrot.rightclickitempickup.tab_completion;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class RCIPTabCompleter
implements TabCompleter
{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		if (args.length == 1)
			if (args[0].startsWith("d"))
				return Arrays.asList("disable");
			else if (args[0].startsWith("e"))
				return Arrays.asList("enable");
			else if (args[0].startsWith("t"))
				return Arrays.asList("toggle");
			else
				return Arrays.asList("enable", "disable", "toggle");
		
		return null;
		
	}

}
