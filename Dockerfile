FROM mcr.microsoft.com/dotnet/sdk:7.0.302-jammy-amd64
## We use a fat docker image until there's compilation support.

RUN apt-get update \
    && apt-get install -y wget unzip curl default-jre rlwrap \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY CljApi.csproj .
COPY clj_api/ clj_api
COPY user.cljr .

RUN dotnet tool install --global potion
RUN dotnet restore

# Add .NET global tools to PATH
ENV PATH="$PATH:/root/.dotnet/tools"

CMD ["potion", "-m", "clj-api.main"]