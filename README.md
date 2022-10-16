## Summary

TUI DX Backend technical Test v2

* This is a Maven Project.
* The H2 in-memory database is used for storing data.
* After staring the application, go to http://localhost:8080/swagger-ui/index.html

## Order creation api
* Request URL is http://localhost:8080/api/v1/orders/create
* POST method 
* Request body contains the data of the order to create : 
  * ```{"deliveryAddressId": 0,"clientId": 0,"orderItems": [{"mealCode": "string","quantity": 0}]}```


## Order Update api
* Request URL is http://localhost:8080/api/v1/orders/{orderNumber}
* {orderNumber} is the number of the order to update
* PUT method
* Request body contains the new data :
    * ```{"deliveryAddressId": 0,"clientId": 0,"orderItems": [{"mealCode": "string","quantity": 0}]}```

## Search Order by client data api
* Request URL is http://localhost:8080/api/v1/orders/search
* POST method
* Request body contains keyword that could be part of the first name, last name, telephone, email of any client:
  * ```{"keyword": "string"}```
* This api needs authentication with a user having admin or manager roles

## Sign in 
* Request URL is http://localhost:8080/auth/signin
* POST method
* Request body contains username and password :
  * ```{"username": "string","password": "string"}```
* This api returns a token that can be used for private apis

## Access and Roles
* There are 3 available roles: Admin, manager, guest
* For Testing:
  * username:admin , password:password has all roles
  * username:manager , password:password has only manager role
  * username:guest , password:password has only guest role