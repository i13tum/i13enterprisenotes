package de.tum.i13.service;

import de.tum.i13.data.NoteRepository;
import de.tum.i13.model.Note;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class NoteService {

    @Inject
    private Logger log;

    @Inject
    private NoteRepository nr;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Note> noteEventSrc;

    public void create(Note note) throws Exception {
        log.info("Create note: " + note.getTitel());
        em.persist(note);
        noteEventSrc.fire(note);
    }

    public void update(Note updated) {
        log.info("Updated note: " + updated.getTitel());
        em.merge(updated);
        noteEventSrc.fire(updated);
    }

    public Note getNote(Long id) {
        return nr.findById(id);
    }
}
