/*
 * BrainFuck is an interesting language that consists of only 8 tokens and it's Turing Complete
 */

grammar BrainFuck;

top : prog;
prog : (loop | ops)*;

ops : INPUT | OUTPUT | INC | DEC | P_INC | P_DEC | LOOP_START | LOOP_END;
loop : LOOP_START prog LOOP_END;
INPUT : ',';
OUTPUT : '.';
INC : '+';
DEC : '-';
P_INC : '>';
P_DEC : '<';

LOOP_START : '[';
LOOP_END : ']';

WS : [ \n\t\r]+ -> skip;
