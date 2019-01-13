// ---------------------------------------------------------------------------
// RISCAL.g4
// RISC Algorithm Language ANTLR 4 Grammar 
// $Id: RISCAL.g4,v 1.80 2018/06/14 13:22:44 schreine Exp schreine $
//
// Author: Wolfgang Schreiner <Wolfgang.Schreiner@risc.jku.at>
// Copyright (C) 2016-, Research Institute for Symbolic Computation (RISC)
// Johannes Kepler University, Linz, Austria, http://www.risc.jku.at
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
// ----------------------------------------------------------------------------

grammar RISCAL;

options 
{
  language=Java;
}

@header 
{
  package riscal.parser;
}
@members {boolean cartesian = true;}

// ---------------------------------------------------------------------------
// modules, declarations, commands
// ---------------------------------------------------------------------------

specification: ( declaration )* EOF ;

declaration:
// declarations (value externally defined, others forward defined)
  'val' ident ':' ( 'Nat' | 'ℕ' ) EOS                                #ValueDeclaration
| multiple 'fun' ident '(' ( param ( ',' param)* )? ')' ':' type EOS #FunctionDeclaration
| multiple 'pred' ident '(' ( param ( ',' param)* )? ')' EOS         #PredicateDeclaration
| multiple 'proc' ident '(' ( param ( ',' param)* )? ')' ':' type    #ProcedureDeclaration

// definitions
| 'type' ident '=' type ( 'with' exp )? EOS					       #TypeDefinition
| 'rectype' '(' exp ')' ritem ( 'and' ritem)* EOS          #RecTypeDefinition
| 'enumtype' ritem EOS                                     #EnumTypeDefinition
| 'val' ident ( ':' type )? '=' exp EOS                    #ValueDefinition
| 'pred' ident  ( '⇔' | '<=>' ) exp EOS                    #PredicateValueDefinition
| 'theorem' ident ( '⇔' | '<=>' ) exp EOS                  #TheoremDefinition
| multiple 'fun' ident '(' ( param ( ',' param)* )? ')' ':' type 
  ( funspec )* '=' exp EOS                                 #FunctionDefinition
| multiple 'pred' ident '(' ( param ( ',' param)* )? ')' 
  ( funspec )* ( '⇔' | '<=>' ) exp EOS                     #PredicateDefinition
| multiple 'proc' ident '(' ( param ( ',' param)* )? ')' ':' type 
  ( funspec )* '{' ( command )* ( 'return' exp EOS )? '}'  #ProcedureDefinition
| multiple 'theorem' ident '(' ( param ( ',' param)* )? ')' 
  ( funspec )* ( '⇔' | '<=>' ) exp EOS                     #TheoremParamDefinition
;

funspec :
  'requires' exp EOS		          #RequiresSpec
| 'ensures' exp	 EOS		          #EnsuresSpec
| 'decreases' exp ( ',' exp)* EOS	#DecreasesSpec
| 'modular' EOS                   #ContractSpec
;

// commands terminated by a semicolon
scommand:
                                                         #EmptyCommand
| ident ( sel )* ( ':=' | '≔' | '=' ) exp                #AssignmentCommand
| 'choose' qvar                                          #ChooseCommand
| 'do' ( loopspec )* command 'while' exp                 #DoWhileCommand
| 'var' ident ':' type ( ( ':=' | '≔' | '=' ) exp )?     #VarCommand
| 'val' ident ( ':' type )? ( ':=' | '≔' | '=' ) exp     #ValCommand
| 'assert' exp                                           #AssertCommand
| 'print' ( STRING ',' )? exp ( ',' exp)*                #PrintCommand
| 'print' STRING                                         #Print2Command
| 'check' ident ( 'with' exp )?                          #CheckCommand
| exp                                                    #ExpCommand
;

command:
  scommand EOS                                              #SemicolonCommand
| 'choose' qvar 'then' command 'else' command               #ChooseElseCommand
| 'if' exp 'then' command                                   #IfThenCommand
| 'if' exp 'then' command 'else' command                    #IfThenElseCommand
| 'match' exp 'with' '{' ( pcommand )+ '}'                  #MatchCommand
| 'while' exp 'do' ( loopspec )* command                    #WhileCommand
| 'for' scommand EOS exp EOS scommand 'do' ( loopspec )* command #ForCommand
| 'for' qvar 'do' ( loopspec )* command                     #ForInCommand
| 'choose' qvar 'do' ( loopspec )* command                  #ChooseDoCommand
| '{' ( command )* '}'                                      #CommandSequence
;

