package ru.bgcrm.plugin.mobile;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import ru.bgcrm.model.BGException;
import ru.bgcrm.struts.action.BaseAction;
import ru.bgcrm.util.ParameterMap;

public class GMS extends ru.bgcrm.util.Config {
	
	private static final Logger log = Logger.getLogger(GMS.class);

	private static final String SERVER_URL = "http://mob.bgcrm.ru/cgi/server.py";
	private static final String PARAM_COMMAND = "command";
	private static final String PARAM_TO = "to";
	private static final String PARAM_DATA = "data";
	private static final String MESSAGE_COMMAND = "message";

	public GMS(ParameterMap setup) {
		super(setup);
	}

	public void sendMessage(String key, String subject, String text) throws BGException {
		try {
			Map<String, String> data = new HashMap<>();
			data.put("subject", subject);
			data.put("type", "message");
			data.put("message", text);

			URIBuilder url = new URIBuilder(SERVER_URL);
			url.addParameter(PARAM_COMMAND, MESSAGE_COMMAND);
			url.addParameter(PARAM_TO, key);
			url.addParameter(PARAM_DATA, BaseAction.mapper.writeValueAsString(data));

			Request req = Request.Get(url.build());
			if (log.isDebugEnabled())
				log.debug("Sending: " + req);

			String result = req.execute().returnContent().asString(Charset.forName("UTF-8"));
			if (log.isDebugEnabled())
				log.debug("Response: " + result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void sendUpdateStateCommand(String key) throws BGException {}

}
