PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media ms:has_related_resource ?related_resource .

    ?related_resource a ms:related_resource;
                      ms:has_id ?related_resource_id;
                      ms:has_type ?related_resource_type;
                      ms:has_name ?related_resource_name;
                      ms:has_relation ?related_resource_relation .

    ?main_resource ?relation ?related_resource
} WHERE {
    ?media a ms:media;
           ms:has_related_resource ?related_resource;
           ms:has_main_resource ?main_resource .

    ?related_resource ms:has_id ?related_resource_id;
                      ms:has_type ?related_resource_type;
                      ms:has_name ?related_resource_name;
                      ms:has_relation ?related_resource_relation .

    ?main_resource ?relation ?related_resource
};