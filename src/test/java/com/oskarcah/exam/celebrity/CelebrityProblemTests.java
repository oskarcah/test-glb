package com.oskarcah.exam.celebrity;

import com.oskarcah.exam.celebrity.application.CelebrityApplication;
import com.oskarcah.exam.celebrity.application.DatabaseSeedDataRunner;
import com.oskarcah.exam.celebrity.components.ProblemSolverImpl;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes= {CelebrityApplication.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DatabaseSeedDataRunner.class))
@ActiveProfiles({"test"})
public class CelebrityProblemTests {

	private Problem p1;

	private Problem p2;

	private ProblemSolverImpl problemSolver;


	@Before
	public void setup() {

		problemSolver  = new ProblemSolverImpl();

		// people problem
		p1 = new Problem();
		p1.setPeople(new ArrayList<>());
		p1.getPeople().add(new Person("Jorge", 0, p1));
		p1.getPeople().add(new Person("Carlos", 1, p1));
		p1.getPeople().add(new Person("Juan", 2, p1));

		// people relations
		p1.setPeopleRelations(new ArrayList<>());
		p1.getPeopleRelations().add(new KnownPerson(p1.getPeople().get(0), p1.getPeople().get(1)));
		p1.getPeopleRelations().add(new KnownPerson(p1.getPeople().get(0), p1.getPeople().get(2)));
		p1.getPeopleRelations().add(new KnownPerson(p1.getPeople().get(2), p1.getPeople().get(0)));
		p1.getPeopleRelations().add(new KnownPerson(p1.getPeople().get(2), p1.getPeople().get(1)));
		p1.getPeopleRelations().add(new KnownPerson(p1.getPeople().get(1), p1.getPeople().get(0)));

		// people problem
		p2 = new Problem();
		p2.setPeople(new ArrayList<>());
		p2.getPeople().add(new Person("Jorge", 0, p2));
		p2.getPeople().add(new Person("Carlos", 1, p2));
		p2.getPeople().add(new Person("Juan", 2, p2));
		p2.getPeople().add(new Person("Maria", 3, p2));
		p2.getPeople().add(new Person("Ana", 4, p2));

		// people relations
		List<KnownPerson> relations = new ArrayList<>();
		p2.setPeopleRelations(new ArrayList<>());
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(0), p2.getPeople().get(1)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(0), p2.getPeople().get(3)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(0), p2.getPeople().get(2)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(1), p2.getPeople().get(4)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(1), p2.getPeople().get(3)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(2), p2.getPeople().get(3)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(4), p2.getPeople().get(1)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(4), p2.getPeople().get(2)));
		p2.getPeopleRelations().add(new KnownPerson(p2.getPeople().get(4), p2.getPeople().get(3)));
	}


	@Test
	public void testGenerateMatrixProblem() {
		int[][] matrix = problemSolver.createRelationshipMatrix(p1);
		Assertions.assertThat(matrix).isNotEmpty();
		Assertions.assertThat(matrix.length).isEqualTo(3);
		Assertions.assertThat(matrix[0][1]).isNotZero();
		Assertions.assertThat(matrix[0][2]).isNotZero();
		Assertions.assertThat(matrix[1][0]).isNotZero();
		Assertions.assertThat(matrix[2][0]).isNotZero();
		Assertions.assertThat(matrix[2][1]).isNotZero();
		Assertions.assertThat(matrix[1][2]).isZero();
		for (int i = 0 ; i < 3; ++i) {
			Assertions.assertThat(matrix[i][i]).isZero();
		}
	}

	@Test
	public void testProblemSolving() {
		List<Person> solution = problemSolver.solveProblem(p2);
		Assertions.assertThat(solution).isNotEmpty();
		Assertions.assertThat(solution.size()).isEqualTo(1);
		Assertions.assertThat(solution.get(0)).isNotNull();
		Assertions.assertThat(solution.get(0).getFullName()).isEqualTo("Maria");
		Assertions.assertThat(solution.get(0).getFullName()).isEqualTo("Maria");



	}


}
