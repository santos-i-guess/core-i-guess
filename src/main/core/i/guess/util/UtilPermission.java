package main.core.i.guess.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import main.core.i.guess.command.Permission;
import main.core.i.guess.common.NautHashMap;

public class UtilPermission
{
	
	private static NautHashMap<UUID, List<Permission>> _permissionsMap = new NautHashMap<>();

	public static NautHashMap<UUID, List<Permission>> getPermissions()
	{
		return _permissionsMap;
	}
	
	public static boolean hasPermission(UUID uuid, Permission perm)
	{
		return getPermissions(uuid).contains(perm);
	}
	
	public static List<Permission> getPermissions(UUID uuid){
		if(!_permissionsMap.containsKey(uuid)) return new ArrayList<Permission>();
		
		return _permissionsMap.get(uuid);
	}
	
	public static void setPermissions(UUID uuid, List<Permission> perms)
	{
		_permissionsMap.put(uuid, perms);
	}
	
	public static void addPermission(UUID uuid, Permission perm)
	{
		List<Permission> _existing = getPermissions(uuid);
		_existing.add(perm);
		
		setPermissions(uuid, _existing);
	}
	
	public static void removePermission(UUID uuid, Permission perm)
	{
		List<Permission> _existing = getPermissions(uuid);
		_existing.remove(perm);
		
		setPermissions(uuid, _existing);
	}
}
