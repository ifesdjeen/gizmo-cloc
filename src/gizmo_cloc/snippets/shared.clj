(ns gizmo-cloc.snippets.shared
  (:require [net.cgrand.enlive-html :as html]
            [clojurewerkz.gizmo.enlive :refer [defsnippet]]))

(defsnippet header-snippet "templates/shared/header.html"
  [*header]
  [query-string]
  [*search-box] (fn [node]
                  (assoc-in node [:attrs :value] query-string)))
