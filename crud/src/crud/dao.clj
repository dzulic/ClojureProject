(ns crud.dao
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]))


(def db
  {:dbtype   "postgresql"
   :dbname   "image_fixer"
   :schema   "image_fixer"
   :host     "localhost"
   :port     5432
   :user     "postgres"
   :password "postgres"
   })


(defn create-user-sql [] (jdbc/create-table-ddl :image_fixer.user [[:id :serial "PRIMARY KEY"]
                                                                      [:name "VARCHAR(32)"]
                                                                      [:password "VARCHAR(32)"]
                                                                      [:username "VARCHAR(32)"]
                                                                      [:email "VARCHAR(32)"]
                                                                      [:dateOfBirth "VARCHAR(32)"]
                                                                      ]))

(defn create-image-sql [] (jdbc/create-table-ddl :image_fixer.image [[:id :serial "PRIMARY KEY"]
                                                                        [:filename "VARCHAR(255)"]
                                                                        [:value "BYTEA"]
                                                                        [:userid "VARCHAR(32)"]
                                                                        [:contenttype "VARCHAR(32)"]
                                                                        ]))

(defn db-schema-migrated?
  "Check if the schema has been migrated to the database"
  []
  (-> (jdbc/query db [(str "SELECT EXISTS (SELECT FROM information_schema.tables  WHERE  table_schema = 'image_fixer'   AND    table_name   = 'image'  )")])
      first :exists)
  )

(defn apply-schema-migration
  "Apply the schema to the database"
  []
  (when (not (db-schema-migrated?))
    (do
      (jdbc/db-do-commands db (create-user-sql))
      (jdbc/db-do-commands db (create-image-sql))
      )
    )
  )
(apply-schema-migration)

(defn user-create [user]
  (jdbc/insert! db :image_fixer.user {:name        (user :name)
                                         :password    (user :password)
                                         :username    (user :username)
                                         :email       (user :email)
                                         :dateOfBirth (user :dateOfBirth)}))

(defn user-login [user pass]
  (first (jdbc/query db (string/join "" ["SELECT * FROM image_fixer.user WHERE username = '" user "' AND password = '" pass "'"]))))

(defn image-create [img]
  (println img)
  (jdbc/insert! db :image_fixer.image {:filename    (img :filename)
                                          :value       (img :value)
                                          :userid      (img :userid)
                                          :contenttype (img :contenttype)
                                          }))

(defn get-image [id]
  (first (jdbc/query db (string/join ""["SELECT value, id, filename FROM image_fixer.image WHERE id = "id""]))))

(defn get-all-images [userId]
  (jdbc/query db (string/join "" ["SELECT value, id FROM image_fixer.image WHERE userId = '" userId "'"])))
