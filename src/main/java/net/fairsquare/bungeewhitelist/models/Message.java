package net.fairsquare.bungeewhitelist.models;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public enum Message {

    PREFIX("&8[]&r "),
    EMPTY_LINE(""),
    SEPARATOR("&8--- --- --- --- --- --- --- --- ---"),
    KICK_MESSAGE("&cYou are not whitelisted on this server"),
    NO_PERMISSION("&cYou don't have permission to execute this command."),
    PLUGIN_DESCRIPTION("&eFairSquare Bungee Whitelist plugin, version &6%1$s&e."),
    NOT_ENOUGH_ARGUMENTS("&cNot enough arguments provided. Usage:"),
    UNKNOWN_SUBCOMMAND("&eUnknown subcommand &6%1$s&e. Use &6/whitelist help &eto see all available commands."),
    AVAILABLE_COMMANDS("&eAvailable commands (&8<&6arg&8> &e= required, &8[&6arg&8] &e= optional):"),
    HELP_COMMAND("&8- &6/whitelist help &edisplays help about this plugin."),
    ADD_COMMAND("&8- &6/whitelist add &8<&6username&8> &eadds a user to the whitelist."),
    REMOVE_COMMAND("&8- &6/whitelist remove &8<&6username&8> &eremoves a user from the whitelist."),
    STATUS_COMMAND("&8- &6/whitelist status &edisplays the status of the whitelist."),
    LIST_COMMAND("&8- &6/whitelist list &elists all whitelisted users."),
    ENABLE_COMMAND("&8- &6/whitelist enable &eenables the whitelist."),
    DISABLE_COMMAND("&8- &6/whitelist disable &edisables the whitelist."),
    RELOAD_COMMAND("&8- &6/whitelist reload &ereloads the whitelist from config."),
    STATUS("&eThe whitelist is currently %1$s&e."),
    ENABLED("&2enabled"),
    DISABLED("&4disabled"),
    RETRIEVING_UUID("&eRetrieving UUID for username &6%1$s&e..."),
    RETRIEVED_UUID("&eRetrieved UUID &6%1$s&e for username &6%2$s&e."),
    ADDED_USER("&aAdded user &2%1$s &ato the whitelist."),
    ALREADY_WHITELISTED("&cUser &4%1$s &cis already whitelisted."),
    INVALID_USERNAME("&cCould not retrieve UUID for username &4%1$s&c. There might be no player with that username."),
    REMOVED_USER("&aRemoved user &2%1$s &afrom the whitelist."),
    NOT_WHITELISTED("&cNo user with username &4%1$s &cfound on the whitelist. Did you spell it correctly, including correct capital letters?"),
    AMOUNT_WHITELISTED("&eThere are currently &6%1$s &eusers whitelisted."),
    WHITELIST_ENTRY("&6%1$s"),
    WHITELIST_ENTRY_SEPARATOR("&8, "),
    WHITELIST_STATUS_CHANGE("&eThe whitelist is now %1$s&e."),
    WHITELIST_ALREADY_STATUS("&cThe whitelist is already %1$s&c."),
    RELOADED("&eReloaded the plugin from the configuration file.");

    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getText(Object... args) {
        return ChatColor.translateAlternateColorCodes('&',
                String.format(this.text, args));
    }

    public BaseComponent[] getTextComponent(Object... args) {
        return TextComponent.fromLegacyText(getText(args));
    }

    public void send(CommandSender target, Object... args) {
        target.sendMessage(TextComponent.fromLegacyText(Message.PREFIX.getText() + getText(args)));
    }

}
