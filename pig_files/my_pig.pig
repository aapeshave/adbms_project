raw = LOAD '/pig_tutorial/reviews_test.json' 
 	USING JsonLoader('reviewerID:chararray, 
                      asin:chararray');
STORE raw 
    INTO '/pig_tutorial/first_table.json' 
    USING JsonStorage();