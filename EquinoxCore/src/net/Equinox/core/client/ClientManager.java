package net.Equinox.core.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import net.Equinox.core.Core;
import net.Equinox.core.client.infinitum.InfinitumIcon;

public class ClientManager 
{

	private Map<UUID, Client> _clients;
	private Core _core;
	
	public ClientManager(Core core)
	{
		_clients = new HashMap<UUID, Client>();
		_core = core;
	}
	
	public Map<UUID, Client> getClients()
	{
		return _clients;
	}
	
	public Client getClient(UUID id)
	{
		if(!_clients.containsKey(id))
		{
			_clients.put(id, _core.playerData.load(id));
		}
		
		Client client = _clients.get(id);
		return client;
	}
	
	public Client getClient(OfflinePlayer player)
	{
		Client client = getClient(player.getUniqueId());
		return client;
	}
	
	public void addClient(Client client)
	{
		_clients.put(client.getUUID(), client);
	}
	
	public void removeClient(Client client)
	{
		_clients.remove(client.getUUID());
	}
	
	public void setRank(UUID id, Rank rank)
	{
		getClient(id).setRank(rank);
	}
	
	public void setRank(OfflinePlayer player, Rank rank)
	{
		getClient(player).setRank(rank);
	}
	
	public void setInfinitum(UUID id, boolean status)
	{
		getClient(id).setInfinitum(status);
	}
	
	public void setInfinitum(OfflinePlayer player, boolean status)
	{
		getClient(player).setInfinitum(status);
	}
	
	public void setIcon(UUID id, InfinitumIcon icon)
	{
		getClient(id).setIcon(icon);
	}
	
	public void setIcon(OfflinePlayer player, InfinitumIcon icon)
	{
		getClient(player).setIcon(icon);
	}
	
	public void setCoins(UUID id, long amount)
	{
		getClient(id).setCoins(amount);
	}
	
	public void setCoins(OfflinePlayer player, long amount)
	{
		getClient(player).setCoins(amount);
	}
	
	public void setCubits(UUID id, long amount)
	{
		getClient(id).setCubits(amount);
	}
	
	public void setCubits(OfflinePlayer player, long amount)
	{
		getClient(player).setCubits(amount);
	}
	
	public void clean()
	{
		_clients.clear();
		_core = null;
		_clients = null;
	}
}
