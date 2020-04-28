package me.Leblanct.supercreative.world;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class CreativeWorld 
{

	private UUID _owner;
	private String _name;
	private Location _spawn;
	private List<UUID> _playersWithAccess;
	
	public CreativeWorld(UUID id, String name, Location spawn, List<UUID> allowed)
	{
		_owner = id;
		_name = name;
		_spawn = spawn;
		_playersWithAccess = allowed;
	}
	
	public UUID getOwner()
	{
		return _owner;
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
		Location spawn = new Location(Bukkit.getWorld(_name), _spawn.getX(), _spawn.getY(), _spawn.getZ());
		return spawn;
	}
	
	public void setSpawn(Location loc)
	{
		_spawn = loc;
	}
	
	public List<UUID> getAllowed()
	{
		return _playersWithAccess;
	}
	
	public void giveAccess(UUID id)
	{
		_playersWithAccess.add(id);
	}
	
	public void removeAccess(UUID id)
	{
		_playersWithAccess.remove(id);
	}
	
}
