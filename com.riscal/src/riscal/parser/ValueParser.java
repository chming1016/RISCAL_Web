// Generated from Value.g4 by ANTLR 4.5.3

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
public class ValueParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, DECIMAL=15, WHITESPACE=16, 
		ERROR=17;
	public static final int
		RULE_valueFile = 0, RULE_value = 1;
	public static final String[] ruleNames = {
		"valueFile", "value"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'true'", "'false'", "'-'", "'{'", "','", "'}'", "'<'", 
		"'->'", "'>'", "':'", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "DECIMAL", "WHITESPACE", "ERROR"
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
	public String getGrammarFileName() { return "Value.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ValueParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ValueFileContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ValueParser.EOF, 0); }
		public ValueFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterValueFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitValueFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitValueFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueFileContext valueFile() throws RecognitionException {
		ValueFileContext _localctx = new ValueFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_valueFile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4);
			value();
			setState(5);
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

	public static class ValueContext extends ParserRuleContext {
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	 
		public ValueContext() { }
		public void copyFrom(ValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArrayContext extends ValueContext {
		public TerminalNode DECIMAL() { return getToken(ValueParser.DECIMAL, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ArrayContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DecimalNumberContext extends ValueContext {
		public TerminalNode DECIMAL() { return getToken(ValueParser.DECIMAL, 0); }
		public DecimalNumberContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterDecimalNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitDecimalNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitDecimalNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetContext extends ValueContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public SetContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitSet(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueContext extends ValueContext {
		public TrueContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitTrue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends ValueContext {
		public FalseContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitFalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnitContext extends ValueContext {
		public UnitContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitUnit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegDecimalNumberContext extends ValueContext {
		public TerminalNode DECIMAL() { return getToken(ValueParser.DECIMAL, 0); }
		public NegDecimalNumberContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterNegDecimalNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitNegDecimalNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitNegDecimalNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapContext extends ValueContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public MapContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).enterMap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValueListener ) ((ValueListener)listener).exitMap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ValueVisitor ) return ((ValueVisitor<? extends T>)visitor).visitMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_value);
		int _la;
		try {
			setState(59);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new UnitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(7);
				match(T__0);
				setState(8);
				match(T__1);
				}
				break;
			case 2:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(9);
				match(T__2);
				}
				break;
			case 3:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(10);
				match(T__3);
				}
				break;
			case 4:
				_localctx = new DecimalNumberContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(11);
				match(DECIMAL);
				}
				break;
			case 5:
				_localctx = new NegDecimalNumberContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(12);
				match(T__4);
				setState(13);
				match(DECIMAL);
				}
				break;
			case 6:
				_localctx = new SetContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(14);
				match(T__5);
				setState(23);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__12) | (1L << DECIMAL))) != 0)) {
					{
					setState(15);
					value();
					setState(20);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(16);
						match(T__6);
						setState(17);
						value();
						}
						}
						setState(22);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(25);
				match(T__7);
				}
				break;
			case 7:
				_localctx = new MapContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(26);
				match(T__8);
				setState(40);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__12) | (1L << DECIMAL))) != 0)) {
					{
					setState(27);
					value();
					setState(28);
					match(T__9);
					setState(29);
					value();
					setState(37);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(30);
						match(T__6);
						setState(31);
						value();
						setState(32);
						match(T__9);
						setState(33);
						value();
						}
						}
						setState(39);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(42);
				match(T__10);
				}
				break;
			case 8:
				_localctx = new ArrayContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(45);
				_la = _input.LA(1);
				if (_la==DECIMAL) {
					{
					setState(43);
					match(DECIMAL);
					setState(44);
					match(T__11);
					}
				}

				setState(47);
				match(T__12);
				setState(56);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__8) | (1L << T__12) | (1L << DECIMAL))) != 0)) {
					{
					setState(48);
					value();
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(49);
						match(T__6);
						setState(50);
						value();
						}
						}
						setState(55);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(58);
				match(T__13);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23@\4\2\t\2\4\3\t"+
		"\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\25\n\3"+
		"\f\3\16\3\30\13\3\5\3\32\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7"+
		"\3&\n\3\f\3\16\3)\13\3\5\3+\n\3\3\3\3\3\3\3\5\3\60\n\3\3\3\3\3\3\3\3\3"+
		"\7\3\66\n\3\f\3\16\39\13\3\5\3;\n\3\3\3\5\3>\n\3\3\3\2\2\4\2\4\2\2K\2"+
		"\6\3\2\2\2\4=\3\2\2\2\6\7\5\4\3\2\7\b\7\2\2\3\b\3\3\2\2\2\t\n\7\3\2\2"+
		"\n>\7\4\2\2\13>\7\5\2\2\f>\7\6\2\2\r>\7\21\2\2\16\17\7\7\2\2\17>\7\21"+
		"\2\2\20\31\7\b\2\2\21\26\5\4\3\2\22\23\7\t\2\2\23\25\5\4\3\2\24\22\3\2"+
		"\2\2\25\30\3\2\2\2\26\24\3\2\2\2\26\27\3\2\2\2\27\32\3\2\2\2\30\26\3\2"+
		"\2\2\31\21\3\2\2\2\31\32\3\2\2\2\32\33\3\2\2\2\33>\7\n\2\2\34*\7\13\2"+
		"\2\35\36\5\4\3\2\36\37\7\f\2\2\37\'\5\4\3\2 !\7\t\2\2!\"\5\4\3\2\"#\7"+
		"\f\2\2#$\5\4\3\2$&\3\2\2\2% \3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2("+
		"+\3\2\2\2)\'\3\2\2\2*\35\3\2\2\2*+\3\2\2\2+,\3\2\2\2,>\7\r\2\2-.\7\21"+
		"\2\2.\60\7\16\2\2/-\3\2\2\2/\60\3\2\2\2\60\61\3\2\2\2\61:\7\17\2\2\62"+
		"\67\5\4\3\2\63\64\7\t\2\2\64\66\5\4\3\2\65\63\3\2\2\2\669\3\2\2\2\67\65"+
		"\3\2\2\2\678\3\2\2\28;\3\2\2\29\67\3\2\2\2:\62\3\2\2\2:;\3\2\2\2;<\3\2"+
		"\2\2<>\7\20\2\2=\t\3\2\2\2=\13\3\2\2\2=\f\3\2\2\2=\r\3\2\2\2=\16\3\2\2"+
		"\2=\20\3\2\2\2=\34\3\2\2\2=/\3\2\2\2>\5\3\2\2\2\n\26\31\'*/\67:=";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}