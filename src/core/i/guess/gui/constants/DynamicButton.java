package core.i.guess.gui.constants;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import core.i.guess.gui.GuiItem;

public class DynamicButton implements GuiItem {
	public Player P;
	public ItemStack I;
	
	public String CMD;
	
	public DynamicButton(Player P, String COMMAND, ItemStack REF){
		this.P = P;
		this.CMD = COMMAND;
		
		this.I = REF.clone();
	}
	
	@Override
	public void click(ClickType clickType) {
		this.P.chat(CMD);
	}

	@Override
	public ItemStack getObject(){
		return this.I.clone();
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
