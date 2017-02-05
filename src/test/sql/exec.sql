SELECT public.fillTable('test_table', 1, 100);
SELECT public.randomReads('test_table', 100000, 100);
SELECT public.cleanTable('test_table');