loopspec :
  'invariant' exp	EOS             #InvariantLoopSpec
| 'decreases' exp ( ',' exp)* EOS #DecreasesLoopSpec
;

// ---------------------------------------------------------------------------
// expressions, types
// ---------------------------------------------------------------------------

exp  :
// literals
  '(' ')'																	#UnitExp
| ( '⊤' | 'true'	)												#TrueExp
| ( '⊥' | 'false'	)												#FalseExp
| decimal																  #IntLiteralExp
| ( '∅' | '{' '}' ) '[' type ']'					#EmptySetExp

// identifier-like
| ident '!' ident												  #RecIdentifierExp
| ident '!' ident '(' exp ( ',' exp)* ')' #RecApplicationExp
| ident																	  #IdentifierExp
| ident '(' ( exp ( ',' exp)* )? ')'		  #ApplicationExp
| 'Array' '[' exp ',' type ']' '(' exp ')' #ArrayBuilderExp
| 'Map' '[' type ',' type ']' '(' exp ')' #MapBuilderExp
| 'Set' '(' exp ')'                       #PowerSetExp
| 'Set' '(' exp ',' exp ')'               #PowerSet1Exp
| 'Set' '(' exp ',' exp ',' exp ')'       #PowerSet2Exp

// selections
| exp '[' exp ']'                         #ArraySelectionExp
| exp '.' decimal                         #TupleSelectionExp
| exp '.' ident                           #RecordSelectionExp

// postfix-term-like
| exp '!'                                 #FactorialExp

// prefix-term-like
| '-' exp																	#NegationExp
| ('⋂' | 'Intersect') exp   							#BigIntersectExp
| ('⋃' | 'Union') exp                     #BigUnionExp

// infix-term-like
| exp '^' exp                              #PowerExp
| exp ( '*' | '⋅' ) exp                    #TimesExp
| exp ( '*' | '⋅' ) '..' ( '*' | '⋅' ) exp #TimesExpMult
| exp '/' exp                              #DividesExp
| exp '%' exp                              #RemainderExp
| exp '-' exp                              #MinusExp
| exp '+' exp															 #PlusExp
| exp '+' '..' '+' exp                     #PlusExpMult
| '{' exp ( ',' exp)* '}'			             #EnumeratedSetExp
| exp '..' exp														 #IntervalExp
| ( '〈' | '⟨' | '〈' | '<<' ) exp ( ',' exp )* ( '〉' | '⟩' | '〉' | '>>' )       #TupleExp
| ( '〈' | '⟨' | '〈' | '<<' ) eident ( ',' eident )* ( '〉' | '⟩' | '〉' | '>>' ) #RecordExp
| '|' exp '|'															 #SetSizeExp
| exp ( '∩' | 'intersect') exp 						 #IntersectExp
| exp ( '∪' | 'union' ) exp                #UnionExp
| exp '\\' exp              							 #WithoutExp

// n-ary infix-term-like (hack allows non-associative parsing without parentheses)
| '(' exp ( ( '×' | 'times' ) exp  )+ ')' #CartesianExp
| exp ( {cartesian}? ( '×' | 'times' ) {cartesian=false;} exp {cartesian=true;} )+ #CartesianExp

// term-quantifier like
| '#' qvar        						    				 #NumberExp
| '{' exp '|' qvar '}'										 #SetBuilderExp
| 'choose' qvar														 #ChooseExp
| ( '∑' | 'Σ' | 'sum' ) qvar '.' exp       #SumExp
| ( '∏' | 'Π' | 'product' ) qvar '.' exp   #ProductExp
| 'min' qvar '.' exp                       #MinExp
| 'max' qvar '.' exp                       #MaxExp

// updates
| exp 'with' '[' exp ']' '=' exp          #ArrayUpdateExp
| exp 'with' '.' decimal '=' exp          #TupleUpdateExp
| exp 'with' '.' ident '=' exp            #RecordUpdateExp

// relations
| exp '=' exp															#EqualsExp
| exp ( '≠' | '~=' ) exp									#NotEqualsExp
| exp '<' exp															#LessExp
| exp ( '≤' | '<=' ) exp									#LessEqualExp
| exp '>' exp															#GreaterExp
| exp ( '≥' | '>=' ) exp									#GreaterEqualExp
| exp ( '∈' | 'isin' ) exp        			  #InSetExp
| exp ( '⊆' | 'subseteq' ) exp   					#SubsetExp

