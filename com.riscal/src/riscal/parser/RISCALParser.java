// Generated from RISCAL.g4 by ANTLR 4.5.3

  package riscal.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RISCALParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, T__91=92, T__92=93, T__93=94, 
		T__94=95, T__95=96, T__96=97, T__97=98, T__98=99, T__99=100, T__100=101, 
		T__101=102, T__102=103, T__103=104, T__104=105, T__105=106, T__106=107, 
		T__107=108, T__108=109, T__109=110, T__110=111, T__111=112, T__112=113, 
		T__113=114, T__114=115, T__115=116, T__116=117, T__117=118, T__118=119, 
		T__119=120, T__120=121, T__121=122, T__122=123, T__123=124, T__124=125, 
		T__125=126, IDENT=127, DECIMAL=128, EOS=129, STRING=130, WHITESPACE=131, 
		LINECOMMENT=132, COMMENT=133, ERROR=134;
	public static final int
		RULE_specification = 0, RULE_declaration = 1, RULE_funspec = 2, RULE_scommand = 3, 
		RULE_command = 4, RULE_loopspec = 5, RULE_exp = 6, RULE_type = 7, RULE_qvar = 8, 
		RULE_qvcore = 9, RULE_binder = 10, RULE_pcommand = 11, RULE_pexp = 12, 
		RULE_param = 13, RULE_ritem = 14, RULE_eident = 15, RULE_rident = 16, 
		RULE_sel = 17, RULE_multiple = 18, RULE_ident = 19, RULE_decimal = 20;
	public static final String[] ruleNames = {
		"specification", "declaration", "funspec", "scommand", "command", "loopspec", 
		"exp", "type", "qvar", "qvcore", "binder", "pcommand", "pexp", "param", 
		"ritem", "eident", "rident", "sel", "multiple", "ident", "decimal"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'val'", "':'", "'Nat'", "'ℕ'", "'fun'", "'('", "','", "')'", "'pred'", 
		"'proc'", "'type'", "'='", "'with'", "'rectype'", "'and'", "'enumtype'", 
		"'⇔'", "'<=>'", "'theorem'", "'{'", "'return'", "'}'", "'requires'", "'ensures'", 
		"'decreases'", "'modular'", "':='", "'≔'", "'choose'", "'do'", "'while'", 
		"'var'", "'assert'", "'print'", "'check'", "'then'", "'else'", "'if'", 
		"'match'", "'for'", "'invariant'", "'⊤'", "'true'", "'⊥'", "'false'", 
		"'∅'", "'['", "']'", "'!'", "'Array'", "'Map'", "'Set'", "'.'", "'-'", 
		"'⋂'", "'Intersect'", "'⋃'", "'Union'", "'^'", "'*'", "'⋅'", "'..'", "'/'", 
		"'%'", "'+'", "'〈'", "'⟨'", "'〈'", "'<<'", "'〉'", "'⟩'", "'〉'", "'>>'", 
		"'|'", "'∩'", "'intersect'", "'∪'", "'union'", "'\\'", "'×'", "'times'", 
		"'#'", "'∑'", "'Σ'", "'sum'", "'∏'", "'Π'", "'product'", "'min'", "'max'", 
		"'≠'", "'~='", "'<'", "'≤'", "'<='", "'>'", "'≥'", "'>='", "'∈'", "'isin'", 
		"'⊆'", "'subseteq'", "'¬'", "'~'", "'∧'", "'/\\'", "'∨'", "'\\/'", "'⇒'", 
		"'=>'", "'∀'", "'forall'", "'∃'", "'exists'", "'let'", "'in'", "'letpar'", 
		"'Unit'", "'Bool'", "'Int'", "'ℤ'", "'Tuple'", "'Record'", "'->'", "'_'", 
		"'multiple'", null, null, "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "IDENT", "DECIMAL", "EOS", "STRING", 
		"WHITESPACE", "LINECOMMENT", "COMMENT", "ERROR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RISCAL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	boolean cartesian = true;
	public RISCALParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SpecificationContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(RISCALParser.EOF, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public SpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecificationContext specification() throws RecognitionException {
		SpecificationContext _localctx = new SpecificationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_specification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__13) | (1L << T__15) | (1L << T__18))) != 0) || _la==T__125) {
				{
				{
				setState(42);
				declaration();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	 
		public DeclarationContext() { }
		public void copyFrom(DeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EnumTypeDefinitionContext extends DeclarationContext {
		public RitemContext ritem() {
			return getRuleContext(RitemContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public EnumTypeDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEnumTypeDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEnumTypeDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEnumTypeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValueDeclarationContext extends DeclarationContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public ValueDeclarationContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterValueDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitValueDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitValueDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValueDefinitionContext extends DeclarationContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ValueDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterValueDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitValueDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitValueDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicateDeclarationContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public PredicateDeclarationContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPredicateDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPredicateDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPredicateDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProcedureDefinitionContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<FunspecContext> funspec() {
			return getRuleContexts(FunspecContext.class);
		}
		public FunspecContext funspec(int i) {
			return getRuleContext(FunspecContext.class,i);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public ProcedureDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterProcedureDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitProcedureDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitProcedureDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TheoremDefinitionContext extends DeclarationContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public TheoremDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTheoremDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTheoremDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTheoremDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecTypeDefinitionContext extends DeclarationContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<RitemContext> ritem() {
			return getRuleContexts(RitemContext.class);
		}
		public RitemContext ritem(int i) {
			return getRuleContext(RitemContext.class,i);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public RecTypeDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecTypeDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecTypeDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecTypeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypeDefinitionContext extends DeclarationContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TypeDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTypeDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTypeDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTypeDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TheoremParamDefinitionContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<FunspecContext> funspec() {
			return getRuleContexts(FunspecContext.class);
		}
		public FunspecContext funspec(int i) {
			return getRuleContext(FunspecContext.class,i);
		}
		public TheoremParamDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTheoremParamDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTheoremParamDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTheoremParamDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionDeclarationContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public FunctionDeclarationContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProcedureDeclarationContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ProcedureDeclarationContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterProcedureDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitProcedureDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitProcedureDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicateDefinitionContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<FunspecContext> funspec() {
			return getRuleContexts(FunspecContext.class);
		}
		public FunspecContext funspec(int i) {
			return getRuleContext(FunspecContext.class,i);
		}
		public PredicateDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPredicateDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPredicateDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPredicateDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionDefinitionContext extends DeclarationContext {
		public MultipleContext multiple() {
			return getRuleContext(MultipleContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<FunspecContext> funspec() {
			return getRuleContexts(FunspecContext.class);
		}
		public FunspecContext funspec(int i) {
			return getRuleContext(FunspecContext.class,i);
		}
		public FunctionDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicateValueDefinitionContext extends DeclarationContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public PredicateValueDefinitionContext(DeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPredicateValueDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPredicateValueDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPredicateValueDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		int _la;
		try {
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new ValueDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				match(T__0);
				setState(51);
				ident();
				setState(52);
				match(T__1);
				setState(53);
				_la = _input.LA(1);
				if ( !(_la==T__2 || _la==T__3) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(54);
				match(EOS);
				}
				break;
			case 2:
				_localctx = new FunctionDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				multiple();
				setState(57);
				match(T__4);
				setState(58);
				ident();
				setState(59);
				match(T__5);
				setState(68);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(60);
					param();
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(61);
						match(T__6);
						setState(62);
						param();
						}
						}
						setState(67);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(70);
				match(T__7);
				setState(71);
				match(T__1);
				setState(72);
				type();
				setState(73);
				match(EOS);
				}
				break;
			case 3:
				_localctx = new PredicateDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(75);
				multiple();
				setState(76);
				match(T__8);
				setState(77);
				ident();
				setState(78);
				match(T__5);
				setState(87);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(79);
					param();
					setState(84);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(80);
						match(T__6);
						setState(81);
						param();
						}
						}
						setState(86);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(89);
				match(T__7);
				setState(90);
				match(EOS);
				}
				break;
			case 4:
				_localctx = new ProcedureDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(92);
				multiple();
				setState(93);
				match(T__9);
				setState(94);
				ident();
				setState(95);
				match(T__5);
				setState(104);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(96);
					param();
					setState(101);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(97);
						match(T__6);
						setState(98);
						param();
						}
						}
						setState(103);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(106);
				match(T__7);
				setState(107);
				match(T__1);
				setState(108);
				type();
				}
				break;
			case 5:
				_localctx = new TypeDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(110);
				match(T__10);
				setState(111);
				ident();
				setState(112);
				match(T__11);
				setState(113);
				type();
				setState(116);
				_la = _input.LA(1);
				if (_la==T__12) {
					{
					setState(114);
					match(T__12);
					setState(115);
					exp(0);
					}
				}

				setState(118);
				match(EOS);
				}
				break;
			case 6:
				_localctx = new RecTypeDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(120);
				match(T__13);
				setState(121);
				match(T__5);
				setState(122);
				exp(0);
				setState(123);
				match(T__7);
				setState(124);
				ritem();
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__14) {
					{
					{
					setState(125);
					match(T__14);
					setState(126);
					ritem();
					}
					}
					setState(131);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(132);
				match(EOS);
				}
				break;
			case 7:
				_localctx = new EnumTypeDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(134);
				match(T__15);
				setState(135);
				ritem();
				setState(136);
				match(EOS);
				}
				break;
			case 8:
				_localctx = new ValueDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(138);
				match(T__0);
				setState(139);
				ident();
				setState(142);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(140);
					match(T__1);
					setState(141);
					type();
					}
				}

				setState(144);
				match(T__11);
				setState(145);
				exp(0);
				setState(146);
				match(EOS);
				}
				break;
			case 9:
				_localctx = new PredicateValueDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(148);
				match(T__8);
				setState(149);
				ident();
				setState(150);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(151);
				exp(0);
				setState(152);
				match(EOS);
				}
				break;
			case 10:
				_localctx = new TheoremDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(154);
				match(T__18);
				setState(155);
				ident();
				setState(156);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(157);
				exp(0);
				setState(158);
				match(EOS);
				}
				break;
			case 11:
				_localctx = new FunctionDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(160);
				multiple();
				setState(161);
				match(T__4);
				setState(162);
				ident();
				setState(163);
				match(T__5);
				setState(172);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(164);
					param();
					setState(169);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(165);
						match(T__6);
						setState(166);
						param();
						}
						}
						setState(171);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(174);
				match(T__7);
				setState(175);
				match(T__1);
				setState(176);
				type();
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0)) {
					{
					{
					setState(177);
					funspec();
					}
					}
					setState(182);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(183);
				match(T__11);
				setState(184);
				exp(0);
				setState(185);
				match(EOS);
				}
				break;
			case 12:
				_localctx = new PredicateDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(187);
				multiple();
				setState(188);
				match(T__8);
				setState(189);
				ident();
				setState(190);
				match(T__5);
				setState(199);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(191);
					param();
					setState(196);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(192);
						match(T__6);
						setState(193);
						param();
						}
						}
						setState(198);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(201);
				match(T__7);
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0)) {
					{
					{
					setState(202);
					funspec();
					}
					}
					setState(207);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(208);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(209);
				exp(0);
				setState(210);
				match(EOS);
				}
				break;
			case 13:
				_localctx = new ProcedureDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(212);
				multiple();
				setState(213);
				match(T__9);
				setState(214);
				ident();
				setState(215);
				match(T__5);
				setState(224);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(216);
					param();
					setState(221);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(217);
						match(T__6);
						setState(218);
						param();
						}
						}
						setState(223);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(226);
				match(T__7);
				setState(227);
				match(T__1);
				setState(228);
				type();
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0)) {
					{
					{
					setState(229);
					funspec();
					}
					}
					setState(234);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(235);
				match(T__19);
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__5) | (1L << T__19) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__66 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)) | (1L << (T__73 - 66)) | (1L << (T__81 - 66)) | (1L << (T__82 - 66)) | (1L << (T__83 - 66)) | (1L << (T__84 - 66)) | (1L << (T__85 - 66)) | (1L << (T__86 - 66)) | (1L << (T__87 - 66)) | (1L << (T__88 - 66)) | (1L << (T__89 - 66)) | (1L << (T__102 - 66)) | (1L << (T__103 - 66)) | (1L << (T__110 - 66)) | (1L << (T__111 - 66)) | (1L << (T__112 - 66)) | (1L << (T__113 - 66)) | (1L << (T__114 - 66)) | (1L << (T__116 - 66)) | (1L << (IDENT - 66)) | (1L << (DECIMAL - 66)) | (1L << (EOS - 66)))) != 0)) {
					{
					{
					setState(236);
					command();
					}
					}
					setState(241);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(246);
				_la = _input.LA(1);
				if (_la==T__20) {
					{
					setState(242);
					match(T__20);
					setState(243);
					exp(0);
					setState(244);
					match(EOS);
					}
				}

				setState(248);
				match(T__21);
				}
				break;
			case 14:
				_localctx = new TheoremParamDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(250);
				multiple();
				setState(251);
				match(T__18);
				setState(252);
				ident();
				setState(253);
				match(T__5);
				setState(262);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(254);
					param();
					setState(259);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(255);
						match(T__6);
						setState(256);
						param();
						}
						}
						setState(261);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(264);
				match(T__7);
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0)) {
					{
					{
					setState(265);
					funspec();
					}
					}
					setState(270);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(271);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(272);
				exp(0);
				setState(273);
				match(EOS);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunspecContext extends ParserRuleContext {
		public FunspecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funspec; }
	 
		public FunspecContext() { }
		public void copyFrom(FunspecContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RequiresSpecContext extends FunspecContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public RequiresSpecContext(FunspecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRequiresSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRequiresSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRequiresSpec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EnsuresSpecContext extends FunspecContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public EnsuresSpecContext(FunspecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEnsuresSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEnsuresSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEnsuresSpec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ContractSpecContext extends FunspecContext {
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public ContractSpecContext(FunspecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterContractSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitContractSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitContractSpec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DecreasesSpecContext extends FunspecContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public DecreasesSpecContext(FunspecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDecreasesSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDecreasesSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDecreasesSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunspecContext funspec() throws RecognitionException {
		FunspecContext _localctx = new FunspecContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_funspec);
		int _la;
		try {
			setState(298);
			switch (_input.LA(1)) {
			case T__22:
				_localctx = new RequiresSpecContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(277);
				match(T__22);
				setState(278);
				exp(0);
				setState(279);
				match(EOS);
				}
				break;
			case T__23:
				_localctx = new EnsuresSpecContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
				match(T__23);
				setState(282);
				exp(0);
				setState(283);
				match(EOS);
				}
				break;
			case T__24:
				_localctx = new DecreasesSpecContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				match(T__24);
				setState(286);
				exp(0);
				setState(291);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(287);
					match(T__6);
					setState(288);
					exp(0);
					}
					}
					setState(293);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(294);
				match(EOS);
				}
				break;
			case T__25:
				_localctx = new ContractSpecContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(296);
				match(T__25);
				setState(297);
				match(EOS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScommandContext extends ParserRuleContext {
		public ScommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scommand; }
	 
		public ScommandContext() { }
		public void copyFrom(ScommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CheckCommandContext extends ScommandContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public CheckCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterCheckCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitCheckCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitCheckCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DoWhileCommandContext extends ScommandContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<LoopspecContext> loopspec() {
			return getRuleContexts(LoopspecContext.class);
		}
		public LoopspecContext loopspec(int i) {
			return getRuleContext(LoopspecContext.class,i);
		}
		public DoWhileCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDoWhileCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDoWhileCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDoWhileCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Print2CommandContext extends ScommandContext {
		public TerminalNode STRING() { return getToken(RISCALParser.STRING, 0); }
		public Print2CommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPrint2Command(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPrint2Command(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPrint2Command(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintCommandContext extends ScommandContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode STRING() { return getToken(RISCALParser.STRING, 0); }
		public PrintCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPrintCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPrintCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPrintCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarCommandContext extends ScommandContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VarCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterVarCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitVarCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitVarCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentCommandContext extends ScommandContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<SelContext> sel() {
			return getRuleContexts(SelContext.class);
		}
		public SelContext sel(int i) {
			return getRuleContext(SelContext.class,i);
		}
		public AssignmentCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterAssignmentCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitAssignmentCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitAssignmentCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EmptyCommandContext extends ScommandContext {
		public EmptyCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEmptyCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEmptyCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEmptyCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValCommandContext extends ScommandContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ValCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterValCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitValCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitValCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssertCommandContext extends ScommandContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssertCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterAssertCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitAssertCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitAssertCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChooseCommandContext extends ScommandContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ChooseCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterChooseCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitChooseCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitChooseCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpCommandContext extends ScommandContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ExpCommandContext(ScommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterExpCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitExpCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitExpCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScommandContext scommand() throws RecognitionException {
		ScommandContext _localctx = new ScommandContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_scommand);
		int _la;
		try {
			setState(365);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				_localctx = new EmptyCommandContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case 2:
				_localctx = new AssignmentCommandContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				ident();
				setState(305);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__46 || _la==T__52) {
					{
					{
					setState(302);
					sel();
					}
					}
					setState(307);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(308);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__26) | (1L << T__27))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(309);
				exp(0);
				}
				break;
			case 3:
				_localctx = new ChooseCommandContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				match(T__28);
				setState(312);
				qvar();
				}
				break;
			case 4:
				_localctx = new DoWhileCommandContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(313);
				match(T__29);
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24 || _la==T__40) {
					{
					{
					setState(314);
					loopspec();
					}
					}
					setState(319);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(320);
				command();
				setState(321);
				match(T__30);
				setState(322);
				exp(0);
				}
				break;
			case 5:
				_localctx = new VarCommandContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(324);
				match(T__31);
				setState(325);
				ident();
				setState(326);
				match(T__1);
				setState(327);
				type();
				setState(330);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__26) | (1L << T__27))) != 0)) {
					{
					setState(328);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__26) | (1L << T__27))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(329);
					exp(0);
					}
				}

				}
				break;
			case 6:
				_localctx = new ValCommandContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(332);
				match(T__0);
				setState(333);
				ident();
				setState(336);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(334);
					match(T__1);
					setState(335);
					type();
					}
				}

				setState(338);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__26) | (1L << T__27))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(339);
				exp(0);
				}
				break;
			case 7:
				_localctx = new AssertCommandContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(341);
				match(T__32);
				setState(342);
				exp(0);
				}
				break;
			case 8:
				_localctx = new PrintCommandContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(343);
				match(T__33);
				setState(346);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(344);
					match(STRING);
					setState(345);
					match(T__6);
					}
				}

				setState(348);
				exp(0);
				setState(353);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(349);
					match(T__6);
					setState(350);
					exp(0);
					}
					}
					setState(355);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 9:
				_localctx = new Print2CommandContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(356);
				match(T__33);
				setState(357);
				match(STRING);
				}
				break;
			case 10:
				_localctx = new CheckCommandContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(358);
				match(T__34);
				setState(359);
				ident();
				setState(362);
				_la = _input.LA(1);
				if (_la==T__12) {
					{
					setState(360);
					match(T__12);
					setState(361);
					exp(0);
					}
				}

				}
				break;
			case 11:
				_localctx = new ExpCommandContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(364);
				exp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	 
		public CommandContext() { }
		public void copyFrom(CommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WhileCommandContext extends CommandContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public List<LoopspecContext> loopspec() {
			return getRuleContexts(LoopspecContext.class);
		}
		public LoopspecContext loopspec(int i) {
			return getRuleContext(LoopspecContext.class,i);
		}
		public WhileCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterWhileCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitWhileCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitWhileCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChooseElseCommandContext extends CommandContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public ChooseElseCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterChooseElseCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitChooseElseCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitChooseElseCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfThenCommandContext extends CommandContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public IfThenCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIfThenCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIfThenCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIfThenCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SemicolonCommandContext extends CommandContext {
		public ScommandContext scommand() {
			return getRuleContext(ScommandContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public SemicolonCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSemicolonCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSemicolonCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSemicolonCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForCommandContext extends CommandContext {
		public List<ScommandContext> scommand() {
			return getRuleContexts(ScommandContext.class);
		}
		public ScommandContext scommand(int i) {
			return getRuleContext(ScommandContext.class,i);
		}
		public List<TerminalNode> EOS() { return getTokens(RISCALParser.EOS); }
		public TerminalNode EOS(int i) {
			return getToken(RISCALParser.EOS, i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public List<LoopspecContext> loopspec() {
			return getRuleContexts(LoopspecContext.class);
		}
		public LoopspecContext loopspec(int i) {
			return getRuleContext(LoopspecContext.class,i);
		}
		public ForCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterForCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitForCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitForCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChooseDoCommandContext extends CommandContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public List<LoopspecContext> loopspec() {
			return getRuleContexts(LoopspecContext.class);
		}
		public LoopspecContext loopspec(int i) {
			return getRuleContext(LoopspecContext.class,i);
		}
		public ChooseDoCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterChooseDoCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitChooseDoCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitChooseDoCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfThenElseCommandContext extends CommandContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public IfThenElseCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIfThenElseCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIfThenElseCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIfThenElseCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForInCommandContext extends CommandContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public List<LoopspecContext> loopspec() {
			return getRuleContexts(LoopspecContext.class);
		}
		public LoopspecContext loopspec(int i) {
			return getRuleContext(LoopspecContext.class,i);
		}
		public ForInCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterForInCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitForInCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitForInCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MatchCommandContext extends CommandContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<PcommandContext> pcommand() {
			return getRuleContexts(PcommandContext.class);
		}
		public PcommandContext pcommand(int i) {
			return getRuleContext(PcommandContext.class,i);
		}
		public MatchCommandContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMatchCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMatchCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMatchCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommandSequenceContext extends CommandContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public CommandSequenceContext(CommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterCommandSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitCommandSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitCommandSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_command);
		int _la;
		try {
			setState(456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				_localctx = new SemicolonCommandContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(367);
				scommand();
				setState(368);
				match(EOS);
				}
				break;
			case 2:
				_localctx = new ChooseElseCommandContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(370);
				match(T__28);
				setState(371);
				qvar();
				setState(372);
				match(T__35);
				setState(373);
				command();
				setState(374);
				match(T__36);
				setState(375);
				command();
				}
				break;
			case 3:
				_localctx = new IfThenCommandContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(377);
				match(T__37);
				setState(378);
				exp(0);
				setState(379);
				match(T__35);
				setState(380);
				command();
				}
				break;
			case 4:
				_localctx = new IfThenElseCommandContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(382);
				match(T__37);
				setState(383);
				exp(0);
				setState(384);
				match(T__35);
				setState(385);
				command();
				setState(386);
				match(T__36);
				setState(387);
				command();
				}
				break;
			case 5:
				_localctx = new MatchCommandContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(389);
				match(T__38);
				setState(390);
				exp(0);
				setState(391);
				match(T__12);
				setState(392);
				match(T__19);
				setState(394); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(393);
					pcommand();
					}
					}
					setState(396); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__124 || _la==IDENT );
				setState(398);
				match(T__21);
				}
				break;
			case 6:
				_localctx = new WhileCommandContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(400);
				match(T__30);
				setState(401);
				exp(0);
				setState(402);
				match(T__29);
				setState(406);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24 || _la==T__40) {
					{
					{
					setState(403);
					loopspec();
					}
					}
					setState(408);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(409);
				command();
				}
				break;
			case 7:
				_localctx = new ForCommandContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(411);
				match(T__39);
				setState(412);
				scommand();
				setState(413);
				match(EOS);
				setState(414);
				exp(0);
				setState(415);
				match(EOS);
				setState(416);
				scommand();
				setState(417);
				match(T__29);
				setState(421);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24 || _la==T__40) {
					{
					{
					setState(418);
					loopspec();
					}
					}
					setState(423);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(424);
				command();
				}
				break;
			case 8:
				_localctx = new ForInCommandContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(426);
				match(T__39);
				setState(427);
				qvar();
				setState(428);
				match(T__29);
				setState(432);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24 || _la==T__40) {
					{
					{
					setState(429);
					loopspec();
					}
					}
					setState(434);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(435);
				command();
				}
				break;
			case 9:
				_localctx = new ChooseDoCommandContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(437);
				match(T__28);
				setState(438);
				qvar();
				setState(439);
				match(T__29);
				setState(443);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24 || _la==T__40) {
					{
					{
					setState(440);
					loopspec();
					}
					}
					setState(445);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(446);
				command();
				}
				break;
			case 10:
				_localctx = new CommandSequenceContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(448);
				match(T__19);
				setState(452);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__5) | (1L << T__19) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__66 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)) | (1L << (T__73 - 66)) | (1L << (T__81 - 66)) | (1L << (T__82 - 66)) | (1L << (T__83 - 66)) | (1L << (T__84 - 66)) | (1L << (T__85 - 66)) | (1L << (T__86 - 66)) | (1L << (T__87 - 66)) | (1L << (T__88 - 66)) | (1L << (T__89 - 66)) | (1L << (T__102 - 66)) | (1L << (T__103 - 66)) | (1L << (T__110 - 66)) | (1L << (T__111 - 66)) | (1L << (T__112 - 66)) | (1L << (T__113 - 66)) | (1L << (T__114 - 66)) | (1L << (T__116 - 66)) | (1L << (IDENT - 66)) | (1L << (DECIMAL - 66)) | (1L << (EOS - 66)))) != 0)) {
					{
					{
					setState(449);
					command();
					}
					}
					setState(454);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(455);
				match(T__21);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopspecContext extends ParserRuleContext {
		public LoopspecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopspec; }
	 
		public LoopspecContext() { }
		public void copyFrom(LoopspecContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DecreasesLoopSpecContext extends LoopspecContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public DecreasesLoopSpecContext(LoopspecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDecreasesLoopSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDecreasesLoopSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDecreasesLoopSpec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InvariantLoopSpecContext extends LoopspecContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode EOS() { return getToken(RISCALParser.EOS, 0); }
		public InvariantLoopSpecContext(LoopspecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterInvariantLoopSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitInvariantLoopSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitInvariantLoopSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopspecContext loopspec() throws RecognitionException {
		LoopspecContext _localctx = new LoopspecContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_loopspec);
		int _la;
		try {
			setState(473);
			switch (_input.LA(1)) {
			case T__40:
				_localctx = new InvariantLoopSpecContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(458);
				match(T__40);
				setState(459);
				exp(0);
				setState(460);
				match(EOS);
				}
				break;
			case T__24:
				_localctx = new DecreasesLoopSpecContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(462);
				match(T__24);
				setState(463);
				exp(0);
				setState(468);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(464);
					match(T__6);
					setState(465);
					exp(0);
					}
					}
					setState(470);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(471);
				match(EOS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdentifierExpContext extends ExpContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public IdentifierExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdentifierExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdentifierExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdentifierExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProductExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ProductExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterProductExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitProductExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitProductExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NotExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterNotExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitNotExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitNotExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TimesExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TimesExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTimesExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTimesExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTimesExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CartesianExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public CartesianExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterCartesianExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitCartesianExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitCartesianExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualsExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EqualsExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEqualsExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEqualsExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEqualsExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BigUnionExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BigUnionExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterBigUnionExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitBigUnionExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitBigUnionExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode STRING() { return getToken(RISCALParser.STRING, 0); }
		public PrintExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPrintExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPrintExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPrintExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseExpContext extends ExpContext {
		public FalseExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterFalseExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitFalseExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitFalseExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecIdentifierExpContext extends ExpContext {
		public List<IdentContext> ident() {
			return getRuleContexts(IdentContext.class);
		}
		public IdentContext ident(int i) {
			return getRuleContext(IdentContext.class,i);
		}
		public RecIdentifierExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecIdentifierExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecIdentifierExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecIdentifierExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegationExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NegationExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterNegationExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitNegationExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitNegationExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InSetExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public InSetExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterInSetExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitInSetExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitInSetExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TupleExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TupleExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTupleExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTupleExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTupleExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OrExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterOrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitOrExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitOrExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessEqualExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public LessEqualExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterLessEqualExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitLessEqualExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitLessEqualExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public LessExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterLessExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitLessExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitLessExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesizedExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ParenthesizedExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterParenthesizedExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitParenthesizedExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitParenthesizedExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LetParExpContext extends ExpContext {
		public List<BinderContext> binder() {
			return getRuleContexts(BinderContext.class);
		}
		public BinderContext binder(int i) {
			return getRuleContext(BinderContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public LetParExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterLetParExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitLetParExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitLetParExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssertExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public AssertExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterAssertExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitAssertExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitAssertExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotEqualsExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public NotEqualsExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterNotEqualsExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitNotEqualsExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitNotEqualsExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetBuilderExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public SetBuilderExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSetBuilderExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSetBuilderExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSetBuilderExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetSizeExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public SetSizeExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSetSizeExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSetSizeExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSetSizeExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EnumeratedSetExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EnumeratedSetExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEnumeratedSetExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEnumeratedSetExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEnumeratedSetExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForallExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ForallExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterForallExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitForallExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitForallExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ImpliesExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ImpliesExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterImpliesExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitImpliesExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitImpliesExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinusExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public MinusExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMinusExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMinusExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMinusExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MatchExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<PexpContext> pexp() {
			return getRuleContexts(PexpContext.class);
		}
		public PexpContext pexp(int i) {
			return getRuleContext(PexpContext.class,i);
		}
		public MatchExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMatchExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMatchExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMatchExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SumExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public SumExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSumExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSumExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSumExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueExpContext extends ExpContext {
		public TrueExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTrueExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTrueExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTrueExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntLiteralExpContext extends ExpContext {
		public DecimalContext decimal() {
			return getRuleContext(DecimalContext.class,0);
		}
		public IntLiteralExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIntLiteralExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIntLiteralExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIntLiteralExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChooseInExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ChooseInExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterChooseInExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitChooseInExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitChooseInExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public NumberExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterNumberExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitNumberExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitNumberExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayUpdateExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ArrayUpdateExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterArrayUpdateExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitArrayUpdateExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitArrayUpdateExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArraySelectionExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ArraySelectionExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterArraySelectionExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitArraySelectionExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitArraySelectionExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapBuilderExpContext extends ExpContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public MapBuilderExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMapBuilderExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMapBuilderExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMapBuilderExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TimesExpMultContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TimesExpMultContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTimesExpMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTimesExpMult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTimesExpMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public AndExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterAndExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitAndExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitAndExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChooseExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ChooseExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterChooseExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitChooseExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitChooseExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public GreaterExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterGreaterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitGreaterExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitGreaterExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ApplicationExpContext extends ExpContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ApplicationExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterApplicationExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitApplicationExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitApplicationExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowerSetExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public PowerSetExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPowerSetExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPowerSetExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPowerSetExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExistsExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ExistsExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterExistsExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitExistsExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitExistsExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnionExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public UnionExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterUnionExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitUnionExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitUnionExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubsetExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public SubsetExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSubsetExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSubsetExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSubsetExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecordExpContext extends ExpContext {
		public List<EidentContext> eident() {
			return getRuleContexts(EidentContext.class);
		}
		public EidentContext eident(int i) {
			return getRuleContext(EidentContext.class,i);
		}
		public RecordExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecordExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecordExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecordExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowerSet2ExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public PowerSet2ExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPowerSet2Exp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPowerSet2Exp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPowerSet2Exp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MaxExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public MaxExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMaxExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMaxExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMaxExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayBuilderExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArrayBuilderExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterArrayBuilderExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitArrayBuilderExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitArrayBuilderExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EquivExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EquivExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEquivExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEquivExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEquivExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public MinExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMinExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMinExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMinExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TupleUpdateExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public DecimalContext decimal() {
			return getRuleContext(DecimalContext.class,0);
		}
		public TupleUpdateExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTupleUpdateExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTupleUpdateExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTupleUpdateExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BigIntersectExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BigIntersectExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterBigIntersectExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitBigIntersectExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitBigIntersectExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LetExpContext extends ExpContext {
		public List<BinderContext> binder() {
			return getRuleContexts(BinderContext.class);
		}
		public BinderContext binder(int i) {
			return getRuleContext(BinderContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public LetExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterLetExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitLetExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitLetExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusExpMultContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public PlusExpMultContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPlusExpMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPlusExpMult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPlusExpMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FactorialExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public FactorialExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterFactorialExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitFactorialExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitFactorialExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RemainderExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public RemainderExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRemainderExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRemainderExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRemainderExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecApplicationExpContext extends ExpContext {
		public List<IdentContext> ident() {
			return getRuleContexts(IdentContext.class);
		}
		public IdentContext ident(int i) {
			return getRuleContext(IdentContext.class,i);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public RecApplicationExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecApplicationExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecApplicationExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecApplicationExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CheckExpContext extends ExpContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public CheckExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterCheckExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitCheckExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitCheckExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public PlusExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPlusExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPlusExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPlusExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TupleSelectionExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public DecimalContext decimal() {
			return getRuleContext(DecimalContext.class,0);
		}
		public TupleSelectionExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTupleSelectionExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTupleSelectionExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTupleSelectionExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowerSet1ExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public PowerSet1ExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPowerSet1Exp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPowerSet1Exp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPowerSet1Exp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecordUpdateExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public RecordUpdateExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecordUpdateExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecordUpdateExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecordUpdateExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfThenElseExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IfThenElseExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIfThenElseExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIfThenElseExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIfThenElseExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecordSelectionExpContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public RecordSelectionExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecordSelectionExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecordSelectionExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecordSelectionExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntervalExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IntervalExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIntervalExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIntervalExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIntervalExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WithoutExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public WithoutExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterWithoutExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitWithoutExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitWithoutExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EmptySetExpContext extends ExpContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public EmptySetExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEmptySetExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEmptySetExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEmptySetExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintInExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode STRING() { return getToken(RISCALParser.STRING, 0); }
		public PrintInExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPrintInExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPrintInExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPrintInExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DividesExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public DividesExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDividesExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDividesExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDividesExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntersectExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IntersectExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIntersectExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIntersectExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIntersectExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterEqualExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public GreaterEqualExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterGreaterEqualExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitGreaterEqualExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitGreaterEqualExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnitExpContext extends ExpContext {
		public UnitExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterUnitExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitUnitExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitUnitExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChooseInElseExpContext extends ExpContext {
		public QvarContext qvar() {
			return getRuleContext(QvarContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ChooseInElseExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterChooseInElseExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitChooseInElseExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitChooseInElseExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowerExpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public PowerExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterPowerExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitPowerExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitPowerExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(752);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				{
				_localctx = new UnitExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(476);
				match(T__5);
				setState(477);
				match(T__7);
				}
				break;
			case 2:
				{
				_localctx = new TrueExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(478);
				_la = _input.LA(1);
				if ( !(_la==T__41 || _la==T__42) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 3:
				{
				_localctx = new FalseExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(479);
				_la = _input.LA(1);
				if ( !(_la==T__43 || _la==T__44) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 4:
				{
				_localctx = new IntLiteralExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(480);
				decimal();
				}
				break;
			case 5:
				{
				_localctx = new EmptySetExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(484);
				switch (_input.LA(1)) {
				case T__45:
					{
					setState(481);
					match(T__45);
					}
					break;
				case T__19:
					{
					setState(482);
					match(T__19);
					setState(483);
					match(T__21);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(486);
				match(T__46);
				setState(487);
				type();
				setState(488);
				match(T__47);
				}
				break;
			case 6:
				{
				_localctx = new RecIdentifierExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(490);
				ident();
				setState(491);
				match(T__48);
				setState(492);
				ident();
				}
				break;
			case 7:
				{
				_localctx = new RecApplicationExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(494);
				ident();
				setState(495);
				match(T__48);
				setState(496);
				ident();
				setState(497);
				match(T__5);
				setState(498);
				exp(0);
				setState(503);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(499);
					match(T__6);
					setState(500);
					exp(0);
					}
					}
					setState(505);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(506);
				match(T__7);
				}
				break;
			case 8:
				{
				_localctx = new IdentifierExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(508);
				ident();
				}
				break;
			case 9:
				{
				_localctx = new ApplicationExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(509);
				ident();
				setState(510);
				match(T__5);
				setState(519);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__19) | (1L << T__28) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__37) | (1L << T__38) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__66 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)) | (1L << (T__73 - 66)) | (1L << (T__81 - 66)) | (1L << (T__82 - 66)) | (1L << (T__83 - 66)) | (1L << (T__84 - 66)) | (1L << (T__85 - 66)) | (1L << (T__86 - 66)) | (1L << (T__87 - 66)) | (1L << (T__88 - 66)) | (1L << (T__89 - 66)) | (1L << (T__102 - 66)) | (1L << (T__103 - 66)) | (1L << (T__110 - 66)) | (1L << (T__111 - 66)) | (1L << (T__112 - 66)) | (1L << (T__113 - 66)) | (1L << (T__114 - 66)) | (1L << (T__116 - 66)) | (1L << (IDENT - 66)) | (1L << (DECIMAL - 66)))) != 0)) {
					{
					setState(511);
					exp(0);
					setState(516);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(512);
						match(T__6);
						setState(513);
						exp(0);
						}
						}
						setState(518);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(521);
				match(T__7);
				}
				break;
			case 10:
				{
				_localctx = new ArrayBuilderExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(523);
				match(T__49);
				setState(524);
				match(T__46);
				setState(525);
				exp(0);
				setState(526);
				match(T__6);
				setState(527);
				type();
				setState(528);
				match(T__47);
				setState(529);
				match(T__5);
				setState(530);
				exp(0);
				setState(531);
				match(T__7);
				}
				break;
			case 11:
				{
				_localctx = new MapBuilderExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(533);
				match(T__50);
				setState(534);
				match(T__46);
				setState(535);
				type();
				setState(536);
				match(T__6);
				setState(537);
				type();
				setState(538);
				match(T__47);
				setState(539);
				match(T__5);
				setState(540);
				exp(0);
				setState(541);
				match(T__7);
				}
				break;
			case 12:
				{
				_localctx = new PowerSetExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(543);
				match(T__51);
				setState(544);
				match(T__5);
				setState(545);
				exp(0);
				setState(546);
				match(T__7);
				}
				break;
			case 13:
				{
				_localctx = new PowerSet1ExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(548);
				match(T__51);
				setState(549);
				match(T__5);
				setState(550);
				exp(0);
				setState(551);
				match(T__6);
				setState(552);
				exp(0);
				setState(553);
				match(T__7);
				}
				break;
			case 14:
				{
				_localctx = new PowerSet2ExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(555);
				match(T__51);
				setState(556);
				match(T__5);
				setState(557);
				exp(0);
				setState(558);
				match(T__6);
				setState(559);
				exp(0);
				setState(560);
				match(T__6);
				setState(561);
				exp(0);
				setState(562);
				match(T__7);
				}
				break;
			case 15:
				{
				_localctx = new NegationExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(564);
				match(T__53);
				setState(565);
				exp(57);
				}
				break;
			case 16:
				{
				_localctx = new BigIntersectExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(566);
				_la = _input.LA(1);
				if ( !(_la==T__54 || _la==T__55) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(567);
				exp(56);
				}
				break;
			case 17:
				{
				_localctx = new BigUnionExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(568);
				_la = _input.LA(1);
				if ( !(_la==T__56 || _la==T__57) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(569);
				exp(55);
				}
				break;
			case 18:
				{
				_localctx = new EnumeratedSetExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(570);
				match(T__19);
				setState(571);
				exp(0);
				setState(576);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(572);
					match(T__6);
					setState(573);
					exp(0);
					}
					}
					setState(578);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(579);
				match(T__21);
				}
				break;
			case 19:
				{
				_localctx = new TupleExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(581);
				_la = _input.LA(1);
				if ( !(((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__66 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(582);
				exp(0);
				setState(587);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(583);
					match(T__6);
					setState(584);
					exp(0);
					}
					}
					setState(589);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(590);
				_la = _input.LA(1);
				if ( !(((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 20:
				{
				_localctx = new RecordExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(592);
				_la = _input.LA(1);
				if ( !(((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (T__65 - 66)) | (1L << (T__66 - 66)) | (1L << (T__67 - 66)) | (1L << (T__68 - 66)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(593);
				eident();
				setState(598);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(594);
					match(T__6);
					setState(595);
					eident();
					}
					}
					setState(600);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(601);
				_la = _input.LA(1);
				if ( !(((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 21:
				{
				_localctx = new SetSizeExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(603);
				match(T__73);
				setState(604);
				exp(0);
				setState(605);
				match(T__73);
				}
				break;
			case 22:
				{
				_localctx = new CartesianExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(607);
				match(T__5);
				setState(608);
				exp(0);
				setState(611); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(609);
					_la = _input.LA(1);
					if ( !(_la==T__79 || _la==T__80) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(610);
					exp(0);
					}
					}
					setState(613); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__79 || _la==T__80 );
				setState(615);
				match(T__7);
				}
				break;
			case 23:
				{
				_localctx = new NumberExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(617);
				match(T__81);
				setState(618);
				qvar();
				}
				break;
			case 24:
				{
				_localctx = new SetBuilderExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(619);
				match(T__19);
				setState(620);
				exp(0);
				setState(621);
				match(T__73);
				setState(622);
				qvar();
				setState(623);
				match(T__21);
				}
				break;
			case 25:
				{
				_localctx = new ChooseExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(625);
				match(T__28);
				setState(626);
				qvar();
				}
				break;
			case 26:
				{
				_localctx = new SumExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(627);
				_la = _input.LA(1);
				if ( !(((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (T__82 - 83)) | (1L << (T__83 - 83)) | (1L << (T__84 - 83)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(628);
				qvar();
				setState(629);
				match(T__52);
				setState(630);
				exp(33);
				}
				break;
			case 27:
				{
				_localctx = new ProductExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(632);
				_la = _input.LA(1);
				if ( !(((((_la - 86)) & ~0x3f) == 0 && ((1L << (_la - 86)) & ((1L << (T__85 - 86)) | (1L << (T__86 - 86)) | (1L << (T__87 - 86)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(633);
				qvar();
				setState(634);
				match(T__52);
				setState(635);
				exp(32);
				}
				break;
			case 28:
				{
				_localctx = new MinExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(637);
				match(T__88);
				setState(638);
				qvar();
				setState(639);
				match(T__52);
				setState(640);
				exp(31);
				}
				break;
			case 29:
				{
				_localctx = new MaxExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(642);
				match(T__89);
				setState(643);
				qvar();
				setState(644);
				match(T__52);
				setState(645);
				exp(30);
				}
				break;
			case 30:
				{
				_localctx = new NotExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(647);
				_la = _input.LA(1);
				if ( !(_la==T__102 || _la==T__103) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(648);
				exp(18);
				}
				break;
			case 31:
				{
				_localctx = new IfThenElseExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(649);
				match(T__37);
				setState(650);
				exp(0);
				setState(651);
				match(T__35);
				setState(652);
				exp(0);
				setState(653);
				match(T__36);
				setState(654);
				exp(0);
				}
				break;
			case 32:
				{
				_localctx = new MatchExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(656);
				match(T__38);
				setState(657);
				exp(0);
				setState(658);
				match(T__12);
				setState(659);
				match(T__19);
				setState(663); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(660);
					pexp();
					setState(661);
					match(EOS);
					}
					}
					setState(665); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__124 || _la==IDENT );
				setState(667);
				match(T__21);
				}
				break;
			case 33:
				{
				_localctx = new ForallExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(669);
				_la = _input.LA(1);
				if ( !(_la==T__110 || _la==T__111) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(670);
				qvar();
				setState(671);
				match(T__52);
				setState(672);
				exp(11);
				}
				break;
			case 34:
				{
				_localctx = new ExistsExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(674);
				_la = _input.LA(1);
				if ( !(_la==T__112 || _la==T__113) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(675);
				qvar();
				setState(676);
				match(T__52);
				setState(677);
				exp(10);
				}
				break;
			case 35:
				{
				_localctx = new LetExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(679);
				match(T__114);
				setState(680);
				binder();
				setState(685);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(681);
					match(T__6);
					setState(682);
					binder();
					}
					}
					setState(687);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(688);
				match(T__115);
				setState(689);
				exp(9);
				}
				break;
			case 36:
				{
				_localctx = new LetParExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(691);
				match(T__116);
				setState(692);
				binder();
				setState(697);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(693);
					match(T__6);
					setState(694);
					binder();
					}
					}
					setState(699);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(700);
				match(T__115);
				setState(701);
				exp(8);
				}
				break;
			case 37:
				{
				_localctx = new ChooseInExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(703);
				match(T__28);
				setState(704);
				qvar();
				setState(705);
				match(T__115);
				setState(706);
				exp(7);
				}
				break;
			case 38:
				{
				_localctx = new ChooseInElseExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(708);
				match(T__28);
				setState(709);
				qvar();
				setState(710);
				match(T__115);
				setState(711);
				exp(0);
				setState(712);
				match(T__36);
				setState(713);
				exp(0);
				}
				break;
			case 39:
				{
				_localctx = new AssertExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(715);
				match(T__32);
				setState(716);
				exp(0);
				setState(717);
				match(T__115);
				setState(718);
				exp(0);
				}
				break;
			case 40:
				{
				_localctx = new PrintExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(720);
				match(T__33);
				setState(723);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(721);
					match(STRING);
					setState(722);
					match(T__6);
					}
				}

				setState(725);
				exp(4);
				}
				break;
			case 41:
				{
				_localctx = new PrintInExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(726);
				match(T__33);
				setState(729);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(727);
					match(STRING);
					setState(728);
					match(T__6);
					}
				}

				setState(731);
				exp(0);
				setState(736);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(732);
					match(T__6);
					setState(733);
					exp(0);
					}
					}
					setState(738);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(739);
				match(T__115);
				setState(740);
				exp(0);
				}
				break;
			case 42:
				{
				_localctx = new CheckExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(742);
				match(T__34);
				setState(743);
				ident();
				setState(746);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
				case 1:
					{
					setState(744);
					match(T__12);
					setState(745);
					exp(0);
					}
					break;
				}
				}
				break;
			case 43:
				{
				_localctx = new ParenthesizedExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(748);
				match(T__5);
				setState(749);
				exp(0);
				setState(750);
				match(T__7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(878);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(876);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
					case 1:
						{
						_localctx = new PowerExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(754);
						if (!(precpred(_ctx, 54))) throw new FailedPredicateException(this, "precpred(_ctx, 54)");
						setState(755);
						match(T__58);
						setState(756);
						exp(55);
						}
						break;
					case 2:
						{
						_localctx = new TimesExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(757);
						if (!(precpred(_ctx, 53))) throw new FailedPredicateException(this, "precpred(_ctx, 53)");
						setState(758);
						_la = _input.LA(1);
						if ( !(_la==T__59 || _la==T__60) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(759);
						exp(54);
						}
						break;
					case 3:
						{
						_localctx = new TimesExpMultContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(760);
						if (!(precpred(_ctx, 52))) throw new FailedPredicateException(this, "precpred(_ctx, 52)");
						setState(761);
						_la = _input.LA(1);
						if ( !(_la==T__59 || _la==T__60) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(762);
						match(T__61);
						setState(763);
						_la = _input.LA(1);
						if ( !(_la==T__59 || _la==T__60) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(764);
						exp(53);
						}
						break;
					case 4:
						{
						_localctx = new DividesExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(765);
						if (!(precpred(_ctx, 51))) throw new FailedPredicateException(this, "precpred(_ctx, 51)");
						setState(766);
						match(T__62);
						setState(767);
						exp(52);
						}
						break;
					case 5:
						{
						_localctx = new RemainderExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(768);
						if (!(precpred(_ctx, 50))) throw new FailedPredicateException(this, "precpred(_ctx, 50)");
						setState(769);
						match(T__63);
						setState(770);
						exp(51);
						}
						break;
					case 6:
						{
						_localctx = new MinusExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(771);
						if (!(precpred(_ctx, 49))) throw new FailedPredicateException(this, "precpred(_ctx, 49)");
						setState(772);
						match(T__53);
						setState(773);
						exp(50);
						}
						break;
					case 7:
						{
						_localctx = new PlusExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(774);
						if (!(precpred(_ctx, 48))) throw new FailedPredicateException(this, "precpred(_ctx, 48)");
						setState(775);
						match(T__64);
						setState(776);
						exp(49);
						}
						break;
					case 8:
						{
						_localctx = new PlusExpMultContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(777);
						if (!(precpred(_ctx, 47))) throw new FailedPredicateException(this, "precpred(_ctx, 47)");
						setState(778);
						match(T__64);
						setState(779);
						match(T__61);
						setState(780);
						match(T__64);
						setState(781);
						exp(48);
						}
						break;
					case 9:
						{
						_localctx = new IntervalExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(782);
						if (!(precpred(_ctx, 45))) throw new FailedPredicateException(this, "precpred(_ctx, 45)");
						setState(783);
						match(T__61);
						setState(784);
						exp(46);
						}
						break;
					case 10:
						{
						_localctx = new IntersectExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(785);
						if (!(precpred(_ctx, 41))) throw new FailedPredicateException(this, "precpred(_ctx, 41)");
						setState(786);
						_la = _input.LA(1);
						if ( !(_la==T__74 || _la==T__75) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(787);
						exp(42);
						}
						break;
					case 11:
						{
						_localctx = new UnionExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(788);
						if (!(precpred(_ctx, 40))) throw new FailedPredicateException(this, "precpred(_ctx, 40)");
						setState(789);
						_la = _input.LA(1);
						if ( !(_la==T__76 || _la==T__77) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(790);
						exp(41);
						}
						break;
					case 12:
						{
						_localctx = new WithoutExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(791);
						if (!(precpred(_ctx, 39))) throw new FailedPredicateException(this, "precpred(_ctx, 39)");
						setState(792);
						match(T__78);
						setState(793);
						exp(40);
						}
						break;
					case 13:
						{
						_localctx = new ArrayUpdateExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(794);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(795);
						match(T__12);
						setState(796);
						match(T__46);
						setState(797);
						exp(0);
						setState(798);
						match(T__47);
						setState(799);
						match(T__11);
						setState(800);
						exp(30);
						}
						break;
					case 14:
						{
						_localctx = new TupleUpdateExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(802);
						if (!(precpred(_ctx, 28))) throw new FailedPredicateException(this, "precpred(_ctx, 28)");
						setState(803);
						match(T__12);
						setState(804);
						match(T__52);
						setState(805);
						decimal();
						setState(806);
						match(T__11);
						setState(807);
						exp(29);
						}
						break;
					case 15:
						{
						_localctx = new RecordUpdateExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(809);
						if (!(precpred(_ctx, 27))) throw new FailedPredicateException(this, "precpred(_ctx, 27)");
						setState(810);
						match(T__12);
						setState(811);
						match(T__52);
						setState(812);
						ident();
						setState(813);
						match(T__11);
						setState(814);
						exp(28);
						}
						break;
					case 16:
						{
						_localctx = new EqualsExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(816);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(817);
						match(T__11);
						setState(818);
						exp(27);
						}
						break;
					case 17:
						{
						_localctx = new NotEqualsExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(819);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(820);
						_la = _input.LA(1);
						if ( !(_la==T__90 || _la==T__91) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(821);
						exp(26);
						}
						break;
					case 18:
						{
						_localctx = new LessExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(822);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(823);
						match(T__92);
						setState(824);
						exp(25);
						}
						break;
					case 19:
						{
						_localctx = new LessEqualExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(825);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(826);
						_la = _input.LA(1);
						if ( !(_la==T__93 || _la==T__94) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(827);
						exp(24);
						}
						break;
					case 20:
						{
						_localctx = new GreaterExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(828);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(829);
						match(T__95);
						setState(830);
						exp(23);
						}
						break;
					case 21:
						{
						_localctx = new GreaterEqualExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(831);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(832);
						_la = _input.LA(1);
						if ( !(_la==T__96 || _la==T__97) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(833);
						exp(22);
						}
						break;
					case 22:
						{
						_localctx = new InSetExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(834);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(835);
						_la = _input.LA(1);
						if ( !(_la==T__98 || _la==T__99) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(836);
						exp(21);
						}
						break;
					case 23:
						{
						_localctx = new SubsetExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(837);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(838);
						_la = _input.LA(1);
						if ( !(_la==T__100 || _la==T__101) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(839);
						exp(20);
						}
						break;
					case 24:
						{
						_localctx = new AndExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(840);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(841);
						_la = _input.LA(1);
						if ( !(_la==T__104 || _la==T__105) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(842);
						exp(18);
						}
						break;
					case 25:
						{
						_localctx = new OrExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(843);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(844);
						_la = _input.LA(1);
						if ( !(_la==T__106 || _la==T__107) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(845);
						exp(17);
						}
						break;
					case 26:
						{
						_localctx = new ImpliesExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(846);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(847);
						_la = _input.LA(1);
						if ( !(_la==T__108 || _la==T__109) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(848);
						exp(16);
						}
						break;
					case 27:
						{
						_localctx = new EquivExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(849);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(850);
						_la = _input.LA(1);
						if ( !(_la==T__16 || _la==T__17) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(851);
						exp(15);
						}
						break;
					case 28:
						{
						_localctx = new ArraySelectionExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(852);
						if (!(precpred(_ctx, 61))) throw new FailedPredicateException(this, "precpred(_ctx, 61)");
						setState(853);
						match(T__46);
						setState(854);
						exp(0);
						setState(855);
						match(T__47);
						}
						break;
					case 29:
						{
						_localctx = new TupleSelectionExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(857);
						if (!(precpred(_ctx, 60))) throw new FailedPredicateException(this, "precpred(_ctx, 60)");
						setState(858);
						match(T__52);
						setState(859);
						decimal();
						}
						break;
					case 30:
						{
						_localctx = new RecordSelectionExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(860);
						if (!(precpred(_ctx, 59))) throw new FailedPredicateException(this, "precpred(_ctx, 59)");
						setState(861);
						match(T__52);
						setState(862);
						ident();
						}
						break;
					case 31:
						{
						_localctx = new FactorialExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(863);
						if (!(precpred(_ctx, 58))) throw new FailedPredicateException(this, "precpred(_ctx, 58)");
						setState(864);
						match(T__48);
						}
						break;
					case 32:
						{
						_localctx = new CartesianExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(865);
						if (!(precpred(_ctx, 37))) throw new FailedPredicateException(this, "precpred(_ctx, 37)");
						setState(872); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(866);
								if (!(cartesian)) throw new FailedPredicateException(this, "cartesian");
								setState(867);
								_la = _input.LA(1);
								if ( !(_la==T__79 || _la==T__80) ) {
								_errHandler.recoverInline(this);
								} else {
									consume();
								}
								cartesian=false;
								setState(869);
								exp(0);
								cartesian=true;
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(874); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,60,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					}
					} 
				}
				setState(880);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArrayTypeContext extends TypeContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolTypeContext extends TypeContext {
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterBoolType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitBoolType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecordTypeContext extends TypeContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public RecordTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecordType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecordType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecordType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public SetTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterSetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitSetType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitSetType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnitTypeContext extends TypeContext {
		public UnitTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterUnitType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitUnitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitUnitType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TupleTypeContext extends TypeContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TupleTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTupleType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTupleType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTupleType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NatTypeContext extends TypeContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NatTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterNatType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitNatType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitNatType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentifierTypeContext extends TypeContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public IdentifierTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdentifierType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdentifierType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdentifierType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapTypeContext extends TypeContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public MapTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMapType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMapType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntTypeContext extends TypeContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIntType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIntType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type);
		int _la;
		try {
			setState(943);
			switch (_input.LA(1)) {
			case T__5:
			case T__117:
				_localctx = new UnitTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(884);
				switch (_input.LA(1)) {
				case T__117:
					{
					setState(881);
					match(T__117);
					}
					break;
				case T__5:
					{
					setState(882);
					match(T__5);
					setState(883);
					match(T__7);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__118:
				_localctx = new BoolTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(886);
				match(T__118);
				}
				break;
			case T__119:
			case T__120:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(887);
				_la = _input.LA(1);
				if ( !(_la==T__119 || _la==T__120) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(888);
				match(T__46);
				setState(889);
				exp(0);
				setState(890);
				match(T__6);
				setState(891);
				exp(0);
				setState(892);
				match(T__47);
				}
				break;
			case T__50:
				_localctx = new MapTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(894);
				match(T__50);
				setState(895);
				match(T__46);
				setState(896);
				type();
				setState(897);
				match(T__6);
				setState(898);
				type();
				setState(899);
				match(T__47);
				}
				break;
			case T__121:
				_localctx = new TupleTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(901);
				match(T__121);
				setState(902);
				match(T__46);
				setState(903);
				type();
				setState(908);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(904);
					match(T__6);
					setState(905);
					type();
					}
					}
					setState(910);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(911);
				match(T__47);
				}
				break;
			case T__122:
				_localctx = new RecordTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(913);
				match(T__122);
				setState(914);
				match(T__46);
				setState(915);
				param();
				setState(920);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(916);
					match(T__6);
					setState(917);
					param();
					}
					}
					setState(922);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(923);
				match(T__47);
				}
				break;
			case T__2:
			case T__3:
				_localctx = new NatTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(925);
				_la = _input.LA(1);
				if ( !(_la==T__2 || _la==T__3) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(926);
				match(T__46);
				setState(927);
				exp(0);
				setState(928);
				match(T__47);
				}
				break;
			case T__51:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(930);
				match(T__51);
				setState(931);
				match(T__46);
				setState(932);
				type();
				setState(933);
				match(T__47);
				}
				break;
			case T__49:
				_localctx = new ArrayTypeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(935);
				match(T__49);
				setState(936);
				match(T__46);
				setState(937);
				exp(0);
				setState(938);
				match(T__6);
				setState(939);
				type();
				setState(940);
				match(T__47);
				}
				break;
			case IDENT:
				_localctx = new IdentifierTypeContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(942);
				ident();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QvarContext extends ParserRuleContext {
		public QvarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qvar; }
	 
		public QvarContext() { }
		public void copyFrom(QvarContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class QuantifiedVariableContext extends QvarContext {
		public List<QvcoreContext> qvcore() {
			return getRuleContexts(QvcoreContext.class);
		}
		public QvcoreContext qvcore(int i) {
			return getRuleContext(QvcoreContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public QuantifiedVariableContext(QvarContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterQuantifiedVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitQuantifiedVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitQuantifiedVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QvarContext qvar() throws RecognitionException {
		QvarContext _localctx = new QvarContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_qvar);
		try {
			int _alt;
			_localctx = new QuantifiedVariableContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(945);
			qvcore();
			setState(950);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(946);
					match(T__6);
					setState(947);
					qvcore();
					}
					} 
				}
				setState(952);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,67,_ctx);
			}
			setState(955);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				{
				setState(953);
				match(T__12);
				setState(954);
				exp(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QvcoreContext extends ParserRuleContext {
		public QvcoreContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qvcore; }
	 
		public QvcoreContext() { }
		public void copyFrom(QvcoreContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdentifierTypeQuantifiedVarContext extends QvcoreContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentifierTypeQuantifiedVarContext(QvcoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdentifierTypeQuantifiedVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdentifierTypeQuantifiedVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdentifierTypeQuantifiedVar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentifierSetQuantifiedVarContext extends QvcoreContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IdentifierSetQuantifiedVarContext(QvcoreContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdentifierSetQuantifiedVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdentifierSetQuantifiedVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdentifierSetQuantifiedVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QvcoreContext qvcore() throws RecognitionException {
		QvcoreContext _localctx = new QvcoreContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_qvcore);
		int _la;
		try {
			setState(965);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				_localctx = new IdentifierTypeQuantifiedVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(957);
				ident();
				setState(958);
				match(T__1);
				setState(959);
				type();
				}
				break;
			case 2:
				_localctx = new IdentifierSetQuantifiedVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(961);
				ident();
				setState(962);
				_la = _input.LA(1);
				if ( !(_la==T__98 || _la==T__115) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(963);
				exp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinderContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BinderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterBinder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitBinder(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitBinder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinderContext binder() throws RecognitionException {
		BinderContext _localctx = new BinderContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_binder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(967);
			ident();
			setState(968);
			match(T__11);
			setState(969);
			exp(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PcommandContext extends ParserRuleContext {
		public PcommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pcommand; }
	 
		public PcommandContext() { }
		public void copyFrom(PcommandContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ApplicationPatternCommandContext extends PcommandContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ApplicationPatternCommandContext(PcommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterApplicationPatternCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitApplicationPatternCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitApplicationPatternCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentifierPatternCommandContext extends PcommandContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public IdentifierPatternCommandContext(PcommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdentifierPatternCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdentifierPatternCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdentifierPatternCommand(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DefaultPatternCommandContext extends PcommandContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public DefaultPatternCommandContext(PcommandContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDefaultPatternCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDefaultPatternCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDefaultPatternCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PcommandContext pcommand() throws RecognitionException {
		PcommandContext _localctx = new PcommandContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pcommand);
		int _la;
		try {
			setState(992);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				_localctx = new IdentifierPatternCommandContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(971);
				ident();
				setState(972);
				match(T__123);
				setState(973);
				command();
				}
				break;
			case 2:
				_localctx = new ApplicationPatternCommandContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(975);
				ident();
				setState(976);
				match(T__5);
				setState(977);
				param();
				setState(982);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(978);
					match(T__6);
					setState(979);
					param();
					}
					}
					setState(984);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(985);
				match(T__7);
				setState(986);
				match(T__123);
				setState(987);
				command();
				}
				break;
			case 3:
				_localctx = new DefaultPatternCommandContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(989);
				match(T__124);
				setState(990);
				match(T__123);
				setState(991);
				command();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PexpContext extends ParserRuleContext {
		public PexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pexp; }
	 
		public PexpContext() { }
		public void copyFrom(PexpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdentifierPatternExpContext extends PexpContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IdentifierPatternExpContext(PexpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdentifierPatternExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdentifierPatternExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdentifierPatternExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ApplicationPatternExpContext extends PexpContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ApplicationPatternExpContext(PexpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterApplicationPatternExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitApplicationPatternExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitApplicationPatternExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DefaultPatternExpContext extends PexpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public DefaultPatternExpContext(PexpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDefaultPatternExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDefaultPatternExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDefaultPatternExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PexpContext pexp() throws RecognitionException {
		PexpContext _localctx = new PexpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_pexp);
		int _la;
		try {
			setState(1015);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				_localctx = new IdentifierPatternExpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(994);
				ident();
				setState(995);
				match(T__123);
				setState(996);
				exp(0);
				}
				break;
			case 2:
				_localctx = new ApplicationPatternExpContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(998);
				ident();
				setState(999);
				match(T__5);
				setState(1000);
				param();
				setState(1005);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(1001);
					match(T__6);
					setState(1002);
					param();
					}
					}
					setState(1007);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1008);
				match(T__7);
				setState(1009);
				match(T__123);
				setState(1010);
				exp(0);
				}
				break;
			case 3:
				_localctx = new DefaultPatternExpContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1012);
				match(T__124);
				setState(1013);
				match(T__123);
				setState(1014);
				exp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1017);
			ident();
			setState(1018);
			match(T__1);
			setState(1019);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RitemContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public List<RidentContext> rident() {
			return getRuleContexts(RidentContext.class);
		}
		public RidentContext rident(int i) {
			return getRuleContext(RidentContext.class,i);
		}
		public RitemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ritem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRitem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRitem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRitem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RitemContext ritem() throws RecognitionException {
		RitemContext _localctx = new RitemContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ritem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1021);
			ident();
			setState(1022);
			match(T__11);
			setState(1023);
			rident();
			setState(1028);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__73) {
				{
				{
				setState(1024);
				match(T__73);
				setState(1025);
				rident();
				}
				}
				setState(1030);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EidentContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public EidentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterEident(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitEident(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitEident(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EidentContext eident() throws RecognitionException {
		EidentContext _localctx = new EidentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_eident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1031);
			ident();
			setState(1032);
			match(T__1);
			setState(1033);
			exp(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RidentContext extends ParserRuleContext {
		public RidentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rident; }
	 
		public RidentContext() { }
		public void copyFrom(RidentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RecIdentifierContext extends RidentContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public RecIdentifierContext(RidentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecApplicationContext extends RidentContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public RecApplicationContext(RidentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecApplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecApplication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecApplication(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RidentContext rident() throws RecognitionException {
		RidentContext _localctx = new RidentContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_rident);
		int _la;
		try {
			setState(1048);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,76,_ctx) ) {
			case 1:
				_localctx = new RecIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1035);
				ident();
				}
				break;
			case 2:
				_localctx = new RecApplicationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1036);
				ident();
				setState(1037);
				match(T__5);
				setState(1038);
				type();
				setState(1043);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(1039);
					match(T__6);
					setState(1040);
					type();
					}
					}
					setState(1045);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1046);
				match(T__7);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelContext extends ParserRuleContext {
		public SelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sel; }
	 
		public SelContext() { }
		public void copyFrom(SelContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MapSelectorContext extends SelContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public MapSelectorContext(SelContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterMapSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitMapSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitMapSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RecordSelectorContext extends SelContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public RecordSelectorContext(SelContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterRecordSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitRecordSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitRecordSelector(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TupleSelectorContext extends SelContext {
		public DecimalContext decimal() {
			return getRuleContext(DecimalContext.class,0);
		}
		public TupleSelectorContext(SelContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterTupleSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitTupleSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitTupleSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelContext sel() throws RecognitionException {
		SelContext _localctx = new SelContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_sel);
		try {
			setState(1058);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
			case 1:
				_localctx = new MapSelectorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1050);
				match(T__46);
				setState(1051);
				exp(0);
				setState(1052);
				match(T__47);
				}
				break;
			case 2:
				_localctx = new TupleSelectorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1054);
				match(T__52);
				setState(1055);
				decimal();
				}
				break;
			case 3:
				_localctx = new RecordSelectorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1056);
				match(T__52);
				setState(1057);
				ident();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleContext extends ParserRuleContext {
		public MultipleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple; }
	 
		public MultipleContext() { }
		public void copyFrom(MultipleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IsMultipleContext extends MultipleContext {
		public IsMultipleContext(MultipleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIsMultiple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIsMultiple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIsMultiple(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNotMultipleContext extends MultipleContext {
		public IsNotMultipleContext(MultipleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIsNotMultiple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIsNotMultiple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIsNotMultiple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleContext multiple() throws RecognitionException {
		MultipleContext _localctx = new MultipleContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_multiple);
		try {
			setState(1062);
			switch (_input.LA(1)) {
			case T__4:
			case T__8:
			case T__9:
			case T__18:
				_localctx = new IsNotMultipleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case T__125:
				_localctx = new IsMultipleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1061);
				match(T__125);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(RISCALParser.IDENT, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitIdent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1064);
			match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecimalContext extends ParserRuleContext {
		public TerminalNode DECIMAL() { return getToken(RISCALParser.DECIMAL, 0); }
		public DecimalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decimal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).enterDecimal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RISCALListener ) ((RISCALListener)listener).exitDecimal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RISCALVisitor ) return ((RISCALVisitor<? extends T>)visitor).visitDecimal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecimalContext decimal() throws RecognitionException {
		DecimalContext _localctx = new DecimalContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_decimal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1066);
			match(DECIMAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 54);
		case 1:
			return precpred(_ctx, 53);
		case 2:
			return precpred(_ctx, 52);
		case 3:
			return precpred(_ctx, 51);
		case 4:
			return precpred(_ctx, 50);
		case 5:
			return precpred(_ctx, 49);
		case 6:
			return precpred(_ctx, 48);
		case 7:
			return precpred(_ctx, 47);
		case 8:
			return precpred(_ctx, 45);
		case 9:
			return precpred(_ctx, 41);
		case 10:
			return precpred(_ctx, 40);
		case 11:
			return precpred(_ctx, 39);
		case 12:
			return precpred(_ctx, 29);
		case 13:
			return precpred(_ctx, 28);
		case 14:
			return precpred(_ctx, 27);
		case 15:
			return precpred(_ctx, 26);
		case 16:
			return precpred(_ctx, 25);
		case 17:
			return precpred(_ctx, 24);
		case 18:
			return precpred(_ctx, 23);
		case 19:
			return precpred(_ctx, 22);
		case 20:
			return precpred(_ctx, 21);
		case 21:
			return precpred(_ctx, 20);
		case 22:
			return precpred(_ctx, 19);
		case 23:
			return precpred(_ctx, 17);
		case 24:
			return precpred(_ctx, 16);
		case 25:
			return precpred(_ctx, 15);
		case 26:
			return precpred(_ctx, 14);
		case 27:
			return precpred(_ctx, 61);
		case 28:
			return precpred(_ctx, 60);
		case 29:
			return precpred(_ctx, 59);
		case 30:
			return precpred(_ctx, 58);
		case 31:
			return precpred(_ctx, 37);
		case 32:
			return cartesian;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\u0088\u042f\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3B\n\3"+
		"\f\3\16\3E\13\3\5\3G\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\7\3U\n\3\f\3\16\3X\13\3\5\3Z\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\7\3f\n\3\f\3\16\3i\13\3\5\3k\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\5\3w\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\u0082\n\3"+
		"\f\3\16\3\u0085\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u0091"+
		"\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\u00aa\n\3\f\3\16\3\u00ad\13\3\5\3\u00af"+
		"\n\3\3\3\3\3\3\3\3\3\7\3\u00b5\n\3\f\3\16\3\u00b8\13\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\u00c5\n\3\f\3\16\3\u00c8\13\3\5\3\u00ca"+
		"\n\3\3\3\3\3\7\3\u00ce\n\3\f\3\16\3\u00d1\13\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\7\3\u00de\n\3\f\3\16\3\u00e1\13\3\5\3\u00e3\n\3\3"+
		"\3\3\3\3\3\3\3\7\3\u00e9\n\3\f\3\16\3\u00ec\13\3\3\3\3\3\7\3\u00f0\n\3"+
		"\f\3\16\3\u00f3\13\3\3\3\3\3\3\3\3\3\5\3\u00f9\n\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\7\3\u0104\n\3\f\3\16\3\u0107\13\3\5\3\u0109\n\3\3\3\3"+
		"\3\7\3\u010d\n\3\f\3\16\3\u0110\13\3\3\3\3\3\3\3\3\3\5\3\u0116\n\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4\u0124\n\4\f\4\16\4\u0127"+
		"\13\4\3\4\3\4\3\4\3\4\5\4\u012d\n\4\3\5\3\5\3\5\7\5\u0132\n\5\f\5\16\5"+
		"\u0135\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u013e\n\5\f\5\16\5\u0141\13"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u014d\n\5\3\5\3\5\3\5\3"+
		"\5\5\5\u0153\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u015d\n\5\3\5\3\5"+
		"\3\5\7\5\u0162\n\5\f\5\16\5\u0165\13\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u016d"+
		"\n\5\3\5\5\5\u0170\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u018d"+
		"\n\6\r\6\16\6\u018e\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u0197\n\6\f\6\16\6\u019a"+
		"\13\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u01a6\n\6\f\6\16\6\u01a9"+
		"\13\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u01b1\n\6\f\6\16\6\u01b4\13\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\7\6\u01bc\n\6\f\6\16\6\u01bf\13\6\3\6\3\6\3\6\3\6\7"+
		"\6\u01c5\n\6\f\6\16\6\u01c8\13\6\3\6\5\6\u01cb\n\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\7\7\u01d5\n\7\f\7\16\7\u01d8\13\7\3\7\3\7\5\7\u01dc\n\7\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u01e7\n\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u01f8\n\b\f\b\16\b\u01fb\13"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0205\n\b\f\b\16\b\u0208\13\b\5"+
		"\b\u020a\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\7\b\u0241\n\b\f\b\16\b\u0244\13\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\7\b\u024c\n\b\f\b\16\b\u024f\13\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u0257"+
		"\n\b\f\b\16\b\u025a\13\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\6\b\u0266"+
		"\n\b\r\b\16\b\u0267\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\6\b\u029a\n\b\r\b\16\b\u029b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u02ae\n\b\f\b\16\b\u02b1\13\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\7\b\u02ba\n\b\f\b\16\b\u02bd\13\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\5\b\u02d6\n\b\3\b\3\b\3\b\3\b\5\b\u02dc\n\b\3\b\3\b\3\b\7\b\u02e1"+
		"\n\b\f\b\16\b\u02e4\13\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u02ed\n\b\3\b"+
		"\3\b\3\b\3\b\5\b\u02f3\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\6\b\u036b\n\b\r\b\16\b\u036c\7\b\u036f\n\b\f\b\16\b\u0372\13"+
		"\b\3\t\3\t\3\t\5\t\u0377\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u038d\n\t\f\t\16\t\u0390\13"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0399\n\t\f\t\16\t\u039c\13\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\5\t\u03b2\n\t\3\n\3\n\3\n\7\n\u03b7\n\n\f\n\16\n\u03ba\13\n\3\n\3"+
		"\n\5\n\u03be\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u03c8\n"+
		"\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u03d7\n\r"+
		"\f\r\16\r\u03da\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u03e3\n\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u03ee\n\16\f\16\16\16\u03f1"+
		"\13\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u03fa\n\16\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\7\20\u0405\n\20\f\20\16\20\u0408\13"+
		"\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\7\22\u0414\n\22"+
		"\f\22\16\22\u0417\13\22\3\22\3\22\5\22\u041b\n\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\5\23\u0425\n\23\3\24\3\24\5\24\u0429\n\24\3\25\3"+
		"\25\3\26\3\26\3\26\2\3\16\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"$&(*\2\36\3\2\5\6\3\2\23\24\4\2\16\16\35\36\3\2,-\3\2./\3\29:\3\2;<\3"+
		"\2DG\3\2HK\3\2RS\3\2UW\3\2XZ\3\2ij\3\2qr\3\2st\3\2>?\3\2MN\3\2OP\3\2]"+
		"^\3\2`a\3\2cd\3\2ef\3\2gh\3\2kl\3\2mn\3\2op\3\2z{\4\2eevv\u04d9\2/\3\2"+
		"\2\2\4\u0115\3\2\2\2\6\u012c\3\2\2\2\b\u016f\3\2\2\2\n\u01ca\3\2\2\2\f"+
		"\u01db\3\2\2\2\16\u02f2\3\2\2\2\20\u03b1\3\2\2\2\22\u03b3\3\2\2\2\24\u03c7"+
		"\3\2\2\2\26\u03c9\3\2\2\2\30\u03e2\3\2\2\2\32\u03f9\3\2\2\2\34\u03fb\3"+
		"\2\2\2\36\u03ff\3\2\2\2 \u0409\3\2\2\2\"\u041a\3\2\2\2$\u0424\3\2\2\2"+
		"&\u0428\3\2\2\2(\u042a\3\2\2\2*\u042c\3\2\2\2,.\5\4\3\2-,\3\2\2\2.\61"+
		"\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\7\2\2\3"+
		"\63\3\3\2\2\2\64\65\7\3\2\2\65\66\5(\25\2\66\67\7\4\2\2\678\t\2\2\289"+
		"\7\u0083\2\29\u0116\3\2\2\2:;\5&\24\2;<\7\7\2\2<=\5(\25\2=F\7\b\2\2>C"+
		"\5\34\17\2?@\7\t\2\2@B\5\34\17\2A?\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2"+
		"\2DG\3\2\2\2EC\3\2\2\2F>\3\2\2\2FG\3\2\2\2GH\3\2\2\2HI\7\n\2\2IJ\7\4\2"+
		"\2JK\5\20\t\2KL\7\u0083\2\2L\u0116\3\2\2\2MN\5&\24\2NO\7\13\2\2OP\5(\25"+
		"\2PY\7\b\2\2QV\5\34\17\2RS\7\t\2\2SU\5\34\17\2TR\3\2\2\2UX\3\2\2\2VT\3"+
		"\2\2\2VW\3\2\2\2WZ\3\2\2\2XV\3\2\2\2YQ\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\"+
		"\7\n\2\2\\]\7\u0083\2\2]\u0116\3\2\2\2^_\5&\24\2_`\7\f\2\2`a\5(\25\2a"+
		"j\7\b\2\2bg\5\34\17\2cd\7\t\2\2df\5\34\17\2ec\3\2\2\2fi\3\2\2\2ge\3\2"+
		"\2\2gh\3\2\2\2hk\3\2\2\2ig\3\2\2\2jb\3\2\2\2jk\3\2\2\2kl\3\2\2\2lm\7\n"+
		"\2\2mn\7\4\2\2no\5\20\t\2o\u0116\3\2\2\2pq\7\r\2\2qr\5(\25\2rs\7\16\2"+
		"\2sv\5\20\t\2tu\7\17\2\2uw\5\16\b\2vt\3\2\2\2vw\3\2\2\2wx\3\2\2\2xy\7"+
		"\u0083\2\2y\u0116\3\2\2\2z{\7\20\2\2{|\7\b\2\2|}\5\16\b\2}~\7\n\2\2~\u0083"+
		"\5\36\20\2\177\u0080\7\21\2\2\u0080\u0082\5\36\20\2\u0081\177\3\2\2\2"+
		"\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0086"+
		"\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\7\u0083\2\2\u0087\u0116\3\2\2"+
		"\2\u0088\u0089\7\22\2\2\u0089\u008a\5\36\20\2\u008a\u008b\7\u0083\2\2"+
		"\u008b\u0116\3\2\2\2\u008c\u008d\7\3\2\2\u008d\u0090\5(\25\2\u008e\u008f"+
		"\7\4\2\2\u008f\u0091\5\20\t\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2"+
		"\u0091\u0092\3\2\2\2\u0092\u0093\7\16\2\2\u0093\u0094\5\16\b\2\u0094\u0095"+
		"\7\u0083\2\2\u0095\u0116\3\2\2\2\u0096\u0097\7\13\2\2\u0097\u0098\5(\25"+
		"\2\u0098\u0099\t\3\2\2\u0099\u009a\5\16\b\2\u009a\u009b\7\u0083\2\2\u009b"+
		"\u0116\3\2\2\2\u009c\u009d\7\25\2\2\u009d\u009e\5(\25\2\u009e\u009f\t"+
		"\3\2\2\u009f\u00a0\5\16\b\2\u00a0\u00a1\7\u0083\2\2\u00a1\u0116\3\2\2"+
		"\2\u00a2\u00a3\5&\24\2\u00a3\u00a4\7\7\2\2\u00a4\u00a5\5(\25\2\u00a5\u00ae"+
		"\7\b\2\2\u00a6\u00ab\5\34\17\2\u00a7\u00a8\7\t\2\2\u00a8\u00aa\5\34\17"+
		"\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac"+
		"\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00a6\3\2\2\2\u00ae"+
		"\u00af\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\7\n\2\2\u00b1\u00b2\7\4"+
		"\2\2\u00b2\u00b6\5\20\t\2\u00b3\u00b5\5\6\4\2\u00b4\u00b3\3\2\2\2\u00b5"+
		"\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\3\2"+
		"\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba\7\16\2\2\u00ba\u00bb\5\16\b\2\u00bb"+
		"\u00bc\7\u0083\2\2\u00bc\u0116\3\2\2\2\u00bd\u00be\5&\24\2\u00be\u00bf"+
		"\7\13\2\2\u00bf\u00c0\5(\25\2\u00c0\u00c9\7\b\2\2\u00c1\u00c6\5\34\17"+
		"\2\u00c2\u00c3\7\t\2\2\u00c3\u00c5\5\34\17\2\u00c4\u00c2\3\2\2\2\u00c5"+
		"\u00c8\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00ca\3\2"+
		"\2\2\u00c8\u00c6\3\2\2\2\u00c9\u00c1\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca"+
		"\u00cb\3\2\2\2\u00cb\u00cf\7\n\2\2\u00cc\u00ce\5\6\4\2\u00cd\u00cc\3\2"+
		"\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0"+
		"\u00d2\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d3\t\3\2\2\u00d3\u00d4\5\16"+
		"\b\2\u00d4\u00d5\7\u0083\2\2\u00d5\u0116\3\2\2\2\u00d6\u00d7\5&\24\2\u00d7"+
		"\u00d8\7\f\2\2\u00d8\u00d9\5(\25\2\u00d9\u00e2\7\b\2\2\u00da\u00df\5\34"+
		"\17\2\u00db\u00dc\7\t\2\2\u00dc\u00de\5\34\17\2\u00dd\u00db\3\2\2\2\u00de"+
		"\u00e1\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e3\3\2"+
		"\2\2\u00e1\u00df\3\2\2\2\u00e2\u00da\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3"+
		"\u00e4\3\2\2\2\u00e4\u00e5\7\n\2\2\u00e5\u00e6\7\4\2\2\u00e6\u00ea\5\20"+
		"\t\2\u00e7\u00e9\5\6\4\2\u00e8\u00e7\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea"+
		"\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ed\3\2\2\2\u00ec\u00ea\3\2"+
		"\2\2\u00ed\u00f1\7\26\2\2\u00ee\u00f0\5\n\6\2\u00ef\u00ee\3\2\2\2\u00f0"+
		"\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f8\3\2"+
		"\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f5\7\27\2\2\u00f5\u00f6\5\16\b\2\u00f6"+
		"\u00f7\7\u0083\2\2\u00f7\u00f9\3\2\2\2\u00f8\u00f4\3\2\2\2\u00f8\u00f9"+
		"\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fb\7\30\2\2\u00fb\u0116\3\2\2\2"+
		"\u00fc\u00fd\5&\24\2\u00fd\u00fe\7\25\2\2\u00fe\u00ff\5(\25\2\u00ff\u0108"+
		"\7\b\2\2\u0100\u0105\5\34\17\2\u0101\u0102\7\t\2\2\u0102\u0104\5\34\17"+
		"\2\u0103\u0101\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106"+
		"\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0108\u0100\3\2\2\2\u0108"+
		"\u0109\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010e\7\n\2\2\u010b\u010d\5\6"+
		"\4\2\u010c\u010b\3\2\2\2\u010d\u0110\3\2\2\2\u010e\u010c\3\2\2\2\u010e"+
		"\u010f\3\2\2\2\u010f\u0111\3\2\2\2\u0110\u010e\3\2\2\2\u0111\u0112\t\3"+
		"\2\2\u0112\u0113\5\16\b\2\u0113\u0114\7\u0083\2\2\u0114\u0116\3\2\2\2"+
		"\u0115\64\3\2\2\2\u0115:\3\2\2\2\u0115M\3\2\2\2\u0115^\3\2\2\2\u0115p"+
		"\3\2\2\2\u0115z\3\2\2\2\u0115\u0088\3\2\2\2\u0115\u008c\3\2\2\2\u0115"+
		"\u0096\3\2\2\2\u0115\u009c\3\2\2\2\u0115\u00a2\3\2\2\2\u0115\u00bd\3\2"+
		"\2\2\u0115\u00d6\3\2\2\2\u0115\u00fc\3\2\2\2\u0116\5\3\2\2\2\u0117\u0118"+
		"\7\31\2\2\u0118\u0119\5\16\b\2\u0119\u011a\7\u0083\2\2\u011a\u012d\3\2"+
		"\2\2\u011b\u011c\7\32\2\2\u011c\u011d\5\16\b\2\u011d\u011e\7\u0083\2\2"+
		"\u011e\u012d\3\2\2\2\u011f\u0120\7\33\2\2\u0120\u0125\5\16\b\2\u0121\u0122"+
		"\7\t\2\2\u0122\u0124\5\16\b\2\u0123\u0121\3\2\2\2\u0124\u0127\3\2\2\2"+
		"\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0128\3\2\2\2\u0127\u0125"+
		"\3\2\2\2\u0128\u0129\7\u0083\2\2\u0129\u012d\3\2\2\2\u012a\u012b\7\34"+
		"\2\2\u012b\u012d\7\u0083\2\2\u012c\u0117\3\2\2\2\u012c\u011b\3\2\2\2\u012c"+
		"\u011f\3\2\2\2\u012c\u012a\3\2\2\2\u012d\7\3\2\2\2\u012e\u0170\3\2\2\2"+
		"\u012f\u0133\5(\25\2\u0130\u0132\5$\23\2\u0131\u0130\3\2\2\2\u0132\u0135"+
		"\3\2\2\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0136\3\2\2\2\u0135"+
		"\u0133\3\2\2\2\u0136\u0137\t\4\2\2\u0137\u0138\5\16\b\2\u0138\u0170\3"+
		"\2\2\2\u0139\u013a\7\37\2\2\u013a\u0170\5\22\n\2\u013b\u013f\7 \2\2\u013c"+
		"\u013e\5\f\7\2\u013d\u013c\3\2\2\2\u013e\u0141\3\2\2\2\u013f\u013d\3\2"+
		"\2\2\u013f\u0140\3\2\2\2\u0140\u0142\3\2\2\2\u0141\u013f\3\2\2\2\u0142"+
		"\u0143\5\n\6\2\u0143\u0144\7!\2\2\u0144\u0145\5\16\b\2\u0145\u0170\3\2"+
		"\2\2\u0146\u0147\7\"\2\2\u0147\u0148\5(\25\2\u0148\u0149\7\4\2\2\u0149"+
		"\u014c\5\20\t\2\u014a\u014b\t\4\2\2\u014b\u014d\5\16\b\2\u014c\u014a\3"+
		"\2\2\2\u014c\u014d\3\2\2\2\u014d\u0170\3\2\2\2\u014e\u014f\7\3\2\2\u014f"+
		"\u0152\5(\25\2\u0150\u0151\7\4\2\2\u0151\u0153\5\20\t\2\u0152\u0150\3"+
		"\2\2\2\u0152\u0153\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0155\t\4\2\2\u0155"+
		"\u0156\5\16\b\2\u0156\u0170\3\2\2\2\u0157\u0158\7#\2\2\u0158\u0170\5\16"+
		"\b\2\u0159\u015c\7$\2\2\u015a\u015b\7\u0084\2\2\u015b\u015d\7\t\2\2\u015c"+
		"\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u0163\5\16"+
		"\b\2\u015f\u0160\7\t\2\2\u0160\u0162\5\16\b\2\u0161\u015f\3\2\2\2\u0162"+
		"\u0165\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164\u0170\3\2"+
		"\2\2\u0165\u0163\3\2\2\2\u0166\u0167\7$\2\2\u0167\u0170\7\u0084\2\2\u0168"+
		"\u0169\7%\2\2\u0169\u016c\5(\25\2\u016a\u016b\7\17\2\2\u016b\u016d\5\16"+
		"\b\2\u016c\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u0170\3\2\2\2\u016e"+
		"\u0170\5\16\b\2\u016f\u012e\3\2\2\2\u016f\u012f\3\2\2\2\u016f\u0139\3"+
		"\2\2\2\u016f\u013b\3\2\2\2\u016f\u0146\3\2\2\2\u016f\u014e\3\2\2\2\u016f"+
		"\u0157\3\2\2\2\u016f\u0159\3\2\2\2\u016f\u0166\3\2\2\2\u016f\u0168\3\2"+
		"\2\2\u016f\u016e\3\2\2\2\u0170\t\3\2\2\2\u0171\u0172\5\b\5\2\u0172\u0173"+
		"\7\u0083\2\2\u0173\u01cb\3\2\2\2\u0174\u0175\7\37\2\2\u0175\u0176\5\22"+
		"\n\2\u0176\u0177\7&\2\2\u0177\u0178\5\n\6\2\u0178\u0179\7\'\2\2\u0179"+
		"\u017a\5\n\6\2\u017a\u01cb\3\2\2\2\u017b\u017c\7(\2\2\u017c\u017d\5\16"+
		"\b\2\u017d\u017e\7&\2\2\u017e\u017f\5\n\6\2\u017f\u01cb\3\2\2\2\u0180"+
		"\u0181\7(\2\2\u0181\u0182\5\16\b\2\u0182\u0183\7&\2\2\u0183\u0184\5\n"+
		"\6\2\u0184\u0185\7\'\2\2\u0185\u0186\5\n\6\2\u0186\u01cb\3\2\2\2\u0187"+
		"\u0188\7)\2\2\u0188\u0189\5\16\b\2\u0189\u018a\7\17\2\2\u018a\u018c\7"+
		"\26\2\2\u018b\u018d\5\30\r\2\u018c\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e"+
		"\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u0190\3\2\2\2\u0190\u0191\7\30"+
		"\2\2\u0191\u01cb\3\2\2\2\u0192\u0193\7!\2\2\u0193\u0194\5\16\b\2\u0194"+
		"\u0198\7 \2\2\u0195\u0197\5\f\7\2\u0196\u0195\3\2\2\2\u0197\u019a\3\2"+
		"\2\2\u0198\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019b\3\2\2\2\u019a"+
		"\u0198\3\2\2\2\u019b\u019c\5\n\6\2\u019c\u01cb\3\2\2\2\u019d\u019e\7*"+
		"\2\2\u019e\u019f\5\b\5\2\u019f\u01a0\7\u0083\2\2\u01a0\u01a1\5\16\b\2"+
		"\u01a1\u01a2\7\u0083\2\2\u01a2\u01a3\5\b\5\2\u01a3\u01a7\7 \2\2\u01a4"+
		"\u01a6\5\f\7\2\u01a5\u01a4\3\2\2\2\u01a6\u01a9\3\2\2\2\u01a7\u01a5\3\2"+
		"\2\2\u01a7\u01a8\3\2\2\2\u01a8\u01aa\3\2\2\2\u01a9\u01a7\3\2\2\2\u01aa"+
		"\u01ab\5\n\6\2\u01ab\u01cb\3\2\2\2\u01ac\u01ad\7*\2\2\u01ad\u01ae\5\22"+
		"\n\2\u01ae\u01b2\7 \2\2\u01af\u01b1\5\f\7\2\u01b0\u01af\3\2\2\2\u01b1"+
		"\u01b4\3\2\2\2\u01b2\u01b0\3\2\2\2\u01b2\u01b3\3\2\2\2\u01b3\u01b5\3\2"+
		"\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01b6\5\n\6\2\u01b6\u01cb\3\2\2\2\u01b7"+
		"\u01b8\7\37\2\2\u01b8\u01b9\5\22\n\2\u01b9\u01bd\7 \2\2\u01ba\u01bc\5"+
		"\f\7\2\u01bb\u01ba\3\2\2\2\u01bc\u01bf\3\2\2\2\u01bd\u01bb\3\2\2\2\u01bd"+
		"\u01be\3\2\2\2\u01be\u01c0\3\2\2\2\u01bf\u01bd\3\2\2\2\u01c0\u01c1\5\n"+
		"\6\2\u01c1\u01cb\3\2\2\2\u01c2\u01c6\7\26\2\2\u01c3\u01c5\5\n\6\2\u01c4"+
		"\u01c3\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2"+
		"\2\2\u01c7\u01c9\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01cb\7\30\2\2\u01ca"+
		"\u0171\3\2\2\2\u01ca\u0174\3\2\2\2\u01ca\u017b\3\2\2\2\u01ca\u0180\3\2"+
		"\2\2\u01ca\u0187\3\2\2\2\u01ca\u0192\3\2\2\2\u01ca\u019d\3\2\2\2\u01ca"+
		"\u01ac\3\2\2\2\u01ca\u01b7\3\2\2\2\u01ca\u01c2\3\2\2\2\u01cb\13\3\2\2"+
		"\2\u01cc\u01cd\7+\2\2\u01cd\u01ce\5\16\b\2\u01ce\u01cf\7\u0083\2\2\u01cf"+
		"\u01dc\3\2\2\2\u01d0\u01d1\7\33\2\2\u01d1\u01d6\5\16\b\2\u01d2\u01d3\7"+
		"\t\2\2\u01d3\u01d5\5\16\b\2\u01d4\u01d2\3\2\2\2\u01d5\u01d8\3\2\2\2\u01d6"+
		"\u01d4\3\2\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d9\3\2\2\2\u01d8\u01d6\3\2"+
		"\2\2\u01d9\u01da\7\u0083\2\2\u01da\u01dc\3\2\2\2\u01db\u01cc\3\2\2\2\u01db"+
		"\u01d0\3\2\2\2\u01dc\r\3\2\2\2\u01dd\u01de\b\b\1\2\u01de\u01df\7\b\2\2"+
		"\u01df\u02f3\7\n\2\2\u01e0\u02f3\t\5\2\2\u01e1\u02f3\t\6\2\2\u01e2\u02f3"+
		"\5*\26\2\u01e3\u01e7\7\60\2\2\u01e4\u01e5\7\26\2\2\u01e5\u01e7\7\30\2"+
		"\2\u01e6\u01e3\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e9"+
		"\7\61\2\2\u01e9\u01ea\5\20\t\2\u01ea\u01eb\7\62\2\2\u01eb\u02f3\3\2\2"+
		"\2\u01ec\u01ed\5(\25\2\u01ed\u01ee\7\63\2\2\u01ee\u01ef\5(\25\2\u01ef"+
		"\u02f3\3\2\2\2\u01f0\u01f1\5(\25\2\u01f1\u01f2\7\63\2\2\u01f2\u01f3\5"+
		"(\25\2\u01f3\u01f4\7\b\2\2\u01f4\u01f9\5\16\b\2\u01f5\u01f6\7\t\2\2\u01f6"+
		"\u01f8\5\16\b\2\u01f7\u01f5\3\2\2\2\u01f8\u01fb\3\2\2\2\u01f9\u01f7\3"+
		"\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fc\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fc"+
		"\u01fd\7\n\2\2\u01fd\u02f3\3\2\2\2\u01fe\u02f3\5(\25\2\u01ff\u0200\5("+
		"\25\2\u0200\u0209\7\b\2\2\u0201\u0206\5\16\b\2\u0202\u0203\7\t\2\2\u0203"+
		"\u0205\5\16\b\2\u0204\u0202\3\2\2\2\u0205\u0208\3\2\2\2\u0206\u0204\3"+
		"\2\2\2\u0206\u0207\3\2\2\2\u0207\u020a\3\2\2\2\u0208\u0206\3\2\2\2\u0209"+
		"\u0201\3\2\2\2\u0209\u020a\3\2\2\2\u020a\u020b\3\2\2\2\u020b\u020c\7\n"+
		"\2\2\u020c\u02f3\3\2\2\2\u020d\u020e\7\64\2\2\u020e\u020f\7\61\2\2\u020f"+
		"\u0210\5\16\b\2\u0210\u0211\7\t\2\2\u0211\u0212\5\20\t\2\u0212\u0213\7"+
		"\62\2\2\u0213\u0214\7\b\2\2\u0214\u0215\5\16\b\2\u0215\u0216\7\n\2\2\u0216"+
		"\u02f3\3\2\2\2\u0217\u0218\7\65\2\2\u0218\u0219\7\61\2\2\u0219\u021a\5"+
		"\20\t\2\u021a\u021b\7\t\2\2\u021b\u021c\5\20\t\2\u021c\u021d\7\62\2\2"+
		"\u021d\u021e\7\b\2\2\u021e\u021f\5\16\b\2\u021f\u0220\7\n\2\2\u0220\u02f3"+
		"\3\2\2\2\u0221\u0222\7\66\2\2\u0222\u0223\7\b\2\2\u0223\u0224\5\16\b\2"+
		"\u0224\u0225\7\n\2\2\u0225\u02f3\3\2\2\2\u0226\u0227\7\66\2\2\u0227\u0228"+
		"\7\b\2\2\u0228\u0229\5\16\b\2\u0229\u022a\7\t\2\2\u022a\u022b\5\16\b\2"+
		"\u022b\u022c\7\n\2\2\u022c\u02f3\3\2\2\2\u022d\u022e\7\66\2\2\u022e\u022f"+
		"\7\b\2\2\u022f\u0230\5\16\b\2\u0230\u0231\7\t\2\2\u0231\u0232\5\16\b\2"+
		"\u0232\u0233\7\t\2\2\u0233\u0234\5\16\b\2\u0234\u0235\7\n\2\2\u0235\u02f3"+
		"\3\2\2\2\u0236\u0237\78\2\2\u0237\u02f3\5\16\b;\u0238\u0239\t\7\2\2\u0239"+
		"\u02f3\5\16\b:\u023a\u023b\t\b\2\2\u023b\u02f3\5\16\b9\u023c\u023d\7\26"+
		"\2\2\u023d\u0242\5\16\b\2\u023e\u023f\7\t\2\2\u023f\u0241\5\16\b\2\u0240"+
		"\u023e\3\2\2\2\u0241\u0244\3\2\2\2\u0242\u0240\3\2\2\2\u0242\u0243\3\2"+
		"\2\2\u0243\u0245\3\2\2\2\u0244\u0242\3\2\2\2\u0245\u0246\7\30\2\2\u0246"+
		"\u02f3\3\2\2\2\u0247\u0248\t\t\2\2\u0248\u024d\5\16\b\2\u0249\u024a\7"+
		"\t\2\2\u024a\u024c\5\16\b\2\u024b\u0249\3\2\2\2\u024c\u024f\3\2\2\2\u024d"+
		"\u024b\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u0250\3\2\2\2\u024f\u024d\3\2"+
		"\2\2\u0250\u0251\t\n\2\2\u0251\u02f3\3\2\2\2\u0252\u0253\t\t\2\2\u0253"+
		"\u0258\5 \21\2\u0254\u0255\7\t\2\2\u0255\u0257\5 \21\2\u0256\u0254\3\2"+
		"\2\2\u0257\u025a\3\2\2\2\u0258\u0256\3\2\2\2\u0258\u0259\3\2\2\2\u0259"+
		"\u025b\3\2\2\2\u025a\u0258\3\2\2\2\u025b\u025c\t\n\2\2\u025c\u02f3\3\2"+
		"\2\2\u025d\u025e\7L\2\2\u025e\u025f\5\16\b\2\u025f\u0260\7L\2\2\u0260"+
		"\u02f3\3\2\2\2\u0261\u0262\7\b\2\2\u0262\u0265\5\16\b\2\u0263\u0264\t"+
		"\13\2\2\u0264\u0266\5\16\b\2\u0265\u0263\3\2\2\2\u0266\u0267\3\2\2\2\u0267"+
		"\u0265\3\2\2\2\u0267\u0268\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026a\7\n"+
		"\2\2\u026a\u02f3\3\2\2\2\u026b\u026c\7T\2\2\u026c\u02f3\5\22\n\2\u026d"+
		"\u026e\7\26\2\2\u026e\u026f\5\16\b\2\u026f\u0270\7L\2\2\u0270\u0271\5"+
		"\22\n\2\u0271\u0272\7\30\2\2\u0272\u02f3\3\2\2\2\u0273\u0274\7\37\2\2"+
		"\u0274\u02f3\5\22\n\2\u0275\u0276\t\f\2\2\u0276\u0277\5\22\n\2\u0277\u0278"+
		"\7\67\2\2\u0278\u0279\5\16\b#\u0279\u02f3\3\2\2\2\u027a\u027b\t\r\2\2"+
		"\u027b\u027c\5\22\n\2\u027c\u027d\7\67\2\2\u027d\u027e\5\16\b\"\u027e"+
		"\u02f3\3\2\2\2\u027f\u0280\7[\2\2\u0280\u0281\5\22\n\2\u0281\u0282\7\67"+
		"\2\2\u0282\u0283\5\16\b!\u0283\u02f3\3\2\2\2\u0284\u0285\7\\\2\2\u0285"+
		"\u0286\5\22\n\2\u0286\u0287\7\67\2\2\u0287\u0288\5\16\b \u0288\u02f3\3"+
		"\2\2\2\u0289\u028a\t\16\2\2\u028a\u02f3\5\16\b\24\u028b\u028c\7(\2\2\u028c"+
		"\u028d\5\16\b\2\u028d\u028e\7&\2\2\u028e\u028f\5\16\b\2\u028f\u0290\7"+
		"\'\2\2\u0290\u0291\5\16\b\2\u0291\u02f3\3\2\2\2\u0292\u0293\7)\2\2\u0293"+
		"\u0294\5\16\b\2\u0294\u0295\7\17\2\2\u0295\u0299\7\26\2\2\u0296\u0297"+
		"\5\32\16\2\u0297\u0298\7\u0083\2\2\u0298\u029a\3\2\2\2\u0299\u0296\3\2"+
		"\2\2\u029a\u029b\3\2\2\2\u029b\u0299\3\2\2\2\u029b\u029c\3\2\2\2\u029c"+
		"\u029d\3\2\2\2\u029d\u029e\7\30\2\2\u029e\u02f3\3\2\2\2\u029f\u02a0\t"+
		"\17\2\2\u02a0\u02a1\5\22\n\2\u02a1\u02a2\7\67\2\2\u02a2\u02a3\5\16\b\r"+
		"\u02a3\u02f3\3\2\2\2\u02a4\u02a5\t\20\2\2\u02a5\u02a6\5\22\n\2\u02a6\u02a7"+
		"\7\67\2\2\u02a7\u02a8\5\16\b\f\u02a8\u02f3\3\2\2\2\u02a9\u02aa\7u\2\2"+
		"\u02aa\u02af\5\26\f\2\u02ab\u02ac\7\t\2\2\u02ac\u02ae\5\26\f\2\u02ad\u02ab"+
		"\3\2\2\2\u02ae\u02b1\3\2\2\2\u02af\u02ad\3\2\2\2\u02af\u02b0\3\2\2\2\u02b0"+
		"\u02b2\3\2\2\2\u02b1\u02af\3\2\2\2\u02b2\u02b3\7v\2\2\u02b3\u02b4\5\16"+
		"\b\13\u02b4\u02f3\3\2\2\2\u02b5\u02b6\7w\2\2\u02b6\u02bb\5\26\f\2\u02b7"+
		"\u02b8\7\t\2\2\u02b8\u02ba\5\26\f\2\u02b9\u02b7\3\2\2\2\u02ba\u02bd\3"+
		"\2\2\2\u02bb\u02b9\3\2\2\2\u02bb\u02bc\3\2\2\2\u02bc\u02be\3\2\2\2\u02bd"+
		"\u02bb\3\2\2\2\u02be\u02bf\7v\2\2\u02bf\u02c0\5\16\b\n\u02c0\u02f3\3\2"+
		"\2\2\u02c1\u02c2\7\37\2\2\u02c2\u02c3\5\22\n\2\u02c3\u02c4\7v\2\2\u02c4"+
		"\u02c5\5\16\b\t\u02c5\u02f3\3\2\2\2\u02c6\u02c7\7\37\2\2\u02c7\u02c8\5"+
		"\22\n\2\u02c8\u02c9\7v\2\2\u02c9\u02ca\5\16\b\2\u02ca\u02cb\7\'\2\2\u02cb"+
		"\u02cc\5\16\b\2\u02cc\u02f3\3\2\2\2\u02cd\u02ce\7#\2\2\u02ce\u02cf\5\16"+
		"\b\2\u02cf\u02d0\7v\2\2\u02d0\u02d1\5\16\b\2\u02d1\u02f3\3\2\2\2\u02d2"+
		"\u02d5\7$\2\2\u02d3\u02d4\7\u0084\2\2\u02d4\u02d6\7\t\2\2\u02d5\u02d3"+
		"\3\2\2\2\u02d5\u02d6\3\2\2\2\u02d6\u02d7\3\2\2\2\u02d7\u02f3\5\16\b\6"+
		"\u02d8\u02db\7$\2\2\u02d9\u02da\7\u0084\2\2\u02da\u02dc\7\t\2\2\u02db"+
		"\u02d9\3\2\2\2\u02db\u02dc\3\2\2\2\u02dc\u02dd\3\2\2\2\u02dd\u02e2\5\16"+
		"\b\2\u02de\u02df\7\t\2\2\u02df\u02e1\5\16\b\2\u02e0\u02de\3\2\2\2\u02e1"+
		"\u02e4\3\2\2\2\u02e2\u02e0\3\2\2\2\u02e2\u02e3\3\2\2\2\u02e3\u02e5\3\2"+
		"\2\2\u02e4\u02e2\3\2\2\2\u02e5\u02e6\7v\2\2\u02e6\u02e7\5\16\b\2\u02e7"+
		"\u02f3\3\2\2\2\u02e8\u02e9\7%\2\2\u02e9\u02ec\5(\25\2\u02ea\u02eb\7\17"+
		"\2\2\u02eb\u02ed\5\16\b\2\u02ec\u02ea\3\2\2\2\u02ec\u02ed\3\2\2\2\u02ed"+
		"\u02f3\3\2\2\2\u02ee\u02ef\7\b\2\2\u02ef\u02f0\5\16\b\2\u02f0\u02f1\7"+
		"\n\2\2\u02f1\u02f3\3\2\2\2\u02f2\u01dd\3\2\2\2\u02f2\u01e0\3\2\2\2\u02f2"+
		"\u01e1\3\2\2\2\u02f2\u01e2\3\2\2\2\u02f2\u01e6\3\2\2\2\u02f2\u01ec\3\2"+
		"\2\2\u02f2\u01f0\3\2\2\2\u02f2\u01fe\3\2\2\2\u02f2\u01ff\3\2\2\2\u02f2"+
		"\u020d\3\2\2\2\u02f2\u0217\3\2\2\2\u02f2\u0221\3\2\2\2\u02f2\u0226\3\2"+
		"\2\2\u02f2\u022d\3\2\2\2\u02f2\u0236\3\2\2\2\u02f2\u0238\3\2\2\2\u02f2"+
		"\u023a\3\2\2\2\u02f2\u023c\3\2\2\2\u02f2\u0247\3\2\2\2\u02f2\u0252\3\2"+
		"\2\2\u02f2\u025d\3\2\2\2\u02f2\u0261\3\2\2\2\u02f2\u026b\3\2\2\2\u02f2"+
		"\u026d\3\2\2\2\u02f2\u0273\3\2\2\2\u02f2\u0275\3\2\2\2\u02f2\u027a\3\2"+
		"\2\2\u02f2\u027f\3\2\2\2\u02f2\u0284\3\2\2\2\u02f2\u0289\3\2\2\2\u02f2"+
		"\u028b\3\2\2\2\u02f2\u0292\3\2\2\2\u02f2\u029f\3\2\2\2\u02f2\u02a4\3\2"+
		"\2\2\u02f2\u02a9\3\2\2\2\u02f2\u02b5\3\2\2\2\u02f2\u02c1\3\2\2\2\u02f2"+
		"\u02c6\3\2\2\2\u02f2\u02cd\3\2\2\2\u02f2\u02d2\3\2\2\2\u02f2\u02d8\3\2"+
		"\2\2\u02f2\u02e8\3\2\2\2\u02f2\u02ee\3\2\2\2\u02f3\u0370\3\2\2\2\u02f4"+
		"\u02f5\f8\2\2\u02f5\u02f6\7=\2\2\u02f6\u036f\5\16\b9\u02f7\u02f8\f\67"+
		"\2\2\u02f8\u02f9\t\21\2\2\u02f9\u036f\5\16\b8\u02fa\u02fb\f\66\2\2\u02fb"+
		"\u02fc\t\21\2\2\u02fc\u02fd\7@\2\2\u02fd\u02fe\t\21\2\2\u02fe\u036f\5"+
		"\16\b\67\u02ff\u0300\f\65\2\2\u0300\u0301\7A\2\2\u0301\u036f\5\16\b\66"+
		"\u0302\u0303\f\64\2\2\u0303\u0304\7B\2\2\u0304\u036f\5\16\b\65\u0305\u0306"+
		"\f\63\2\2\u0306\u0307\78\2\2\u0307\u036f\5\16\b\64\u0308\u0309\f\62\2"+
		"\2\u0309\u030a\7C\2\2\u030a\u036f\5\16\b\63\u030b\u030c\f\61\2\2\u030c"+
		"\u030d\7C\2\2\u030d\u030e\7@\2\2\u030e\u030f\7C\2\2\u030f\u036f\5\16\b"+
		"\62\u0310\u0311\f/\2\2\u0311\u0312\7@\2\2\u0312\u036f\5\16\b\60\u0313"+
		"\u0314\f+\2\2\u0314\u0315\t\22\2\2\u0315\u036f\5\16\b,\u0316\u0317\f*"+
		"\2\2\u0317\u0318\t\23\2\2\u0318\u036f\5\16\b+\u0319\u031a\f)\2\2\u031a"+
		"\u031b\7Q\2\2\u031b\u036f\5\16\b*\u031c\u031d\f\37\2\2\u031d\u031e\7\17"+
		"\2\2\u031e\u031f\7\61\2\2\u031f\u0320\5\16\b\2\u0320\u0321\7\62\2\2\u0321"+
		"\u0322\7\16\2\2\u0322\u0323\5\16\b \u0323\u036f\3\2\2\2\u0324\u0325\f"+
		"\36\2\2\u0325\u0326\7\17\2\2\u0326\u0327\7\67\2\2\u0327\u0328\5*\26\2"+
		"\u0328\u0329\7\16\2\2\u0329\u032a\5\16\b\37\u032a\u036f\3\2\2\2\u032b"+
		"\u032c\f\35\2\2\u032c\u032d\7\17\2\2\u032d\u032e\7\67\2\2\u032e\u032f"+
		"\5(\25\2\u032f\u0330\7\16\2\2\u0330\u0331\5\16\b\36\u0331\u036f\3\2\2"+
		"\2\u0332\u0333\f\34\2\2\u0333\u0334\7\16\2\2\u0334\u036f\5\16\b\35\u0335"+
		"\u0336\f\33\2\2\u0336\u0337\t\24\2\2\u0337\u036f\5\16\b\34\u0338\u0339"+
		"\f\32\2\2\u0339\u033a\7_\2\2\u033a\u036f\5\16\b\33\u033b\u033c\f\31\2"+
		"\2\u033c\u033d\t\25\2\2\u033d\u036f\5\16\b\32\u033e\u033f\f\30\2\2\u033f"+
		"\u0340\7b\2\2\u0340\u036f\5\16\b\31\u0341\u0342\f\27\2\2\u0342\u0343\t"+
		"\26\2\2\u0343\u036f\5\16\b\30\u0344\u0345\f\26\2\2\u0345\u0346\t\27\2"+
		"\2\u0346\u036f\5\16\b\27\u0347\u0348\f\25\2\2\u0348\u0349\t\30\2\2\u0349"+
		"\u036f\5\16\b\26\u034a\u034b\f\23\2\2\u034b\u034c\t\31\2\2\u034c\u036f"+
		"\5\16\b\24\u034d\u034e\f\22\2\2\u034e\u034f\t\32\2\2\u034f\u036f\5\16"+
		"\b\23\u0350\u0351\f\21\2\2\u0351\u0352\t\33\2\2\u0352\u036f\5\16\b\22"+
		"\u0353\u0354\f\20\2\2\u0354\u0355\t\3\2\2\u0355\u036f\5\16\b\21\u0356"+
		"\u0357\f?\2\2\u0357\u0358\7\61\2\2\u0358\u0359\5\16\b\2\u0359\u035a\7"+
		"\62\2\2\u035a\u036f\3\2\2\2\u035b\u035c\f>\2\2\u035c\u035d\7\67\2\2\u035d"+
		"\u036f\5*\26\2\u035e\u035f\f=\2\2\u035f\u0360\7\67\2\2\u0360\u036f\5("+
		"\25\2\u0361\u0362\f<\2\2\u0362\u036f\7\63\2\2\u0363\u036a\f\'\2\2\u0364"+
		"\u0365\6\b\"\2\u0365\u0366\t\13\2\2\u0366\u0367\b\b\1\2\u0367\u0368\5"+
		"\16\b\2\u0368\u0369\b\b\1\2\u0369\u036b\3\2\2\2\u036a\u0364\3\2\2\2\u036b"+
		"\u036c\3\2\2\2\u036c\u036a\3\2\2\2\u036c\u036d\3\2\2\2\u036d\u036f\3\2"+
		"\2\2\u036e\u02f4\3\2\2\2\u036e\u02f7\3\2\2\2\u036e\u02fa\3\2\2\2\u036e"+
		"\u02ff\3\2\2\2\u036e\u0302\3\2\2\2\u036e\u0305\3\2\2\2\u036e\u0308\3\2"+
		"\2\2\u036e\u030b\3\2\2\2\u036e\u0310\3\2\2\2\u036e\u0313\3\2\2\2\u036e"+
		"\u0316\3\2\2\2\u036e\u0319\3\2\2\2\u036e\u031c\3\2\2\2\u036e\u0324\3\2"+
		"\2\2\u036e\u032b\3\2\2\2\u036e\u0332\3\2\2\2\u036e\u0335\3\2\2\2\u036e"+
		"\u0338\3\2\2\2\u036e\u033b\3\2\2\2\u036e\u033e\3\2\2\2\u036e\u0341\3\2"+
		"\2\2\u036e\u0344\3\2\2\2\u036e\u0347\3\2\2\2\u036e\u034a\3\2\2\2\u036e"+
		"\u034d\3\2\2\2\u036e\u0350\3\2\2\2\u036e\u0353\3\2\2\2\u036e\u0356\3\2"+
		"\2\2\u036e\u035b\3\2\2\2\u036e\u035e\3\2\2\2\u036e\u0361\3\2\2\2\u036e"+
		"\u0363\3\2\2\2\u036f\u0372\3\2\2\2\u0370\u036e\3\2\2\2\u0370\u0371\3\2"+
		"\2\2\u0371\17\3\2\2\2\u0372\u0370\3\2\2\2\u0373\u0377\7x\2\2\u0374\u0375"+
		"\7\b\2\2\u0375\u0377\7\n\2\2\u0376\u0373\3\2\2\2\u0376\u0374\3\2\2\2\u0377"+
		"\u03b2\3\2\2\2\u0378\u03b2\7y\2\2\u0379\u037a\t\34\2\2\u037a\u037b\7\61"+
		"\2\2\u037b\u037c\5\16\b\2\u037c\u037d\7\t\2\2\u037d\u037e\5\16\b\2\u037e"+
		"\u037f\7\62\2\2\u037f\u03b2\3\2\2\2\u0380\u0381\7\65\2\2\u0381\u0382\7"+
		"\61\2\2\u0382\u0383\5\20\t\2\u0383\u0384\7\t\2\2\u0384\u0385\5\20\t\2"+
		"\u0385\u0386\7\62\2\2\u0386\u03b2\3\2\2\2\u0387\u0388\7|\2\2\u0388\u0389"+
		"\7\61\2\2\u0389\u038e\5\20\t\2\u038a\u038b\7\t\2\2\u038b\u038d\5\20\t"+
		"\2\u038c\u038a\3\2\2\2\u038d\u0390\3\2\2\2\u038e\u038c\3\2\2\2\u038e\u038f"+
		"\3\2\2\2\u038f\u0391\3\2\2\2\u0390\u038e\3\2\2\2\u0391\u0392\7\62\2\2"+
		"\u0392\u03b2\3\2\2\2\u0393\u0394\7}\2\2\u0394\u0395\7\61\2\2\u0395\u039a"+
		"\5\34\17\2\u0396\u0397\7\t\2\2\u0397\u0399\5\34\17\2\u0398\u0396\3\2\2"+
		"\2\u0399\u039c\3\2\2\2\u039a\u0398\3\2\2\2\u039a\u039b\3\2\2\2\u039b\u039d"+
		"\3\2\2\2\u039c\u039a\3\2\2\2\u039d\u039e\7\62\2\2\u039e\u03b2\3\2\2\2"+
		"\u039f\u03a0\t\2\2\2\u03a0\u03a1\7\61\2\2\u03a1\u03a2\5\16\b\2\u03a2\u03a3"+
		"\7\62\2\2\u03a3\u03b2\3\2\2\2\u03a4\u03a5\7\66\2\2\u03a5\u03a6\7\61\2"+
		"\2\u03a6\u03a7\5\20\t\2\u03a7\u03a8\7\62\2\2\u03a8\u03b2\3\2\2\2\u03a9"+
		"\u03aa\7\64\2\2\u03aa\u03ab\7\61\2\2\u03ab\u03ac\5\16\b\2\u03ac\u03ad"+
		"\7\t\2\2\u03ad\u03ae\5\20\t\2\u03ae\u03af\7\62\2\2\u03af\u03b2\3\2\2\2"+
		"\u03b0\u03b2\5(\25\2\u03b1\u0376\3\2\2\2\u03b1\u0378\3\2\2\2\u03b1\u0379"+
		"\3\2\2\2\u03b1\u0380\3\2\2\2\u03b1\u0387\3\2\2\2\u03b1\u0393\3\2\2\2\u03b1"+
		"\u039f\3\2\2\2\u03b1\u03a4\3\2\2\2\u03b1\u03a9\3\2\2\2\u03b1\u03b0\3\2"+
		"\2\2\u03b2\21\3\2\2\2\u03b3\u03b8\5\24\13\2\u03b4\u03b5\7\t\2\2\u03b5"+
		"\u03b7\5\24\13\2\u03b6\u03b4\3\2\2\2\u03b7\u03ba\3\2\2\2\u03b8\u03b6\3"+
		"\2\2\2\u03b8\u03b9\3\2\2\2\u03b9\u03bd\3\2\2\2\u03ba\u03b8\3\2\2\2\u03bb"+
		"\u03bc\7\17\2\2\u03bc\u03be\5\16\b\2\u03bd\u03bb\3\2\2\2\u03bd\u03be\3"+
		"\2\2\2\u03be\23\3\2\2\2\u03bf\u03c0\5(\25\2\u03c0\u03c1\7\4\2\2\u03c1"+
		"\u03c2\5\20\t\2\u03c2\u03c8\3\2\2\2\u03c3\u03c4\5(\25\2\u03c4\u03c5\t"+
		"\35\2\2\u03c5\u03c6\5\16\b\2\u03c6\u03c8\3\2\2\2\u03c7\u03bf\3\2\2\2\u03c7"+
		"\u03c3\3\2\2\2\u03c8\25\3\2\2\2\u03c9\u03ca\5(\25\2\u03ca\u03cb\7\16\2"+
		"\2\u03cb\u03cc\5\16\b\2\u03cc\27\3\2\2\2\u03cd\u03ce\5(\25\2\u03ce\u03cf"+
		"\7~\2\2\u03cf\u03d0\5\n\6\2\u03d0\u03e3\3\2\2\2\u03d1\u03d2\5(\25\2\u03d2"+
		"\u03d3\7\b\2\2\u03d3\u03d8\5\34\17\2\u03d4\u03d5\7\t\2\2\u03d5\u03d7\5"+
		"\34\17\2\u03d6\u03d4\3\2\2\2\u03d7\u03da\3\2\2\2\u03d8\u03d6\3\2\2\2\u03d8"+
		"\u03d9\3\2\2\2\u03d9\u03db\3\2\2\2\u03da\u03d8\3\2\2\2\u03db\u03dc\7\n"+
		"\2\2\u03dc\u03dd\7~\2\2\u03dd\u03de\5\n\6\2\u03de\u03e3\3\2\2\2\u03df"+
		"\u03e0\7\177\2\2\u03e0\u03e1\7~\2\2\u03e1\u03e3\5\n\6\2\u03e2\u03cd\3"+
		"\2\2\2\u03e2\u03d1\3\2\2\2\u03e2\u03df\3\2\2\2\u03e3\31\3\2\2\2\u03e4"+
		"\u03e5\5(\25\2\u03e5\u03e6\7~\2\2\u03e6\u03e7\5\16\b\2\u03e7\u03fa\3\2"+
		"\2\2\u03e8\u03e9\5(\25\2\u03e9\u03ea\7\b\2\2\u03ea\u03ef\5\34\17\2\u03eb"+
		"\u03ec\7\t\2\2\u03ec\u03ee\5\34\17\2\u03ed\u03eb\3\2\2\2\u03ee\u03f1\3"+
		"\2\2\2\u03ef\u03ed\3\2\2\2\u03ef\u03f0\3\2\2\2\u03f0\u03f2\3\2\2\2\u03f1"+
		"\u03ef\3\2\2\2\u03f2\u03f3\7\n\2\2\u03f3\u03f4\7~\2\2\u03f4\u03f5\5\16"+
		"\b\2\u03f5\u03fa\3\2\2\2\u03f6\u03f7\7\177\2\2\u03f7\u03f8\7~\2\2\u03f8"+
		"\u03fa\5\16\b\2\u03f9\u03e4\3\2\2\2\u03f9\u03e8\3\2\2\2\u03f9\u03f6\3"+
		"\2\2\2\u03fa\33\3\2\2\2\u03fb\u03fc\5(\25\2\u03fc\u03fd\7\4\2\2\u03fd"+
		"\u03fe\5\20\t\2\u03fe\35\3\2\2\2\u03ff\u0400\5(\25\2\u0400\u0401\7\16"+
		"\2\2\u0401\u0406\5\"\22\2\u0402\u0403\7L\2\2\u0403\u0405\5\"\22\2\u0404"+
		"\u0402\3\2\2\2\u0405\u0408\3\2\2\2\u0406\u0404\3\2\2\2\u0406\u0407\3\2"+
		"\2\2\u0407\37\3\2\2\2\u0408\u0406\3\2\2\2\u0409\u040a\5(\25\2\u040a\u040b"+
		"\7\4\2\2\u040b\u040c\5\16\b\2\u040c!\3\2\2\2\u040d\u041b\5(\25\2\u040e"+
		"\u040f\5(\25\2\u040f\u0410\7\b\2\2\u0410\u0415\5\20\t\2\u0411\u0412\7"+
		"\t\2\2\u0412\u0414\5\20\t\2\u0413\u0411\3\2\2\2\u0414\u0417\3\2\2\2\u0415"+
		"\u0413\3\2\2\2\u0415\u0416\3\2\2\2\u0416\u0418\3\2\2\2\u0417\u0415\3\2"+
		"\2\2\u0418\u0419\7\n\2\2\u0419\u041b\3\2\2\2\u041a\u040d\3\2\2\2\u041a"+
		"\u040e\3\2\2\2\u041b#\3\2\2\2\u041c\u041d\7\61\2\2\u041d\u041e\5\16\b"+
		"\2\u041e\u041f\7\62\2\2\u041f\u0425\3\2\2\2\u0420\u0421\7\67\2\2\u0421"+
		"\u0425\5*\26\2\u0422\u0423\7\67\2\2\u0423\u0425\5(\25\2\u0424\u041c\3"+
		"\2\2\2\u0424\u0420\3\2\2\2\u0424\u0422\3\2\2\2\u0425%\3\2\2\2\u0426\u0429"+
		"\3\2\2\2\u0427\u0429\7\u0080\2\2\u0428\u0426\3\2\2\2\u0428\u0427\3\2\2"+
		"\2\u0429\'\3\2\2\2\u042a\u042b\7\u0081\2\2\u042b)\3\2\2\2\u042c\u042d"+
		"\7\u0082\2\2\u042d+\3\2\2\2Q/CFVYgjv\u0083\u0090\u00ab\u00ae\u00b6\u00c6"+
		"\u00c9\u00cf\u00df\u00e2\u00ea\u00f1\u00f8\u0105\u0108\u010e\u0115\u0125"+
		"\u012c\u0133\u013f\u014c\u0152\u015c\u0163\u016c\u016f\u018e\u0198\u01a7"+
		"\u01b2\u01bd\u01c6\u01ca\u01d6\u01db\u01e6\u01f9\u0206\u0209\u0242\u024d"+
		"\u0258\u0267\u029b\u02af\u02bb\u02d5\u02db\u02e2\u02ec\u02f2\u036c\u036e"+
		"\u0370\u0376\u038e\u039a\u03b1\u03b8\u03bd\u03c7\u03d8\u03e2\u03ef\u03f9"+
		"\u0406\u0415\u041a\u0424\u0428";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}