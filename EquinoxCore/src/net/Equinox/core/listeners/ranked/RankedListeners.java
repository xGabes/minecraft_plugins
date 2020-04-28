package net.Equinox.core.listeners.ranked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import net.Equinox.core.Core;
import net.Equinox.core.client.Rank;

public class RankedListeners implements Listener
{

	
	private Core _core;
	private Map<UUID, Boolean> _cooldown;
	private List<UUID> _fixed;
	
	public RankedListeners(Core plugin)
	{
		_core = plugin;
		_cooldown = new HashMap<UUID, Boolean>();
		_fixed = new ArrayList<UUID>();
		
	}
	
	@SuppressWarnings("deprecation")
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
            Player p = e.getPlayer();
           
            if(!_core.serverManager.canDoubleJump()) return;
            if (p.getGameMode() == GameMode.CREATIVE) return;
            
            if (!_core.clientManager.getClient(p.getUniqueId()).getRank()
            		.hasRank(_core.clientManager.getClient(p.getUniqueId()).getRank(), Rank.VIP) && p.getAllowFlight() == true && !_fixed.contains(p.getUniqueId()))
            {
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    _fixed.add(p.getUniqueId());
            }
           
            if (!_core.clientManager.getClient(p.getUniqueId()).getRank()
            		.hasRank(_core.clientManager.getClient(p.getUniqueId()).getRank(), Rank.VIP)) return;
           
            if (_cooldown.get(p.getUniqueId()) != null && _cooldown.get(p.getUniqueId()) == true) {
                    p.setAllowFlight(true);
            } else 
            {
                    p.setAllowFlight(false);
            }
           
            if (p.isOnGround()) {
                    _cooldown.put(p.getUniqueId(), true);
            }
    }
	
	@SuppressWarnings("deprecation")
    @EventHandler
    public void onFly(PlayerToggleFlightEvent e)
	{
            Player p = e.getPlayer();
            
            if (p.getGameMode() == GameMode.CREATIVE) return;
            if(!_core.serverManager.canFly())
            {
            	    e.setCancelled(true);
            	    return;
            }
            if(_core.serverManager.canClientFly(p.getUniqueId())) return;
            if (_core.clientManager.getClient(p.getUniqueId()).getRank().hasRank(_core.clientManager.getClient(p.getUniqueId()).getRank(), Rank.VIP)) {
                    if (_cooldown.get(p.getUniqueId()) == true)
                    {
                            e.setCancelled(true);
                            _cooldown.put(p.getUniqueId(), false);
                            p.setVelocity(p.getLocation().getDirection().multiply(1.6D).setY(1.0D));
                            p.setAllowFlight(false);
                    }
            }
    }
	
	@EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
       
        if (e.getCause() == DamageCause.FALL && _cooldown.containsKey(p.getUniqueId()))
        {
            e.setCancelled(true);
        }
    }
}
