package core.i.guess.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilBlock
{

	public static void QuickChangeBlockAt(Location location, Material setTo)
	{
		QuickChangeBlockAt(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), setTo);
	}

	public static void QuickChangeBlockAt(World world, int x, int y, int z, Material setTo)
	{
		world.getBlockAt(x, y, z).setType(setTo);
	}

	public static String blockLocation(Block B){
		return B.getX() + ", " + B.getY() + ", " + B.getZ();
	}
	
	public static List<Block> radius(Location L, int R){
		return blocks(L.clone().add(-R, -R, -R), L.clone().add(R, R, R));
	}
	
	public static int blocks_squared(Location L1, Location L2){
		
		L1.setY(0.0D);
		L2.setY(0.0D);
		
		return blocks(L1, L2).size();
	}
	
	public static Set<Block> getSideAirBlocks(Location A, Location B){
		Set<Block> R = new HashSet<Block>();
		R = UtilBlock.getSideBlocks(A, B);
		
		for(Block BL : R){
			if(BL.getType().isSolid()){
				R.remove(BL);
			}
		}
		
		return R;
	}
	
	public static Set<Block> getSideBlocks(Location A, Location B){
		
		Vector firstVector = A.toVector();
        Vector secondVector = B.toVector();
        Vector maximum = Vector.getMaximum(firstVector, secondVector);
        Vector minimum = Vector.getMinimum(firstVector, secondVector);
		
        Set<Block> blocks = new HashSet<>();
        for (int y = minimum.getBlockY(); y <= maximum.getBlockY(); y++) {
            for (int x = minimum.getBlockX(); x <= maximum.getBlockX(); x++) {
                blocks.add(A.getWorld().getBlockAt(x, y, minimum.getBlockZ()));
                blocks.add(A.getWorld().getBlockAt(x, y, maximum.getBlockZ()));
            }
            for (int z = minimum.getBlockZ(); z <= maximum.getBlockZ(); z++) {
                blocks.add(A.getWorld().getBlockAt(minimum.getBlockX(), y, z));
                blocks.add(A.getWorld().getBlockAt(maximum.getBlockX(), y, z));
            }
        }
        return blocks;
    }
	
	public static List<Block> blocks(Location L1, Location L2){
		
		List<Block> blocks = new ArrayList<Block>();
		
		World W = L1.getWorld();
		
		int MINX = Math.min(L1.getBlockX(), L2.getBlockX()),
		MINY = Math.min(L1.getBlockY(), L2.getBlockY()),
		MINZ = Math.min(L1.getBlockZ(), L2.getBlockZ()),
		MAXX = Math.max(L1.getBlockX(), L2.getBlockX()),
		MAXY = Math.max(L1.getBlockY(), L2.getBlockY()),
		MAXZ = Math.max(L1.getBlockZ(), L2.getBlockZ());
		for(int x = MINX; x <= MAXX; x++){
			for(int y = MINY; y <= MAXY; y++){
				for(int z = MINZ; z <= MAXZ; z++){
					Block b = W.getBlockAt(x, y, z);
					blocks.add(b);
				}
			}
		}
		
		return blocks;
	}
	
	public static List<Block> blocks(Location L1, Location L2, boolean ignoreAir){
		
		List<Block> blocks = new ArrayList<Block>();
		
		World W = L1.getWorld();
		
		int MINX = Math.min(L1.getBlockX(), L2.getBlockX()),
		MINY = Math.min(L1.getBlockY(), L2.getBlockY()),
		MINZ = Math.min(L1.getBlockZ(), L2.getBlockZ()),
		MAXX = Math.max(L1.getBlockX(), L2.getBlockX()),
		MAXY = Math.max(L1.getBlockY(), L2.getBlockY()),
		MAXZ = Math.max(L1.getBlockZ(), L2.getBlockZ());
		for(int x = MINX; x <= MAXX; x++){
			for(int y = MINY; y <= MAXY; y++){
				for(int z = MINZ; z <= MAXZ; z++){
					Block b = W.getBlockAt(x, y, z);
					if(!ignoreAir)
					{
						blocks.add(b);
					}
					else
					{
						if(!b.getType().isAir())
						{
							blocks.add(b);
						}
					}
				}
			}
		}
		
		return blocks;
	}
	
}
