package com.whenuhackunoob.magictp.commands;

import com.whenuhackunoob.magictp.MagicTP;
import com.whenuhackunoob.magictp.util.Format;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class RtpCommand implements CommandExecutor {

    private final MagicTP plugin;

    public RtpCommand(MagicTP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        FileConfiguration config = plugin.configuration.getConfiguration();

        int minx = config.getInt("coordinates.x.min");
        int maxx = config.getInt("coordinates.x.max");

        int miny = config.getInt("coordinates.y.min");
        int maxy = config.getInt("coordinates.y.max");

        int minz = config.getInt("coordinates.z.min");
        int maxz = config.getInt("coordinates.z.max");

        int x = (int) generateNumber(minx, maxx);
        int y = (int) generateNumber(miny, maxy);
        int z = (int) generateNumber(minz, maxz);

        // World stuff
        Block randomLocBlock = null;
        if(config.getBoolean("world.usecurrent")) {
            randomLocBlock = Bukkit.getWorld(player.getWorld().getName()).getBlockAt(x, y, z);
        } else {
            randomLocBlock = Bukkit.getWorld(config.getString("world.world")).getBlockAt(x, y, z);
        }

        player.sendMessage(randomLocBlock.toString());

        String block = "GRASS_BLOCK";
        if (getVersion() <= 1122) block = "GRASS";

        Material grass = Material.valueOf(block);

        Block blockBelow = null;
        if(config.getBoolean("world.usecurrent")) {
            blockBelow = Bukkit.getWorld(player.getWorld().getName()).getBlockAt(x, y-1, z);
        } else {
            blockBelow = Bukkit.getWorld(config.getString("world.world")).getBlockAt(x, y-1, z);
        }

        if(config.getBoolean("safety.check.enabled")) {
            if(config.getString("safety.check.type").equalsIgnoreCase("air")) {
                if (randomLocBlock.getType() == Material.AIR) {
                    Location loc = new Location(player.getWorld(), x, y, z);
                    player.sendMessage(Format.color("&aLocation found! Teleporting..."));
                    if (config.getBoolean("safety.block.place")) {
                        blockBelow.setType(grass);
                    }
                    player.teleport(loc);
                } else {
                    player.sendMessage(Format.color("&cCould not find safe location! Please try again."));
                }
            }
        } else {
            Location loc = new Location(player.getWorld(), x, y, z);
            player.sendMessage(Format.color("&aLocation found! Teleporting..."));
            player.teleport(loc);
        }

        return true;
    }

    public double generateNumber(int max, int min) {
        return Math.random() * (max - min) + min;
    }

    public static int getVersion() {
        String[] ver = Bukkit.getServer().getVersion().split(" ");
        String version = ver[ver.length - 1].replace(")", "").replace(".", "");
        return Integer.parseInt(version);
    }
}
