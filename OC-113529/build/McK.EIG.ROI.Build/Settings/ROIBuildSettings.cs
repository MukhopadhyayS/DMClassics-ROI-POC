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
using System.Collections;
using System.Collections.Specialized;
using System.Configuration;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Diagnostics;
using System.Xml;
using System.Xml.XPath;

namespace McK.EIG.ROI.Build.Settings
{
    public partial class ROIBuildSettings : Form
    {
        private ListDictionary devProps;
        private ListDictionary envHomeProps;
        private ListDictionary baseProps;       
        private string devPropsFiles;
        private string batchFile;
        private string envHomeFile;
        private string basePropsFile;
        private string baseProbsXML;
        private XmlDocument baseXML;
        private XmlElement newElement;

        public ROIBuildSettings()
        {
            InitializeComponent();
            InitView();
        }

        private void InitView()
        {
            
            GetConfigurations();           
            InitializeBuildSettings();
            InitializeEnvironmentSettings();
            InitializeBaseProperties();            
        }

        private void InitializeBaseProperties()
        {
            baseProps = new ListDictionary();
            if (CheckFileExists(basePropsFile))
            {
                LoadBasePropertiesFile();
            }
                
        }

        private void LoadBasePropertiesFile()
        {
            try
            {
                FileStream fs = new FileStream(basePropsFile, FileMode.Open, FileAccess.ReadWrite);
                StreamReader sr = new StreamReader(fs);
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    if ((line.IndexOf('=') != -1))
                    {
                        LoadBaseProps(line);
                    }
                }
                sr.Close();
                fs.Close();
                LoadBasePropertiesDGV();
                LoadDevPropertiesDGV();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "ROI Developer Build", MessageBoxButtons.OK, MessageBoxIcon.Error);
                throw ex;
            }
        }

        private void LoadDevPropertiesDGV()
        {
            dgvDevProp.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            gbDevProp.Text = gbDevProp.Text + "  (" + devProps.Count.ToString() + ")";

            DataGridViewColumn devProbColumn1 = new DataGridViewColumn();
            devProbColumn1.ReadOnly = true;
            devProbColumn1.HeaderText = "Variable";
            devProbColumn1.SortMode = DataGridViewColumnSortMode.Automatic;
            devProbColumn1.Width = 250;
            dgvDevProp.Columns.Add(devProbColumn1);

            DataGridViewColumn devProbColumn2 = new DataGridViewColumn();
            devProbColumn2.HeaderText = "Value";            
            devProbColumn2.Width = 656;
            dgvDevProp.Columns.Add(devProbColumn2);

            foreach (DictionaryEntry de in devProps)
            {
                DataGridViewRow buildProbsRow = new DataGridViewRow();
                DataGridViewTextBoxCell buildProbsCell1 = new DataGridViewTextBoxCell();
                buildProbsCell1.Value = de.Key.ToString();
                buildProbsRow.Cells.Add(buildProbsCell1);
                if ((de.Value.ToString().ToLower() != "true") && (de.Value.ToString().ToLower() != "false"))
                {
                    DataGridViewTextBoxCell buildProbsCell2 = new DataGridViewTextBoxCell();
                    buildProbsCell2.Value = de.Value.ToString();
                    buildProbsRow.Cells.Add(buildProbsCell2);

                }
                else
                {
                    DataGridViewCheckBoxCell buildProbsCell2 = new DataGridViewCheckBoxCell();
                    if ((de.Value.ToString().ToLower() == "true"))
                    {
                        buildProbsCell2.Value = true;
                    }
                    else
                    {
                        buildProbsCell2.Value = false;
                    }
                    buildProbsRow.Cells.Add(buildProbsCell2);

                }
                dgvDevProp.Rows.Add(buildProbsRow);
            }
            dgvDevProp.CurrentCell.Selected = true;
            dgvDevProp.CurrentCell = null;
        }

        private void LoadBasePropertiesDGV()
        {


            gbBaseProp.Text = gbBaseProp.Text + "  (" + baseProps.Count.ToString() + ")";
            dgvBaseProp.SelectionMode = DataGridViewSelectionMode.FullRowSelect;

            DataGridViewColumn basePropTypeCol = new DataGridViewColumn();
            basePropTypeCol.HeaderText = "Type";
            basePropTypeCol.SortMode = DataGridViewColumnSortMode.Automatic;
            basePropTypeCol.Width = 100;
            dgvBaseProp.Columns.Add(basePropTypeCol);

            DataGridViewColumn basePropDescCol = new DataGridViewColumn();
            basePropDescCol.HeaderText = "Description";
            basePropDescCol.SortMode = DataGridViewColumnSortMode.Automatic;
            basePropDescCol.Width = 356;
            dgvBaseProp.Columns.Add(basePropDescCol);

            DataGridViewColumn basePropValCol = new DataGridViewColumn();
            basePropValCol.HeaderText = "Value";
            basePropValCol.Width = 200;
            dgvBaseProp.Columns.Add(basePropValCol);

            DataGridViewColumn basePropVarCol = new DataGridViewColumn();
            basePropVarCol.HeaderText = "Variable";
            basePropVarCol.SortMode = DataGridViewColumnSortMode.Automatic;
            basePropVarCol.Width = 250;
            basePropVarCol.ReadOnly = true;
            dgvBaseProp.Columns.Add(basePropVarCol);

           
            

            try
            {
                foreach (DictionaryEntry de in baseProps)
                {
                    DataGridViewRow dgvr = new DataGridViewRow();

                    XmlElement selectedElement = (XmlElement)newElement.SelectSingleNode("/build-properties/build[@id='" + de.Key.ToString() + "']");
                    DataGridViewTextBoxCell baseProbsTypeCell = new DataGridViewTextBoxCell();
                    baseProbsTypeCell.Value = selectedElement.GetAttribute("type");
                    dgvr.Cells.Add(baseProbsTypeCell);

                    DataGridViewTextBoxCell baseProbsDescCell = new DataGridViewTextBoxCell();
                    baseProbsDescCell.Value = selectedElement.InnerText;
                    dgvr.Cells.Add(baseProbsDescCell);                    

                    if ((de.Value.ToString().ToLower() != "true") && (de.Value.ToString().ToLower() != "false"))
                    {
                        DataGridViewTextBoxCell basePropValCell = new DataGridViewTextBoxCell();
                        basePropValCell.Value = de.Value.ToString();
                        dgvr.Cells.Add(basePropValCell);

                    }
                    else
                    {
                        DataGridViewCheckBoxCell basePropValCell = new DataGridViewCheckBoxCell();
                        if ((de.Value.ToString().ToLower() == "true"))
                        {
                            basePropValCell.Value = true;
                        }
                        else
                        {
                            basePropValCell.Value = false;
                        }
                        dgvr.Cells.Add(basePropValCell);

                    }
                    
                    DataGridViewTextBoxCell basePropVarCell = new DataGridViewTextBoxCell();
                    basePropVarCell.Value = de.Key.ToString();
                    dgvr.Cells.Add(basePropVarCell);
                    dgvBaseProp.Rows.Add(dgvr);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

 

        private void LoadBaseProps(string line)
        {
            
            string[] kvp = line.Split('=');
            if (kvp.Length == 2)
            {
                baseProps.Add(kvp[0].Trim(), kvp[1].Trim());
            }   
        }

        #region Build Settings
        /// <summary>
        /// Initialize the build settings tab page
        /// </summary>
        private void InitializeBuildSettings()
        {
            devProps = new ListDictionary();

            if (CheckFileExists(devPropsFiles))
                LoadBuildPropsFile();

        }

       

        /// <summary>
        /// Method that loads the build specific props into ListDictionary object
        /// </summary>
        /// <param name="content"></param>
        private void LoadBuildProps(string content)
        {
            string[] kvp = content.Split('=');
            if (kvp.Length == 2)
            {
                devProps.Add(kvp[0].Trim(), kvp[1].Trim());
            }
        }

        /// <summary>
        /// Method that loads properties from properties file
        /// </summary>
        private void LoadBuildPropsFile()
        {
            try
            {
                FileStream fs = new FileStream(devPropsFiles, FileMode.Open, FileAccess.ReadWrite);
                StreamReader sr = new StreamReader(fs);
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    if (line.IndexOf('=') != -1)
                    {
                        LoadBuildProps(line);
                    }
                }

                sr.Close();
                fs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "ROI Developer Build", MessageBoxButtons.OK, MessageBoxIcon.Error);
                throw ex;
            }
        }
        /// <summary>
        /// Method that overwrites the value in property file 
        /// after changing settings of checkbox controls
        /// </summary>
        private void UpdatePropsFile()
        {
            try
            {
  
                StreamWriter sw = File.CreateText(devPropsFiles);
                IDictionaryEnumerator propsenum = devProps.GetEnumerator();
                while (propsenum.MoveNext())
                {
                    sw.WriteLine(Convert.ToString(propsenum.Key) + "=" + Convert.ToString(propsenum.Value).ToLower());
                }
                sw.Close();
                
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        

        /// <summary>
        /// Initialize the environment settings tab page controls
        /// </summary>
        private void InitializeEnvironmentSettings()
        {
            envHomeProps = new ListDictionary();

            try
            {
                if (CheckFileExists(envHomeFile))
                    LoadEnvSettingsFile();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Environment settings not found:  " + ex.Message);
            }
           

        }

       


        /// <summary>
        /// Method that loads environment settings file
        /// </summary>
        private void LoadEnvSettingsFile()
        {
            try
            {
                FileStream fs = new FileStream(envHomeFile, FileMode.Open, FileAccess.ReadWrite);
                StreamReader sr = new StreamReader(fs);
                
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    if (line.IndexOf('=') != -1)
                    {
                        if (line.IndexOf("set", StringComparison.OrdinalIgnoreCase) != -1)
                        {
                            int startIndex = line.IndexOf("set", StringComparison.OrdinalIgnoreCase) + 3;
                            LoadEnvHomeProps(line.Substring(startIndex, line.Length - startIndex));
                        }
                    }
                }

                sr.Close();
                fs.Close();
                LoadEnvHomeGrid();
              

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "ROI Developer Build", MessageBoxButtons.OK, MessageBoxIcon.Error);
                throw ex;
            }
        }

        private void LoadEnvHomeGrid()
        {
            dgvEnvHome.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            dgvEnvHome.Columns.Add("envVar", "Env Variable");
            dgvEnvHome.Columns.Add("envPath", "Env Path");
            dgvEnvHome.Columns[0].Width = 200;
            dgvEnvHome.Columns[1].Width = 729;
            
            foreach (DictionaryEntry de in envHomeProps)
            {
                dgvEnvHome.Rows.Add(new object[] { de.Key.ToString(), de.Value.ToString() });
            }
            dgvEnvHome.Columns[0].ReadOnly = true;
        }

        /// <summary>
        /// Method that loads the environemnt specific props into ListDictionary object
        /// </summary>
        /// <param name="content"></param>
        private void LoadEnvHomeProps(string content)
        {
            string[] kvp = content.Split('=');            
            if (kvp.Length == 2)
            {
                envHomeProps.Add(kvp[0].Trim(), kvp[1].Trim());               
            }          
            
        }

      

        /// <summary>
        /// Method that overwrites the value in property file 
        /// after changing settings of checkbox controls
        /// </summary>
        private void UpdateEnvSettingsFile()
        {
            try
            {
                StreamWriter sw = File.CreateText(envHomeFile);
                IDictionaryEnumerator envHomePropsEnum = envHomeProps.GetEnumerator();
                while (envHomePropsEnum.MoveNext())
                {
                    sw.WriteLine("set " + Convert.ToString(envHomePropsEnum.Key) + "=" + Convert.ToString(envHomePropsEnum.Value));
                }
                sw.Close();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        #endregion


        /// <summary>
        /// Method that gets the configurations from config file
        /// </summary>
        private void GetConfigurations()
        {
            devPropsFiles = Configuration.GetValue("dev.props.file");
            gbDevProp.Text = devPropsFiles;
            batchFile = Configuration.GetValue("dev.bat.file");
            envHomeFile = Configuration.GetValue("env.bat.file");
            basePropsFile = Configuration.GetValue("base.props.file");
            gbBaseProp.Text = basePropsFile;
            baseProbsXML = Configuration.GetValue("base.probs.xml.file");

            baseXML = new XmlDocument();
            baseXML.Load(baseProbsXML);
            newElement = baseXML.DocumentElement;
            

        }
       
        /// <summary>
        /// Update the props file and env settings file then run the dev build batch file
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnRun_Click(object sender, EventArgs e)
        {
          
            UpdateBatchFile();
            UpdateDevPropertiesFile();
            try
            {
                if (File.Exists(batchFile))
                {
                    Process.Start(batchFile);
                }
                else
                {
                    MessageBox.Show("Could not find file '" + new FileInfo(batchFile).FullName + "'", "ROI Developer Build", MessageBoxButtons.OK, MessageBoxIcon.Error);

                }
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message, "ROI Developer Build", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }          
        }

        private void UpdateBatchFile()
        {
            try
            {
                StreamWriter sw = File.CreateText(envHomeFile);                
                foreach (DataGridViewRow dgvr in dgvEnvHome.Rows)
                {
                   sw.WriteLine("set " + dgvr.Cells[0].Value.ToString() + "=" + dgvr.Cells[1].Value.ToString());
                }                
                sw.Close();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private void UpdateDevPropertiesFile()
        {
            try
            {
                StreamWriter sw = File.CreateText(devPropsFiles);

                foreach (DataGridViewRow dgvr in dgvDevProp.Rows)
                {
                    sw.WriteLine(dgvr.Cells[0].Value.ToString().ToLower() + "=" + dgvr.Cells[1].Value.ToString().ToLower());
                }
                sw.Close();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }


       
        /// <summary>
        /// Check given directory exists or not 
        /// </summary>
        /// <param name="dirPath"></param>
        /// <returns></returns>
        private bool CheckDirectoryExists(string dirPath)
        {
            return Directory.Exists(dirPath);
        }

        /// <summary>
        /// Check whether the file already exist or not
        /// </summary>
        /// <param name="fileName"></param>
        /// <returns></returns>
        private bool CheckFileExists(string fileName)
        {
            return File.Exists(fileName);
        }

        /// <summary>
        /// Quit the application
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btnQuit_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        /// <summary>
        /// The form will be closed when user press Esc key
        /// </summary>
        /// <param name="msg"></param>
        /// <param name="keyData"></param>
        /// <returns></returns>
        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            if (keyData == Keys.Escape)
                Application.Exit();

            return base.ProcessCmdKey(ref msg, keyData);
        }

        private StreamReader GetInputStream(string fileName)
        {
            FileStream fs = new FileStream(fileName, FileMode.Open, FileAccess.Read);
            return new StreamReader(fs);
        }



        private void dgvBaseProp_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex != -1)
            {
                if (devProps.Contains(dgvBaseProp.Rows[e.RowIndex].Cells[3].Value.ToString()) == false)
                {
                    //dgvDevProp.Rows.Add(new object[] { dgvBaseProp.Rows[e.RowIndex].Cells[0].Value.ToString(), dgvBaseProp.Rows[e.RowIndex].Cells[1].Value.ToString() });
                    devProps.Add(dgvBaseProp.Rows[e.RowIndex].Cells[3].Value.ToString(), dgvBaseProp.Rows[e.RowIndex].Cells[2].Value.ToString());

                    DataGridViewRow buildProbsRow = new DataGridViewRow();
                    DataGridViewTextBoxCell buildProbsCell1 = new DataGridViewTextBoxCell();
                    buildProbsCell1.Value = dgvBaseProp.Rows[e.RowIndex].Cells[3].Value.ToString();
                    buildProbsRow.Cells.Add(buildProbsCell1);
                    if ((dgvBaseProp.Rows[e.RowIndex].Cells[2].Value.ToString().ToLower() != "true") && (dgvBaseProp.Rows[e.RowIndex].Cells[2].Value.ToString().ToLower() != "false"))
                    {
                        DataGridViewTextBoxCell buildProbsCell2 = new DataGridViewTextBoxCell();
                        buildProbsCell2.Value = dgvBaseProp.Rows[e.RowIndex].Cells[2].Value.ToString();
                        buildProbsRow.Cells.Add(buildProbsCell2);

                    }
                    else
                    {
                        DataGridViewCheckBoxCell buildProbsCell2 = new DataGridViewCheckBoxCell();
                        if ((dgvBaseProp.Rows[e.RowIndex].Cells[2].Value.ToString().ToLower() == "true"))
                        {
                            buildProbsCell2.Value = true;
                        }
                        else
                        {
                            buildProbsCell2.Value = false;
                        }
                        buildProbsRow.Cells.Add(buildProbsCell2);

                    }
                    dgvDevProp.Rows.Add(buildProbsRow);

                }
                else
                {

                    int i = 0;
                    IDictionaryEnumerator de = devProps.GetEnumerator();
                    while (de.MoveNext())
                    {
                        if (de.Key.ToString() == dgvBaseProp.Rows[e.RowIndex].Cells[3].Value.ToString())
                        {
                            try
                            {
                                dgvDevProp.Rows[i].Selected = true;
                                dgvDevProp.FirstDisplayedScrollingRowIndex = 1;
                                dgvDevProp.SelectedCells[1].Selected = true;
                                dgvDevProp.BeginEdit(true);
                            }
                            catch (Exception ex)
                            {
                                MessageBox.Show(ex.Message + " : " + i);
                            }
                        }
                        else
                        {
                            i++;
                        }
                    }


                }
            }

            gbDevProp.Text = devPropsFiles + "  (" + devProps.Count.ToString() + ")";
        }



        private void dgvDevProp_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            dgvDevProp.Rows[e.RowIndex].Selected = true;
            dgvDevProp.FirstDisplayedScrollingRowIndex = e.RowIndex;
            dgvDevProp.SelectedCells[1].Selected = true;
            dgvDevProp.BeginEdit(true);
            
        }

        private void dgvEnvHome_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            if (CheckDirectoryExists(dgvEnvHome.Rows[e.RowIndex].Cells[e.ColumnIndex].Value.ToString()))
            {
                envHomeProps[dgvEnvHome.Rows[e.RowIndex].Cells[0].Value.ToString()] = dgvEnvHome.Rows[e.RowIndex].Cells[e.ColumnIndex].Value.ToString();
            }
            else
            {
                MessageBox.Show("Invalid Path");
                try
                {
                    dgvEnvHome.Rows[e.RowIndex].Cells[1].Value = envHomeProps[dgvEnvHome.Rows[e.RowIndex].Cells[0].Value.ToString()].ToString();
                    dgvEnvHome.Rows[e.RowIndex].Selected = true;                    
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void dgvEnvHome_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {            
            envHomeProps.Remove(dgvEnvHome.Rows[e.RowIndex].Cells[e.ColumnIndex].Value);
        }

        private void dgvDevProp_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex == 0 && e.RowIndex != -1)
            {
                DialogResult result =  MessageBox.Show("Are you sure to delete the property from dev.properties?", "Remove from dev.properties", MessageBoxButtons.YesNo);

                if (result == DialogResult.Yes)
                {
                    devProps.Remove(dgvDevProp.Rows[e.RowIndex].Cells[0].Value.ToString());
                    dgvDevProp.Rows.Remove(dgvDevProp.Rows[e.RowIndex]);                     
                    dgvDevProp.Refresh();
                    gbDevProp.Text = devPropsFiles + "  (" + devProps.Count.ToString() + ")";
                }
            }
        }

       
    }
}
