PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?user a ms:user;
          ms:has_id ?user_id;
          ms:has_login ?old_user_login;
          ms:like ?old_main_resource
} WHERE {
    ?user a ms:user;
          ms:has_id ?user_id;
          ms:has_login ?old_user_login;
          ms:like ?old_main_resource
};

INSERT DATA {
    ?user a ms:user;
          ms:has_id ?user_id;
          ms:has_login ?user_login
}