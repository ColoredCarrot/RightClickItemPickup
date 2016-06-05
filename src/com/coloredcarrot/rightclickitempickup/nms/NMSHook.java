package com.coloredcarrot.rightclickitempickup.nms;

import org.bukkit.entity.Player;

public interface NMSHook
{

	public void sendJSON(String json, Player player);
	
	public void sendActionBar(String json, Player player);
	
	public void sendTitle(String titleJSON, String subtitleJSON, int fadeIn, int stay, int fadeOut, Player player);
	
	public void sendTitle(String titleJSON, int fadeIn, int stay, int fadeOut, Player player);
	
}
