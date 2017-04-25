raw = LOAD '/project/out_summarization_reviews/part-r-00000' USING PigStorage(',') AS (asin:chararray, average:float);

--raw = LOAD '/Users/ajinkya/Downloads/pigtmp/input_top10/top-10' USING PigStorage(',') AS (asin:chararray, average:float);
----------------------------	FIRST JOIN -------------------------------------------- 

toJoin = LOAD '/project/out_top10_by_category/final/part-r-00010' USING PigStorage('\t') AS (category:chararray, asin:chararray);

--toJoin = LOAD '/Users/ajinkya/Downloads/pigtmp/input_top10/part-r/part-r-00010' USING PigStorage('\t') AS (category:chararray, asin:chararray);
joinedList = JOIN raw BY asin, toJoin BY asin;
ordered_list = ORDER joinedList BY average DESC;
top_10_items_first = LIMIT ordered_list 10;

STORE top_10_items_first INTO '/pig_temp/part-0-out' USING PigStorage();

--STORE top_10_items_first INTO '/Users/ajinkya/Downloads/pigtmp/top_10_out' USING PigStorage();
--STORE raw INTO '/pig_join' USING PigStorage();
