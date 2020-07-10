package net.fairsquare.bungeewhitelist;

import net.fairsquare.bungeewhitelist.commands.WhitelistCommand;
import net.fairsquare.bungeewhitelist.listeners.JoinListener;
import net.fairsquare.bungeewhitelist.models.Dependant;
import net.fairsquare.bungeewhitelist.models.Whitelist;
import net.fairsquare.bungeewhitelist.utils.ConfigUtil;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the plugin. Serves as the entry point where the plugin starts executing from.
 *
 * @author McJeffr
 */
public final class BungeeWhitelist extends Plugin {

    /* Attributes */
    private static BungeeWhitelist plugin;

    private List<Dependant<Whitelist>> dependants;

    /**
     * Getter for the plugin instance.
     *
     * @return The plugin instance.
     */
    public static BungeeWhitelist getPlugin() {
        return plugin;
    }

    /**
     * Updates all dependants with the provided whitelist.
     *
     * @param whitelist The whitelist to pass on to all the dependants.
     */
    public void updateWhitelist(Whitelist whitelist) {
        for (Dependant<Whitelist> dependant : dependants) {
            dependant.inject(whitelist);
        }
    }

    @Override
    public void onEnable() {
        plugin = this;

        /* Create all of the commands and listeners */
        Whitelist whitelist = ConfigUtil.loadWhitelist();

        JoinListener joinListener = new JoinListener(whitelist);
        WhitelistCommand whitelistCommand = new WhitelistCommand(whitelist);

        dependants = new ArrayList<>();
        dependants.add(joinListener);
        dependants.add(whitelistCommand);

        /* Register the commands and listeners */
        getProxy().getPluginManager().registerListener(this, joinListener);
        getProxy().getPluginManager().registerCommand(this, whitelistCommand);
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

}
