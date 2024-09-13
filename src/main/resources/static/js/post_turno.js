document.getElementById("add_new_turno").onsubmit=function(e) {
          e.preventDefault();
          };

window.addEventListener('load', function () {

    //Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará de la nueva paciente
    const formulario = document.querySelector('#add_new_turno');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {

       //creamos un JSON que tendrá los datos de la nueva paciente
        const formData = {
             fecha: document.querySelector('#fecha_todo').value,
             hora: document.querySelector('#hora_todo').value,
             odontologo: {
                 id: document.querySelector('#odontologo_id').value,
                 nombre: document.querySelector('#odontologo_nombre').value,
                 apellido: document.querySelector('#odontologo_apellido').value,
                 matricula: document.querySelector('#odontologo_matricula').value,
                },
            paciente: {
                id: document.querySelector('#paciente_id').value,
                nombre: document.querySelector('#paciente_nombre').value,
                apellido: document.querySelector('#paciente_apellido').value,
                dni: document.querySelector('#paciente_dni').value,
                fechaAlta: document.querySelector('#paciente_fechaAlta').value,
                domicilio:{
                    id:document.querySelector('#paciente_domicilioid').value,
                    calle: document.querySelector('#paciente_calle').value,
                    numero: document.querySelector('#paciente_numero').value,
                    localidad: document.querySelector('#paciente_localidad').value,
                    provincia: document.querySelector('#paciente_provincia').value,
                }
            }
        };
        //invocamos utilizando la función fetch la API peliculas con el método POST que guardará
        //la paciente que enviaremos en formato JSON
        const url = '/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 //Si no hay ningun error se muestra un mensaje diciendo que la paciente
                 //se agrego bien
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Turno agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    //Si hay algun error se muestra un mensaje diciendo que la paciente
                    //no se pudo guardar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     //se dejan todos los campos vacíos por si se quiere ingresar otra paciente
                     resetUploadForm();})
    });


    function resetUploadForm(){
        document.querySelector('#fecha_todo').value = "";
        document.querySelector('#hora_todo').value = "";
         document.querySelector('#odontologo_id').value = "";
        document.querySelector('#odontologo_nombre').value = "";
        document.querySelector('#odontologo_apellido').value = "";
         document.querySelector('#odontologo_matricula').value = "";
         document.querySelector('#paciente_id').value = "";
         document.querySelector('#paciente_nombre').value = "";
         document.querySelector('#paciente_apellido').value = "";
         document.querySelector('#paciente_dni').value = "";
         document.querySelector('#paciente_fechaAlta').value = "";
         document.querySelector('#paciente_domicilioid').value = "";
         document.querySelector('#paciente_calle').value = "";
         document.querySelector('#paciente_numero').value = "";
         document.querySelector('#paciente_localidad').value = "";
         document.querySelector('#paciente_provincia').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/turnoList.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});