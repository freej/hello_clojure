(defproject helloworld "0.1"
    :dependencies [[org.clojure/clojure
                      "1.2.1"]
                   [org.clojure/clojure-contrib
                      "1.2.0"]
                   [org.apache.ant/ant
                      "1.8.2"]
                   [org.apache.ant/ant-launcher
                      "1.8.2"]
                   [clj-record 
                      "1.1.1"]
                   [mysql/mysql-connector-java "5.1.17"]]
    :main helloworld
    :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"})