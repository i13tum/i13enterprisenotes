package de.tum.i13.controller;

import de.tum.i13.model.Note;
import de.tum.i13.service.NoteService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class NewNoteController {
    @Inject
    private FacesContext facesContext;

    @Inject
    private NoteService noteService;

    @Produces
    @Named
    private Note newNote;

    @PostConstruct
    public void initNewMember() {
        newNote = new Note();
        newNote.setPercentDone(0);
    }

    public void create() throws Exception {
        try {
            noteService.create(newNote);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Created!", "Note created successfully");
            facesContext.addMessage(null, m);
            initNewMember();
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Note was not created successful");
            facesContext.addMessage(null, m);
        }
    }
}
