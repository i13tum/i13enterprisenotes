package de.tum.i13.controller;

import de.tum.i13.model.Note;
import de.tum.i13.service.NoteService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;
import java.util.Map;

@Model
@Path("/edit")
@ManagedBean
public class EditNoteController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private NoteService noteService;

    @Produces
    @Named
    private Note editNote;

    @PostConstruct
    public void initNewMember() {
        Map<String, String> getParameters = facesContext.getExternalContext().getRequestParameterMap();

        if(getParameters.containsKey("id")) {
            editNote = noteService.getNote(Long.parseLong(getParameters.get("id")));
        }
        else {
            //TODO: redirect to all here
            editNote = new Note();
        }
    }

    public void update() throws Exception {
        try {
            noteService.update(editNote);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Note updated successful");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Note was not  updated successful");
            facesContext.addMessage(null, m);
        }
    }
}
