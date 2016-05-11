Use CABINET;
SET NOCOUNT ON
Print 'Prior to cleaning the tables'
Select count(*)  AS [ROI_RequestorTypeToBillingTier records] FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTier] WHERE Created_by_Seq < 0
Select count(*)  AS [ROI_BillingTemplateToFeeType records] from ROI_BillingTemplateToFeeType where [Created_By_Seq] < 0
Select count(*)  AS [ROI_FeeType records] from ROI_FeeType where [Created_By_Seq] < 0
Select count(*)  AS [ROI_RequestorTypeToBillingTemplate  records] from ROI_RequestorTypeToBillingTemplate where [Created_By_Seq] < 0
Select count(*)  AS [ROI_BillingTemplate  records] from ROI_BillingTemplate where [Created_By_Seq] < 0
Select count(*)  AS [ROI_RequestorTypeToBillingTier  records] from ROI_RequestorTypeToBillingTier where [Created_By_Seq] < 0
Select count(*)  AS [ROI_MediaType records] from ROI_MediaType where [Created_By_Seq] < 0
Select count(*)  AS [ROI_BillingTierDetail  records] from ROI_BillingTierDetail where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestEvent  records] from ROI_RequestEvent  where ROI_RequestMain_Seq in (Select ROI_Requestmain_Seq FROM ROI_Requestmain ROI_RequestMain where [Created_By_Seq]< 0)
Select count(*)  AS [ROI_RequestDeliveryCharges  records] from ROI_RequestDeliveryCharges where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestDelivery  records] from ROI_RequestDelivery where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestMain  records] from ROI_RequestMain where [Created_By_Seq]< 0
Select count(*)  AS [ROI_SearchLOV  records] from ROI_SearchLOV where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestorToAddress  records] from ROI_RequestorToAddress where [Created_By_Seq]< 0
Select count(*)  AS [ROI_Address  records] from ROI_Address where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestorToEmailPhone  records] from ROI_RequestorToEmailPhone where [Created_By_Seq]< 0
Select count(*)  AS [ROI_EmailPhone  records] from ROI_EmailPhone where [Created_By_Seq]< 0
Select count(*)  AS [ROI_Requestor  records] from ROI_Requestor where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestorType  records] from ROI_RequestorType where [Created_By_Seq]<0

Delete FROM ROI_RequestorTypeToBillingTier WHERE [Created_by_Seq] < 0
Delete from ROI_BillingTemplateToFeeType where [Created_By_Seq] < 0
Delete from ROI_FeeType where [Created_By_Seq] < 0
Delete from ROI_RequestorTypeToBillingTemplate where [Created_By_Seq] < 0
Delete from ROI_BillingTemplate where [Created_By_Seq] < 0
Delete from ROI_RequestorTypeToBillingTier where [Created_By_Seq] < 0
Delete from ROI_MediaType where [Created_By_Seq] < 0
Delete from ROI_BillingTierDetail where [Created_By_Seq]< 0
Delete from ROI_RequestEvent where ROI_RequestMain_Seq in (Select ROI_Requestmain_Seq FROM ROI_Requestmain ROI_RequestMain where [Created_By_Seq]< 0)
Delete from ROI_RequestDeliveryCharges where [Created_By_Seq]< 0
Delete from ROI_RequestDelivery where [Created_By_Seq]< 0
Delete from ROI_RequestMain where [Created_By_Seq]< 0
Delete from ROI_SearchLOV where [Created_By_Seq]< 0
Delete from ROI_RequestorToAddress where [Created_By_Seq]< 0
Delete from ROI_Address where [Created_By_Seq]< 0
Delete from ROI_RequestorToEmailPhone where [Created_By_Seq]< 0
Delete from ROI_EmailPhone where [Created_By_Seq]< 0
Delete from ROI_Requestor where [Created_By_Seq]< 0
Delete from ROI_RequestorType where [Created_By_Seq]<0

Print 'After cleaning the tables'
Select count(*)  AS [ROI_RequestorTypeToBillingTier records] FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTier] WHERE Created_by_Seq < 0
Select count(*)  AS [ROI_BillingTemplateToFeeType records] from ROI_BillingTemplateToFeeType where [Created_By_Seq] < 0
Select count(*)  AS [ROI_FeeType records] from ROI_FeeType where [Created_By_Seq] < 0
Select count(*)  AS [ROI_RequestorTypeToBillingTemplate  records] from ROI_RequestorTypeToBillingTemplate where [Created_By_Seq] < 0
Select count(*)  AS [ROI_BillingTemplate  records] from ROI_BillingTemplate where [Created_By_Seq] < 0
Select count(*)  AS [ROI_RequestorTypeToBillingTier  records] from ROI_RequestorTypeToBillingTier where [Created_By_Seq] < 0
Select count(*)  AS [ROI_MediaType records] from ROI_MediaType where [Created_By_Seq] < 0
Select count(*)  AS [ROI_BillingTierDetail  records] from ROI_BillingTierDetail where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestEvent  records] from ROI_RequestEvent  where ROI_RequestMain_Seq in (Select ROI_Requestmain_Seq FROM ROI_Requestmain ROI_RequestMain where [Created_By_Seq]< 0)
Select count(*)  AS [ROI_RequestDeliveryCharges  records] from ROI_RequestDeliveryCharges where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestDelivery  records] from ROI_RequestDelivery where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestMain  records] from ROI_RequestMain where [Created_By_Seq]< 0
Select count(*)  AS [ROI_SearchLOV  records] from ROI_SearchLOV where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestorToAddress  records] from ROI_RequestorToAddress where [Created_By_Seq]< 0
Select count(*)  AS [ROI_Address  records] from ROI_Address where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestorToEmailPhone  records] from ROI_RequestorToEmailPhone where [Created_By_Seq]< 0
Select count(*)  AS [ROI_EmailPhone  records] from ROI_EmailPhone where [Created_By_Seq]< 0
Select count(*)  AS [ROI_Requestor  records] from ROI_Requestor where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestorType  records] from ROI_RequestorType where [Created_By_Seq]<0

