mvn -f ansible-quarkus-module/pom.xml clean install -Pnative
cp ansible-quarkus-module/target/ansible-quarkus-module-1.0.0-SNAPSHOT-runner ansible-quarkus-module-runner