INSERT INTO [USP].[dbo].[T_USER]
           ([CODE]
           ,[USERNAME]
           ,[PASSWORD]
           ,[NAME]
           ,[GENDER]
           ,[MOBILE_PHONE]
           ,[TELEPHONE]
           ,[EMAIL]
           ,[IS_ACTIVE]
           ,[IS_LOCKED]
           ,[IS_DELETED]
           ,[CREATE_AT]
           ,[UPDATE_AT]
           ,[CREATE_BY_USER_ID]
           ,[UPDATE_BY_USER_ID]
           ,[DATA_SOURCE])
     VALUES
           (<CODE, varchar(20),>
           ,<USERNAME, varchar(50),>
           ,<PASSWORD, varchar(100),>
           ,<NAME, varchar(50),>
           ,<GENDER, char(10),>
           ,<MOBILE_PHONE, varchar(50),>
           ,<TELEPHONE, varchar(50),>
           ,<EMAIL, varchar(50),>
           ,<IS_ACTIVE, char(1),>
           ,<IS_LOCKED, char(1),>
           ,<IS_DELETED, char(1),>
           ,<CREATE_AT, datetime,>
           ,<UPDATE_AT, datetime,>
           ,<CREATE_BY_USER_ID, bigint,>
           ,<UPDATE_BY_USER_ID, bigint,>
           ,<DATA_SOURCE, varchar(20),>)
GO

