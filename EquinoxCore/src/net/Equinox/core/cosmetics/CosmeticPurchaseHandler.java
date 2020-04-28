package net.Equinox.core.cosmetics;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.Equinox.core.Core;
import net.Equinox.core.cosmetics.deathcries.DeathCryType;
import net.Equinox.core.cosmetics.killeffects.KillEffectType;
import net.Equinox.core.cosmetics.swordheros.SwordHeroType;

public class CosmeticPurchaseHandler 
{
	private Core _core;
	private Map<UUID, DeathCryType> _deathCryPurchase;
	private Map<UUID, KillEffectType> _killEffectPurchase;
	private Map<UUID, SwordHeroType> _swordHeroPurchase;
	
	public CosmeticPurchaseHandler(Core plugin)
	{
		_core = plugin;
		_deathCryPurchase = new HashMap<UUID, DeathCryType>();
		_killEffectPurchase = new HashMap<UUID, KillEffectType>();
		_swordHeroPurchase = new HashMap<UUID, SwordHeroType>();
		
	}
	
	
}
