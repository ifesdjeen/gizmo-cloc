(ns gizmo-cloc.snippets.main
  (:require [net.cgrand.enlive-html :as html]
            [gizmo-cloc.routes :as routes]
            [clojurewerkz.gizmo.widget :refer [defwidget trace]]
            [clojurewerkz.gizmo.enlive :refer [defsnippet within replace-vars]]))

(defmacro interpolate-many
  [entries]
  `(html/clone-for [entry# ~entries]
                   [html/any-node] (replace-vars entry#)))

(defsnippet index-snippet "templates/main/index.html"
  [*libraries-snippet]
  [libraries]
  (within *libraries-list [*libraries-list-item]) (interpolate-many libraries))

(defsnippet library-namespaces-snippet "templates/main/index.html"
  [*namespaces-snippet]
  [{:keys [library-name namespaces]}]
  (within *namespaces-list [*namespaces-list-item]) (interpolate-many namespaces))

(defsnippet namespace-public-var-list "templates/main/index.html"
  [*namespace-public-vars-list]
  [publics]
  (within *namespace-public-vars-list [*namespace-public-vars-list-item])
  (interpolate-many publics))

(defsnippet library-description-snippet "templates/main/index.html"
  [*library-description]
  [namespaces]
  (within *namespace-list [*namespace-list-item])
  (html/clone-for [{:keys [name docs] :as all} namespaces]
                  [html/any-node] (replace-vars all)
                  [*ns-docstring] (when-let [doc (:doc docs)]
                                    (html/content doc))
                  [*namespace-public-vars-list] (html/substitute
                                                 (namespace-public-var-list (:publics docs)))))


(defsnippet library-namespace-docs-snippet "templates/main/index.html"
  [*namespace-docs]
  [{:keys [library-name namespace docs]}]
  [*ns-docstring] (when-let [doc (:doc docs)]
                    (html/content doc))
  (within *docstring-list [*docstring-list-item])
  (let [{:keys [publics]} docs]
    (html/clone-for [{:keys [doc file name source] :as all} publics]
                    [html/any-node] (replace-vars all)
                    [*code] (when (not (empty? source))
                              identity))))

(defsnippet search-snippet "templates/main/index.html"
  [*search]
  [search-results]
  (within *search-result-list [*search-result-list-item])
  (html/clone-for [{:keys [lib namespace name docs source] :as all} search-results]
                  [html/any-node]
                  (replace-vars {:lib            lib
                                 :library-path   (routes/library-path :library lib)
                                 :namespace      namespace
                                 :namespace-path (routes/namespace-path :library lib
                                                                        :namespace namespace)
                                 :name           name
                                 :docs           docs
                                 :source         source}))
  [*search-result-list] (if (empty? search-results)
                          (html/substitute "No matches for your search request")
                          identity))
