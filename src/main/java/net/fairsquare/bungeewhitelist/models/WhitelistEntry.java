package net.fairsquare.bungeewhitelist.models;

import java.util.UUID;

/**
 * This class represents a single entry on the whitelist.
 *
 * @author McJeffr
 */
public class WhitelistEntry {

    /* Attributes */
    private UUID uuid;
    private String username;

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

    @Override
    public String toString() {
        return "WhitelistEntry{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                '}';
    }

}
