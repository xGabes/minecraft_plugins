package net.Equinox.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class UtilsMessages
{

	public static void inform(String prefix, String message, Player player)
	{
		player.sendMessage(ChatColor.RED + "[" + prefix + ChatColor.RED + "] " + ChatColor.GRAY + message);
	}
	
	public static void informAll(String prefix, String message)
	{
		Bukkit.broadcastMessage(ChatColor.RED + "[" + prefix + ChatColor.RED + "] " + ChatColor.GRAY + message);
	}
	
	public static void shout(String message, Player shouter)
	{
		Bukkit.broadcastMessage(ChatColor.YELLOW + "[Shout] " + ChatColor.WHITE + shouter.getName() + ": " + message);
	}
	
	public static void broadcast(String message, Player broadcaster)
	{
		Bukkit.broadcastMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + broadcaster.getName() + ": "
				+ ChatColor.WHITE.toString() + ChatColor.BOLD + message);
	}
	
	public static void announce(String message)
	{
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "ANNOUNCEMENT");
		Bukkit.broadcastMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + message);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
	}
}
