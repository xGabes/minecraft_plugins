package net.Equinox.core.client.punishment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import net.Equinox.core.Core;

public class PunishedClientManager 
{

	private Core _core;
	private Map<UUID, PunishedClient> _punished;
	
	public PunishedClientManager(Core plugin)
	{
		_core = plugin;
		_punished = new HashMap<UUID, PunishedClient>();
	}
	
	public Map<UUID, PunishedClient> getPunished()
	{
		return _punished;
	}
	
	public PunishedClient getPunishedClient(UUID id)
	{
		if(!_punished.containsKey(id))
		{
			//TODO: Load from database
		}
		
		PunishedClient punished = _punished.get(id);
		return punished;
	}
	
	public PunishedClient getPunishedClient(OfflinePlayer player)
	{
		PunishedClient punished = getPunishedClient(player.getUniqueId());
		return punished;
	}
	
	public void addPunishedClient(PunishedClient client)
	{
		_punished.put(client.getUUID(), client);
	}
	
	public void removePunishedClient(PunishedClient client)
	{
		_punished.remove(client.getUUID());
	}
	
	public void setPunisher(UUID punished, String punisher)
	{
		getPunishedClient(punished).setPunisher(punisher);
	}
	
	public void setPunishmentType(UUID id, PunishmentType type)
	{
		getPunishedClient(id).setPunishmentType(type);
	}
	
	public void setMuted(UUID id, boolean state)
	{
		getPunishedClient(id).setMuted(state);
	}
	
	public void setMutedReason(UUID id, String reason)
	{
		getPunishedClient(id).setMutedReason(reason);
	}
	
	public void setBanned(UUID id, boolean state)
	{
		getPunishedClient(id).setBanned(state);
	}
	
	public void setBannedReason(UUID id, String reason)
	{
		getPunishedClient(id).setBannedReason(reason);
	}
	
	public void clean()
	{
		_punished.clear();
		_punished = null;
	}
	
	
}
