package utility;

public class LoadingUtil {

    // ANSI colour codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String ORANGE = "\u001B[38;5;208m";

    public static void startScreen() {
        System.out.print("\nCreating the Wonderland");
        Ellipsis();

        System.out.print("Decorating the streets");
        Ellipsis();

        System.out.print("Preparing the costumes");
        Ellipsis();
        Delay();

        System.out.println("Done!");
        Delay();

        System.out.println("        ,--.   ,--.      ,--.                             ,--------.       ,--------.,--.            ");
        System.out.println("        |  |   |  |,---. |  |,---. ,---. ,--,--,--.,---.  '--.  .--',---.  '--.  .--'|  ,---. ,---.  ");
        System.out.println("        |  |.'.|  | .-. :|  | .--'| .-. ||        | .-. :    |  |  | .-. |    |  |   |  .-.  | .-. : ");
        System.out.println("        |   ,'.   \\   --.|  \\ `--.' '-' '|  |  |  \\   --.    |  |  ' '-' '    |  |   |  | |  \\   --. ");
        System.out.println("        '--'   '--'`----'`--'`---' `---' `--`--`--'`----'    `--'   `---'     `--'   `--' `--'`----'");
        System.out.println();
        Delay();
        Delay();
        
        System.out.println(RED + "                          ,------.  ,---.  ,------.   ,---.  ,------.  ,------. " + RESET);
        Delay();
        System.out.println(GREEN + "                          |  .--. '/  O  \\ |  .--. ' /  O  \\ |  .-.  \\ |  .---' " + RESET);
        Delay();
        System.out.println(BLUE + "                          |  '--' |  .-.  ||  '--'.'|  .-.  ||  |  \\  :|  `--,  " + RESET);
        Delay();
        System.out.println(PURPLE + "                          |  | --'|  | |  ||  |\\  \\ |  | |  ||  '--'  /|  `---. " + RESET);
        Delay();
        System.out.println(ORANGE + "                          `--'    `--' `--'`--' '--'`--' `--'`-------' `------' " + RESET);
        System.out.println();
        Delay();
        Delay();

    }

    // Prints buffering ellipses
    public static void Ellipsis() {
        for (int i = 0; i < 4; i++) {
            Delay();
            System.out.print(".");
        }
        System.out.println("\n");
    }

    // Pauses the program to create a short delay
    public static void Delay(){
        try {
            Thread.sleep(400); // Wait 400 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
