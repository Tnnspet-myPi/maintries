package maintries;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

  // character of tireNode
  char ch;
  
  String fullName;

  //Hashmap to store childern of a tireNode
  Map<Character, TrieNode> childern = new HashMap<Character, TrieNode>();

  //Check if the TireNode is the leaf
  boolean isLeaf;

  // Constructor for TireNode with character
  public TrieNode(char ch) {
    this.ch = ch;
  }
  
  // overload constructor
  public TrieNode() {}

}
