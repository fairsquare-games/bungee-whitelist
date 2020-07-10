package net.fairsquare.bungeewhitelist.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.fairsquare.bungeewhitelist.BungeeWhitelist;
import net.fairsquare.bungeewhitelist.models.Whitelist;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * This class contains some utilities for dealing with the configuration file of this plugin.
 *
 * @author McJeffr
 */
public class ConfigUtil {

    private static final String CONFIG_FILE = "config.json";

    public static void createDefaultConfig() {
        BungeeWhitelist plugin = BungeeWhitelist.getPlugin();

        /* Create the data folder if it does not exist */
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdir()) {
                plugin.getLogger().log(Level.SEVERE,
                        "Data folder for plugin does not exist and could not be created.");
            }
        }

        /* Check if the configuration file exists. If it does, we don't have to do anything. */
        File file = getConfigFile();
        if (file.exists()) {
            return;
        }

        /* If the configuration file does not exist, we create the default one */
        try {
            Whitelist whitelist = new Whitelist(false, new HashMap<>());
            ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            om.writeValue(file, whitelist);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not create " + CONFIG_FILE, e);
        }
    }

    public static Whitelist loadWhitelist() {
        createDefaultConfig();

        File config = getConfigFile();
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(config, Whitelist.class);
        } catch (IOException e) {
            BungeeWhitelist.getPlugin().getLogger().log(Level.SEVERE,
                    "Could not read " + CONFIG_FILE, e);
            return null;
        }
    }

    public static void saveWhitelist(Whitelist whitelist) {
        createDefaultConfig();

        File config = getConfigFile();
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            om.writeValue(config, whitelist);
        } catch (IOException e) {
            BungeeWhitelist.getPlugin().getLogger().log(Level.SEVERE,
                    "Could not save to " + CONFIG_FILE, e);
        }
    }

    private static File getConfigFile() {
        return new File(BungeeWhitelist.getPlugin().getDataFolder() +
                File.separator + CONFIG_FILE);
    }

}
