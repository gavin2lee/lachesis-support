USE [USP]
GO

/****** Object:  Table [dbo].[T_ROLE]    Script Date: 11/23/2016 10:18:55 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[T_ROLE](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CODE] [varchar](20) NULL,
	[NAME] [varchar](50) NULL,
	[LABEL] [nvarchar](50) NULL,
	[DESCRIPTION] [nvarchar](100) NULL,
	[IS_DELETED] [char](10) NULL,
	[CREATE_AT] [datetime] NULL,
	[UPDATE_AT] [datetime] NULL,
	[CREATE_BY_USER_ID] [bigint] NULL,
	[UPDATE_BY_USER_ID] [bigint] NULL,
	[DATA_SOURCE] [varchar](50) NULL,
 CONSTRAINT [PK_ROLE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[T_ROLE] ADD  CONSTRAINT [DF_ROLE_IS_DELETED]  DEFAULT ('N') FOR [IS_DELETED]
GO
