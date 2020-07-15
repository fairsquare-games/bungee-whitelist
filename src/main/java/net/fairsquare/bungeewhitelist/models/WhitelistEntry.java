package net.fairsquare.bungeewhitelist.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

/**
 * This class represents a single entry on the whitelist.
 *
 * @author McJeffr
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhitelistEntry {

    /* Attributes */
    private UUID uuid;
    private String username;
    private Option option;

    /**
     * Empty constructor.
     */
    public WhitelistEntry() {
        this.uuid = null;
        this.username = null;
    }

    /**
     * Default constructor.
     *
     * @param uuid     The UUID of the user.
     * @param username The (last known) username of the user.
     */
    public WhitelistEntry(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    /**
     * Extended constructor.
     *
     * @param uuid     The UUID of the user.
     * @param username The (last known) username of the user.
     * @param option   The additional whitelist option.
     */
    public WhitelistEntry(UUID uuid, String username, Option option) {
        this.uuid = uuid;
        this.username = username;
        this.option = option;
    }

    /**
     * Getter for the UUID of the user.
     *
     * @return The UUID of the user.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Setter for the UUID of the user.
     *
     * @param uuid The UUID of the user.
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Getter for the (last known) username of the user.
     *
     * @return The (last known) username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the (last known) username of the user.
     *
     * @param username The (last known) username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the option of this entry.
     *
     * @return The option of this entry.
     */
    public Option getOption() {
        return option;
    }

    /**
     * Setter for the option of this entry.
     *
     * @param option The option of this entry.
     */
    public void setOptions(Option option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "WhitelistEntry{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                ", option=" + option +
                '}';
    }

}
