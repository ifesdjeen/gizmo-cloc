(ns gizmo-cloc.snippets.main
  (:require [net.cgrand.enlive-html :as html]
            clojure.repl
            [gizmo-cloc.routes :as routes]
            [clojurewerkz.gizmo.enlive :refer [defsnippet within]]))


(defsnippet index-snippet "templates/main/index.html"
  [*libraries-snippet]
  [libraries]
  (within *libraries-list [*libraries-list-item])
  (html/clone-for [library libraries]
                  [html/any-node] (html/replace-vars {:library-path (routes/library-path :library library)
                                                      :library-name library})))

(defsnippet library-namespaces-snippet "templates/main/index.html"
  [*namespaces-snippet]
  [{:keys [library-name namespaces]}]
  (within *namespaces-list [*namespaces-list-item])
  (html/clone-for [namespace namespaces]
                  [html/any-node] (html/replace-vars {:namespace-path (routes/namespace-path :library library-name
                                                                                             :namespace namespace)
                                                      :namespace-name namespace})))

(defsnippet library-namespace-docs-snippet "templates/main/index.html"
  [*namespace-docs]
  [{:keys [library-name namespace docs]}]
  [*ns-docstring] (html/content (:doc docs))
  (within *docstring-list [*docstring-list-item])
  (let [{:keys [publics]} docs]
    (html/clone-for [{:keys [doc file name] :as all} publics]
                    [html/any-node]
                    (html/replace-vars {:doc (or doc "")
                                        :file (or file "")
                                        :name (str name)
                                        :source (or
                                                 (clojure.repl/source-fn
                                                  (symbol (str namespace "/" name)))
                                                 "")}))))

(defsnippet search-snippet "templates/main/index.html"
  [*search]
  [search-results]
  (within *search-result-list [*search-result-list-item])
  (html/clone-for [{:keys [lib namespace name docs] :as all} search-results]
                  [html/any-node]
                  (html/replace-vars {:lib       (or lib "")
                                      :library-path (routes/library-path :library lib)
                                      :namespace (or namespace "")
                                      :namespace-path (routes/namespace-path :library lib
                                                                             :namespace namespace)
                                      :name      (or name "")
                                      :docs      (or docs "")
                                      :source (or
                                               (clojure.repl/source-fn
                                                (symbol (str namespace "/" name)))
                                               "")})))
