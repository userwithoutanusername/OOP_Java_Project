package data.Kazeta;

public enum KazetaTypCivky {
    HLAVNI_CIVKA ("hlavni civka"),
    PRIJIMACI_CIVKA ("prijimaci civka");

    private String typCivky;

    KazetaTypCivky(String typCivky) {
        this.typCivky = typCivky;
    }

    public static KazetaTypCivky fromString(String i) {
        switch (i) {
            case "hlavni civka":
                return HLAVNI_CIVKA;
            case "prijimaci civka":
                return PRIJIMACI_CIVKA;
            default:
                throw new IllegalArgumentException("Nesprávný typ civky: " + i);
        }
    }

    public String getTypCivky() {
        return typCivky;
    }

    @Override
    public String toString() {
        return typCivky;
    }
}
