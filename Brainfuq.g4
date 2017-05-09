/*
 * Brainfuq is an interpreter for Brainfuck
 * Brainfuck is an interesting language that consists of only 8 tokens and it's Turing Complete
 */

grammar Brainfuq;

top : prog;
prog : (loop | ops)*;

ops : op=(INPUT | OUTPUT | INC | DEC | P_INC | P_DEC);
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
