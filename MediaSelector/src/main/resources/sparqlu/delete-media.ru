PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?related_resource a ms:related_resource;
                      ms:has_id ?related_resource_id;
                      ms:has_name ?related_resource_name
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_related_resource ?related_resource .

    ?related_resource ms:has_id ?related_resource_id;
                      ms:has_name ?related_resource_name
};

DELETE {
    ?main_resource a ms:main_resource;
                   ms:has_id ?main_resource_id;
                   ms:has_name ?main_resource_name;
                   ?property ?related_resource
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?main_resource .

    ?main_resource ms:has_id ?main_resource_id;
                   ms:has_name ?main_resource_name;
                   ?property ?related_resource
};

DELETE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_end_point ?media_end_point;
           ms:has_name ?media_name
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_end_point ?media_end_point;
           ms:has_name ?media_name
}