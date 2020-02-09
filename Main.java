import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

/**
 * (C) Copyright 2020
 */
public final class Main {
    private static final int MAGIC_ONE   = 1;
    private static final int MAGIC_TWO   = 2;
    private static final int MAGIC_THREE = 3;
    private static final int MAGIC_FOUR  = 4;
    private static final int MAGIC_FIVE  = 5;
    private static DeterministicFiniteAutomaton dfa;

    private Main() {
    }

    public static void main(final String[] args) {
        if (args.length != 1) {
            System.err.println("Argument error");
            System.exit(1);
        }
        int mode = -1;
        switch (args[0]) {
            case "-e":
                mode = MAGIC_ONE;
                break;
            case "-a":
                mode = MAGIC_TWO;
                break;
            case "-u":
                mode = MAGIC_THREE;
                break;
            case "-v":
                mode = MAGIC_FOUR;
                break;
            case "-f":
                mode = MAGIC_FIVE;
                break;
            default:
                System.err.println("Argument error");
                System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("dfa"));
            Flexer scanner = new Flexer(br);
            scanner.yylex();
            br.close();
            dfa = scanner.getDfa();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        switch (mode) {
            case MAGIC_ONE:
                // -e
                if (dfa.isSourceFinal()) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                break;
            case MAGIC_TWO:
                // -a
                Set<String> aStates = dfa.getAStates();

                for (String s : aStates) {
                    System.out.println(s);
                }
                break;
            case MAGIC_THREE:
                // -u
                Set<String> uStates = dfa.getUStates();

                for (String s : uStates) {
                    System.out.println(s);
                }
                break;
            case MAGIC_FOUR:
                // -v
                if (dfa.voidLanguage()) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                break;
            case MAGIC_FIVE:
                // -f
                if (dfa.isFinite()) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                break;
            default:
                System.err.println("Argument error");
                System.exit(1);
        }

    }
}
