public class Solver {

    private boolean actionIsPossible;
    private final Game game;
    public Solver(Game game) {
        this.game = game;
    }
    private double minRisk = 1;
    private double[][] optionsRisk = new double[10][10];
    private int actionsDone=0;

    public boolean nextStep() {
        actionIsPossible = false;
        actionsDone=0;
        solveFlag();
        solveOpen();
        if (actionsDone==0)
            return openAndPray();
        return actionIsPossible;
    }

    private boolean isOutOfBounds(int x, int y) {
        return x >= game.fieldLength || x < 0 || y >= game.fieldLength || y < 0;
    }

    private int calcOutOfBounds(int x, int y) {
        int outOfBounds = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isOutOfBounds(x, y))
                    outOfBounds++;
            }
        }
        return outOfBounds;
    }

    private int calcOpenAround(int x, int y) {
        int openAround = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l)) continue;
                if (game.cells[x + k][y + l].isOpen()) ++openAround;
            }
        }
        --openAround;
        return openAround;
    }

    private int calcFlagsAround(int x, int y) {
        int flagsAround = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l)) {
                    continue;
                }
                if (game.cells[x + k][y + l].isFlag()) ++flagsAround;
            }
        }
        return flagsAround;
    }

    private boolean solveOpen() {

        boolean success = true;
        for (int x = 0; x < game.fieldLength; x++) {
            for (int y = 0; y < game.fieldLength; y++) {
                if (game.cells[x][y].isOpen()) {
                    if (calcFlagsAround(x, y) == game.cells[x][y].getBombNear()/*&&newOpened>0*/)
                        success = openAllAround(x, y);
                    actionIsPossible = success;
                }
            }
        }
        return actionIsPossible;
    }

    private boolean openAllAround(int x, int y) {
        boolean success = true;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l)) continue;
                Cell checkCell=game.cells[x+k][y+l];
                success = game.setOpen(x + k, y + l);
                if (checkCell!=game.cells[x+k][y+l]) actionsDone++;
            }
        }
        return success;
    }

    private void flagAllAround(int x, int y) {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l)) continue;
                if (!game.cells[x+k][y+l].isFlag()&&!game.cells[x+k][y+l].isOpen()) actionsDone++;
                game.setFlag(x + k, y + l);
            }
        }
    }

    private void solveFlag() {
        for (int x = 0; x < game.fieldLength; x++) {
            for (int y = 0; y < game.fieldLength; y++) {
                if (game.cells[x][y].isOpen()) {
                    if (calcOutOfBounds(x, y) + calcOpenAround(x, y) + game.cells[x][y].getBombNear() == 8/*&&newFlagged>0*/) {
                        flagAllAround(x, y);
                        actionIsPossible = true;
                    }
                }
            }
        }
    }

    private boolean openAndPray() {
        setMinRisk();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (minRisk == optionsRisk[x][y]) {
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            if (!game.cells[x][y].isOpen() || !game.cells[x][y].isFlag() || !isOutOfBounds(x, y)){
                                game.setOpen(x, y); return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void setMinRisk(){
        for (int i = 0; i < 10; i++) {
           for (int j = 0; j < 10; j++) {
               if (8 - calcOpenAround(i, j) + calcFlagsAround(i, j) + calcOutOfBounds(i, j)!=0)
                optionsRisk[i][j] = (game.cells[i][j].getBombNear() - calcFlagsAround(i, j)) / (8 - calcOpenAround(i, j) + calcFlagsAround(i, j) + calcOutOfBounds(i, j));
                if (minRisk<optionsRisk[i][j]&&optionsRisk[i][j]>0) minRisk=optionsRisk[i][j];

           }
        }
    }
}

