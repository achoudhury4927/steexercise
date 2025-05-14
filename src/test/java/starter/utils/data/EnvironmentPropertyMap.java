package starter.utils.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum to represent some of the properties file names
 */

@AllArgsConstructor
public enum EnvironmentPropertyMap {

    ENV_LOCAL("","local"),
    ENV_SIT("SIT", "sit");

    @Getter
    private final String environment;

    @Getter
    private final String propertyFile;

}
