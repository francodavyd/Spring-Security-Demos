# Implementando Spring Security con JWT (JSON Web Token)
Proyecto con seguridad basada en Usuarios, Roles y Permisos, cuenta con una base de datos SQLite3 para pruebas, donde ya se encuentra un usuario con el rol de administrador para que puedan probar las operaciones aseguradas. <br>
username : admin <br>
password: admin <br>
El rol de administrador cuenta con los permisos CREATE, UPDATE, READ, DELETE mientras que el rol user solo cuenta con READ y el rol autor con CREATE, READ, UPDATE. <br>
La aplicación es muy simple sobre un blog online donde se pueden realizar publicaciones, y cada publicacion tiene un autor. Los End-Points son los siguientes: <br>
<br>
# Entidad Post: <br>
Estructura JSON: <br>
{  <br>
  "title" : "example",  <br>
  "description" : "example",  <br>
  "authorsList" : [  <br>
    {  <br>
    "id" : 1  <br>
  } ]  <br>
}  <br>
* POST (Request Body): localhost:8080/api/post/save <br>
* GET: localhost:8080/api/post/all <br>
* GET: localhost:8080/api/post/get/{id} <br>
* DELETE: localhost:8080/api/post/delete/{id} <br>
* PUT (Request Body): localhost:8080/api/post/edit/{id} <br> <br> 

# Entidad Author: <br>
Estructura JSON: <br>
{  <br>
  "name" : "example",  <br>
  "lastname" : "example",  <br>
}  <br>
* POST (Request Body): localhost:8080/api/author/save <br>
* GET: localhost:8080/api/author/all <br>
* GET: localhost:8080/api/author/get/{id} <br>
* DELETE: localhost:8080/api/author/delete/{id} <br>
* PUT (Request Body): localhost:8080/api/author/edit/{id} <br>

# User (Spring Security): <br>
Estructura JSON: <br>
{  <br>
  "username" : "example",  <br>
  "password" : "example",  <br>
  "enabled" : true, <br>
  "accountNotExpired": true, <br>
  "accountNotLocked": true, <br>
  "credentialNotExpired": true, <br>
  "rolesList" : [{ <br>
  "id" : 1 <br>
  }] <br>
}  <br>
* POST (Request Body): localhost:8080/api/users/save <br>
* GET: localhost:8080/api/users/all <br>
* GET: localhost:8080/api/users/get/{id} <br>
* DELETE: localhost:8080/api/users/delete/{id} <br>
* PUT (Request Body): localhost:8080/api/users/edit/{id} <br>

# Permission: <br>
Estructura JSON: <br>
{  <br>
  "permissionName" : "example",  <br>
}  <br>
* POST (Request Body): localhost:8080/api/permission/save <br>
* GET: localhost:8080/api/permission/all <br>
* GET: localhost:8080/api/permission/get/{id} <br>
* DELETE: localhost:8080/api/permission/delete/{id} <br>
* PUT (Request Body): localhost:8080/api/permission/edit/{id} <br>
# Role: <br>
Estructura JSON: <br>
{  <br>
  "role" : "example",  <br>
  "permissionsList" : [{ <br>
  "id":1 <br>
  }]  <br>
}  <br>
* POST (Request Body): localhost:8080/api/role/save <br>
* GET: localhost:8080/api/role/all <br>
* GET: localhost:8080/api/role/get/{id} <br>
* DELETE: localhost:8080/api/role/delete/{id} <br>
* PUT (Request Body): localhost:8080/api/role/edit/{id} <br>

# Controlador de autenticación  <br>
Estructura JSON: <br>
{  <br>
  "username" : "example",  <br>
  "password" : "example",  <br>
}  <br>
* POST (Request Body): localhost:8080/auth/login

# Instalación <br>
Para probar este programa puedes clonar este repositorio e importarlo a tu IDE favorito, una vez levantado puedes realizar pruebas con postman.


 
