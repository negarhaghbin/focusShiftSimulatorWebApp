package com.testWebApp.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <12.Jan.2021>
 */
public class OutputManager {

    private PrintStream prSt;
    private final PrintStream console;

    public OutputManager() {
        try {
            prSt = new PrintStream(new File("log_file.txt"));
        } catch (FileNotFoundException ex) {
            System.err.print(ex.getMessage());
        }
        console = System.out;
    }

    public void printMessage(String msg) {
        try {
            // write to the file:
            System.setOut(prSt);
            System.out.println(msg);

            // write to the console:
            System.setOut(console);
            System.out.println(msg);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
