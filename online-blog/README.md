# Implementando Spring Security con JWT (JSON Web Token)
Proyecto con seguridad basada en Usuarios, Roles y Permisos, cuenta con una base de datos SQLite3 para pruebas, donde ya se encuentra un usuario con el rol de administrador para que puedan probar las operaciones aseguradas. <br>
username : admin <br>
password: admin <br>
El rol de administrador cuenta con los permisos CREATE, UPDATE, READ, DELETE mientras que el rol user solo cuenta con READ y el rol autor con CREATE, READ, UPDATE. <br>
La aplicación es muy simple sobre un blog online donde se pueden realizar publicaciones, y cada publicacion tiene un autor. Los End-Points son los siguientes: <br>
<br>
Entidad Post: <br>
Estructura JSON: <br>
{  <br>
  "title" : "example",  <br>
  "description" : "example",  <br>
  "authorsList" : [  <br>
    {  <br>
    "id" : 1  <br>
  } ]  <br>
}  <br>
* POST: localhost:8080/api/post/save <br>
* GET: localhost:8080/api/post/all <br>
* GET: localhost:8080/api/post/get/{id} <br>
* DELETE: localhost:8080/api/post/delete/{id} <br>
* PUT: localhost:8080/api/post/edit/{id} <br> <br> 

Entidad Author: <br>
Estructura JSON: <br>
{  <br>
  "name" : "example",  <br>
  "lastname" : "example",  <br>
}  <br>
* POST: localhost:8080/api/author/save <br>
* GET: localhost:8080/api/author/all <br>
* GET: localhost:8080/api/author/get/{id} <br>
* DELETE: localhost:8080/api/author/delete/{id} <br>
* PUT: localhost:8080/api/author/edit/{id} <br>



 