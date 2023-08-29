package com.dp.sleep.constants;

import java.util.List;

public interface SecurityConstants {
//  class REST {
//    public static List<String> BOOKS_WHITE_LIST = List.of("/rest/books",
//        "/rest/books/search",
//        "/rest/books/{id}");
//
//    public static List<String> AUTHORS_WHITE_LIST = List.of("/rest/authors",
//        "/rest/authors/search",
//        "/rest/authors/getAll",
//        "/rest/books/search/author",
//        "/rest/authors/{id}");
//
//    public static List<String> USERS_WHITE_LIST = List.of("/rest/users/auth",
//        "/rest/users/registration",
//        "/rest/users/remember-password",
//        "/rest/users/change-password");
//
//    public static List<String> AUTHORS_PERMISSION_LIST = List.of(
//        "/rest/authors/add",
//        "/rest/authors/update",
////                                                                     "/rest/authors/delete/**",
//        "/rest/authors/delete/{id}",
//        "/rest/authors/soft-delete/{id}",
//        "/rest/authors/restore/{id}"
//    );
//
//    public static List<String> BOOKS_PERMISSION_LIST = List.of("/rest/books/add",
//        "/rest/books/update",
//        "/rest/books/delete/**",
//        "/rest/books/delete/{id}",
//        "/rest/books/download/{bookId}");
//
//    public static List<String> USERS_PERMISSION_LIST = List.of("/rest/rent/book/*");
//  }

  List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
      "/js/**",
      "/static/**",
      "/static/sleep.css",
      "/",
      // -- Swagger UI v3 (OpenAPI)
      "/swagger-ui/**",
      "/webjars/bootstrap/5.0.2/**",
      "/v3/api-docs/**",
      "/error", "/users",
          "/favicon.ico");


  List<String> USERS_WHITE_LIST = List.of("/login",
      "/users/registration",
      "/users/remember-password",
      "/users/change-password",
      "/users/profile/{id}",
      "/users/profile/update/{id}",
      "/users/profile/update",
      "/users/subscription",
      "/users/subscription/{userId}",
      "/users/start/{id}",
      "/users/start",
      "/users/stop"
  );
  List<String> USERS_PERMISSION_LIST = List.of(
          "/users",
          "/users/ban/{id}"

          );

  List<String> MEDITATION_WHITE_LIST = List.of(
    "/meditations/*",
    "/meditations",
    "/meditations/search",
    "/meditations/download",
    "/meditations/{meditationId}"
  );

  List<String> MEDITATION_PERMISSION_LIST = List.of(
          "/meditations/add",
          "/meditations/delete/{meditationId}",
          "/meditations/restore/{id}",
          "/meditations/update/{id}",
          "/meditations/restore",
          "/meditations/update",
          "/meditations/delete"
  );

  List<String> CONVERTED_DATA_PERMISSION_LIST = List.of(
          "/history",
          "/history/*",
          "/convertedDates",
          "/convertedDates/*",
          "/history/{userConvertedDataId}",
          "/history/search"
  );


}
