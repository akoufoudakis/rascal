/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/

package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.BooleanEvaluator;

import org.rascalmpl.interpreter.Evaluator;

import org.rascalmpl.interpreter.PatternEvaluator;

import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Replacement extends AbstractAST {
  public Replacement(IConstructor node) {
    super(node);
  }
  

  public boolean hasReplacementExpression() {
    return false;
  }

  public org.rascalmpl.ast.Expression getReplacementExpression() {
    throw new UnsupportedOperationException();
  }

  public boolean hasConditions() {
    return false;
  }

  public java.util.List<org.rascalmpl.ast.Expression> getConditions() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Replacement {
  private final java.util.List<org.rascalmpl.ast.Replacement> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Replacement> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  @Override
  public Result<IValue> interpret(Evaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment env) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public IBooleanResult buildBooleanBacktracker(BooleanEvaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }

  @Override
  public IMatchingResult buildMatcher(PatternEvaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  public java.util.List<org.rascalmpl.ast.Replacement> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitReplacementAmbiguity(this);
  }
}





  public boolean isUnconditional() {
    return false;
  }
  
static public class Unconditional extends Replacement {
  // Production: sig("Unconditional",[arg("org.rascalmpl.ast.Expression","replacementExpression")])

  
     private final org.rascalmpl.ast.Expression replacementExpression;
  

  
public Unconditional(IConstructor node , org.rascalmpl.ast.Expression replacementExpression) {
  super(node);
  
    this.replacementExpression = replacementExpression;
  
}


  @Override
  public boolean isUnconditional() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitReplacementUnconditional(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Expression getReplacementExpression() {
        return this.replacementExpression;
     }
     
     @Override
     public boolean hasReplacementExpression() {
        return true;
     }
  	
}


  public boolean isConditional() {
    return false;
  }
  
static public class Conditional extends Replacement {
  // Production: sig("Conditional",[arg("org.rascalmpl.ast.Expression","replacementExpression"),arg("java.util.List\<org.rascalmpl.ast.Expression\>","conditions")])

  
     private final org.rascalmpl.ast.Expression replacementExpression;
  
     private final java.util.List<org.rascalmpl.ast.Expression> conditions;
  

  
public Conditional(IConstructor node , org.rascalmpl.ast.Expression replacementExpression,  java.util.List<org.rascalmpl.ast.Expression> conditions) {
  super(node);
  
    this.replacementExpression = replacementExpression;
  
    this.conditions = conditions;
  
}


  @Override
  public boolean isConditional() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitReplacementConditional(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Expression getReplacementExpression() {
        return this.replacementExpression;
     }
     
     @Override
     public boolean hasReplacementExpression() {
        return true;
     }
  
     @Override
     public java.util.List<org.rascalmpl.ast.Expression> getConditions() {
        return this.conditions;
     }
     
     @Override
     public boolean hasConditions() {
        return true;
     }
  	
}



}
