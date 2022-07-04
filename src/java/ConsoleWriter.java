public class ConsoleWriter {
    private static int step = 0;

    public static void printOut(Game game) {
        System.out.println("\n\n\n");
        System.out.println("\t\t\tStep "+step++);
        for (int i = 0; i < game.fieldLength; i++) {
            for (int j = 0; j < game.fieldLength; j++) {
                if (game.cells[i][j].isOpen()) {
                    if (!game.cells[i][j].isBomb()) System.out.print("[" + game.cells[i][j].getBombNear() + "]");
                    if (game.cells[i][j].isFlag()) System.out.print("[F]");
                    if (game.cells[i][j].isBomb()) System.out.print("[*]");
                } else System.out.print("[#]");
            }
            System.out.println(" ");
        }
    }

}
