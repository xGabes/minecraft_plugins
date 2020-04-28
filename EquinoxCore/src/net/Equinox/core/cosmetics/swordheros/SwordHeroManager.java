package net.Equinox.core.cosmetics.swordheros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.Equinox.core.cosmetics.deathcries.DeathCryType;

public class SwordHeroManager 
{

	private List<SwordHeroType> _swordHeroTypes;
	private Map<UUID, List<SwordHeroType>> _players;
	private Map<UUID, SwordHeroType> _enabled;
	
	public SwordHeroManager()
	{
		_swordHeroTypes = new ArrayList<SwordHeroType>();
		_players = new HashMap<UUID, List<SwordHeroType>>();
		_enabled = new HashMap<UUID, SwordHeroType>();
		
	}
	
	public boolean hasEnabled(UUID id)
	{
		if(_enabled.containsKey(id))
		{
			return true;
		}
		
		return false;
	}
	
	public SwordHeroType getEnabled(UUID id)
	{
		return _enabled.get(id);
	}
	
	public void setEnabled(UUID id, SwordHeroType type)
	{
		if(_enabled.containsKey(id))
		{
			_enabled.remove(id);
			_enabled.put(id, type);
		}
		
		_enabled.put(id, type);
	}
	
	public void addSwordHero(UUID id, SwordHeroType type)
	{
		if(_players.containsKey(id))
		{
			_players.get(id).add(type);
			
		} else
		{
			_players.put(id, _swordHeroTypes);
			_players.get(id).add(type);
		}
	}
	
	public void addSwordHero(UUID id, List<SwordHeroType> type)
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
	
	public boolean hasSwordHero(UUID id, SwordHeroType type)
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
