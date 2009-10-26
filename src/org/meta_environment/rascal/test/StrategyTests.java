package org.meta_environment.rascal.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO This test needs refactoring.
public class StrategyTests extends TestFramework{
	
	@Test
	public void testStrategy(){
		prepare("import StrategyTests;");
		prepareMore("import Strategy;");
		
		prepareMore("A t = f(g(g(b())),g(g(b())));");
		assertTrue(runTestInSameEvaluator("top_down(rules)(t) == f(g(b()),g(b()))"));
		assertTrue(runTestInSameEvaluator("bottom_up(rules)(t) == f(b(),b())"));
		
		prepareMore("B t2 = g(c());");
		assertTrue(runTestInSameEvaluator("once_top_down(rules2)(t2) == c()"));
		assertTrue(runTestInSameEvaluator("once_bottom_up(rules2)(t2) == g(b())"));
		
		prepareMore("list[B] l = [g(c()),c()];");
		assertTrue(runTestInSameEvaluator("makeAll(rules2)(l) == [c(),b()]"));
		
		prepareMore("tuple[A,B] t3 = <a(),c()>;");
		assertTrue(runTestInSameEvaluator("top_down(rules2)(t3) == <a(),b()>"));
		
		prepareMore("rel[A, B] r = {<a(), b()>, <f(b(),b()), c()>};");
		assertTrue(runTestInSameEvaluator("top_down(rules3)(r) == {<a(), c()>, <f(c(),c()), c()>}"));
		
		prepareMore("A t4 = f(g(b()),g(b()));");
		assertTrue(runTestInSameEvaluator("top_down(functionToStrategy(rules4))(t4) == f(g(c()),g(c()))"));
		
		assertTrue(runTestInSameEvaluator("innermost(rules)(t) == f(b(),b())"));
		assertTrue(runTestInSameEvaluator("outermost(rules)(t) == f(b(),b())"));
	}
	
	@Test
	public void testStrategySeperately(){
		TestFramework tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("A t = f(g(g(b())),g(g(b())));");
		assertTrue(tf.runTestInSameEvaluator("top_down(rules)(t) == f(g(b()),g(b()))"));
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("A t = f(g(g(b())),g(g(b())));");
		assertTrue(tf.runTestInSameEvaluator("bottom_up(rules)(t) == f(b(),b())"));

		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("B t2 = g(c());");
		assertTrue(tf.runTestInSameEvaluator("once_top_down(rules2)(t2) == c()"));
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("B t2 = g(c());");
		assertTrue(tf.runTestInSameEvaluator("once_bottom_up(rules2)(t2) == g(b())"));

		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("list[B] l = [g(c()),c()];");
		assertTrue(tf.runTestInSameEvaluator("makeAll(rules2)(l) == [c(),b()]"));

		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("tuple[A,B] t3 = <a(),c()>;");
		assertTrue(tf.runTestInSameEvaluator("top_down(rules2)(t3) == <a(),b()>"));

		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("rel[A, B] r = {<a(), b()>, <f(b(),b()), c()>};");
		assertTrue(tf.runTestInSameEvaluator("top_down(rules3)(r) == {<a(), c()>, <f(c(),c()), c()>}"));

		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("A t4 = f(g(b()),g(b()));");
		assertTrue(tf.runTestInSameEvaluator("top_down(functionToStrategy(rules4))(t4) == f(g(c()),g(c()))"));
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("A t = f(g(g(b())),g(g(b())));");
		assertTrue(tf.runTestInSameEvaluator("innermost(rules)(t) == f(b(),b())"));
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import Strategy;");
		tf.prepareMore("A t = f(g(g(b())),g(g(b())));");
		assertTrue(tf.runTestInSameEvaluator("outermost(rules)(t) == f(b(),b())"));
	}
	
	@Test
	public void testTopologicalStrategy(){
		prepare("import StrategyTests;");
		prepareMore("import TopologicalStrategy;");
		
		prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(runTestInSameEvaluator("topological_once_bottom_up(rules5)(r2) == {<a(),ee()>,<a(),d()>,<d(),ee()>}"));
		assertTrue(runTestInSameEvaluator("topological_once_top_down(rules5)(r2) == {<aa(),e()>,<aa(),d()>,<d(),e()>}"));
		
		assertTrue(runTestInSameEvaluator("topological_top_down(rules5)(r2) == {<aa(), dd()>, <aa(), ee()>, <dd(),ee()>}"));
		
		assertTrue(runTestInSameEvaluator("topological_bottom_up(rules5)(r2) == {<aa(), dd()>, <aa(), ee()>, <dd(),ee()>}"));
		assertTrue(runTestInSameEvaluator("topological_innermost(rules6)(r2) == {<h(aa()), h(dd())>, <h(aa()), h(ee())>, <h(dd()),h(ee())>}"));
		
		assertTrue(runTestInSameEvaluator("topological_outermost(rules6)(r2) == {<h(aa()), h(dd())>, <h(aa()), h(ee())>, <h(dd()),h(ee())>}"));
	}
	
	@Test
	public void testTopologicalStrategySeperately(){
		TestFramework tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import TopologicalStrategy;");
		tf.prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(tf.runTestInSameEvaluator("topological_once_bottom_up(rules5)(r2) == {<a(),ee()>,<a(),d()>,<d(),ee()>}"));
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import TopologicalStrategy;");
		tf.prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(tf.runTestInSameEvaluator("topological_once_top_down(rules5)(r2) == {<aa(),e()>,<aa(),d()>,<d(),e()>}"));
		
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import TopologicalStrategy;");
		tf.prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(tf.runTestInSameEvaluator("topological_top_down(rules5)(r2) == {<aa(), dd()>, <aa(), ee()>, <dd(),ee()>}"));
		
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import TopologicalStrategy;");
		tf.prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(tf.runTestInSameEvaluator("topological_bottom_up(rules5)(r2) == {<aa(), dd()>, <aa(), ee()>, <dd(),ee()>}"));
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import TopologicalStrategy;");
		tf.prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(tf.runTestInSameEvaluator("topological_innermost(rules6)(r2) == {<h(aa()), h(dd())>, <h(aa()), h(ee())>, <h(dd()),h(ee())>}"));
		
		tf = new TestFramework();
		tf.prepare("import StrategyTests;");
		tf.prepareMore("import TopologicalStrategy;");
		tf.prepareMore("rel[A, A] r2 = {<a(), d()>, <a(), e()>, <d(), e()>};");
		assertTrue(tf.runTestInSameEvaluator("topological_outermost(rules6)(r2) == {<h(aa()), h(dd())>, <h(aa()), h(ee())>, <h(dd()),h(ee())>}"));
	}
	
	@Test
	public void testCyclicStrategy(){
		prepare("import StrategyTests;");
		prepareMore("import TopologicalStrategy;");
		
		// test Top-Down in a cyclic relation
		prepareMore("rel[A, A] r3 = {<a(), d()>, <d(), e()>, <e(), d()>};");
		prepareMore("list[value] elts = [];");
		prepareMore("&T(&T) collect = &T(&T t) { elts += [t]; return t; };");
		prepareMore("topological_top_down(collect)(r3);");
		assertTrue(runTestInSameEvaluator("elts == [{<e(),d()>,<d(),e()>,<a(),d()>},a(),d(),e(),d()]"));
	}
}
