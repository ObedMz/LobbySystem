package obed.me.lobbysystem.Objects;

import net.md_5.bungee.api.config.ServerInfo;

import java.util.Comparator;

public class ShortUtil implements Comparator<ServerInfo> {
    @Override
    public int compare(ServerInfo o1, ServerInfo o2) {
        Integer a = o1.getPlayers().size();
        Integer b = o2.getPlayers().size();
        return a.compareTo(b);
    }
}
