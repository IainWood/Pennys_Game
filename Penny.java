/**
 * Created by Iain Woodburn on 23-Jul-16
 */
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Penny{

    //Timer variables used to calculate the elapsed time of the game
    private static long startTime;
    private static long endTime;

    //Used to calculate seconds and minutes from milliseconds
    private static final int MILLIS_IN_SEC = 1000;
    private static final int SEC_IN_MIN = 60;

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) throws InterruptedException{
        Scanner scanString = new Scanner(System.in);
        Scanner scanInteger = new Scanner(System.in);

        //Placeholder for playAgain, makes sure that the answer is yes
        String playAgain = "y";

        //Repeats while user answers 'yes' or 'y' to playing again, yes by default
         while(playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y")) {

            String playerSeq = "XXX";
            String cancelOperation;
            boolean notDone = true;
            int numOfGames = 0;
            int numOfWins = 0;
            int playerScore = 0;
            int computerScore = 0;
            int playerWins = 0;
            int computerWins = 0;

            //Continues until the player enters a string of length 3
            //and all of the characters are of type 'T' or 'H'
            while (playerSeq.length() == 3 && notDone) {

                try {
                    //Gets the sequence of tosses from the player
                    System.out.println("Enter a sequence of three coin flips (H-Heads, T-Tails)");
                    playerSeq = scanString.next();
                    if (!verifyString(playerSeq)) {
                        System.err.println("--ERROR: make sure that you type a sequence of three letters,\nonly 'H's or 'T's with no spaces.--");

                        //Makes sure that the above line is printed out BEFORE continuing
                        Thread.sleep(10);
                        playerSeq = "XXX";
                        notDone = true;
                        continue;
                    } //Resets the loop if string is dirty

                    System.out.println("First player to reach how many wins(1 is recommended, as this will provide the most accurate results in the fastest time:");
                    numOfWins = scanString.nextInt();
                    if (!verifyInteger(numOfWins)) {
                        System.err.println("--ERROR: make sure that the number is positive--");

                        //Makes sure that the above line is printed out BEFORE continuing
                        Thread.sleep(10);
                        notDone = true;
                        continue;
                    } //Resets the loop if the int is dirty

                    System.out.println("First player to win how many games:");
                    numOfGames = scanInteger.nextInt();

                    //Between 1 million and 100 million iterations
                    if(numOfGames > 1000000 && numOfGames < 100000000){
                        System.err.println("WARNING: this calculation will take some time\n" +
                                           "it is not recommended. Would you like to continue(y/n)?");

                        //Makes sure that the above line is printed out BEFORE continuing
                        Thread.sleep(10);
                        cancelOperation = scanString.next();
                        if(!cancelOperation.equalsIgnoreCase("y")){
                            continue ;
                        }
                        //Between 100 million and 1 billion iterations
                    }else if(numOfGames >= 100000000 && numOfGames < 1000000000){
                        System.err.println("WARNING: this calculation will take considerable time\n" +
                                           "it is not recommended. Would you like to continue(y/n)?");

                        //Makes sure that the above line is printed out BEFORE continuing
                        Thread.sleep(10);
                        cancelOperation = scanString.next();
                        if(!cancelOperation.equalsIgnoreCase("y")){
                            continue ;
                        }
                        //Excess of 1 billion
                    }else if(numOfGames >= 1000000000){
                        System.err.println("WARNING: this calculation will take a very long time,(excess of 10 minutes if win limit is 1)\n" +
                                           "it is highly not recommended. Would you like to continue(y/n)?");

                        //Makes sure that the above line is printed out BEFORE continuing
                        Thread.sleep(10);
                        cancelOperation = scanString.next();

                        if(cancelOperation.equalsIgnoreCase("n") || cancelOperation.equalsIgnoreCase("no")){
                            continue ;
                        }

                        System.err.println("ARE YOU SURE!");
                        cancelOperation = scanString.next();
                        if(cancelOperation.equalsIgnoreCase("n") || cancelOperation.equalsIgnoreCase("no")){
                            continue ;
                        }
                    }

                    if (!verifyInteger(numOfGames)) {
                        System.err.println("--ERROR: make sure that the number is positive--");
                        notDone = true;
                        continue;
                    }
                } catch (InputMismatchException missMatch) {
                    System.err.println("--ERROR: make sure that you type letters for the first question\nand numbers for the next two--");
                }

                //Converts to uppercase and trims, everything should be uppercase past this point
                playerSeq = playerSeq.trim();
                playerSeq = playerSeq.toUpperCase();

                //All input should be sanitized by now, set notDone to true to allow program to continue
                notDone = false;
            }//end while

            //Creates a computer sequence that beats the player sequence,
            //passes first character and second character only, third is not needed
            String computerSeq = calculateBetterSeq(playerSeq.charAt(0), playerSeq.charAt(1));
            System.out.println("Computer chooses sequence: " + computerSeq);
            boolean done = false;
            String randomSeq = "";

            //Gets current time, in milliseconds. Used to calculate elapsed time later in statistics
            startTime = System.currentTimeMillis();
            while (!done) {

                //Adds a new, random flip onto the end of the random sequence
                randomSeq = randomSeq.concat(generateRandomFlip());

                //Shortens the string to 3 characters, removing the first
                //and keeping the last one added
                if (randomSeq.length() == 4) {
                    randomSeq = randomSeq.substring(1);
                }

                if (randomSeq.length() == 3) {

                    //Checks if a player has won
                    if (randomSeq.equals(playerSeq)) {
                        playerScore++;
                    } else if (randomSeq.equals(computerSeq)) {
                        computerScore++;
                    }

                }

                //Adds a win to either the player or computer
                if (playerScore == numOfWins) {
                    playerWins++;

                    //Resets string of flips and player and computer score to 0
                    randomSeq = "";
                    playerScore = 0;
                    computerScore = 0;
                } else if (computerScore == numOfWins) {
                    computerWins++;

                    //Resets string of flips and player and computer score to 0
                    randomSeq = "";
                    playerScore = 0;
                    computerScore = 0;
                }

                if (playerWins == numOfGames || computerWins == numOfGames) {
                    endTime = System.currentTimeMillis();
                    //Prints the results to the screen
                    printStats(playerWins, computerWins);

                    //Stops the loop once either side has reached 10 wins
                    done = true;
                }

            }//end while

            System.out.println("Would you like to run again(y/n)?");
            playAgain = scanString.next();
        }//end while

    }//end main

    /**
     * Verifies that the imputed string is valid
     *
     * @param playerSeq - The sequence of flips that the user defined
     * @return true if string is valid (i.e. contains just T or H)
     */
    private static boolean verifyString(String playerSeq){

        if(playerSeq.length() == 3) {
            char playerFirst = playerSeq.charAt(0);
            char playerSecond = playerSeq.charAt(1);
            char playerThird = playerSeq.charAt(2);

            //Checks all characters, to see if they are at least 'T' or 'H'
            return !((playerFirst != 'T' && playerFirst != 'H')   ||
                    (playerSecond != 'T' && playerSecond != 'H') ||
                    (playerThird != 'T' && playerThird != 'H'));
        }else{
            return false;
        }

    }//end verifyString

    /**
     * Verifies that the imputed integer is valid
     *
     * @param num - the int that the user tried to enter
     * @return true if int is valid (i.e. not negative)
     */
    private static boolean verifyInteger(int num){
        return num > 0;
    }//end verifyInteger

    /**
     * Converts the user's series of flips to one that can beat it
     *
     * @param first - the first flip of the user's three
     * @param second - the second flip of the user's three
     * @return an altered version of the user's strings
     */
    private static String calculateBetterSeq(char first, char second){

        //Keeps the original second character, and makes a new one to be transformed
        char newSecond;

        //Transforms the second character into whatever it is not
        if(second == 'T'){
            newSecond = 'H';
        }else{
            newSecond = 'T';
        }
        //Takes the second character, flips it, and puts it on the front, the third is discarded
        return "" + newSecond + first + second;
    }//end calculateBetterSeq

    /**
     * Generates a random Heads or Tails to play the game from
     *
     * @return H if the random number is 0, T if it is 1
     */
    private static String generateRandomFlip(){
        Random rand = new Random();

        //Random number from 0 to 1, which will be converted into a char
        int numFlip = rand.nextInt(2);
        //If num is 0, H(Heads) is returned, if 1, then T(Tails) is returned
        if(numFlip == 0){
            return "H";
        }else{
            return "T";
        }

    }//end generateRandomFlip

    /**
     * Prints the results after playing the game
     *
     * @param playerWins - number of times the user has won
     * @param computerWins - number of times the computer has won
     */
    private static void printStats(int playerWins, int computerWins){
        NumberFormat formatter = new DecimalFormat("#0.0000");
        int totalGames = playerWins + computerWins;
        long elapsedTime = endTime - startTime;

        //Intentional integer division to get whole number
        //MILLIS_IN_SEC = 1000
        int seconds = (int)elapsedTime / MILLIS_IN_SEC;

        //Intentional integer division to get whole number
        //SEC_IN_MIN = 60
        int minutes = seconds / SEC_IN_MIN;

        if(seconds > SEC_IN_MIN){
            seconds %= SEC_IN_MIN;
        }

        int milliseconds = (int)elapsedTime - ((seconds * MILLIS_IN_SEC) + (minutes * SEC_IN_MIN));

        System.out.println("---Results---");
        System.out.println("Total games: " + totalGames);
        System.out.println("Player game wins: " + playerWins);
        System.out.println("Computer game wins: " + computerWins);
        System.out.println("Player won " + formatter.format((double) playerWins / totalGames) + "%");
        System.out.println("Computer won " + formatter.format((double)computerWins/totalGames) + "%");
        System.out.println("Elapsed time: \nMinutes: " + minutes + "\nSeconds: " + seconds + "\nMilliseconds: " + milliseconds);
    }

}//end class
