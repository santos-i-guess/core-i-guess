package main.core.i.guess.common;

import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UtilServer
{

	public static Player[] getPlayers()
	{
		return getServer().getOnlinePlayers().toArray(new Player[0]);
	}

	public static Collection<? extends Player> getPlayersCollection()
	{
		return getServer().getOnlinePlayers();
	}

	public static List<Player> getSortedPlayers()
	{
		return getSortedPlayers(Comparator.comparing(Player::getName));
	}

	public static List<Player> getSortedPlayers(Comparator<Player> comparator)
	{
		ArrayList<Player> players = new ArrayList<Player>(getServer().getOnlinePlayers());
		Collections.sort(players, comparator);
		return players;
	}

	public static Server getServer()
	{
		return Bukkit.getServer();
	}

	public static double getFilledPercent()
	{
		return (double) getPlayers().length / (double) UtilServer.getServer().getMaxPlayers();
	}

	public static void RegisterEvents(Listener listener)
	{
		getPluginManager().registerEvents(listener, getPlugin());
	}

	public static void Unregister(Listener listener)
	{
		HandlerList.unregisterAll(listener);
	}

	public static JavaPlugin getPlugin()
	{
		return JavaPlugin.getProvidingPlugin(UtilServer.class);
	}

	public static PluginManager getPluginManager()
	{
		return getServer().getPluginManager();
	}

	public static <T extends Event> T CallEvent(T event)
	{
		getPluginManager().callEvent(event);
		return event;
	}

	public static void repeat(BukkitRunnable runnable, long time)
	{
		runnable.runTaskTimer(getPlugin(), time, time);
	}

	public static Collection<Player> GetPlayers()
	{
		return Lists.newArrayList(getPlayers());
	}

	public static BukkitTask runAsync(Runnable runnable)
	{
		return getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), runnable);
	}

	public static BukkitTask runAsync(Runnable runnable, long time)
	{
		return getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(getPlugin(), runnable, time);
	}

	public static BukkitTask runAsyncTimer(Runnable runnable, long time, long period)
	{
		return getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), runnable, time, period);
	}

	public static BukkitTask runSync(Runnable runnable)
	{
		return getPlugin().getServer().getScheduler().runTask(getPlugin(), runnable);
	}

	public static BukkitTask runSyncLater(Runnable runnable, long delay)
	{
		return getPlugin().getServer().getScheduler().runTaskLater(getPlugin(), runnable, delay);
	}

	public static BukkitTask runSyncTimer(Runnable runnable, long delay, long period)
	{
		return getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), runnable, delay, period);
	}

	public static BukkitTask runSyncTimer(BukkitRunnable runnable, long delay, long period)
	{
		return runnable.runTaskTimer(getPlugin(), delay, period);
	}
}