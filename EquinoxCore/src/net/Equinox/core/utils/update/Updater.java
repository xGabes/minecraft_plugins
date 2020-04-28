package net.Equinox.core.utils.update;

import org.bukkit.Bukkit;

import net.Equinox.core.Core;

public class Updater implements Runnable
{

	public Updater(Core plugin)
	{
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
	}

	@Override
	public void run()
	{
		for (UpdateType type : UpdateType.values())
		{
			if (type.elapsed())
			{
				Bukkit.getServer().getPluginManager()
						.callEvent(new UpdateEvent(type));
			}
		}
	}

}
