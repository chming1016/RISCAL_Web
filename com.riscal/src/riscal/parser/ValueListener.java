// Generated from Value.g4 by ANTLR 4.5.3

  package riscal.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ValueParser}.
 */
public interface ValueListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ValueParser#valueFile}.
	 * @param ctx the parse tree
	 */
	void enterValueFile(ValueParser.ValueFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValueParser#valueFile}.
	 * @param ctx the parse tree
	 */
	void exitValueFile(ValueParser.ValueFileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Unit}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterUnit(ValueParser.UnitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Unit}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitUnit(ValueParser.UnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code True}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterTrue(ValueParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code True}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitTrue(ValueParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code False}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterFalse(ValueParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code False}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitFalse(ValueParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DecimalNumber}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterDecimalNumber(ValueParser.DecimalNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DecimalNumber}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitDecimalNumber(ValueParser.DecimalNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegDecimalNumber}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNegDecimalNumber(ValueParser.NegDecimalNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegDecimalNumber}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNegDecimalNumber(ValueParser.NegDecimalNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Set}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterSet(ValueParser.SetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Set}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitSet(ValueParser.SetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Map}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterMap(ValueParser.MapContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Map}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitMap(ValueParser.MapContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Array}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void enterArray(ValueParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Array}
	 * labeled alternative in {@link ValueParser#value}.
	 * @param ctx the parse tree
	 */
	void exitArray(ValueParser.ArrayContext ctx);
}