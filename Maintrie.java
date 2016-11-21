package maintries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Maintrie {

  public static void main(String[] args) throws IOException {

    File inputFile = new File("infile.dat");     

    File company = new File("companies.dat");

    System.out.println("Company\t\t\tHit Count\t\tRelevance");

    Maintrie s = new Maintrie(company, inputFile);
  }

  // Constructor for Maintrie
  public Maintrie(File company, File inputfile) throws IOException {

    String article = processFile(inputfile);
    ArrayList<ArrayList<String>> companyName = processCompanyName(company);
    Trie companyTire = new Trie();
    companyTire.insertCompanyNames(companyName);
    Map<String, Integer> map = companyTire.searchWords(article, companyName);
    outputResult(map, processFileLength(inputfile));

  }

  /*---------------------------------------------------------*/
  /* Function Name: processFile */
  /*                                                         */
  /* Description: This is the function that use scanner to */
  /* store each company's name into a arraylist but ignoring */
  /* "a,an,the,and,or,but" */
  /* -------------------------------------------------------- */
  public String processFile(File inputFile) throws IOException {

    String inputString = new String(Files.readAllBytes(inputFile.toPath()));

    return inputString;
  }

  /*---------------------------------------------------------*/
  /* Function Name: processCompanyName               */
  /*                                                         */
  /* Description: This is the function that use scanner to   */
  /* store each company's name into a arraylist of arraylist */
  /*---------------------------------------------------------*/

  public ArrayList<ArrayList<String>> processCompanyName(File companyFile) throws FileNotFoundException {
    // Create arraylist of arraylist, the inner arraylist to store
    // primary name and synonyms for each company, and the outter
    // arraylist to hold each company.
    ArrayList<ArrayList<String>> companyList = new ArrayList<>();

    // scanner a line
    Scanner scanner = new Scanner(companyFile);

    while (scanner.hasNextLine()) {

      String companyName = scanner.nextLine();
      String[] split = companyName.split("\t");

      // create a new list for each line scanned
      ArrayList<String> list = new ArrayList<>();

      for (String s : split) {
        list.add(s);
      }
      companyList.add(list);
    }

    scanner.close();
    return companyList;
  }

  /*---------------------------------------------------------*/
  /* Function Name: processFileName                */
  /*                                                         */
  /* Description: This is the function that use scanner to   */
  /* store each word into a arrayList, that would eliminate  */
  /* special character                     */
  /*---------------------------------------------------------*/
  public Integer processFileLength(File inputFile) throws FileNotFoundException {
    ArrayList<String> inputList = new ArrayList<>();

    Scanner scanner = new Scanner(inputFile);

    while (scanner.hasNextLine()) {

      String sentence = scanner.nextLine();
      String[] tokens = sentence.split("(\\, )|(\\ )|(\\'s )|(\" )|(\")|(\\. )|(\\/)");

      for (String word : tokens) {
        if (!word.equals("a") && !word.equals("an") && !word.equals("the") && !word.equals("and")
            && !word.equals("or") && !word.equals("but") && !word.equals("s") && !word.equals("But")
            && !word.equals("And") && !word.equals("The") && !word.equals("An") && !word.equals("A")
            && !word.equals("Or")) {
          if(word.equals("")){continue;}
          inputList.add(word);
        }
      }
    }

    scanner.close();

    return inputList.size();
  }

  /*---------------------------------------------------------*/
  /* Function Name: outputResult                 */
  /*                                                         */
  /* Description: This function output the company primary   */
  /* name, frequency of each company as well as total        */
  /*---------------------------------------------------------*/

  public void outputResult(Map<String, Integer> map, int size) {

    // format variable
    NumberFormat formatter = new DecimalFormat("#0.000000");
    String format1 = "%-20s %7d %28s\n";
    String format2 = "%-20s %20d\n";

    // local variable
    double totalHit = 0.00;
    double totalRelevance = 0.00;
    double total = size;

    // Output for each company by primary name, hit and frequency.
    for (Entry<String, Integer> entry : map.entrySet()) {

      System.out.printf(format1, entry.getKey(), entry.getValue(),
          formatter.format(entry.getValue() * 100 / total) + "%");
      totalHit += entry.getValue();
      totalRelevance += entry.getValue() / total;
    }
    System.out.printf(format1, "Total", (int) totalHit, formatter.format(totalRelevance * 100) + "%");
    System.out.printf(format2, "Total Words", (int) total);
  }

}