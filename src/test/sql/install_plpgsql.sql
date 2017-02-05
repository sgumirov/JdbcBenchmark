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
  Delta := clock_timestamp() - StartTime;

  ret := 'Duration in millisecs=' || Cast(Delta As text);
      
  RETURN ret; 
END;
$$
LANGUAGE 'plpgsql' VOLATILE;

CREATE OR REPLACE FUNCTION randomReadS(numb integer, maxnum integer)
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
    FOR i IN 1 .. numb LOOP
      --INSERT INTO test_table(id,val) VALUES(i, i);
      ri := round(random()*(maxnum-1))+1;
      SELECT val INTO v FROM test_table WHERE id = ri;
      avg := (avg + v) / 2;
      v := i;
    END LOOP;
  END IF;
      
  EndTime := clock_timestamp();
  Delta := clock_timestamp() - StartTime;
  
  ret := 'Total ops = '||v||'. Average = ' || avg || '. Duration in millisecs = ' || Cast(Delta As text);
      
  RETURN ret; 
END;
$$
LANGUAGE 'plpgsql' VOLATILE;
