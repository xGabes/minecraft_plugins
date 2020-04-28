package net.Equinox.core.client.infinitum;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.utils.UtilsMessages;

public class IconCommand implements CommandExecutor
{

	private Core _core;
	
	public IconCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("icon").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(!_core.clientManager.getClient(player.getUniqueId()).isInfinitum())
			{
				UtilsMessages.inform("Permissions", "You must be an " + ChatColor.DARK_PURPLE + "Infinitum" + ChatColor.GRAY + " to use this!", player);
				return false;
			}
			
			_core.infinitumMenu.openIcons(player);
		}
		return false;
	}

	
}
