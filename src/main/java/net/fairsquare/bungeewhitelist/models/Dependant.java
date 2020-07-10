package net.fairsquare.bungeewhitelist.models;

/**
 * Little dependency-injection interface that allows for storing dependents in a list instead of
 * having to store all of them as their own types.
 *
 * @param <T> The type that is being depended on.
 * @author McJeffr
 */
public interface Dependant<T> {

    /**
     * Injects the dependant
     *
     * @param dependant The depended type to inject.
     */
    void inject(T dependant);

}
