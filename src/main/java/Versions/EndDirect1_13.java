package Versions;

import com.swagsteve.enddirect.EDFunctions;
import com.swagsteve.enddirect.EndDirect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class EndDirect1_13 implements EDFunctions {

    @Override
    public void runCheck() {
        if (EndDirect.isdragondead) {
            for (Player p : Bukkit.getOnlinePlayers()) {

                double x = p.getLocation().getX();
                double z = p.getLocation().getZ();

                if (p.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL) && p.getLocation().getY() > EndDirect.overworld_tp_y_level) {
                    Location endTp = new Location(Bukkit.getWorld(EndDirect.end), x, EndDirect.end_tp_to, z);
                    p.teleport(endTp);
                } else if (p.getLocation().getWorld().getEnvironment().equals(World.Environment.THE_END) && p.getLocation().getY() < EndDirect.end_tp_y_level) {
                    Location ovwTp = new Location(Bukkit.getWorld(EndDirect.overworld), x, EndDirect.overworld_tp_to, z);
                    p.teleport(ovwTp);
                }
            }
        }
    }
}