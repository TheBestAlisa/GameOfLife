import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {

    /*
     * public static final int MAX_NUMBER_PATTERNS = 1000; private Pattern[]
     * patterns = new Pattern[MAX_NUMBER_PATTERNS]; private int numberUsed = 0;
     * 
     */

    private ArrayList<Pattern> patterns = new ArrayList<>();

    public PatternStore(String source) throws Exception {
        if (source.startsWith("http://") || source.startsWith("https://")) {
            loadFromURL(source);
        } else {
            loadFromDisk(source);
        }
    }

    public PatternStore(Reader source) throws Exception {
        load(source);
    }

    private void load(Reader r) throws Exception {

        BufferedReader bufRead = null;
        bufRead = new BufferedReader(r);

        String line;
        while ((line = bufRead.readLine()) != null) {
            try {
                Pattern pattern = new Pattern(line);
                patterns.add(pattern);
                // System.out.println("-" + line);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        bufRead.close();

    }

    private void loadFromURL(String url) throws Exception {

        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new InputStreamReader(conn.getInputStream());
        load(r);
    }

    private void loadFromDisk(String filename) throws Exception {
        Reader r = new FileReader(filename);
        load(r);
    }

    public Pattern[] getPatterns() {
        Pattern[] result = new Pattern[patterns.size()];
        for (int i = 0; i < patterns.size(); ++i) {
            result[i] = patterns.get(i);
        }
        return result;
    }

    public ArrayList<Pattern> getPatternsNameSorted() {
        ArrayList<Pattern> copy = new ArrayList<>(patterns);
        Collections.sort(copy);
        return copy;
    }

    public String[] getPatternAuthors() {

        String[] authors = new String[getPatternsNameSorted().size()];

        for (int i = 0; i < authors.length; i++) {
            String authorsName = getPatternsNameSorted().get(i).getAuthor();
            authors[i] = authorsName;
        }
        return authors;
    }

    public String[] getPatternNames() {

        String[] names = new String[getPatternsNameSorted().size()];

        for (int i = 0; i < names.length; i++) {
            String patternNames = getPatternsNameSorted().get(i).getName();
            names[i] = patternNames;
        }

        return names;

    }

    public static void main(String args[]) throws Exception {

        PatternStore p = new PatternStore(args[0]);
        String[] names = p.getPatternAuthors();
        for (String s : names) {
            System.out.println(s);
        }

    }
}
