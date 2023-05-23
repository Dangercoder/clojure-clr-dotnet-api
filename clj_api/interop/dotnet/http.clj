(ns clj-api.interop.dotnet.http
  (:import [Microsoft.AspNetCore.Http HttpRequestRewindExtensions]
           [Microsoft.AspNetCore.Http
            HttpContext
            HttpRequest
            HttpResponseWritingExtensions
            RequestDelegate]
           [System.IO StreamReader]
           [System.Text.Json JsonDocumentOptions JsonSerializer JsonSerializerOptions]
           [System.Text.Json.Nodes JsonNode]
           [System.Threading CancellationTokenSource]
           [System.Threading CancellationTokenSource]
           [System.Threading.Tasks Task]))

(def json-document-options (new JsonDocumentOptions))

(def json-serializer-options (new JsonSerializerOptions))

;; TODO convert any clojure keywords to strings. 
(defn format-body [v])

(defn stream->json-object [stream]
  (System.Text.Json.Nodes.JsonNode/Parse stream nil json-document-options))

(defn ^JsonNode get-request-body-json-object [^HttpRequest request]
  (when (= "POST" (.Method request))
  (let [_ (HttpRequestRewindExtensions/EnableBuffering request)
        body (-> request .Body)
        _ (set! (.Position body) 0)]
;; TODO this is blocking and why we need to configure Kestrel to allow sync io.
    (-> (new StreamReader body)
        (.ReadToEnd)
        (stream->json-object)))))

(defn add-request-json-body [context]
  (let [http-context ^HttpContext (:http/context context)
        request (.Request http-context)
        json-object (get-request-body-json-object request)]
    (-> context
        (assoc :request/raw-json-object json-object))))

(defn ->json-string [v]
  (def v v)
  v
  (JsonSerializer/Serialize v (type v) json-serializer-options))

#_{:clj-kondo/ignore [:unresolved-symbol]}
(defn request-handler [f]
  (gen-delegate RequestDelegate [^HttpContext http-context]
                (Task/Run (gen-delegate System.Action []
                                        (let [raw-request (.Request http-context)
                                              handler-response (-> {:http/context http-context}
                                                                   add-request-json-body
                                                                   f)
                                              _ (def handler-response handler-response)
                                              raw-response (.Response http-context)
                                              cancellation-token-source (new CancellationTokenSource)
                                              token (.Token cancellation-token-source)
                                              _ (set! (.-StatusCode raw-response) ^Int32 (-> handler-response :response/status))
                                              _ (set! (.-ContentType raw-response) "application/json")
                                              body (-> handler-response
                                                       :response/body
                                                       ->json-string)]
                                          (HttpResponseWritingExtensions/WriteAsync raw-response body token))))))