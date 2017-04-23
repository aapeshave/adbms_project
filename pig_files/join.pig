raw = LOAD 'input_top10/top-10' USING PigStorage(',') AS (asin:chararray, average:float);

toJoin = LOAD 'input_top10/part-r/part-r-00010' USING 
	PigStorage('\t') AS (category:chararray, asin:chararray);

joinedList = JOIN raw BY asin, toJoin BY asin;

ordered_list = ORDER joinedList BY average DESC;

top_10_items_first = LIMIT ordered_list 10;

--DUMP top_10_items
--DUMP joinedList;

STORE top_10_items_first INTO 'out_top_10_first' USING PigStorage();

toJoin_second = LOAD 'input_top10/part-r/part-r-00123' 
	USING PigStorage('\t') AS (category:chararray, asin:chararray);

joinedList_second = JOIN raw BY asin, toJoin_second BY asin;

ordered_list_second = ORDER joinedList_second BY average DESC;

top_10_items_second = LIMIT ordered_list_second 10;

STORE top_10_items_second INTO 'out_top_10_second' USING PigStorage();