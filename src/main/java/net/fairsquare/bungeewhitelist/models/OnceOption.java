package net.fairsquare.bungeewhitelist.models;

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

}
