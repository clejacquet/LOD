PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?relative_resource_type_counter a ms:relative_resource_type_counter;
                                    ms:has_id ?media_id;
                                    ms:has_value ?old_relative_resource_type_count_value
} WHERE {
    ?relative_resource_type_counter a ms:relative_resource_type_counter;
                                    ms:has_id ?media_id;
                                    ms:has_value ?old_relative_resource_type_count_value
};

INSERT DATA {
    ?relative_resource_type_counter a ms:relative_resource_type_counter;
                                    ms:has_id ?media_id;
                                    ms:has_value ?relative_resource_type_count_value
}