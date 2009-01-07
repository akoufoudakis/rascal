module AsFix

data ParseTree parsetree(Tree top, int amb_cnt);

data Tree 	appl(Production prod, Args args) |
	 		cycle(Symbol symbol, int cycle_length) |
	 		amb(Args args) | 
	 		int intVal;

// constructor(Tree, char, <character(int)>), %%??

/*

type list[Tree] Args;


data Production prod(Symbols lhs, Symbol rhs, Attributes attributes);
 
//constructor(Production, List, list(<rhs(Symbol)>)), 

data Production list[Symbol] symbols;

data Attributes no-attrs;
data Attributes attrs(Attrs attrs);

type list[Attr] Attrs;

data Attr assoc(Associativity assoc) | // term(term term) |
             id(str module_name) | bracket | reject | prefer | avoid;

data Associativity left | right | assoc | non-assoc;

type  list[Symbol] Symbols;
//data Symbols list[Symbol] symbols;

// constructor(CharRange, character, <start(int)>), 

data CharRange int range;
data CharRange range(int start, int end);

data CharRanges alist(CharRange ranges);  // clash with keyword list!

data Symbol lit(str string);
data Symbol cilit(str string);
data Symbol cf(Symbol symbol);
data Symbol lex(Symbol symbol);
data Symbol empty;
data Symbol seq(Symbols symbols);
data Symbol opt(Symbol symbol);
data Symbol alt(Symbol lhs, Symbol rhs);
data Symbol atuple(Symbol head, Symbols rest);  // clash with keyword tuple!
data Symbol sort (str string); 
data Symbol iter-plus(Symbol symbol); 
data Symbol iter-star(Symbol symbol); 
data Symbol iter-plus-sep(Symbol symbol, Symbol separator); 
data Symbol iter-start-sep(Symbol symbol, Symbol separator); 
data Symbol iter-n(Symbol symbol, int number); 
data Symbol iter-sep-n(Symbol symbol, Symbol separator, int number); 
data Symbol func(Symbols symbols, Symbol symbol); 
data Symbol parameterized-sort(str sort, Symbols parameters); 
data Symbol strategy(Symbol lhs, Symbol rhs);
data Symbol var-sym(Symbol symbol);
data Symbol layout; 
data Symbol char-class(CharRanges ranges);



str join(list[&T] lst, str (&T arg) tostring, str asep){ 
    str res = "";
    str sep = "";
    for(&T t : lst){
         res = sep + #tostring(t);
         sep = asep;
      };
     return res;
}

str yieldTree(Tree T) {
  switch (T) {
  case appl(Production prod, Args args): {
     str res = "";
     visit(prod.lhs) {
       case lit(str s): {
         res = res + s.string;
       }
       case cf(Symbol sym): {
         res = res + yieldTree(head(args)); args = rest(args);
         res = res + yieldTree(head(args)); args = rest(args);
       }
       // ...
     };
     if(args != []){
        res = res + yieldTree(head(args));
     };
     assert "args should be empty": args == [];
     return res;
   }
   case amb(Args args): {
     return "<<<" + join(args, yieldTree, ",") + ">>>";
 
   }
//   case int n: return n;
   
   case cycle(Symbol symbol, int cycle_length): { return ""; }
   
 }
}
*/
