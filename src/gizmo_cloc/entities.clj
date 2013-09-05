(ns gizmo-cloc.entities
  (:require [clojure.java.classpath :as cp]
            [cloc.indexer :as indexer]))

(defonce index (atom ::unset))

(defn init-index-from-classpath!
  "Use cloc/indexer to generate an index from classpath."
  []
  (reset! index (indexer/index-classpath (cp/classpath))))

(defn init-index-from-lein!
  "Initialise index state to given pre-generated index."
  [idx]
  (reset! index idx))

(defn libraries
  []
  (indexer/libraries @index))

(defn namespaces
  "Namespaces for certain library"
  [library]
  (indexer/namespaces @index library))

(defn docs
  "Docs for `namespace` of `library`"
  [library namespace]
  (indexer/docs @index library namespace))

;; (init-index-from-classpath! (cp/classpath))
