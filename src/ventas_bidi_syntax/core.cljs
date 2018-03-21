(ns ventas-bidi-syntax.core
  (:require
   [clojure.string :as str]
   [expound.alpha :as expound]
   [cljs.spec.alpha :as spec]))

(defn route-parents
  ":admin.users.something -> [:admin :admin.users]"
  [handler]
  (->> (drop-last (str/split (name handler) #"\."))
       (reduce (fn [acc i]
                 (conj acc (conj (vec (last acc)) i)))
               [])
       (mapv #(keyword (str/join "." %)))))

(defn- index-urls
  "Creates a [route -> url] map"
  [routes]
  (->> routes
       (map (fn [{:keys [handler url]}]
              [handler url]))
       (into {})))

(defn- update-urls [routes]
  (let [indexed-urls (index-urls routes)]
    (map (fn [{:keys [handler] :as route}]
           (let [parent (last (route-parents handler))
                 parent-url (indexed-urls parent)]
             (update route :url #(cond
                                   (= parent-url "") %1
                                   (string? %1) (str "/" %1)
                                   :else (into ["/"] %)))))
         routes)))

(defn- accumulate-route [indexed-urls acc {:keys [handler url]}]
  (let [parents (route-parents handler)]
    (update-in acc
               (conj (mapv indexed-urls parents) url)
               #(assoc % "" handler))))

(spec/def ::handler keyword?)

(spec/def ::url
  (spec/or :composite sequential?
           :simple string?))

(spec/def ::route
  (spec/keys :req-un [::handler
                      ::url]))

(spec/def ::routes
  (spec/coll-of ::route))

(defn spec-check! [& args]
  (when-not (apply spec/valid? args)
    (throw (js/Error. (with-out-str (apply expound/expound args))))))

(defn to-bidi [routes]
  (spec-check! ::routes routes)
  (let [routes (update-urls routes)
        indexed-urls (index-urls routes)]
    ["" (reduce (partial accumulate-route indexed-urls)
                {true :not-found}
                routes)]))
