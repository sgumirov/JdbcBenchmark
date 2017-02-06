SELECT sqlj.replace_jar('file:/tmp/JdbcBenchmark-1.0-SNAPSHOT.jar', 'benchmark', true);
SELECT sqlj.set_classpath('public', 'benchmark');
DROP TABLE IF EXISTS test_table;
CREATE TABLE test_table (id INTEGER PRIMARY KEY, val integer);
CREATE INDEX test_table_idx ON test_table(id);
