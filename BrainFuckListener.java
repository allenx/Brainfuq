// Generated from BrainFuck.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BrainFuckParser}.
 */
public interface BrainFuckListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BrainFuckParser#top}.
	 * @param ctx the parse tree
	 */
	void enterTop(BrainFuckParser.TopContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainFuckParser#top}.
	 * @param ctx the parse tree
	 */
	void exitTop(BrainFuckParser.TopContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainFuckParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(BrainFuckParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainFuckParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(BrainFuckParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainFuckParser#ops}.
	 * @param ctx the parse tree
	 */
	void enterOps(BrainFuckParser.OpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainFuckParser#ops}.
	 * @param ctx the parse tree
	 */
	void exitOps(BrainFuckParser.OpsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainFuckParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(BrainFuckParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainFuckParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(BrainFuckParser.LoopContext ctx);
}