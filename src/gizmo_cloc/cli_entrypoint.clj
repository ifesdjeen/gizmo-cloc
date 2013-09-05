(ns gizmo-cloc.cli-entrypoint
  (:gen-class)
  (:require [clojure.tools.cli :refer [cli]]
            [compojure.handler :refer [api]]
            [clojurewerkz.gizmo
             [core :refer [require-widgets require-snippets require-handlers
                           require-services register-snippet-reload]]
             [config :refer [load-config!]]
             [service :refer [start-all! all-services]]]
            [gizmo-cloc.entities :as entities]))

(alter-var-root #'*out* (constantly *out*))

(defn -main
  [& args]
  (let [[options positional-args banner] (cli args
                                              ["--config" "Path to configuration file to use"])]
    (load-config! (:config options)))
  (require-handlers "gizmo-cloc")
  (require-snippets "gizmo-cloc")
  (require-widgets "gizmo-cloc")
  (require-services "gizmo-cloc")
  (register-snippet-reload "gizmo-cloc")

  (entities/init-index-from-classpath!)
  (start-all!))
