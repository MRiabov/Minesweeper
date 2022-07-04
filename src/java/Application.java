import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Application {
    private static final Random random = new Random();
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game(10, 13);
        Solver solver = new Solver(game);
        game.setOpen(random.nextInt(game.fieldLength), random.nextInt(game.fieldLength));
        boolean isContinue = true;
        while (isContinue) {
            TimeUnit.MILLISECONDS.sleep(1000);
            isContinue = solver.nextStep();
            ConsoleWriter.printOut(game);
        }
        System.out.println("GAME OVER");
    }
}
