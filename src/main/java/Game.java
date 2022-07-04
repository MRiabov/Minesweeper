public class Game {
    public final int fieldLength;
    public final int bombAmount;
    public final Cell[][] cells;
    private boolean isFirstStep = true;
    private int countOpen = 0;

    public Game(int fieldLength, int bombAmount) {
        this.fieldLength = fieldLength;
        this.bombAmount = bombAmount;
        cells = new Cell[fieldLength][fieldLength];
        start();
    }

    public void start(){
        generateField();
        generateBombs();
        generateNumbers();
     }

    public boolean setOpen(int x, int y) {
        if (isFirstStep) makeFirstStep(x, y);
        if (!cells[x][y].isFlag() && !cells[x][y].isOpen()) {//if not flag and not open, then open it and return that this true if hit not on bomb
            cells[x][y].setOpen(true);
            if (cells[x][y].isBomb()) System.out.println("BOOOOOOOM\nPOW! YOU ARE DEAD. NOT BIG SURPRISE.");
            return !cells[x][y].isBomb();
        }
        ++countOpen;
        return !isWin();
    }

    public void setFlag(int x, int y) {
        if (!cells[x][y].isOpen())
            cells[x][y].setFlag(true);
    }
         private boolean isWin(){
        return countOpen==Math.pow(fieldLength,2-bombAmount);
         }

   private void generateField(){
        for (int x = 0; x <fieldLength; x++) {
            for (int y = 0; y < fieldLength; y++) {
                cells[x][y]=new Cell();
            }
        }
    }

    private void generateBombs() {
        for (int i = 0;i<bombAmount; i++) {
            Cell currentCell = cells[(int) (Math.random()*fieldLength)][(int) (Math.random()*fieldLength)];
            if (!currentCell.isBomb()) currentCell.setBomb(true);
            else i--;
        }
    }

    private void generateNumbers(){
        for (int x = 0; x <fieldLength; x++) {
            for (int y = 0; y < fieldLength; y++) {
                Cell currentCell = cells[x][y];
                if (currentCell.isBomb()) {
                    for (int i =-1; i <=1; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (!(x+i>=fieldLength||x+i<0||y+j>=fieldLength||y+j<0)){
                                currentCell = cells[x + i][y + j];
                                if (!currentCell.isBomb()) {
                                    currentCell.setBombNear(currentCell.getBombNear() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeFirstStep(int x, int y) {
        if (isFirstStep && (cells[x][y].isBomb() || cells[x][y].getBombNear() != 0)) {
            start();
            setOpen(x, y);
        }
        if (isFirstStep) isFirstStep = false;
    }




}