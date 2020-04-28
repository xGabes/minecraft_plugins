package net.Equinox.typhoon.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.Equinox.core.nms.Titles;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.typhoon.Typhoon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TyphoonCountdown extends BukkitRunnable
{
	
	private Typhoon _typhoon;
	private int _timer;
	private int _end = 600;

	public TyphoonCountdown(Typhoon plugin, int timer)
	{
		_typhoon = plugin;
		_timer = timer;
	}
	
	@Override
	public void run() 
	{
		_timer--;
		
		if(_timer == 0)
		{
			if(Bukkit.getOnlinePlayers().size() < 2)
			{
				_timer = 60;
				for(Player p : Bukkit.getOnlinePlayers())
				{
					Titles.sendFullTitle(p, 0, 60, 0, ChatColor.RED + "Not enough players", "Restarting countdown!");
				}
				UtilsMessages.informAll("Typhoon", "Not enough players online! We need at least 2");
				return;
			}
			
			_typhoon.game.startGame();
			_end = 600;
			_typhoon.game.setTillEnd("10:00");
		}
		if(_timer > 0)
		{
			for(Player p : Bukkit.getOnlinePlayers())
			{
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR	, new ComponentBuilder(ChatColor.RED + "Starting in " + ChatColor.GOLD.toString() + _timer + ChatColor.WHITE + (_timer == 1 ? " second" : " seconds")).create());
				
				if(_timer < 6)
				{
					if(_typhoon.game.getGameState() != TyphoonState.SECONDS)
					{
						_typhoon.game.setGameState(TyphoonState.SECONDS);
					}
					
					Titles.sendFullTitle(p, 0, 40, 0, ChatColor.RED + "Starting in...", ChatColor.GOLD.toString() + _timer + ChatColor.WHITE + (_timer == 1 ? " second" : " seconds"));
				}
			}
		}
		
		if(_timer < 0)
		{
			
			_end--;
			int minutes = _end / 60;
			int seconds = _end % 60;
			String disMinu = (minutes < 10 ? "0" : "") + minutes;
			String disSec = (seconds < 10 ? "0" : "") + seconds;
			String formattedTime = disMinu + ":" + disSec;
			_typhoon.game.setTillEnd(formattedTime);
			
			if(_end <= 0)
			{
				_typhoon.game.gameEnd();
			}
			
			/*
			_seconds--;
			if(_end > 540)
			{
				_typhoon.game.setTillEnd("9:" + _seconds);
			} else if(_end == 540)
			{
				_seconds = 59;
				_typhoon.game.setTillEnd("8:" + _seconds);
			} else if(_end > 480)
			{
				_typhoon.game.setTillEnd("8:" + _seconds);
			} else if(_end == 480)
			{
				_seconds = 59;
				_typhoon.game.setTillEnd("7:" + _seconds);
			} else if(_end > 420)
			{
				_typhoon.game.setTillEnd("7:" + _seconds);
			} else if(_end == 420)
			{
				_seconds = 59;
				_typhoon.game.setTillEnd("6:" + _seconds);
			}*/
		}
	}
	
	public void startCountdown()
	{
		_timer = 60;
		this.runTaskTimer(_typhoon, 20*5L, 20L);
	}
	
	public void resetCountdown()
	{
		_timer = 60;
	}
	
	public void stopCountdown()
	{
		this.cancel();
	}

}
