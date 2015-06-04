PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?user ms:like ?main_resource
} WHERE {
    ?user a ms:user;
          ms:has_id ?user_id
};

INSERT {
    ?user ms:like ?main_resource
} WHERE {
    ?user a ms:user;
          ms:has_id ?user_id
}