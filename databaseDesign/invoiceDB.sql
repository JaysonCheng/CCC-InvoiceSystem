# Author : Xinyi Zhu, Jin Seng Cheng
# Date : 03/08/2019
# Cinco Computer Consultants (CCC) project
#
#
# This file contains queries to create the tables and input all the values into it.




DROP TABLE IF EXISTS ProductOrder;
DROP TABLE IF EXISTS Invoice;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS EmailAddress;
DROP TABLE IF EXISTS Person;
Drop table if exists Address;

CREATE TABLE Address (
	addressId INT PRIMARY KEY NOT NULL auto_increment, 
    street VARCHAR(100) NOT NULL, 
    city VARCHAR(100) NOT NULL, 
    state VARCHAR(100) NOT NULL, 
    zip VARCHAR(100) NOT NULL, 
    country VARCHAR(100) NOT NULL
    );

CREATE TABLE Person (
	personId INT PRIMARY KEY NOT NULL auto_increment, 
    personCode VARCHAR(100) NOT NULL, 
    firstName VARCHAR(100) NOT NULL, 
    lastName VARCHAR(100) NOT NULL, 
    addressId INT NOT NULL, 
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
    );

CREATE TABLE EmailAddress (
	emailAddressId INT PRIMARY KEY NOT NULL auto_increment, 
    email VARCHAR(200) NOT NULL, 
    personId INT NOT NULL, 
    FOREIGN KEY (personId) REFERENCES Person(personId)
    );

CREATE TABLE Customer (
	customerId INT PRIMARY KEY NOT NULL auto_increment, 
    customerCode VARCHAR(200) NOT NULL, 
    type VARCHAR(1) NOT NULL, 
    primaryContact VARCHAR(200) NOT NULL, 
    fullName VARCHAR(200) NOT NULL, 
    addressId INT NOT NULL, 
    personId INT NOT NULL, 
    FOREIGN KEY (addressId) REFERENCES Address(addressId), 
    FOREIGN KEY (personId) REFERENCES Person(personId)
    );

CREATE TABLE Product (
	productId INT PRIMARY KEY NOT NULL auto_increment, 
    productCode VARCHAR(200) NOT NULL, 
    type VARCHAR(1) NOT NULL, 
    name VARCHAR(200) NOT NULL, 
    pricePerUnit DOUBLE NULL, 
    serviceFee DOUBLE NULL, 
    annualLicenseFee DOUBLE NULL, 
    consultantPersonCode VARCHAR(200) NULL, 
    hourlyFee DOUBLE NULL, 
    consultantId INT NULL, 
    FOREIGN KEY (consultantId) REFERENCES Person(personId)
    );

CREATE TABLE Invoice (
	invoiceId INT PRIMARY KEY NOT NULL auto_increment, 
    invoiceCode VARCHAR(200) NOT NULL, 
    customerId INT NOT NULL, 
    personId INT NOT NULL, 
    FOREIGN KEY (personId) REFERENCES Person(personId), 
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
    );

CREATE TABLE ProductOrder (
	productOrderId INT PRIMARY KEY NOT NULL auto_increment, 
    productId INT NOT NULL, 
    startDate VARCHAR(50) NULL, 
    endDate VARCHAR(50) NULL, 
    units DOUBLE  NULL, 
    invoiceId INT NOT NULL, 
    FOREIGN KEY (invoiceId) REFERENCES Invoice(invoiceId), 
    FOREIGN KEY (productId) REFERENCES Product(productId), 
    CONSTRAINT uniquePair UNIQUE INDEX (invoiceId, productId)
    );
    
