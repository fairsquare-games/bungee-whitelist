package net.fairsquare.bungeewhitelist.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.fairsquare.bungeewhitelist.BungeeWhitelist;
import net.fairsquare.bungeewhitelist.models.MojangApiResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

/**
 * This class contains several utilities for dealing with UUIDs.
 *
 * @author McJeffr
 */
public class UuidUtil {

    /* Constants */
    private static final String BASE_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final String USERNAME_REGEX = "[a-zA-Z0-9_]{1,16}";

    /**
     * Retrieves a UUID for the provided username.
     *
     * @param username The username to retrieve the UUID for.
     * @return The UUID for the provided username, if it exists. Null otherwise.
     */
    public static UUID retrieveUniqueId(String username) {
        BungeeWhitelist plugin = BungeeWhitelist.getPlugin();
        if (!isValidUsername(username)) {
            plugin.getLogger().log(Level.WARNING, "Provided username '" + username +
                    "' does not match Mojang's specifications.");
            return null;
        }

        ObjectMapper om = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL + username).build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body == null || body.contentLength() == 0) {
                return null;
            }
            return om.readValue(body.byteStream(), MojangApiResponse.class).getUniqueId();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE,
                    "Could not send call to Mojang's API to fetch UUID from a playername", e);
        }

        return null;
    }

    /**
     * Checks whether or not a provided username is valid according to the username specifications.
     *
     * @param username The username to check.
     * @return True if the username is valid, false if it is not.
     */
    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    /**
     * Converts a string to a UUID. This string can either contain a UUID with or without dashes.
     *
     * @param uuid The string to convert to a UUID.
     * @return A UUID object if it could be converted from the provided string, null if the string
     * was invalid.
     */
    public static UUID fromString(String uuid) {
        if (uuid == null) {
            return null;
        }
        uuid = uuid.trim();
        try {
            return uuid.length() == 32
                    ? fromTrimmed(uuid.replaceAll("-", ""))
                    : UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            BungeeWhitelist.getPlugin().getLogger().log(Level.WARNING,
                    "Could not convert string '" + uuid + "' to UUID", e);
            return null;
        }
    }

    /**
     * Retrieves a UUID from a trimmed uuid string.
     *
     * @param uuid The string to convert to a UUID.
     * @return The UUID object if it could be converted, null otherwise.
     * @throws IllegalArgumentException If the UUID has an invalid format.
     */
    private static UUID fromTrimmed(String uuid) throws IllegalArgumentException {
        if (uuid == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(uuid);
        try {
            builder.insert(20, "-");
            builder.insert(16, "-");
            builder.insert(12, "-");
            builder.insert(8, "-");
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }

        return UUID.fromString(builder.toString());
    }

}
