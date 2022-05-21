 

package ru.iothub.jef.mcu.core.boards.sunxi;

// https://linux-sunxi.org/GPIO
/**
 * (position of letter in alphabet - 1) * 32 + pin number
 * E.g for PH18 this would be ( 8 - 1) * 32 + 18 = 224 + 18 = 242 (since 'h' is the 8th letter).
 */
public class BallToPin {
    public static int convert(char c, int number) {
        int multiplier;
        switch (c) {
            case 'A':
            case 'a':
                multiplier = 0;
                break;
            case 'B':
            case 'b':
                multiplier = 1;
                break;
            case 'C':
            case 'c':
                multiplier = 2;
                break;
            case 'D':
            case 'd':
                multiplier = 3;
                break;
            case 'E':
            case 'e':
                multiplier = 4;
                break;
            case 'F':
            case 'f':
                multiplier = 5;
                break;
            case 'G':
            case 'g':
                multiplier = 6;
                break;
            case 'H':
            case 'h':
                multiplier = 7;
                break;
            case 'I':
            case 'i':
                multiplier = 8;
                break;
            case 'J':
            case 'j':
                multiplier = 9;
                break;
            case 'K':
            case 'k':
                multiplier = 10;
                break;
            case 'L':
            case 'l':
                multiplier = 11;
                break;
            default:
                throw new RuntimeException("Invalid pin letter '"+c+"'");
        }
        return multiplier * 32 + number;
    }
}
