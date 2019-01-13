// Generated from RISCAL.g4 by ANTLR 4.5.3

  package riscal.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RISCALParser}.
 */
public interface RISCALListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RISCALParser#specification}.
	 * @param ctx the parse tree
	 */
	void enterSpecification(RISCALParser.SpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#specification}.
	 * @param ctx the parse tree
	 */
	void exitSpecification(RISCALParser.SpecificationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterValueDeclaration(RISCALParser.ValueDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitValueDeclaration(RISCALParser.ValueDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(RISCALParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(RISCALParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterPredicateDeclaration(RISCALParser.PredicateDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitPredicateDeclaration(RISCALParser.PredicateDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ProcedureDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDeclaration(RISCALParser.ProcedureDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ProcedureDeclaration}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDeclaration(RISCALParser.ProcedureDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDefinition(RISCALParser.TypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDefinition(RISCALParser.TypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecTypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterRecTypeDefinition(RISCALParser.RecTypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecTypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitRecTypeDefinition(RISCALParser.RecTypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EnumTypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumTypeDefinition(RISCALParser.EnumTypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EnumTypeDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumTypeDefinition(RISCALParser.EnumTypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterValueDefinition(RISCALParser.ValueDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitValueDefinition(RISCALParser.ValueDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateValueDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterPredicateValueDefinition(RISCALParser.PredicateValueDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateValueDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitPredicateValueDefinition(RISCALParser.PredicateValueDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TheoremDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterTheoremDefinition(RISCALParser.TheoremDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TheoremDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitTheoremDefinition(RISCALParser.TheoremDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(RISCALParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(RISCALParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PredicateDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterPredicateDefinition(RISCALParser.PredicateDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PredicateDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitPredicateDefinition(RISCALParser.PredicateDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ProcedureDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDefinition(RISCALParser.ProcedureDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ProcedureDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDefinition(RISCALParser.ProcedureDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TheoremParamDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterTheoremParamDefinition(RISCALParser.TheoremParamDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TheoremParamDefinition}
	 * labeled alternative in {@link RISCALParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitTheoremParamDefinition(RISCALParser.TheoremParamDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RequiresSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void enterRequiresSpec(RISCALParser.RequiresSpecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RequiresSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void exitRequiresSpec(RISCALParser.RequiresSpecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EnsuresSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void enterEnsuresSpec(RISCALParser.EnsuresSpecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EnsuresSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void exitEnsuresSpec(RISCALParser.EnsuresSpecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DecreasesSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void enterDecreasesSpec(RISCALParser.DecreasesSpecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DecreasesSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void exitDecreasesSpec(RISCALParser.DecreasesSpecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ContractSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void enterContractSpec(RISCALParser.ContractSpecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ContractSpec}
	 * labeled alternative in {@link RISCALParser#funspec}.
	 * @param ctx the parse tree
	 */
	void exitContractSpec(RISCALParser.ContractSpecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterEmptyCommand(RISCALParser.EmptyCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitEmptyCommand(RISCALParser.EmptyCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentCommand(RISCALParser.AssignmentCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentCommand(RISCALParser.AssignmentCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChooseCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterChooseCommand(RISCALParser.ChooseCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChooseCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitChooseCommand(RISCALParser.ChooseCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoWhileCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterDoWhileCommand(RISCALParser.DoWhileCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoWhileCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitDoWhileCommand(RISCALParser.DoWhileCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterVarCommand(RISCALParser.VarCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitVarCommand(RISCALParser.VarCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterValCommand(RISCALParser.ValCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitValCommand(RISCALParser.ValCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssertCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterAssertCommand(RISCALParser.AssertCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssertCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitAssertCommand(RISCALParser.AssertCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterPrintCommand(RISCALParser.PrintCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitPrintCommand(RISCALParser.PrintCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Print2Command}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterPrint2Command(RISCALParser.Print2CommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Print2Command}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitPrint2Command(RISCALParser.Print2CommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CheckCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterCheckCommand(RISCALParser.CheckCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CheckCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitCheckCommand(RISCALParser.CheckCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void enterExpCommand(RISCALParser.ExpCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpCommand}
	 * labeled alternative in {@link RISCALParser#scommand}.
	 * @param ctx the parse tree
	 */
	void exitExpCommand(RISCALParser.ExpCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SemicolonCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterSemicolonCommand(RISCALParser.SemicolonCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SemicolonCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitSemicolonCommand(RISCALParser.SemicolonCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChooseElseCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterChooseElseCommand(RISCALParser.ChooseElseCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChooseElseCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitChooseElseCommand(RISCALParser.ChooseElseCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfThenCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterIfThenCommand(RISCALParser.IfThenCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfThenCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitIfThenCommand(RISCALParser.IfThenCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfThenElseCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseCommand(RISCALParser.IfThenElseCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfThenElseCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseCommand(RISCALParser.IfThenElseCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MatchCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterMatchCommand(RISCALParser.MatchCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MatchCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitMatchCommand(RISCALParser.MatchCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterWhileCommand(RISCALParser.WhileCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitWhileCommand(RISCALParser.WhileCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterForCommand(RISCALParser.ForCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitForCommand(RISCALParser.ForCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForInCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterForInCommand(RISCALParser.ForInCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForInCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitForInCommand(RISCALParser.ForInCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChooseDoCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterChooseDoCommand(RISCALParser.ChooseDoCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChooseDoCommand}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitChooseDoCommand(RISCALParser.ChooseDoCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommandSequence}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommandSequence(RISCALParser.CommandSequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommandSequence}
	 * labeled alternative in {@link RISCALParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommandSequence(RISCALParser.CommandSequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InvariantLoopSpec}
	 * labeled alternative in {@link RISCALParser#loopspec}.
	 * @param ctx the parse tree
	 */
	void enterInvariantLoopSpec(RISCALParser.InvariantLoopSpecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InvariantLoopSpec}
	 * labeled alternative in {@link RISCALParser#loopspec}.
	 * @param ctx the parse tree
	 */
	void exitInvariantLoopSpec(RISCALParser.InvariantLoopSpecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DecreasesLoopSpec}
	 * labeled alternative in {@link RISCALParser#loopspec}.
	 * @param ctx the parse tree
	 */
	void enterDecreasesLoopSpec(RISCALParser.DecreasesLoopSpecContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DecreasesLoopSpec}
	 * labeled alternative in {@link RISCALParser#loopspec}.
	 * @param ctx the parse tree
	 */
	void exitDecreasesLoopSpec(RISCALParser.DecreasesLoopSpecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExp(RISCALParser.IdentifierExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExp(RISCALParser.IdentifierExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ProductExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterProductExp(RISCALParser.ProductExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ProductExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitProductExp(RISCALParser.ProductExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNotExp(RISCALParser.NotExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNotExp(RISCALParser.NotExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TimesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTimesExp(RISCALParser.TimesExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TimesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTimesExp(RISCALParser.TimesExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CartesianExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCartesianExp(RISCALParser.CartesianExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CartesianExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCartesianExp(RISCALParser.CartesianExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqualsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEqualsExp(RISCALParser.EqualsExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEqualsExp(RISCALParser.EqualsExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BigUnionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBigUnionExp(RISCALParser.BigUnionExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BigUnionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBigUnionExp(RISCALParser.BigUnionExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPrintExp(RISCALParser.PrintExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPrintExp(RISCALParser.PrintExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterFalseExp(RISCALParser.FalseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitFalseExp(RISCALParser.FalseExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecIdentifierExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRecIdentifierExp(RISCALParser.RecIdentifierExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecIdentifierExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRecIdentifierExp(RISCALParser.RecIdentifierExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNegationExp(RISCALParser.NegationExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNegationExp(RISCALParser.NegationExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterInSetExp(RISCALParser.InSetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitInSetExp(RISCALParser.InSetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTupleExp(RISCALParser.TupleExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTupleExp(RISCALParser.TupleExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterOrExp(RISCALParser.OrExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitOrExp(RISCALParser.OrExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessEqualExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLessEqualExp(RISCALParser.LessEqualExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessEqualExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLessEqualExp(RISCALParser.LessEqualExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLessExp(RISCALParser.LessExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLessExp(RISCALParser.LessExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesizedExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExp(RISCALParser.ParenthesizedExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesizedExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExp(RISCALParser.ParenthesizedExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LetParExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLetParExp(RISCALParser.LetParExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LetParExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLetParExp(RISCALParser.LetParExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssertExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAssertExp(RISCALParser.AssertExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssertExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAssertExp(RISCALParser.AssertExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotEqualsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNotEqualsExp(RISCALParser.NotEqualsExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqualsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNotEqualsExp(RISCALParser.NotEqualsExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterSetBuilderExp(RISCALParser.SetBuilderExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitSetBuilderExp(RISCALParser.SetBuilderExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetSizeExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterSetSizeExp(RISCALParser.SetSizeExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetSizeExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitSetSizeExp(RISCALParser.SetSizeExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EnumeratedSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratedSetExp(RISCALParser.EnumeratedSetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EnumeratedSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratedSetExp(RISCALParser.EnumeratedSetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForallExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterForallExp(RISCALParser.ForallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForallExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitForallExp(RISCALParser.ForallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpliesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterImpliesExp(RISCALParser.ImpliesExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpliesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitImpliesExp(RISCALParser.ImpliesExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MinusExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMinusExp(RISCALParser.MinusExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MinusExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMinusExp(RISCALParser.MinusExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MatchExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMatchExp(RISCALParser.MatchExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MatchExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMatchExp(RISCALParser.MatchExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SumExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterSumExp(RISCALParser.SumExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SumExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitSumExp(RISCALParser.SumExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTrueExp(RISCALParser.TrueExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTrueExp(RISCALParser.TrueExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntLiteralExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteralExp(RISCALParser.IntLiteralExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntLiteralExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteralExp(RISCALParser.IntLiteralExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChooseInExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterChooseInExp(RISCALParser.ChooseInExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChooseInExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitChooseInExp(RISCALParser.ChooseInExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNumberExp(RISCALParser.NumberExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNumberExp(RISCALParser.NumberExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterArrayUpdateExp(RISCALParser.ArrayUpdateExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitArrayUpdateExp(RISCALParser.ArrayUpdateExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArraySelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterArraySelectionExp(RISCALParser.ArraySelectionExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArraySelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitArraySelectionExp(RISCALParser.ArraySelectionExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMapBuilderExp(RISCALParser.MapBuilderExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMapBuilderExp(RISCALParser.MapBuilderExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TimesExpMult}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTimesExpMult(RISCALParser.TimesExpMultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TimesExpMult}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTimesExpMult(RISCALParser.TimesExpMultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterAndExp(RISCALParser.AndExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitAndExp(RISCALParser.AndExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChooseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterChooseExp(RISCALParser.ChooseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChooseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitChooseExp(RISCALParser.ChooseExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterGreaterExp(RISCALParser.GreaterExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitGreaterExp(RISCALParser.GreaterExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ApplicationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterApplicationExp(RISCALParser.ApplicationExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ApplicationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitApplicationExp(RISCALParser.ApplicationExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowerSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPowerSetExp(RISCALParser.PowerSetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowerSetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPowerSetExp(RISCALParser.PowerSetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExistsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExistsExp(RISCALParser.ExistsExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExistsExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExistsExp(RISCALParser.ExistsExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterUnionExp(RISCALParser.UnionExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitUnionExp(RISCALParser.UnionExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubsetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterSubsetExp(RISCALParser.SubsetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubsetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitSubsetExp(RISCALParser.SubsetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRecordExp(RISCALParser.RecordExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRecordExp(RISCALParser.RecordExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowerSet2Exp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPowerSet2Exp(RISCALParser.PowerSet2ExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowerSet2Exp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPowerSet2Exp(RISCALParser.PowerSet2ExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MaxExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMaxExp(RISCALParser.MaxExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MaxExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMaxExp(RISCALParser.MaxExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterArrayBuilderExp(RISCALParser.ArrayBuilderExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayBuilderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitArrayBuilderExp(RISCALParser.ArrayBuilderExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EquivExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEquivExp(RISCALParser.EquivExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EquivExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEquivExp(RISCALParser.EquivExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MinExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterMinExp(RISCALParser.MinExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MinExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitMinExp(RISCALParser.MinExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTupleUpdateExp(RISCALParser.TupleUpdateExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTupleUpdateExp(RISCALParser.TupleUpdateExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BigIntersectExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBigIntersectExp(RISCALParser.BigIntersectExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BigIntersectExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBigIntersectExp(RISCALParser.BigIntersectExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterLetExp(RISCALParser.LetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitLetExp(RISCALParser.LetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PlusExpMult}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPlusExpMult(RISCALParser.PlusExpMultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PlusExpMult}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPlusExpMult(RISCALParser.PlusExpMultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FactorialExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterFactorialExp(RISCALParser.FactorialExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FactorialExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitFactorialExp(RISCALParser.FactorialExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RemainderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRemainderExp(RISCALParser.RemainderExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RemainderExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRemainderExp(RISCALParser.RemainderExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecApplicationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRecApplicationExp(RISCALParser.RecApplicationExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecApplicationExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRecApplicationExp(RISCALParser.RecApplicationExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CheckExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterCheckExp(RISCALParser.CheckExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CheckExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitCheckExp(RISCALParser.CheckExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PlusExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPlusExp(RISCALParser.PlusExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PlusExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPlusExp(RISCALParser.PlusExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleSelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterTupleSelectionExp(RISCALParser.TupleSelectionExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleSelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitTupleSelectionExp(RISCALParser.TupleSelectionExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowerSet1Exp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPowerSet1Exp(RISCALParser.PowerSet1ExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowerSet1Exp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPowerSet1Exp(RISCALParser.PowerSet1ExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRecordUpdateExp(RISCALParser.RecordUpdateExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordUpdateExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRecordUpdateExp(RISCALParser.RecordUpdateExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfThenElseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIfThenElseExp(RISCALParser.IfThenElseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfThenElseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIfThenElseExp(RISCALParser.IfThenElseExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordSelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterRecordSelectionExp(RISCALParser.RecordSelectionExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordSelectionExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitRecordSelectionExp(RISCALParser.RecordSelectionExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntervalExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIntervalExp(RISCALParser.IntervalExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntervalExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIntervalExp(RISCALParser.IntervalExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WithoutExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterWithoutExp(RISCALParser.WithoutExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WithoutExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitWithoutExp(RISCALParser.WithoutExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptySetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterEmptySetExp(RISCALParser.EmptySetExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptySetExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitEmptySetExp(RISCALParser.EmptySetExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintInExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPrintInExp(RISCALParser.PrintInExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintInExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPrintInExp(RISCALParser.PrintInExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DividesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterDividesExp(RISCALParser.DividesExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DividesExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitDividesExp(RISCALParser.DividesExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntersectExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIntersectExp(RISCALParser.IntersectExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntersectExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIntersectExp(RISCALParser.IntersectExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterEqualExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterGreaterEqualExp(RISCALParser.GreaterEqualExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterEqualExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitGreaterEqualExp(RISCALParser.GreaterEqualExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnitExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterUnitExp(RISCALParser.UnitExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnitExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitUnitExp(RISCALParser.UnitExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ChooseInElseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterChooseInElseExp(RISCALParser.ChooseInElseExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ChooseInElseExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitChooseInElseExp(RISCALParser.ChooseInElseExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PowerExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterPowerExp(RISCALParser.PowerExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PowerExp}
	 * labeled alternative in {@link RISCALParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitPowerExp(RISCALParser.PowerExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnitType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterUnitType(RISCALParser.UnitTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnitType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitUnitType(RISCALParser.UnitTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(RISCALParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(RISCALParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(RISCALParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(RISCALParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterMapType(RISCALParser.MapTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitMapType(RISCALParser.MapTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterTupleType(RISCALParser.TupleTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitTupleType(RISCALParser.TupleTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterRecordType(RISCALParser.RecordTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitRecordType(RISCALParser.RecordTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NatType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterNatType(RISCALParser.NatTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NatType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitNatType(RISCALParser.NatTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterSetType(RISCALParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitSetType(RISCALParser.SetTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(RISCALParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(RISCALParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierType(RISCALParser.IdentifierTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierType}
	 * labeled alternative in {@link RISCALParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierType(RISCALParser.IdentifierTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code QuantifiedVariable}
	 * labeled alternative in {@link RISCALParser#qvar}.
	 * @param ctx the parse tree
	 */
	void enterQuantifiedVariable(RISCALParser.QuantifiedVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code QuantifiedVariable}
	 * labeled alternative in {@link RISCALParser#qvar}.
	 * @param ctx the parse tree
	 */
	void exitQuantifiedVariable(RISCALParser.QuantifiedVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierTypeQuantifiedVar}
	 * labeled alternative in {@link RISCALParser#qvcore}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierTypeQuantifiedVar(RISCALParser.IdentifierTypeQuantifiedVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierTypeQuantifiedVar}
	 * labeled alternative in {@link RISCALParser#qvcore}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierTypeQuantifiedVar(RISCALParser.IdentifierTypeQuantifiedVarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierSetQuantifiedVar}
	 * labeled alternative in {@link RISCALParser#qvcore}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierSetQuantifiedVar(RISCALParser.IdentifierSetQuantifiedVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierSetQuantifiedVar}
	 * labeled alternative in {@link RISCALParser#qvcore}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierSetQuantifiedVar(RISCALParser.IdentifierSetQuantifiedVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link RISCALParser#binder}.
	 * @param ctx the parse tree
	 */
	void enterBinder(RISCALParser.BinderContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#binder}.
	 * @param ctx the parse tree
	 */
	void exitBinder(RISCALParser.BinderContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierPatternCommand(RISCALParser.IdentifierPatternCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierPatternCommand(RISCALParser.IdentifierPatternCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ApplicationPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 */
	void enterApplicationPatternCommand(RISCALParser.ApplicationPatternCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ApplicationPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 */
	void exitApplicationPatternCommand(RISCALParser.ApplicationPatternCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefaultPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 */
	void enterDefaultPatternCommand(RISCALParser.DefaultPatternCommandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefaultPatternCommand}
	 * labeled alternative in {@link RISCALParser#pcommand}.
	 * @param ctx the parse tree
	 */
	void exitDefaultPatternCommand(RISCALParser.DefaultPatternCommandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierPatternExp(RISCALParser.IdentifierPatternExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierPatternExp(RISCALParser.IdentifierPatternExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ApplicationPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 */
	void enterApplicationPatternExp(RISCALParser.ApplicationPatternExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ApplicationPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 */
	void exitApplicationPatternExp(RISCALParser.ApplicationPatternExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefaultPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 */
	void enterDefaultPatternExp(RISCALParser.DefaultPatternExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefaultPatternExp}
	 * labeled alternative in {@link RISCALParser#pexp}.
	 * @param ctx the parse tree
	 */
	void exitDefaultPatternExp(RISCALParser.DefaultPatternExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link RISCALParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(RISCALParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(RISCALParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link RISCALParser#ritem}.
	 * @param ctx the parse tree
	 */
	void enterRitem(RISCALParser.RitemContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#ritem}.
	 * @param ctx the parse tree
	 */
	void exitRitem(RISCALParser.RitemContext ctx);
	/**
	 * Enter a parse tree produced by {@link RISCALParser#eident}.
	 * @param ctx the parse tree
	 */
	void enterEident(RISCALParser.EidentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#eident}.
	 * @param ctx the parse tree
	 */
	void exitEident(RISCALParser.EidentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecIdentifier}
	 * labeled alternative in {@link RISCALParser#rident}.
	 * @param ctx the parse tree
	 */
	void enterRecIdentifier(RISCALParser.RecIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecIdentifier}
	 * labeled alternative in {@link RISCALParser#rident}.
	 * @param ctx the parse tree
	 */
	void exitRecIdentifier(RISCALParser.RecIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecApplication}
	 * labeled alternative in {@link RISCALParser#rident}.
	 * @param ctx the parse tree
	 */
	void enterRecApplication(RISCALParser.RecApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecApplication}
	 * labeled alternative in {@link RISCALParser#rident}.
	 * @param ctx the parse tree
	 */
	void exitRecApplication(RISCALParser.RecApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MapSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 */
	void enterMapSelector(RISCALParser.MapSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MapSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 */
	void exitMapSelector(RISCALParser.MapSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 */
	void enterTupleSelector(RISCALParser.TupleSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 */
	void exitTupleSelector(RISCALParser.TupleSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RecordSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 */
	void enterRecordSelector(RISCALParser.RecordSelectorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RecordSelector}
	 * labeled alternative in {@link RISCALParser#sel}.
	 * @param ctx the parse tree
	 */
	void exitRecordSelector(RISCALParser.RecordSelectorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsNotMultiple}
	 * labeled alternative in {@link RISCALParser#multiple}.
	 * @param ctx the parse tree
	 */
	void enterIsNotMultiple(RISCALParser.IsNotMultipleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsNotMultiple}
	 * labeled alternative in {@link RISCALParser#multiple}.
	 * @param ctx the parse tree
	 */
	void exitIsNotMultiple(RISCALParser.IsNotMultipleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IsMultiple}
	 * labeled alternative in {@link RISCALParser#multiple}.
	 * @param ctx the parse tree
	 */
	void enterIsMultiple(RISCALParser.IsMultipleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IsMultiple}
	 * labeled alternative in {@link RISCALParser#multiple}.
	 * @param ctx the parse tree
	 */
	void exitIsMultiple(RISCALParser.IsMultipleContext ctx);
	/**
	 * Enter a parse tree produced by {@link RISCALParser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(RISCALParser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(RISCALParser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RISCALParser#decimal}.
	 * @param ctx the parse tree
	 */
	void enterDecimal(RISCALParser.DecimalContext ctx);
	/**
	 * Exit a parse tree produced by {@link RISCALParser#decimal}.
	 * @param ctx the parse tree
	 */
	void exitDecimal(RISCALParser.DecimalContext ctx);
}