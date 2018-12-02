(defproject joelsanchez/ventas-bidi-syntax "0.1.3"

  :description "Human-optimized bidi routes"

  :url "https://github.com/JoelSanchez/ventas"

  :pedantic? :abort

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.9.0" :scope "provided"]
                 [org.clojure/clojurescript "1.9.946" :scope "provided"]
                 [expound "0.4.0"]
                 [org.clojure/core.async  "0.4.474"]]

  :plugins [[lein-figwheel "0.5.15"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :test-paths ["test"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {}
                :compiler {:main ventas-bidi-syntax.core
                           :parallel-build true
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/ventas_bidi_syntax.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               {:id "test"
                :source-paths ["src" "test"]
                :compiler {:output-to "resources/public/js/compiled/testable.js"
                           :main ventas-bidi-syntax.test-runner
                           :optimizations :none
                           :parallel-build true}}]}

  :figwheel {:nrepl-port 7888
             :repl false}

  :doo {:build "test"}

  :repositories {"releases" {:url "https://repo.clojars.org"
                             :creds :gpg}
                 "snapshots" {:url "https://repo.clojars.org"
                              :creds :gpg}}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [figwheel-sidecar "0.5.15" :exclusions [org.clojure/tools.nrepl]]
                                  [com.cemerick/piggieback "0.2.2"]]
                   :plugins [[lein-doo "0.1.8" :exclusions [org.clojure/clojure]]]
                   :source-paths ["src" "dev"]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
