package net.fairsquare.bungeewhitelist.listeners;

import net.fairsquare.bungeewhitelist.models.Dependant;
import net.fairsquare.bungeewhitelist.models.Whitelist;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
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

        if (!whitelist.isWhitelisted(uuid)) {
            player.disconnect(new TextComponent(ChatColor.RED +
                    "You are not whitelisted on this server"));
        }
    }

}
