# clojure-clr-dotnet-api
Example of using clojure-clr+dotnet. 

## Setup
* Install .NET 6 or 7 (tested with .NET 6)

* Install [dotnet-script](https://github.com/dotnet-script/dotnet-script) as a tool: `dotnet tool install -g dotnet-script`


## Start & Connect to a repl
From the terminal:
1. cd src
2. dotnet-script main.csx
3. Start a socket repl: `(start-socket-repl)` - starts a socket repl on `localhost:7650`
4. Install vs-code and [the clover plugin](https://marketplace.visualstudio.com/items?itemName=mauricioszabo.clover) and connect to the socket repl.
5. Eval code via the repl 💠

## Issues
Despite loading every assembly manually in:  
`"/usr/share/dotnet/shared/Microsoft.AspNetCore.App/6.0.13"`  
`"/usr/share/dotnet/shared/Microsoft.NETCore.App/6.0.13"`  

 [Microsoft.AspNetCore.Builder WebApplication](https://learn.microsoft.com/en-us/aspnet/core/fundamentals/minimal-apis/webapplication?view=aspnetcore-7.0) is not available when using the [Clojure.main dotnet tool](https://github.com/clojure/clojure-clr/wiki/Getting-started#installing-clojureclr-as-a-dotnet-tool)

This made me package everything into a dotnet-script. Not pretty but works for now.


## Structure
[src/clj_api](./src/clj_api) contains the clojure application code.   
[src/main.csx](./src/main.csx) is for loading dependencies and Clojure.

## TODO for fun
* Add routes that accepts json, returns json
* Add routes that stores data in a database using e.g. Dapper.
* Use .NET Middleware on routes
* CI - build a Docker image from a Dockerfile. 
