public class ArrayWorld extends World implements Cloneable {

    private boolean[][] world;
    private boolean[] deadRow;

    public ArrayWorld(String string) throws PatternFormatException {
        this(new Pattern(string));
    }

    public ArrayWorld(Pattern pattern) throws PatternFormatException {
        super(pattern);
        world = new boolean[this.getHeight()][this.getWidth()];
        deadRow = new boolean[getWidth()];

        getPattern().initialise(this);
        for (int i = 0; i < getHeight(); i++) {
            boolean flag = true;
            for (int j = 0; j < getWidth(); j++) {
                if (world[i][j]) {
                    flag = false;
                }
            }
            if (flag) {

                world[i] = deadRow;
            }
        }
    }

    public ArrayWorld(ArrayWorld aworld) {
        super(aworld);

        deadRow = this.deadRow;
        world = new boolean[this.getHeight()][this.getWidth()];

        for (int i = 0; i < this.getHeight(); i++) {
            boolean[] newRow = new boolean[this.getWidth()];
            boolean flag = true;
            for (int j = 0; j < this.getWidth(); j++) {
                newRow[j] = this.getCell(j, i);
                if (newRow[j]) {
                    flag = false;
                }
            }
            if (flag) {
                world[i] = deadRow;
            } else
                world[i] = newRow;
        }
    }

    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight()) {
            return false;
        }
        if (col < 0 || col >= getWidth()) {
            return false;
        }
        return world[row][col];
    }

    public void setCell(int col, int row, boolean value) {
        if (row < 0 || row >= getHeight()) {
            return;
        }
        if (col < 0 || col >= getWidth()) {
            return;
        }
        world[row][col] = value;
    }

    protected void nextGenerationImpl() {
        boolean[][] newField = new boolean[getHeight()][getWidth()];

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                newField[i][j] = computeCell(j, i);
            }
        }
        world = newField;

    }

    @Override
    public ArrayWorld clone() {
        ArrayWorld cloned = (ArrayWorld) super.clone();

        cloned.deadRow = deadRow;

        boolean[][] copied = new boolean[this.getHeight()][this.getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            boolean[] newRow = new boolean[this.getWidth()];
            boolean flag = true;
            for (int j = 0; j < this.getWidth(); j++) {
                newRow[j] = getCell(j, i);
                if (newRow[j]) {
                    flag = false;
                }
            }
            if (flag) {
                copied[i] = deadRow;
            } else {
                copied[i] = newRow;
            }
        }

        cloned.world = copied;
        return cloned;
    }
}
