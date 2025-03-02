package extensions;

import interfaces.ISource;

import java.util.UUID;

public class SourceName implements ISource {
    public String name;
    public UUID uuid = UUID.randomUUID();

    public String getInfo() {
        return null;
    }

    public String packedStr() {
        return null;
    }

    public String getName() { return null; }
}
