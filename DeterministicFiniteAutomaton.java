import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * (C) Copyright 2020
 */
public final class DeterministicFiniteAutomaton implements DFA {
    private Set<String> finalStates;
    private HashMap<String, Set<String>> transitions;
    private HashMap<String, Set<String>> reversedTransitions;
    private String source;

    private String srcTransition;
    private boolean hasCycles;

    /**
     * Abstractizarea Automatului sub forma unui graf reprezentat prin liste de adiacență.
     */
    public DeterministicFiniteAutomaton() {
        finalStates = new HashSet<>();
        transitions = new HashMap<>();
        reversedTransitions = new HashMap<>();
    }

    /**
     * Adaugă starea inițială a automatului.
     *
     * @param src = numele sursei
     */
    @Override
    public void addSource(final String src) {
        source = src;
    }

    /**
     * Adaugă o stare finală a automatului în mulțimea specifică.
     *
     * @param dst = numele stării finale
     */
    @Override
    public void addFinalState(final String dst) {
        finalStates.add(dst);
    }

    /**
     * Reține sursa tranziției curente.
     *
     * @param src = numele stării sursă a tranziției
     */
    @Override
    public void addTransitionSrc(final String src) {
        srcTransition = src;
    }

    /**
     * Împreună cu sursa tranziției, adaugă o muchie în graful Automatului.
     *
     * @param dst = numele stării destinație a tranziției
     */
    @Override
    public void addTransitionDst(final String dst) {
        if (transitions.containsKey(srcTransition)) {
            transitions.get(srcTransition).add(dst);
        } else {
            Set<String> aux = new HashSet<>();
            aux.add(dst);
            transitions.put(srcTransition, aux);
        }

        if (reversedTransitions.containsKey(dst)) {
            reversedTransitions.get(dst).add(srcTransition);
        } else {
            Set<String> aux = new HashSet<>();
            aux.add(srcTransition);
            reversedTransitions.put(dst, aux);
        }
    }

    /**
     * Verifică dacă Automatul acceptă șirul vid i.e. starea inițială este stare finală.
     *
     * @return true,  dacă automatul acceptă șirul vid
     *         false, altfel
     */
    @Override
    public boolean isSourceFinal() {
        return finalStates.contains(source);
    }

    /**
     * Întoarce stările accesibile ale automatului printr-un DFS recursiv din sursă.
     *
     * @return mulțimea stărilor accesibile ale automatului
     */
    @Override
    public Set<String> getAStates() {
        Set<String> visited = new HashSet<>();

        dfsAStates(source, visited);

        return visited;
    }

    /**
     * Întoarce stările utile ale automatului, prin DFS-uri recursive din stările finale, pe
     * graful transpus.
     *
     * @return mulțimea stărilor utile ale automatului
     */
    @Override
    public Set<String> getUStates() {
        Set<String> visited = new HashSet<>();
        Set<String> aStates = getAStates();

        for (String s : finalStates) {
            if (!visited.contains(s) && aStates.contains(s)) {
                dfsUStates(s, visited, aStates);
            }
        }

        return visited;
    }

    /**
     * Verifică dacă limbajul Automatului este vid.
     *
     * @return true,  dacă limbajul generat de automat este vid
     *         false, altfel
     */
    @Override
    public boolean voidLanguage() {
        Set<String> uStates = getUStates();

        return uStates.isEmpty();
    }

    /**
     * Verifică dacă limbajul Automatului este finit, adică dacă nu conține cicluri în calea
     * curentă din parcurgerea DFS.
     *
     * @return true,  dacă limbajul generat de automat este finit
     *         false, altfel
     */
    @Override
    public boolean isFinite() {
        Set<String> visited = new HashSet<>();
        Set<String> path = new HashSet<>();

        Set<String> uStates = getUStates();
        if (voidLanguage()) {
            return true;
        }

       findCycles(source, visited, uStates, path);

        return !hasCycles;
    }

    /**
     * Funcție ajutătoare.
     * Realizează o parcurgere DFS din sursă pentru a descoperi stările accesibile.
     *
     * @param node    numele stării curente în parcurgere
     * @param visited vectorul de noduri vizitate
     */
    private void dfsAStates(final String node, final Set<String> visited) {
        visited.add(node);

        if (!transitions.containsKey(node)) {
            return;
        }

        for (String v : transitions.get(node)) {
            if (!visited.contains(v)) {
                visited.add(v);
                dfsAStates(v, visited);
            }
        }
    }

    /**
     * Funcție ajutătoare.
     * Realizează o parcurgere DFS dintr-o stare finală pentru a descoperi stările utile.
     *
     * @param node    numele stării curente în parcurgere
     * @param visited vectorul de noduri vizitate
     * @param aStates mulțimea stărilor accesibile
     */
    private void dfsUStates(final String node, final Set<String> visited,
                            final Set<String> aStates) {
        visited.add(node);

        if (!reversedTransitions.containsKey(node)) {
            return;
        }

        for (String v : reversedTransitions.get(node)) {
            if (!visited.contains(v) && aStates.contains(v)) {
                visited.add(v);
                dfsUStates(v, visited, aStates);
            }
        }
    }

    /**
     * Funcție ajutătoare.
     * Realizează o parcurgere DFS pentru a descoperi ciclurile ce conțin stări utile.
     *
     * @param node    numele stării curente în parcurgere
     * @param visited vectorul de noduri vizitate
     * @param uStates mulțimea stărilor utile
     * @param path    calea curentă urmată de DFS
     */
    private void findCycles(final String node, final Set<String> visited,
                            final Set<String> uStates, final Set<String> path) {
        visited.add(node);
        path.add(node);

        if (!transitions.containsKey(node)) {
            return;
        }

        for (String v : transitions.get(node)) {
            if (!visited.contains(v)) {
                visited.add(v);
                path.add(v);
                findCycles(v, visited, uStates, path);
                path.remove(v);
            } else if (uStates.contains(v) && path.contains(v)) {
                hasCycles = true;
                return;
            }
        }
    }
}
