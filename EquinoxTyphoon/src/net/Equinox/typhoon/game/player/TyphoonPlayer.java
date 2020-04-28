package net.Equinox.typhoon.game.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.Equinox.typhoon.game.kits.TyphoonKits;

public class TyphoonPlayer 
{

	private Player _player;
	private TyphoonKits _kit;
	private int _lives;
	private long _coins;
	private long _cubits;
	
	public TyphoonPlayer(Player player, TyphoonKits kit)
	{
		_player = player;
		_kit = kit;
		_lives = 2;
		_coins = 0;
		_cubits = 0;
	}
	
	public Player getPlayer()
	{
		return _player;
	}
	
	public TyphoonKits getKit()
	{
		return _kit;
	}
	
	public void setKit(TyphoonKits kit)
	{
		_kit = kit;
	}

	public int getLives()
	{
		return _lives;
	}
	
	public void setLives(int lives)
	{
		_lives = lives;
	}
	
	public long getCoins()
	{
		return _coins;
	}
	
	public void setCoins(long coins)
	{
		_coins = coins;
	}
	
	public long getCubits()
	{
		return _cubits;
	}
	
	public void setCubits(long cubits)
	{
		_cubits = cubits;
	}
}
