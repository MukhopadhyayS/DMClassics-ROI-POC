using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;

namespace McK.EIG.ROI.Client.Base.Controller
{
    public class CustomWebServicesClientProtocol : Microsoft.Web.Services3.WebServicesClientProtocol
    {
        private string secureToken;
        private string jSession;

        public void SetSecureToken(string token)
        {
            secureToken = token;
        }

        public void SetjSessionid(string jsessionVal)
        {
            jSession = jsessionVal;
        }

        protected override WebRequest GetWebRequest(Uri uri)
        {
            HttpWebRequest request = (HttpWebRequest)base.GetWebRequest(uri);

            WebHeaderCollection myWebHeaderCollection = request.Headers;

            myWebHeaderCollection.Add("tenantid", "mpf");

            if (!string.IsNullOrEmpty(secureToken))
            {
                secureToken = secureToken.Substring(3);
            }

            myWebHeaderCollection.Add("ca", "ROI_CLIENT");
            myWebHeaderCollection.Add("st", secureToken);
            myWebHeaderCollection.Add("JSESSION", jSession);

            if (!string.IsNullOrEmpty(jSession))
            {
                char []paramsep = new char[1]{';'};
                String []value = jSession.Split(paramsep);

                Array arrayVal = value as Array;
                string modJsession = value[0];
                for (int count = 2; count < arrayVal.Length; count++)
                {                    
                    modJsession = modJsession + ";" + value[count];
                }

                CookieContainer.SetCookies(request.RequestUri, modJsession);
            }

            return request;
        }

    }
}
