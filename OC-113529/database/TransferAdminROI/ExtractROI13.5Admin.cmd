Title Extract HPF 13.5 ROI Admin data
@echo off
rem ExtractROI13.5Admin.cmd
rem HPF ROI version 13.5 extract
rem
rem Author :  RC
rem Date   :  07/15/2009
rem Usage is as follows:  ExtractROI13.5Admin.cmd <Source servername> <sa passwd> <database name>
rem 
rem %1 = servername
rem %2 = sa password
rem %3 = database name

echo "X" > .\ExtractROI13.5Admin.log
del .\ExtractROI13.5Admin.log
cls
echo Extracting data from server %1
echo Extracting data from server %1>> .\ExtractROI13.5Admin.log
echo extracting data from database %3
echo extracting data from database %3>> .\ExtractROI13.5Admin.log
echo If the information above is incorrect press CTRL-C or
pause
 
echo Starting extract of ROI admin Tables
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_SalesTaxRate >> .\ExtractROI13.5Admin.log
bcp %3..ROI_SalesTaxRate out .\ROI_SalesTaxRate.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_PaymentMethod >> .\ExtractROI13.5Admin.log
bcp %3..ROI_PaymentMethod out .\ROI_PaymentMethod.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_Weight  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_Weight out .\ROI_Weight.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_Reason  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_Reason out .\ROI_Reason.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_DeliveryMethod  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_DeliveryMethod out .\ROI_DeliveryMethod.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_DocType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_DocType out .\ROI_DocType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_LetterTemplateFile  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_LetterTemplateFile out .\ROI_LetterTemplateFile.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_LetterTemplate  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_LetterTemplate out .\ROI_LetterTemplate.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_BillingTemplateToFeeType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_BillingTemplateToFeeType out .\ROI_BillingTemplateToFeeType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_BillingTemplate  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_BillingTemplate out .\ROI_BillingTemplate.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_FeeType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_FeeType out .\ROI_FeeType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_RequestorTypeToBillingTemplate  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorTypeToBillingTemplate out .\ROI_RequestorTypeToBillingTemplate.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_RequestorTypeToBillingTier  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorTypeToBillingTier out .\ROI_RequestorTypeToBillingTier.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_RequestorType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorType out .\ROI_RequestorType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_BillingTier  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_BillingTier out .\ROI_BillingTier.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_BillingTierDetail  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_BillingTierDetail out .\ROI_BillingTierDetail.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log
echo ............................................................................>> .\ExtractROI13.5Admin.log
echo ROI_MediaType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_MediaType out .\ROI_MediaType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_AddressType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_AddressType out .\ROI_AddressType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_EmailPhoneType  >> .\ExtractROI13.5Admin.log
bcp %3..ROI_EmailPhoneType out .\ROI_EmailPhoneType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_EmailPhone >> .\ExtractROI13.5Admin.log
bcp %3..ROI_EmailPhone out .\ROI_EmailPhone.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_CreditCard >> .\ExtractROI13.5Admin.log
bcp %3..ROI_CreditCard out .\ROI_CreditCard.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_ContactType >> .\ExtractROI13.5Admin.log
bcp %3..ROI_ContactType out .\ROI_ContactType.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_Address >> .\ExtractROI13.5Admin.log
bcp %3..ROI_Address out .\ROI_Address.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log


echo ROI_Requestor >> .\ExtractROI13.5Admin.log
bcp %3..ROI_Requestor out .\ROI_Requestor.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_RequestorToAddress>> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorToAddress out .\ROI_RequestorToAddress.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_RequestorToEmailPhone >> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorToEmailPhone out .\ROI_RequestorToEmailPhone.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_RequestorRestriction >> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorRestriction out .\ROI_RequestorRestriction.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log


echo ROI_Contact >> .\ExtractROI13.5Admin.log
bcp %3..ROI_Contact out .\ROI_Contact.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log


echo ROI_ContactToCreditCard >> .\ExtractROI13.5Admin.log
bcp %3..ROI_ContactToCreditCard out .\ROI_ContactToCreditCard.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_ContactToAddress >> .\ExtractROI13.5Admin.log
bcp %3..ROI_ContactToAddress out .\ROI_ContactToAddress.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log


echo ROI_ContactToEmailPhone >> .\ExtractROI13.5Admin.log
bcp %3..ROI_ContactToEmailPhone out .\ROI_ContactToEmailPhone.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log

echo ROI_RequestorToContact >> .\ExtractROI13.5Admin.log
bcp %3..ROI_RequestorToContact out .\ROI_RequestorToContact.dat /n /U sa /P %2 /S %1 >> .\ExtractROI13.5Admin.log



echo Review then close the ExtractROI13.5Admin log file
call notepad .\ExtractROI13.5Admin.log
