(defproject gizmo-cloc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clojurewerkz/gizmo "0.1.0-SNAPSHOT"]
                 [org.clojure/tools.nrepl "0.2.3"]
                 [org.clojure/tools.cli "0.2.2"]
                 [cloc/indexer "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]}}
  :source-paths ["src"]
  :resource-paths ["resources"]
  :main gizmo-cloc.cli-entrypoint)
