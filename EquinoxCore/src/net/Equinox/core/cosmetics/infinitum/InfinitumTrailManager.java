package net.Equinox.core.cosmetics.infinitum;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InfinitumTrailManager 
{

	private Map<UUID, InfinitumTrailType> _enabled;
	
	public InfinitumTrailManager()
	{
		_enabled = new HashMap<UUID, InfinitumTrailType>();
	}
	
	public boolean isEnabled(UUID id, InfinitumTrailType type)
	{
		if(!_enabled.containsKey(id))
		{
			return false;
		}
		if(_enabled.get(id) == type)
		{
			return true;
		}
		
		return false;
	}
	
	public void setEnabled(UUID id, InfinitumTrailType type)
	{
		_enabled.put(id, type);
	}
	
	public void disable(UUID id)
	{
		_enabled.remove(id);
	}
}

