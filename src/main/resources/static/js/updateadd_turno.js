window.addEventListener('load', function() {
    // Función para llenar los selects con datos de pacientes y odontólogos
    function populateSelects() {
        // Consultar pacientes
        fetch('/pacientes', { method: 'GET' })
            .then(response => response.json())
            .then(pacientes => {
                const pacienteSelect = document.querySelector('#pacienteSelect');
                pacienteSelect.innerHTML = ''; // Limpiar opciones anteriores
                pacientes.forEach(paciente => {
                    const option = document.createElement('option');
                    option.value = paciente.id;
                    option.textContent = `${paciente.nombre} ${paciente.apellido}`;
                    pacienteSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching pacientes:', error));

        // Consultar odontólogos
        fetch('/odontologos', { method: 'GET' })
            .then(response => response.json())
            .then(odontologos => {
                const odontologoSelect = document.querySelector('#odontologoSelect');
                odontologoSelect.innerHTML = ''; // Limpiar opciones anteriores
                odontologos.forEach(odontologo => {
                    const option = document.createElement('option');
                    option.value = odontologo.id;
                    option.textContent = `${odontologo.nombre} ${odontologo.apellido}`;
                    odontologoSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching odontologos:', error));
    }

    // Llenar selects al cargar la página
    populateSelects();

    // Actualizar datos del formulario cuando se seleccione un paciente o odontólogo
    document.querySelector('#pacienteSelect').addEventListener('change', function() {
        const pacienteId = this.value;
        if (pacienteId) {
            fetch(`/pacientes/${pacienteId}`)
                .then(response => response.json())
                .then(paciente => {
                    document.querySelector('#paciente_id').value = paciente.id;
                    document.querySelector('#paciente_nombre').value = paciente.nombre;
                    document.querySelector('#paciente_apellido').value = paciente.apellido;
                    document.querySelector('#paciente_dni').value = paciente.dni;
                })
                .catch(error => console.error('Error fetching paciente details:', error));
        }
    });

    document.querySelector('#odontologoSelect').addEventListener('change', function() {
        const odontologoId = this.value;
        if (odontologoId) {
            fetch(`/odontologos/${odontologoId}`)
                .then(response => response.json())
                .then(odontologo => {
                    document.querySelector('#odontologo_id').value = odontologo.id;
                    document.querySelector('#odontologo_nombre').value = odontologo.nombre;
                    document.querySelector('#odontologo_apellido').value = odontologo.apellido;
                    document.querySelector('#odontologo_matricula').value = odontologo.matricula;
                })
                .catch(error => console.error('Error fetching odontologo details:', error));
        }
    });
});
