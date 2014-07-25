package me.Matti.Sjabloon;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class YTRater extends JavaPlugin implements Listener {
	private static YTRater instance;
	private File baseSchematicsFile;
	   private void addClassPath(final URL url) throws IOException {
	        final URLClassLoader sysloader = (URLClassLoader) ClassLoader
	                .getSystemClassLoader();
	        final Class<URLClassLoader> sysclass = URLClassLoader.class;
	        try {
	            final Method method = sysclass.getDeclaredMethod("addURL",
	                    new Class[] { URL.class });
	            method.setAccessible(true);
	            method.invoke(sysloader, new Object[] { url });
	        } catch (final Throwable t) {
	            t.printStackTrace();
	            throw new IOException("Error adding " + url
	                    + " to system classloader");
	        }
	    }
	public void onEnable() {
		
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		
		 try {
	            final File[] libs = new File[] {
	                   
	                    new File(getDataFolder(), "jnbt-1.1.jar") };
	            for (final File lib : libs) {
	                if (!lib.exists()) {
	                    JarUtils.extractFromJar(lib.getName(),
	                            lib.getAbsolutePath());
	                }
	            }
	            for (final File lib : libs) {
	                if (!lib.exists()) {
	                    getLogger().warning(
	                            "There was a critical error loading My plugin! Could not find lib: "
	                                    + lib.getName());
	                    Bukkit.getServer().getPluginManager().disablePlugin(this);
	                    return;
	                }
	                addClassPath(JarUtils.getJarUrl(lib));
	            }
	        } catch (final Exception e) {
	            e.printStackTrace();
	        }
		 baseSchematicsFile = new File(this.getDataFolder() + File.separator + "schematics");
			if(!(baseSchematicsFile.exists())){
				baseSchematicsFile.mkdirs();
			}
			SchematicUtils.initFile(baseSchematicsFile);
			SchematicUtils.initSchematics();
			
			
			log("Loaded " + SchematicUtils.getAllSchematics().size() + " schematic(s)!");
			log("Enabled!");
	}
	
	public static YTRater getInstance(){
		return instance;
	}
	public void log(String msg){
		this.getLogger().info(msg);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("mattibuild")){
			BuildingUtils.initBuildings();
			/*File schematicFile = new File(this.getDataFolder() + File.separator + "schematics");
			Schematic schematic = SchematicUtils.loadScheamtic(schematicFile);
			Player p = (Player) sender;
			Building.build(p.getLocation(), schematic);*/
			BuildingUtils.getAllBuildings();
			BuildingUtils.setBuilding(p, BuildingUtils.getBuilding("worldRFS2!!"));
			Building building = BuildingUtils.getPlayerBuilding(p);
			if(building != null){
				p.sendMessage(ChatColor.GREEN + "Your building is being built!");
				Building.build(p.getLocation().add(0.0D, 1.0D, 0.0D));
			} else {
			
				p.sendMessage(ChatColor.RED + "Select a building first!");
			}
			return true;
		}
	return false;
	}
 
    
}