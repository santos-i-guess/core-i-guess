package core.i.guess.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

import core.i.guess.common.UtilServer;

public class PlayerGrabber
{
	
	public static Player p(UUID uuid){ return UtilServer.getPlugin().getServer().getPlayer(uuid); }
	public static Player p(String str){
		if(!str.contains("-")){
			return UtilServer.getPlugin().getServer().getPlayerExact(str);
		}
		else{
			return UtilServer.getPlugin().getServer().getPlayer(UUID.fromString(str));
		}
	}
	
	public static OfflinePlayer op(UUID uuid){ return UtilServer.getPlugin().getServer().getOfflinePlayer(uuid); }
	@SuppressWarnings("deprecation")
	public static OfflinePlayer op(String str){
		if(!str.contains("-")){
			return UtilServer.getPlugin().getServer().getOfflinePlayer(str);
		}
		else{
			return UtilServer.getPlugin().getServer().getOfflinePlayer(UUID.fromString(str));
		}
	}

}
