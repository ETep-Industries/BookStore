# Create a MySQL database by using 'Run SQL Script..' in MySQL Workbench to upload the BookStoreDB.sql schema file

For reference the ser_team2mysql.sql schema file should start with the following:

CREATE DATABASE BOOK_STORE_DB;
USE BOOK_STORE_DB;
CREATE TABLE USER
(
  UserID       VARCHAR(25) NOT NULL,
  Last_Name    VARCHAR(25) NOT NULL,
  Middle_Name  VARCHAR(25) NOT NULL,
}

The script will populate the database with test/default values.

Tested with MySQL Community Version 8 but should be backwards compatible with previous versions.

/////////

Sourcecode: Book_Store.java

To start the Java application. Enter DB_URL, USER, PASS, and JDBC_DRIVER in order in the command line in Book_Store.java program. Then Compile the Application. Then Run the java application using an IDE, JAR File, or command line after editing the DB Connection information.

EX: java Book_Store <url> <user> <pwd> <driver>

//////////
Configure before compilation, data base conection.

//////////
