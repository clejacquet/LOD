PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media ms:has_main_resource ?main_resource .

    ?main_resource a ms:main_resource;
                   ms:has_name ?main_resource_name;
                   ms:has_type ?main_resource_type;
                   ms:has_title_property ?title_property;
                   ms:has_author_property ?author_property;
                   ms:has_author_name_property ?author_name_property;
                   ms:has_abstract_property ?abstract_property;
                   ms:has_date_property ?date_property
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id .

    ?media ms:has_main_resource ?main_resource .

    ?main_resource ms:has_name ?main_resource_name;
                   ms:has_type ?main_resource_type;
                   ms:has_title_property ?title_property;
                   ms:has_author_property ?author_property;
                   ms:has_author_name_property ?author_name_property;
                   ms:has_abstract_property ?abstract_property;
                   ms:has_date_property ?date_property
}