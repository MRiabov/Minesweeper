public class Cell {
private boolean isFlag;
private boolean isBomb;
private boolean isOpen;
private int bombNear;

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getBombNear() {
        return bombNear;
    }

    public void setBombNear(int bombNear) {
        this.bombNear = bombNear;
    }
}
