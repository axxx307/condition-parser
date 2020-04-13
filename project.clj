(defproject parser "0.1.0-SNAPSHOT"
  :description "Simple parser for specific Redux state"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/core.match "1.0.0"]]
  :main ^:skip-aot parser.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
