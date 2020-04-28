package net.Equinox.typhoon.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;
import net.Equinox.core.nms.Titles;
import net.Equinox.core.utils.UtilsMessages;
import net.Equinox.typhoon.Typhoon;
import net.Equinox.typhoon.game.kits.TyphoonKits;
import net.Equinox.typhoon.game.player.TyphoonPlayer;
import net.md_5.bungee.api.ChatColor;

public class TyphoonGame 
{

	private boolean _started;
	private Typhoon _typhoon;
	private Core _core;
	private TyphoonState _gameState;
	private String _tillEnd;
	private List<TyphoonPlayer> _alive;
	private List<TyphoonPlayer> _spectator;
	
	public TyphoonGame(Typhoon plugin)
	{
		_core = Core.getPlugin(Core.class);
		_typhoon = plugin;
		_started = false;
		_gameState = TyphoonState.LOBBY;
		_alive = new ArrayList<TyphoonPlayer>();
		_spectator = new ArrayList<TyphoonPlayer>();
		_tillEnd = "10:00";
	}
	
	public TyphoonState getGameState()
	{
		return _gameState;
	}
	
	public boolean hasStarted()
	{
		return _started;
	}
	
	public String tillEnd()
	{
		return _tillEnd;
	}
	
	public void setTillEnd(String tillEnd)
	{
		_tillEnd = tillEnd;
	}
	
	public void setGameState(TyphoonState state)
	{
		_gameState = state;
	}
	
	public List<TyphoonPlayer> getAlive()
	{
		return _alive;
	}
	
	public void addAlivePlayer(TyphoonPlayer player)
	{
		_alive.add(player);
	}
	
	public void removeAlivePlayer(TyphoonPlayer player)
	{
		_alive.remove(player);
	}
	
	public List<TyphoonPlayer> getSpectators()
	{
		return _spectator;
	}
	
	public boolean isSpectator(TyphoonPlayer player)
	{
		if(_spectator.contains(player))
		{
			return true;
		}
		
		return false;
	}
	
	public void addSpectator(TyphoonPlayer player)
	{
		_spectator.add(player);
	}
	
	public void removeSpector(TyphoonPlayer player)
	{
		_spectator.remove(player);
	}
	
	public void startGame()
	{
		
		_gameState = TyphoonState.GAME;
		_started = true;
		_core.serverManager.setCanCosmetics(false);
		_core.serverManager.setCanFly(false);
		_core.serverManager.setCanDoubleJump(false);
		
		
		for(TyphoonPlayer p : _typhoon.playerManager.getAllPlayer().values())
		{
			addAlivePlayer(p);
			p.getPlayer().teleport(_typhoon.mapManager.getSelected().getSpawn());
			p.getPlayer().getInventory().clear();
			
			if(p.getKit().equals(TyphoonKits.ZOMBIE))
			{
				_typhoon.zombie.giveZombieItems(p.getPlayer());
			} else if(p.getKit().equals(TyphoonKits.SKELETON))
			{
				_typhoon.skeleton.giveSkeletonItems(p.getPlayer());
			} else if(p.getKit().equals(TyphoonKits.CREEPER))
			{
				_typhoon.creeper.giveCreeperItems(p.getPlayer());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void gameEnd()
	{
		_typhoon.mapManager.pickMap();
		_typhoon.countdown.resetCountdown();
		_gameState = TyphoonState.LOBBY;
		_started = false;
		_core.serverManager.setCanCosmetics(true);
		_core.serverManager.setCanFly(true);
		_core.serverManager.setCanDoubleJump(true);
		
		for(TyphoonPlayer p : _typhoon.playerManager.getAllPlayer().values())
		{
			_typhoon.listeners.addLobbyItems(p.getPlayer());
			p.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
			
			if(_spectator.contains(p))
			{
				_spectator.remove(p);
			}
			
			_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCoins(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCoins() + p.getCoins());
			
			if(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getRank().isRank(Rank.VIP))
			{
				_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + ((p.getCubits()) * 2));
				p.setCubits((p.getCubits()) * 2);
				
			} else if(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getRank().hasRank(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getRank(),Rank.PRO))
			{
				_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + ((p.getCubits()) * 3));
				p.setCubits((p.getCubits()) * 3);
			} else {
				_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + p.getCubits());
				p.setCubits(p.getCubits());
			}
			
			Titles.sendFullTitle(p.getPlayer(), 0, 80, 0, ChatColor.RED + "Game ended!", "Nobody has won this round!");
			
			UtilsMessages.inform("Typhoon", "", p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.GREEN + _alive.get(0).getPlayer().getName() + " won!", p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.YELLOW.toString() + ChatColor.BOLD + "Coins earned: " + ChatColor.WHITE + p.getCoins(), p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.AQUA.toString() + ChatColor.BOLD + "Cubits earned: " + ChatColor.WHITE + p.getCubits(), p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.RED + "Hope you're enjoying this minigame!", p.getPlayer());
			
			
			p.setCoins(0);
			p.setCubits(0);
			p.setLives(2);
		}
		
		_alive.clear();
	}
	
