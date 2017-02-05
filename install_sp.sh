~/maven/bin/mvn clean package
cp /home/user1/projects/JdbcBenchmark/target/JdbcBenchmark-1.0-SNAPSHOT.jar /tmp/
psql test < src/test/sql/install.sql
