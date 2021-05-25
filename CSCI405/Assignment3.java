/* Jesse Ericksen
   CSCI 405 Summer 2019
   Western Washington University
   July 26 2019
   
   Assignment 3
   
   Description: You are on a road trip for which there are multiple hotels (pit stops) 
   along the way for which you can stop at. Each pit stop, a(1) < a(2) < .. < a(n) is some distance
   from your starting location at mile post 0. The goal is to get to the final location: a(n) while
   traveling an ideal 200 miles a day. Assuming you travel x distance each day, and that you will
   incur a penalty of (200 - x)^2 for each day, find an algorithm to calculate the best route through
   available hotels to get to the final destination with the least penalty.
   
   Input: A text file consisting of tab separated integers representing hotel locations from start.
   Output: Sequence of integers representing route through hotels.
   
   Algorithm: 
   
   
*/

import java.util.*;
import java.io.*;

public class Assignment3 {

   public static void main(String[] args){
      if(args.length == 0){
            System.out.println("Please specify input file name as command line argument");
            System.exit(0);
      }
      
      try {
         Integer[] hotels = parseFile(args[0]);
         System.out.println("Hotel Locations: " + Arrays.toString(hotels));
         
      } catch(FileNotFoundException e){
         e.printStackTrace();
      }
   
   }
   
   //Checks if file exists and returns integer elements in array
   private static Integer[] parseFile(String filename) throws FileNotFoundException {
      File file = new File(filename);
      
      if(!file.exists()){
         System.out.println("File: " + filename + " does not exist");
         System.exit(0);
      }
      
      Scanner scanner = new Scanner(file);
      LinkedList<Integer> list = new LinkedList<Integer>();
      int i = 0;
      while(scanner.hasNextInt()){
         i++;
         list.add(scanner.nextInt());
      }
  
      return list.toArray(new Integer[list.size()]);
   }




}