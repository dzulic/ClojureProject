(ns crud.dao
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.string :as string]))


(def db
  {:dbtype   "postgresql"
   :dbname   "image_analyser"
   :schema   "image_analyser"
   :host     "localhost"
   :port     5432
   :user     "postgres"
   :password "postgres"
   })


(defn create-user-sql [] (jdbc/create-table-ddl :image_analyser.user [[:id :serial "PRIMARY KEY"]
                                                                      [:name "VARCHAR(32)"]
                                                                      [:password "VARCHAR(32)"]
                                                                      [:username "VARCHAR(32)"]
                                                                      [:email "VARCHAR(32)"]
                                                                      [:dateOfBirth "VARCHAR(32)"]
                                                                      ]))


(defn db-schema-migrated?
  "Check if the schema has been migrated to the database"
  []
  (-> (jdbc/query db
                  [(str "select count(*) from information_schema.tables "
                        "where table_name='user'")])
      first :count pos?))

(defn apply-schema-migration
  "Apply the schema to the database"
  []
  (when (not (db-schema-migrated?))
    (jdbc/db-do-commands db (create-user-sql))))

(apply-schema-migration)

(defn user-create [user]
  (jdbc/insert! db :image_analyser.user {:name        (user :name)
                                         :password    (user :password)
                                         :username    (user :username)
                                         :email       (user :email)
                                         :dateOfBirth (user :dateOfBirth)}))

(defn user-login [user]
  (first (jdbc/query db (string/join "" ["SELECT * FROM image_analyser.user WHERE username = '" (user :username) "' AND password = '" (user :password) "'"]))))
