(ns gizmo-cloc.widgets.main
  (:require [clojurewerkz.gizmo.widget :refer [defwidget]]
            [gizmo-cloc.snippets.main :as snippets]
            [gizmo-cloc.entities :as entities]))

(defwidget libraries
  :view snippets/index-snippet
  :fetch (fn [_] (entities/libraries)))

(defwidget library-namespaces
  :view snippets/library-namespaces-snippet
  :fetch (fn [{:keys [library-name]}]
           {:library-name library-name
            :namespaces   (entities/namespaces library-name)}))

(defwidget library-description
  :view snippets/library-description-snippet
  :fetch (fn [{:keys [library-name]}]
           {:library-name library-name
            :namespaces   (map
                           (fn [n] {:name n :docs (entities/docs library-name n)})
                           (entities/namespaces library-name))}))

(defwidget library-namespace-docs
  :view snippets/library-namespace-docs-snippet
  :fetch (fn [{:keys [library-name namespace]}]
           {:library-name library-name
            :namespace    namespace
            :docs         (entities/docs library-name namespace)}))

(defwidget search
  :view snippets/search-snippet
  :fetch (fn [{:keys [q]}]
           (entities/search q)))

(defwidget empty-widget)
