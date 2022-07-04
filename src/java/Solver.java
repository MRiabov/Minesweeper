public class Solver {
    private boolean actionIsPossible;
    private final Game game;

    public Solver(Game game) {
        this.game = game;
    }

    public boolean nextStep() {
        actionIsPossible = false;
        solveFlag();
        return solveOpen();
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
                if (isOutOfBounds(x + k, y + l)) {
                    continue;
                }
                if (game.cells[x + k][y + l].isOpen())
                    ++openAround;
            }
        }
        return --openAround;
    }

    private int calcFlagsAround(int x, int y) {
        int openFlags = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l)) {
                    continue;
                }
                if (game.cells[x + k][y + l].isFlag()) ++openFlags;
            }
        }
        return openFlags;
    }

    private boolean solveOpen() {
        boolean success = true;
        for (int i = 0; i < game.fieldLength; i++) {
            for (int j = 0; j < game.fieldLength; j++) {
                if (game.cells[i][j].isOpen()) {
                    if (calcFlagsAround(i, j) == game.cells[i][j].getBombNear()) {
                        success = openAllAround(i, j);
                    }
                    actionIsPossible = true;
                }
            }
        }
        return success;
    }

    private boolean openAllAround(int x, int y) {
        boolean success = true;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l)) continue;
                success = game.setOpen(x + k, y + l);
            }
        }
        return success;
    }

    private void flagAllAround(int x, int y) {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (isOutOfBounds(x + k, y + l))
                    continue;
                game.setFlag(x + k, y + l);
            }
        }
    }

    private void solveFlag() {
        for (int i = 0; i < game.fieldLength; i++) {
            for (int j = 0; j < game.fieldLength; j++) {
                if (game.cells[i][j].isOpen()) {
                    if (calcOutOfBounds(i, j) + calcFlagsAround(i, j) + calcOpenAround(i, j) + game.cells[i][j].getBombNear() == 8) {
                        flagAllAround(i, j);
                        actionIsPossible = true;
                    }
                }
            }
        }
    }
}