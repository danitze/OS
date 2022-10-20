package functions;

import utils.Utils;

import java.util.Optional;

public class IntOps {
    public static Optional<Optional<Integer>> trialF(int argument) throws InterruptedException {
        int value = Utils.randomValue(10);
        if(value < 5) {
            return Optional.empty();
        }
        return Optional.of(os.lab1.compfuncs.basic.IntOps.trialF(argument));
    }

    public static Optional<Optional<Integer>> trialG(int argument) throws InterruptedException {
        int value = Utils.randomValue(10);
        if(value < 5) {
            return Optional.empty();
        }
        return Optional.of(os.lab1.compfuncs.basic.IntOps.trialG(argument));
    }
}
