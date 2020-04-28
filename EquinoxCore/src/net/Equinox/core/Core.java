package net.Equinox.core;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.Equinox.core.chat.AutoMessages;
import net.Equinox.core.chat.ChatManager;
import net.Equinox.core.client.ClientManager;
import net.Equinox.core.client.admin.AnnounceCommand;
import net.Equinox.core.client.admin.BroadcastCommand;
import net.Equinox.core.client.admin.ChatCommand;
import net.Equinox.core.client.admin.ManageCommand;
import net.Equinox.core.client.admin.PunishCommand;
import net.Equinox.core.client.admin.RankCommand;
import net.Equinox.core.client.infinitum.ColorCommand;
import net.Equinox.core.client.infinitum.IconCommand;
import net.Equinox.core.client.member.HelpCommand;
import net.Equinox.core.client.owner.UploadCommand;
import net.Equinox.core.client.pro.FlyCommand;
import net.Equinox.core.client.punishment.PunishedClientManager;
import net.Equinox.core.client.vip.FireworkCommand;
import net.Equinox.core.client.vip.ShoutCommand;
import net.Equinox.core.cosmetics.deathcries.DeathCryManager;
import net.Equinox.core.cosmetics.infinitum.InfinitumTrailManager;
import net.Equinox.core.cosmetics.killeffects.KillEffectManager;
import net.Equinox.core.cosmetics.swordheros.SwordHeroManager;
import net.Equinox.core.database.PlayerDatabase;
import net.Equinox.core.database.PunishmentDatabase;
import net.Equinox.core.guis.ChatMenu;
import net.Equinox.core.guis.ClientMenu;
import net.Equinox.core.guis.CosmeticsMenu;
import net.Equinox.core.guis.InfinitumMenu;
import net.Equinox.core.guis.PunishmentMenu;
import net.Equinox.core.guis.ServerMenu;
import net.Equinox.core.listeners.GlobalListeners;
import net.Equinox.core.listeners.ranked.RankedListeners;
import net.Equinox.core.server.ServerManagement;
import net.Equinox.core.utils.particles.HelixEffect;
import net.Equinox.core.utils.update.Updater;
import net.md_5.bungee.api.ChatColor;

public class Core extends JavaPlugin
{
	
	//
	private Updater _updater;
	private AutoMessages _auto;
	private HelixEffect _helix;
	
	//Commands:
	private HelpCommand _helpCommand;
	private ShoutCommand _shoutCommand;
	private BroadcastCommand _broadcastCommand;
	private AnnounceCommand _announceCommand;
	private FlyCommand _flyCommand;
	private UploadCommand _uploadCommand;
	private PunishCommand _punishCommand;
	private IconCommand _iconCommand;
	private ColorCommand _colorCommand;
	private ChatCommand _chatCommand;
	private FireworkCommand _fireworkCommand;
	private RankCommand _rankCommand;
	private ManageCommand _manageCommand;
	
	//Other:
	public ClientManager clientManager;
	public ChatManager chatManager;
	public PlayerDatabase playerData;
	public PunishmentDatabase punishmentData;
	public PunishedClientManager punishedManager;
	public ServerManagement serverManager;
	
	//Menus:
	public PunishmentMenu punishmentMenu;
	public ClientMenu clientMenu;
	public InfinitumMenu infinitumMenu;
	public ChatMenu chatMenu;
	public ServerMenu serverMenu;
	public CosmeticsMenu cosmeticsMenu;
	
	//Cosmetics:
	public InfinitumTrailManager infinitumTrailManager;
	public DeathCryManager deathCryManager;
	public KillEffectManager killEffectManager;
	public SwordHeroManager swordHeroManager;
	

	public void onEnable()
	{
		
		System.out.println("");
		System.out.println(ChatColor.BLUE + "Equinox Core");
		System.out.println(ChatColor.GREEN + "Enabled");
		System.out.println("");
		
		_auto = new AutoMessages(this, "", 20*60*1L, 20*60*3L, 
				"Welcome to " + ChatColor.WHITE + "Equinox " + ChatColor.BLUE + "Network",
				"Please, report bugs to " + ChatColor.RED + "Leblanct",
				"Enjoy your stay!",
				"Remember this is only the " + ChatColor.WHITE.toString() + ChatColor.BOLD + "beta!",
				"Cosmetics are available to everyone during beta!",
				"Register on our website at" + ChatColor.AQUA + " http://equinoxnetwork.net/",
				"Purchasing ranks will help us maintain the server!");
		_auto.start();
		_updater = new Updater(this);
		
		_helix = new HelixEffect(this);
		
		//Commands:
		_helpCommand = new HelpCommand(this);
		_shoutCommand = new ShoutCommand(this);
		_broadcastCommand = new BroadcastCommand(this);
		_announceCommand = new AnnounceCommand(this);
		_flyCommand = new FlyCommand(this);
		_uploadCommand = new UploadCommand(this);
		_punishCommand = new PunishCommand(this);
		_iconCommand = new IconCommand(this);
		_colorCommand = new ColorCommand(this);
		_chatCommand = new ChatCommand(this);
		_fireworkCommand = new FireworkCommand(this);
		_rankCommand = new RankCommand(this);
		_manageCommand = new ManageCommand(this);
		
		clientManager = new ClientManager(this);
		chatManager = new ChatManager(this);
		playerData = new PlayerDatabase(this);
		punishmentData = new PunishmentDatabase(this);
		punishedManager = new PunishedClientManager(this);
		serverManager = new ServerManagement(this);
		punishmentMenu = new PunishmentMenu(this);
		clientMenu = new ClientMenu(this);
		infinitumMenu = new InfinitumMenu(this);
		chatMenu = new ChatMenu(this);
		serverMenu = new ServerMenu(this);
		cosmeticsMenu = new CosmeticsMenu(this);
		
		infinitumTrailManager = new InfinitumTrailManager();
		killEffectManager = new KillEffectManager();
		deathCryManager = new DeathCryManager();
		swordHeroManager = new SwordHeroManager();
		
		Bukkit.getServer().getPluginManager().registerEvents(cosmeticsMenu, this);
		Bukkit.getServer().getPluginManager().registerEvents(serverMenu, this);
		Bukkit.getServer().getPluginManager().registerEvents(chatMenu, this);
		Bukkit.getServer().getPluginManager().registerEvents(infinitumMenu, this);
		Bukkit.getServer().getPluginManager().registerEvents(punishmentMenu, this);
		Bukkit.getServer().getPluginManager().registerEvents(clientMenu, this);
		Bukkit.getServer().getPluginManager().registerEvents(new GlobalListeners(this), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new RankedListeners(this), this);
	}
	
	public void onDisable()
	{
		System.out.println("");
		System.out.println(ChatColor.BLUE + "Equinox Core");
		System.out.println(ChatColor.RED + "Disabled");
		System.out.println("");
		
		try {
			playerData.uploadAll();
			punishmentData.uploadAll();
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			player.kickPlayer(ChatColor.WHITE + "Equinox" + ChatColor.BLUE + " Network "
					+ ChatColor.GREEN + "\nJoin again in 30 seconds!");
		}
		
		clientManager.clean();
		punishedManager.clean();
	}
}
