package net.fairsquare.bungeewhitelist.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.fairsquare.bungeewhitelist.models.serialization.MojangApiResponseDeserializer;

import java.util.UUID;

/**
 * This class represents a little container that holds data returned from the Mojang API regarding
 * UUIDs and usernames.
 *
 * @author McJeffr
 */
@JsonDeserialize(using = MojangApiResponseDeserializer.class)
public class MojangApiResponse {

    /* Attributes */
    private final String username;
    private final UUID uniqueId;

    /**
     * Default constructor.
     *
     * @param username The username of a player.
     * @param uniqueId The UUID of a player.
     */
    public MojangApiResponse(String username, UUID uniqueId) {
        this.username = username;
        this.uniqueId = uniqueId;
    }

    /**
     * Getter for the username of a player.
     *
     * @return The username of a player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the UUID of a player.
     *
     * @return The UUID of a player.
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String toString() {
        return "MojangApiResponse{" +
                "username='" + username + '\'' +
                ", uuid=" + uniqueId.toString() +
                '}';
    }

}
