import java.util.Scanner;

public class Penny{
  public static void main(String[] args){
    Scanner scan = new Scanner();
    
    //Placeholder for playerSeq, makes sure that the length is 3
    String playerSeq = "XXX";
    
    while(playerSeq.length() == 3){ 
      //Gets the sequence of tosses from the player
      System.out.println("Enter a sequence of three flips (H-Heads, T-Tails)");
      String playerSeq = scan.nextLine();
    
    }
    
    
    char playerFirst = playerSeq.charAt(0);
    char playerSecond = playerSeq.charAt(1);
    char playerThird = playerSeq.charAt(2);
    
    
  }
}
