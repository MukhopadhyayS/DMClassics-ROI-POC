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

using McK.EIG.ROI.Client.ResourceUtility.Validator;
using McK.EIG.ROI.Client.ResourceUtility.Generator;

namespace McK.EIG.ROI.Client.ResourceUtility
{
    class Start
    {
        static void Main(string[] args)
        {
            try
            {
                Console.WriteLine("Opeartions :");
                Console.WriteLine("1. Resource Validation - RV");
                Console.WriteLine("2. Resource Generation - RG");
                Console.WriteLine("Which one of above do you want to perform(type opeartion code) : ");

                string operationCode = Console.ReadLine();
                switch(operationCode.ToLower())
                {
                    case "rv" : 
                        {
                            McK.EIG.ROI.Client.ResourceUtility.Validator.ResourceValidator validator = new McK.EIG.ROI.Client.ResourceUtility.Validator.ResourceValidator();
                            validator.ValidateResourceFile();
                        }
                        break;
                    case "rg" : 
                        {
                            ResourceGenerator generator = new ResourceGenerator();
                            generator.GenerateResourceFile();
                        }
                        break;
                    default:
                        {
                            Console.WriteLine("Invalid operation code");
                            operationCode = Console.ReadLine();
                            return;
                        }
                }
                
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                Console.Read();
            }
        }
    }
}

