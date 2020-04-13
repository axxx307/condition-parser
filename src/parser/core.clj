(ns parser.core
  (:gen-class)
  (:require [clojure.data.json :as json]
            [clojure.core.match :refer [match]]))

(def starting-selection "{\"model\":\"test\",\"conditions\":{\"matchType\":\"all\",\"groups\":{\"byId\":{\"initial_group\":{\"id\":\"initial_group\",\"matchType\":\"all\",\"filterGroups\":[\"default_filter_group\"]}},\"allIds\":[\"initial_group\"]},\"filterGroups\":{\"byId\":{\"default_filter_group\":{\"id\":\"default_filter_group\",\"matchType\":\"all\",\"filters\":[\"initial_filter\",\"87772922-b8c1-4feb-8356-0d7f925a4310\"]}},\"allIds\":[\"default_filter_group\"]},\"filters\":{\"byId\":{\"initial_filter\":{\"id\":\"initial_filter\",\"field\":\"name\",\"fieldValue\":\"specific\",\"conditionAction\":\"eq\",\"conditionValue\":\"Test\"},\"87772922-b8c1-4feb-8356-0d7f925a4310\":{\"id\":\"87772922-b8c1-4feb-8356-0d7f925a4310\",\"field\":\"notes\",\"fieldValue\":\"specific\",\"conditionAction\":\"neq\",\"conditionValue\":\"test\"}},\"allIds\":[\"initial_filter\",\"87772922-b8c1-4feb-8356-0d7f925a4310\"]}},\"shouldSkipChecks\":false}")
(def entity "{\"name\": \"Test\"}")

(def parse #(json/read-str %1 :key-fn keyword))

(defn perform-action [argument {:keys [field conditionAction conditionValue]}]
  (let [field-keyword (keyword field)
        result (match [conditionAction]
                      ["eq"] (= (field-keyword argument) conditionValue)
                      ["neq"] (not (= (field-keyword argument) conditionValue)))] result))

(defn evaluate-filters [argument filters]
  (map
    (fn [[k v]] (let [evaluation-result (perform-action argument v)] {k evaluation-result}))
    filters))

(defn evaluate-by-matchtype [matchType filters]
  (if (= matchType "all")
    (every? #(vals %1) filters)
    (some #(vals %1) filters)))

(defn evaluate-groups
  "Evaluate groups. Takes last evaluated group (filters, etc), parent groups
  and keyword to extract filters"
  [evaluated-filters groups fl-keyword]
  (map
    (fn [group]
      (let [connected-filters (filter
                                (fn [fl]
                                  (< -1 (.indexOf (fl-keyword (last group)) (name (first (keys fl))))))
                                evaluated-filters)
            result (evaluate-by-matchtype (:matchType (last group)) connected-filters)
            ] {(first group) result}))
    (:byId groups))
  )

(defn evaluate-condition
  "Evaluate condition based on schema"
  [{:keys [conditions]} argument]
  (let [{:keys [filters filterGroups groups matchType]} conditions
        filters-byId (seq (:byId filters))
        evaluated-filters (evaluate-filters argument filters-byId)
        evaluatedFilterGroups (evaluate-groups evaluated-filters filterGroups :filters)
        evaluatedGroups (evaluate-groups evaluatedFilterGroups groups :filterGroups)
        result (evaluate-by-matchtype matchType evaluatedGroups)
        ] result))

(defn -main
  "optional [starting-selection entity]
  Parse entity and evaluate all of the filters"
  [& args]
  (->
    (parse (or (first args) starting-selection))
    (evaluate-condition (parse (or (second args) entity)))
    (println)))