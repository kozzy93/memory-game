import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class MemoryGame {
    public static void main(String[] args) throws IOException {

        String[] allWords = DataConverter.readTxtFile("src/Words.txt");
        String[] easyWords = new String[8];
        String[] hardWords = new String[16];
        boolean[] showOrNotEasy = new boolean[8];
        boolean[] showOrNotHard = new boolean[16];
        int howManyTrials = 10;
        int howManyTrialsHard = 15;
        Scanner scanner = new Scanner(System.in);
        String firstWord = null;
        String secondWord = null;
        int firstNumber = 0;
        int seccondNumber = 0;
        boolean checkIfWin = false;
        long actualTime = System.currentTimeMillis();
        int howManyAttempts = 0;
        PrintWriter saveGame = new PrintWriter(new FileWriter("bestScores.txt", true));
        Date nowDate = new Date();


        int width = 150;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("MEMORY", 10, 20);
        System.out.println();


        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {

                sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
        }

        System.out.println("Choose the difficulty, if you want to play in easy mode, type 'easy', if you want try a hard mode type 'hard'");

        String fourthScanner = scanner.nextLine();
        switch (fourthScanner) {
            case "easy":

                //Wypełnianie tablicy Stringów losowymi danymi - wersja łatwa
                System.out.println("Type AX or BX (X - value between 0 to 3)");
                System.out.print("   0 1 2 3");
                System.out.println();
                System.out.print("A: ");

                for (int i = 0; i < showOrNotEasy.length; i++) {
                    System.out.print("X ");
                    if (i == showOrNotEasy.length / 2 - 1) {
                        System.out.println();
                        System.out.print("B: ");
                    }
                }

                fillTheData(allWords, easyWords);

                //losowanie położenia słów w tablicach
                randomizeArray(easyWords);

                do {
                    String firstScanner = scanner.nextLine();
                    if (!firstScanner.equals("A0") && !firstScanner.equals("A1") && !firstScanner.equals("A2") && !firstScanner.equals("A3") && !firstScanner.equals("B0") && !firstScanner.equals("B1") && !firstScanner.equals("B2") && !firstScanner.equals("B3"))
                        try {
                            throw new Exception("Wprowadzono niewłaściwe dane");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                    System.out.println();
                    System.out.println("LEVEL: EASY");
                    System.out.println("You have got " + howManyTrials + " trials left");
                    System.out.println();

                    // Zmiana parametru false/true do wyświetlania słowa po wskazaniu przez użytkownika - wiersz pierwszy
                    for (int i = 0; i < (easyWords.length / 2); i++) {
                        if (firstScanner.equals("A" + i)) {
                            showOrNotEasy[i] = true;
                            firstWord = easyWords[i];
                            firstNumber = i;
                            //Wywietlanie słowa
                            showWordsEasy(easyWords, showOrNotEasy);

                        }
                    }

                    // Zmiana parametru false/true do wyświetlania słowa po wskazaniu przez użytkownika - wiersz drugi
                    for (int i = 0; i < (easyWords.length / 2); i++) {
                        if (firstScanner.equals("B" + i)) {
                            showOrNotEasy[i + (easyWords.length / 2)] = true;
                            firstWord = easyWords[i + (easyWords.length / 2)];
                            firstNumber = i + (easyWords.length / 2);
                            //Wywietlanie słowa
                            showWordsEasy(easyWords, showOrNotEasy);
                        }

                    }
                    //Pobieranie drugiego argmunetu

                    String secondScanner = scanner.nextLine();
                    if ((!secondScanner.equals("A0") && !secondScanner.equals("A1") && !secondScanner.equals("A2") && !secondScanner.equals("A3") && !secondScanner.equals("B0") && !secondScanner.equals("B1") && !secondScanner.equals("B2") && !secondScanner.equals("B3")) || secondScanner.equals(firstScanner))
                        try {
                            throw new Exception("Wprowadzono niewłaściwe dane");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }
                    for (int i = 0; i < (easyWords.length / 2); i++) {
                        if (secondScanner.equals("A" + i)) {
                            showOrNotEasy[i] = true;
                            secondWord = easyWords[i];
                            seccondNumber = i;
                            howManyTrials = showOrNotEasyMethod(easyWords, showOrNotEasy, howManyTrials);
                        }
                    }

                    for (int i = 0; i < (easyWords.length / 2); i++) {
                        if (secondScanner.equals("B" + i)) {
                            showOrNotEasy[i + easyWords.length / 2] = true;
                            secondWord = easyWords[i + easyWords.length / 2];
                            seccondNumber = i + easyWords.length / 2;
                            howManyTrials = showOrNotEasyMethod(easyWords, showOrNotEasy, howManyTrials);
                        }

                        //Sprawdzenie warunków do wyświetlenia

                    }
                    if (firstWord.equals(secondWord)) {
                        showOrNotEasy[firstNumber] = true;
                        showOrNotEasy[seccondNumber] = true;
                        howManyTrials++;
                        firstWord = "DiffValue1";
                        secondWord = "DiffValue2";
                        firstNumber = 123;
                        seccondNumber = 213;

                    } else {
                        showOrNotEasy[firstNumber] = false;
                        showOrNotEasy[seccondNumber] = false;
                        firstNumber = 56123;
                        seccondNumber = 6213;
                        firstWord = "DiffValue7";
                        secondWord = "DiffValue8";
                    }
                    for (boolean condition :
                            showOrNotEasy) {
                        checkIfWin = false;
                        if (condition == false) break;
                        else checkIfWin = true;
                    }

                    howManyAttempts++;

                    if (checkIfWin == true) {
                        long finishTime = System.currentTimeMillis() - actualTime;
                        System.out.println();
                        System.out.println("Congratulations, you have won! You solved the memory game after " + howManyAttempts + " chances. It took you " + finishTime / 1000 + " seconds. ");
                        System.out.println("Type your name to save your score");
                        System.out.println();

                        String fiveScanner = scanner.nextLine();
                        saveGame.println("Name: " + fiveScanner);
                        saveGame.println("Date: " + nowDate);
                        saveGame.println("Attempts: " + howManyAttempts);
                        saveGame.println("Finish time: " + finishTime / 1000 + " sec");
                        saveGame.println("Mode: EASY");
                        saveGame.println();
                        saveGame.close();

                        System.out.println("If you would like to restart the game input here 'restart'");
                        String thirdScanner = scanner.nextLine();
                        howManyTrials = restartGameEasy(easyWords, showOrNotEasy, howManyTrials, thirdScanner);
                    }

                    if (howManyTrials == 0) {
                        System.out.println();
                        System.out.println("Unfortunately, your attempts are over, if you want to restart the game input here 'restart'");
                        String thirdScanner = scanner.nextLine();
                        howManyTrials = restartGameEasy(easyWords, showOrNotEasy, howManyTrials, thirdScanner);
                    }
                }
                while (howManyTrials > 0);
                break;
            /*/---------------------HARD MODE-------------------------------------------------------------------/*/
            case "hard":

                System.out.println("Type AX or BX (X - value between 0 to 7)");
                System.out.print("   0 1 2 3 4 5 6 7");
                System.out.println();
                System.out.print("A: ");
                for (int i = 0; i < showOrNotHard.length; i++) {
                    System.out.print("X ");
                    if (i == showOrNotHard.length / 2 - 1) {
                        System.out.println();
                        System.out.print("B: ");
                    }
                }

                //Wypełnianie tablicy Stringów losowymi danymi - wersja trudna
                fillTheData(allWords, hardWords);

                //losowanie położenia słów w tablicach
                randomizeArray(hardWords);

                do {
                    String firstScanner = scanner.nextLine();
                    if (!firstScanner.equals("A0") && !firstScanner.equals("A1") && !firstScanner.equals("A2") && !firstScanner.equals("A3") && !firstScanner.equals("A4") && !firstScanner.equals("A5") && !firstScanner.equals("A6") && !firstScanner.equals("A7") && !firstScanner.equals("B0") && !firstScanner.equals("B1") && !firstScanner.equals("B2") && !firstScanner.equals("B3") && !firstScanner.equals("B4") && !firstScanner.equals("B5") && !firstScanner.equals("B6") && !firstScanner.equals("B7"))
                        try {
                            throw new Exception("Wprowadzono niewłaściwe dane");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }

                    System.out.println();
                    System.out.println("LEVEL: HARD");
                    System.out.println("You have got " + howManyTrialsHard + " trials left");
                    System.out.println();

                    // Zmiana parametru false/true do wyświetlania słowa po wskazaniu przez użytkownika - wiersz pierwszy
                    for (int i = 0; i < (hardWords.length / 2); i++) {
                        if (firstScanner.equals("A" + i)) {
                            showOrNotHard[i] = true;
                            firstWord = hardWords[i];
                            firstNumber = i;
                            //Wyswietlanie słowa
                            showWordsHard(hardWords, showOrNotHard);

                        }

                    }
                    // Zmiana parametru false/true do wyświetlania słowa po wskazaniu przez użytkownika - wiersz drugi
                    for (int i = 0; i < (hardWords.length / 2); i++) {
                        if (firstScanner.equals("B" + i)) {
                            showOrNotHard[i + (hardWords.length / 2)] = true;
                            firstWord = hardWords[i + (hardWords.length / 2)];
                            firstNumber = i + (hardWords.length / 2);
                            //Wywietlanie słowa
                            showWordsHard(hardWords, showOrNotHard);
                        }

                    }
                    String secondScanner = scanner.nextLine();

                    if ((!secondScanner.equals("A0") && !secondScanner.equals("A1") && !secondScanner.equals("A2") && !secondScanner.equals("A3") && !secondScanner.equals("A4") && !secondScanner.equals("A5") && !secondScanner.equals("A6") && !secondScanner.equals("A7") && !secondScanner.equals("B0") && !secondScanner.equals("B1") && !secondScanner.equals("B2") && !secondScanner.equals("B3") && !secondScanner.equals("B4") && !secondScanner.equals("B5") && !secondScanner.equals("B6") && !secondScanner.equals("B7")) || secondScanner.equals(firstScanner))
                        try {
                            throw new Exception("Wprowadzono niewłaściwe dane");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(0);
                        }

                    //Pobieranie drugiego argmunetu
                    for (int i = 0; i < (hardWords.length / 2); i++) {
                        if (secondScanner.equals("A" + i)) {
                            showOrNotHard[i] = true;
                            secondWord = hardWords[i];
                            seccondNumber = i;

                            howManyTrialsHard = showOrNotHardMethod(hardWords, showOrNotHard, howManyTrialsHard);
                        }
                    }

                    for (int i = 0; i < (hardWords.length / 2); i++) {
                        if (secondScanner.equals("B" + i)) {
                            showOrNotHard[i + hardWords.length / 2] = true;
                            secondWord = hardWords[i + hardWords.length / 2];
                            seccondNumber = i + hardWords.length / 2;
                            howManyTrialsHard = showOrNotHardMethod(hardWords, showOrNotHard, howManyTrialsHard);
                        }

                        //Sprawdzenie warunków do wyświetlenia

                    }
                    if (firstWord.equals(secondWord)) {
                        showOrNotHard[firstNumber] = true;
                        showOrNotHard[seccondNumber] = true;
                        howManyTrialsHard++;
                        firstWord = "DiffValue1";
                        secondWord = "DiffValue2";
                        firstNumber = 123;
                        seccondNumber = 213;

                    } else {
                        showOrNotHard[firstNumber] = false;
                        showOrNotHard[seccondNumber] = false;
                        firstNumber = 456;
                        seccondNumber = 567;
                        firstWord = "DiffValue7";
                        secondWord = "DiffValue8";
                    }
                    for (boolean condition :
                            showOrNotHard) {
                        checkIfWin = false;
                        if (condition == false) break;
                        else checkIfWin = true;
                    }

                    howManyAttempts++;

                    if (checkIfWin == true) {
                        long finishTime = System.currentTimeMillis() - actualTime;
                        System.out.println();
                        System.out.println("Congratulations, you have won! You solved the memory game after " + howManyAttempts + " chances. It took you " + finishTime / 1000 + " seconds. ");
                        System.out.println("Type your name to save your score");
                        System.out.println();

                        String fiveScanner = scanner.nextLine();
                        saveGame.println("Name: " + fiveScanner);
                        saveGame.println("Date: " + nowDate);
                        saveGame.println("Attempts: " + howManyAttempts);
                        saveGame.println("Finish time: " + finishTime / 1000 + " sec");
                        saveGame.println("Mode: HARD");
                        saveGame.println();
                        saveGame.close();

                        System.out.println("If you would like to restart the game input here 'restart'");

                        String thirdScanner = scanner.nextLine();
                        howManyTrialsHard = restartGameHard(hardWords, showOrNotHard, howManyTrialsHard, thirdScanner);
                    }

                    if (howManyTrialsHard == 0) {
                        System.out.println();
                        System.out.println("Unfortunately, your attempts are over, if you want to restart the game input here 'restart'");
                        String thirdScanner = scanner.nextLine();
                        howManyTrialsHard = restartGameHard(hardWords, showOrNotHard, howManyTrialsHard, thirdScanner);
                    }
                }
                while (howManyTrialsHard > 0);
                break;
        }
    }

    private static void fillTheData(String[] allWords, String[] hardWords) {
        for (int i = 0; i < (hardWords.length / 2); i++) {
            Random random = new Random();
            int a = random.nextInt(allWords.length);
            hardWords[i] = allWords[a];
            hardWords[i + (hardWords.length / 2)] = allWords[a];
        }
    }

    public static String[] randomizeArray(String[] array) {

        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomPosition = random.nextInt(array.length);
            String word = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = word;
        }
        return array;
    }

    private static void showWordsEasy(String[] easyWords, boolean[] showOrNotEasy) {
        for (int j = 0; j < easyWords.length; j++) {
            if (showOrNotEasy[j] == true) {
                System.out.print(easyWords[j] + " ");
            } else System.out.print("X ");
            if (j == 3) {
                System.out.println();
            }
        }
    }

    private static int showOrNotEasyMethod(String[] easyWords, boolean[] showOrNotEasy, int howManyTrials) {
        showWordsEasy(easyWords, showOrNotEasy);
        howManyTrials--;
        return howManyTrials;
    }

    private static int restartGameEasy(String[] easyWords, boolean[] showOrNotEasy, int howManyTrials, String thirdScanner) {
        switch (thirdScanner) {
            case "restart":
                howManyTrials = 10;
                randomizeArray(easyWords);
                for (int i = 0; i < showOrNotEasy.length; i++) {
                    showOrNotEasy[i] = false;
                }
                break;
            default:
                System.exit(0);
        }
        return howManyTrials;
    }


    private static void showWordsHard(String[] hardWords, boolean[] showOrNotHard) {
        for (int j = 0; j < hardWords.length; j++) {
            if (showOrNotHard[j] == true) {
                System.out.print(hardWords[j] + " ");
            } else System.out.print("X ");
            if (j == (hardWords.length / 2) - 1) {
                System.out.println();
            }
        }
    }

    private static int showOrNotHardMethod(String[] hardWords, boolean[] showOrNotHard, int howManyTrialsHard) {
        showWordsHard(hardWords, showOrNotHard);
        howManyTrialsHard--;
        return howManyTrialsHard;
    }

    private static int restartGameHard(String[] hardWords, boolean[] showOrNotHard, int howManyTrialsHard, String thirdScanner) {
        switch (thirdScanner) {
            case "restart":
                howManyTrialsHard = 15;
                randomizeArray(hardWords);
                for (int i = 0; i < showOrNotHard.length; i++) {
                    showOrNotHard[i] = false;
                }
                break;
            default:
                System.exit(0);
        }
        return howManyTrialsHard;
    }


}
