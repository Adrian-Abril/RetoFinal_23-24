document.addEventListener('DOMContentLoaded', async () => {
    const getEmployees = async () => {
        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=EMPLEADO.FIND_ALL';
            const response = await fetch(url);
            const employees = await response.json();

            createEmployeeRows(employees);
        } catch (error) {
            console.error('Error fetching employees:', error);
        }
    };
    
    const createEmployeeRows = (employees) => {
        const tbody = document.querySelector('tbody');
        tbody.innerHTML = '';
    
        employees.forEach(employee => {
            const row = `
                <tr>
                    <td>${employee.idEmpleado}</td>
                    <td>${employee.nombre}</td>
                    <td>${employee.apellidos}</td>
                    <td>${employee.email}</td>
                    <td>${employee.telefono}</td>
                    <td>${employee.idCatEmpleado}</td>
                </tr>
            `;
            tbody.insertAdjacentHTML('beforeend', row);
        });
    };
    
    getEmployees();
});
