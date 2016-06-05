package com.coloredcarrot.rightclickitempickup.nms;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class NMSHook_v1_8_R3
implements NMSHook
{

	@Override
	public void sendJSON(String json, Player player)
	{
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(json)));
	}

	@Override
	public void sendActionBar(String json, Player player)
	{
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(json), (byte) 2));
	}

	@Override
	public void sendTitle(String titleJSON, String subtitleJSON, int fadeIn, int stay, int fadeOut, Player player)
	{
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(titleJSON), fadeIn, stay, fadeOut));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(subtitleJSON), fadeIn, stay, fadeOut));
	}

	@Override
	public void sendTitle(String titleJSON, int fadeIn, int stay, int fadeOut, Player player)
	{
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(titleJSON), fadeIn, stay, fadeOut));
	}
	
}
