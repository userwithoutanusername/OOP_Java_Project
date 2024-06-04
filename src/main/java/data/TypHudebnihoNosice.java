package data;

public enum TypHudebnihoNosice {
    CD ("cd"),
    KAZETA("kazeta"),
    VINYLOVA_DESKA("vinylova deska");

    private String typ;

    TypHudebnihoNosice(String typ) {
        this.typ = typ;
    }

    public static TypHudebnihoNosice fromString(String i) {
        switch(i) {
            case "cd":
                return CD;
            case "kazeta":
                return KAZETA;
            case "vinylova deska":
                return VINYLOVA_DESKA;
            default:
                throw new IllegalArgumentException("Neplatny typ hudebniho nosice!");
        }
    }

    public String getTyp() {
        return typ;
    }

    @Override
    public String toString() {
        return typ;
    }
}
