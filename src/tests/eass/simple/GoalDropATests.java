// ----------------------------------------------------------------------------
// Copyright (C) 2012 Louise A. Dennis,  and Michael Fisher
//
// This file is part of Gwendolen
// 
// Gwendolen is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
// 
// Gwendolen is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with Gwendolen; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// 
// To contact the authors:
// http://www.csc.liv.ac.uk/~lad
//
//----------------------------------------------------------------------------

package eass.simple;

import org.junit.Test;

import ail.util.AJPF_w_AIL;

import gov.nasa.jpf.util.test.TestJPF;

/**
 * Tests relating to goal dropping behaviour
 */
public class GoalDropATests extends TestJPF {

	static final String[] JPF_ARGS = { "-show", 
		 "+listener+=,.listener.ExecTracker",
	     "+et.print_insn=false",
	     "+vm.max_transition_length=MAX"

	};

	  //--- test methods

	  
	 @Test //----------------------------------------------------------------------
	 public void testMultipleIdenticalGoal () {
	   if (verifyNoPropertyViolation(JPF_ARGS)){
	   	String filename =  "/src/examples/eass/simple/DroppingGoals/CheckingGoalExistence.ail";
	   	String prop_filename =  "/src/examples/eass/simple/DroppingGoals/simple.psl";
	   	String[] args = new String[3];
	   	args[0] = filename;
	   	args[1] = prop_filename;
	   	args[2] = "1";
	   	AJPF_w_AIL.run(args);
	    } else {
	   	 
	    }
	 }


}