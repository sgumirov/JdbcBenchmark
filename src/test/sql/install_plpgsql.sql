CREATE OR REPLACE FUNCTION fillTableS(num integer)
  RETURNS text AS
$$
DECLARE
  StartTime timestamptz;
  EndTime timestamptz;
  Delta interval;
  ret text;
BEGIN
  StartTime := clock_timestamp();

  IF num > 0 THEN
    FOR i IN 1 .. num LOOP
      INSERT INTO test_table(id,val) VALUES(i, i);
    END LOOP;
  END IF;
      
  EndTime := clock_timestamp();
  --Delta := 1000 * ( extract(epoch from EndTime) - extract(epoch from StartTime) );
    Delta := clock_timestamp() - StartTime;

  --RAISE NOTICE 'Duration in millisecs=%', Delta;
  
  ret := 'Duration in millisecs=' || Cast(Delta As text);
      
  RETURN ret; 
END;
$$
LANGUAGE 'plpgsql' VOLATILE;

CREATE OR REPLACE FUNCTION randomReadS(num integer)
  RETURNS text AS
$$
DECLARE
  StartTime timestamptz;
  EndTime timestamptz;
  Delta interval;
  ret text;
  ri integer;
  v integer;
  avg integer := 0;
BEGIN
  StartTime := clock_timestamp();

  IF num > 0 THEN
    FOR i IN 1 .. num LOOP
      --INSERT INTO test_table(id,val) VALUES(i, i);
      ri := round(random()*99)+1;
      SELECT val INTO v FROM test_table WHERE id = ri;
      avg := (avg + v) / 2;
      v := i;
    END LOOP;
  END IF;
      
  EndTime := clock_timestamp();
  --Delta := 1000 * ( extract(epoch from EndTime) - extract(epoch from StartTime) );
  Delta := clock_timestamp() - StartTime;
  --RAISE NOTICE 'Duration in millisecs=%', Delta;
  
  ret := 'Total ops = '||v||'. Average = ' || avg || '. Duration in millisecs = ' || Cast(Delta As text);
      
  RETURN ret; 
END;
$$
LANGUAGE 'plpgsql' VOLATILE;
