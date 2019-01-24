// ---------------------------------------------------------------------------
// Value.g4
// Value ANTLR 4 Grammar 
// $Id: Value.g4,v 1.2 2016/12/12 13:01:54 schreine Exp $
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

grammar Value;

options 
{
  language=Java;
}

@header 
{
  package riscal.parser;
}

// ---------------------------------------------------------------------------
// parser rules
// ---------------------------------------------------------------------------

valueFile: value EOF ;

value : 
  '(' ')'                                                 #Unit
| 'true'                                                  #True
| 'false'                                                 #False
| DECIMAL                                                 #DecimalNumber
| '-' DECIMAL                                             #NegDecimalNumber
| '{' ( value ( ',' value )* )? '}'                       #Set
| '<' ( value '->' value ( ',' value '->' value )* )? '>' #Map
| ( DECIMAL ':' )? '[' ( value ( ',' value )* )? ']'      #Array
;

// ---------------------------------------------------------------------------
// lexical rules
// ---------------------------------------------------------------------------

DECIMAL     : [0-9]+ ;
WHITESPACE  : [ \t\r\n\f]+ -> skip ;
ERROR       : . ;

// ---------------------------------------------------------------------------
// end of file
// ---------------------------------------------------------------------------