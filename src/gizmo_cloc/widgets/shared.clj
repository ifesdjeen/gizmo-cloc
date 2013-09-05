(ns gizmo-cloc.widgets.shared
  (:require [clojurewerkz.gizmo.widget :refer [defwidget]]
            [gizmo-cloc.snippets.shared :as shared]))

(defwidget header
  :view shared/header-snippet
  :fetch (fn [_]))
