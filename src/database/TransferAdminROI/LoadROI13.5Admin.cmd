Title LOAD ROI13.5 Admin data
@Echo off
rem LoadROI13.5Admin.cmd
rem HPF version 13.5 ROI admin data import-- this script will load data
cls
rem
rem Author :  RC
rem Date   :  7/15/2009

rem Usage is as follows:  LoadROI13.5Admin.cmd <target servername> <sa passwd> <database name>
rem 
rem 1 = servername
rem 2 = sa password
rem 3 = database name

echo "X" > .\LoadROI13.5Admin.log
echo "X" > .\deleteROI13.5Admin.log

del .\LoadROI13.5Admin.log
del .\DeleteROI13.5Admin.log

echo Are you prepared to delete all ROI admin related data from
echo servername %1
echo in database name %3
echo If you are not prepared to delete all ROI admin related data press CTRL-C or
pause
echo Deleting ROI admin data in server %1 database %3
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_SalesTaxRate" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_PaymentMethod" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_Weight" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_Reason" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_DocType" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_DeliveryMethod" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_LetterTemplate" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_LetterTemplateFile" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_BillingTemplateToFeeType" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_FeeType" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorTypeToBillingTemplate" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_BillingTemplate" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorTypeToBillingTier" -m0 >>.\deleteROI13.5Admin.log
rem sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_Requestor" -m0 >>.\deleteROI13.5Admin.log
rem sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorType" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_BillingTierDetail" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_BillingTier" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_MediaType" -m0 >>.\deleteROI13.5Admin.log


sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorToContact" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_ContactToEmailPhone" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_ContactToAddress" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_ContactToCreditCard" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_Contact" -m0 >>.\deleteROI13.5Admin.log

sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorRestriction" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorToEmailPhone" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorToAddress" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_Requestor" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_RequestorType" -m0 >>.\deleteROI13.5Admin.log

sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_Address" -m0 >>.\deleteROI13.5Admin.log

sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_ContactType" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_CreditCard" -m0 >>.\deleteROI13.5Admin.log

sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_EmailPhone" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_EmailPhoneType" -m0 >>.\deleteROI13.5Admin.log
sqlcmd /S %1 /d %3 /U sa /P %2 -Q "Delete from ROI_AddressType" -m0 >>.\deleteROI13.5Admin.log

echo Review then close the DeleteROI13.5Admin.log log file to continue
rem pause
notepad .\deleteROI13.5Admin.log
echo If an error exists in the deleteROI13.5Admin.log log file press CTRL-C or
pause

echo Starting copy of ROI 13.5 admin tables
echo Starting copy of ROI 13.5 admin tables >> .\LoadROI13.5Admin.log

Rem 1
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_SalesTaxRate Table Start
echo ROI_SalesTaxRate >> .\LoadROI13.5Admin.log
bcp %3..ROI_SalesTaxRate  in .\ROI_SalesTaxRate.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 2
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_PaymentMethod Table Start
echo ROI_PaymentMethod >> .\LoadROI13.5Admin.log
bcp %3..ROI_PaymentMethod  in .\ROI_PaymentMethod.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 3
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_Weight Table Start
echo ROI_Weight >> .\LoadROI13.5Admin.log
bcp %3..ROI_Weight  in .\ROI_Weight.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 4
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_Reason Table Start
echo ROI_Reason >> .\LoadROI13.5Admin.log
bcp %3..ROI_Reason in .\ROI_Reason.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 5
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_DeliveryMethod Table Start
echo ROI_DeliveryMethod >> .\LoadROI13.5Admin.log
bcp %3..ROI_DeliveryMethod in .\ROI_DeliveryMethod.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 6
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_DocType Table Start
echo ROI_DocType >> .\LoadROI13.5Admin.log
bcp %3..ROI_DocType in .\ROI_DocType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 7
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_LetterTemplateFile Table Start
echo ROI_LetterTemplateFile >> .\LoadROI13.5Admin.log
bcp %3..ROI_LetterTemplateFile in .\ROI_LetterTemplateFile.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 8
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_LetterTemplate Table Start
echo ROI_LetterTemplate >> .\LoadROI13.5Admin.log
bcp %3..ROI_LetterTemplate in .\ROI_LetterTemplate.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 9
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_FeeType Table Start
echo ROI_FeeType >> .\LoadROI13.5Admin.log
bcp %3..ROI_FeeType in .\ROI_FeeType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 10
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_BillingTemplate Table Start
echo ROI_BillingTemplate >> .\LoadROI13.5Admin.log
bcp %3..ROI_BillingTemplate in .\ROI_BillingTemplate.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 11
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_BillingTemplateToFeeType Table Start
echo ROI_BillingTemplateToFeeType >> .\LoadROI13.5Admin.log
bcp %3..ROI_BillingTemplateToFeeType in .\ROI_BillingTemplateToFeeType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 12
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorType Table Start
echo ROI_RequestorType >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorType in .\ROI_RequestorType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 13

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorTypeToBillingTemplate Table Start
echo ROI_RequestorTypeToBillingTemplate >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorTypeToBillingTemplate in .\ROI_RequestorTypeToBillingTemplate.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 14
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_MediaType Table Start
echo ROI_MediaType >> .\LoadROI13.5Admin.log
bcp %3..ROI_MediaType in .\ROI_MediaType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 15
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_BillingTier Table Start
echo ROI_BillingTier >> .\LoadROI13.5Admin.log
bcp %3..ROI_BillingTier in .\ROI_BillingTier.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 16
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_BillingTierDetail Table Start
echo ROI_BillingTierDetail >> .\LoadROI13.5Admin.log
bcp %3..ROI_BillingTierDetail in .\ROI_BillingTierDetail.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
Rem 17
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorTypeToBillingTier Table Start
echo ROI_RequestorTypeToBillingTier >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorTypeToBillingTier in .\ROI_RequestorTypeToBillingTier.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

