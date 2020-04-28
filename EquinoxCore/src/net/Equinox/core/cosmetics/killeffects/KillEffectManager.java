package net.Equinox.core.cosmetics.killeffects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.Equinox.core.Core;
import net.Equinox.core.cosmetics.deathcries.DeathCryType;

public class KillEffectManager 
{
	
	private List<KillEffectType> _killEffectTypes;
	private Map<UUID, List<KillEffectType>> _players;
	private Map<UUID, KillEffectType> _enabled;
	
	public KillEffectManager()
	{
		_killEffectTypes = new ArrayList<KillEffectType>();
		_players = new HashMap<UUID, List<KillEffectType>>();
		_enabled = new HashMap<UUID, KillEffectType>();
		
	}
	
	public boolean hasEnabled(UUID id)
	{
		if(_enabled.containsKey(id))
		{
			return true;
		}
		
		return false;
	}
	
	public KillEffectType getEnabled(UUID id)
	{
		return _enabled.get(id);
	}
	
	public void setEnabled(UUID id, KillEffectType type)
	{
		if(_enabled.containsKey(id))
		{
			_enabled.remove(id);
			_enabled.put(id, type);
		}
		
		_enabled.put(id, type);
	}
	
	public void addKillEffect(UUID id, KillEffectType type)
	{
		if(_players.containsKey(id))
		{
			_players.get(id).add(type);
			
		} else
		{
			_players.put(id, _killEffectTypes);
			_players.get(id).add(type);
		}
	}
	
	public void addKillEffects(UUID id, List<KillEffectType> type)
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
	
	public boolean hasKillEffect(UUID id, KillEffectType type)
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
