#r "sdk:Microsoft.NET.Sdk.Web"
#r "nuget: Clojure, 1.12.0-alpha3"
#r "nuget: DynamicLanguageRuntime 1.3.2"
#r "nuget: clojure.core.specs.alpha 0.2.26"
#r "nuget: clojure.spec.alpha 0.3.218"

using Microsoft.AspNetCore.Builder;
using clojure.lang;
using clojure.clr.api;

 readonly Symbol CLOJURE_MAIN = Symbol.intern("clojure.main");
 readonly Var REQUIRE = RT.var("clojure.core", "require");
 readonly Var LEGACY_REPL = RT.var("clojure.main", "legacy-repl");
 readonly Var LEGACY_SCRIPT = RT.var("clojure.main", "legacy-script");
 readonly Var MAIN = RT.var("clojure.main", "main");

RT.Init();
REQUIRE.invoke(CLOJURE_MAIN);
var load = Clojure.var("clojure.core", "load");
load.invoke("/clj_api/user");

MAIN.applyTo(RT.seq(Args));