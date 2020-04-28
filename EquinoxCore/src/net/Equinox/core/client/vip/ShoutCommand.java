package net.Equinox.core.client.vip;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.core.utils.UtilsTime;
import net.md_5.bungee.api.ChatColor;

public class ShoutCommand implements CommandExecutor
{
	
	private Core _core;
	private Map<UUID, Long> _lastUse;
	private int _time = 50;
	
	public ShoutCommand(Core plugin)
	{
		_core = plugin;
		_lastUse = new HashMap<UUID, Long>();
		plugin.getCommand("shout").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(!_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(player, 
					_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.VIP, true))
			{
				return true;
			}
			if(args.length < 1)
			{
				UtilsMessages.inform("Command", "Correct use is " + ChatColor.GOLD + "/shout <message>", player);
				return false;
			}
			
			if(_lastUse.containsKey(player.getUniqueId()))
			{
				long timeLeft = ((_lastUse.get(player.getUniqueId()) / 1000) + _time) - (System.currentTimeMillis() / 1000);
				if(timeLeft <= 0)
				{
					_lastUse.remove(player.getUniqueId());
					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < args.length; i++)
					{
						builder.append(args[i]);
						if (i != args.length - 1)
						{
							builder.append(" ");
						}
					}
					
					UtilsMessages.shout(builder.toString(), player);
					_lastUse.put(player.getUniqueId(), System.currentTimeMillis());
					
				}
				if(timeLeft > 0)
				{
					UtilsMessages.inform("Shout", "You can't use that for another " + ChatColor.WHITE + timeLeft + " seconds" , player);
					return true;
				}
			} else
			{
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < args.length; i++)
				{
					builder.append(args[i]);
					if (i != args.length - 1)
					{
						builder.append(" ");
					}
				}
				
				UtilsMessages.shout(builder.toString(), player);
				_lastUse.put(player.getUniqueId(), System.currentTimeMillis());
			}
			
		} else
		{
			return false;
		}
		return false;
	}
	
	public void clean()
	{
		_lastUse.clear();
	}

}
