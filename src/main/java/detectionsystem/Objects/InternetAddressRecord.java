package detectionsystem.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InternetAddressRecord {

    private InetSocketAddress IP = null;
    private ArrayList<UUID> uuids = new ArrayList<>();
    private HashMap<UUID, Integer> logins = new HashMap<>();
    private String flag = "none";

    public InternetAddressRecord(Player player) {
        setIP(player.getAddress());
        if (!uuids.contains(player.getUniqueId())) {
            uuids.add(player.getUniqueId());
            logins.put(player.getUniqueId(), 1);
        }
    }

    public InetSocketAddress getIP() {
        return IP;
    }

    public int getLogins(UUID uuid) {
        return logins.getOrDefault(uuid, 0);
    }

    public String getFlag() {
        return flag;
    }

    public void setIP(InetSocketAddress newIP) {
        IP = newIP;
    }

    public void setFlag(String s) {
        flag = s;
    }

    public void incrementLogins(Player player) {
        logins.replace(player.getUniqueId(), logins.get(player.getUniqueId()) + 1);

        // TODO: check if two players have logged in more than 3 times, if so flag as probable
    }

    // this only ever gets called when a secondary account logs in using this IP address (primary account added in constructor)
    public void addSecondaryUUID(UUID uuid) {
        if (!uuids.contains(uuid)) {
            uuids.add(uuid);
            logins.put(uuid, 1);
            setFlag("suspected");
            System.out.println(IP.toString() +  " has been flagged as as suspected. There may be multiple accounts using this IP address.");
        }
    }

    public Player getPlayerWithMostLogins() {
        int max = 0;
        Player toReturn = null;
        for (UUID uuid : uuids) {
            if (logins.get(uuid) > max) {
                toReturn = Bukkit.getPlayer(uuid);
            }
        }
        return toReturn;
    }

    public String getSecondaryAccountsFormatted() {

        String toReturn = "";

        Player primary = getPlayerWithMostLogins();

        int counter = 0;
        for (UUID uuid : uuids) {
            if (!uuid.equals(primary.getUniqueId()))
            toReturn = toReturn + Bukkit.getPlayer(uuid).getName() + " [" + getLogins(uuid) + "]";
            counter++;
            if (counter < uuids.size()) {
                toReturn = toReturn + ", ";
            }
        }
        return toReturn;
    }

    public int getNumUUIDS() {
        return uuids.size();
    }
}
