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
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace McK.EIG.ROI.Client.Base.View.Common.Tree.Threading
{
	public class AbortThreadPool
	{
		private LinkedList<WorkItem> _callbacks = new LinkedList<WorkItem>();
		private Dictionary<WorkItem, Thread> _threads = new Dictionary<WorkItem, Thread>();

		public WorkItem QueueUserWorkItem(WaitCallback callback)
		{
			return QueueUserWorkItem(callback, null);
		}

		public WorkItem QueueUserWorkItem(WaitCallback callback, object state)
		{
			if (callback == null) throw new ArgumentNullException("callback");

			WorkItem item = new WorkItem(callback, state, ExecutionContext.Capture());
			lock (_callbacks)
			{
				_callbacks.AddLast(item);
			}
			ThreadPool.QueueUserWorkItem(new WaitCallback(HandleItem));
			return item;
		}

		private void HandleItem(object ignored)
		{
			WorkItem item = null;
			try
			{
				lock (_callbacks)
				{
					if (_callbacks.Count > 0)
					{
						item = _callbacks.First.Value;
						_callbacks.RemoveFirst();
					}
					if (item == null)
						return;
					_threads.Add(item, Thread.CurrentThread);

				}
				ExecutionContext.Run(item.Context,
					delegate { item.Callback(item.State); }, null);
			}
			finally
			{
				lock (_callbacks)
				{
					if (item != null)
						_threads.Remove(item);
				}
			}
		}

		public bool IsMyThread(Thread thread)
		{
			lock (_callbacks)
			{
				foreach (Thread t in _threads.Values)
				{
					if (t == thread)
						return true;
				}
				return false;
			}
		}

		public WorkItemStatus Cancel(WorkItem item, bool allowAbort)
		{
			if (item == null)
				throw new ArgumentNullException("item");
			lock (_callbacks)
			{
				LinkedListNode<WorkItem> node = _callbacks.Find(item);
				if (node != null)
				{
					_callbacks.Remove(node);
					return WorkItemStatus.Queued;
				}
				else if (_threads.ContainsKey(item))
				{
					if (allowAbort)
					{
						_threads[item].Abort();
						_threads.Remove(item);
						return WorkItemStatus.Aborted;
					}
					else
						return WorkItemStatus.Executing;
				}
				else
					return WorkItemStatus.Completed;
			}
		}

		public void CancelAll(bool allowAbort)
		{
			lock (_callbacks)
			{
				_callbacks.Clear();
				if (allowAbort)
				{
					foreach (Thread t in _threads.Values)
						t.Abort();
				}
			}
		}
	}
}
