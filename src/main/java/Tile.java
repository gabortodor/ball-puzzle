public class Tile {
    boolean topBorder;
    boolean rightBorder;
    boolean bottomBorder;
    boolean leftBorder;



    public Tile(){
        this(false,false,false,false);
    }
    public Tile(boolean topBorder,boolean rightBorder, boolean bottomBorder, boolean leftBorder){
        this.topBorder=topBorder;
        this.rightBorder=rightBorder;
        this.bottomBorder=bottomBorder;
        this.leftBorder=leftBorder;
    }

    public boolean isTopBorder() {
        return topBorder;
    }

    public void setTopBorder(boolean topBorder) {
        this.topBorder = topBorder;
    }

    public boolean isBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(boolean bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public boolean isRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(boolean rightBorder) {
        this.rightBorder = rightBorder;
    }

    public boolean isLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(boolean leftBorder) {
        this.leftBorder = leftBorder;
    }
}
