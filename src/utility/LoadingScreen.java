package utility;

public class LoadingScreen {

    // ANSI colour codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String ORANGE = "\u001B[38;5;208m";

    public static void run() {
        System.out.print("\nCreating the Wonderland");
        for (int i = 0; i < 4; i++) {
            Delay();
            System.out.print(".");
        }

        System.out.print("\n\nDecorating the streets");
        for (int i = 0; i < 4; i++) {
            Delay();
            System.out.print(".");
        }

        System.out.print("\n\nPreparing the costumes");
        for (int i = 0; i < 4; i++) {
            Delay();
            System.out.print(".");
        }
        Delay();

        System.out.println("\n\nDone!");
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

    private static void Delay(){
        try {
            Thread.sleep(500); // Wait 500 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
