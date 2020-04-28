package net.Equinox.core.chat;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.Equinox.core.Core;
import net.md_5.bungee.api.ChatColor;

public class AutoMessages
{

	private Core _core;
	private Random _random = new Random();
	
	private String[] _messages;
	private String _prefix;
	private String _lastMessage;
	private String _message;
	
	private long _delay;              
	private long _period;
	
	private boolean _started = false;
	
	private BukkitRunnable _rn = new BukkitRunnable()
	{

		@Override
		public void run()
		{
			_message = _messages[_random.nextInt(_messages.length)];
			
			if(_lastMessage == _message)
			{
				_message = _messages[_random.nextInt(_messages.length)];
			}
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(ChatColor.YELLOW + _message);
			Bukkit.broadcastMessage("");
			
			_lastMessage = _message;
		}
	};
	
	public AutoMessages(Core plugin, String prefix, long delay, long period, String... messages)
	{
		_core = plugin;
		_delay = delay;
		_period = period;
		_prefix = prefix;
		_messages = messages;
	}
	
	public void start()
	{
		if(_started) return;
		_started = true;
		_rn.runTaskTimer(_core, _delay, _period);
	}
	
	public void stop()
	{
		if(!_started) return;
		_started = false;
		_rn.cancel();
	}
}
