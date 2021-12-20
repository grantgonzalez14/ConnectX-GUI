package cpsc2150.extendedConnectX;

public class BoardPosition {
    private int row;
    private int col;

    /**
     * BoardPosition constructor
     *
     * @param r     row initializer
     * @param c     column initializer
     * @post        row = r AND
     *              col = c
     */
    public BoardPosition(int r, int c){

        row = r;
        col = c;

    }

    /**
     * Getter function to get current row
     *
     * @return      the desired row
     * @post        getRow = row
     */
    public int getRow(){ return row; }

    /**
     * Getter function to get current column
     *
     * @return      the desired column
     * @post        getColumn = column
     */
    public int getColumn(){ return col; }

    /**
     * Overrided equals function
     *
     * @param b     position that is being checked for equality
     * @return      true if b == [BoardPosition object] otherwise false
     * @post        equals(BoardPosition b) = true if b == this OR
     *              temp
     */
    @Override
    public boolean equals(Object b){

        // If the object passed is the same object
        // that it is being compared to true is returned
        if (b == this) return true;

        // If the object passed is not a BoardPosition
        // object false is returned
        if (!(b instanceof BoardPosition)) return false;

        // Temp variable is created for a type casted Object b
        // used to make things more readable
        BoardPosition temp = (BoardPosition) b;

        // Comparison between the object passed and the object that is being compared to
        // If the rows and columns are equivalent then true is returned otherwise false is returned
        return (temp.getRow() == this.getRow() && temp.getColumn() == this.getColumn());

    }

    /**
     * Overrided toString function used to convert board position into
     * <row, column> form.
     *
     * @return      string in <row, column> form
     * @post        toString = pos
     */
    @Override
    public String toString(){

        String pos = "<" + row + ">,<" + col + ">";

        return pos;

    }

}
