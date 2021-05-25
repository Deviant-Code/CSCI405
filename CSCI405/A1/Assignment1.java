import java.util.*;
import java.io.*;


/* Jesse Ericksen
   CSCI 405, Summer 2019
   Western Washington University

   ***Assignment 1***
   REQUIREMENTS:
      This program takes in an txt file (containing a list of space separated integers) as a command line argument.

   INTENT: 
      FIND the sum of the maximum values within every subarray given an array of integers.
   
   ALGORITHM & COMPLEXITY:    
      Using a dynamic approach, it is possible to reduce the time complexity to just O(n)
      The below algorithm is not dependent on the length of the array, it will always run through an array exactly 3 times.
         The first run through calculates the lesser values to the left of each element. 
         The second run through calculates the lesser values to the right of each element. 
         The final run through calculates the total max sum given the above calculations
      
      Includes optimization for an array that contains all of the same value (as in the given test file). */

public class Assignment1 {

   public static void main(String[] args){
   
      if(args.length == 0){
         System.out.println("Please specify input file name as command line argument");
         System.exit(0);
      }
      
      try {
         Integer[] array = parseFile(args[0]);
         System.out.println("Array: " + Arrays.toString(array));
     
         int result = maxSubarray(array, array.length);
         System.out.println("Result: " + result);
         
      } catch(FileNotFoundException e){
         e.printStackTrace();
      }
      
   }
   
   //Checks if file exists and returns elements as array
   private static Integer[] parseFile(String filename) throws FileNotFoundException {
      File arrayFile = new File(filename);
      
      if(!arrayFile.exists()){
         System.out.println("File: " + filename + " does not exist");
         System.exit(0);
      }
      
      Scanner scanner = new Scanner(arrayFile);
      LinkedList<Integer> list = new LinkedList<Integer>();
      
      while(scanner.hasNextInt()){
         list.add(scanner.nextInt());
      }
  
      return list.toArray(new Integer[list.size()]);
   }

   //Checks each subarray of given array for max value
   //Returns the sum of all max values in each subarray
   private static int maxSubarray(Integer[] array, int length){
      Stack<Integer> stack = new Stack<Integer>(); //Stores indexes while iterating through array
      int leftCount[] = new int[length]; // Holds count of lesser vals left of index
      int rightCount[] = new int[length]; // Holds count of lesser vals right of index 
      int total = 0; //Holds our final value for sum of max subarray vals
      boolean sameNumArray = true;
      
      
      //First check for lesser values to the left of each index: O(N)
      for(int i = 0; i < length; i++){
         while(stack.size() != 0 && array[stack.peek()] <= array[i]){
            leftCount[i] += leftCount[stack.peek()] + 1;
            if(leftCount[i] != i){
               sameNumArray = false;
            }
            stack.pop();
         }
         stack.push(i);
      }
      
      //Optimization 1
      //If the array contains only one number, the solution is trivial. (N*(N+1)/2)*Value
      if(sameNumArray){
         total = ((length * (length + 1)) / 2) * array[0];
         System.out.println("Optimized solution for array of same values: Only one pass thru.");
         return total;
      }
               
      stack.clear(); // Assuming O(n) time for clearing stack
      
      //Now check for lesser values to the right of each index: O(N)
      for(int i = length - 1; i >= 0; i--){
         while(stack.size() != 0 && array[stack.peek()] < array[i]){
            rightCount[i] += rightCount[stack.peek()] + 1;
            stack.pop();
         }
         stack.push(i);
      } 
            
      stack.clear();
            
      // Calculate the total for each index given the counts stored in left/right arrays
      // This process is also dependent on the length so it takes O(N) time
      for(int i = 0; i < length; i++){
         total += (leftCount[i] + 1) * (rightCount[i] + 1) * array[i];
      }
      
      return total;
   }
}