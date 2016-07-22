import java.util.Scanner;

public class Penny{
  
  public static void main(String[] args){
    Scanner scan = new Scanner();
    
    //Placeholder for playerSeq, makes sure that the length is 3
    String playerSeq = "XXX";
    boolean sanitized = false;
    
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
    
  }//end main
  
  public static String calculateBetterSeq(String seq){
    
  }//end calculateBetterSeq
  
}//end class
