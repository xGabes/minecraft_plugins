package net.Equinox.core.chat;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.client.infinitum.InfinitumIcon;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.core.utils.UtilsTime;
import net.Equinox.core.utils.update.UpdateEvent;
import net.Equinox.core.utils.update.UpdateType;

public class ChatManager implements Listener
{

	private Core _core;
	private Object _chatLock = new Object();
	private boolean _isSilenced;
	private long _silenceStart;
	private long _timeSilenced;
	private boolean _isSlow;
	private long _chatSlowTime;
	private Map<UUID, Long> _lastMessage;
	
	public ChatManager(Core plugin)
	{
		_core = plugin;
		_lastMessage = new HashMap<UUID, Long>();
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void setSilenced(boolean silenced, long timeSilenced)
	{
		synchronized (_chatLock)
		{
			_isSilenced = silenced;
			_timeSilenced = timeSilenced;
			_silenceStart = System.currentTimeMillis();
		}
	}

	public boolean isSilenced()
	{
		synchronized (_chatLock)
		{
			return _isSilenced;
		}
	}

	public void setChatSlowed(boolean chatSlow, long chatSlowTime)
	{
		synchronized (_chatLock)
		{
			_isSlow = chatSlow;
			_chatSlowTime = chatSlowTime;
		}
	}

	public boolean isChatSlowed()
	{
		synchronized (_chatLock)
		{
			return _isSlow;
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		
		if(_core.punishedManager.getPunishedClient(player.getUniqueId()).isMuted())
		{
			UtilsMessages.inform("Punishment", "You have been muted by " 
		+ ChatColor.RED +  _core.punishedManager.getPunishedClient(player.getUniqueId()).getPunisher()
		+ ChatColor.GRAY +  " for " + ChatColor.YELLOW + _core.punishedManager.getPunishedClient(player.getUniqueId()).getMutedReason(), player);
			UtilsMessages.inform("Punishment", "You can appeal your punishment at" + ChatColor.WHITE + " equinoxnetwork.net", player);
			event.setCancelled(true);
			return;
		}
		
		synchronized(_chatLock)
		{
			
			DecimalFormat time = new DecimalFormat("#0.0");
			if(_isSilenced && !_core.clientManager.getClient(player.getUniqueId()).getRank()
					.hasRank(_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.ADMIN))
			{
				String duration = _timeSilenced == -1 ? "permanently" 
						: "for " + time.format(((_timeSilenced - (System.currentTimeMillis() - _silenceStart)) / 1000.0D) / 60.0D) + " minutes";
				UtilsMessages.inform("Chat", "The chat is currently silenced "
						+ ChatColor.WHITE + duration, player);
				
				event.setCancelled(true);
				return;
			}
			
			if(_isSlow && !_core.clientManager.getClient(player.getUniqueId()).getRank()
					.hasRank(_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.ADMIN))
			{
				if(!_lastMessage.containsKey(player.getUniqueId()))
				{
					_lastMessage.put(player.getUniqueId(), System.currentTimeMillis());
					
				} else
				{
					if(UtilsTime.elapsed(_lastMessage.get(player.getUniqueId()), _chatSlowTime))
					{
						_lastMessage.put(player.getUniqueId(), System.currentTimeMillis());
					} else
					{
						String duration = time.format((_chatSlowTime - (System.currentTimeMillis() - _lastMessage.get(player.getUniqueId()))) / 1000.D) + " seconds";
						UtilsMessages.inform("Chat", "The chat is slowed. You can't chat for another "
						        + ChatColor.WHITE + duration, player);
						event.setCancelled(true);
						return;
					}
				}
			}
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).isInfinitum() &&
				_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.ADMIN))
		{
			event.setFormat(ChatColor.WHITE + "[" + _core.clientManager.getClient(player.getUniqueId()).getIcon().getChar() + "] " +
					_core.clientManager.getClient(player.getUniqueId()).getRank().getTag(true, true).toUpperCase()
					+ ChatColor.RESET + _core.clientManager.getClient(player.getUniqueId()).getRank().getColor() + " %s" + ChatColor.GRAY + ": %s");
			// [x] RANK Name: Message
			return;
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).isInfinitum())
		{
			event.setFormat(ChatColor.WHITE + "[" + _core.clientManager.getClient(player.getUniqueId()).getIcon().getChar() + "] " +
					_core.clientManager.getClient(player.getUniqueId()).getRank().getTag(true, true).toUpperCase()
					+ ChatColor.GRAY + " %s" + ChatColor.GRAY + ": %s");
			// [x] RANK Name: Message
			return;
		}
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.ADMIN))
		{
			event.setFormat(_core.clientManager.getClient(player.getUniqueId()).getRank().getTag(true, true).toUpperCase()
					+ ChatColor.RESET + _core.clientManager.getClient(player.getUniqueId()).getRank().getColor() + " %s" + ChatColor.GRAY + ": %s");
			return;
		}
		
		if(_core.clientManager.getClient(player.getUniqueId()).getRank().hasRank(_core.clientManager.getClient(player.getUniqueId()).getRank(), Rank.MEMBER))
		{
			event.setFormat(_core.clientManager.getClient(player.getUniqueId()).getRank().getTag(true, true).toUpperCase()
					+ ChatColor.GRAY + " %s: %s");
			// RANK Name: Message
			return;
		}
	}
	
	@EventHandler
	public void onUpdate(UpdateEvent event)
	{
		if (event.getType() == UpdateType.TICK)
		{
			synchronized (_chatLock)
			{
				if (_isSilenced)
				{
					if (_timeSilenced == -1)
					{
						return;
					}
					if (UtilsTime.elapsed(_silenceStart, _timeSilenced))
					{
						_isSilenced = false;
						_silenceStart = 0;
						_timeSilenced = 0;
					}
				}
			}
		}
	}
	
}
