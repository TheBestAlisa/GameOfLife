import java.io.IOException;

public class PackedWorld extends World implements Cloneable {
    private long world;

    public PackedWorld(Pattern pattern) throws PatternFormatException {
        super(pattern);

        getPattern().initialise(this);
    }

    public PackedWorld(String format) throws PatternFormatException {
        super(format);
        // System.out.println("packed");
        if (getWidth() * getHeight() > 64) {
            throw new PatternFormatException(getHeight() + "-by-" + getWidth() + " is too big for a packed long");
        }

        getPattern().initialise(this);

    }

    public PackedWorld(PackedWorld anotherPackedWorld) {
        super(anotherPackedWorld.getPattern());

        this.world = anotherPackedWorld.world;

    }

    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight()) {
            return false;
        }
        if (col < 0 || col >= getWidth()) {
            return false;
        }
        if (((world >>> (row * getWidth() + col)) & 1L) == 1L)
            return true;
        else
            return false;
    }

    public void setCell(int col, int row, boolean value) {
        int pos = 0;
        if (row * getWidth() + col < getWidth() * getHeight() && row * getWidth() + col >= 0)
            pos = 1 << row * getWidth() + col;
        if (value) {
            this.world = this.world | pos;
        } else {
            this.world = this.world & (~pos);
        }
    }

    protected void nextGenerationImpl() {
        long newLong = 0L;

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (computeCell(j, i)) {
                    newLong = (1L << i * getWidth() + j) | newLong;
                }
            }
        }
        world = newLong;

    }

    public PackedWorld clone() {
        PackedWorld cloned = (PackedWorld) super.clone();
        cloned.world = this.world;
        return cloned;
    }

}