using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using McK.EIG.ROI.Client.Base.Model;
using OCSecurity;


namespace McK.EIG.ROI.Client.Base.Controller
{
    public class OCSecurityWrapper
    {
         private static OCSecurityFacilitator objOCSecurityFacilitator;
         private static bool isDLLInitialized;

         public static bool IsDLLInitialized()
         {
             return isDLLInitialized;
         }

         public static void GetSecureToken()
         {
             try
             {
                 SecurityCredentials securityCredentials;
                 if (UserData.Instance.IsLdapEnabled)
                 {
                     if (UserData.Instance.HpfMappedUserId == null)
                         UserData.Instance.HpfMappedUserId = "";

                     ADUserCredentials objADUser = new ADUserCredentials(UserData.Instance.LDAPUserId, UserData.Instance.DomainSecretWord,
                         UserData.Instance.Domain, UserData.Instance.HpfMappedUserId, null);
                     securityCredentials = objOCSecurityFacilitator.getSecureToken(objADUser);  
                 }
                 else
                 {
                     OCAndOBOUserCredentials objUser = new OCAndOBOUserCredentials(UserData.Instance.UserId, UserData.Instance.UserId, UserData.Instance.SecretWord, null);
                     securityCredentials = objOCSecurityFacilitator.getSecureToken(objUser);
                 }
                 UserData.Instance.RSAToken = "st:" + securityCredentials.getSecureToken();
                 UserData.Instance.JsessionID = securityCredentials.getJsessionId();
                 UserData.Instance.GetURLFormat= securityCredentials.getURLformat();
             }
             catch (Exception ex)
             {
                 if (String.Compare(ex.Message, " Invalid security credentials.") != 0)
                 {
                     throw ex;
                 }
             }
         }

        public static void loadDll()
        {   
            try
            {

                objOCSecurityFacilitator = OCSecurityFacilitatorFactory.createOCSecurityFacilitator();
                objOCSecurityFacilitator.init(McK.EIG.ROI.Client.Base.Controller.INIFile.GetROIServerName(), McK.EIG.ROI.Client.Base.Controller.INIFile.GetROIPort(), McK.EIG.ROI.Client.Base.Controller.INIFile.GetROIProtocol()
                    , "ROI_CLIENT", "mpf", "OCSecuritylog.txt", "DEBUG");
                isDLLInitialized = true;
                
            }
            catch (ArgumentException ae)
            {
                throw new Exception("Error occurred");
            }
            catch (Exception ex)
            {          
                throw new Exception(ex.Message);
            }
        }

        public static System.Security.SecureString encryptData(System.Security.SecureString dataToEncrypt)
        {
            System.Security.SecureString returnStr = null;
            try
            {
                returnStr = objOCSecurityFacilitator.encryptData(dataToEncrypt);

            }
            catch (Exception ex)
            {
                if (String.Compare(ex.Message, " Invalid security credentials.") != 0)
                {
                    throw ex;
                }
            }
           
            return returnStr;            
        }

    }
}
