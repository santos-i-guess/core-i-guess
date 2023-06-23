package core.i.guess.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

import core.i.guess.lifetimes.Component;

public interface ICommand extends Component
{
	void SetCommandCenter(CommandCenter commandCenter);
	void Execute(Player caller, String[] args);

	Collection<String> Aliases();

	void SetAliasUsed(String name);
	
	Permission getPermission();

	@Override
	default void activate()
	{
		CommandCenter.Instance.addCommand(this);
	}

	@Override
	default void deactivate()
	{
		CommandCenter.Instance.removeCommand(this);
	}

	List<String> onTabComplete(CommandSender sender, String commandLabel, String[] args);
}