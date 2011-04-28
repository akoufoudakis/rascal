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
 *   * Emilie Balland - emilie.balland@inria.fr (INRIA)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter.matching;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.PatternEvaluator;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;

public class MatchResult extends AbstractBooleanResult {
	private boolean positive;
	private IMatchingResult mp;
	private Expression expression;
	private boolean firstTime;
	private Expression pattern;
	
	public MatchResult(IEvaluatorContext ctx, Expression pattern, boolean positive, Expression expression) {
		super(ctx);
    	this.positive = positive;
    	this.pattern = pattern;
    	this.mp = null;
    	this.expression = expression;
	}

    @Override
    public void init() {
    	super.init();
    	
    	// because the right hand side may introduce types that are needed
    	// in the left-hand side, we first need to evaluate the expression
    	// before we construct a pattern.
		Result<IValue> result = expression.interpret(ctx.getEvaluator());
		Type subjectType = result.getType();

		if (mp == null) {
			mp = pattern.getMatcher(new PatternEvaluator(ctx));
		}
		
    	mp.initMatch(expression.interpret(ctx.getEvaluator()));

    	if(!mp.mayMatch(subjectType, ctx.getCurrentEnvt())) {
    		throw new UnexpectedTypeError(mp.getType(ctx.getCurrentEnvt()), subjectType, ctx.getCurrentAST());
    	}
    	
    	firstTime = true;
    }

    @Override
	public boolean hasNext() {
    	if (positive) {
    		return mp.hasNext();
    	}
    	
		if (firstTime) {
			return true;
		}
		return mp.hasNext();
	}

    @Override
	public boolean next() {
    	firstTime = false;
		// TODO: should manage escape variable from negative matches!!!
		if(hasNext()){	
			return positive ? mp.next() : !mp.next();
		}
		
		return !positive;
	}
}