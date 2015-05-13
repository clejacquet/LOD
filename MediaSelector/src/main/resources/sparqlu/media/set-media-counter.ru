PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media_counter ms:has_value ?media_count_value
}
INSERT {
    ?media_counter ms:has_value ?new_count_value
}
WHERE {
    ?media a ms:media .
    ?media ms:has_counter ?media_counter .
    ?media_counter ms:has_value ?media_count_value
}