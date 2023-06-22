package core.i.guess.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import common.i.guess.util.F;
import core.i.guess.MiniPlugin;


public abstract class CommandBase<PluginType extends MiniPlugin> implements ICommand
{
	private final Permission _permission;
	private List<String> _aliases;

	protected PluginType Plugin;
	protected String _aliasUsed;
	protected CommandCenter _commandCenter;

	public CommandBase(PluginType plugin, Permission permission, String... aliases)
	{
		Plugin = plugin;
		_permission = permission;
		_aliases = Arrays.asList(aliases);
	}

	public Collection<String> Aliases()
	{
		return _aliases;
	}

	public void SetAliasUsed(String alias)
	{
		_aliasUsed = alias;
	}

	public Permission getPermission()
	{
		return _permission;
	}

	public void SetCommandCenter(CommandCenter commandCenter)
	{
		_commandCenter = commandCenter;
	}

	protected void reply(Player caller, String message)
	{
		reply(caller, Plugin.getName(), message);
	}

	protected void reply(Player caller, String module, String message)
	{
		caller.sendMessage(F.main(module, message));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String commandLabel, String[] args)
	{
		return null;
	}

	protected <T> List<String> getMatches(String start, Collection<T> possibleMatches, Function<T, String> toString)
	{
		List<String> matches = new ArrayList<String>();

		for (T possibleMatch : possibleMatches)
		{
			String str = toString.apply(possibleMatch);
			if (str.toLowerCase().startsWith(start.toLowerCase()))
				matches.add(str);
		}

		return matches;
	}

	protected List<String> getMatches(String start, Stream<String> possibleMatches)
	{
		String lcase = start.toLowerCase(Locale.ENGLISH);
		return possibleMatches.filter(str -> str.toLowerCase(Locale.ENGLISH).startsWith(lcase)).collect(Collectors.toList());
	}

	protected List<String> getMatches(String start, Collection<String> possibleMatches)
	{
		List<String> matches = new ArrayList<String>();

		for (String possibleMatch : possibleMatches)
		{
			if (possibleMatch.toLowerCase().startsWith(start.toLowerCase()))
				matches.add(possibleMatch);
		}

		return matches;
	}

	@SuppressWarnings("rawtypes")
	protected List<String> getMatches(String start, Enum[] numerators)
	{
		List<String> matches = new ArrayList<String>();

		for (Enum e : numerators)
		{
			String s = e.toString();
			if (s.toLowerCase().startsWith(start.toLowerCase()))
				matches.add(s);
		}

		return matches;
	}
}