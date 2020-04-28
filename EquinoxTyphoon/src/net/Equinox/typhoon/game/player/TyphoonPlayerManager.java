package net.Equinox.typhoon.game.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.Equinox.typhoon.game.kits.TyphoonKits;

public class TyphoonPlayerManager 
{

	
	private Map<Player, TyphoonPlayer> _players;
	
	
	public TyphoonPlayerManager()
	{
		_players = new HashMap<Player, TyphoonPlayer>(Bukkit.getMaxPlayers());
	}
	
	public boolean isSelectedKit(Player player, TyphoonKits kit)
	{
		if(getTyphoonPlayer(player).getKit() == kit)
		{
			return true;
		}
		
		return false;
	}
	
	public Map<Player, TyphoonPlayer> getAllPlayer()
	{
		return _players;
	}
	
	public TyphoonPlayer getTyphoonPlayer(Player player)
	{
		if(!_players.containsKey(player))
		{
			return null;
		}
		
		return _players.get(player);
		
	}
	
	public void addTyphoonPlayer(TyphoonPlayer player)
	{
		if(_players.containsKey(player.getPlayer()))
		{
			return;
		}
		
		_players.put(player.getPlayer(), player);
	}
	
	public void removeTyphoonPlayer(TyphoonPlayer player)
	{
		_players.remove(player.getPlayer());
	}
	
}
