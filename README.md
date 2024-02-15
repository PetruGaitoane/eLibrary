# eLibrary
eLibrary 230907-FinalProject

Purpose
- The purpose for this backend project is to manage the books belonging to a eLibrary.
- This spring boot application is running on port 8080
- The application uses PostgreSQL for DBMS to get, store, update or delete the books in the library DB

Available endpoints
- GET /api/library/info - returns a message if the server is up;

RoleController
- GET /api/library/roles/{roleId} - returns a role by its id;
- GET /api/library/roles - returns a list of all the roles in the library or returns the roles by filters (param : role);
- POST /api/library/roles - creates a new role and stores it in the database based on a JSON body;
- PUT /api/library/roles/{roleId} - updates an existing role, using its id, to look it up and a JSON body to update its fields;
- DELETE/api/library/roles/{roleId} – deletes a role using its id to look it up;

UserController
- GET /api/library/users/{userId} - returns a user by its id;
- GET /api/library/users - returns a list of all the users in the library or returns the users by filters (param : name and email);
- POST /api/library/users - creates a new user and stores it in the database based on a JSON body;
- PUT /api/library/users/{userId} - updates an existing user, using his/hers id, to look it up and a JSON body to update its fields;
- PUT /api/library/users/{userId}/role/{roleId} - assigns an existing user, using His/hers id, to a existing role, using  its id;
- DELETE/api/library/users/{userId} – deletes a user using his/hers id to look it up;

BookController
- GET /api/library/books/{bookId} - returns a book by its id;
- GET /api/library/books - returns a list of all the books in the library or returns the books by filters (param : title and genre);
- POST /api/library/books - creates a new book and stores it in the database based on a JSON body;
- PUT /api/library/books/{bookId} - updates an existing book, using its id, to look it up and a JSON body to update its fields;
- PUT /api/library/books/{bookId}/author/{authorId} - assigns an existing book, using its id, to a existing author, using  his/hers id;
- DELETE/api/library/books/{bookId} – deletes a book using its id to look it up;

AuthorController
- GET /api/library/authors/{authorId} - returns a author by his/hers id;
- GET /api/library/authors - returns a list of all the authors in the library or returns the author by filters (param : author);
- POST /api/library/authors - creates a new author and stores it in the database based on a JSON body;
- PUT /api/library/authors/{authorId} - updates an existing author using his/hers id to look it up and a JSON body to update its fields;
- DELETE/api/library/author/{authorId} – deletes a author using his/hers id to look it up;

OrderDetailController
- GET /api/library/orderDetails/{orderId} - returns a order by its id;
- GET /api/library/orderDetails - returns a list of all the orders;
- POST /api/library/placeOrder - creates a book order and stores it in the database based on a JSON body.
- DELETE /api/library/deleteOrder/{orderId} – deletes a order using its id to look it up;

CartController
- GET /api/library/books/addToCart/{bookId} - adds a book to the cart, using its id
- GET /api/library/books/getCartDetails/{cartId} - returns a cart by its id;
- DELETE /api/library/books/deleteCart/{cartId} - deletes a cart using its id;

Entity description

Role:
- roleId – Long type (generated value), its DB Table corresponded is ‘roleId’;
- roleName – String type , its DB Table corresponded is ‘Role’;
- users - Set<User> type, ManyToMany

User:
- userId – Long type (generated value), its DB Table corresponded is ‘userId’;
- userFirstName – String type , its DB Table corresponded is ‘FirstName’;
- userLastName – String type , its DB Table corresponded is ‘LastName’;
- userName – String type , its DB Table corresponded is ‘userName’;
- userPassword – Sting type, its DB Table corresponded is ‘Password’;
- userContactNumber – int type, its DB Table corresponded is ‘ContactNumber’;
- userEmail – Sting type, its DB Table corresponded is ‘Email’;
- roles - Set<Role> type, ManyToMany

Book:
- bookId – Long type (generated value), its DB Table corresponded is ‘bookId’;
- title – String type , its DB Table corresponded is ‘Title’;
- genre – Sting type, its DB Table corresponded is ‘Genre’;
- description – String type, its DB Table corresponded is ‘Description’;
- saleQuantity - int type, its DB Table corresponded is ‘ForSale’;
- salePrice - double type, its DB Table corresponded is ‘SalePrice’;
- lendQuantity - int type, its DB Table corresponded is ‘ForLend’;
- authors - Set<Author> type, ManyToMany

Author:
- authorId – Long type (generated value), its DB Table corresponded is ‘authorId’;
- authorName – String type , its DB Table corresponded is ‘Author’;
- books - Set<Book> type, ManyToMany

OrderDetail:
- orderId - Long type (generated value);
- orderUserName;
- orderContactAddress;
- orderContactNumber;
- orderStatus;
- orderQuantity;
- orderValue;
- book - Book type, OneToOne;

OrderBookQuantity:
- bookId – Long type;
- quantity - int type;

OrderInput:
- fullName - String type;
- fullAddress - String type;
- contactNumber - int type;
- orderBookQuantityList - List<OrderBookQuantity> type;

Cart
- cartId - Long type (generated value);
- book - Book type, OneToOne;


Possible further developments:
- Adding a system to add multiple users;
- Adding a system to connect the user and the roles to the security configuration;
- Adding a system to show different responses form the GET command base on users roles;
- Adding multiple custom exceptions for nicer responses in code;
- Adding tests for all the classes;
