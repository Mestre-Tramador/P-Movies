#! /bin/sh

(./gradlew -t :bootWar) &

./gradlew bootRun -PskipDownload=true
