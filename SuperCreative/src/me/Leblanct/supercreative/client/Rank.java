package me.Leblanct.supercreative.client;

import org.bukkit.ChatColor;

public enum Rank 
{
	MEMBER("Member", ChatColor.GRAY, ChatColor.DARK_GRAY, 0),
	VIP("VIP", ChatColor.GREEN, ChatColor.DARK_GREEN, 10),
	PRO("PRO", ChatColor.BLUE, ChatColor.DARK_BLUE, 20),
	MOD("Mod", ChatColor.YELLOW, ChatColor.GOLD, 30),
	ADMIN("Admin", ChatColor.YELLOW, ChatColor.GOLD, 50),
	OWNER("Owner", ChatColor.YELLOW, ChatColor.GOLD, 100);
	
	private String _name;
	private ChatColor _color;
	private ChatColor _second;
	private int _priority;
	
	Rank(String name, ChatColor color, ChatColor second, int priority)
	{
		_name = name;
		_color = color;
		_second = second;
		_priority = priority;
	}
	
	public String getRankName()
	{
		return _name;
	}
	
	public String getFullTag()
	{
		return _color + ">" + _second + "> " + _color + ChatColor.BOLD + name().toUpperCase() + ChatColor.RESET + _color;
	}
	
	public String getSimpleTag()
	{
		return _color + "" + ChatColor.BOLD + name().toUpperCase();
	}
	
	public int getPriority()
	{
		return _priority;
	}
	
	public ChatColor getColor()
	{
		return _color;
	}
}
