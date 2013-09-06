(ns gizmo-cloc.routes
    (:use [clojurewerkz.route-one.compojure])
    (:require [compojure.core :as compojure]
              [compojure.route :as route]))

(compojure/defroutes main-routes
  (GET root        "/"                             request (gizmo-cloc.handlers.main/index request))
  (GET search      "/search"                       request (gizmo-cloc.handlers.main/search request))
  (GET library     "/libs/:library"                request (gizmo-cloc.handlers.main/library-show request))
  (GET namespace   "/libs/:library/nss/:namespace" request (gizmo-cloc.handlers.main/namespace-show request))
  (GET favicon     "/favicon.ico"                  _       (fn [_] {:render :nothing}))
  (route/not-found "Page not found"))
