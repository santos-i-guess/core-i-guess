package core.i.guess.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.i.guess.common.C;
import core.i.guess.common.Callback;
import core.i.guess.common.F;
import core.i.guess.common.UtilPlayerBase;
import core.i.guess.common.UtilServer;

public class UtilPlayer
{
	
	public static void message(Entity client, LinkedList<String> messageList)
	{
		UtilPlayerBase.message(client, messageList);
	}

	public static void message(Entity client, String message)
	{
		UtilPlayerBase.message(client, message);
	}

	public static void message(Entity client, LinkedList<String> messageList, boolean wiki)
	{
		UtilPlayerBase.message(client, messageList, wiki);
	}

	public static void message(Entity client, String message, boolean wiki)
	{
		UtilPlayerBase.message(client, message, wiki);
	}

	public static void messageSearchOnlineResult(Player caller, String player, int matchCount)
	{
		UtilPlayerBase.messageSearchOnlineResult(caller, player, matchCount);
	}
	
	public static Player searchExact(String name)
	{
		for (Player cur : UtilServer.getPlayers())
			if (cur.getName().equalsIgnoreCase(name))
				return cur;

		return null;
	}

	public static Player searchExact(UUID uuid)
	{
		return UtilServer.getServer().getPlayer(uuid);
	}


	public static void sendMatches(Player target, String module, Collection<String> matches)
	{
		sendMatches(target, module, matches, Function.identity());
	}

	public static <T> void sendMatches(Player target, String module, Collection<T> matches, Function<T, String> mapToString)
	{
		String matchString = matches.stream().map(mapToString).collect(Collectors.joining(C.mBody + ", " + C.mElem));
		UtilPlayer.message(target, F.main(module, "Matches [" + C.mElem + matchString + C.mBody + "]."));
	}

	public static String searchCollection(Player caller, String player, Collection<String> coll, String collName, boolean inform)
	{
		LinkedList<String> matchList = new LinkedList<String>();

		for (String cur : coll)
		{
			if (cur.equalsIgnoreCase(player))
				return cur;

			if (cur.toLowerCase().contains(player.toLowerCase()))
				matchList.add(cur);
		}

		// No / Non-Unique
		if (matchList.size() != 1)
		{
			if (!inform)
				return null;

			// Inform
			UtilPlayerBase.messageSearchOnlineResult(caller, player, 0);

			if (matchList.size() > 0)
			{
				StringBuilder matchString = new StringBuilder();
				for (String cur : matchList)
					matchString.append(cur).append(" ");

				message(caller,
						F.main(collName + " Search", "" + C.mBody + " Matches [" + C.mElem + matchString + C.mBody + "]."));
			}

			return null;
		}

		return matchList.get(0);
	}

	public static Player searchOnline(Player caller, String player, boolean inform)
	{
		return UtilPlayerBase.searchOnline(caller, player, inform);
	}

	public static void searchOffline(List<String> matches, final Callback<String> callback, final Player caller,
									 final String player, final boolean inform)
	{
		// No / Non-Unique
		if (matches.size() != 1)
		{
			if (!inform || !caller.isOnline())
			{
				callback.run(null);
				return;
			}

			// Inform
			message(caller,
					F.main("Offline Player Search", "" + C.mCount + matches.size() + C.mBody + " matches for [" + C.mElem
							+ player + C.mBody + "]."));

			if (matches.size() > 0)
			{
				String matchString = "";
				for (String cur : matches)
					matchString += cur + " ";
				if (matchString.length() > 1)
					matchString = matchString.substring(0, matchString.length() - 1);

				message(caller,
						F.main("Offline Player Search", "" + C.mBody + "Matches [" + C.mElem + matchString + C.mBody + "]."));
			}

			callback.run(null);
			return;
		}

		callback.run(matches.get(0));
	}

	public static LinkedList<Player> matchOnline(Player caller, String players, boolean inform)
	{
		LinkedList<Player> matchList = new LinkedList<Player>();

		String failList = "";

		for (String cur : players.split(","))
		{
			Player match = searchOnline(caller, cur, inform);

			if (match != null)
				matchList.add(match);

			else
				failList += cur + " ";
		}

		if (inform && failList.length() > 0)
		{
			failList = failList.substring(0, failList.length() - 1);
			message(caller, F.main("Online Player(s) Search", "" + C.mBody + "Invalid [" + C.mElem + failList + C.mBody + "]."));
		}

		return matchList;
	}
	
	public static void clearInventory(Player player)
	{
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
	}

	public static void clearPotionEffects(Player player)
	{
		for (PotionEffect effect : player.getActivePotionEffects())
		{
			player.removePotionEffect(effect.getType());
		}
	}
	
	public static Location getTargetLocation(Player player, double distance)
	{
		Vector looking = player.getLocation().getDirection().clone();
		looking.multiply(distance);
		return player.getEyeLocation().clone().add(looking);
	}
	
	/**
	 * Check if the player is at least the specified amount of blocks in the air
	 * while provided block types are ignored.
	 *
	 * @see #isInAir(Player, int, Set)
	 */
	public static boolean isInAir(Player player, int minAir, Material... exclude)
	{
		EnumSet<Material> excludeSet = EnumSet.noneOf(Material.class);
		if (exclude != null) excludeSet.addAll(Arrays.asList(exclude));

		return isInAir(player, minAir, excludeSet);
	}

	/**
	 * Check if the player is at least the specified amount of blocks in the air
	 * while provided block types are ignored.
	 *
	 * @param player The Player to check
	 * @param minAir The min amount of Blocks to count as in the air
	 * @param exclude that are being ignored and count as Air
	 *
	 * @return if the Player is in the air
	 */
	public static boolean isInAir(Player player, int minAir, Set<Material> exclude)
	{
		Block block = player.getLocation().getBlock();
		int i = 0;
		while (i < minAir)
		{
			if (block.getType() != Material.AIR)
			{
				if (exclude.contains(block.getType()))
				{
					continue;
				}

				return false;
			}
			block = block.getRelative(BlockFace.DOWN);
			i++;
		}
		return true;
	}
	
	public static void closeInventoryIfOpen(Player player)
	{
		if (hasOpenInventory(player))
			player.closeInventory();
	}

	public static boolean hasOpenInventory(Player player)
	{
		return player.getOpenInventory().getType() != InventoryType.CRAFTING;
	}

	public static String getIp(Player player)
	{
		return player.getAddress().getAddress().getHostAddress();
	}

	public static void teleportUniform(List<Player> players, List<Location> locations, BiConsumer<Player, Location> teleport)
	{
		// Find the number of spaces between each player,
		int spacesBetween = Math.max(Math.floorDiv(locations.size(), players.size()), 1);
		int spaceIndex = 0;

		// Teleport the players to locations every [spacesBetween] spaces
		for (Player player : players)
		{
			teleport.accept(player, locations.get(spaceIndex));

			spaceIndex = (spaceIndex + spacesBetween) % locations.size();
		}
	}

	public static void teleportUniform(List<Player> players, List<Location> locations)
	{
		teleportUniform(players, locations, Entity::teleport);
	}
}
