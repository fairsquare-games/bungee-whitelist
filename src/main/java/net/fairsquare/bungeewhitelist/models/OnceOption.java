package net.fairsquare.bungeewhitelist.models;

import net.md_5.bungee.api.chat.BaseComponent;

/**
 * This class represents the option where someone is only allowed to join the server once. This
 * means that if they are whitelisted with this option added, they will automatically be removed
 * from the whitelist as soon as they join the server.
 *
 * @author McJeffr
 */
public class OnceOption extends Option {

    public OnceOption() {
        super(OptionType.ONCE);
    }

    @Override
    public BaseComponent[] getInformation() {
        return Message.SHOW_ONCE_OPTION.getTextComponent();
    }

}
