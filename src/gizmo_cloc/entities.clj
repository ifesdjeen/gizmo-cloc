(ns gizmo-cloc.entities
  (:require [clojure.java.classpath :as cp]
            [clojure.repl :as repl]
            [cloc.indexer :as indexer]
            [cemerick.pomegranate :refer [add-dependencies]]
            [gizmo-cloc.entities.search :as search]))

(defonce index (atom ::unset))

(defn get-source
  [namespace name]
  (or
   (repl/source-fn
    (symbol (str namespace "/" name)))
   ""))

(defn inject-source
  [namespace {:keys [name] :as all}]
  (assoc all :source (get-source namespace name)))

(defn matches-libs?
  [libs jar]
  (let [path (.getPath jar)]
    (some (fn [[qualifier version]]
            (re-matches (re-pattern (str "(.*)" (name qualifier) "-" version ".jar")) path))
          libs)))
(defn init-with-libs!
  "Use cloc/indexer to generate an index from classpath."
  [libs]
  (add-dependencies :coordinates libs
                    :repositories (merge cemerick.pomegranate.aether/maven-central
                                         {"clojars" "http://clojars.org/repo"}))




  (reset! index (indexer/index-classpath (filter (partial matches-libs? libs) (cp/classpath)))))

(defn libraries
  []
  (indexer/libraries @index))

(defn docs
  "Docs for `namespace` of `library`"
  [library namespace]
  (update-in (indexer/docs @index library namespace)
             [:publics]
             #(map (partial inject-source namespace) %)))

(defn namespaces
  "Namespaces for certain library"
  [library]
  (filter #(let [docs (docs library %)]
             (or
              (not (empty? (:doc docs)))
              (not (empty? (:publics docs)))))
          (indexer/namespaces @index library)))

(defn search
  [query]
  (when (not (empty? query))
    (map (fn [{:keys [namespace] :as all}]
           (inject-source namespace all)) (search/search query))))
;; (init-index-from-classpath! (cp/classpath))
