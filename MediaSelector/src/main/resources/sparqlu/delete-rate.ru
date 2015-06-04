PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?user ms:like ?main_resource .
    ?main_resource a ?main_resource_type
} WHERE {
    ?user a ms:user;
          ms:has_id ?user_id .

    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?main_resource_type .
}