	@SuppressWarnings("deprecation")
	public void gameFinish()
	{
		_typhoon.mapManager.pickMap();
		_typhoon.countdown.resetCountdown();
		_gameState = TyphoonState.LOBBY;
		_started = false;
		_core.serverManager.setCanCosmetics(true);
		_core.serverManager.setCanFly(true);
		_core.serverManager.setCanDoubleJump(true);
		
		for(TyphoonPlayer p : _typhoon.playerManager.getAllPlayer().values())
		{
			
			_typhoon.listeners.addLobbyItems(p.getPlayer());
			p.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
			Titles.sendFullTitle(p.getPlayer(), 0, 60, 0, ChatColor.YELLOW + _alive.get(0).getPlayer().getName(), ChatColor.WHITE + "has won this round!");
			p.setKit(TyphoonKits.ZOMBIE);
			
			if(_spectator.contains(p))
			{
				_spectator.remove(p);
			}
			
			if(_alive.contains(p))
			{
				
				_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCoins(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCoins() + p.getCoins() + 15);
				
				if(_core.clientManager.getClient(_alive.get(0).getPlayer().getUniqueId()).getRank().isRank(Rank.VIP))
				{
					_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + ((p.getCubits() + 5) * 2));
					p.setCubits((p.getCubits() + 5) * 2);
					
				} else if(_core.clientManager.getClient(_alive.get(0).getPlayer().getUniqueId()).getRank().hasRank(_core.clientManager.getClient(_alive.get(0).getPlayer().getUniqueId()).getRank(),Rank.PRO))
				{
					_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + ((p.getCubits() + 5) * 3));
					p.setCubits((p.getCubits() + 5) * 3);
				} else {
					_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + p.getCubits() + 5);
					p.setCubits(p.getCubits() + 5);
				}
				
				UtilsMessages.inform("Typhoon", "You won! You got an extra " 
						+ ChatColor.YELLOW + "15 coins" + " (" + (p.getCoins()) + " total)"  + ChatColor.GRAY + " and " 
						+ ChatColor.AQUA + "5 cubits" + " (" + (p.getCubits()) + " total)" + ChatColor.GRAY + " for winning!", _alive.get(0).getPlayer());
			}
			else 
			{
				_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCoins(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCoins() + p.getCoins());
				_core.clientManager.getClient(p.getPlayer().getUniqueId()).setCubits(_core.clientManager.getClient(p.getPlayer().getUniqueId()).getCubits() + p.getCubits());
			}
			
			UtilsMessages.inform("Typhoon", "", p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.GREEN + _alive.get(0).getPlayer().getName() + " won!", p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.YELLOW.toString() + ChatColor.BOLD + "Coins earned: " + ChatColor.WHITE + p.getCoins(), p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.AQUA.toString() + ChatColor.BOLD + "Cubits earned: " + ChatColor.WHITE + p.getCubits(), p.getPlayer());
			UtilsMessages.inform("Typhoon", ChatColor.RED + "Hope you're enjoying this minigame!", p.getPlayer());
			
			p.setCoins(0);
			p.setCubits(0);
			p.setLives(2);
		}
		
		_alive.clear();
	}
	
}
