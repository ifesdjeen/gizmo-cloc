(ns gizmo-cloc.handlers.library)

(defn show
  [request]
  {:render :html
   :widgets {:main-content 'gizmo-cloc.widgets.library/show}})
