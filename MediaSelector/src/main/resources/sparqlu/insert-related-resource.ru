PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?related_resource a ?related_resource_type;
                      ms:has_id ?related_resource_id;
                      ms:is_bound_to ?old_related_resource_uri
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_related_resource ?related_resource_type .

    ?related_resource_type ms:has_id ?related_resource_type_id .

    ?related_resource a ?related_resource_type;
                      ms:has_id ?related_resource_id;
                      ms:is_bound_to ?old_related_resource_uri
};

INSERT {
    ?related_resource a ?related_resource_type;
                      ms:has_id ?related_resource_id;
                      ms:is_bound_to ?related_resource_uri .
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_related_resource ?related_resource_type .

    ?related_resource_type a ms:related_resource;
                           ms:has_id ?related_resource_type_id
}