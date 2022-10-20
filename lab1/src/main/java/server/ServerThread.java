package server;

import utils.Constants;
import utils.Utils;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class ServerThread extends Thread {
    private final AsynchronousSocketChannel client;

    public ServerThread(String argument, AsynchronousSocketChannel client) throws ExecutionException, InterruptedException {
        this.client = client;
        Utils.writeMessage(client, argument);
    }

    @Override
    public void run() {
        boolean stopReceivingMessages = false;
        while (!stopReceivingMessages) {
            try {
                String message = Utils.readMessage(client);
                stopReceivingMessages = onMessageReceived(message);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private boolean onMessageReceived(String message) {
        if(message == null) {
            return false;
        }
        if(message.contains(Constants.F_FUNCTION_NAME + "=") || message.contains(Constants.G_FUNCTION_NAME + "=")) {
            Manager.writeResult(message.substring(0, 1), Integer.parseInt(message.substring(2)));
            return true;
        }
        if(message.contains("SoftFail")) {
            System.out.println(message);
            return false;
        }
        if(message.contains("HardFail")) {
            Manager.handleHardFail(message.substring("HardFail ".length()));
            return true;
        }
        return false;
    }
}
