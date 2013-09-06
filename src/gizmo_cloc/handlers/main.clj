(ns gizmo-cloc.handlers.main)

(defn index
  [request]
  {:render :html
   :widgets {:left-content 'gizmo-cloc.widgets.main/libraries
             :middle-content 'gizmo-cloc.widgets.main/empty-widget
             :main-content 'gizmo-cloc.widgets.main/empty-widget
             }})

(defn library-show
  [request]
  {:render :html
   :library-name (get-in request [:route-params :library])
   :widgets {:left-content 'gizmo-cloc.widgets.main/libraries
             :middle-content 'gizmo-cloc.widgets.main/library-namespaces
             :main-content 'gizmo-cloc.widgets.main/empty-widget}})

(defn namespace-show
  [{:keys [route-params]}]
  {:render :html
   :library-name (:library route-params)
   :namespace (:namespace route-params)
   :widgets {:left-content 'gizmo-cloc.widgets.main/libraries
             :middle-content 'gizmo-cloc.widgets.main/library-namespaces
             :main-content  'gizmo-cloc.widgets.main/library-namespace-docs}})

(defn search
  [{:keys [query-params]}]
  (let [q (get-in query-params ["q"])]
    (assert q "Param can't be nil")
    {:render :html
     :widgets {:left-content 'gizmo-cloc.widgets.main/libraries
               :middle-content 'gizmo-cloc.widgets.main/empty-widget
               :main-content 'gizmo-cloc.widgets.main/search}
     :q q}))
