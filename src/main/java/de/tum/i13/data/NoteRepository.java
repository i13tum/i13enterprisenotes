package de.tum.i13.data;

import de.tum.i13.model.Note;
import de.tum.i13.model.Note_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class NoteRepository {

    @PersistenceContext
    private EntityManager em;

    public Note findById(Long id) {
        return em.find(Note.class, id);
    }


    public List<Note> findAllOrderedByCompleted() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Note> criteria = cb.createQuery(Note.class);
        Root<Note> member = criteria.from(Note.class);

        criteria.select(member).orderBy(cb.asc(member.get(Note_.percentDone)));
        return em.createQuery(criteria).getResultList();
    }

    public List<Note> getAllNonFinishedNotesOrderedByCompletePercentage() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Note> cq = cb.createQuery(Note.class);

        ParameterExpression<Integer> maxPercent = cb.parameter(Integer.class);
        Root<Note> note = cq.from(Note.class);

        cq.where(cb.le(note.get(Note_.percentDone), maxPercent));
        cq.orderBy(cb.asc(note.get(Note_.percentDone)));

        TypedQuery<Note> query = em.createQuery(cq);
        query.setParameter(maxPercent, 100);

        return query.getResultList();
    }
}
