// Generated from /Users/ray/workspace/java/NewJava/src/main/resources/Graph.g4 by ANTLR 4.9.1
package main.antlr.autogen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GraphParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GraphVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GraphParser#graph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraph(GraphParser.GraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphParser#vertex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVertex(GraphParser.VertexContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraphParser#edge}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdge(GraphParser.EdgeContext ctx);
}