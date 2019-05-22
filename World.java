public abstract class World implements Cloneable {

    private int generation;
    private Pattern pattern;

    public World(String format) throws PatternFormatException {
        pattern = new Pattern(format);
    }

    public World(Pattern pattern) {
        this.pattern = pattern;
    }

    public World(World anotherWorld) {
        this.generation = anotherWorld.generation;
        this.pattern = anotherWorld.pattern;
    }

    public World clone() {
        World cloned = null;
        try {
            cloned = (World) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        cloned.generation = generation;
        cloned.pattern = pattern;
        return cloned;
    }

    public int getWidth() {
        return pattern.getWidth();
    }

    public int getHeight() {
        return pattern.getHeight();
    }

    public int getGenerationCount() {
        return this.generation;
    }

    protected void incrementGenerationCount() {
        this.generation++;
    }

    protected Pattern getPattern() {
        return pattern;
    }

    public void nextGeneration() {
        nextGenerationImpl();
        incrementGenerationCount();
    }

    protected abstract void nextGenerationImpl();

    public abstract boolean getCell(int c, int r);

    public abstract void setCell(int col, int row, boolean val);

    protected int countNeighbours(int col, int row) {

        int count = 0;
        if (getCell(col + 1, row + 1))
            count++;
        if (getCell(col, row + 1))
            count++;
        if (getCell(col - 1, row + 1))
            count++;
        if (getCell(col - 1, row - 1))
            count++;
        if (getCell(col, row - 1))
            count++;
        if (getCell(col + 1, row - 1))
            count++;
        if (getCell(col + 1, row))
            count++;
        if (getCell(col - 1, row))
            count++;

        return count;

    }

    protected boolean computeCell(int col, int row) {

        if (!getCell(col, row) && countNeighbours(col, row) == 3) {
            return true;
        }

        if (getCell(col, row) && countNeighbours(col, row) == 2 || countNeighbours(col, row) == 3) {
            return true;
        }

        return false;
    }

}