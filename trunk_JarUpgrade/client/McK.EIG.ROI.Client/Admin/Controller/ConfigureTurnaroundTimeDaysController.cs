using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.WebReferences.ConfigureDaysWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {
        #region Methods

        #region Service Methods
        
        /// <summary>
        /// This method is used to retrieve the Turnaround time days configuration.
        /// </summary>
        /// <returns>TurnaroundTimeDay collection</returns>
        public Collection<TurnaroundTimeDay> retrieveDaysStatus()
        {
            log.Debug(logMethodName, configureDaysService.GetType().Name, "retrieveConfigureDaysStatus");
            object response = ROIHelper.Invoke(configureDaysService, "retrieveConfigureDaysStatus", new object[0]);
            Collection<TurnaroundTimeDay> turnAroundTimeDays = new Collection<TurnaroundTimeDay>();
           if (response != null)
            {
                turnAroundTimeDays = MapModel((ConfigureDaysDto[])response);
            }
           return turnAroundTimeDays;
        }

        /// <summary>
        /// Save the changes done in the UI, for Turnaround Time Days.
        /// </summary>
        /// <param name="days"></param>
        /// <returns>true /false</returns>
        public bool UpdateDayStatus(Collection<TurnaroundTimeDay> days)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdateDaysStatus(days))
            {
                throw validator.ClientException;
            }
            ConfigureDaysDto[] serverDays = new ConfigureDaysDto[days.Count];
            for (int i = 0; i < days.Count; i++)
            {
                serverDays[i] = new ConfigureDaysDto();
                serverDays[i] = MapModel(days[i]);
                
            }
            object[] requestParams = new object[] { serverDays};
            log.Debug(logMethodName, configureDaysService.GetType().Name, "updateDaysStatus");
            log.Debug(logParams + "Collection<TurnaroundTimeDay>");
            TurnaroundTimeDay TatDay;
            for (int j = 0; j < days.Count; j++)
            {
               TatDay = days[j].Clone();
               foreach (PropertyDescriptor descriptor in TypeDescriptor.GetProperties(TatDay))
               {
                   log.Debug("\t     [{0} = {1}]", descriptor.Name, descriptor.GetValue(TatDay));
               }
            }
            object response = ROIHelper.Invoke(configureDaysService, "updateDaysStatus", requestParams);
            return (bool)response;


        }

        #endregion

        #region ModalMapper

        /// <summary>
        /// Convert client TurnaroundTimeDay to server ConfigureDaysDto object
        /// </summary>
        /// <param name="client"></param>
        /// <returns>server ConfigureDaysDto object</returns>
        public ConfigureDaysDto MapModel(TurnaroundTimeDay client)
        {
            ConfigureDaysDto server = new ConfigureDaysDto();
            server.dayName = client.Day;
            server.dayStatus = client.StatusOfDay;
            return server;
        }

        /// <summary>
        /// Convert collection of server ConfigureDaysDto to client TurnaroundTimeDay collection
        /// </summary>
        /// <param name="server"></param>
        /// <returns>client TurnaroundTimeDay Collection</returns>
        public Collection<TurnaroundTimeDay> MapModel(ConfigureDaysDto[] server)
        {
            Collection<TurnaroundTimeDay> clientDays=new Collection<TurnaroundTimeDay>();
            TurnaroundTimeDay day;
            foreach(ConfigureDaysDto serverDay in server)
            {
                day = new TurnaroundTimeDay();
                day = MapModel(serverDay);
                clientDays.Add(day);
            }
            return clientDays;
        }

        /// <summary>
        /// Convert server ConfigureDaysDto to TurnarounTimeDay object
        /// </summary>
        /// <param name="server"></param>
        /// <returns>client TurnaroundTimeDay object</returns>
        public TurnaroundTimeDay MapModel(ConfigureDaysDto server)
        {
            TurnaroundTimeDay client = new TurnaroundTimeDay();
            client.Day = server.dayName;
            client.StatusOfDay = server.dayStatus;
            return client;
        }

        #endregion

       #endregion
    }
}
