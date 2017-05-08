import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.runtime.TokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
//import BrainfuqLexer;
//import BrainfuqParser;

public class BrainfuckInterpreter {
    public static void main(String[] args) {
        try {
            final FileInputStream inStream = new FileInputStream(args[0]);
            final ANTLRInputStream input = new ANTLRInputStream(inStream);
            BrainfuqLexer lexer = new BrainfuqLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            BrainfuqParser parser = new BrainfuqParser(tokens);
            parser.top();
            System.out.println("parsed");
        } catch (IOException foo) {
            foo.printStackTrace();
        }
    }
}

