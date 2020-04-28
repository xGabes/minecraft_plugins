package net.Equinox.typhoon.game.kits;

import org.bukkit.ChatColor;

public enum TyphoonKits
{

	SPECTATOR("Spectator", ChatColor.GRAY, 0),
	ZOMBIE("Zombie", ChatColor.YELLOW, 0),
	SKELETON("Skeleton", ChatColor.YELLOW, 0),
	CREEPER("Creeper", ChatColor.YELLOW, 0);
	
	private String _kitName;
	private ChatColor _color;
	private int _price;
	
	TyphoonKits(String kitName, ChatColor color, int price)
	{
		_kitName = kitName;
		_color = color;
		_price = price;
	}
	
	public String getKitName()
	{
		return _kitName;
	}
	
	public int getKitPrice()
	{
		return _price;
	}
	
	public ChatColor getColor()
	{
		return _color;
	}
	
}
