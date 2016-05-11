#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion

using System;
using System.Diagnostics;
using System.Configuration;
using System.IO;

namespace ROIConfigUtil
{
    class Program
    {
        static void Main(string[] args)
        {
            string assemblyLocation = System.Reflection.Assembly.GetExecutingAssembly().Location;
            assemblyLocation =  assemblyLocation.Substring(0, assemblyLocation.LastIndexOf("\\"));            
            Process p = new Process();
            if (args.Length > 0)
            {
                if (!string.IsNullOrEmpty(args[0]))
                {
                    string batchFilePath = assemblyLocation + "\\" + args[0];
                    if (File.Exists(batchFilePath))
                    {
                        System.Diagnostics.Process.Start(assemblyLocation + "\\" + args[0]);
                    }
                    else
                    {
                        Console.WriteLine(args[0] + " file is not availble in the current location.");
                        Console.Read();
                    }
                }
            }
            else
            {
                string batchFilePath = ConfigurationSettings.AppSettings["batchfilename"];
                if (File.Exists(batchFilePath))
                {
                    System.Diagnostics.Process.Start(assemblyLocation + "\\" + batchFilePath);
                }
                else
                {
                    Console.WriteLine(batchFilePath + " file is not availble in the current location.");
                    Console.Read();
                }
            }           
        }
    }
}
