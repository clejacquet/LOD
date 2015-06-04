PREFIX ms: <http://mediaselector.com/data/ont/>

DELETE {
    ?media ms:has_subscribed ?user
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id .

    ?user a ms:user;
          ms:has_id ?user_id
};


INSERT {
    ?media ms:has_subscribed ?user
} WHERE {
    ?media a ms:media;
           ms:has_id ?media_id .

    ?user a ms:user;
          ms:has_id ?user_id
}