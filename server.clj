
(def fs (require "fs"))
(def http (require "http"))
(def url (require "url"))
(def querystring (require "querystring"))

(def todos [{id (+ (Math.random) "")
	         message "do morning exercises."
	         completed false}
	         {id (+ (Math.random) "")
	         message "buy gift for mom"
	         completed false}
	         {id (+ (Math.random) "")
	         message "do homework."
	         completed false}])

(console.log todos)

(def server 
  (http.createserver 
    (fn [req res] 
      (def filepath (+ "./public" req.url))
      (fs.readFile filepath (fn [err data]
                  (if err
                     (do (assign res.statusCode 404)
                         (res.end "Page not found")))
	              (assign res.statusCode 200)
	              (res.end data)))
	  (def parsedUrl (url.parse req.url))
	  (def parsedQuery (querystring.parse parsedUrl.query))
	  (def method req.method)
	  (if (== method "GET")
	      (if (= (request.url.indexOf "/todos") 0)
	          (do (let localTodos todos)
	              (res.setHeader "Content-Type" "application/json")
	              (if parsedQuery.searchtext
	                  (do (assign localTodos (localTodos.filter (fn [obj]
	                      (def temp (obj.message.toLowerCase))
	                      (>= (temp.indexOf (parsedQuery.searchtext.toLowerCase)) 0))
	                                		     	 	 )))
	                  (res.end (JSON.stringfy localTodos))))
	          (res.end (JSON.stringfy localTodos)))
	      (res.end (JSON.stringfy localTodos)))
	  (if (== method "POST")
	  	   (if (= (request.url.indexOf "/todos") 0)
	  	   	   (do (let body "")
	  	   	   	    (req.on "data" (fn [chunk]
	  	   	   	    	               (assign body (+ body chunk))))
	  	   	   	    (req.on "end" (fn []
	  	   	   	    	              (let jsonObj (JSON.parse body))
	  	   	   	    	              (if (jsonObj.message)
	  	   	   	    	              	  (do (assign jsonObj.id (+ (Math.random) ""))
	  	   	   	    	              	  	  (assign)
	  	   	   	    	              	  	  (res.setHeader "Content-Type" "application/json")
	  	   	   	    	              	  	  (res.end (JSON.stringfy (todos)))
	  	   	   	    	              	  	  ))))
	  	   	   	    )))
	  (if (== method "DELETE")
	  	  (if (= (request.url.indexOf "/todos") 0)
	  	  	  (do (let id (req.url.substr 7))                 
	  	  	  	)
	  	  	))

	)))

(server.listen 3001)









