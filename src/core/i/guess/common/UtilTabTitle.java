package core.i.guess.common;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UtilTabTitle
{
	public static void broadcastHeader(String header)
	{
		broadcastHeaderAndFooter(header, null);
	}


	public static void broadcastFooter(String footer)
	{
		broadcastHeaderAndFooter(null, footer);
	}


	public static void broadcastHeaderAndFooter(String header, String footer)
	{
		for (Player player : Bukkit.getOnlinePlayers())
			doHeaderAndFooter(player, header, footer);
	}

	public static void setHeaderAndFooter(Player player, String header, String footer)
	{
		doHeaderAndFooter(player, header, footer);
	}

	public static void setHeader(Player p, String header)
	{
		doHeaderAndFooter(p, header, null);
	}


	public static void setFooter(Player p, String footer)
	{
		doHeaderAndFooter(p, null, footer);
	}


	public static void doHeaderAndFooter(Player p, String rawHeader, String rawFooter)
	{
		String h = UtilHEX.stylish(rawHeader);
		String f = UtilHEX.stylish(rawFooter);
		
		p.setPlayerListHeaderFooter(h, f);
	}
}