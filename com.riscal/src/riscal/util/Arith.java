// ---------------------------------------------------------------------------
// Arith.java
// Arithmetic functions
// $Id: Arith.java,v 1.4 2016/11/14 15:36:32 schreine Exp $
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
package riscal.util;

public final class Arith
{  
  /***************************************************************************
   * Get exponential value
   * @param base the base evalue
   * @param power the exponent (non-negative)
   * @return the value of base to the given power (exception on overflow)
   **************************************************************************/
  public static long power(long base, long power)
  {
    if (power == 0) return 1;
    long result = 1;
    while (true)
    {
      if (power%2 != 0) result = Math.multiplyExact(result, base);
      power = power/2;
      if (power == 0) return result;
      base = Math.multiplyExact(base, base);  
    }
  }
  
  /****************************************************************************
   * Get sum from+(from+1)+...+to
   * @param from the first summand
   * @param to the last summand
   * @return the sum (raises exception on overflow)
   ***************************************************************************/
  public static long multiSum(long from, long to)
  {
    // s = (a+b)*n/2
    if (to < from) return 0;
    long n = Math.subtractExact(to,from)+1;
    if (n%2 == 0) 
      return Math.multiplyExact(Math.addExact(from,to),n/2);
    else
      return Math.multiplyExact(Math.addExact(from,to)/2,n);
  }
  
  /****************************************************************************
   * Get product from*(from+1)*...*to
   * @param from the first factor
   * @param to the last factor
   * @return the product (raises exception on overflow)
   ***************************************************************************/
  public static long multiProduct(long from, long to)
  {
    if (from <= 0)
    {
      if (to >= 0) return 0;
    }
    else
    {
      if (to <= 0) return 0;
    }
    if (to < from) return 1;
    long result = 1;
    for (long factor = from; factor <= to; factor++)
      result = Math.multiplyExact(result, factor);
    return result;
  }
}
// ----------------------------------------------------------------------------
// end of file
// ----------------------------------------------------------------------------