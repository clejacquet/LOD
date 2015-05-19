PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media ms:has_related_resource ?old_related_resource .

    ?old_related_resource a ms:related_resource;
                          ms:has_id ?related_resource_id;
                          ms:has_name ?old_related_resource_name;
                          ms:has_type ?old_related_resource_type;
                          ms:has_title_property ?old_title_property;
                          ms:has_relation ?old_related_resource_relation
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?main_resource;
           ms:has_related_resource ?old_related_resource .

    ?old_related_resource ms:has_id ?related_resource_id;
                          ms:has_name ?old_related_resource_name;
                          ms:has_type ?old_related_resource_type;
                          ms:has_title_property ?old_title_property;
                          ms:has_relation ?old_related_resource_relation
};

INSERT {
    ?media ms:has_related_resource ?related_resource .

    ?related_resource a ms:related_resource;
                      ms:has_id ?related_resource_id;
                      ms:has_name ?related_resource_name;
                      ms:has_type ?related_resource_type;
                      ms:has_title_property ?title_property;
                      ms:has_relation ?related_resource_relation
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?main_resource
}