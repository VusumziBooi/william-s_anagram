import java.io.File;
import java.util.*;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileNotFoundException;

public class Anagram{
    //Given input , a random list with some anagrams in it
    static List<String>mylist;

    //A method that displays a list (just for visualisation)
    public static void displayList(List<String>mylist){
        String space  = "";
        for(int i = 0 ; i < mylist.size() ; i++){
            System.out.print(space + mylist.get(i));
            space = " ";
        }
        System.out.println();
    }

    //A method that returns the number of anagrams in a given list
    public static Integer anagramCounter(List<String>mylist){
        //Sort all the strings
        int Counter = 0;
        List<String>sortedStrings = new ArrayList<>();
        for(String current: mylist){
            //ASSUMPTION : The String "ABOUT" is the same as the string "about" which is the same as the string "aBout" and any other combination of letters
            //change the string into lower cases
            current = current.toLowerCase();
            char charArray[] = current.toCharArray();
            Arrays.sort(charArray);
            sortedStrings.add(new String(charArray));
        }
        //Use red-black-tree algorithm to find the frequency of each string
        TreeMap<String , Integer>tmap = new TreeMap<String , Integer>();
        for(String current: sortedStrings){
            Integer temp = tmap.get(current);
            tmap.put(current, (temp == null) ? 1: temp+1);
        }

        for(Map.Entry m : tmap.entrySet()){
            int tempCounter = (Integer)m.getValue();
            if(tempCounter > 1){
                Counter += tempCounter;
            }
        }
        return Counter;

    }

    //A method to get the last element in a list with the current word length
    public static Integer getStopIndex(int startIndex , int currIndexLength){
        int lastIndex = startIndex;
        for(int i = startIndex ; i < mylist.size() ; i++){
            if(mylist.get(i).length() > currIndexLength){
                //return the previous elemet's index
                lastIndex = i-1;
                return lastIndex;
            }
        }
        return lastIndex;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Taking input from the dictionary in the specified path and add into the list
        Scanner scanner = new Scanner(new File("/home/vmuser/Desktop/Vusumzi/Dictionary.txt"));
        List<String> list = new ArrayList<String>();
        while (scanner.hasNext()){
            list.add(scanner.next());
        }
        scanner.close();
        mylist = list;     
        //sort the list by length
        mylist.sort((s1, s2) -> s1.length() - s2.length());
        //For each word length from 2 , get the sublist and call the anagramCounter for that word length 
        int startIndex = 0, stopIndex, currIndexLength = mylist.get(startIndex).length();
        //Time the whole program
        long startTime = System.currentTimeMillis();
        while(startIndex < mylist.size()-1){
            stopIndex = getStopIndex(startIndex , currIndexLength);
            if(startIndex == stopIndex){
                //Ignore the term , it is the only one with that word length
                //Jump to the last part of the loop
            }
            else{
                //Get the sublist and return the number of anagrams
                List<String> currList = mylist.subList(startIndex , stopIndex+1);
                System.out.println("Words with the character length of " + currIndexLength + " had " + anagramCounter(currList) + " anagrams");
            }      
            //Update the start index and the stop index
            startIndex = stopIndex+1;
            currIndexLength = mylist.get(startIndex).length();
            stopIndex = getStopIndex(startIndex , currIndexLength);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Anagram Results (completed in " + (endTime - startTime) + " ms):");
        

    }

}