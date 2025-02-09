package net.fairsquare.bungeewhitelist.commands;

import net.fairsquare.bungeewhitelist.BungeeWhitelist;
import net.fairsquare.bungeewhitelist.models.*;
import net.fairsquare.bungeewhitelist.utils.ConfigUtil;
import net.fairsquare.bungeewhitelist.utils.UuidUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.*;

/**
 * This class implements the /whitelist command that is used to manage the whitelist.
 *
 * @author McJeffr
 */
public class WhitelistCommand extends Command implements Dependant<Whitelist> {

    /* Constants */
    private static final String COMMAND = "whitelist";
    private static final String PERMISSION = "fairsquare.whitelist";

    /* Attributes */
    private Whitelist whitelist;

    /**
     * Default constructor.
     *
     * @param whitelist The whitelist.
     */
    public WhitelistCommand(Whitelist whitelist) {
        super(COMMAND, PERMISSION);
        this.whitelist = whitelist;
    }

    @Override
    public void inject(Whitelist whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            Message.NO_PERMISSION.send(sender);
            return;
        }

        if (args.length == 0) {
            helpCommand(sender, args);
            return;
        }

        switch (args[0]) {
            case "help":
                helpCommand(sender, args);
                break;
            case "status":
                statusCommand(sender, args);
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
            case "show":
                showCommand(sender, args);
                break;
            case "on":
                enableCommand(sender, args);
                break;
            case "off":
                disableCommand(sender, args);
                break;
            case "reload":
                reloadCommand(sender, args);
                break;
            default:
                Message.UNKNOWN_SUBCOMMAND.send(sender, args[0]);
        }
    }

    /**
     * Displays help (and status) about the plugin.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void helpCommand(CommandSender sender, String[] args) {
        Message.SEPARATOR.send(sender);

        Message.PLUGIN_DESCRIPTION.send(sender,
                BungeeWhitelist.getPlugin().getDescription().getVersion());
        Message.AMOUNT_WHITELISTED.send(sender, whitelist.getEntries().size());
        if (whitelist.isEnabled()) {
            Message.STATUS.send(sender, Message.ENABLED.getText());
        } else {
            Message.STATUS.send(sender, Message.DISABLED.getText());
        }

        Message.EMPTY_LINE.send(sender);

        Message.AVAILABLE_COMMANDS.send(sender);
        Message.HELP_COMMAND.send(sender);
        Message.STATUS_COMMAND.send(sender);
        Message.ADD_COMMAND.send(sender);
        Message.REMOVE_COMMAND.send(sender);
        Message.LIST_COMMAND.send(sender);
        Message.SHOW_COMMAND.send(sender);
        Message.ENABLE_COMMAND.send(sender);
        Message.DISABLE_COMMAND.send(sender);
        Message.RELOAD_COMMAND.send(sender);

        Message.SEPARATOR.send(sender);
    }

    /**
     * Displays status of the whitelist.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void statusCommand(CommandSender sender, String[] args) {
        if (whitelist.isEnabled()) {
            Message.STATUS.send(sender, Message.ENABLED.getText());
        } else {
            Message.STATUS.send(sender, Message.DISABLED.getText());
        }
    }

    /**
     * Adds a new user to the whitelist.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void addCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            Message.NOT_ENOUGH_ARGUMENTS.send(sender);
            Message.ADD_COMMAND.send(sender);
            return;
        }

        /* Parse the option from the arguments */
        Option option;
        try {
            option = parseOption(args);
        } catch (IllegalArgumentException e) {
            Message.INVALID_OPTION.send(sender, args[2]);
            return;
        }

        String username = args[1];

        /* Fetch the UUID in async as we don't want to block the main thread with network IO */
        final BungeeWhitelist plugin = BungeeWhitelist.getPlugin();
        Message.RETRIEVING_UUID.send(sender, username);
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            UUID uuid = UuidUtil.retrieveUniqueId(username);
            if (uuid == null) {
                Message.INVALID_USERNAME.send(sender, username);
                return;
            }
            Message.RETRIEVED_UUID.send(sender, uuid.toString(), username);

            if (whitelist.getEntries().containsKey(uuid)) {
                Message.ALREADY_WHITELISTED.send(sender, username);
                return;
            }

            whitelist.addEntry(new WhitelistEntry(uuid, username, option));
            plugin.updateWhitelist(whitelist);

            Message.ADDED_USER.send(sender, username);
        });
    }

    /**
     * Removes a user from the whitelist.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void removeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            Message.NOT_ENOUGH_ARGUMENTS.send(sender);
            Message.REMOVE_COMMAND.send(sender);
            return;
        }

        String username = args[1];
        WhitelistEntry entry = whitelist.findEntry(username);
        if (entry == null) {
            Message.NOT_WHITELISTED.send(sender, username);
            return;
        }

        whitelist.removeEntry(entry);
        BungeeWhitelist.getPlugin().updateWhitelist(whitelist);
        Message.REMOVED_USER.send(sender, username);
    }

    /**
     * Lists all whitelisted users.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void listCommand(CommandSender sender, String[] args) {
        Collection<WhitelistEntry> entries = whitelist.getEntries().values();
        Message.WHITELIST_ENTRIES.send(sender, entries.size());

        StringBuilder sb = new StringBuilder(Message.PREFIX.getText());
        Iterator<WhitelistEntry> it = entries.iterator();
        while (it.hasNext()) {
            WhitelistEntry entry = it.next();
            if (entry.getOption() == null) {
                sb.append(Message.WHITELIST_ENTRY.getText(entry.getUsername()));
            } else {
                sb.append(Message.WHITELIST_ENTRY_OPTION.getText(entry.getUsername()));
            }
            if (it.hasNext()) {
                sb.append(Message.WHITELIST_ENTRY_SEPARATOR.getText());
            }
        }

        sender.sendMessage(TextComponent.fromLegacyText(sb.toString()));
    }

    /**
     * Enables the whitelist.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void enableCommand(CommandSender sender, String[] args) {
        if (whitelist.isEnabled()) {
            Message.WHITELIST_ALREADY_STATUS.send(sender, Message.ENABLED.getText());
            return;
        }
        whitelist.setEnabled(true);
        BungeeWhitelist.getPlugin().updateWhitelist(whitelist);
        Message.WHITELIST_STATUS_CHANGE.send(sender, Message.ENABLED.getText());
    }

    /**
     * Disables the whitelist.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void disableCommand(CommandSender sender, String[] args) {
        if (!whitelist.isEnabled()) {
            Message.WHITELIST_ALREADY_STATUS.send(sender, Message.DISABLED.getText());
            return;
        }
        whitelist.setEnabled(false);
        BungeeWhitelist.getPlugin().updateWhitelist(whitelist);
        Message.WHITELIST_STATUS_CHANGE.send(sender, Message.DISABLED.getText());
    }

    /**
     * Reloads the whitelist.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void reloadCommand(CommandSender sender, String[] args) {
        Whitelist whitelist = ConfigUtil.loadWhitelist();
        BungeeWhitelist.getPlugin().updateWhitelist(whitelist);
        Message.RELOADED.send(sender);
    }

    /**
     * Shows information about a whitelisted player.
     *
     * @param sender The sender of the command.
     * @param args   The arguments of the command.
     */
    private void showCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            Message.NOT_ENOUGH_ARGUMENTS.send(sender);
            Message.SHOW_COMMAND.send(sender);
            return;
        }

        String username = args[1];
        WhitelistEntry entry = whitelist.findEntry(username);
        if (entry == null) {
            Message.NOT_WHITELISTED.send(sender, username);
            return;
        }

        Message.SHOW_OPTIONS.send(sender, username);
        if (entry.getOption() == null) {
            Message.SHOW_NO_OPTIONS.send(sender);
        } else {
            sender.sendMessage(entry.getOption().getInformation());
        }
    }

    /**
     * Parses the correct option instance from the given command arguments, or null if no valid
     * option could be parsed.
     *
     * @param args The arguments of the command.
     * @return An option instance derived from the provided arguments, or null if no valid option
     * could be derived.
     */
    private Option parseOption(String[] args) throws IllegalArgumentException {
        if (args.length < 3) {
            return null;
        }

        String optionName = args[2];
        OptionType type = OptionType.valueOf(optionName.toUpperCase());

        switch (type) {
            case ONCE: {
                return new OnceOption();
            }
        }

        return null;
    }

}
