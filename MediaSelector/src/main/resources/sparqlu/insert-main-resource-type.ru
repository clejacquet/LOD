PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media ms:has_main_resource ?old_main_resource .

    ?old_main_resource a ms:main_resource;
                       ms:has_name ?old_main_resource_name;
                       ms:has_type ?old_main_resource_type;
                       ms:has_title_property ?old_title_property;
                       ms:has_author_property ?old_author_property;
                       ms:has_author_name_property ?old_author_name_property;
                       ms:has_abstract_property ?old_abstract_property
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id;
           ms:has_main_resource ?old_main_resource .

    ?old_main_resource ms:has_name ?old_main_resource_name;
                       ms:has_type ?old_main_resource_type;
                       ms:has_title_property ?old_title_property;
                       ms:has_author_property ?old_author_property;
                       ms:has_author_name_property ?old_author_name_property;
                       ms:has_abstract_property ?old_abstract_property
};

INSERT {
    ?media ms:has_main_resource ?main_resource .

    ?main_resource a ms:main_resource;
                   ms:has_type ?main_resource_type;
                   ms:has_name ?main_resource_name;
                   ms:has_title_property ?title_property;
                   ms:has_author_property ?author_property;
                   ms:has_author_name_property ?author_name_property;
                   ms:has_abstract_property ?abstract_property
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id
}