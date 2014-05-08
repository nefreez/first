	
// search engine
// two types of counters - overall and per website

import java.util.*;
import java.net.*;
import java.io.*;

public class dkconsole  {
   public static List<String> urlinput ()  {        //URL input class
      System.out.println("Where to search?");  
      Scanner dkscannerurl = new Scanner(System.in);
      String urlflow = new String (dkscannerurl.nextLine()); // URL addresses in one row
      List<String> urllist = Arrays.asList(urlflow.split(", *")); // splitting URL-s separated by a comma
      
      for(String url : urllist) {                   // checking HTTP:// prefix
         if(!url.startsWith("http://")) urllist.set(urllist.indexOf(url),"http://"+url);
      }
      return urllist; // a list of separated URL-s		
   }     
   public static List<String> wordinput ()  {       // search words input class
      System.out.println("What to search?");  
      Scanner dkscannerinput = new Scanner(System.in);
      String inputflow = new String(dkscannerinput.nextLine());  // search words in one row
      List<String>wordlist= Arrays.asList(inputflow.split(", *"));  // splitting words separated by a comma
      return wordlist; // a list of separated words
   }
   public static void main (String[] args)throws Exception  {  
      List<String> urllist = new ArrayList<String>();  // empty URL list
      List<String> wordlist = new ArrayList<String>();  // emty word list
      List<Integer> counter1 = new ArrayList<Integer>();  // initialising match counter for each website
      List<Integer> counter2 = new ArrayList<Integer>();  // initialising an overall match counter for all websites
      int charcount1 = 0; //character counter per each website
      int charcount2 = 0; //character counter for all websites in total
      boolean processtime = false; // process timer is turned off by default
      
      System.out.println("Welcome!");
      System.out.println("press 'where' for address input");
      System.out.println("press 'what' for word input");
      System.out.println("press 'search' for search initialisation");
      System.out.println("press 'exit' for programm termination");
      System.out.println("process timer is OFF. Press 'timer on' or 'timer off' to toggle");

      for(;;) {           //repeating loop for command input
         Scanner abc = new Scanner(System.in); // command scanner
         String command = new String(abc.nextLine());
         if(command.equals("timer on")) {processtime = true;System.out.println("process timer ON");} // turn process timer indication ON
         if(command.equals("timer off")) {processtime = false;System.out.println("process timer OFF");} // turn process time indication OFF
         if(command.equals("where")) {urllist = new ArrayList<String>(dkconsole.urlinput()); System.out.println(urllist);} // start URL input
         if(command.equals("what")) {wordlist = new ArrayList<String>(dkconsole.wordinput());System.out.println(wordlist);} // start search words input
         if(command.equals("exit")) {System.out.println("Good Bye");break;} // terminate program
         if(command.equals("search")) {                                     // start search process
            long processTimerStart = System.currentTimeMillis(); // process timer start
            for(String a :wordlist) {counter2.add(0);} // each search word is given its counter - overall
            
            for(String url:urllist) {     // check per each website
               URL currentURL = new URL (url); //url as string - to url as URL
               System.out.println("");
               System.out.println(currentURL); // indicating processed website 
               for(String a :wordlist) {counter1.add(0);} // each search word is given its counter - per website
               
               BufferedReader in = new BufferedReader(new InputStreamReader(currentURL.openStream()));
               String line = null; // line - a line polluted with tags
            
               while((line=in.readLine()) != null) {  // check per each row of a website
                  String notagline = ""; // notagline - a line peeled of <tags>
                  boolean tag = false; // <tag> filter is open by default
                  for (char bukva : line.toCharArray()) {   // a loop for <tag> filtration
                     if(bukva=='<') {tag=true;} // filtering starts when "<" is noticed
                     if(tag==false) {notagline +=bukva;if(bukva!=' ') {charcount1++; charcount2++;}} // adding char from line to notagline only
                                                                                                   // if filer is off(tag=false) 
                     if (bukva=='>') {tag=false;} // letting through |the next character| after <tag> is closed
                  }
                  for(String word : wordlist) {    // counting word matches
                     int p = wordlist.indexOf(word);   //p - number of the word in the list
                     for(int i=0;i+word.length()<notagline.length();i++ ) { // checking by each character in the line
                                                                           //i-number of the character in the line
                        if(notagline.startsWith(word,i)) {counter1.set(p,(counter1.get(p)+1));counter2.set(p,(counter2.get(p)+1));}
                     }
                  }
               }
               in.close(); // closing the bufferedreader stream
               for(String word : wordlist) { // printing results of each website
                  int p = wordlist.indexOf(word);  //p - number of the word in the list
                  System.out.println  (word+" matched "+ counter1.get(p)+" times");}
               System.out.println("Р±СѓРєРІ РЅР° СЃР°Р№С‚Рµ "+charcount1);  //printing number of characters of each website
               charcount1=0;// resetting individual character counter
               counter1.clear();counter1.add(0);// resetting individual word match counter
            }
            long processTimerFinish = System.currentTimeMillis(); // stopping the timer
            System.out.println("");
            System.out.println("TOTAL");  // printing total results
            for(String word:wordlist) { //total amount of word matches for all websites
               int p = wordlist.indexOf(word);  //p - number of the word in the list
               System.out.println(wordlist.get(p)+" matched "+ counter2.get(p) + " times");
            }
            counter2.clear();counter2.add(0); // resetting the counter of total word matches for all websites
            
            System.out.println ("Р±СѓРєРІ РЅР° РІСЃРµС… СЃР°Р№С‚Р°С…"+charcount2);
            charcount2=0;     //resetting the total counter of characters
            if(processtime == true) {System.out.println("Processing time "+(processTimerFinish - processTimerStart)+" milliseconds");
            }// if timer displaying is ON - displaying timer results
         }
      }
   }
}
