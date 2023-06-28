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

import core.i.guess.Managers;
import core.i.guess.common.C;
import core.i.guess.common.NautHashMap;
import core.i.guess.permission.Permission;
import core.i.guess.permission.PermissionManager;

public class CommandCenter implements Listener
{
	public static final List<String> ALLOW_SPAM_IF_LAST = new ArrayList<>();
	public static CommandCenter Instance;

	protected JavaPlugin Plugin;
	protected static NautHashMap<String, ICommand> Commands;
	private final List<String> BLOCKED_COMMANDS = new ArrayList<>();
	private final String MESSAGE = C.cRed + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.";

	private PermissionManager _permissionManager;
	
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
		
		_permissionManager = Managers.require(PermissionManager.class);
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

			if (_permissionManager.hasPermission(event.getPlayer().getUniqueId(), command.getPermission(), true))
			{

				_playerLastCommand.put(event.getPlayer().getUniqueId(), commandLabel);

				command.SetAliasUsed(commandLabel);
				
				command.Execute(event.getPlayer(), args);
			}
			return;
		}

		if (BLOCKED_COMMANDS.contains(commandName.toLowerCase()) && !(event.getPlayer().isOp() || _permissionManager.hasPermission(event.getPlayer().getUniqueId(), Perm.BLOCKED_COMMAND)))
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
			if (_permissionManager.hasPermission(player.getUniqueId(), entry.getValue().getPermission()))
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