import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;
import java.util.*;

import org.antlr.runtime.TokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;


public class BFInterpreter extends BrainfuqBaseVisitor {

    //private static Logger logger = Logger.getLogger(BFInterpreter.class.getName());

    private int LENGTH = 65443;
    byte[] tape = new byte[LENGTH];
    int dataPointer = 0;

    //For the input
    private Scanner sc = new Scanner(System.in);


    @Override
    public Object visitTop(BrainfuqParser.TopContext ctx) {

//        System.out.print("Enterted Top\n");
        visitChildren(ctx);
        return null;
    }

    @Override
    public Object visitProg(BrainfuqParser.ProgContext ctx) {

//        System.out.print("Enterted Prog\n");
        visitChildren(ctx);
        return null;
    }


    @Override
    public Object visitOps(BrainfuqParser.OpsContext ctx) {

//        System.out.print("Enterted Ops\n");
        switch (ctx.op.getType()) {
            case BrainfuqLexer.INPUT:
                tape[dataPointer] = (byte) sc.next().charAt(0);
                break;
            case BrainfuqLexer.OUTPUT:
                System.out.print((char) tape[dataPointer]);
                break;
            case BrainfuqLexer.INC:
                tape[dataPointer]++;
                break;
            case BrainfuqLexer.DEC:
                tape[dataPointer]--;
                break;
            case BrainfuqLexer.P_INC:
                dataPointer = (dataPointer == LENGTH-1) ? 0 : dataPointer + 1;
                break;
            case BrainfuqLexer.P_DEC:
                dataPointer = (dataPointer == 0) ? LENGTH-1 : dataPointer - 1;
                break;
        }

        return null;
    }

    @Override
    public Object visitLoop(BrainfuqParser.LoopContext ctx) {

//        System.out.print("Enterted Loop\n");
        while (tape[dataPointer] != 0) {
            visitChildren(ctx);
        }


        return null;

    }


    public static void main(String[] args) throws IOException {
        BFInterpreter bfInt = new BFInterpreter();

        if (args[0].equals("-i")) {

            //Stack<String> commandStack = new Stack<String>();
    
            Scanner commands = new Scanner(System.in);
            System.out.print("Welcome to Brainfuck Interactive REPL :)\n");


            while (true) {
                System.out.print("BF >> ");
                String command = commands.next();
                
                //TODO: Implement a Command Stack and detect arrow keys to select through previous commands
                //if (command.equals("^[[A")) {
                    //System.out.print("foooo");
                //}
                //commandStack.push(command);
                System.out.print("\n    ");
                final ANTLRInputStream input = new ANTLRInputStream(command);
                BrainfuqLexer lexer = new BrainfuqLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                BrainfuqParser parser = new BrainfuqParser(tokens);
                bfInt.visit(parser.top());
                System.out.print("\n\n");

                bfInt.dataPointer = 0;
                bfInt.tape = null;
                bfInt.tape = new byte[bfInt.LENGTH];

            }
                
        } else {
            try {
            final FileInputStream inStream = new FileInputStream(args[0]);
            final ANTLRInputStream input = new ANTLRInputStream(inStream);
            BrainfuqLexer lexer = new BrainfuqLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            BrainfuqParser parser = new BrainfuqParser(tokens);
            bfInt.visit(parser.top());

//            logger.log(Level.INFO, "p: {0}; tape: {1}", new Object[] {
//                    bfInt.p,
//                    Arrays.toString(bfInt.tape)
//            });
            } catch (IOException foo) {
                foo.printStackTrace();
            }

        }

    }
}
