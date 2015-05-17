PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media ms:has_main_resource ?main_resource .

    ?main_resource a ms:main_resource;
                   ms:has_name ?main_resource_name;
                   ms:has_type ?main_resource_type
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id .

    ?media ms:has_main_resource ?main_resource .

    ?main_resource ms:has_name ?main_resource_name;
                   ms:has_type ?main_resource_type
}