package core.i.guess.bossbar;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import core.i.guess.common.UtilHEX;
import core.i.guess.common.UtilServer;

public class Bossbar {

	private String ID = "";
	
	private BossBar BAR;
	
	public BarColor COLOR;
	public BarStyle STYLE;
	
	public String TEXT_TO_DISPLAY;
		
	public Bossbar(String NAME){
		this(NAME, BarColor.WHITE, BarStyle.SOLID, "...");
	}
	
	public Bossbar(String NAME, BarColor C, BarStyle S, String T){
		
		this.ID = NAME;
		
		this.COLOR = C;
		this.STYLE = S;
		this.TEXT_TO_DISPLAY = UtilHEX.stylish(T);
		
		this.BAR = UtilServer.getPlugin().getServer().createBossBar(this.TEXT_TO_DISPLAY, this.COLOR, this.STYLE);
	}
	
	public String id(){
		return this.ID;
	}
	
	public void update(double PROGRESS){
		
		if(this.BAR == null){ this.BAR = UtilServer.getPlugin().getServer().createBossBar(TEXT_TO_DISPLAY, COLOR, STYLE); }
		
		if(this.TEXT_TO_DISPLAY.isBlank() || this.TEXT_TO_DISPLAY.isEmpty()){ this.delete(); }
		
		this.BAR.setColor(this.COLOR);
		this.BAR.setStyle(this.STYLE);
		this.BAR.setTitle(UtilHEX.stylish(TEXT_TO_DISPLAY));
		this.BAR.setProgress(PROGRESS);
		this.BAR.setVisible(true);
		
		for(Player P : UtilServer.getPlayers()){
			if(!this.BAR.getPlayers().contains(P)){
				this.BAR.addPlayer(P);
			}
		}
	}
	
	public void delete(){
		this.BAR.removeAll();
		this.BAR.setVisible(false);
		this.BAR = null;
	}
}
