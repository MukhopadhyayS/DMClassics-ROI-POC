
-- Moved ROI_RequestorType to another ROI_RequestorType and then delete the source row

declare @sourceROI_RequestorType int, @targetROI_RequestorType int

-- replace the source/target numbers below based on your customer data

select @sourceROI_RequestorType = 999999
select @targetROI_RequestorType = 999999

-- Change ROI_RequestorType_Seq from source to target value

Update ROI_Requestor 
set ROI_RequestorType_Seq = @targetROI_RequestorType
where ROI_RequestorType_Seq = @sourceROI_RequestorType

-- Remove source ROI_RequestorType_Seq

delete ROI_RequestorTypeToBillingTier
where ROI_RequestorType_Seq = @sourceROI_RequestorType

delete ROI_RequestorTypeToBillingTemplate
where ROI_RequestorType_Seq = @sourceROI_RequestorType

delete ROI_RequestorType
where ROI_RequestorType_Seq = @sourceROI_RequestorType
go
