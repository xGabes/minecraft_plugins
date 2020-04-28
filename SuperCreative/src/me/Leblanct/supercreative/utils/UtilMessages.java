package me.Leblanct.supercreative.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Leblanct.supercreative.client.Rank;
import net.md_5.bungee.api.ChatColor;

public class UtilMessages
{

	
	public static void warn(String prefix, String message, Player player)
	{
		player.sendMessage(ChatColor.RED + ">" + ChatColor.DARK_RED + "> " + ChatColor.RED + prefix + ": " + ChatColor.WHITE + message);
	}
	
	public static void inform(String prefix, String message, Player player)
	{
		player.sendMessage(ChatColor.GREEN + ">" + ChatColor.DARK_GREEN + "> " + ChatColor.GREEN + prefix + ": " + ChatColor.WHITE + message);
	}
	
	public static void announce(String message)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage(ChatColor.RED + ">" + ChatColor.DARK_RED + "> " + ChatColor.RED.toString() + ChatColor.BOLD + "ANNOUNCEMENT");
			p.sendMessage(ChatColor.WHITE + message);
		}
	}
	
	public static void load(String prefix, String message, Player player)
	{
		player.sendMessage(ChatColor.AQUA + ">" + ChatColor.DARK_AQUA + "> " + ChatColor.AQUA + prefix + ": " + ChatColor.WHITE + message);
	}
}
