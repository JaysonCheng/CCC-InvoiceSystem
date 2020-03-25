# Author : Xinyi Zhu, Jin Seng Cheng
# Date : 03/08/2019
# Cinco Computer Consultants (CCC) project
#
#
# This file contains queries to perform the following opera-tions by using the database I create in the invoiceDB file.



#1.  A query to retrieve the major fields for every person #

SELECT p.personCode
	,p.firstName
	,p.lastName
	,a.street
	,a.city
	,a.STATE
	,a.zip
	,a.country
	,e.email
FROM Person p
LEFT JOIN Address a ON p.personId = a.addressId
LEFT JOIN EmailAddress e ON p.personId = e.personId;

#2. A query to retrieve the email(s) of a given person #

SELECT p.personId
	,p.personCode
	,p.firstName
	,p.lastName
	,e.email
FROM Person p
LEFT JOIN EmailAddress e ON p.personId = e.personId
WHERE p.firstName LIKE 'Bicker';

#3.  A query to add an email to a specific person #

INSERT INTO EmailAddress (
	emailAddressId
	,email
	,personId
	)
VALUES (
	999
	,'testEmail1@unl.edu'
	,15
	);

#4. A query to change the email address of a given email record#

UPDATE EmailAddress e
SET e.email = 'testEmail2@unl.edu'
WHERE e.email LIKE 'hw@who.com';

#5.  A query (or series of queries) to remove a given person record#
# delete the child row first and then delete the parent row.#

DELETE
FROM Customer
WHERE personId = 7;

DELETE
FROM EmailAddress
WHERE personId = 7;

DELETE
FROM Person
WHERE personId = 7;

#6.  A query to create a person record #

INSERT INTO Person (
	personId
	,personCode
	,firstName
	,lastName
	,addressId
	)
VALUES (
	99
	,'367de'
	,'Ben'
	,'Timmy'
	,14
	);

#7. A query to get all the products in a particular invoice#

SELECT i.invoiceCode
	,p.productCode
	,p.type
	,p.name
FROM ProductOrder pro
LEFT JOIN Invoice i ON pro.invoiceId = i.invoiceId
LEFT JOIN Product p ON pro.productId = p.productId
WHERE i.invoiceCode = 'INV003';

#8. A query to get all the invoices of a particular customer#

SELECT i.invoiceCode
	,c.customerCode
	,c.type
	,c.fullName
FROM Invoice i
LEFT JOIN Customer c ON i.customerId = c.customerId
WHERE c.fullName = 'Selleck Dining Hall';

#9.  A query to create a new product record#

INSERT INTO Product (
	productId
	,productCode
	,type
	,name
	,serviceFee
	,annualLicenseFee
	)
VALUES (
	110
	,'4ew2'
	,'L'
	,'Test Case Product'
	,223.01
	,1000.23
	);

#10.  A query to create a new invoice record#

INSERT INTO Invoice (
	invoiceId
	,invoiceCode
	,customerId
	,personId
	)
VALUES (
	99
	,'INV099'
	,2
	,3
	);

#11.  A query to associate a particular product with a particular invoice#
#Since the ProductOrder is like the join table for my Product Table and Invoice Table#

INSERT ProductOrder (
	productOrderId
	,productId
	,units
	,invoiceId
	)
VALUES (
	'99'
	,7
	,23
	,1
	);

#12.  A query to find the total number of invoices foreach(and every) customer record#

SELECT c.customerCode
	,c.type
	,c.fullName
	,COUNT(*) AS numberOfInvoice
FROM Invoice i
LEFT JOIN Customer c ON i.customerId = c.customerId
GROUP BY c.customerCode;

#13.  A query to find the total number of invoices for each salesperson#

SELECT p.personCode
	,p.firstName
	,p.lastName
	,COUNT(*) AS numberOfInvoice
FROM Invoice i
LEFT JOIN Person p ON i.personId = p.personId
GROUP BY i.personId;

#14. A query to find the total number of invoices that include a particular product#

SELECT i.invoiceCode
	,p.productCode
	,p.type
	,p.name
	,COUNT(*) AS numberOfInvoice
FROM ProductOrder pro
LEFT JOIN Invoice i ON pro.invoiceId = i.invoiceId
LEFT JOIN Product p ON pro.productId = p.productId
GROUP BY i.invoiceCode
HAVING p.name = 'Cinco-Fone Training';

#A query to find thetotalcost (excluding fees and taxes) of all equipment in each invoice#
SELECT i.invoiceCode
	,p.productId
	,sum(po.units * p.pricePerUnit) AS subtotal
	,p.type
	,p.name
FROM Invoice i
LEFT JOIN ProductOrder po ON po.invoiceId = i.invoiceId
LEFT JOIN Product p ON p.productid = po.productId
WHERE p.type = 'E'
GROUP BY i.invoiceCode;

#16. a query to find any invoice that includes multipleinstances of the same equipment product.#

#Since I use the constraint key to prevent the prevents duplicate invoice/product records # 
#So, this query should return an empty result. #

SELECT i.invoiceId
	,i.invoiceCode
	,po.productId
	,p.type
	,p.name
FROM Invoice i
INNER JOIN ProductOrder po ON po.invoiceId = i.invoiceId
INNER JOIN Product p ON p.productId = po.productId
WHERE p.type = 'E'
GROUP BY i.invoiceCode
HAVING count(DISTINCT i.invoiceId) > 1;

#17 a query to find any and all invoices where the salesperson is the same as the primary contact of the invoice’s customer.#

#In this query, I create a temporary table called t that join Customer and Person Table and join this temporary table#
# to the Invoice Table I made to see if have the record indicate the salesperson is the same as the primary contact  #

SELECT i.invoiceCode
	,i.personId AS salesPerson
	,t.personId AS primarycontact
FROM Invoice i
INNER JOIN (
	SELECT c.customerId
		,p.personId
	FROM Customer c
	INNER JOIN Person p ON c.personId = p.personId
	) t ON t.customerId = i.customerId
	AND t.personId = i.personId;
