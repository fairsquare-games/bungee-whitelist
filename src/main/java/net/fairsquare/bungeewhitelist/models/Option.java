package net.fairsquare.bungeewhitelist.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.md_5.bungee.api.chat.BaseComponent;

/**
 * Abstract class that represents an option of a whitelist entry.
 *
 * @author McJeffr
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OnceOption.class, name = "ONCE")
})
public abstract class Option {

    /* Attributes */
    private OptionType type;

    /**
     * Default constructor.
     *
     * @param type The type of the option.
     */
    public Option(OptionType type) {
        this.type = type;
    }

    /**
     * Returns information about this option.
     *
     * @return Information about this option.
     */
    public abstract BaseComponent[] getInformation();

    /**
     * Getter for the type of the option.
     *
     * @return The type of the option.
     */
    public OptionType getType() {
        return type;
    }

    /**
     * Setter for the type of the option.
     *
     * @param type The type of the option.
     */
    public void setType(OptionType type) {
        this.type = type;
    }

}
