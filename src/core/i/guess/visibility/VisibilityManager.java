package core.i.guess.visibility;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import core.i.guess.MiniPlugin;

public class VisibilityManager extends MiniPlugin
{

	private final Map<Player, Map<Player, Set<String>>> _visibility = new HashMap<>();
	
	public VisibilityManager(JavaPlugin plugin)
	{
		super("Visibility Manager", plugin);
	}
	
	public boolean canSee(Player viewer, Player target)
	{
		return _visibility.get(viewer).getOrDefault(target, new HashSet<>()).isEmpty();
	}
	
	public void refreshVisibility(Player viewer, Player target)
	{
		if (viewer == null || target == null)
		{
			return;
		}
		if (viewer.getEntityId() == target.getEntityId())
		{
			return;
		}
		if (canSee(viewer, target))
		{
			viewer.showPlayer(_plugin, target);
		}
		else
		{
			viewer.hidePlayer(_plugin, target);
		}
	}
	
	public void hidePlayer(Player viewer, Player target, String reason)
	{
		if (viewer == null || target == null || reason == null)
		{
			return;
		}
		if (viewer.getEntityId() == target.getEntityId())
		{
			return;
		}
		Set<String> reasons = _visibility.get(viewer).computeIfAbsent(target, (p) -> new HashSet<>());
		if (reasons.contains(reason))
		{
			return;
		}
		reasons.add(reason);
		refreshVisibility(viewer, target);
	}
	
	public void showPlayer(Player viewer, Player target, String reason)
	{
		if (viewer == null || target == null || reason == null)
		{
			return;
		}
		if (viewer.getEntityId() == target.getEntityId())
		{
			return;
		}
		Set<String> reasons = _visibility.get(viewer).get(target);
		if (reasons == null)
		{
			return;
		}
		boolean modified = reasons.remove(reason);
		if (reasons.isEmpty())
		{
			_visibility.get(viewer).remove(target);
		}
		if (modified)
		{
			refreshVisibility(viewer, target);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event)
	{
		_visibility.put(event.getPlayer(), new HashMap<>());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event)
	{
		_visibility.remove(event.getPlayer());
		_visibility.values().forEach(v -> v.remove(event.getPlayer()));
	}
}
