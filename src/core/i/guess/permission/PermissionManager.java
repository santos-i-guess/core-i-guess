package core.i.guess.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.i.guess.MiniPlugin;
import core.i.guess.common.F;
import core.i.guess.common.NautHashMap;
import core.i.guess.util.PlayerGrabber;
import core.i.guess.util.UtilPlayer;

public class PermissionManager extends MiniPlugin
{
	
	private NautHashMap<UUID, List<Permission>> _permissionsMap;
	private NautHashMap<Permission, String> _nodeMap;
	
	public static PermissionManager Instance;
	
	public PermissionManager()
	{
		super("Permissions");
		
		Instance = this;
		
		_permissionsMap = new NautHashMap<>();
		_nodeMap = new NautHashMap<>();
	}
	
	public void setNode(Permission perm, String node)
	{
		_nodeMap.put(perm, node);
	}

	public NautHashMap<UUID, List<Permission>> getPermissions()
	{
		return _permissionsMap;
	}
	
	public boolean hasPermission(UUID uuid, Permission perm)
	{
		return hasPermission(uuid, perm, false);
	}
	
	public boolean hasPermission(UUID uuid, Permission perm, boolean inform)
	{
		
		return hasPermission(uuid, perm, inform, "You do not have permission to do that.");
	}
	
	public boolean hasPermission(UUID uuid, Permission perm, String message)
	{
		return hasPermission(uuid, perm, true, message);
	}
	
	public boolean hasPermission(UUID uuid, Permission perm, boolean inform, String message)
	{
		
		boolean status = getPermissions(uuid).contains(perm);
		
		if(inform)
		{
			if(!status && PlayerGrabber.op(uuid).isOnline())
			{
				UtilPlayer.message(PlayerGrabber.p(uuid), F.main(_moduleName, message));
			}
		}
		
		return status;
	}
	
	public List<Permission> getPermissions(UUID uuid){
		if(!_permissionsMap.containsKey(uuid)) return new ArrayList<Permission>();
		
		return _permissionsMap.get(uuid);
	}
	
	public void setPermissions(UUID uuid, List<Permission> perms)
	{
		_permissionsMap.put(uuid, perms);
	}
	
	public void addPermission(UUID uuid, Permission perm)
	{
		List<Permission> _existing = getPermissions(uuid);
		_existing.add(perm);
		
		setPermissions(uuid, _existing);
	}
	
	public void removePermission(UUID uuid, Permission perm)
	{
		List<Permission> _existing = getPermissions(uuid);
		_existing.remove(perm);
		
		setPermissions(uuid, _existing);
	}
}
