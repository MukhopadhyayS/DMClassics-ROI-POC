Use CABINET;
SET NOCOUNT ON
Select COUNT(TableRowID) AS [Rows Migrated], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestorTypeToBillingTier'),
	OBJECT_ID(N'ROI_RequestorTypeToBillingTemplate'),
	OBJECT_ID(N'ROI_RequestorToEmailPhone'),
	OBJECT_ID(N'ROI_RequestorToAddress'),
	OBJECT_ID(N'ROI_Address'),
	OBJECT_ID(N'ROI_EmailPhone'),
	OBJECT_ID(N'ROI_Requestor'),
	OBJECT_ID(N'ROI_RequestorType'),
	OBJECT_ID(N'ROI_RequestEvent'),
	OBJECT_ID(N'ROI_RequestDeliveryCharges'),
	OBJECT_ID(N'ROI_RequestDelivery'),
	OBJECT_ID(N'ROI_SearchLOV'),
	OBJECT_ID(N'ROI_RequestMain')
)
GROUP BY 
	OBJECT_Name(TableObjectID)
ORDER BY
	OBJECT_Name(TableObjectID) 

