(ns gizmo-cloc.snippets.shared
  (:require [net.cgrand.enlive-html :as html]
            [clojurewerkz.gizmo.enlive :refer [defsnippet]]))

(defsnippet header-snippet "templates/shared/header.html"
  [*header]
  [env])