#From line 91-114 are record for Address Table#
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (1, '122 East Jorgenson Street', 'Chicago', 'IL', '60613', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (2, '187 N 2nd Street', 'Omaha', 'NE', 68116, 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (3, '8896 East 1st Ave.', 'Dallas', 'TX', '75001', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (4, '895 Enemy Street', 'Ottawa', 'ON', 'K1A 0G9', 'Canada');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (5, '7 Wall Street', 'New Jersey', 'NJ', '10006-0018', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (6, '345 Ashes Street', 'Brooklyn', 'BN', '10098', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (7, '1075 East Addidas Street', 'Minneapolis', 'MN', '40865', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (8, '1041 East Addidas Street', 'Minneapolis', 'MN', '40865', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (9, '201 Back St E', 'Toronto', 'ON', 'M5V 2T6', 'Canada');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (10, '4 Red Jazz Way', 'Toronto', 'ON', 'M5V 1J1', 'Canada');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (11, 'Campus El287', 'Mexico City', 'FD', '21312', 'Mexico');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (12, 'Othmer Hall', 'Lincoln', 'NE', '68503', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (13, '117-08 Roosevelt Ave', 'Flushing', 'NY', '11368', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (14, '8 Lifeend Stadium Sr', 'West Rutherheck', 'NJ', '07060', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (15, '600 N 15th St', 'Lincoln', 'NE', '685808', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (16, '184 Marvel Way', 'New York', 'NY', '10453', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (17, '233 Fenghuang way', 'Shanghai', 'Shanghai', '90230', 'China');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (18, '4235 Pepper Rd', 'Barton', 'MD', '05822', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (19, '1060 East town', 'Omaha', 'NE', '60808', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (20, '233 South 7th St.', 'Omaha', 'NE', '68980', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (21, '530 N 17th St', 'Lincoln', 'NE', '685808', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (22, '1101 P St', 'Lincoln', 'NE', '68508', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (23, '301 N 12th St', 'Lincoln', 'NE', '68508', 'USA');
INSERT INTO Address (addressId, street, city, state, zip, country) VALUES (24, '366 Fengyuan Way', 'Shanghai', 'Shanghai', '200092', 'USA');

#From line 117-131 are record for Person Table#
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (1, '408c', 'Bryan', 'Stockton', 1);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (2, '188a', 'Tony', 'Coocie', 2);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (3, '61cb', 'O ''James', 'Madison', 3);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (4, '1652', 'O ''James', 'Madison', 4);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (5, 'alf9', 'Goku', 'Son', 5);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (6, 'ac89', 'Fox', 'Deaaron', 6);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (7, 'pt81', 'Ben', 'Simmons', 7);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (8, '652md', 'Harry', 'Williams', 8);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (9, 'mk98a', 'Stark', 'Patrick', 8);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (10, 'l3a9', 'Perry', 'Platypus', 9);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (11, '98ac', 'Bicker', 'Tom', 10);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (12, 'li87', 'Howard', 'Lancy', 11);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (13, '654pf', 'Steve', 'C.', 12);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (14, '3lnrd', 'McClarance', 'Timmy', 13);
INSERT INTO Person (personId, personCode, firstName, lastName, addressId) VALUES (15, '1569st', 'McGann', 'Parker', 14);

#From line 135-158 are record for EmailAddress Table#
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (1, 'bstockton@cubs.com', 1);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (2, 'bryan_stockton91@gmail.com', 1);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (3, 'tony_c_cococie@gmail.com', 2);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (4, 'tcoocie@fiz.com', 2);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (5, 'ojamesmad@ds9.com', 3);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (6, 'ojames@enterprise.gov', 3);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (7, 'dfox@gmail.com', 6);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (8, 'foxdear@crazy.net', 6);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (9, 'simmonsbb@cubs.com', 7);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (10, 'willwillhair@gov.com', 8);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (11, 'hw@who.com', 8);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (12, 'patrickstar@cse.unl.edu', 9);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (13, 'ptrstar98@unl.edu', 9);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (14, 'dadadada@whofan.com', 10);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (15, 'thebickertom@who.com', 11);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (16, 'tbicker@cse.unl.edu', 11);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (17, 'mostunpopular@whovian.com', 11);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (18, 'thelawyer@bbc.com', 11);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (19, 'lancehoward@cse.unl.edu', 12);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (20, 'lancy@unl.edu', 12);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (21, 'captainamerica@avengers.com', 13);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (22, 'timmc@hotmail.com', 14);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (23, 'mctimmymc@whofan.com', 14);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (24, 'pmcganker@mlb.com', 15);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (25, 'spidey@bar.com', 15);
INSERT INTO EmailAddress (emailAddressId, email, personId) VALUES (26, 'pmc@unl.edu', 15);

#From line 161-170 are record for Customer Table#
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (1, 'C001', 'G', '61cb', 'Selleck Dining Hall', 15, 3);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (2, 'C002', 'C', '1652', 'Stark Industries', 16, 4);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (3, 'C003', 'C', '408c', 'Fenghuang Bike Company', 17, 1);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (4, 'C004', 'G', 'ac89', 'National Pepper Research Institute', 18, 6);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (5, 'C005', 'C', '98ac', 'Cheese Industries', 19, 11);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (6, 'C006', 'C', '654pf', 'Hot Chill Company', 20, 13);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (7, 'C007', 'G', '3lnrd', 'University of Nebraska-Lincoln', 21, 14);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (8, 'C008', 'C', '1569st', 'The Marcus Lincoln Grand Cinema', 22, 15);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (9, 'C009', 'C', '188a', 'Lied Center for Performing Arts', 23, 2);
INSERT INTO Customer (customerId, customerCode, type, primaryContact, fullName, addressId, personId) VALUES (10, 'C010', 'G', 'pt81', 'Shanghai University', 24, 7);

#From line 172-182 are record for Product Table#
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (1, '3301', 'L', 'Member registration', NULL, 100.00, 1500.00, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (2, 'fl24', 'E', 'Cinco-Fone', 115.99, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (3, 'fg13', 'E', 'Cinco Video Cube Playback System', 4500.00, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (4, '1256', 'E', 'Internette Discs', 24.00, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (5, '789p', 'L', 'Cinco Long Distance Service', NULL, 1800.00, 14500.00, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (6, '3175', 'C', 'Cinco-Fone Training', NULL, NULL, NULL, 'li87', 30.00, 12);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (7, '793g', 'C', 'Database System Setup', NULL, NULL, NULL, 'mk98a', 175.00, 9);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (8, '3249', 'L', 'Cloud SQL Hosting', NULL, 1000.00, 32500.00, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (9, '325y', 'L', 'Apple Quick Service', NULL, 1500.00, 900.00, NULL, NULL, NULL);
INSERT INTO Product (productId, productCode, type, name, pricePerUnit, serviceFee, annualLicenseFee, consultantPersonCode, hourlyFee, consultantId) VALUES (10, '3455', 'L', 'Domain registration', NULL, 230.00, 1000.00, NULL, NULL, NULL);

#From line 185-190 are record for Invoice Table#
INSERT INTO Invoice (invoiceId, invoiceCode, customerId, personId) VALUES (1, 'INV001', 8, 15);
INSERT INTO Invoice (invoiceId, invoiceCode, customerId, personId) VALUES (2, 'INV002 ', 9, 8);
INSERT INTO Invoice (invoiceId, invoiceCode, customerId, personId) VALUES (3, 'INV003', 3, 12);
INSERT INTO Invoice (invoiceId, invoiceCode, customerId, personId) VALUES (4, 'INV004 ', 5, 9);
INSERT INTO Invoice (invoiceId, invoiceCode, customerId, personId) VALUES (5, 'INV005', 7, 6);
INSERT INTO Invoice (invoiceId, invoiceCode, customerId, personId) VALUES (6, 'INV006 ', 1, 1);

#From line 192-206 are record for ProductOrder Table#
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (1, 2, NULL, NULL,15, 1);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (2, 1, '2016-02-14','2016-10-30', 0.709, 1);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (3, 3, NULL,NULL ,2, 2);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (4, 6, NULL, NULL, 10, 2);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (5, 1, '2013-09-14', '2016-10-30', 3.13, 2);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (6, 6, NULL, NULL,2, 3);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (7, 2, NULL, NULL,6.0, 3);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (8, 9, '2011-09-14','2011-09-19', 0.014, 3);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (9, 6, NULL,NULL ,233, 4);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (10, 7, NULL, NULL,666.0, 4);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (11, 10, '2011-12-14','2019-02-20', 7.19, 5);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (12, 5, '2000-01-14','2013-04-20', 13.27, 5);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (13, 2, NULL, NULL,1, 6);
INSERT INTO ProductOrder (productOrderId, productId, startDate, endDate, units, invoiceId) VALUES (14, 4, NULL, NULL,1, 6);
