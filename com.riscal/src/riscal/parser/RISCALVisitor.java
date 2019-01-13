// Generated from RISCAL.g4 by ANTLR 4.5.3

  package riscal.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RISCALParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RISCALVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RISCALParser#specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecification(RISCALParser.SpecificationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ValueDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueDeclaration(RISCALParser.ValueDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(RISCALParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateDeclaration(RISCALParser.PredicateDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ProcedureDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDeclaration(RISCALParser.ProcedureDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDefinition(RISCALParser.TypeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecTypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecTypeDefinition(RISCALParser.RecTypeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EnumTypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumTypeDefinition(RISCALParser.EnumTypeDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ValueDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueDefinition(RISCALParser.ValueDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateValueDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateValueDefinition(RISCALParser.PredicateValueDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TheoremDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTheoremDefinition(RISCALParser.TheoremDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(RISCALParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PredicateDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicateDefinition(RISCALParser.PredicateDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ProcedureDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDefinition(RISCALParser.ProcedureDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TheoremParamDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTheoremParamDefinition(RISCALParser.TheoremParamDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RequiresSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequiresSpec(RISCALParser.RequiresSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EnsuresSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnsuresSpec(RISCALParser.EnsuresSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecreasesSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecreasesSpec(RISCALParser.DecreasesSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ContractSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContractSpec(RISCALParser.ContractSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyCommand(RISCALParser.EmptyCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentCommand(RISCALParser.AssignmentCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChooseCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseCommand(RISCALParser.ChooseCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoWhileCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileCommand(RISCALParser.DoWhileCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarCommand(RISCALParser.VarCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ValCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValCommand(RISCALParser.ValCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssertCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertCommand(RISCALParser.AssertCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintCommand(RISCALParser.PrintCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Print2Command}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint2Command(RISCALParser.Print2CommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CheckCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckCommand(RISCALParser.CheckCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpCommand(RISCALParser.ExpCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SemicolonCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSemicolonCommand(RISCALParser.SemicolonCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChooseElseCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseElseCommand(RISCALParser.ChooseElseCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfThenCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenCommand(RISCALParser.IfThenCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfThenElseCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseCommand(RISCALParser.IfThenElseCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MatchCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchCommand(RISCALParser.MatchCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileCommand(RISCALParser.WhileCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCommand(RISCALParser.ForCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForInCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInCommand(RISCALParser.ForInCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChooseDoCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseDoCommand(RISCALParser.ChooseDoCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandSequence}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandSequence(RISCALParser.CommandSequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InvariantLoopSpec}
	 * labeled alternative in {@link RISCALParser#loopspec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvariantLoopSpec(RISCALParser.InvariantLoopSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DecreasesLoopSpec}
	 * labeled alternative in {@link RISCALParser#loopspec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecreasesLoopSpec(RISCALParser.DecreasesLoopSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExp(RISCALParser.IdentifierExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ProductExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProductExp(RISCALParser.ProductExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExp(RISCALParser.NotExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TimesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimesExp(RISCALParser.TimesExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CartesianExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCartesianExp(RISCALParser.CartesianExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsExp(RISCALParser.EqualsExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BigUnionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBigUnionExp(RISCALParser.BigUnionExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExp(RISCALParser.PrintExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseExp(RISCALParser.FalseExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecIdentifierExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecIdentifierExp(RISCALParser.RecIdentifierExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationExp(RISCALParser.NegationExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInSetExp(RISCALParser.InSetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleExp(RISCALParser.TupleExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExp(RISCALParser.OrExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessEqualExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessEqualExp(RISCALParser.LessEqualExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessExp(RISCALParser.LessExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenthesizedExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExp(RISCALParser.ParenthesizedExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LetParExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetParExp(RISCALParser.LetParExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssertExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertExp(RISCALParser.AssertExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotEqualsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqualsExp(RISCALParser.NotEqualsExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetBuilderExp(RISCALParser.SetBuilderExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetSizeExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetSizeExp(RISCALParser.SetSizeExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EnumeratedSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumeratedSetExp(RISCALParser.EnumeratedSetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForallExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForallExp(RISCALParser.ForallExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpliesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpliesExp(RISCALParser.ImpliesExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MinusExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusExp(RISCALParser.MinusExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MatchExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchExp(RISCALParser.MatchExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SumExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumExp(RISCALParser.SumExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueExp(RISCALParser.TrueExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteralExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteralExp(RISCALParser.IntLiteralExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChooseInExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseInExp(RISCALParser.ChooseInExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExp(RISCALParser.NumberExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayUpdateExp(RISCALParser.ArrayUpdateExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArraySelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArraySelectionExp(RISCALParser.ArraySelectionExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapBuilderExp(RISCALParser.MapBuilderExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TimesExpMult}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimesExpMult(RISCALParser.TimesExpMultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExp(RISCALParser.AndExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChooseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseExp(RISCALParser.ChooseExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterExp(RISCALParser.GreaterExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ApplicationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplicationExp(RISCALParser.ApplicationExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerSetExp(RISCALParser.PowerSetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExistsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistsExp(RISCALParser.ExistsExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionExp(RISCALParser.UnionExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubsetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubsetExp(RISCALParser.SubsetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordExp(RISCALParser.RecordExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerSet2Exp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerSet2Exp(RISCALParser.PowerSet2ExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MaxExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaxExp(RISCALParser.MaxExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayBuilderExp(RISCALParser.ArrayBuilderExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EquivExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquivExp(RISCALParser.EquivExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MinExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinExp(RISCALParser.MinExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleUpdateExp(RISCALParser.TupleUpdateExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BigIntersectExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBigIntersectExp(RISCALParser.BigIntersectExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetExp(RISCALParser.LetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PlusExpMult}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusExpMult(RISCALParser.PlusExpMultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FactorialExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactorialExp(RISCALParser.FactorialExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RemainderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRemainderExp(RISCALParser.RemainderExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecApplicationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecApplicationExp(RISCALParser.RecApplicationExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CheckExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCheckExp(RISCALParser.CheckExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PlusExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusExp(RISCALParser.PlusExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleSelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleSelectionExp(RISCALParser.TupleSelectionExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerSet1Exp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerSet1Exp(RISCALParser.PowerSet1ExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordUpdateExp(RISCALParser.RecordUpdateExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfThenElseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseExp(RISCALParser.IfThenElseExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordSelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordSelectionExp(RISCALParser.RecordSelectionExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntervalExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntervalExp(RISCALParser.IntervalExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WithoutExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWithoutExp(RISCALParser.WithoutExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptySetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptySetExp(RISCALParser.EmptySetExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintInExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintInExp(RISCALParser.PrintInExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DividesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDividesExp(RISCALParser.DividesExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntersectExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntersectExp(RISCALParser.IntersectExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterEqualExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterEqualExp(RISCALParser.GreaterEqualExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnitExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitExp(RISCALParser.UnitExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ChooseInElseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChooseInElseExp(RISCALParser.ChooseInElseExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExp(RISCALParser.PowerExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnitType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnitType(RISCALParser.UnitTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(RISCALParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(RISCALParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(RISCALParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleType(RISCALParser.TupleTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordType(RISCALParser.RecordTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NatType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNatType(RISCALParser.NatTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetType(RISCALParser.SetTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(RISCALParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierType(RISCALParser.IdentifierTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QuantifiedVariable}
	 * labeled alternative in {@link RISCALParser#qvar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifiedVariable(RISCALParser.QuantifiedVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierTypeQuantifiedVar}
	 * labeled alternative in {@link RISCALParser#qvcore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierTypeQuantifiedVar(RISCALParser.IdentifierTypeQuantifiedVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierSetQuantifiedVar}
	 * labeled alternative in {@link RISCALParser#qvcore}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierSetQuantifiedVar(RISCALParser.IdentifierSetQuantifiedVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link RISCALParser#binder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinder(RISCALParser.BinderContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierPatternCommand(RISCALParser.IdentifierPatternCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ApplicationPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplicationPatternCommand(RISCALParser.ApplicationPatternCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefaultPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultPatternCommand(RISCALParser.DefaultPatternCommandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierPatternExp(RISCALParser.IdentifierPatternExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ApplicationPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplicationPatternExp(RISCALParser.ApplicationPatternExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DefaultPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultPatternExp(RISCALParser.DefaultPatternExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link RISCALParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(RISCALParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link RISCALParser#ritem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRitem(RISCALParser.RitemContext ctx);
	/**
	 * Visit a parse tree produced by {@link RISCALParser#eident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEident(RISCALParser.EidentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecIdentifier}
	 * labeled alternative in {@link RISCALParser#rident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecIdentifier(RISCALParser.RecIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecApplication}
	 * labeled alternative in {@link RISCALParser#rident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecApplication(RISCALParser.RecApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapSelector(RISCALParser.MapSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleSelector(RISCALParser.TupleSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RecordSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordSelector(RISCALParser.RecordSelectorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsNotMultiple}
	 * labeled alternative in {@link RISCALParser#multiple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotMultiple(RISCALParser.IsNotMultipleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IsMultiple}
	 * labeled alternative in {@link RISCALParser#multiple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsMultiple(RISCALParser.IsMultipleContext ctx);
	/**
	 * Visit a parse tree produced by {@link RISCALParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(RISCALParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link RISCALParser#decimal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(RISCALParser.DecimalContext ctx);
}