/**
 * Created by Iain Woodburn on 23-Jul-16.
 */
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Penny{

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

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
                    System.out.println("Enter a sequence of three flips (H-Heads, T-Tails)");
                    playerSeq = scan.nextLine();
                    if (!verifyString(playerSeq)) {
                        System.out.println("--ERROR: make sure that you type a sequence of three letters,\n only 'H's or 'T's with no spaces.--");
                        playerSeq = "XXX";
                        notDone = true;
                        continue;
                    } //Resets the loop if string is dirty

                    System.out.println("First player to reach how many wins:");
                    numOfWins = scan.nextInt();
                    if (!verifyInteger(numOfWins)) {
                        System.out.println("--ERROR: make sure that the number is positive--");
                        notDone = true;
                        continue;
                    } //Resets the loop if the int is dirty

                    System.out.println("First player to win how many games:");
                    numOfGames = scan.nextInt();

                    //Between 1 million and 100 million iterations
                    if(numOfGames > 1000000 && numOfGames < 100000000){
                        System.out.println("WARNING: this calculation will take some time\n" +
                                " it is not recommended. Would you like to continue(y/n)?");
                        cancelOperation = scan.next();
                        if(!cancelOperation.equalsIgnoreCase("y")){
                            continue;
                        }
                    //Between 100 million and 1 billion iterations
                    }else if(numOfGames >= 100000000 && numOfGames < 1000000000){
                        System.out.println("WARNING: this calculation will take considerable time\n" +
                                " it is not recommended. Would you like to continue(y/n)?");
                        cancelOperation = scan.next();
                        if(!cancelOperation.equalsIgnoreCase("y")){
                            continue;
                        }
                    //Excess of 1 billion
                    }else if(numOfGames >= 1000000000){
                        System.out.println("WARNING: this calculation will take a very long time,\n" +
                                " it is highly not recommended. Would you like to continue(y/n)?");
                        cancelOperation = scan.next();
                        System.out.println("ARE YOU SURE!");
                        cancelOperation = scan.next();
                        if(!cancelOperation.equalsIgnoreCase("y")){
                            continue;
                        }
                    }

                    if (!verifyInteger(numOfGames)) {
                        System.out.println("--ERROR: make sure that the number is positive--");
                        notDone = true;
                        continue;
                    }
                } catch (InputMismatchException missMatch) {
                    System.out.println("--ERROR: make sure that you type letters for the first question\nand numbers for the next two--");
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

                    //Prints the results to the screen
                    printStats(playerWins, computerWins);

                    //Stops the loop once either side has reached 10 wins
                    done = true;
                }

            }//end while

            System.out.println("Would you like to run again(y/n)?");
            playAgain = scan.next();
        }//end while

    }//end main

    public static boolean verifyString(String playerSeq){

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

    public static boolean verifyInteger(int num){
        return num > 0;
    }//end verifyInteger

    public static String calculateBetterSeq(char first, char second){

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

    public static String generateRandomFlip(){
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

    public static void printStats(int playerWins, int computerWins){
        NumberFormat formatter = new DecimalFormat("#0.0000");
        int totalGames = playerWins + computerWins;

        System.out.println("Total games: " + totalGames);
        System.out.println("Player game wins: " + playerWins);
        System.out.println("Computer game wins: " + computerWins);
        System.out.println("Player won " + formatter.format((double) playerWins / totalGames) + "%");
        System.out.println("Computer won " + formatter.format((double)computerWins/totalGames) + "%");
    }

}//end class
