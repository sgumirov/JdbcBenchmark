CREATE OR REPLACE FUNCTION fillTableS(num integer)
  RETURNS text AS
$$
DECLARE
  StartTime timestamptz;
  EndTime timestamptz;
  Delta interval;
BEGIN
  StartTime := clock_timestamp();

  IF num > 0 THEN
    FOR i IN 1 .. num LOOP
      INSERT INTO test_table(id,val) VALUES(i, i);
    END LOOP;
  END IF;
      
  EndTime := clock_timestamp();
  Delta := 1000 * ( extract(epoch from EndTime) - extract(epoch from StartTime) );
  --RAISE NOTICE 'Duration in millisecs=%', Delta;
  
  ret := 'Duration in millisecs=' || Cast(Delta As text);
      
  RETURN 
END;
$$
LANGUAGE 'plpgsql' VOLATILE;
