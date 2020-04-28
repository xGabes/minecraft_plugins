package net.Equinox.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.Equinox.core.Core;

public class ServerManagement
{

	private Core _core;
	private boolean _canDoubleJump;
	private boolean _canFly;
	private boolean _canCosmetics;
	private List<UUID> _canClientDoubleJump;
	private List<UUID> _canClientFly;
	
	public ServerManagement(Core plugin)
	{
		_core = plugin;
		_canDoubleJump = true;
		_canFly = true;
		_canCosmetics = true;
		_canClientDoubleJump = new ArrayList<UUID>();
		_canClientFly = new ArrayList<UUID>();
	}
	
	public boolean canDoubleJump()
	{
		return _canDoubleJump;
	}
	
	public void setCanDoubleJump(boolean state)
	{
		_canDoubleJump = state;
	}
	
	public boolean canFly()
	{
		return _canFly;
	}
	
	public void setCanFly(boolean state)
	{
		_canFly = state;
	}
	
	public boolean canCosmetics()
	{
		return _canCosmetics;
	}
	
	public void setCanCosmetics(boolean state)
	{
		_canCosmetics = state;
	}
	
	public boolean canClientDoubleJump(UUID id)
	{
		if(_canClientDoubleJump.contains(id))
		{
			return false;
		}
		
		return true;
	}
	
	public void setClientDoubleJump(UUID id, boolean state)
	{
		if(state)
		{
			_canClientDoubleJump.remove(id);
			return;
		}
		
		_canClientDoubleJump.add(id);
	}
	
	public boolean canClientFly(UUID id)
	{
		if(_canClientFly.contains(id))
		{
			return false;
		}
		
		return true;
	}
	
	public void setClientFly(UUID id, boolean state)
	{
		if(state)
		{
			_canClientFly.remove(id);
			return;
		}
		
		_canClientFly.add(id);
	}
}
