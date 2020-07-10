package net.fairsquare.bungeewhitelist.commands;

import net.fairsquare.bungeewhitelist.models.Dependant;
import net.fairsquare.bungeewhitelist.models.Whitelist;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

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
