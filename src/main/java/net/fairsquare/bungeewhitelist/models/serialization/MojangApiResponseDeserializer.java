package net.fairsquare.bungeewhitelist.models.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.fairsquare.bungeewhitelist.BungeeWhitelist;
import net.fairsquare.bungeewhitelist.models.MojangApiResponse;
import net.fairsquare.bungeewhitelist.utils.UuidUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Jackson deserializer class that turns a response from Mojang's UUID endpoint into a nice Java
 * object. Also has some validations to prevent odd crashes from happening.
 *
 * @author McJeffr
 */
public class MojangApiResponseDeserializer extends JsonDeserializer<MojangApiResponse> {

    @Override
    public MojangApiResponse deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        JsonNode node = parser.readValueAsTree();

        if (!node.has("name") || !node.has("id")) {
            BungeeWhitelist.getPlugin().getLogger().log(Level.WARNING,
                    "Mojang's API returned an invalid response body: " + node.toPrettyString());
            return null;
        }

        String username = extractUsername(node);
        UUID uuid = extractUniqueId(node);
        if (username == null || uuid == null) {
            return null;
        }

        return new MojangApiResponse(username, uuid);
    }

    private String extractUsername(JsonNode node) {
        String username = node.get("name").asText();
        if (!UuidUtil.isValidUsername(username)) {
            BungeeWhitelist.getPlugin().getLogger().log(Level.WARNING,
                    "Mojang's API returned a username that doesn't match the " +
                            "specifications: " + username);
            return null;
        }
        return username;
    }

    private UUID extractUniqueId(JsonNode node) {
        String uuid = node.get("id").asText();
        try {
            return UuidUtil.fromString(uuid);
        } catch (IllegalArgumentException e) {
            BungeeWhitelist.getPlugin().getLogger().log(Level.WARNING,
                    "Mojang's API returned a UUID that doesn't match the " +
                            "specifications: " + uuid);
            return null;
        }
    }

}
