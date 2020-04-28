package net.Equinox.core.client.punishment;

import java.util.UUID;

public class PunishedClient
{

	private UUID _uuid;
	private String _punisher;
	private PunishmentType _type;
	private boolean _isMuted;
	private String _mutedReason;
	private boolean _isBanned;
	private String _bannedReason;
	
	public PunishedClient(UUID id, String punisher, PunishmentType type, boolean muted, String muteReason, boolean banned, String banReason)
	{
		_uuid = id;
		_punisher = punisher;
		_type = type;
		_isMuted = muted;
		_mutedReason = muteReason;
		_isBanned = banned;
		_bannedReason = banReason;
		
	}
	
	public UUID getUUID()
	{
		return _uuid;
	}
	
	public String getPunisher()
	{
		return _punisher;
	}
	
	public void setPunisher(String name)
	{
		_punisher = name;
	}
	
	public PunishmentType getPunishmentType()
	{
		return _type;
	}
	
	public void setPunishmentType(PunishmentType type)
	{
		_type = type;
	}
	
	public boolean isMuted()
	{
		return _isMuted;
	}
	
	public void setMuted(boolean state)
	{
		_isMuted = state;
	}
	
	public String getMutedReason()
	{
		return _mutedReason;
	}
	
	public void setMutedReason(String reason)
	{
		_mutedReason = reason;
	}
	
	public boolean isBanned()
	{
		return _isBanned;
	}
	
	public void setBanned(boolean state)
	{
		_isBanned = state;
	}
	
	public String getBannedReason()
	{
		return _bannedReason;
	}
	
	public void setBannedReason(String reason)
	{
		_bannedReason = reason;
	}
}
