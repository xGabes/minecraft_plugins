package net.Equinox.core.client.owner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class UploadCommand implements CommandExecutor
{

	private Core _core;
	
	public UploadCommand(Core plugin)
	{
		_core = plugin;
		plugin.getCommand("upload").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(!_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(player, 
					_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.OWNER, true)) 
			{
				UtilsMessages.inform("Commands", "No such command exists.", player);
				return false;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) 
			{
				UtilsMessages.inform("Upload", "Couldn't find player: " + args[0], player);
				return false;
			}
			
			if(!target.isOnline())
			{
				UtilsMessages.inform("Upload", "Player: " + ChatColor.WHITE + target.getName() + ChatColor.GRAY + " is not online!", player);
				return false;
			}
			
			try 
			{
				_core.playerData.upload(target.getUniqueId());
				UtilsMessages.inform("Upload", "Uploaded user " + target.getName() + " to database!", player);
			} catch  (Exception e) 
			{
				e.printStackTrace();
			}
			
		} else
		{
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) 
			{
				System.out.println(ChatColor.RED + "Cannot find user: " + args[0]);
				return false;
			}
			
			if(!target.isOnline())
			{
				System.out.println(ChatColor.RED + "Is not online: " + args[0]);
				return false;
			}
			
			try 
			{
				_core.playerData.upload(target.getUniqueId());
				System.out.println(ChatColor.GREEN + "Uploaded to database: " + target.getName());
			} catch  (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return false;
	}

}
