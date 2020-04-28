package net.Equinox.core.database;

import java.util.UUID;

import net.Equinox.core.Core;
import net.Equinox.core.cosmetics.deathcries.DeathCryType;
import net.Equinox.core.cosmetics.killeffects.KillEffectType;
import net.Equinox.core.cosmetics.swordheros.SwordHeroType;

public class CosmeticsDatabase 
{
	
	private Core _core;
	
	public CosmeticsDatabase(Core plugin)
	{
		_core = plugin;
	}

	public void accessDeathCry(byte access, UUID id, DeathCryType type)
	{
		if(access == 0)
		{
			return;
		}
		
		if(access == 1)
		{
			_core.deathCryManager.addDeathCry(id, type);
			return;
		}
		
		if(access == 2)
		{
			_core.deathCryManager.addDeathCry(id, type);
			_core.deathCryManager.setEnabled(id, type);
			return;
		}
	}
	
	public void accessKillEffect(byte access, UUID id, KillEffectType type)
	{
		if(access == 0)
		{
			return;
		}
		
		if(access == 1)
		{
			_core.killEffectManager.addKillEffect(id, type);
			return;
		}
		
		if(access == 2)
		{
			_core.killEffectManager.addKillEffect(id, type);
			_core.killEffectManager.setEnabled(id, type);
			return;
		}
	}
	
	public void accessSwordHero(byte access, UUID id, SwordHeroType type)
	{
		if(access == 0)
		{
			return;
		}
		
		if(access == 1)
		{
			_core.swordHeroManager.addSwordHero(id, type);
			return;
		}
		
		if(access == 2)
		{
			_core.swordHeroManager.addSwordHero(id, type);
			_core.swordHeroManager.setEnabled(id, type);
			return;
		}
	}
}
