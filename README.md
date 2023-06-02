# clojure-clr-dotnet-api
Example of using clojure-clr and dotnet 7 minimal api 100% async - interoping with System.Threading.Tasks.Task using clojure core.async.

## Quickstart (Dockerfile)
1. build the image:  `docker build -t clojure-clr-api .`
2. docker-compose up (launches postgres and the application)

## Development Setup
* Install .NET 7 and Docker 
* Install [potion - a clojure clr tool](https://github.com/clojure/clojure-clr/wiki/Getting-started#installing-clojureclr-as-a-dotnet-tool) as a tool: `dotnet tool install --global potion` -- must be version 1.0.5+
* Run `dotnet restore` to install the nuget packages specified in `CljApi.csproj`
* docker-compose up using the docker-compose in /dev

## Start & Connect to a repl
From the terminal:
1. start a clojure nrepl:  `potion repl`
2. connect with a nrepl compatible editor, e.g. VSCode using the Calva extension.
3. Eval code via the repl 💠 - `main.cljr` contains a comment block where you can start and stop the application via the repl.

## Run the application
`potion -m clj-api.main`

## Routes
GET - `/`          
GET - `/users` -     returns all users
GET - `/healthz` -   returns health check status

## Features
* System composition using integrant :white_check_mark:
* Routing :white_check_mark:
* Db Access :white_check_mark:
* Add routes that accepts json, returns json :white_check_mark:
* Health Checks :white_check_mark 
* Structured Logging with Serilog :white_check_mark
* Background jobs 
* Metrics                         
* Swagger Docs                   
* Request Validation             
* CI - build a Docker image from a Dockerfile


## Limitations
- Can't produce a standalone DLL with clojure-clr for .NET Core 3.1+
