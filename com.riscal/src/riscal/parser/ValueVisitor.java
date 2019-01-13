// Generated from Value.g4 by ANTLR 4.5.3

  package riscal.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ValueParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ValueVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ValueParser#valueFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueFile(ValueParser.ValueFileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Unit}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(ValueParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code True}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(ValueParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code False}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(ValueParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecimalNumber}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimalNumber(ValueParser.DecimalNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegDecimalNumber}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegDecimalNumber(ValueParser.NegDecimalNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Set}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet(ValueParser.SetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Map}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(ValueParser.MapContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Array}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(ValueParser.ArrayContext ctx);
}