package ru.bgcrm.plugin.mobile.struts.action.open;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.bgcrm.cache.ProcessQueueCache;
import ru.bgcrm.cache.UserCache;
import ru.bgcrm.cache.UserNewsCache;
import ru.bgcrm.event.client.NewsInfoEvent;
import ru.bgcrm.model.BGMessageException;
import ru.bgcrm.model.process.Queue;
import ru.bgcrm.model.process.queue.config.SavedFiltersConfig;
import ru.bgcrm.model.process.queue.config.SavedFiltersConfig.SavedFilterSet;
import ru.bgcrm.model.user.User;
import ru.bgcrm.plugin.mobile.dao.MobileDAO;
import ru.bgcrm.plugin.mobile.model.Account;
import ru.bgcrm.struts.action.BaseAction;
import ru.bgcrm.struts.form.DynActionForm;
import ru.bgcrm.util.Utils;
import ru.bgcrm.util.sql.ConnectionSet;
import ru.bgcrm.worker.FilterEntryCounter;

public class UserAction
	extends BaseAction
{
	public ActionForward state(ActionMapping mapping, DynActionForm form, ConnectionSet conSet) throws Exception {
		form.setResponseType(DynActionForm.RESPONSE_TYPE_JSON);

		String key = form.getParam("key");
		if (Utils.isBlankString(key))
			throw new IllegalArgumentException();

		Account account = new MobileDAO(conSet.getConnection()).findAccount(key, User.OBJECT_TYPE);
		if (account == null)
			throw new BGMessageException("Account isn't registred");

	    // непрочитанные новости и сообщения
		NewsInfoEvent event = UserNewsCache.getUserEvent( conSet.getConnection(), account.getObjectId() );		
		form.getResponse().setData("news", event);	
		
		// счётчики
		List<Counter> counters = new ArrayList<>();
		form.getResponse().setData("counters", counters);

		FilterEntryCounter counter = FilterEntryCounter.getInstance();
		SavedFiltersConfig config = UserCache.getUser(account.getObjectId()).getPersonalizationMap()
				.getConfig(SavedFiltersConfig.class);

		Map<Integer, SavedFilterSet> topFilters = config.getTopFilters();
		for (SavedFilterSet topFilter : topFilters.values()) {
			Queue queue = ProcessQueueCache.getQueue(topFilter.getQueueId());
			int count = counter.parseUrlAndGetCountSync(queue, topFilter.getUrl(), form.getUser());
			
			Counter cnt = new Counter();
			cnt.title = topFilter.getTitle();
			cnt.color = topFilter.getColor();
			cnt.value = count;
			
			counters.add(cnt);
		}

		return status(conSet, form);
	}
	
	public static class Counter {
		public String title;
		public int value;
		public String color;
	}
}
