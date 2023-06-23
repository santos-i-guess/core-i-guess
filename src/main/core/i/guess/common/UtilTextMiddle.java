package main.core.i.guess.common;

import org.bukkit.entity.Player;

public class UtilTextMiddle
{
	public static void display(String text, String subtitle, Player... players)
	{
		display(text, subtitle, 20, 60, 20, players);
	}
	
	public static void display(String text, String subtitle)
	{
		display(text, subtitle, 20, 60, 20);
	}
	
	public static void display(String text, String subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks)
	{
		display(text, subtitle, fadeInTicks, stayTicks, fadeOutTicks, UtilServer.getPlayers());
	}
	
	public static void display(String text, String subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks, Player... players)
	{
		showTitle(text, subtitle, fadeInTicks, stayTicks, fadeOutTicks, players);
	}
	
	/**
	 * Show Title text for a player with their current set timings.
	 *
	 * Default timings are 20, 60, 20 (in ticks)
	 */
	private static void showTitle(String title, String sub, int fadeInTicks, int stayTicks, int fadeOutTicks, Player... players)
	{
		if (title == null)
			title = " ";
		if (sub == null)
			sub = " ";
		
		for(Player player : players)
		{
			player.sendTitle(UtilHEX.stylish(title), UtilHEX.stylish(sub), fadeInTicks, stayTicks, fadeOutTicks);
		}
	}

	/**
	 * Clear the title that is currently being displayed, has no affect on timings or subtitle.
	 */
	public static void clear(Player... players)
	{
		display(" ", " ", 0, 1, 0, players);
	}

	public static String progress(float exp)
	{
		String out = "";
		
		for (int i=0 ; i<40 ; i++)
		{
			float cur = i * (1f /40f);
			
			if (cur < exp)
				out += C.cGreen + C.Bold + "|";
			else
				out += C.cGray + C.Bold + "|";
		}
		
		return out;
	}

}
