import java.util.*;
import java.io.*;

/* Jesse Ericksen
   CSCI 405 Summer 2019
   Western Washington University
   July 14 2019

   Assignment 2
   
   REQUIREMENTS: A txt file (as argument), with three strings on separate lines.
   
   INTENT:  1. Find the longest common subsequence of the three strings.
            2. Use dynamic programming to improve the algorithms efficiency 
            
   ALGORITHM & COMPLEXITY: O(n^3)
      Total comparisons depends on the length of each string and consists of 2 steps
         1. Build value table: (n1 * n2 * n3) comparisons, where n is the length of each string 
         2. Check value table for result: n comparisons */
           
 public class Assignment2 {
   public static void main(String[] args){
   
      if(args.length == 0){
         System.out.println("Please specify input file name as command line argument");
         System.exit(0);
      }
      
      try {
         String[] strings = parseFile(args[0]);
         System.out.println("Strings: " + Arrays.toString(strings));
         char[] result = longestSubsequence(strings); //Run Algorithm
         if(result.length > 0){
            System.out.print("Longest Subsequence: ");
            System.out.println(result);
         } else {
            System.out.println("No subsequences exist...");
         }
      } catch(FileNotFoundException e){
         e.printStackTrace();
      }
   }
   
   
   //Performs dynamic algorithm to find longest subsequence
   //in an array of three strings. Returns that subsequence.
   private static char[] longestSubsequence(String[] strings){
      char[] string1, string2, string3; //Char array of each string
      int l1, l2, l3; // Holds length of each string
      
      //Initialize string variables as char arrays for easy data retrieval
      string1 = strings[0].toCharArray(); 
      string2 = strings[1].toCharArray(); 
      string3 = strings[2].toCharArray(); 
      
      //Store length of each string because it will be used repeatedly
      l1 = string1.length;
      l2 = string2.length;
      l3 = string3.length;
     
      //Holds our count for longest subsequence of all three strings     
      int valueTable[][][] = new int[l1 + 1][l2 + 1][l3 + 1];
      //Builds table of values to calculate longest subsequence length
      //Method: Iterate through each string comparing values. When a
      //shared value is found, store 1 + current longest subseq. @
      //index where val was found. Efficiency: O(n^3)
      for(int i = 1; i <= l1; i++){
         for(int j = 1; j <= l2; j++){
            for(int k = 1; k <= l3; k++){
               if(string1[i-1] == string2[j-1] && string3[k-1] == string1[i-1]){
                  valueTable[i][j][k] = valueTable[i-1][j-1][k-1] + 1;
               } else {
                  valueTable[i][j][k] = max(valueTable[i-1][j][k], valueTable[i][j-1][k], valueTable[i][j][k-1]);
               }
            }
         }
      }
      
      //Use table to determine longest subsequence
      //   1. Start at bottom right corner of value table
      //   2. If value to left is equal to value at curr. index: move left; 
      //      else, if the value diagonally is equally, save the letter
      //   3. Repeat until we reach 0 index or 0 value

      int index = valueTable[l1][l2][l3]; //Tracks index of char in result string, back to front
      char resultString[] = new char[index]; //Create a result string using the known length
      
      //Traverse table in backwards order, storing the value at indexes
      //which shared values originated. Store backwards in result array
      while(l1 > 0 && l2 > 0 && l3 > 0 && valueTable[l1][l2][l3] != 0){
         if(valueTable[l1][l2][l3] == valueTable[l1-1][l2][l3]){
            l1--; // move up in table
         } else if(valueTable[l1][l2][l3-1] == valueTable[l1][l2][l3]){
            l3--; //move left in table
         } else { //move diagonally in table
            resultString[index-1] = string3[l3-1];
            index--;
            l1--;
            l2--;
            l3--;
         }      
      }
      return resultString;
   }
   
   //Returns the max of 3 integers
   private static int max(int a, int b, int c){
      if(a >= b && a >= c){
         return a;
      } else 
         if(b >= a && b >= c){
         return b;
      } else {
         return c;
      }
   }
   
   //Checks if file exists and returns string elements in an array
   private static String[] parseFile(String filename) throws FileNotFoundException {
      File stringFile = new File(filename);
      
      if(!stringFile.exists()){
         System.out.println("File: " + filename + " does not exist");
         System.exit(0);
      }
      
      Scanner scanner = new Scanner(stringFile);
      LinkedList<String> list = new LinkedList<String>();
      int i = 0;
      while(scanner.hasNextLine()){
         i++;
         list.add(scanner.nextLine());
      }
      
      if(i < 3){
         System.out.println("Input file must include three line separated strings.");
         System.exit(0);
      }
  
      return list.toArray(new String[list.size()]);
   }
}