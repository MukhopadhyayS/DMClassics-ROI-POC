using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.IO;
using System.Windows.Forms;

namespace McK.EIG.ROI.Client.Base.Controller
{
    public class INIFile
    {
        [DllImport("kernel32.dll")]
        private static extern int WritePrivateProfileString(string ApplicationName, string KeyName, string StrValue, string FileName);
        [DllImport("kernel32.dll")]
        private static extern int GetPrivateProfileString(string ApplicationName, string KeyName, string DefaultValue, StringBuilder ReturnString, int nSize, string FileName);

        static string path = Application.StartupPath + "\\" + "Mck.EIG.ROI.Client.App.ini";

        public static void WriteValue(string SectionName, string KeyName, string KeyValue, string FileName)
        {
            WritePrivateProfileString(SectionName, KeyName, KeyValue, FileName);
        }

        public static string ReadValue(string SectionName, string KeyName)
        {
            StringBuilder szStr = new StringBuilder(255);
            GetPrivateProfileString(SectionName, KeyName, "", szStr, 255, path);
            return szStr.ToString().Trim();
        }

        public static string GetROIServerName()
        {
            StringBuilder szStr = new StringBuilder(255);            
            GetPrivateProfileString("ROI", "ip", "", szStr, 255, path);

            string serverName = szStr.ToString().Trim();
            if (string.IsNullOrEmpty(serverName))
            {
                serverName = "localhost";
            }

            return serverName;
        }


        public static string GetROIProtocol()
        {
            StringBuilder szStr = new StringBuilder(255);
            GetPrivateProfileString("ROI", "protocol", "", szStr, 255, path);

            string protocol = szStr.ToString().Trim();
            if (string.IsNullOrEmpty(protocol))
            {
                protocol = "http";
            }

            return protocol;
        }

        public static int GetROIPort()
        {
            StringBuilder szStr = new StringBuilder(255);
            GetPrivateProfileString("ROI", "port", "", szStr, 255, path);

            string port = szStr.ToString().Trim();
            if (string.IsNullOrEmpty(port))
            {
                port = "18080";
            }
           

            return Int32.Parse(port);
        }


        public static string getURLWithINIValues(string server, string restURL)
        {
            StringBuilder szStr = new StringBuilder(255);

            GetPrivateProfileString(server, "protocol", "", szStr, 255, path);
            string protocol = szStr.ToString().Trim();

            if (string.IsNullOrEmpty(protocol))
            {
                protocol = "http";
            }

            GetPrivateProfileString(server, "ip", "", szStr, 255, path);
            string ip = szStr.ToString().Trim();

            if (string.IsNullOrEmpty(ip))
            {
                ip = "localhost";
            }

            GetPrivateProfileString(server, "port", "", szStr, 255, path);
            string port = szStr.ToString().Trim();

            if (string.IsNullOrEmpty(port))
            {
                port = "8080";
            }

            return protocol + "://" + ip + ":" + port + restURL;
        }
    }
}

