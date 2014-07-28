package me.Matti.Sjabloon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class Utilities {
	public static int round(int number, int multiple){
		int newNumber = number;

		while(newNumber % multiple != 0){
			newNumber += 1;
		}

		return newNumber;
	}

	/*public static void regenerateBlocks(Collection<Block> blocks, final Material type, final byte data, final int blocksPerTime, final long delay, Comparator<Block> comparator){
		final List<Block> orderedBlocks = new ArrayList<Block>();

		orderedBlocks.addAll(blocks);

		if(comparator != null){
			Collections.sort(orderedBlocks, comparator);
		}

		final int size = orderedBlocks.size();

		if(size > 0){
			new BukkitRunnable(){
				int index = size - 1;

				@Override
				public void run(){
					for(int i = 0; i < blocksPerTime; i++){
						if(index >= 0){
							final Block block = orderedBlocks.get(index);

							regenerateBlock(block, type, data);

							index -= 1;
						} else {
							this.cancel();
							return;
						}						
					}
				}
			}.runTaskTimer(YTRater.getInstance(), 0L, delay);
		}
	}*/

	@SuppressWarnings("deprecation")
	public static void regenerateBlock(Block block, final Material type, final byte data){
		final Location loc = block.getLocation();
		if(type != Material.AIR && type != null){			
		loc.getWorld().playEffect(loc, Effect.STEP_SOUND, type.getId());
		}
		//block.setTypeIdAndData(type.getId(), data, false);
		
		if(type != Material.AIR && type != null){

		block.setType(type);
		
		block.setData(data);
		
		} else {
			block.setType(Material.AIR);
		}
	}

	public static String capitalize(String text){
		String firstLetter = text.substring(0, 1).toUpperCase();
		String next = text.substring(1).toLowerCase();
		String capitalized = firstLetter + next;

		return capitalized;
	}
}