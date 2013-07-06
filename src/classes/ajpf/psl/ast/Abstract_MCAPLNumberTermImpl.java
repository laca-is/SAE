// ----------------------------------------------------------------------------
// Copyright (C) 2012 Louise A. Dennis, and  Michael Fisher 
//
// This file is part of Agent JPF (AJPF)
// 
// AJPF is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
// 
// AJPF is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with AJPF; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// 
// To contact the authors:
// http://www.csc.liv.ac.uk/~lad
//
//----------------------------------------------------------------------------

package ajpf.psl.ast;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ajpf.psl.MCAPLNumberTermImpl;

import gov.nasa.jpf.annotation.FilterField;
//import gov.nasa.jpf.jvm.abstraction.filter.FilterField;
import gov.nasa.jpf.jvm.ClassInfo;
import gov.nasa.jpf.jvm.ElementInfo;
import gov.nasa.jpf.jvm.Heap;
import gov.nasa.jpf.jvm.JVM;
import gov.nasa.jpf.jvm.MJIEnv;
import gov.nasa.jpf.jvm.ThreadInfo;
import gov.nasa.jpf.jvm.Types;
import gov.nasa.jpf.jvm.Verify;

/** Immutable class that implements a term that represents a number */
public final class Abstract_MCAPLNumberTermImpl implements Abstract_MCAPLNumberTerm {

	private final double fValue;
	
	public Abstract_MCAPLNumberTermImpl() {
		super();
		fValue = 0;
	}
	
	public Abstract_MCAPLNumberTermImpl(String sn) {
		double t = 0;
		try {
			t = Double.parseDouble(sn);
		} catch (Exception e) {
			// logger.log(Level.SEVERE,"Error setting number term value from "+sn,e);
		}
		fValue = t;
	}
	
	public Abstract_MCAPLNumberTermImpl(double vl) {
		fValue = vl;
	}
	
	public Abstract_MCAPLNumberTermImpl clone() {
		//return new NumberTermImpl(solve());
		return this;
	}
	
    
	public String toString() {
		return String.valueOf(fValue);
	}
	
	public MCAPLNumberTermImpl toMCAPL() {
		return new MCAPLNumberTermImpl(fValue);
	}
	
	public int newJPFObject(MJIEnv env) {
		int ref = env.newObject("ajpf.psl.ast.Abstract_MCAPLNumberTermImpl");
		env.setDoubleField(ref, "fValue", fValue);
		return ref;

	}

	public int createInJPF(JVM vm) {
		Heap heap = vm.getHeap();
		ThreadInfo ti = vm.getLastThreadInfo();
		ClassInfo ci = ClassInfo.getResolvedClassInfo("ajpf.psl.ast.Abstract_MCAPLNumberTermImpl");
		int objref = heap.newObject(ci, ti);
		ElementInfo ei = vm.getElementInfo(objref);
		ei.setDoubleField("fValue", fValue);

		return objref;
	}

	public Abstract_MCAPLTerm getTerm(int i) {
		return null;
	}
    
  
}