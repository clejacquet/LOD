PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?old_media a ms:media;
               ms:has_name ?old_media_name;
               ms:has_end_point ?old_media_end_point;
               ms:has_id ?media_id
} WHERE {
    ?old_media a ms:media;
               ms:has_name ?old_media_name;
               ms:has_end_point ?old_media_end_point;
               ms:has_id ?media_id
};

INSERT DATA {
    ?media a ms:media;
           ms:has_name ?media_name;
           ms:has_end_point ?media_end_point;
           ms:has_id ?media_id
}