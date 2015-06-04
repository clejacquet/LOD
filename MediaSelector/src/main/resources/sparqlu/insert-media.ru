PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?old_media a ms:media;
               ms:has_name ?old_media_name;
               ms:has_description ?old_media_description;
               ms:has_end_point ?old_media_end_point;
               ms:has_id ?media_id;
               ms:has_author ?old_author
} WHERE {
    ?old_media a ms:media;
               ms:has_name ?old_media_name;
               ms:has_description ?old_media_description;
               ms:has_end_point ?old_media_end_point;
               ms:has_id ?media_id;
               ms:has_author ?old_author
};

INSERT {
    ?media a ms:media;
           ms:has_name ?media_name;
           ms:has_description ?media_description;
           ms:has_end_point ?media_end_point;
           ms:has_id ?media_id;
           ms:has_author ?author
} WHERE {
    ?author a ms:user;
            ms:has_id ?author_id
}