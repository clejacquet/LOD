PREFIX ms: <http://mediaselector.com/data/ont/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

DELETE {
    ?relative_counter a ms:relative_resource_type_counter;
                      ms:has_id ?relative_counter_id;
                      ms:has_value ?relative_counter_value
} WHERE {
    ?relative_counter a ms:relative_resource_type_counter;
                      ms:has_id ?relative_counter_id;
                      ms:has_value ?relative_counter_value
};

DELETE {
    ?media ms:has_related_resource ?related_resource .

    ?related_resource a ms:related_resource;
                      ms:has_id ?related_resource_id;
                      ms:has_name ?related_resource_name;
                      ms:has_type ?related_resource_type;
                      ms:has_title_property ?related_resource_title_property;
                      ms:has_relation ?related_resource_relation
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_related_resource ?related_resource .

    ?related_resource ms:has_id ?related_resource_id;
                      ms:has_name ?related_resource_name;
                      ms:has_type ?related_resource_type;
                      ms:has_title_property ?related_resource_title_property;
                      ms:has_relation ?related_resource_relation
};

DELETE {
    ?media ms:has_main_resource ?main_resource .

    ?main_resource a ms:main_resource;
                   ms:has_id ?main_resource_id;
                   ms:has_name ?main_resource_name;
                   ms:has_type ?main_resource_type;
                   ms:has_title_property ?main_resource_title_property
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?main_resource .

    ?main_resource ms:has_id ?main_resource_id;
                   ms:has_name ?main_resource_name;
                   ms:has_type ?main_resource_type;
                   ms:has_title_property ?main_resource_title_property
};

DELETE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_end_point ?media_end_point;
           ms:has_name ?media_name;
           ms:has_author ?author
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_end_point ?media_end_point;
           ms:has_name ?media_name;
           ms:has_author ?author .

    ?author a ms:user;
            ms:has_id ?author_id
}