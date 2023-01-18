# clojure-clr-dotnet-api
Example of using clojure-clr and dotnet 7 and integrant for building a web api with metrics, db-access, structured-logging, swagger, health-checks.


## Setup
* Install .NET 6 or 7 - .NET 7 contains huge performance improvements.
* Install [Clojure.Main as a dotnet tool](https://github.com/clojure/clojure-clr/wiki/Getting-started#installing-clojureclr-as-a-dotnet-tool) as a tool: `dotnet tool install --global --version 1.12.0-alpha3 Clojure.Main`


## Start & Connect to a repl
From the terminal:
1. start a clojure repl:  `Clojure.Main`
3. Start a socket repl server: `(clojure.core.server/start-server {:name "test" :port 7650 :accept 'clojure.core.server/repl})`
4. Install vs-code and [the clover plugin](https://marketplace.visualstudio.com/items?itemName=mauricioszabo.clover) and connect to the socket repl.
5. Eval code via the repl 💠

## Run the application
`Clojure.Main -m clj-api.main`

## Routes
POST - `/json` - accepts a json map: `{"name":"danger"}`  
GET - `/` -     returns json  
GET - `/health` -     returns health check status

## Features
* System composition using integrant [X]
* Routing [X]
* Health Checks [X]
* Add routes that accepts json, returns json [X]
* Structured Logging with Serilog []
* Metrics                         []
* Swagger Docs                    []
* Request Validation              []
* Add routes that stores data in a database []
* CI - build a Docker image from a Dockerfile []


## Problems
- Package management = copy paste dll's atm.
- interop with Tasks using clojure.core.async only works on Windows which means we can't go non-blocking.
- Can't produce a standalone DLL 