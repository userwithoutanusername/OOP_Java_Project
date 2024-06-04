package command;

import java.util.Scanner;

public class Keyboard {

    private static final Scanner scanner = new Scanner(System.in);

    public Keyboard() {
    }

    public static String readString(String popis, String nazev) {
        System.out.print(popis);
        String temp = scanner.nextLine().trim();
        if (temp == null || temp.equals("")) {
            throw new IllegalArgumentException("Hodnota " + nazev + " nesmi byt null!");
        }
        return temp;
    }

    public int readInt(String popis, String nazev) {
        System.out.print(popis);
        int temp;
        try {
            temp = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hodnota " + nazev + " musi byt celym cislem!");
        }
        return temp;
    }
}
