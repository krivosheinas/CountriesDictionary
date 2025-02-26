package extensions;

import interfaces.ISource;

import java.util.UUID;

public class SourceName implements ISource {
    public String name;
    public UUID uuid = UUID.randomUUID();

    public String getString() {
        return null;
    }

    public String convertToString() {
        return null;
    }
}
