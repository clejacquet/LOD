PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?resource a ?main_resource;
              ms:has_id ?resource_id;
              ms:is_bound_to ?resource_uri
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?main_resource .

    ?resource a ?main_resource;
              ms:is_bound_to ?resource_uri
}