package net.Equinox.core.client;

import java.util.UUID;

import net.Equinox.core.client.infinitum.InfinitumIcon;

public class Client
{

	private UUID _uuid;
	
	private Rank _rank;
	private boolean _isInfinitum;
	private InfinitumIcon _icon;
	
	private long _coins;
	private long _cubits;
	
	public Client(UUID uuid, Rank rank, boolean isInfinitum, InfinitumIcon icon, long coins, long cubits)
	{
		_uuid = uuid;
		_rank = rank;
		_isInfinitum = isInfinitum;
		_icon = icon;
		_coins = coins;
		_cubits = cubits;
	}
	
	public UUID getUUID()
	{
		return _uuid;
	}
	
	public Rank getRank()
	{
		return _rank;
	}
	
	public boolean isInfinitum()
	{
		return _isInfinitum;
	}
	
	public long getCoins()
	{
		return _coins;
	}
	
	public long getCubits()
	{
		return _cubits;
	}
	
	public void setRank(Rank rank)
	{
		_rank = rank;
	}
	
	public InfinitumIcon getIcon()
	{
		return _icon;
	}
	
	public void setInfinitum(boolean state)
	{
		_isInfinitum = state;
	}
	
	public void setIcon(InfinitumIcon icon)
	{
		_icon = icon;
	}
	
	public void setCoins(long amount)
	{
		_coins = amount;
	}
	
	public void setCubits(long amount)
	{
		_cubits = amount;
	}
}
