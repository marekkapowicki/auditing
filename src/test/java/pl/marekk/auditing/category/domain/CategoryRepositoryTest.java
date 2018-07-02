package pl.marekk.auditing.category.domain;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;

    @Test
    public void shouldFindRevisions() {

        //Given
        Long storedId = repository.save(Categories.old).getId();
        Category newCategory = repository.findById(storedId)
                .map(c -> new Category(c.getId(), "new_category"))
                .orElseThrow(IllegalStateException::new);
        repository.save(newCategory);

        //When
        Optional<Category> found = repository.findById(storedId);
        //Then
        assertThat(found).isPresent();
        Category category = found.get();
        assertThat(category.getName()).isEqualTo("new_category");
//        assertThat(category.getCreationTime()).isNotNull();

        Optional<Revision<Integer, Category>> lastChangeRevision = repository.findLastChangeRevision(storedId);
        assertThat(lastChangeRevision).isPresent();

        //revisions
        Revisions<Integer, Category> revisions = repository.findRevisions(storedId);
        assertThat(revisions.getContent()).hasSize(2);


        Iterator<Revision<Integer, Category>> iterator = revisions.iterator();
        Revision<Integer, Category> first = iterator.next();
        Revision<Integer, Category> second = iterator.next();

        assertThat(repository.findRevision(storedId, first.getRequiredRevisionNumber())).hasValueSatisfying(it -> {
            assertThat(it.getEntity().getName()).isEqualTo("old_category");
        });

        assertThat(repository.findRevision(storedId, second.getRequiredRevisionNumber())).hasValueSatisfying(it -> {
            assertThat(it.getEntity().getName()).isEqualTo("new_category");
        });

    }

    @Test
    public void shouldFindDeletedRevision() {
        //Given
        Long storedId = repository.save(Categories.to_delete).getId();
        repository.delete(repository.findById(storedId).get());

        //When
        Revisions<Integer, Category> revisions = repository.findRevisions(storedId);
        //Then

        assertThat(revisions).hasSize(2);
        assertThat(revisions.getLatestRevision().getEntity().getName()) //
                .isNull();
    }
}