package data.VinylovaDeska;

public enum VinylovaDeskaRychlost {
    RYCHLE (45), // 45 (otáčky za minutu)
    POMALU (33); // 33 1/3 (otáčky za minutu)

    private int rychlost;

    VinylovaDeskaRychlost(int rychlost) {
        this.rychlost = rychlost;
    }

    public static VinylovaDeskaRychlost fromString(int i) {
        switch (i) {
            case 45:
                return RYCHLE;
            case 33:
                return POMALU;
            default:
                throw new IllegalArgumentException("Nesprávna rychlost: " + i);
        }
    }

    public int getRychlost() {
        return rychlost;
    }

    @Override
    public String toString() {
        return String.valueOf(rychlost);
    }
}
