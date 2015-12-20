package de.tum.i13.data;

import de.tum.i13.model.Note;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class NoteListProducer {

    @Inject
    private NoteRepository noteRepository;

    private List<Note> notes;


    @Produces
    @Named
    public List<Note> getNotes() {
        return notes;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Note note) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        notes = noteRepository.getAllNonFinishedNotesOrderedByCompletePercentage();
    }
}
