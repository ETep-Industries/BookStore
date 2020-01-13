CREATE DATABASE BOOK_STORE_DB;
USE BOOK_STORE_DB;
CREATE TABLE USER 
  ( 
     UserID       VARCHAR(25) NOT NULL, 
     Last_Name    VARCHAR(25) NOT NULL, 
     Middle_Name  VARCHAR(25) NOT NULL, 
     First_Name   VARCHAR(25) NOT NULL, 
     Password     VARCHAR(25) NOT NULL, 
     Street       VARCHAR(50), 
     City         VARCHAR(50), 
     State        VARCHAR(50), 
     Zip          INTEGER, 
     Email        VARCHAR(50), 
     Phone_Number VARCHAR(20), 
     PRIMARY KEY (UserID) 
  ); 
  
INSERT INTO USER VALUES ('1','Thomason','Peters', 'Mark', 'mau7fo1U', '302 Freed Drive', 'Merced', 'CA', 95340, 'MarkPThomason@gmail.com', '2097242647');
INSERT INTO USER VALUES ('2','Obrien','Annie', 'Ruby', 'ChiPh9aeQu', '450 Wilson Street', 'Hesperia', 'CA', 92345, 'RubyJObrien@gmail.com', '7609563009');
INSERT INTO USER VALUES ('3','Caron','Kim', 'Jane', 'eiLae6eegh', '3730 Augusta Park', 'Saint Albans', 'WV', 25177, 'JaneCCaron@gmail.com', '2497213447');
INSERT INTO USER VALUES ('4','Watkins','Diane', 'Emma', 'Bei6quieza3', '4499 Harron Drive', 'Annapolis', 'MD', 21401, 'EmmaDWatkins@gmail.com', '4434826300');
INSERT INTO USER VALUES ('5','Hatcher','H', 'Jeff', 'iMeeThe6', '764 Simpson Avenue', 'Millersville', 'PA', 17551, 'JeffHHatcher@gmail.com', '7178714638');

  
  CREATE TABLE ADMINISTRATOR 
  ( 
     AdminID  VARCHAR(25) NOT NULL, 
     Password VARCHAR(25) NOT NULL, 
     PRIMARY KEY (AdminID) 
  ); 
  
INSERT INTO ADMINISTRATOR VALUES ('DaveTheAdmin', 'password');
INSERT INTO ADMINISTRATOR VALUES ('Wilson1', 'aKaphohx5');

CREATE TABLE Book 
  ( 
     ISBN          CHAR(13) NOT NULL, 
     Title         VARCHAR(100) NOT NULL, 
     PublishedDate DATE, 
     Publisher     VARCHAR(100), 
     Price         DECIMAL(10, 2) NOT NULL, 
     Genre         VARCHAR(25), 
     Description   TEXT, 
     PRIMARY KEY (ISBN)
  ); 
 
INSERT INTO Book VALUES ('9780735219090', 'Where the Crawdads Sing', '2014-08-14', 'G.P. Putnams Sons', 15.60, 'Fiction', 'For years, rumors of the “Marsh Girl” have haunted Barkley Cove, a quiet town on the North Carolina coast. So in late 1969, when handsome Chase Andrews is found dead, the locals immediately suspect Kya Clark, the so-called Marsh Girl. But Kya is not what they say. Sensitive and intelligent, she has survived for years alone in the marsh that she calls home, finding friends in the gulls and lessons in the sand. Then the time comes when she yearns to be touched and loved. When two young men from town become intrigued by her wild beauty, Kya opens herself to a new life–until the unthinkable happens.' );
INSERT INTO Book VALUES ('9781476773094', 'Unfreedom of the Press', '2019-05-21', 'Threshold Editions', 16.80, 'Politics', 'A groundbreaking and enlightening book that shows how the great tradition of the American free press has degenerated into a standardless profession that has squandered the faith and trust of the American public, not through actions of government officials, but through its own abandonment of reportorial integrity and objective journalism.' );
INSERT INTO Book VALUES ('9780964519145', 'Maxwell Quick Medical Reference', '2011-12-15', 'Maxwell Pub Co', 7.63, 'Education', 'A pocket guide that includes essential information used in everyday medical practice. Topics include ACLS protocols, normal lab values, serum drug levels, common healthcare equations, chart note formats, glucose tolerance values, immunization schedule, developmental milestones, APGAR scoring exam, history and physical exam, neurological exam, dermatome maps, Glasgow coma scale, and Snellen eye charts.' );
INSERT INTO Book VALUES ('9780316154895', 'The House of Broken Angels', '2019-03-05', 'Back Bay Books', 10.91, 'Social Sciences', 'In his final days, beloved and ailing patriarch Miguel Angel de La Cruz, affectionately called Big Angel, has summoned his entire clan for one last legendary birthday party. But as the party approaches, his mother, nearly one hundred, dies, transforming the weekend into a farewell doubleheader.' );
INSERT INTO Book VALUES ('9788184898651', 'The Algorithm Design Manual', '2010-11-05', 'Springer', 28.82, 'Education', 'Expanding on the highly successful formula of the first edition, this book now serves as the primary textbook of choice for any algorithm design course while maintaining its status as the premier practical reference guide to algorithms.' );
INSERT INTO Book VALUES ('9781491952962', 'Practical Statistics for Data Scientists: 50 Essential Concepts', '2017-05-28', 'OReilly Media', 13.46, 'Education', 'Statistical methods are a key part of of data science, yet very few data scientists have any formal statistics training. Courses and books on basic statistics rarely cover the topic from a data science perspective. This practical guide explains how to apply various statistical methods to data science, tells you how to avoid their misuse, and gives you advice on whats important and whats not.' );
INSERT INTO Book VALUES ('9780134757599', 'Refactoring: Improving the Design of Existing Code', '2018-11-30', 'Addison-Wesley Professional', 44.56, 'Education', 'The bulk of the book is around seventy refactorings described in detail: the motivation for doing them, mechanics of how to do them safely and a simple example.' );
INSERT INTO Book VALUES ('9780321127426', 'Patterns of Enterprise Application Architecture', '2002-11-15', 'Addison-Wesley Professional', 52.86, 'Education', 'The practice of enterprise application development has benefited from the emergence of many new enabling technologies. Multi-tiered object-oriented platforms, such as Java and .NET, have become commonplace. These new tools and technologies are capable of building powerful applications, but they are not easily implemented. Common failures in enterprise applications often occur because their developers do not understand the architectural lessons that experienced object developers have learned.' );



