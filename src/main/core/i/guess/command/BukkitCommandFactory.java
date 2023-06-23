package main.core.i.guess.command;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import main.core.i.guess.common.UtilServer;

public class BukkitCommandFactory
{

	public static PluginCommand get(ICommand B){
		
		List<String> aliases = new ArrayList<>();	
		for(String S : B.Aliases()){
			aliases.add(S);
		}
		
		try{
			final Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
			final PluginCommand bukkitCommand = c.newInstance(aliases.get(0), UtilServer.getPlugin());
			bukkitCommand.setName(aliases.get(0));
			bukkitCommand.setLabel("" + aliases.get(0));
			bukkitCommand.setAliases(aliases);
			bukkitCommand.setDescription("");
			bukkitCommand.setPermission("");
			bukkitCommand.setPermissionMessage("");
			bukkitCommand.setUsage("");
			return bukkitCommand;
		}
		catch(final Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
