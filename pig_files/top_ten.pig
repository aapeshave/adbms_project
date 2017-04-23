raw = LOAD 'input_top10/top-10' USING PigStorage(',') AS (asin:chararray, average:float);

ordered_list = ORDER raw BY average DESC;

top_10_items = LIMIT ordered_list 10;

--DUMP top_10_items

STORE top_10_items INTO 'out_top_10' USING PigStorage();