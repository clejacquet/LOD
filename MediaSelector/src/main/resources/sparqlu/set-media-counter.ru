PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ms:media_counter ms:has_value ?old_media_count_value
} WHERE {
    ms:media_counter ms:has_value ?old_media_count_value
};

INSERT DATA {
    ms:media_counter ms:has_value ?media_count_value
}