package net.Equinox.core.utils.update;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event
{

	private static final HandlerList handlers = new HandlerList();
	private UpdateType _type;

	public UpdateEvent(UpdateType type)
	{
		_type = type;
	}

	public UpdateType getType()
	{
		return _type;
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

}
