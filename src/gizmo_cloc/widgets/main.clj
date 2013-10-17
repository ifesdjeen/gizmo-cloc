(ns gizmo-cloc.widgets.main
  (:require [clojurewerkz.gizmo.widget :refer [defwidget trace]]
            [gizmo-cloc.routes :as routes]
            [gizmo-cloc.snippets.main :as snippets]
            [gizmo-cloc.entities :as entities]
            [clojurewerkz.balagan.core :as balagan]
            clojure.walk))

(defn transform-library
  [library]
  {:library-path (routes/library-path :library library)
   :library-name library})

(defwidget libraries
  :view snippets/index-snippet
  :fetch (fn [_]
           (map (fn [x] (transform-library x)) (entities/libraries))))

(defn transform-namespace
  [library-name namespace]
  {:namespace-path (routes/namespace-path :library library-name
                                          :namespace namespace)
   :namespace-name namespace})

(defwidget library-namespaces
  :view snippets/library-namespaces-snippet
  :fetch (fn [{:keys [library-name]}]
           {:library-name library-name
            :namespaces   (map (partial transform-namespace library-name)
                               (entities/namespaces library-name))}))

(defwidget library-description
  :view snippets/library-description-snippet
  :fetch (fn [{:keys [library-name]}]
           (balagan/transform (entities/namespaces library-name)
                              [:*] (fn [n] {:namespace-name n
                                           :namespace-path (routes/namespace-path :library library-name
                                                                                  :namespace n)
                                           :docs (entities/docs library-name n)}))))

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
