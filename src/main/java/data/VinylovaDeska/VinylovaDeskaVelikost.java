package data.VinylovaDeska;

public enum VinylovaDeskaVelikost {
    VELKA ("velka"), // 12" (300mm)
    STREDNI ("stredni"), // 10" (250mm)
    MALA ("mala"); // 8" (175mm)

    private String velikost;

    VinylovaDeskaVelikost(String velikost) {
        this.velikost = velikost;
    }

    public static VinylovaDeskaVelikost fromString(String i) {
        switch (i) {
            case "velka":
                return VELKA;
            case "stredni":
                return STREDNI;
            case "mala":
                return MALA;
            default:
                throw new IllegalArgumentException("Nesprávna velikost: " + i);
        }
    }

    public String getVelikost() {
        return velikost;
    }

    @Override
    public String toString() {
        return velikost;
    }
}
