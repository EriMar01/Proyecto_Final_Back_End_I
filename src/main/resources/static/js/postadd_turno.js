window.addEventListener('load', function () {
    // Función para llenar las tablas de odontólogos y pacientes
    function loadData() {
        // Llamada a la API de odontólogos
        fetch('/odontologos', { method: 'GET' })
            .then(response => response.json())
            .then(odontologos => {
                // Llenar la tabla de odontólogos con los datos recibidos
                const odontologoTableBody = document.getElementById('odontologoTableBody');
                odontologoTableBody.innerHTML = ''; // Limpiar la tabla antes de agregar los datos

                odontologos.forEach(odontologo => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${odontologo.nombre}</td>
                        <td>${odontologo.apellido}</td>
                        <td>${odontologo.matricula}</td>
                    `;
                    row.addEventListener('click', () => {
                        document.getElementById('odontologo_id').value = odontologo.id;
                        document.getElementById('odontologo_nombre').value = odontologo.nombre;
                        document.getElementById('odontologo_apellido').value = odontologo.apellido;
                        document.getElementById('odontologo_matricula').value = odontologo.matricula;
                    });
                    odontologoTableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error al cargar los odontólogos:', error));

        // Llamada a la API de pacientes
        fetch('/pacientes', { method: 'GET' })
            .then(response => response.json())
            .then(pacientes => {
                // Llenar la tabla de pacientes con los datos recibidos
                const pacienteTableBody = document.getElementById('pacienteTableBody');
                pacienteTableBody.innerHTML = ''; // Limpiar la tabla antes de agregar los datos

                pacientes.forEach(paciente => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${paciente.nombre}</td>
                        <td>${paciente.apellido}</td>
                        <td>${paciente.dni}</td>
                    `;
                    row.addEventListener('click', () => {
                        document.getElementById('paciente_id').value = paciente.id;
                        document.getElementById('paciente_nombre').value = paciente.nombre;
                        document.getElementById('paciente_apellido').value = paciente.apellido;
                        document.getElementById('paciente_dni').value = paciente.dni;
                        document.getElementById('paciente_fechaAlta').value = paciente.fechaAlta;

                        // Datos del domicilio del paciente
                        document.getElementById('paciente_domicilioid').value = paciente.domicilio.id;
                        document.getElementById('paciente_calle').value = paciente.domicilio.calle;
                        document.getElementById('paciente_numero').value = paciente.domicilio.numero;
                        document.getElementById('paciente_localidad').value = paciente.domicilio.localidad;
                        document.getElementById('paciente_provincia').value = paciente.domicilio.provincia;
                    });
                    pacienteTableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error al cargar los pacientes:', error));
    }

    // Llamar a loadData al cargar la página
    loadData();
    // Llamar a loadData al cargar la página
    window.onload = loadData;
});
    document.addEventListener('DOMContentLoaded', function() {
    // Filtrar tabla de odontólogos
    document.getElementById('search_odontologo').addEventListener('input', function() {
        filterTable('odontologoTable', this.value);
    });

    // Filtrar tabla de pacientes
    document.getElementById('search_paciente').addEventListener('input', function() {
        filterTable('pacienteTable', this.value);
    });

    // Función para filtrar la tabla según el valor de búsqueda
    function filterTable(tableId, searchText) {
        const table = document.getElementById(tableId);
        const rows = table.getElementsByTagName('tr');
        const lowerSearchText = searchText.toLowerCase();

        // Iterar sobre las filas de la tabla
        for (let i = 1; i < rows.length; i++) { // Comenzar desde 1 para omitir el encabezado
            let row = rows[i];
            let cells = row.getElementsByTagName('td');
            let rowText = '';

            // Concatenar todo el texto de las celdas de la fila
            for (let cell of cells) {
                rowText += cell.textContent.toLowerCase() + ' ';
            }

            // Mostrar u ocultar la fila según si coincide con el texto de búsqueda
            row.style.display = rowText.includes(lowerSearchText) ? '' : 'none';
        }
    }
});