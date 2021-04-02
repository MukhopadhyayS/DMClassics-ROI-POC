Use CABINET;
SET NOCOUNT ON
Print 'Prior to cleaning the tables'
Select count(*)  AS [ROI_RequestEvent  records] from ROI_RequestEvent  where ROI_RequestMain_Seq in (Select ROI_Requestmain_Seq FROM ROI_Requestmain ROI_RequestMain where [Created_By_Seq]< 0)
Select count(*)  AS [ROI_RequestDeliveryCharges  records] from ROI_RequestDeliveryCharges where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestDelivery  records] from ROI_RequestDelivery where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestMain  records] from ROI_RequestMain where [Created_By_Seq]< 0
Select count(*)  AS [ROI_SearchLOV  records] from ROI_SearchLOV where [Created_By_Seq]< 0

Delete from ROI_RequestEvent  where ROI_RequestMain_Seq in (Select ROI_Requestmain_Seq FROM ROI_Requestmain ROI_RequestMain where [Created_By_Seq]< 0)
Delete from ROI_RequestDeliveryCharges where [Created_By_Seq]< 0
Delete from ROI_RequestDelivery where [Created_By_Seq]< 0
Delete from ROI_RequestMain where [Created_By_Seq]< 0
Delete from ROI_SearchLOV where [Created_By_Seq]< 0

Print 'After cleaning the tables'
Select count(*)  AS [ROI_RequestEvent  records] from ROI_RequestEvent  where ROI_RequestMain_Seq in (Select ROI_Requestmain_Seq FROM ROI_Requestmain ROI_RequestMain where [Created_By_Seq]< 0)
Select count(*)  AS [ROI_RequestDeliveryCharges  records] from ROI_RequestDeliveryCharges where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestDelivery  records] from ROI_RequestDelivery where [Created_By_Seq]< 0
Select count(*)  AS [ROI_RequestMain  records] from ROI_RequestMain where [Created_By_Seq]< 0
Select count(*)  AS [ROI_SearchLOV  records] from ROI_SearchLOV where [Created_By_Seq]< 0

