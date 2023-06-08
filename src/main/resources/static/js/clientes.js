/**
 * @author Cristian Peñaranda
 **/

window.onload = function() {
    listarClientes();
};

function guardarCliente(){
    $('#formClientes').validate({
        rules: {
            nombreCliente: 'required',
            direccionCliente: 'required',
            telefonoCliente: 'required',
            contrasenaCliente: 'required'
        }, messages: {
            nombreCliente: 'Por favor, indique el nombre del cliente',
            direccionCliente: 'Por favor, indique la dirección del cliente',
            telefonoCliente: 'Por favor, indique el teléfono del cliente',
            contrasenaCliente: 'Por favor, indique la contraseña del cliente'
        }, errorElement: 'label', errorElement: 'em', errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
        }, highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid').removeClass('is-valid');
        }, unhighlight: function (element, errorClass, validClass) {
            $(element).addClass('is-valid').removeClass('is-invalid');
        }, submitHandler: function (form) {
            let data = armarJsonCliente();
            if(data != null){
                let response = null;
                if(data.idCliente != ""){
                    response = updateCliente(data);
                }else{
                    response = saveCliente(data)
                }
                response.done(function (responseData) {
                    if (response.status === 200 || response.status === 201) {
                        Swal.fire('Información', responseData.message, 'success');
                        setTimeout(function () {
                            location.reload();
                        }, 1500);
                    }else if (response.status === 226) {
                        Swal.fire('Información', responseData.error, 'warning');
                    } else {
                        Swal.fire('Atención', 'Ocurrió un error al guardar la información del cliente', 'warning');
                    }
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 500) {
                        Swal.fire('Atención', 'Error en el servidor', 'error');
                    } else if (jqXHR.status === 403) {
                        Swal.fire('Atención', 'Acceso Denegado', 'error');
                    }
                });
            }
        }
    });
}

function armarJsonCliente(){
    let idCliente = $("#idCliente").val();
    let identificacion = $("#identificacionCliente").val();
    let nombre = $("#nombreCliente").val();
    let genero = $("#generoCliente").val();
    let edad = $("#edadCliente").val();
    let direccion = $("#direccionCliente").val();
    let telefono = $("#telefonoCliente").val();
    let contrasena = $("#contrasenaCliente").val();

    let cliente = {
        idCliente: idCliente,
        contrasena: contrasena,
        persona: {
            identificacion: identificacion,
            nombre: nombre,
            genero: genero,
            edad: edad,
            direccion: direccion,
            telefono: telefono
        }
    }

    return cliente;
}

function listarClientes(){
    let listaClientes = getAllClientes();
    let html = "";
    listaClientes.done(function (obj, textStatus, jqXHR) {
        let listado = listaClientes.responseJSON;
        if(listado.length > 0){
            listado.forEach((cliente)=> {
                html = `
               <tr style="cursor: pointer;">
                    <td>${cliente.idCliente}</td>
                    <td>${cliente.persona.identificacion}</td>
                    <td>${cliente.persona.nombre}</td>
                    <td><button type="button" class="btn btn-outline-secondary" style="margin-right:5px;" title="Ver Información del Cliente" onclick="verCliente(${cliente.idCliente});">
                            <i class="bi bi-eye"></i></button>
                        <button type="button" class="btn btn-outline-danger" title="Eliminar el Cliente" onclick="eliminarCliente(${cliente.idCliente});"><i class="bi bi-x-circle"></i></button>
                    </td>
               </tr>
                `
                $('#tablaClientes tbody').append(html);
            });
        }else{
            html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
            $('#tablaClientes tbody').append(html);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
    });
}

async function verCliente(idCliente) {
    let cliente = await getClienteById(idCliente);
    $("#idCliente").val(cliente.data.idCliente);
    $("#identificacionCliente").val(cliente.data.persona.identificacion);
    $("#nombreCliente").val(cliente.data.persona.nombre);
    $("#generoCliente").val(cliente.data.persona.genero);
    $("#edadCliente").val(cliente.data.persona.edad == "0" ? "" : cliente.data.persona.edad);
    $("#direccionCliente").val(cliente.data.persona.direccion);
    $("#telefonoCliente").val(cliente.data.persona.telefono);
    $("#contrasenaCliente").val(cliente.data.contrasena);
}

function eliminarCliente(idCliente){
    Swal.fire({
        title: 'Está seguro que desea eliminar el cliente?',
        showCancelButton: true,
        confirmButtonText: 'Eliminar',
    }).then((result) => {
        if (result.isConfirmed) {
            let eliminarCliente = deleteCliente(idCliente);
            eliminarCliente.done(function (eliminarClienteData) {
                if (eliminarCliente.status === 200) {
                    Swal.fire('Información', eliminarClienteData.message, 'success');
                    setTimeout(function () {
                        location.reload();
                    }, 1500);
                }else {
                    Swal.fire('Atención', 'Ocurrió un error al eliminar el cliente', 'warning');
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 500) {
                    Swal.fire('Atención', 'Error en el servidor', 'error');
                } else if (jqXHR.status === 403) {
                    Swal.fire('Atención', 'Acceso Denegado', 'error');
                }
            });
        }
    })
}

function mostrarContrasena(){
    var tipo = document.getElementById("contrasenaCliente");
    if(tipo.type == "password"){
        tipo.type = "text";
    }else{
        tipo.type = "password";
    }
}

function saveCliente(data){
    return $.ajax({
        type: 'POST', url: './cliente', headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }, data: JSON.stringify(data), ContentType: 'application/json', dataType: 'json'
    });
}

function updateCliente(data){
    return $.ajax({
        type: 'PUT', url: './cliente', headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }, data: JSON.stringify(data), ContentType: 'application/json', dataType: 'json'
    });
}

function getAllClientes(){
    return $.ajax({
        type: "GET",
        url: "./cliente/listar",
        dataType: "json"
    });
}

function deleteCliente(idCliente) {
    return $.ajax({
        type: "DELETE",
        url: "./cliente/"+ idCliente,
    });
}

function getClienteById(idCliente) {
    return $.ajax({
        type: "GET",
        url: "./cliente/" + idCliente,
    });
}