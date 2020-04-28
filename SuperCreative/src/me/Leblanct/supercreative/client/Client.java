package me.Leblanct.supercreative.client;

import java.util.UUID;

import me.Leblanct.supercreative.world.CreativeWorld;

public class Client 
{
	private UUID _id;
	private Rank _rank;
	private CreativeWorld _world;
	
	public Client(UUID id, Rank rank, CreativeWorld world)
	{
		_id = id;
		_rank = rank;
		_world = world;
	}
	
	public UUID getId()
	{
		return _id;
	}
	
	public Rank getRank()
	{
		return _rank;
	}
	
	public void setRank(Rank rank)
	{
		_rank = rank;
	}
	
	public CreativeWorld getWorld()
	{
		return _world;
	}
	
	public void setWorld(CreativeWorld world)
	{
		_world = world;
	}
}
