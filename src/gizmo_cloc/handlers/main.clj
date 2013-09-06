(ns gizmo-cloc.handlers.main)

(defn index
  [request]
  {:render :html
   :widgets {:left-content 'gizmo-cloc.widgets.main/index-content
             :middle-content 'gizmo-cloc.widgets.main/empty-widget
             :main-content 'gizmo-cloc.widgets.main/empty-widget
             }})

(defn library-show
  [request]
  {:render :html
   :library-name (get-in request [:route-params :library])
   :widgets {:left-content 'gizmo-cloc.widgets.main/index-content
             :middle-content 'gizmo-cloc.widgets.main/library-namespaces
             :main-content 'gizmo-cloc.widgets.main/empty-widget}})

(defn namespace-show
  [request]
  {:render :html
   :library-name (get-in request [:route-params :library])
   :namespace (get-in request [:route-params :namespace])
   :widgets {:left-content 'gizmo-cloc.widgets.main/index-content
             :middle-content 'gizmo-cloc.widgets.main/library-namespaces
             :main-content  'gizmo-cloc.widgets.main/library-namespace-docs
             }})
