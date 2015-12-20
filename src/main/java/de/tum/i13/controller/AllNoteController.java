package de.tum.i13.controller;

import de.tum.i13.data.NoteRepository;
import de.tum.i13.model.Note;
import de.tum.i13.service.NoteService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

public class AllNoteController {
    @Inject
    private FacesContext facesContext;

    @Inject
    private NoteService noteService;

    @Inject
    private NoteRepository repository;

    @Produces
    @Named
    private List<Note> allNotes;

    @PostConstruct
    public void initNewMember() {
        allNotes = repository.getAllNonFinishedNotesOrderedByCompletePercentage();
    }

}
