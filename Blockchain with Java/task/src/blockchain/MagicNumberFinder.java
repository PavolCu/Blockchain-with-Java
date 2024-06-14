package blockchain;

import java.util.concurrent.Callable;


public class MagicNumberFinder implements Callable<Integer> {
    private final String prevHash;
    private final int start;
    private final int end;
    private final int N;

    MagicNumberFinder(String prevHash, int start, int end, int N) {
        this.prevHash = prevHash;
        this.start = start;
        this.end = end;
        this.N = N;
    }

    @Override
    public Integer call() {
        for (int i = start; i < end; i++) {
            String hash = StringUtil.applySha256(prevHash + i);
            if (hash.startsWith("0".repeat(N))) {
                return i;
            }
        }
        return -1;
    }
}
