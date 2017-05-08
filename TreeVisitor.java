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


public class TreeVisitor extends BrainfuqBaseVisitor {

    private static Logger logger = Logger.getLogger(TreeVisitor.class.getName());

    private int LENGTH = 65443;
    protected byte[] tape = new byte[LENGTH];
    protected int dataPointer = 0;

    //For the input
    private Scanner sc = new Scanner(System.in);

//    public TreeVisitor() {
////        System.out.print(LENGTH);
////        System.out.print((char) tape[0]);
//    }

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
//            tape[dataPointer] = 0;
        }


        return null;

    }


    public static void main(String[] args) throws IOException {
        TreeVisitor treeVisitor = new TreeVisitor();
        try {
            final FileInputStream inStream = new FileInputStream(args[0]);
            final ANTLRInputStream input = new ANTLRInputStream(inStream);
            BrainfuqLexer lexer = new BrainfuqLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            BrainfuqParser parser = new BrainfuqParser(tokens);
            treeVisitor.visit(parser.top());

//            logger.log(Level.INFO, "p: {0}; tape: {1}", new Object[] {
//                    treeVisitor.p,
//                    Arrays.toString(treeVisitor.tape)
//            });
        } catch (IOException foo) {
            foo.printStackTrace();
        }
    }
}