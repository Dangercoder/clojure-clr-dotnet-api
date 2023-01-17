
## Setup
* Install dotnet 6.

* Install [dotnet-script](https://github.com/dotnet-script/dotnet-script) as a tool: `dotnet tool install -g dotnet-script`


## Start & Connect to a repl
From the terminal:
1. dotnet-script main.csx
2. Start a socket repl: `(clojure.core.server/start-server {:name "test" :port 7650 :accept 'clojure.core.server/repl})`
3. Install vs-code and [the clover plugin](https://marketplace.visualstudio.com/items?itemName=mauricioszabo.clover) and connect to the socket repl.
4. Eval code via the repl 💠

## Issues
Despite loading every assembly manually in:  
`"/usr/share/dotnet/shared/Microsoft.AspNetCore.App/6.0.13"`  
`"/usr/share/dotnet/shared/Microsoft.NETCore.App/6.0.13"`  

 [Microsoft.AspNetCore.Builder WebApplication](https://learn.microsoft.com/en-us/aspnet/core/fundamentals/minimal-apis/webapplication?view=aspnetcore-7.0) is not available when using the [Clojure.main dotnet tool](https://github.com/clojure/clojure-clr/wiki/Getting-started#installing-clojureclr-as-a-dotnet-tool)

This made me package everything into a dotnet-script. Not pretty but works for now.


## Structure
[src/clj_api](./src/clj_api) contains the clojure application code.   
[src/main.csx](./src/main.csx) is for loading dependencies and Clojure.
