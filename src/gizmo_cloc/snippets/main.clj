(ns gizmo-cloc.snippets.main
  (:require [net.cgrand.enlive-html :as html]
            [gizmo-cloc.routes :as routes]))

(html/defsnippet index-snippet "templates/main/index.html"
  [:ul#libraries]
  [libraries]
  [:ul#libraries [:li html/first-of-type]]
  (html/clone-for [library libraries]
                  [html/any-node] (html/replace-vars {:library-path (routes/library-path :library library)

                                                      :library-name library})))

(html/defsnippet library-namespaces-snippet "templates/main/index.html"
  [:ul#namespaces]
  [{:keys [library-name namespaces]}]
  [:ul#namespaces [:li html/first-of-type]]
  (html/clone-for [namespace namespaces]
                  [html/any-node] (html/replace-vars {:namespace-path (routes/namespace-path :library library-name
                                                                                             :namespace namespace)
                                                      :namespace-name namespace})))

(html/defsnippet library-namespace-docs-snippet "templates/main/index.html"
  [:div#namespace-docs]
  [{:keys [library-name namespace docs]}]
  [:ul [:li html/first-of-type]]
  (let [{:keys [publics doc]} docs]
    (html/clone-for [{:keys [doc file name]} publics]
                    [html/any-node] (html/replace-vars
                                     {:doc doc :file file :name (str name)}))))