// propositions
| ( '¬' | '~' ) exp												#NotExp
| exp ( '∧' | '/\\' ) exp									#AndExp
| exp ( '∨' | '\\/' ) exp									#OrExp
| exp ( '⇒' | '=>' ) exp									#ImpliesExp
| exp ( '⇔' | '<=>' ) exp									#EquivExp

// conditionals
| 'if' exp 'then' exp 'else' exp             #IfThenElseExp
| 'match' exp 'with' '{' ( pexp ';' )+ '}'   #MatchExp
													
// quantified formulas
| ( '∀' | 'forall' ) qvar '.' exp					#ForallExp
| ( '∃' | 'exists' ) qvar '.' exp					#ExistsExp

// binders
| 'let' binder ( ',' binder )* 'in' exp	   #LetExp
| 'letpar' binder ( ',' binder )* 'in' exp #LetParExp
| 'choose' qvar 'in' exp									 #ChooseInExp
| 'choose' qvar 'in' exp 'else' exp				 #ChooseInElseExp

// assertions
| 'assert' exp 'in' exp                   #AssertExp

// print expression
| 'print' ( STRING ',' )? exp                      #PrintExp
| 'print' ( STRING ',' )? exp ( ',' exp)* 'in' exp #PrintInExp

// check operation
| 'check' ident ( 'with' exp )?           #CheckExp

// parenthesized expressions
| '(' exp ')'															#ParenthesizedExp
;
  
// types
type :
  ( 'Unit' | '(' ')' ) 												 #UnitType
| 'Bool'												               #BoolType
| ( 'Int' | 'ℤ' )  '[' exp ',' exp ']' 		     #IntType
| 'Map' '[' type ',' type ']'                  #MapType
| 'Tuple' '[' type ( ',' type)* ']'            #TupleType
| 'Record' '[' param ( ',' param)* ']'         #RecordType
| ( 'Nat' | 'ℕ' ) '[' exp ']'                  #NatType
| 'Set' '[' type ']'						               #SetType
| 'Array' '[' exp ',' type ']'    	           #ArrayType
| ident												                 #IdentifierType 
;

// ---------------------------------------------------------------------------
// auxiliaries
// ---------------------------------------------------------------------------

qvar :
  qvcore ( ',' qvcore )* ( 'with' exp )?	#QuantifiedVariable
;

qvcore :
  ident ':' type            #IdentifierTypeQuantifiedVar
| ident ( '∈' | 'in' ) exp  #IdentifierSetQuantifiedVar
;

binder : ident '=' exp ;

pcommand :
  ident'->' command									            #IdentifierPatternCommand
| ident'(' param ( ',' param)* ')' '->' command	#ApplicationPatternCommand
| '_' '->' command									            #DefaultPatternCommand
;

pexp :
  ident'->' exp											            #IdentifierPatternExp
| ident'(' param ( ',' param)* ')' '->' exp			#ApplicationPatternExp
| '_' '->' exp											            #DefaultPatternExp
;

param : ident ':' type ;

ritem: ident '=' rident ( '|' rident)* ;

eident : ident ':' exp ;

rident :
  ident								            #RecIdentifier
| ident '(' type ( ',' type)* ')'	#RecApplication
;

sel :
  '[' exp ']'		#MapSelector
| '.' decimal	  #TupleSelector
| '.' ident			#RecordSelector
;

multiple : 
             #IsNotMultiple
| 'multiple' #IsMultiple
;

ident   : IDENT ;
decimal : DECIMAL ;

// ---------------------------------------------------------------------------
// lexical rules
// ---------------------------------------------------------------------------

// reserve leading underscore for internal identifiers
IDENT   : [a-zA-Z][a-zA-Z_0-9]* ;
DECIMAL : [0-9]+ ;
EOS     : ';' ;

// format string literals
STRING : '"' (ESC|.)*? '"' ;
fragment ESC : '\\"' | '\\n' | '\\%' | '\\\\';

WHITESPACE  : [ \t\r\n\f]+ -> skip ;
LINECOMMENT : '//' .*? '\r'? ('\n' | EOF) -> skip ;
COMMENT     : '/*' .*? '*/' -> skip ;

// matches any other character
ERROR : . ;

// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------