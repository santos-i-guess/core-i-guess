package core.i.guess.gui.constants;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import core.i.guess.gui.GuiItem;

public class DisplayButton implements GuiItem {

	public ItemStack I;
	
	public DisplayButton(ItemStack REF){
		
		this.I = REF.clone();
	}
	
	@Override
	public void click(ClickType clickType) {
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