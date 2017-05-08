// Generated from Brainfuq.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BrainfuqParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BrainfuqVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BrainfuqParser#top}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop(BrainfuqParser.TopContext ctx);
	/**
	 * Visit a parse tree produced by {@link BrainfuqParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(BrainfuqParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link BrainfuqParser#ops}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOps(BrainfuqParser.OpsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BrainfuqParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(BrainfuqParser.LoopContext ctx);
}