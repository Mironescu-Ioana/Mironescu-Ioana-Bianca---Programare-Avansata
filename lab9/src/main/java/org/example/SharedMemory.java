package org.example;

public class SharedMemory {
    private String bunnyLastKnownPosition = "Necunoscuta";

    public synchronized void updateBunnyPosition(int row, int col) {
        bunnyLastKnownPosition = "(" + row + ", " + col + ")";
        System.out.println("\n! Un robot a gasit iepurasul la: " + bunnyLastKnownPosition + " !\n");
    }

    public synchronized String getBunnyPosition() {
        return bunnyLastKnownPosition;
    }
}
