package clients;

import functions.IntOps;
import utils.Constants;
import utils.Utils;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Optional;
import java.util.concurrent.*;

public class ProcessF {

    private static final int maxAttempts = 5;
    private static int currentAttempt = 0;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel socketChannel = Utils.setupSocketChannel();
        int argument = Integer.parseInt(Utils.readMessage(socketChannel));
        while (currentAttempt < maxAttempts) {
            Optional<Optional<Integer>> result = IntOps.trialF(argument);
            if(result.isPresent()) {
                Optional<Integer> answer = result.get();
                if(answer.isPresent()) {
                    Utils.writeMessage(socketChannel, Constants.F_FUNCTION_NAME + "=" + answer.get());
                } else {
                    Utils.writeMessage(socketChannel, "HardFail " + Constants.F_FUNCTION_NAME);
                }
                return;
            }
            Utils.writeMessage(socketChannel, "SoftFail " + Constants.F_FUNCTION_NAME
                    + ", attempt " + (currentAttempt + 1) + " of " + maxAttempts);
            ++currentAttempt;
            Thread.sleep(10);
        }
        Utils.writeMessage(socketChannel, "HardFail " + Constants.F_FUNCTION_NAME);
    }
}
