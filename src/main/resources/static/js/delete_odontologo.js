function deleteBy(id) {
    // Invocamos la API de odontólogos con el método DELETE, pasando el ID en la URL
    const url = '/odontologos/' + id;
    const settings = {
        method: 'DELETE'
    };

    fetch(url, settings)
        .then(response => {
            // Verificamos si la respuesta es 204 (No Content)
            if (response.status === 204) {
                // Borramos la fila correspondiente al odontólogo eliminado
                let row_id = "#tr_" + id;
                document.querySelector(row_id)?.remove();

                // Mostramos un mensaje de éxito
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Odontólogo eliminado exitosamente</strong></div>';

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
            } else {
                // Si hay un error, mostramos un mensaje de error
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Error al eliminar el odontólogo, intente nuevamente</strong></div>';

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
            }
        })
        .catch(error => {
            // Si ocurre un error en la petición, mostramos un mensaje de error
            let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong>Error al eliminar el odontólogo, intente nuevamente</strong></div>';

            document.querySelector('#response').innerHTML = errorAlert;
            document.querySelector('#response').style.display = "block";
        });
}
