package me.Leblanct.supercreative.world;

import java.util.HashMap;
import java.util.Map;

public class CreativeWorldManager 
{

	private Map<String, CreativeWorld> _worlds;
	
	public CreativeWorldManager()
	{
		_worlds = new HashMap<String, CreativeWorld>();
	}
	
	public Map<String, CreativeWorld> getWorlds()
	{
		return _worlds;
	}
	
	public CreativeWorld getWorld(String id)
	{
		return getWorlds().get(id);
	}
	
	public void addWorld(CreativeWorld world)
	{
		getWorlds().put(world.getOwner().toString(), world);
	}
	
	public void removeWorld(CreativeWorld world)
	{
		getWorlds().remove(world.getOwner().toString());
	}
}
