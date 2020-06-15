# SQL Server Bulk Copy Java Example
Simple example showing how to use SQL Server bulk insert from a Java application

## Prerequisites
If you already have SQL server, great! However, if you do not you can download and install SQL Express.

* Download [SQL Server Express](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
* Download [SQL Server Management Studio](https://docs.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms?view=sql-server-ver15)

## Create Database
The scripts you will need are in the folder: src/main/resources/db

* Create_Database_and_User.sql
    * Create a new database called "Examples" and the user "client"
* Create_Table.sql
    * Create the table dbo.SqlBulkInsertExample
* Select.sql
    * Example select statement to query dbo.SqlBulkInsertExample

## Run the Application
If you have IntelliJ or Eclipse you can open the pom.xml file and run the application.

However, if you have [Maven](https://maven.apache.org/download.cgi) installed you can run the application from the command line with:
* mvn compile
* mvn exec:java -Dexec.mainClass="SqlBulkCopyExample"

