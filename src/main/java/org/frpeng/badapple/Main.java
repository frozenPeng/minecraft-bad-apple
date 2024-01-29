package org.frpeng.badapple;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public void badAppleDisplay(Location loc, int width, int height, int index) {
        String file_name = String.format("frame_%d.jpg", index);
        System.out.println(file_name);

        float[][] image = ImageReader.getImage(file_name, width, height);

        for (int w=0; w<width; w++) {
            for (int h=0; h<height; h++) {
                Location loc2 = loc.clone().add(w,0,h);
                if (image[w][h] < 60) {
                    Bukkit.getWorld("world").getBlockAt(loc2).setType(Material.BLACK_CONCRETE);
                } else if (image[w][h] < 120) {
                    Bukkit.getWorld("world").getBlockAt(loc2).setType(Material.GRAY_CONCRETE);
                } else if (image[w][h] < 180) {
                    Bukkit.getWorld("world").getBlockAt(loc2).setType(Material.LIGHT_GRAY_CONCRETE);
                } else {
                    Bukkit.getWorld("world").getBlockAt(loc2).setType(Material.WHITE_CONCRETE);
                }
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Location loc = p.getLocation();
        loc.setYaw((float) 0);
        loc.setPitch((float) 0);

        switch (label) {
            case "badapple":
                Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count <= 13139) {
                            badAppleDisplay(loc, 80, 60, count);
                            count += 3;
                        } else {
                            Bukkit.getScheduler().cancelTasks(Main.this);
                        }
                    }
                }, 0L, 1L);

                break;
        }
        return false;
    }


}
