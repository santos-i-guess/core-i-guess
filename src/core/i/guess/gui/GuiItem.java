package core.i.guess.gui;

import org.bukkit.inventory.ItemStack;

public interface GuiItem extends ClickExecutor, Container<ItemStack>
{
	public void setup();
	public void close();
}
