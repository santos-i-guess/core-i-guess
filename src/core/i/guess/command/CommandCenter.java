package core.i.guess.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.i.guess.util.C;
import common.i.guess.util.NautHashMap;
import core.i.guess.util.UtilPermission;

public class CommandCenter implements Listener
{
	public static final List<String> ALLOW_SPAM_IF_LAST = new ArrayList<>();
	public static CommandCenter Instance;

	protected JavaPlugin Plugin;
	protected static NautHashMap<String, ICommand> Commands;
	private final List<String> BLOCKED_COMMANDS = new ArrayList<>();
	private final String MESSAGE = C.cRed + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.";

	private Map<UUID, String> _playerLastCommand = new HashMap<>();

	public enum Perm implements Permission
	{
		BLOCKED_COMMAND,
	}

	private CommandCenter(JavaPlugin instance)
	{
		Plugin = instance;
		Commands = new NautHashMap<>();
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}

	public static void Initialize(JavaPlugin plugin)
	{
		if (Instance == null)
			Instance = new CommandCenter(plugin);
	}
	
	 @EventHandler
	 public void sendPlayerCommandsViaTab(PlayerCommandSendEvent e)
	 {
			
		for(String cmd : getCommands(e.getPlayer())){
			e.getCommands().add(cmd);
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		String commandName = event.getMessage().substring(1);
		String argString = event.getMessage().substring(event.getMessage().indexOf(' ') + 1);
		String[] args = new String[]{};

		if (commandName.contains(" "))
		{
			commandName = commandName.split(" ")[0];
			args = argString.split(" ");
		}

		String commandLabel = commandName.toLowerCase();

		ICommand command = Commands.get(commandLabel);

		if (command != null)
		{
			event.setCancelled(true);

			if (UtilPermission.hasPermission(event.getPlayer().getUniqueId(), command.getPermission()))
			{

				_playerLastCommand.put(event.getPlayer().getUniqueId(), commandLabel);

				command.SetAliasUsed(commandLabel);
				
				command.Execute(event.getPlayer(), args);
			}
			else
			{
				event.getPlayer().sendMessage(C.mHead + "Permissions> " + C.mBody + "You do not have permission to do that.");
			}
			return;
		}

		if (BLOCKED_COMMANDS.contains(commandName.toLowerCase()) && !(event.getPlayer().isOp() || UtilPermission.hasPermission(event.getPlayer().getUniqueId(), Perm.BLOCKED_COMMAND)))
		{
			event.setCancelled(true);
			event.getPlayer().sendMessage(MESSAGE);
			return;
		}
	}

	public void addCommand(ICommand command)
	{
		for (String commandRoot : command.Aliases())
		{
			Commands.put(commandRoot.toLowerCase(), command);
			command.SetCommandCenter(this);
		}
	}

	public void removeCommand(ICommand command)
	{
		for (String commandRoot : command.Aliases())
		{
			Commands.remove(commandRoot.toLowerCase());
			command.SetCommandCenter(null);
		}
	}

	public static NautHashMap<String, ICommand> getCommands()
	{
		return Commands;
	}

	private List<String> getCommands(Player player)
	{
		List<String> commands = new ArrayList<>();
		for (Map.Entry<String, ICommand> entry : Commands.entrySet())
		{
			if (UtilPermission.hasPermission(player.getUniqueId(), entry.getValue().getPermission()))
			{
				commands.add(entry.getKey());
			}
		}

		return commands;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.getPlayer().updateCommands();
	}
}