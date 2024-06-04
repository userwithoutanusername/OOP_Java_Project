package data.CD;

public enum CDVyrobci {
    PHILIPS ("philips"),
    SONY("sony");

    private String vyrobec;

    CDVyrobci(String vyrobec) {
        this.vyrobec = vyrobec;
    }

    public static CDVyrobci fromString(String i) {
        switch (i) {
            case "philips":
                return PHILIPS;
            case "sony":
                return SONY;
            default:
                throw new IllegalArgumentException("Nesprávný název výrobce: " + i);
        }
    }

    public String getVyrobec() {
        return vyrobec;
    }

    @Override
    public String toString() {
        return vyrobec;
    }
}
