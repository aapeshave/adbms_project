raw = LOAD 'metadata.json'
 	USING JsonLoader('asin:chararray,
                      categories:{
					  	(category:chararray)
					  }');
STORE raw 
    INTO 'myCategories.json' 
    USING JsonStorage();