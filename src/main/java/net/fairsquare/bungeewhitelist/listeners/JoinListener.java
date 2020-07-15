package net.fairsquare.bungeewhitelist.listeners;

import net.fairsquare.bungeewhitelist.BungeeWhitelist;
import net.fairsquare.bungeewhitelist.models.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

/**
 * This class contains the listener that listens for players joining. If someone is trying to join
 * that isn't on the whitelist, they are forcefully disconnected.
 *
 * @author McJeffr
 */
public class JoinListener implements Listener, Dependant<Whitelist> {

    /* Attributes */
    private Whitelist whitelist;

    /**
     * Default constructor.
     *
     * @param whitelist The whitelist.
     */
    public JoinListener(Whitelist whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void inject(Whitelist whitelist) {
        this.whitelist = whitelist;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (whitelist.isWhitelisted(uuid)
                && !whitelist.getEntry(uuid).getUsername().equals(player.getName())) {
            whitelist.getEntry(uuid).setUsername(player.getName());
            BungeeWhitelist.getPlugin().updateWhitelist(whitelist);
        }

        if (!whitelist.isEnabled()) {
            return;
        }

        if (!whitelist.isWhitelisted(uuid)) {
            player.disconnect(Message.KICK_MESSAGE.getTextComponent());
            return;
        }

        WhitelistEntry entry = whitelist.getEntry(uuid);
        if (entry.getOption() != null && entry.getOption().getType() == OptionType.ONCE) {
            whitelist.removeEntry(entry);
            BungeeWhitelist.getPlugin().updateWhitelist(whitelist);
        }
    }

}
