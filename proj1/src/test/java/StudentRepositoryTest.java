import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentRepositoryTest {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private final EntityManager em = factory.createEntityManager();
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        studentRepository = new StudentRepository(em);
    }

    @Test
    public void shouldReturnAllStudentsWhenDataBaseIsNotEmpty() {
        Student[] students = {
                new Student("Jakub", "Kowalczyk", 729275, "WA", "Architecture", 5),
                new Student("Czeslaw", "Duda", 287464, "WE", "Electricity", 8)
        };

        for (Student student : students) {
            studentRepository.save(student);
        }

        List<Student> studentsFromDatabase = studentRepository.getAll();

        int i = 0;
        for (Student student : studentsFromDatabase) {
            assertEquals(student, students[i]);
            i++;
        }
    }

    @Test
    public void shouldReturnEmptyListWhenDataBaseIsEmpty() {
        List<Student> students = studentRepository.getAll();
        assertTrue(students.isEmpty());
    }

    @Test
    public void shouldAddStudentWhenAddedStudentIsCorrect() {
        Student student = new Student("Stefani", "Germanotta", 123123, "WE", "CompSci", 1);

        assertDoesNotThrow(() -> studentRepository.save(student));
        assertEquals(student, studentRepository.getById(student.getId()));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddedStudentIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.save(null)
        );
        assertEquals("Student cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddedStudentHasEmptyStrings() {
        Student student = new Student("", "", 123123, "", "", 3);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.save(student)
        );
        assertEquals("Passed student has to have all valid parameters", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddedStudentHasNullFields() {
        Student student = new Student(null, null, null, null, null, null);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.save(student)
        );
        assertEquals("All student's attributes cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddedStudentHasNegativeNumbers() {
        Student student = new Student("Stefani", "Germanotta", -123123, "WE", "CompSci", -1);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.save(student)
        );
        assertEquals("Passed student has to have all valid parameters", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAddedStudentHasSameIndexNoAsOtherStudent() {
        Student correctStudent = new Student("Maciek", "Kowalczyk", 777555, "WA", "Architecture", 1);
        Student studentWithSameIndex = new Student("Arkadiusz", "Duda", 777555, "WE", "Electricity", 1);

        studentRepository.save(correctStudent);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.save(studentWithSameIndex)
        );
        assertEquals("Unique index violation", exception.getMessage());
    }

    @Test
    public void shouldSucceedWhenDeletedAnExistingStudent() {
        Student student = new Student("Stefani", "Germanotta", 123123, "WE", "CompSci", 1);

        studentRepository.save(student);
        assertDoesNotThrow(() -> studentRepository.delete(student));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenDeletedANonexistentStudent() {
        Student student = new Student("Stefani", "Germanotta", 123123, "WE", "CompSci", 1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.delete(student)
        );
        assertEquals("There is no such student in the database", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenDeletedStudentIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.delete(null)
        );
        assertEquals("Passed student cannot be null", exception.getMessage());
    }

    @Test
    public void shouldUpdateStudentWhenUpdatedStudentIsCorrect() {
        Student student = new Student("Arkadiusz", "Matczak", 946256, "WE", "Electricity", 3);

        studentRepository.save(student);
        student.setSemesterNo(4);
        studentRepository.updateStudent(student);

        Student updatedStudent = studentRepository.getById(student.getId());

        assertEquals(student, updatedStudent);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUpdatedStudentIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.updateStudent(null)
        );
        assertEquals("Passed student cannot be null", exception.getMessage());
    }


    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUpdatedStudentHasFewNullFields() {
        Student student = new Student("Jacek", "Walesa", 351646, "MINI", "IT", 7);

        studentRepository.save(student);
        student.setSemesterNo(null);
        student.setCourseName(null);
        student.setLastName(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.updateStudent(student)
        );
        assertEquals("All student's attributes cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUpdatedStudentHasFewEmptyStrings() {
        Student student = new Student("Czarek", "Obama", 383494, "MINI", "Math", 6);

        studentRepository.save(student);
        student.setSemesterNo(7);
        student.setFaculty("");
        student.setCourseName("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.updateStudent(student)
        );
        assertEquals("Passed student has to have all valid parameters", exception.getMessage());
    }

    @Test
    public void shouldUpdateStudentWhenUpdatedStudentHasTheSameInfo() {
        Student student = new Student("Stefani", "Germanotta", 123123, "WE", "CompSci", 1);

        studentRepository.save(student);

        assertDoesNotThrow(() -> studentRepository.updateStudent(student));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUpdatedStudentHasSameIndexAsOtherStudent() {
        Student student = new Student("Michal", "Jackowski", 153064, "MINI", "Math", 2);
        Student studentToUpdate = new Student("Kamil", "Marciniak", 873834, "WA", "Architecture", 5);

        studentRepository.save(student);
        studentRepository.save(studentToUpdate);

        studentToUpdate.setIndexNo(student.getIndexNo());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.updateStudent(studentToUpdate)
        );
        assertEquals("Unique index violation", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUpdatedStudentHasNegativeIntegers() {
        Student student = new Student("Stefani", "Germanotta", 123123, "WE", "CompSci", 1);

        studentRepository.save(student);
        student.setSemesterNo(-1);
        student.setIndexNo(-999999);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentRepository.updateStudent(student)
        );
        assertEquals("Passed student has to have all valid parameters", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUpdatedNonexistentStudent() {
        Student student = new Student("Stefani", "Germanotta", 123123, "WE", "CompSci", 1);

        // No studentRepository.save
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> studentRepository.updateStudent(student)
        );
        //TODO
        assertEquals("No student with id 0", exception.getMessage());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenReadNonexistentStudent() {
        // The repository is empty
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> studentRepository.getById(1)
        );
        assertEquals("No student with id 1", exception.getMessage());
    }

}
