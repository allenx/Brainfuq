# Implementing Brainfuck with ANTLR 4

> **Brainfuq**, an interpreter for the language **Brainfuck** built with ANTLR



## Build & Run

There are currently no ready-to-use executable binary now but it's coming soon.

- Download jars of **ANTLR 4** and **Commons IO** and add them to your Java $CLASSPATH
- `git clone https://github.com/allenx/Brainfuq`
- `cd Brainfuq && javac *.java` and then you're good to go.
- Use the convinient scripts to run our interpreter: `brainfuck` to interpret a brainfuck file, and `brainfucki` to enter the magnificent Brainfuck REPL.

There are a few interesting demos in `./BrainfuckDemo/`, and you can just try them out.



## About Brainfuck

**Brainfuck** is an esoteric programming language. The language consists of only 8 simple commands and an instruction pointer, and it's one of the smallest Turing Complete programming languages, meaning it can literally do anything you do with other languages you use.

Below is the commands table for **Brainfuck**:

| Character | Meaning                                  |
| :-------: | :--------------------------------------- |
|    `>`    | Increment the data pointer (to point to the next cell to the right). |
|    `<`    | Decrement the data pointer (to point to the next cell to the left). |
|    `+`    | Increment (increase by one) the byte at the data pointer. |
|    `-`    | Decrement (decrease by one) the byte at the data pointer. |
|    `.`    | Output the byte at the data pointer.     |
|    `,`    | Accept one byte of input, storing its value in the byte at the data pointer. |
|    `[`    | If the byte at the data pointer is zero, then instead of moving the data pointer forward to the next command, jump it *forward* to the command after the *matching* `]` command. |
|    `]`    | If the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next command, jump it *back* to the command after the *matching* `[` command. |



## About ANTLR 4

**ANTLR** (ANother Tool for Language Recognition) is a powerful parser generator for reading, processing, executing, or translating structured text or binary files. It's widely used to build languages, tools, and frameworks. From a grammar, ANTLR generates a parser that can build and walk parse trees.

**ANTLR 4** is the backbone of **Brainfuq** and it's very powerful.



## Implementing the Grammar

**ANTLR 4** is so declarative we can use it to build amazing grammars just like writing Context Free Grammars in **Compiler Principle**.

So implementing Brainfuck is super easy just like below:

```c++
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
```

And then `antlr` this `.g4` file to generate the parser, tokens, visitor and lexer.



## Implementing the Interpreter

> Visiting Parse Tree (nodes and leaves)
>
> Reading Input
>
> Constructing REPL (an interactive shell)
>
> Sytactic Sugar

#### Visiting Parse Tree

After generating the parser, tokens, visitor(a visitor template) and lexer for brainfuck, it's time for us to walk through the Parse Tree manually.

Inside the class `BrainfuqBaseVisitor`, we can see four methods which return generic-typed values:

```java
@Override public T visitTop(BrainfuqParser.TopContext ctx) {return visitChildren(ctx);}
@Override public T visitProg(BrainfuqParser.ProgContext ctx) {return visitChildren(ctx);}
@Override public T visitOps(BrainfuqParser.OpsContext ctx) {return visitChildren(ctx);}
@Override public T visitLoop(BrainfuqParser.LoopContext ctx) {return visitChildren(ctx);}
```

These are the visiting methods we should override and implement in our own way.

So it's time to write a custom class extending `BrainfuqBaseVisitor`. Let's call it `BFInterpreter`. 

```java
public class BFInterpreter extends BrainfuqBaseVisitor {
    //Overriding those visiting methods
    @Override
    public Object visitOps(BrainfuqParser.OpsContext ctx) {
        switch (ctx.op.getType()) {
            case BrainfuqLexer.INPUT:
                tape[dataPointer] = (byte) sc.next().charAt(0);
                break;
            //Other cases are omitted because it'll be too long to cover all the other code in here
        }
        return null;
    }
}
```



#### Reading Input

User input is an essential part of an interactive program. Since we are building a command line tool, which is also what most language interpreters offers. 

Building a command line tool with Java is fun. And building a good one takes time.

There are two ways to use Brainfuq, one is `brainfuck filename` which interprets a whole brainfuck text file and print out the result, and the other one is `brainfucki` which brings the user into the Brainfuck **REPL Evironment**. 

To implement `brainfuck filename`, we can just simply read in the entry argument of `public static void main(String args[])` and treat is as the file name and then read in the file as file stream as follows:

```java
FileInputStream inStream = new FileInputStream(args[0]);
final ANTLRInputStream input = new ANTLRInputStream(code);
BrainfuqLexer lexer = new BrainfuqLexer(input);
CommonTokenStream tokens = new CommonTokenStream(lexer);
BrainfuqParser parser = new BrainfuqParser(tokens);
BFInterpreter().visit(parser.top());
//Simple enough right?
```

