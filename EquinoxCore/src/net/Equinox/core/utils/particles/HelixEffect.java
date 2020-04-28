package net.Equinox.core.utils.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import net.Equinox.core.Core;

public class HelixEffect
{
	
	private static Core _core;
	
	public HelixEffect(Core plugin)
	{
		_core = plugin;
	}

	public static void drawParticles(Location loc, Particle particle)
	{
		new BukkitRunnable()
		{

			double t = 0;
			double r = 2;
			
			@Override
			public void run() 
			{
				t += Math.PI / 6;
				double x = r*Math.cos(t);
				double y = 0.1*t;
				double z = r*Math.sin(t);
				loc.add(x, y, z);
				
				loc.getWorld().spawnParticle(particle, loc, 2, 0D, 0D, 0D, 0D);
				loc.subtract(x, y, z);
				//Trying:
				r -= 0.035;
				if(t > Math.PI * 7)
				{
					this.cancel();
				}
				
			}
			
			
		}.runTaskTimerAsynchronously(_core, 0, 1);
	}
}
