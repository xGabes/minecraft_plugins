package me.Leblanct.supercreative.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

public class ClientManager 
{

	private Map<UUID, Client> _clients;
	
	public ClientManager()
	{
		_clients = new HashMap<UUID, Client>();
	}
	
	public Map<UUID, Client> getClients()
	{
		return _clients;
	}
	
	public Client getClient(UUID id)
	{
		return getClients().get(id);
	}
	
	public void addClient(Client client)
	{
		getClients().put(client.getId(), client);
	}
	
	public void removeClient(Client client)
	{
		getClients().remove(client.getId());
	}
	
	public boolean isRank(Client client, Rank rank)
	{
		boolean access = Bukkit.getOfflinePlayer(client.getId()).isOp();
		
		if(!access)
		{
			if(client.getRank().equals(rank))
			{
				return true;
			}
		}
		
		return access;
	}
	
	public boolean hasRank(Client client, Rank rank)
	{
		boolean access = Bukkit.getOfflinePlayer(client.getId()).isOp();
		
		if(!access)
		{
			if(client.getRank().getPriority() >= rank.getPriority())
			{
				return true;
			}
		}
		
		return access;
	}
	
}
