# clojure-clr-dotnet-api
Example of using clojure-clr and dotnet 7 and integrant for building a web api with metrics, db-access, structured-logging, swagger, health-checks.

## Quickstart (Dockerfile)
1. build the image:  `docker build -t clojure-clr-api .`
2. run the image: `docker run -p 5000:5000 docker.io/library/clojure-clr-api`

## Development Setup
* Install .NET 6 or 7 - .NET 7 
* Install [Clojure.Main as a dotnet tool](https://github.com/clojure/clojure-clr/wiki/Getting-started#installing-clojureclr-as-a-dotnet-tool) as a tool: `dotnet tool install --global --version 1.12.0-alpha3 Clojure.Main`
* Run `dotnet restore` to install the nuget packages specified in `CljApi.csproj`


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
* System composition using integrant :white_check_mark:
* Routing :white_check_mark:
* Health Checks :white_check_mark:
* Db Access :white_check_mark:
* Add routes that accepts json, returns json :white_check_mark:
* Background jobs 
* Structured Logging with Serilog 
* Metrics                         
* Swagger Docs                   
* Request Validation             
* CI - build a Docker image from a Dockerfile


## Problems
- Package management = have to load DLL's ourselves, check `user.cljr`
- Can't produce a standalone DLL 
