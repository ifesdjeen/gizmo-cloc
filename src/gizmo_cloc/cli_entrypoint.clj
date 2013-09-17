(ns gizmo-cloc.cli-entrypoint
  (:gen-class)
  (:require [clojure.tools.cli :refer [cli]]
            [compojure.handler :refer [api]]
            [clojurewerkz.gizmo
             [core :refer [require-widgets require-snippets require-handlers
                           require-services register-snippet-reload]]
             [config :refer [load-config! settings]]
             [service :refer [start-all! all-services]]]
            [gizmo-cloc.entities :as entities]
            [gizmo-cloc.entities.search :as search]))

(alter-var-root #'*out* (constantly *out*))

(defn entrypoint
  [args]
  (let [[options positional-args banner] (cli args
                                              ["--config" "Path to configuration file to use"])]
    (load-config! (or (:config options) "config/development.clj")))
  (require-handlers "gizmo-cloc")
  (require-snippets "gizmo-cloc")
  (require-widgets "gizmo-cloc")
  (require-services "gizmo-cloc")
  (register-snippet-reload "gizmo-cloc")

  (entities/init-with-libs! (:libs settings))
  (search/init-search-index! @entities/index)
  (start-all!))

(defn -main
  [& args]
  (entrypoint args))
