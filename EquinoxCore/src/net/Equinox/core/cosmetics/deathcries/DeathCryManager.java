package net.Equinox.core.cosmetics.deathcries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeathCryManager 
{
	private List<DeathCryType> _deathCryTypes;
	private Map<UUID, List<DeathCryType>> _players;
	private Map<UUID, DeathCryType> _enabled;
	
	public DeathCryManager()
	{
		_deathCryTypes = new ArrayList<DeathCryType>();
		_players = new HashMap<UUID, List<DeathCryType>>();
		_enabled = new HashMap<UUID, DeathCryType>();
	}
	
	public boolean hasEnabled(UUID id)
	{
		if(_enabled.containsKey(id))
		{
			return true;
		}
		
		return false;
	}
	
	public DeathCryType getEnabled(UUID id)
	{
		return _enabled.get(id);
	}
	
	public void setEnabled(UUID id, DeathCryType type)
	{
		if(_enabled.containsKey(id))
		{
			_enabled.remove(id);
			_enabled.put(id, type);
		}
		
		_enabled.put(id, type);
	}
	
	public void addDeathCry(UUID id, DeathCryType type)
	{
		if(_players.containsKey(id))
		{
			_players.get(id).add(type);
			
		} else
		{
			_players.put(id, _deathCryTypes);
			_players.get(id).add(type);
		}
	}
	
	public void addDeathCrys(UUID id, List<DeathCryType> type)
	{
		if(_players.containsKey(id))
		{
			for(int i = 0; i < type.size(); i++)
			{
				_players.get(id).add(type.get(i));
			}
		} else
		{
			_players.put(id, type);
		}
		
		
	}
	
	public boolean hasDeathCry(UUID id, DeathCryType type)
	{
		if(!_players.containsKey(id))
		{
			return false;
		}
		
		if(_players.get(id).contains(type))
		{
			return true;
		}
		return false;
	}
	
	public int getAmountOwned(UUID id)
	{
		if(!_players.containsKey(id))
		{
			return 0;
		}
		if(_players.get(id).isEmpty())
		{
			return 0;
		}
		
		return _players.get(id).size() + 1;
	}
}
