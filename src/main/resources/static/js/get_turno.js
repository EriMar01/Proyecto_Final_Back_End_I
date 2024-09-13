window.addEventListener('load', function () {
  (function () {

      // Usamos fetch para invocar a la API de turnos con el método GET
      const url = '/turnos';
      const settings = {
          method: 'GET'
      }

      fetch(url, settings)
          .then(response => response.json())
          .then(data => {
              // Recorrer la colección de turnos del JSON
              for (turno of data) {
                   console.log(data);
                  // Tablas de Turnos, Odontólogos y Pacientes
                  var turnoTable = document.getElementById("turnoTable");
                  var odontologoTable = document.getElementById("odontologoTable");
                  var pacienteTable = document.getElementById("pacienteTable");

                  // Agregar fila para Turnos
                  var turnoRow = turnoTable.insertRow();
                  let tr_id_turno = 'tr_turno_' + turno.id;
                  turnoRow.id = tr_id_turno;

                  turnoRow.innerHTML =
                      '<td class=\"td_fecha\">' + turno.fecha + '</td>' +
                      '<td class=\"td_hora\">' + turno.hora + '</td>';

                  // Agregar fila para Odontólogos
                  var odontologoRow = odontologoTable.insertRow();
                  let tr_id_odontologo = 'tr_odontologo_' + turno.id;
                  odontologoRow.id = tr_id_odontologo;

                  odontologoRow.innerHTML =
                      '<td class=\"td_odontologonombre\">' + turno.odontologo.nombre.toUpperCase() + '</td>' +
                      '<td class=\"td_odontologoapellido\">' + turno.odontologo.apellido.toUpperCase() + '</td>'+
                      '<td class=\"td_odontologomatricula\">' + turno.odontologo.matricula + '</td>';
                  // Agregar fila para Pacientes
                  var pacienteRow = pacienteTable.insertRow();
                  let tr_id_paciente = 'tr_paciente_' + turno.id;
                  pacienteRow.id = tr_id_paciente;

                  pacienteRow.innerHTML =
                      '<td class=\"td_pacientenombre\">' + turno.paciente.nombre.toUpperCase() + '</td>' +
                      '<td class=\"td_pacienteapellido\">' + turno.paciente.apellido.toUpperCase() + '</td>' +
                       '<td class=\"td_pacientedni\">' + turno.paciente.dni + '</td>';

                  // Crear botones para eliminar y actualizar
                  let deleteButton = '<button' +
                      ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                      ' type="button" onclick="deleteBy(' + turno.id + ')" class="btn btn-danger btn_delete">' +
                      '&times' +
                      '</button>';

                  let updateButton = '<button' +
                      ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                      ' type="button" onclick="findBy(' + turno.id + ')" class="btn btn-info btn_id">' +
                      ' - ' +
                      '</button>';

                  // Agregar los botones de actualización y eliminación en la tabla de Turnos
                  turnoRow.innerHTML =  '<td>' + updateButton + '</td>' +
                    '<td class=\"td_fecha\">' + turno.fecha + '</td>' +
                    '<td class=\"td_hora\">' + turno.hora + '</td>';
                  pacienteRow.innerHTML =
                  '<td class=\"td_pacientenombre\">' + turno.paciente.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_pacienteapellido\">' + turno.paciente.apellido.toUpperCase() + '</td>'+
                     '<td class=\"td_pacientedni\">' + turno.paciente.dni.toUpperCase() + '</td>'+
                  '<td>' + deleteButton + '</td>';
              }
          })
          .catch(error => console.log('Error:', error));
  })();

   (function(){
       let pathname = window.location.pathname;
       if (pathname == "/turnoList.html") {
           document.querySelector(".nav .nav-item a:last").addClass("active");
       }
     })

});
