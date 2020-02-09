/**
 * (C) Copyright 2020
 */
%%

%class Flexer
%unicode
/*%debug*/
%int
/*%line*/
/*%column*/

%{
    private DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton();

    public DeterministicFiniteAutomaton getDfa() {
        return dfa;
    }
%}


LineTerminator = \r|\n|\r\n
WS = {LineTerminator} | [ \t\f]
special = "`"|"-"|"="|"["|"]"|";"|"'"|"\\"|"."|"/"|"~"|"!"|"@"|"#"|"$"|"%"|"^"|"&"|"*"|"_"|"+"|":"|"\""|"|"|"<"|">"|"?"
Symbol = [:uppercase:] | [:lowercase:] | [:digit:] | {special}
Name = ([:uppercase:] | [:lowercase:] | [:digit:] | "_")+

%state STARTK ELEMK STOPK SEPKS STARTS INITS ELEMS STOPS SEPSD STARTD INITD ELEMD SRCT SEPSST SYMT SEPSDT DESTT ENDT ENDD STOPD SEPDS SS SEPSF STARTF INITF ELEMF STOPF ENDDFA FINAL

%state DFA STATES STSEP ALPHABET ALSEP DELTA DSEP START STASE STOP STOSE

%%
{WS}	{}
<YYINITIAL>"(" {
    yybegin(STARTK);
}

<STARTK> "{" {
    yybegin(ELEMK);
}

<ELEMK> {Name} {
	yybegin(STOPK);
}

<STOPK> {
    "}" {
        yybegin(SEPKS);
    }
    ","	{
        yybegin(ELEMK);
    }
}

<SEPKS> "," {
    yybegin(STARTS);
}

<STARTS> "{" {
    yybegin(INITS);
}

<INITS> "}" {
    yybegin(SEPSD);
}

<ELEMS, INITS> {Symbol}	{
    yybegin(STOPS);
}

<STOPS> {
    ","	{
        yybegin(ELEMS);
    }
    "}" {
        yybegin(SEPSD);
    }
}

<SEPSD> "," {
    yybegin(STARTD);
}

<STARTD> "(" {
    yybegin(INITD);
}

<INITD> ")" {
    yybegin(SEPDS);
}

<ELEMD,INITD> "(" {
    yybegin(SRCT);
}

<SRCT> {Name} {
    dfa.addTransitionSrc(yytext());
    yybegin(SEPSST);
}


<SEPSST> "," {
    yybegin(SYMT);
}

<SYMT> {Symbol} {
    yybegin(SEPSDT);
}

<SEPSDT> "," {
    yybegin(DESTT);
}

<DESTT> {Name} {
    dfa.addTransitionDst(yytext());
    yybegin(ENDT);
}

<ENDT> ")" {
    yybegin(STOPD);
}

<STOPD> {
    "," {
        yybegin(ELEMD);
    }
    ")" {
        yybegin(SEPDS);
    }
}

<SEPDS> "," {
    yybegin(SS);
}

<SS> {Name} {
    dfa.addSource(yytext());
    yybegin(SEPSF);
}

<SEPSF> "," {
    yybegin(STARTF);
}

<STARTF> "{" {
    yybegin(INITF);
}

<INITF> "}" {
    yybegin(ENDDFA);
}

<INITF,ELEMF> {Name} {
    dfa.addFinalState(yytext());
    yybegin(STOPF);
}

<STOPF> {
    "," {
        yybegin(ELEMF);
    }
    "}" {
        yybegin(ENDDFA);
    }
}
<ENDDFA> ")" {
    return 0;
}

. {
    System.err.println("Syntax error");
    System.exit(0);
}
