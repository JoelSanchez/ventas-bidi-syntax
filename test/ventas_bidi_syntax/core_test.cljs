(ns ventas-bidi-syntax.core-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [ventas-bidi-syntax.core :as sut]))

(deftest to-bidi
  (testing "subroutes"
    (is (= ["" {"/about-us" {"" :about-us
                             "/our-team" {"" :about-us.our-team}
                             "/company" {"" :about-us.company
                                         "/london" {"" :about-us.company.london}}}
                "/faq" {"" :faq}}]
           (sut/to-bidi [{:handler :about-us
                          :url "about-us"}
                         {:handler :about-us.our-team
                          :url "our-team"}
                         {:handler :about-us.company
                          :url "company"}
                         {:handler :faq
                          :url "faq"}
                         {:handler :about-us.company.london
                          :url "london"}]))))

  (testing "composite handlers"
    (is (= [""
            {"/products" {"" :products
                          ["/" :id "/edit"] {"" :products.edit
                                             "/images" {"" :products.edit.images}}}}]
           (sut/to-bidi [{:handler :products
                          :url "products"}
                         {:handler :products.edit
                          :url [:id "/edit"]}
                         {:handler :products.edit.images
                          :url "images"}]))))

  (testing "invalid routes"
    (is (thrown? js/Error (sut/to-bidi [{:invalid true}])))))