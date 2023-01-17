#r "sdk:Microsoft.NET.Sdk.Web"
#r "nuget: Clojure, 1.12.0-alpha3"
#r "nuget: DynamicLanguageRuntime 1.3.2"
#r "nuget: clojure.core.specs.alpha 0.2.26"
#r "nuget: clojure.spec.alpha 0.3.218"

using Microsoft.AspNetCore.Builder;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using clojure.lang;
using System.Reflection;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing.

 readonly Symbol CLOJURE_MAIN = Symbol.intern("clojure.main");
 readonly Var REQUIRE = RT.var("clojure.core", "require");
readonly Var LEGACY_REPL = RT.var("clojure.main", "legacy-repl");
readonly Var LEGACY_SCRIPT = RT.var("clojure.main", "legacy-script");
 readonly Var MAIN = RT.var("clojure.main", "main");

RT.Init();
REQUIRE.invoke(CLOJURE_MAIN);
MAIN.applyTo(RT.seq(Args));