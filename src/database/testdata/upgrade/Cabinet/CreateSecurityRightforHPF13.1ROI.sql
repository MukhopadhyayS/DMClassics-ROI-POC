Use Cabinet
GO
Set nocount on
If exists
(Select 1 from security_Rights where security_id > 5999 and security_id <7000)
Begin
	--entries exists in the table for the HPF13 ROI Security rights
	Delete from security_Rights where security_id > 5999 and security_id <7000
End



Insert Security_Rights Values (6101,'ROI Administration',0)
Insert Security_Rights Values (6102,'ROI Access Application',0)
Insert Security_Rights Values (6103,'ROI Create Request',0)
Insert Security_Rights Values (6104,'ROI Modify Request',0)
Insert Security_Rights Values (6105,'ROI Delete Request',0)
Insert Security_Rights Values (6106,'ROI VIP Status',0)
Insert Security_Rights Values (6107,'ROI Cancel Request',0)
Insert Security_Rights Values (6108,'ROI Pend Request',0)
Insert Security_Rights Values (6109,'ROI Deny Request',0)
Insert Security_Rights Values (6110,'ROI Adjust Charges',0)
Insert Security_Rights Values (6111,'ROI Reports',0)
Insert Security_Rights Values (6112,'ROI Print/Fax',0)
Insert Security_Rights Values (6113,'ROI Export to PDF',0)
Insert Security_Rights Values (6114,'ROI Post Payment',0)
Insert Security_Rights Values (6115,'ROI View Request',0)


Select * from security_Rights where security_id > 5999 and security_id <7000