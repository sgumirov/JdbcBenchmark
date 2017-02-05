DELETE FROM test_table;
explain analyze SELECT public.fillTableS(100);
explain analyze SELECT public.randomReadS(100000);
