window.addEventListener('load', function () {

    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado de la paciente
    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
        let turnoId = document.querySelector('#turno_id').value;

        //creamos un JSON que tendrá los datos de la paciente
        //a diferencia de una paciente nueva en este caso enviamos el id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
             id: document.querySelector('#turno_id').value,
             fecha: document.querySelector('#fecha_todo').value,
             hora: document.querySelector('#hora_todo').value,
             odontologo: {
                 id: document.querySelector('#odontologo_id').value
                },
            paciente: {
                id: document.querySelector('#paciente_id').value
            }
        };

        //invocamos utilizando la función fetch la API peliculas con el método PUT que modificará
        //la película que enviaremos en formato JSON
        const url = '/turnos/actualizar';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => response.json())

    })
 })

    //Es la funcion que se invoca cuando se hace click sobre el id de una pelicula del listado
    //se encarga de llenar el formulario con los datos de la pelicula
    //que se desea modificar
    function findBy(id) {
          const url = '/turnos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let turno = data;
              console.log(turno)
              document.querySelector('#turno_id').value = turno.id;
              document.querySelector('#fecha_todo').value = turno.fecha;
              document.querySelector('#hora_todo').value = turno.hora;

              document.querySelector('#odontologo_id').value = turno.odontologo.id;
              document.querySelector('#odontologo_nombre').value = turno.odontologo.nombre;
              document.querySelector('#odontologo_apellido').value = turno.odontologo.apellido;
              document.querySelector('#odontologo_matricula').value = turno.odontologo.matricula;


              document.querySelector('#paciente_id').value = turno.paciente.id;
              document.querySelector('#paciente_nombre').value = turno.paciente.nombre;
              document.querySelector('#paciente_apellido').value = turno.paciente.apellido;
              document.querySelector('#paciente_dni').value = turno.paciente.dni;
              //document.querySelector('#fechaAlta').value = turno.paciente.fechaAlta;
              //document.querySelector('#domicilio_id').value = turno.paciente.domicilio.id;
              //document.querySelector('#calle').value = turno.paciente.domicilio.calle;
              //document.querySelector('#numero').value = turno.paciente.domicilio.numero;
              //document.querySelector('#localidad').value = turno.paciente.domicilio.localidad;
              //document.querySelector('#provincia').value = turno.paciente.domicilio.provincia;

              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_turno_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }