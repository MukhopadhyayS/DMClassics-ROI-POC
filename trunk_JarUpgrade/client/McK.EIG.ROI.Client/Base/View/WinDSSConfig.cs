using System;
using System.Windows.Forms;
using Microsoft.Win32;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Security.AccessControl;
//checkmarx Command string injection changes
using McK.EIG.ROI.Client.Base.Model;
namespace McK.EIG.ROI.Client.Base.View
{
    public partial class WinDSSConfig
    {
         [DllImport("kernel32", SetLastError = true, CallingConvention = CallingConvention.Winapi)]
        public extern static IntPtr LoadLibrary(string libraryName);

        [System.Runtime.InteropServices.DllImport("kernel32", SetLastError = true, CallingConvention = CallingConvention.Winapi)]
        public extern static IntPtr GetProcAddress(IntPtr hwnd, string procedureName);

        private delegate bool IsWow64ProcessDelegate([In] IntPtr handle, [Out] out bool isWow64Process);
        private int retryRegisterCount;
        public WinDSSConfig()
        {
        }
        
        private static IsWow64ProcessDelegate GetIsWow64ProcessDelegate()
        {
            IntPtr handle = LoadLibrary("kernel32");

            if (handle != IntPtr.Zero)
            {
                IntPtr fnPtr = GetProcAddress(handle, "IsWow64Process");

                if (fnPtr != IntPtr.Zero)
                {
                    return (IsWow64ProcessDelegate)Marshal.GetDelegateForFunctionPointer((IntPtr)fnPtr, typeof(IsWow64ProcessDelegate));
                }
            }

            return null;
        }

        private static bool Is32BitProcessOn64BitProcessor()
        {
            IsWow64ProcessDelegate fnDelegate = GetIsWow64ProcessDelegate();

            if (fnDelegate == null)
            {
                return false;
            }

            bool isWow64;
            bool retVal = fnDelegate.Invoke(Process.GetCurrentProcess().Handle, out isWow64);

            if (retVal == false)
            {
                return false;
            }

            return isWow64;
        }

        private bool Is64BitOS()
        {
            bool OS64bit = false;
            if (IntPtr.Size == 8 || (IntPtr.Size == 4 && Is32BitProcessOn64BitProcessor()))
            {
                OS64bit = true;
            }
            else
            {
                OS64bit = false;
                
            }

            return OS64bit;
        }


        private void CreateRegistry()
        {
            try
            {
                string filePath =  Application.StartupPath ;

                Process reg = new Process();

                if(Is64BitOS())
                    reg.StartInfo.FileName = "WinDSSReg_64.bat";
                else
                    reg.StartInfo.FileName = "WinDSSReg_32.bat";

                //reg.StartInfo.Arguments = McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "WINDSS_DATABASE") + " " +
                //    McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "WINDSS_USER") + " " + McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "WINDSS_PSWD");
                //checkmarx Command string injection changes
                reg.StartInfo.Arguments = McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue(ROIConstants.WinDSSSectionName, ROIConstants.WinDSSDBKey) + " " +
                   McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue(ROIConstants.WinDSSSectionName, ROIConstants.WinDSSUserKey) + " " + McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue(ROIConstants.WinDSSSectionName, ROIConstants.WinDSSPwdKey);
                reg.StartInfo.UseShellExecute = true;
                reg.StartInfo.CreateNoWindow = true;
                reg.StartInfo.Verb = "runas";
                reg.Start();
                reg.WaitForExit(10000);
                reg.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }           
        }
        
        public void DefaultSetup()
        {            
            try
            {   
                string user = Environment.UserDomainName + "\\" + Environment.UserName;

                RegistrySecurity rs = new RegistrySecurity();

                // Allow the current user to read and delete the key. 
                rs.AddAccessRule(new RegistryAccessRule(user,
                    RegistryRights.FullControl | RegistryRights.Delete,
                    InheritanceFlags.None,
                    PropagationFlags.None,
                    AccessControlType.Allow));

                
                RegistryKey  masterKey = Registry.LocalMachine.OpenSubKey("SOFTWARE\\IMNET\\EPRS\\Cabinet", RegistryKeyPermissionCheck.ReadWriteSubTree, RegistryRights.FullControl);
                if (masterKey == null)
                {
                    CreateRegistry();
                }
                else
                {
                    object obj = masterKey.GetValue("DBServer");

                    if (obj != null)
                    {
                        String dbServerName = obj as String;
                        if(dbServerName != McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI","WINDSS_DATABASE"))
                        {
                            CreateRegistry();
                        }
                    }

                    masterKey = Registry.LocalMachine.OpenSubKey("SOFTWARE\\IMNET\\EPRS\\IE\\Database", RegistryKeyPermissionCheck.ReadWriteSubTree, RegistryRights.FullControl);
                    obj = masterKey.GetValue("User");

                    if (obj != null)
                    {
                        String dbuserName= obj as String;
                        if (dbuserName != McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "WINDSS_USER"))
                        {
                            CreateRegistry();
                        }
                    }

                    obj = masterKey.GetValue("Pswd");

                    if (obj != null)
                    {
                        String dbpswd = obj as String;
                        if (dbpswd != McK.EIG.ROI.Client.Base.Controller.INIFile.ReadValue("ROI", "WINDSS_PSWD"))
                        {
                            CreateRegistry();
                        }
                    }      
                }

                //Check if C:\imnet\Common exists or not. create if not present.
                bool exists = System.IO.Directory.Exists("C:\\imnet\\Common");
                if (!exists)
                    System.IO.Directory.CreateDirectory("C:\\imnet\\Common");

                //Check if config files exists or not. create if not present.
                exists = System.IO.File.Exists("C:\\imnet\\Common\\McKesson.eig.WinDssLogConfig.xml");
                if (!exists)
                    System.IO.File.Copy("McKesson.eig.WinDssLogConfig.xml", "C:\\imnet\\Common\\McKesson.eig.WinDssLogConfig.xml", true);
                exists = System.IO.File.Exists("C:\\imnet\\Common\\McKesson.eig.WebServiceLogConfig.xml");
                if (!exists)
                    System.IO.File.Copy("McKesson.eig.WebServiceLogConfig.xml", "C:\\imnet\\Common\\McKesson.eig.WebServiceLogConfig.xml", true);
            }
            catch (Exception ex)
            {
            }
        }
    }
}
