public class Printer {
    private static int stepsCount;

    public static void printOut(Game game){
        System.out.println("\nStep #"+stepsCount++);
        for (int i = 0; i < game.fieldLength; i++) {
            for (int j = 0; j < game.fieldLength; j++) {
                Cell currentCell=game.cells[i][j];
                if (currentCell.isOpen()) {
                    if (!currentCell.isBomb()) System.out.print("[" + currentCell.getBombNear() + "]");
                    if (currentCell.isFlag())
                    if (currentCell.isBomb()) System.out.print("[*]");
                } else if (currentCell.isFlag()) System.out.print("[F]");
                else System.out.print("[#]");
            }
            System.out.println(" ");
        }
    }

    public static void printFinal(Game game){
        System.out.println("Field showdown:");
        for (int i = 0; i < game.fieldLength; i++) {
            for (int j = 0; j < game.fieldLength; j++) {
                Cell currentCell=game.cells[i][j];
                    if (!currentCell.isBomb()) System.out.print("[" + currentCell.getBombNear() + "]");

                    if (currentCell.isBomb()) System.out.print("[*]");
            }
            System.out.println(" ");
        }
    }
}