What's taking up most of the time is building the **REPL Evironment**.



#### Constructing REPL (an interactive shell)

> REPL: `(loop (print (eval (read))))` 

Above is a LISP style definition of **REPL**. In case you don't know, **REPL** is short for **Read-Eval-Print Loop**, basically it's like read a line, evaluate the line, print the result and loop from the start.

Language learners love **REPL**, it's super convenient for trying out grammars and simple code.

So, how do we construct the **REPL**?

A simple `while (true)` loop in Java will do the loop thing of course.

Below is how Brainfuq reads code line by line:

```java
Scanner commands = new Scanner(System.in);
while (true) {
	String command = commands.next();
	//This command is a line of code
}
```

Then we interprete this single line of command. Oh and, you would always want to reset the status of the data pointer and in **Brainfuck** because you want every loop to start from a clean state. So we do:

```java
bfInt.dataPointer = 0;
bfInt.tape = null;
bfInt.tape = new byte[bfInt.LENGTH];
```

And we want to print colored text in our **REPL** so that the user won't get bored and confused. Code Highlight is so important after all.

How do we print colored text using Java's `System.out.print()`? The answer is  `ANSI Color`. 

```Java
//ANSI COLORS
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

//Now this BRAINFUCK has red color on "fuck"
final String BRAINFUCK = "Brain" + ANSI_RED + "fuck" + ANSI_RESET;
```

By using `ANSI Colors` we made this happen:![brainfucki](https://raw.githubusercontent.com/allenx/Brainfuq/master/images/brainfucki.png)

#### Syntactic Sugar

> We added some emoji flavors to our beloved **Brainfuck**. **Brainfuq** now has full support for pure emoji (unicode) and  mixed-style programming.

Everyone loves syntactic sugars as they make coding easy and fun. So I was thinking what about adding support for emoji just like what Swift (Apple's open source language) did.

Now **Brainfuck** has a new, interesting, and intuitive command table:

| Character | Meaning                                  |
| :-------: | :--------------------------------------- |
| `>` or 👉 | Increment the data pointer (to point to the next cell to the right). |
| `<` or 👈 | Decrement the data pointer (to point to the next cell to the left). |
| `+` or 👆 | Increment (increase by one) the byte at the data pointer. |
| `-` or 👇 | Decrement (decrease by one) the byte at the data pointer. |
| `.` or 🌚 | Output the byte at the data pointer.     |
| `,` or 🌝 | Accept one byte of input, storing its value in the byte at the data pointer. |
| `[` or 🌜 | If the byte at the data pointer is zero, then instead of moving the data pointer forward to the next command, jump it *forward* to the command after the *matching* `]` command. |
| `]` or 🌛 | If the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next command, jump it *back* to the command after the *matching* `[` command. |

And below is our emoji version of `Hello World`:

```java
👆👆👆👆👆👆👆👆🌜👉👆👆👆👆🌜👉👆👆👉👆👆👆👉👆👆👆👉👆👈👈👈👈👇🌛👉👆👉👆👉👇👉👉👆🌜👈🌛👈👇🌛👉👉🌚👉👇👇👇🌚👆👆👆👆👆👆👆🌚🌚👆👆👆🌚👉👉🌚👈👇🌚👈🌚👆👆👆🌚👇👇👇👇👇👇🌚👇👇👇👇👇👇👇👇🌚👉👉👆🌚👉👆👆🌚
```

Achieving this is actually easy.

You may be thinking that, should we write another `.g4` file like `brainfuckEmoji.g4`? I actually tried that. And sadly it did not work out because the parser and lexer generated by **ANTLR 4** does not recognize unicode emoji at all!

So a little trick is need here. 

We do it by character replacing (so easy that I'm even a little bit shy to admit it).

We read in the code, change the emoji (if there are any emojis) into it's coresponding token and then lex and parse it. All set.



## TODO

> Command Stacks
>
> Better Error Handling

When you're working with a Terminal, you would usually use your up and down arrow key to navigate through all your history commands in your **command stacks**.

We want to do this in **Brainfuq REPL** too, I mean, although many language interpreters actually don't support this feature because of some limitations, I would still want to do this. 

In Java command line applications, detecting arrow keys are nasty. So this is a **TODO**

About **Better Error Handling**, here's thing: right now **Brainfuq REPL** has a major problem, that is, when you input an unfinished line of code and you would like to hit `Enter` to continue the code in a new line, **Brainfuq** does not know you're continuing your code. It just interprets your unfinished code, which is the first line as soon as you press `Enter`(`\n`). And what **Brainfuq** needs to do is analyze your code and know it's unfinished and only interpret the code if the code is finished (The AST is complete). This is another **TODO**



