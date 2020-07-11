package net.fairsquare.bungeewhitelist.commands;

import net.fairsquare.bungeewhitelist.BungeeWhitelist;
import net.fairsquare.bungeewhitelist.models.Dependant;
import net.fairsquare.bungeewhitelist.models.Whitelist;
import net.fairsquare.bungeewhitelist.models.WhitelistEntry;
import net.fairsquare.bungeewhitelist.utils.ConfigUtil;
import net.fairsquare.bungeewhitelist.utils.UuidUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;
import java.util.logging.Level;

/**
 * This class implements the /whitelist command that is used to manage the whitelist.
 *
 * @author McJeffr
 */
public class WhitelistCommand extends Command implements Dependant<Whitelist> {

    /* Attributes */
    private Whitelist whitelist;

    /**
     * Default constructor.
     *
     * @param whitelist The whitelist.
     */
    public WhitelistCommand(Whitelist whitelist) {
        super("whitelist");
        this.whitelist = whitelist;
    }

    @Override
    public void inject(Whitelist whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            helpCommand(sender, args);
            return;
        }

        switch (args[0]) {
            case "help":
                helpCommand(sender, args);
                break;
            case "add":
                addCommand(sender, args);
                break;
            case "remove":
                removeCommand(sender, args);
                break;
            case "list":
                listCommand(sender, args);
                break;
            case "on":
                enableCommand(sender, args);
                break;
            case "off":
                disableCommand(sender, args);
                break;
            default:
                sendMessage(sender, "Unknown subcommand '" + args[0] +
                        "'. Use /whitelist help to see all available commands.");
        }
    }

    private void helpCommand(CommandSender sender, String[] args) {
        sendMessage(sender, "help stuff goes here");
    }

    private void addCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sendMessage(sender, "not enough arguments yadieya");
            return;
        }

        String username = args[1];

        /* Fetch the UUID in async as we don't want to block the main thread with network IO */
        final BungeeWhitelist plugin = BungeeWhitelist.getPlugin();
        sendMessage(sender, "Retrieving UUID for " + username + "...");
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            UUID uuid = UuidUtil.retrieveUniqueId(username);
            if (uuid == null) {
                plugin.getLogger().log(Level.WARNING, "Could not retrieve UUID: null");
                return;
            }
            sendMessage(sender, "Retrieved UUID: " + uuid.toString() +
                    " for username " + username);
            whitelist.addEntry(new WhitelistEntry(uuid, username));
            ConfigUtil.saveWhitelist(whitelist);
            plugin.updateWhitelist(whitelist);
        });
    }

    private void removeCommand(CommandSender sender, String[] args) {

    }

    private void listCommand(CommandSender sender, String[] args) {

    }

    private void enableCommand(CommandSender sender, String[] args) {

    }

    private void disableCommand(CommandSender sender, String[] args) {

    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(new TextComponent(message));
    }

}
