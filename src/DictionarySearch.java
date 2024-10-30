import java.io.*;
import java.util.*;

public class DictionarySearch {

    // ANSI escape codes for colors
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";

    public static void main(String[] args) {
        Map<String, String> dictionary = new HashMap<>();
        loadDictionary(dictionary);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addWord(scanner, dictionary);
                    break;
                case "2":
                    searchWord(scanner, dictionary);
                    break;
                case "3":
                    listAllWords(dictionary);
                    break;
                case "4":
                    running = false;
                    System.out.println(RED + "Goodbye" + RESET);
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println(CYAN + "===============================" + RESET);
        System.out.println(BLUE + "         Dictionary Menu       " + RESET);
        System.out.println(CYAN + "===============================" + RESET);
        System.out.println("1. Add a word and its meaning");
        System.out.println("2. Search for a word's meaning");
        System.out.println("3. List all words");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void loadDictionary(Map<String, String> dictionary) {
        dictionary.clear(); // Clear existing entries
        try (BufferedReader br = new BufferedReader(new FileReader("mydict.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2); // Split into two parts only
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String meaning = parts[1].trim();
                    dictionary.put(word, meaning);
                }
            }
        } catch (IOException e) {
            System.out.println(RED + "Error reading the file: " + e.getMessage() + RESET);
        }
    }

    private static void addWord(Scanner scanner, Map<String, String> dictionary) {
        System.out.print("Enter the word: ");
        String word = scanner.nextLine().trim().toLowerCase();
        System.out.print("Enter the meaning: ");
        String meaning = scanner.nextLine().trim().toLowerCase();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("mydict.txt", true))) {
            bw.write(word + ":" + meaning);
            bw.newLine();
            dictionary.put(word, meaning); // Update the dictionary map
            System.out.println(GREEN + "Word added successfully!" + RESET);
        } catch (IOException e) {
            System.out.println(RED + "Error writing to the file: " + e.getMessage() + RESET);
        }
    }

    private static void searchWord(Scanner scanner, Map<String, String> dictionary) {
        System.out.print("Enter your word: ");
        String inputWord = scanner.nextLine().trim();

        String meaning = dictionary.get(inputWord.toLowerCase());
        if (meaning != null) {
            System.out.println(GREEN + inputWord + " means " + meaning + RESET);
        } else {
            System.out.println(YELLOW + "Word not found in the dictionary." + RESET);
        }
    }

    private static void listAllWords(Map<String, String> dictionary) {
        System.out.println(CYAN + "===============================" + RESET);
        System.out.println(BLUE + "        Dictionary Entries     " + RESET);
        System.out.println(CYAN + "===============================" + RESET);
        System.out.printf("%-15s %-15s%n", "Word", "Meaning");
        System.out.println(CYAN + "-------------------------------" + RESET);

        // Use TreeSet to sort the words
        TreeSet<String> sortedWords = new TreeSet<>(dictionary.keySet());
        for (String word : sortedWords) {
            System.out.printf("%-15s %-15s%n", word, dictionary.get(word));
        }

        System.out.println(CYAN + "===============================" + RESET);
    }
}
