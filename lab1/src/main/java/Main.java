
import org.jnativehook.NativeHookException;
import server.Manager;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, NativeHookException {
        System.out.print("Enter function argument: ");
        Scanner scanner = new Scanner(System.in);
        String argument = scanner.next();
        Manager.start(argument);
        scanner.close();
    }
}
