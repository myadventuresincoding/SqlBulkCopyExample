
USE master;
GO

CREATE LOGIN client WITH PASSWORD = 'client';
GO

CREATE DATABASE Examples;
GO

USE Examples
GO

create user client for login client;
GO

Grant ALL to client
GO

EXEC sp_addrolemember 'db_owner', 'client'
GO