
import java.awt.*;
import java.util.Arrays;

public class Pattern extends Component implements Comparable<Pattern> {

    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;

    public Pattern(String format) throws PatternFormatException {

        String[] arr = format.split("\\:");

        if (format.equals("")) {
            throw new PatternFormatException("Please specify a pattern");
        }

        if (arr.length != 7) {
            throw new PatternFormatException("Incorrect number of fields in pattern (found" + arr.length + ".");
        }

        try {
            width = Integer.parseInt(arr[2]);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid pattern format:  Could not interpret the width field as a number" + "(" + arr[2]
                    + "given).");
        }

        try {
            height = Integer.parseInt(arr[3]);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid pattern format:  Could not interpret the height field as a number" + "("
                    + arr[3] + "given).");
        }

        try {
            startCol = Integer.parseInt(arr[4]);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid pattern format:  Could not interpret the height field as a number" + "("
                    + arr[4] + "given).");
        }

        try {
            startRow = Integer.parseInt(arr[5]);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid pattern format:  Could not interpret the height field as a number" + "("
                    + arr[5] + "given).");
        }

        this.name = arr[0];
        this.author = arr[1];
        this.cells = arr[6];

    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public String getCells() {
        return cells;
    }

    public void initialise(World world) throws PatternFormatException {

        String[] update = cells.split(" ");

        for (int i = 0; i < update.length; i++) {
            for (int j = 0; j < update[i].length(); j++)
                if (!(update[i].charAt(j) == '1') && !(update[i].charAt(j) == '0')) {
                    throw new PatternFormatException(
                            "Invalid pattern format: Malformed pattern " + Arrays.toString(update) + ".");
                }
        }

        for (int i = 0; i < update.length; i++) {
            for (int j = 0; j < update[i].length(); j++) {
                if (update[i].charAt(j) == '1') {
                    world.setCell(startCol + i, startRow + j, true);
                } else if (update[i].charAt(j) == '0') {
                    world.setCell(startCol + i, startRow + j, false);
                }
            }
        }
    }

    @Override
    public String toString() {
        return name + ":" + author;
    }

    @Override
    public int compareTo(Pattern o) {

        return this.getName().compareTo(o.getName());

    }

}
