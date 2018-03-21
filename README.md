# ventas-bidi-syntax

[![License](https://img.shields.io/badge/License-EPL%201.0-red.svg)](https://opensource.org/licenses/EPL-1.0)
[![Clojars Project](https://img.shields.io/clojars/v/joelsanchez/ventas-bidi-syntax.svg)](https://clojars.org/joelsanchez/ventas-bidi-syntax)

A nicer syntax for bidi, developed for [ventas](https://github.com/JoelSanchez/ventas)

## Overview

```clojure
(require '[ventas-bidi-syntax.core :as s])
(s/to-bidi [{:handler :admin
             :url "admin"}
            {:handler :admin.products
             :url "products"}
            {:handler :admin.products.edit
             :url [:id "/edit"]}])
```

Will result in:

```clojure
[""
 {true :not-found,
  "/admin" {"" :admin,
            "/products" {"" :admin.products,
                         ["/" :id "/edit"] {"" :admin.products.edit}}}}]
```
As you can see, it nests the routes by splitting `:handler` by the dots:

```clojure
(s/route-parents :admin.products.edit)
;; [:admin :admin.products]
```

Composite handlers are supported, as shown. Invalid routes will throw an error with [expound](https://github.com/bhb/expound)

```clojure
(to-bidi [{:invalid true}])
;;
Uncaught Error: -- Spec failed --------------------

  [:invalid true]
  ^^^^^^^^^^^^^^^

should satisfy

  map?

-- Relevant specs -------

:ventas-bidi-syntax.core/route:
  (cljs.spec.alpha/keys
   :req-un
   [:ventas-bidi-syntax.core/handler :ventas-bidi-syntax.core/url])
:ventas-bidi-syntax.core/routes:
  (cljs.spec.alpha/coll-of :ventas-bidi-syntax.core/route)

-------------------------
```



## License

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
