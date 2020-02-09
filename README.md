# Deterministic-Finite-Automaton
Deterministic Finite Automaton analyzer using JFlex

The program gets a DFA M = (K,Σ,δ,s,F) as input and answers the following questions about it:
 * -v : is the accepted language void?
 * -e : does the accepted language contain the empty string?
 * -a : which are the accessible states?
 * -u : which are the usefull states?
 * -f : is the accepted language finite?

How to run? make run arg=$(arg), where arg is one of the above.
