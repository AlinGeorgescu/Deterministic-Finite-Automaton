import java.util.Set;

/**
 * (C) Copyright 2020
 */
public interface DFA {
    /**
     * Adaugă starea inițială a automatului.
     *
     * @param src = numele sursei
     */
    void addSource(String src);

    /**
     * Adaugă o stare finală a automatului în mulțimea specifică.
     *
     * @param dst = numele stării finale
     */
    void addFinalState(String dst);

    /**
     * Reține sursa tranziției curente.
     *
     * @param src = numele stării sursă a tranziției
     */
    void addTransitionSrc(String src);

    /**
     * Împreună cu sursa tranziției, adaugă o muchie în graful Automatului.
     *
     * @param dst = numele stării destinație a tranziției
     */
    void addTransitionDst(String dst);

    /**
     * Verifică dacă Automatul acceptă șirul vid i.e. starea inițială este stare finală.
     *
     * @return true,  dacă automatul acceptă șirul vid
     *         false, altfel
     */
    boolean isSourceFinal();

    /**
     * Întoarce stările accesibile ale automatului printr-un DFS recursiv din sursă.
     *
     * @return mulțimea stărilor accesibile ale automatului
     */
    Set<String> getAStates();

    /**
     * Întoarce stările utile ale automatului, prin DFS-uri recursive din stările finale, pe
     * graful transpus.
     *
     * @return mulțimea stărilor utile ale automatului
     */
    Set<String> getUStates();

    /**
     * Verifică dacă limbajul Automatului este vid.
     *
     * @return true,  dacă limbajul generat de automat este vid
     *         false, altfel
     */
    boolean voidLanguage();

    /**
     * Verifică dacă limbajul Automatului este finit, adică dacă nu conține cicluri în calea
     * curentă din parcurgerea DFS.
     *
     * @return true,  dacă limbajul generat de automat este finit
     *         false, altfel
     */
    boolean isFinite();
}
