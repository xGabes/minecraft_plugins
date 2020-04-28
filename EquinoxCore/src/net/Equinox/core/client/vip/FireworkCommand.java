package net.Equinox.core.client.vip;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.utils.UtilsMessages;
import net.md_5.bungee.api.ChatColor;

public class FireworkCommand implements CommandExecutor
{

	private Core _core;
	private Map<UUID, Long> _lastUse;
	private int _time = 25;
	
	public FireworkCommand(Core plugin)
	{
		_core = plugin;
		_lastUse = new HashMap<UUID, Long>();
		plugin.getCommand("firework").setExecutor(this);
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
			
			if(_lastUse.containsKey(player.getUniqueId()))
			{
				long timeLeft = ((_lastUse.get(player.getUniqueId()) / 1000) + _time) - (System.currentTimeMillis() / 1000);
				if(timeLeft <= 0)
				{
					_lastUse.remove(player.getUniqueId());
					
					randomFirework(player.getLocation());
					UtilsMessages.inform("Firework", "Sparkle!", player);
					_lastUse.put(player.getUniqueId(), System.currentTimeMillis());
					
				}
				if(timeLeft > 0)
				{
					UtilsMessages.inform("Firework", "You can't use that for another " + ChatColor.WHITE + timeLeft + " seconds" , player);
					return true;
				}
			} else
			{
				randomFirework(player.getLocation());
				UtilsMessages.inform("Firework", "Sparkle!", player);
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
	
	public void randomFirework(Location loc)
	{
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta mt = fw.getFireworkMeta();
		
		Random random = new Random();
		
		int iType = random.nextInt(4) + 1;
		Type type = Type.BALL;
		
		if(iType == 1) type = Type.BALL;
		if(iType == 2) type = Type.BALL_LARGE;
		if(iType == 3) type = Type.STAR;
		if(iType == 4) type = Type.BURST;
		
		mt.addEffect(FireworkEffect.builder().flicker(true).with(type).withColor(Color.WHITE).trail(true).build());
		mt.setPower(random.nextInt(1) +1);
		fw.setFireworkMeta(mt);
		
	}

}
