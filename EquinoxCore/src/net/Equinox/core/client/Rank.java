package net.Equinox.core.client;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.Equinox.core.utils.UtilsMessages;

public enum Rank
{

	MEMBER("Member", ChatColor.GRAY, (short) 0),
	VIP("Vip", ChatColor.AQUA, (short) 10),
	PRO("Pro", ChatColor.BLUE, (short) 20),
	ADMIN("Admin", ChatColor.RED, (short) 50),
	OWNER("Owner", ChatColor.RED, (short) 100);
	
	private String _name;
	private ChatColor _color;
	private short _priority;
	
	private Rank(String name, ChatColor color, short priority)
	{
		_name = name;
		_color = color;
		_priority = priority;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public ChatColor getColor()
	{
		return _color;
	}
	
	public short getPriority()
	{
		return _priority;
	}
	
	public String getTag(boolean bold, boolean uppercase)
	{
		String name = _name;
		if(uppercase)
		{
			name.toUpperCase();
		}
		if(bold)
		{
			return _color.toString() + ChatColor.BOLD + name;
		}
		
		return _color.toString() + name;
	}
	
	public boolean isRank(Rank rank)
	{
		if(rank.ordinal() == ordinal())
		{
			return true;
		}
		return false;
	}
	
	public boolean hasRank(Rank rank, Rank needed)
	{
		return hasRank(null, rank, needed, false);
	}
	
	public boolean hasRank(Player player, Rank rank, Rank needed, boolean inform)
	{
		boolean perm = false;
		
		if(player != null)
		{
			perm = player.isOp();
			if(!perm)
			{
				if(rank._priority >= needed._priority)
				{
					return true;
				}
				if(inform)
				{
					UtilsMessages.inform("Permission", ChatColor.GRAY + "You need rank " + needed.getTag(false, false) + ChatColor.GRAY + " to use that!" , player);
				}
			}
		} else
		{
			if(rank._priority >= needed._priority)
			{
				return true;
			}
			if(inform)
			{
				UtilsMessages.inform("Permission", ChatColor.GRAY + "You need rank " + needed.getTag(false, false) + ChatColor.GRAY + " to use that!" , player);
			}
		}
		
		return perm;
	}
}
