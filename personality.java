// Zacharia Kornas
// 05.25.2021
// CSE 142 Section AX
// TA: Paul George Druta
// Take-home Assesment #7: Personality
//
// This program processes the results of the
// Keirsey Temperament Sorter. Input is read
// from a file and the results are printed
// to an output file. 
import java.util.*;
import java.io.*;

public class Personality {
   public static final int DIMENSIONS = 4; // Number of personality dimensions
                                           // we are reading, can only be 4
                                           // used to improve readability of code

   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      intro();
      System.out.print("input file name? ");
      String inputFile = console.nextLine();
      System.out.print("output file name? ");
      String outputFile = console.nextLine();
      Scanner input = new Scanner(new File(inputFile)); // Scanner object used
                                                        // to read input file
      PrintStream output = new PrintStream(new File(outputFile)); // PrintStream object used
                                                                  // to print to output file
      while(input.hasNextLine()) { //loop to process all user responses
         int [] aCount = new int[DIMENSIONS];
         int [] bCount = new int[DIMENSIONS];
         String name = input.nextLine();
         String answer = input.nextLine();
         computeCount(answer, aCount, bCount);
         int[] bPercentage = computePercentage(aCount, bCount);
         String personalityType = computePersonality(bPercentage);
         output.println(name + ": " + Arrays.toString(bPercentage) + " = " + personalityType);
      }
   }
   
   // Prints intro message explaining what the program does
   public static void intro() {
      System.out.println("This program processes a file of answers to the");
      System.out.println("Keirsey Temperament Sorter. It converts the");
      System.out.println("various A and B answers for each person into");
      System.out.println("a sequence of B-percentages and then into a");
      System.out.println("four-letter personality type.");
      System.out.println();
   }
   
   // Computes the number of type B and type A 
   // responses from the user in each dimension 
   // of personality
   // Parameters:
   //    answer - raw responses from the user
   //    aCount - variable used to store number of type A responses from user
   //    bCount - variable used to store number of type B responses from user
   public static void computeCount(String answer, int[] aCount, int[] bCount) {
      for(int i = 0; i < 70; i++) {
         char response = Character.toUpperCase(answer.charAt(i));
         if((i + 7) % 7 == 0) {
            computeResponse(response, aCount, bCount, 0);
         } else if((i + 7) % 7 == 1 || (i + 7) % 7 == 2) {
            computeResponse(response, aCount, bCount, 1);
         } else if((i + 7) % 7 == 3 || (i + 7) % 7 == 4) {
            computeResponse(response, aCount, bCount, 2);
         } else {
            computeResponse(response, aCount, bCount, 3);
         }
      }
   }
   
   // Computes if the response to a question was an A type or B type personality response
   // Parameters:
   //    response - the response given to the question given by the user
   //    aCount - variable used to store number of A responses from the user
   //    bCount - variable used to store number of B responses from the user
   //    dimension - the dimension of personality that the question is testing
   public static void computeResponse(char response, int[] aCount, int[] bCount, int dimension) {
      if(response == 'A') {
         aCount[dimension]++;
      } else if (response == 'B') {
         bCount[dimension]++;
      }
   }
   
   // Calculates percentage of type B responses in each dimension
   // Returns percentage of type B responses in each dimension
   // Parameters:
   //    aCount - number of type A responses in each dimension
   //    number - number of type B responses in each dimension
   public static int[] computePercentage(int[] aCount, int[] bCount) {
      int[] percentage = new int[DIMENSIONS];
      for(int i = 0; i < DIMENSIONS; i++) {
         int total = (aCount[i] + bCount[i]);
         int percent = (int) Math.round((bCount[i] / (double)total) * 100.0);
         percentage[i] = percent;
      }
      return percentage;
   }
   
   // Determines the final personality type of the user
   // Based on the percentage of type B responses
   // Returns personality type
   // Parameters:
   //    bPercentage - percentage of type B responses in each dimension
   public static String computePersonality(int[] bPercentage) {
      String[] aType = {"E", "S", "T", "J"};
      String[] bType = {"I", "N", "F", "P"};
      String type = "";
      for(int i = 0; i < DIMENSIONS; i++) {
         if(bPercentage[i] < 50) {
            type += aType[i];
         } else if(bPercentage[i] > 50) {
            type += bType[i];
         } else {
            type += "X";
         }
      }
      return type;
   }
}
