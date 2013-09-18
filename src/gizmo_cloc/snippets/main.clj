(ns gizmo-cloc.snippets.main
  (:require [net.cgrand.enlive-html :as html]
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

(defsnippet namespace-public-var-list "templates/main/index.html"
  [*namespace-public-vars-list]
  [library namespace publics]
  (within *namespace-public-vars-list [*namespace-public-vars-list-item])
  (html/clone-for [public publics]
                  [html/any-node] (html/replace-vars {:public-link (str (routes/namespace-path :library library
                                                                                            :namespace namespace) "#" (:name public))
                                                      :public-name (name (:name public))})))

(defsnippet library-description-snippet "templates/main/index.html"
  [*library-description]
  [{:keys [library-name namespaces]}]
  (within *namespace-list [*namespace-list-item])
  (html/clone-for [{:keys [name docs]} namespaces]
                  [html/any-node] (html/replace-vars {:namespace-path (routes/namespace-path :library library-name
                                                                                             :namespace name)
                                                      :namespace-name name})
                  [*ns-docstring] (when-let [doc (:doc docs)]
                                    (html/content doc))
                  [*namespace-public-vars-list] (html/substitute
                                                 (namespace-public-var-list library-name name (:publics docs)))))


(defsnippet library-namespace-docs-snippet "templates/main/index.html"
  [*namespace-docs]
  [{:keys [library-name namespace docs]}]
  [*ns-docstring] (when-let [doc (:doc docs)]
                    (html/content doc))
  (within *docstring-list [*docstring-list-item])
  (let [{:keys [publics]} docs]
    (html/clone-for [{:keys [doc file name source] :as all} publics]
                    [html/any-node]
                    (html/replace-vars {:doc (or doc "")
                                        :file (or file "")
                                        :name (str name)
                                        :source source})
                    [*code] (if (empty? source)
                              nil
                              identity))))

(defsnippet search-snippet "templates/main/index.html"
  [*search]
  [search-results]
  (within *search-result-list [*search-result-list-item])
  (html/clone-for [{:keys [lib namespace name docs source] :as all} search-results]
                  [html/any-node]
                  (html/replace-vars {:lib       (or lib "")
                                      :library-path (routes/library-path :library lib)
                                      :namespace (or namespace "")
                                      :namespace-path (routes/namespace-path :library lib
                                                                             :namespace namespace)
                                      :name      (or name "")
                                      :docs      (or docs "")
                                      :source    source}))
  [*search-result-list] (if (empty? search-results)
                          (html/substitute "No matches for your search request")
                          identity))
