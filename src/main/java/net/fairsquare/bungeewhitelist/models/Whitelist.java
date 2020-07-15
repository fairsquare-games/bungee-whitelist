package net.fairsquare.bungeewhitelist.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the whitelist that is loaded in from the configuration files.
 *
 * @author McJeffr
 */
public class Whitelist {

    /* Attributes */
    private boolean enabled;
    private Map<UUID, WhitelistEntry> entries;

    /**
     * Empty constructor.
     */
    public Whitelist() {
        this.enabled = false;
        this.entries = new HashMap<>();
    }

    /**
     * Default constructor.
     *
     * @param enabled Boolean value that indicates whether or not the whitelist is enabled.
     * @param entries The entries on the whitelist.
     */
    public Whitelist(boolean enabled, Map<UUID, WhitelistEntry> entries) {
        this.enabled = enabled;
        this.entries = entries;
    }

    /**
     * Getter for the boolean value that indicates whether or not the whitelist is enabled.
     *
     * @return The boolean value that indicates whether or not the whitelist is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter for the boolean value that indicates whether or not the whitelist is enabled.
     *
     * @param enabled The boolean value that indicates whether or not the whitelist is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Finds a whitelist entry using the provided username.
     *
     * @param username The username to search for.
     * @return The whitelist entry if it was found, null if none was found.
     */
    public WhitelistEntry findEntry(String username) {
        for (WhitelistEntry entry : entries.values()) {
            if (entry.getUsername().equals(username)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Getter for the entries on this whitelist. Each entry specifies a player that is whitelisted.
     *
     * @return The entries on this whitelist.
     */
    public Map<UUID, WhitelistEntry> getEntries() {
        return entries;
    }

    /**
     * Gets an entry on the whitelist based on its UUID. If there is no entry with the provided
     * UUID, it returns null instead.
     *
     * @param uuid The UUID of the entry to fetch.
     * @return The entry if found, null otherwise.
     */
    public WhitelistEntry getEntry(UUID uuid) {
        return entries.get(uuid);
    }

    /**
     * Returns a boolean value indicated whether or not the provided UUID is whitelisted.
     *
     * @param uuid The UUID to check.
     * @return True if this UUID is whitelisted, false if it is not.
     */
    public boolean isWhitelisted(UUID uuid) {
        return getEntry(uuid) != null;
    }

    /**
     * Adds an entry to the list of entries. If there is already an entry with the same UUID, it
     * will be overwritten. If there is not, the provided entry will be added to the list.
     *
     * @param entry The entry to add.
     */
    public void addEntry(WhitelistEntry entry) {
        entries.put(entry.getUuid(), entry);
    }

    /**
     * Removes an entry from the list of entries. If there exists no entry for the given UUID, no
     * action will be taken. If there is, that entry will be removed from the
     * list.
     *
     * @param entry The entry to remove.
     */
    public void removeEntry(WhitelistEntry entry) {
        entries.remove(entry.getUuid());
    }

    /**
     * Setter for the entries on this whitelist. Each entry specifies a player that is whitelisted.
     *
     * @param entries The entries on this whitelist.
     */
    public void setEntries(Map<UUID, WhitelistEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "Whitelist{" +
                "enabled=" + enabled +
                ", entries=" + entries +
                '}';
    }

}
