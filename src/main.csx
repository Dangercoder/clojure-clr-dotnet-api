#r "sdk:Microsoft.NET.Sdk.Web"
#r "nuget: Clojure, 1.12.0-alpha3"

using Microsoft.AspNetCore.Builder;
using clojure.lang;

 readonly Symbol CLOJURE_MAIN = Symbol.intern("clojure.main");
 readonly Var REQUIRE = RT.var("clojure.core", "require");
 readonly Var LEGACY_REPL = RT.var("clojure.main", "legacy-repl");
 readonly Var LEGACY_SCRIPT = RT.var("clojure.main", "legacy-script");
 readonly Var MAIN = RT.var("clojure.main", "main");

RT.Init();
REQUIRE.invoke(CLOJURE_MAIN);
MAIN.applyTo(RT.seq(Args));