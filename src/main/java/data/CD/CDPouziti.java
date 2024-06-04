package data.CD;

public enum CDPouziti {
    AUDIO ("audio"),
    VIDEO ("video"),
    DATA ("data");

    private String format;

    CDPouziti(String format) {
        this.format = format;
    }

    public static CDPouziti fromString(String i) {
        switch(i) {
            case "audio":
                return AUDIO;
            case "video":
                return VIDEO;
            case "data":
                return DATA;
            default:
                throw new IllegalArgumentException("Nesprávný typ formátu: " + i);
        }

    }

    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return format;
    }
}
