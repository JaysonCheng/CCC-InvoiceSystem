<h1 align="center"><a href="https://github.com/zhuxinyishcn/CCC-Simple-Invoice-System" target="_blank">CCC Simple Invoice System</a></h1>

> CCC Simple Invoice System is an application written in the Object-Oriented Java programming language that is backed by a database to replace the aging AS400 green-screen system which is currently used by CCC. 

__Detail Design Decoment__: [CCC Simple Invoice System](/designDocument-v6.0.pdf)

## Libraries && Implementation
>- [JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/): Java Database Connectivity (JDBC) API provides universal data access from the Java programming language.
> - [Log4j](https://logging.apache.org/log4j/2.x/manual/index.html): A modern logger tool based on Java implementation 
## Class/Entity Model
The Invoice class uses the composition design principle which contains Person class (salesperson in each Invoice), Customer class (customer class) and ProductOrder class (the list of product customer bought).Equipment, license, and Consultation are subclasses of abstract ProductData class. They have an"is -a" relation in this Diagram and all of them are the details for the ProductOrder class. This three classes inherit all the functionality of the product data class.
9Government and Corporate are subclasses of abstract CustomerData. For instance, a Government object is a CustomerData object which inherits all its function and properties.TheDataConverter class is responsible for converting data from three plain .datfiles into usable java instances of objects as well as generate two different types of data files—XML and JSON. Moreover, InvoiceReport is responsible for generating a summary and detailed reports for all products in each invoice for the users.
![](./.github/img/Class_Diagram.jpg "ER_Diagram.png")
## DataBase Design
The database schema of the application is shown in the ER Diagram presented below. This data integrity is maintained by creating a constraint key that conceptually should not have duplicates data in invoices and product tables. This helps users prevent duplicate data in their database.Each of the major data types including Invoice, Product, Customer, and Person are stored in the tables created. Person, Customer, and Invoice are joined to ProductOrder through the foreign key for each invoice associated with those tables. Specialized tables such as Address, email address, and Product, are 
7used to store the specific information for each major table in order to further normalize the database schema. Details of the database schema are shown below.  
![](./.github/img/ER_Diagram.png "ER_Diagram.png")
