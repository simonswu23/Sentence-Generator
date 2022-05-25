//Creates class GrammarSolver that processes grammar rules
//and generates random sentences or other language outputs

import java.util.*;

public class GrammarSolver {

    private SortedMap<String, String[]> grammar;
    
    //Processes grammar rules in BNF format and assigns 
    //non-terminal categories to groups of terminal words.
    //Throws IllegalArgumentException if the list rules is empty
    //or if the same non-terminal appears more than once
    public GrammarSolver(List<String> rules) {
        if (rules.size() == 0) {
            throw new IllegalArgumentException("rules cannot be empty");
        }
        grammar = new TreeMap<>();          
        for (String line : rules) {
            //gets nonterminal
            String[] splitted = line.split("::=");
            String nonterminal = splitted[0];
            //checks to see if there are nonterminal repeats
            if (grammar.containsKey(nonterminal)) {
                throw new IllegalArgumentException("you can only have one of each non-terminal");
            }
            //puts nonterminals and terminals into the map
            grammar.put(nonterminal, splitted[1].split("\\|"));
        }
    }
    
    //returns true if String symbol is a non-terminal 
    //in the grammar rules and false if otherwise
    public boolean grammarContains(String symbol) {
        return grammar.containsKey(symbol);
    }
    
    //returns a String list of the non-terminals
    //in the grammar rules
    public String getSymbols() {
        return grammar.keySet().toString();
    }
    
    //Takes in a non-terminal category String symbols generates
    //int times number of occurences of the non-terminal randomly,
    //returning them in an array of Strings.
    //Throws IllegalArgumentException if times is negative
    //or if String symbols is NOT a non-terminal
    public String[] generate(String symbols, int times) {
        if (times < 0) {
            throw new IllegalArgumentException("times cannot be negative!");
        }
        if (!grammarContains(symbols)) {
            throw new IllegalArgumentException("symbols must be a non-terminal");
        }
        String[] terminals = new String[times];
        for (int i = 0; i < times; i++) {
            terminals[i] = recurse(symbols);
        }
        return terminals;
    }
    
    //Generates a terminal occurence of 
    //nonterminal String symbols and returns it
    //Method runs until a non-terminal can be returned
    private String recurse(String symbols) {
        Random rand = new Random();
        String result = new String();
        String[] pieces = symbols.split("\\s+");
        for (String piece : pieces) {
            if (grammarContains(piece)) {
               int random = rand.nextInt(grammar.get(piece).length);
               result += recurse(grammar.get(piece)[random].trim()) + " ";
            } else {
               result += piece + " ";
            }
        }
        return result.trim();
    }  
}
