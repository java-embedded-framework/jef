 

package ru.iothub.jef.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for execution
 */
public class ExampleExecutor {
    /**
     * Exec method
     * @param args not used
     * @throws Exception if something happen
     */
    public static void main(String[] args) throws Exception {
        boolean debug = "true".equals(System.getProperty("debug"));
        if(debug) {
            System.out.println("Examples runned in DEBUG mode");
        }

        for (; ; ) {
            cls();
            System.out.println("******************** Examples ********************");

            int key = 1;
            for (ExampleGroup group : ExampleManager.getExamples()) {
                System.out.println("--------------- " + group.getName()+ " ---------------");
                for (Example example : group.getItems()) {
                    System.out.println((key) + ". " + example.getName());
                    key++;
                }
            }

            System.out.println("**************************************************");
            System.out.println("Press a key for selecting example or 'q' to exit.");

            String line = readLine();
            if ("q".equalsIgnoreCase(line)) {
                //System.out.println("Exit from application");
                System.exit(0);
            }

            try {
                int index = Integer.parseInt(line) - 1;
                cls();
                ExampleManager.execute(index);
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
            }

        }

    }

    private static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Read line from console
     * @return readed string
     * @throws IOException if some I/O error happen
     */
    public static String readLine() throws IOException {
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
