package org.ibo.manager.payroll.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ibo.manager.payroll.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
