import java.util.Scanner;
import java.util.Random;

public class Penny{
  
  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    
    //Placeholder for playerSeq, makes sure that the length is 3
    String playerSeq = "XXX";
    boolean sanitized = false;
    int playerScore = 0;
    int computerScore = 0;
    int playerWins = 0;
    int computerWins = 0;
    
    //Continues until the player enters a string of length 3
    //and all of the characters are of type 'T' or 'H'
    while(playerSeq.length() == 3 && !sanitized){ 
      //Gets the sequence of tosses from the player
      System.out.println("Enter a sequence of three flips (H-Heads, T-Tails)");
      String playerSeq = scan.nextLine();
      
      //Converts to uppercase, everything should be uppercase past this point
      playerSeq = playerSeq.toUppercase();
      
      char playerFirst = playerSeq.charAt(0);
      char playerSecond = playerSeq.charAt(1);
      char playerThird = playerSeq.charAt(2);
      
      //Checks first character, to see if it is at least a 'T' or 'H'
      if(playerFirst != 'T' && playerFirst != 'H'){
        sanitized = false;
        continue;
      }else{
        sanitized = true;
      }
      
      //Checks second character, just like the first
      if(playerSecond != 'T' && playerSecond != 'H'){
        sanitized = false;
        continue;
      }
      
      //Checks third character, just like the first
      if(playerThird != 'T' && playerThird != 'H'){
        sanitized = false;
        continue;
      }
      
    }//end while
    
    //Creates a computer sequence that beats the player sequence
    String computerSeq = calculateBetterSeq(playerFirst, playerSecond, playerThird);
    boolean done = false;
    String randomSeq = "";

    while(!done){

      randomSeq += generateRandomFlip();

      if(queue.size() == 3){
        //Shortens the string to 3 characters, removing the first
        //and keeping the last one added
        randomSeq = randomSeq.substring(1 , randomSeq.length());

        //Checks if a player has won
        if(randomSeq.equals(playerSeq)){
          playerScore++;
        }else if(randomSeq.equals(computerSeq)){
          computerScore++;
        }

      }

      //Adds a win to either the player or computer 
      if(playerScore == 3){
          playerWins++;
      }else if(computerScore == 3){
          computerWins++;
      }
    
  }//end main
  
  public static String calculateBetterSeq(char first, char second, char third){

    if(second == 'T'){
      second = 'H';
    }else{
      second = 'T';
    }
    //Takes the second character, flips it, and puts it on the front
    String result = second + first + third;
    return result;
  }//end calculateBetterSeq

  public static char generateRandomFlip(){
    Random rand = new Random();

    //Random number from 0 to 1, which will be converted into a char
    int numFlip = rand.nextInt(1);

    //If num is 0, H(Heads) is returned, if 1, then T(Tails) is returned
    if(numFlip == 0){
      return 'H';
    }else{
      return 'T';
    }

  }//end generateRandomFlip
  
}//end class