CREATE TABLE Author 
  ( 
     AuthorID    INTEGER NOT NULL, 
     Middle_Name VARCHAR(100), 
     Last_Name   VARCHAR(100) NOT NULL, 
     First_Name  VARCHAR(100) NOT NULL, 
     BirthDate   DATE, 
     PRIMARY KEY (AuthorID)
  );
  
INSERT INTO Author VALUES(1, NULL, 'Owens', 'Delia', '1949-01-01');
INSERT INTO Author VALUES(2, 'Reed', 'Levin', 'Mark', '1957-09-21');
INSERT INTO Author VALUES(3, 'W', 'Maxwell', 'Robert', '1969-12-31');
INSERT INTO Author VALUES(4, 'Alberto', 'Urrea', 'Luis', '1955-08-20');
INSERT INTO Author VALUES(5, NULL, 'Skiena', 'Steven', '1961-01-30');
INSERT INTO Author VALUES(6, 'C', 'Bruce', 'Peter', '1956-10-02');
INSERT INTO Author VALUES(7, NULL, 'Fowler', 'Martin', '1963-1-1');


CREATE TABLE Wrote 
  ( 
     AuthorID INTEGER NOT NULL, 
     ISBN     CHAR(13) NOT NULL, 
     PRIMARY KEY (AuthorID, ISBN),
	 FOREIGN KEY (AuthorID) REFERENCES Author(AuthorID)
     ON DELETE CASCADE ON UPDATE CASCADE, 
	 FOREIGN KEY (ISBN) REFERENCES Book(ISBN) 
     ON DELETE CASCADE ON UPDATE CASCADE 
  ); 
  
INSERT INTO Wrote VALUES(1, '9780735219090');
INSERT INTO Wrote VALUES(2, '9781476773094');
INSERT INTO Wrote VALUES(3, '9780964519145');
INSERT INTO Wrote VALUES(4, '9780316154895');
INSERT INTO Wrote VALUES(5, '9788184898651');
INSERT INTO Wrote VALUES(6, '9781491952962');
INSERT INTO Wrote VALUES(7, '9780134757599');
INSERT INTO Wrote VALUES(7, '9780321127426');

CREATE TABLE has_Carts 
  ( 
     CartID INTEGER NOT NULL, 
     UserID VARCHAR(25) NOT NULL, 
     PRIMARY KEY (CartID), 
     FOREIGN KEY (UserID) REFERENCES User(UserID) 
     ON DELETE CASCADE ON UPDATE CASCADE 

  ); 

INSERT INTO has_Carts VALUES (1, 1);
INSERT INTO has_Carts VALUES (2, 2);
INSERT INTO has_Carts VALUES (3, 3);
INSERT INTO has_Carts VALUES (4, 4);
INSERT INTO has_Carts VALUES (5, 5);
  
  
CREATE TABLE Shopping_Cart 
  ( 
     CartID       INTEGER NOT NULL, 
     Purchased_yn INTEGER, 
     PRIMARY KEY (cartID),
	 FOREIGN KEY (cartID) REFERENCES has_Carts(CartID)
	 ON DELETE NO ACTION ON UPDATE CASCADE 
  ); 

INSERT INTO Shopping_Cart VALUES (1, 1);
INSERT INTO Shopping_Cart VALUES (2, 0);
INSERT INTO Shopping_Cart VALUES (3, 0);
INSERT INTO Shopping_Cart VALUES (4, 0);
INSERT INTO Shopping_Cart VALUES (5, 0);

  
CREATE TABLE CONTAINS 
  ( 
     CartID INTEGER NOT NULL, 
     ISBN   CHAR(13) NOT NULL, 
     Quanity INTEGER, 
     PRIMARY KEY (CartID, ISBN), 
     FOREIGN KEY (CartID) REFERENCES Shopping_Cart(CartID)
     ON DELETE NO ACTION ON UPDATE CASCADE, 
     FOREIGN KEY (ISBN) REFERENCES Book(ISBN) 
	 ON DELETE NO ACTION ON UPDATE CASCADE 
  ); 

INSERT INTO CONTAINS VALUES(1, '9780735219090', 1);
INSERT INTO CONTAINS VALUES(2, '9781476773094', 1);
INSERT INTO CONTAINS VALUES(3, '9780964519145', 1);
INSERT INTO CONTAINS VALUES(4, '9780316154895', 1);
INSERT INTO CONTAINS VALUES(5, '9788184898651', 1);

  
CREATE VIEW Shopping_Cart_Data AS SELECT SC.*, SQ.TOTAL_COST FROM Shopping_Cart AS SC, (SELECT Contains.CartID AS CID, cast(sum(BOOK.PRICE*CONTAINS.Quanity) as decimal(5,2)) AS TOTAL_COST
FROM BOOK, CONTAINS WHERE BOOK.ISBN=CONTAINS.ISBN 
GROUP BY Contains.CartID) AS SQ WHERE SC.CartID=SQ.CID; 
