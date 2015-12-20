package de.tum.i13.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import de.tum.i13.model.Note;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import de.tum.i13.service.NoteService;
import de.tum.i13.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class NoteRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Note.class, NoteService.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    NoteService noteService;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        Note newNote = new Note();
        newNote.setTitel("Buy Milk");
        newNote.setDescription("special fat free milk");
        newNote.setPercentDone(0);
        noteService.create(newNote);
        assertNotNull(newNote.getId());
        log.info(newNote.getTitel() + " was persisted with id " + newNote.getId());
    }

}
