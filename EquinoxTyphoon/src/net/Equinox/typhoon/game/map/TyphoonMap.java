package net.Equinox.typhoon.game.map;

import org.bukkit.Location;

public class TyphoonMap 
{
	
	private String _name;
	private Location _spawn;
	
	public TyphoonMap(String name, Location spawn)
	{
		_name = name;
		_spawn = spawn;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public Location getSpawn()
	{
		return _spawn;
	}
	
	public void setSpawn(Location spawn)
	{
		_spawn = spawn;
	}
}
