(ns gizmo-cloc.snippets.main
  (:require [net.cgrand.enlive-html :as html]
            [gizmo-cloc.routes :as routes]
            [clojurewerkz.gizmo.widget :refer [defselector]]))

(defn within
  [parent & children]
  (if (sequential? parent)
    (apply conj parent children)
    (apply conj [parent] children)))

(defn has-attr
 [attr]
 (html/pred #(not (nil? (-> % :attrs attr)))))

(defn snip
  [snippet-name]
  (html/pred #(= snippet-name (-> % :attrs :snippet))))

(defn format-selector
  [name]
  (format "*%s" name))

(defmacro defsnippet-wrapper
 "Define a named snippet -- equivalent to (def name (snippet source selector args ...))."
 [name source selector args & forms]
 (let [snippets (html/select (html/html-resource source) [(has-attr :snippet)])
       names    (apply concat (map #(vector
                                     (-> % :attrs :snippet format-selector symbol)
                                     (list 'snip (-> % :attrs :snippet))) snippets))]
   `(let* [~@names]
          (def ~name (html/snippet ~source ~selector ~args ~@forms)))))

(defsnippet-wrapper index-snippet "templates/main/index.html"
  [*libraries-list]
  [libraries]
  [*libraries-list-item]
  (html/clone-for [library libraries]
                  [html/any-node] (html/replace-vars {:library-path (routes/library-path :library library)
                                                      :library-name library})))

(defsnippet-wrapper library-namespaces-snippet "templates/main/index.html"
  [*namespaces-list]
  [{:keys [library-name namespaces]}]
  [*namespaces-list-item]
  (html/clone-for [namespace namespaces]
                  [html/any-node] (html/replace-vars {:namespace-path (routes/namespace-path :library library-name
                                                                                             :namespace namespace)
                                                      :namespace-name namespace})))

(defsnippet-wrapper library-namespace-docs-snippet "templates/main/index.html"
  [*namespace-docs]
  [{:keys [library-name namespace docs]}]
  (within *docstring-list [*docstring-list-item])
  (let [{:keys [publics doc]} docs]
    (html/clone-for [{:keys [doc file name]} publics]
                    [html/any-node] (html/replace-vars
                                     {:doc doc :file file :name (str name)}))))