Rem 18
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_AddressType Table Start
echo ROI_AddressType >> .\LoadROI13.5Admin.log
bcp %3..ROI_AddressType in .\ROI_AddressType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
echo Finished

Rem 19
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_EmailPhoneType Table Start
echo ROI_EmailPhoneType >> .\LoadROI13.5Admin.log
bcp %3..ROI_EmailPhoneType in .\ROI_EmailPhoneType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
echo Finished

Rem 19
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_EmailPhone Table Start
echo ROI_EmailPhone >> .\LoadROI13.5Admin.log
bcp %3..ROI_EmailPhone in .\ROI_EmailPhone.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
echo Finished

Rem 19
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_CreditCard Table Start
echo ROI_CreditCard >> .\LoadROI13.5Admin.log
bcp %3..ROI_CreditCard in .\ROI_CreditCard.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
echo Finished

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_ContactType Table Start
echo ROI_ContactType >> .\LoadROI13.5Admin.log
bcp %3..ROI_ContactType in .\ROI_ContactType.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
echo Finished

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_Address Table Start
echo ROI_Address >> .\LoadROI13.5Admin.log
bcp %3..ROI_Address in .\ROI_Address.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log
echo Finished


Rem 18
echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_Requestor Table Start
echo ROI_Requestor >> .\LoadROI13.5Admin.log
bcp %3..ROI_Requestor in .\ROI_Requestor.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log


echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorToAddress Table Start
echo ROI_RequestorToAddress >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorToAddress in .\ROI_RequestorToAddress.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorToEmailPhone Table Start
echo ROI_RequestorToEmailPhone >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorToEmailPhone in .\ROI_RequestorToEmailPhone.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorRestriction  Table Start
echo ROI_RequestorRestriction  >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorRestriction  in .\ROI_RequestorRestriction.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_Contact  Table Start
echo ROI_Contact  >> .\LoadROI13.5Admin.log
bcp %3..ROI_Contact  in .\ROI_Contact.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_ContactToCreditCard Table Start
echo ROI_ContactToCreditCard  >> .\LoadROI13.5Admin.log
bcp %3..ROI_ContactToCreditCard in .\ROI_ContactToCreditCard.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_ContactToAddress Table Start
echo ROI_ContactToAddress  >> .\LoadROI13.5Admin.log
bcp %3..ROI_ContactToAddress in .\ROI_ContactToAddress.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log


echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_ContactToEmailPhone Table Start
echo ROI_ContactToEmailPhone  >> .\LoadROI13.5Admin.log
bcp %3..ROI_ContactToEmailPhone in .\ROI_ContactToEmailPhone.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log

echo Table Start.................................................................>> .\LoadROI13.5Admin.log
echo ROI_RequestorToContact Table Start
echo ROI_RequestorToContact  >> .\LoadROI13.5Admin.log
bcp %3..ROI_RequestorToContact in .\ROI_RequestorToContact.dat -n -U sa -P %2 -S %1 -E >> .\LoadROI13.5Admin.log


echo Finished
echo Finished

echo Review then close the LoadROI13.5Admin.log log file to complete the import
rem pause

call notepad ./LoadROI13.5Admin.log


