package ru.bgcrm.event.link;

import ru.bgcrm.model.CommonObjectLink;
import ru.bgcrm.struts.form.DynActionForm;

public class LinkRemovingEvent extends LinkAddingEvent {
    public LinkRemovingEvent(DynActionForm form, CommonObjectLink link) {
        super(form, link);
    }
}
