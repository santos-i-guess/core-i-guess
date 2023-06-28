package core.i.guess.bossbar;

import java.util.HashMap;

import core.i.guess.MiniPlugin;

public class BossbarManager extends MiniPlugin
{

	public HashMap<String, Bossbar> _bossbars;
	
	public BossbarManager(){
		super("Bossbar Manager");
				
		_bossbars = new HashMap<>();
	}

	public Bossbar get(String name)
	{
		if(!_bossbars.containsKey(name)){
			_bossbars.put(name, new Bossbar(name));
		}
		
		return _bossbars.get(name);
	}
}
