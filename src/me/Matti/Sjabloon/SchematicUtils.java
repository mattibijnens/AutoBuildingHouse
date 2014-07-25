package me.Matti.Sjabloon;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;




import net.minecraft.server.v1_7_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R3.NBTTagCompound;

import org.bukkit.Bukkit;

public class SchematicUtils {
	private static File baseSchematicsFile;

	private static List<Schematic> allSchematics = new ArrayList<Schematic>();

	public static void initFile(File baseFile){
		baseSchematicsFile = baseFile;
	}

	public static void initSchematics(){
		allSchematics.clear();

		for(File schematicFile : baseSchematicsFile.listFiles()){
			if(!(schematicFile.getName().startsWith("."))){
				Schematic schematic = loadScheamtic(schematicFile);

				if(schematic != null){
					allSchematics.add(schematic);
				}
			}
		}
	}

	public static List<Schematic> getAllSchematics(){
		return allSchematics;
	}
	public static Schematic loadScheamtic(File f){  
		  try{
		   FileInputStream fis = new FileInputStream(f);
		   NBTTagCompound nbt = NBTCompressedStreamTools.a(fis);

		   short width = nbt.getShort("Width");
		   short height = nbt.getShort("Height");
		   short length = nbt.getShort("Length");
		   String materials = nbt.getString("Materials");
		   byte[] blocks = nbt.getByteArray("Blocks");
		   byte[] data = nbt.getByteArray("Data");
		   
		   fis.close();

		   Schematic schematic = new Schematic(f.getName().replace(".schematic", ""), width, height, length, materials, blocks, data);

			return schematic;
		  }
		  catch(Exception e){
			  Bukkit.broadcastMessage("Failed to load the schematic: " + f.getAbsolutePath());
			  e.printStackTrace();
		  }
		return null;
		 }
		
		
	/*public static Schematic loadScheamtic(File file){
		try{
			if(file.exists()){
				
				NBTInputStream nbtStream =  new NBTInputStream(new FileInputStream(file));
				CompoundTag compound = (CompoundTag) nbtStream.readTag();
				Map<String, Tag> tags = compound.getValue();
				Short width = ((ShortTag) tags.get("Width")).getValue();
				Short height = ((ShortTag) tags.get("Height")).getValue();
				Short length = ((ShortTag) tags.get("Length")).getValue();

				String materials = ((StringTag) tags.get("Materials")).getValue();

				byte[] blocks = ((ByteArrayTag) tags.get("Blocks")).getValue();
				byte[] data = ((ByteArrayTag) tags.get("Data")).getValue();

				nbtStream.close();

				Schematic schematic = new Schematic(file.getName().replace(".schematic", ""), width, height, length, materials, blocks, data);

				return schematic;
			}
		} catch(Exception e){
			YTRater.getInstance().getLogger().severe("Failed to load the schematic: " + file.getAbsolutePath());
			e.printStackTrace();
		}

		return null;
	}*/
}