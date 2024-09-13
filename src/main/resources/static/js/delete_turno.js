function deleteBy(id) {
    // Invocamos la API con el método DELETE, pasando el ID en la URL
    const url = '/turnos/' + id;
    const settings = {
        method: 'DELETE'
    };

    fetch(url, settings)
        .then(response => {
            // Verificamos si la respuesta es 204 (No Content)
            if (response.status === 204) {
                // Borramos las filas correspondientes a turno, odontólogo y paciente
                document.querySelector("#tr_turno_" + id)?.remove();
                document.querySelector("#tr_odontologo_" + id)?.remove();
                document.querySelector("#tr_paciente_" + id)?.remove();

                // Mostramos un mensaje de éxito
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Turno eliminado exitosamente</strong></div>';

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
            } else {
                // Si hay un error, mostramos un mensaje de error
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Error al eliminar el turno, intente nuevamente</strong></div>';

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
            }
        })
        .catch(error => {
            // Si ocurre un error en la petición, mostramos un mensaje de error
            let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong>Error al eliminar el turno, intente nuevamente</strong></div>';

            document.querySelector('#response').innerHTML = errorAlert;
            document.querySelector('#response').style.display = "block";
        });
}
