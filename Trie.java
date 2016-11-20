package maintries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trie {

  private TrieNode root;

  // constructor to create a trie
  public Trie() {
    root = new TrieNode();
  }

  /*---------------------------------------------------------*/
  /* Function Name: insertCompanyNames             */
  /*                                                         */
  /* Description: This is a function iterate through each    */
  /* element of arraylist of arraylist, and insert each      */
  /* element into tire                     */
  /* --------------------------------------------------------*/
  
  public void insertCompanyNames(ArrayList<ArrayList<String>> companyName) {

    for (int i = 0; i < companyName.size(); i++) {
      for (int j = 0; j < companyName.get(i).size(); j++) {
        insert(companyName.get(i).get(j));
      }
    }
  }
  
  /*---------------------------------------------------------*/
  /* Function Name: insert                   */
  /*                                                         */
  /* Description: This is a private function serve         */
  /* insertCompanyNames function, it will build tire for     */
  /* each word.                        */
  /* --------------------------------------------------------*/
  private void insert(String word) {
    // map to track the children of a TrieNode
    Map<Character, TrieNode> children = root.childern;

    for (int i = 0; i < word.length(); i++) {

      char ch = word.charAt(i);

      TrieNode tn;
      // If this character already exist, move the pointer to the Tirenode
      if (children.containsKey(ch)) {
        tn = children.get(ch);
      } 
      else {
      // else create a new TireNode with this character
        tn = new TrieNode(ch);
        children.put(ch, tn);
      }

      // move pointer to children
      children = tn.childern;

      // if reach the end of word, mark it's leaf.
      if (i == word.length() - 1) {
        tn.isLeaf = true;
      }
    }
  }

  /*---------------------------------------------------------*/
  /* Function Name: searchWords                */
  /*                                                         */
  /* Description: This is a function that first search for   */
  /* companyName, and then add into a map by updating        */
  /* frequency.                          */
  /* Then match the current map with each arraylist of     */
  /* companyName, and sum up frequency for each company by   */
  /* putting primary name of company and total frequency into*/
  /* a new map.                        */
  /* --------------------------------------------------------*/
  
  
  public Map<String, Integer> searchWords(ArrayList<String> words, ArrayList<ArrayList<String>> companyName) {

    int freq = 0;
    int total = 0;
    // map for storing each company's name and frequency
    Map<String, Integer> map = new HashMap<>();
    // map for storing primary company name and summed frequency
    Map<String, Integer> searchMap = new HashMap<>();

    // iterate each word and compare with tire
    for (String word : words) {
      TrieNode result = search(word);
      if (result != null && result.isLeaf == true) {
        if (map.containsKey(word)) {
          freq = map.get(word);
          map.put(word, ++freq);
        } else {
          map.put(word, 1);
        }
      } else {
        continue;
      }
    }

    // sum up frequency of each companyName, and put
    // primary name and total frequency into searchMap
    for (int i = 0; i < companyName.size(); i++) {
      for (int j = 0; j < companyName.get(i).size(); j++) {
        if (map.containsKey(companyName.get(i).get(j))) {
          total += map.get(companyName.get(i).get(j));
          searchMap.put(companyName.get(i).get(0), total);
        }else{
          continue;
        }
        total=0;
      }

    }
    // if total frequency of a company is 0, then add that company's
    // primary name and total frequency=0 into map.
    for (int i = 0; i < companyName.size(); i++) {
      if (!searchMap.containsKey(companyName.get(i).get(0))) {
        searchMap.put(companyName.get(i).get(0), 0);
      }
    }

    return searchMap;

  }

  /*---------------------------------------------------------*/
  /* Function Name: search                     */
  /*                                                         */
  /* Description: This is a private function serve         */
  /* searchWords function, it will return the last TireNode  */
  /* of each word if it's matched                */
  /* --------------------------------------------------------*/
  
  private TrieNode search(String word) {
    Map<Character, TrieNode> children = root.childern;

    TrieNode tn = null;

    for (int i = 0; i < word.length(); i++) {
      char ch = word.charAt(i);
      if (children.containsKey(ch)) {
        tn = children.get(ch);
        children = tn.childern;
      } else {
        return null;
      }
    }
    return tn;

  }

}
