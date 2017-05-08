// Generated from Brainfuq.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BrainfuqParser}.
 */
public interface BrainfuqListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BrainfuqParser#top}.
	 * @param ctx the parse tree
	 */
	void enterTop(BrainfuqParser.TopContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuqParser#top}.
	 * @param ctx the parse tree
	 */
	void exitTop(BrainfuqParser.TopContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuqParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(BrainfuqParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuqParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(BrainfuqParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuqParser#ops}.
	 * @param ctx the parse tree
	 */
	void enterOps(BrainfuqParser.OpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuqParser#ops}.
	 * @param ctx the parse tree
	 */
	void exitOps(BrainfuqParser.OpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuqParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(BrainfuqParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuqParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(BrainfuqParser.LoopContext ctx);
}