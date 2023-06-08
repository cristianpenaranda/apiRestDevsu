/**
 * @author Cristian Peñaranda
 **/

window.onload = function() {
    listarClientesSelect();
    listarCuentas();
};

function listarClientesSelect(){
    let listaClientes = getAllClientes();
    listaClientes.done(function (obj, textStatus, jqXHR) {
        if (obj.length > 0) {
            $("#clienteCuenta").html(`<option value="">Seleccionar</option>`);
            $.each(obj, function (key, value) {
                $("#clienteCuenta").append('<option value="' + obj[key].idCliente + '">' + obj[key].persona.nombre + '</option>');
            });
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        Swal.fire('Atención', 'No se pudieron cargar los cliente.' + ' ', 'warning');
    });
}

function guardarCuenta(){
    $('#formCuentas').validate({
        rules: {
            numeroCuenta: 'required',
            saldoInicialCuenta: 'required'
        }, messages: {
            numeroCuenta: 'Por favor, indique el número de cuenta',
            saldoInicialCuenta: 'Por favor, indique el saldo inicial'
        }, errorElement: 'label', errorElement: 'em', errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
        }, highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid').removeClass('is-valid');
        }, unhighlight: function (element, errorClass, validClass) {
            $(element).addClass('is-valid').removeClass('is-invalid');
        }, submitHandler: function (form) {
            let data = armarJsonCuenta();
            if(data != null) {
                if($("#tipoCuenta").val() === "Seleccionar") {
                    Swal.fire('Información', 'Por favor debe seleccionar el tipo de cuenta', 'warning');
                }else if($("#clienteCuenta").val() === "") {
                    Swal.fire('Información', 'Por favor debe seleccionar el cliente', 'warning');
                }else{
                    let response = null;
                    if (data.idCuenta != "") {
                        response = updateCuenta(data);
                    } else {
                        response = saveCuenta(data)
                    }
                    response.done(function (responseData) {
                        if (response.status === 200 || response.status === 201) {
                            Swal.fire('Información', responseData.message, 'success');
                            setTimeout(function () {
                                location.reload();
                            }, 1500);
                        } else if (response.status === 226) {
                            Swal.fire('Información', responseData.error, 'warning');
                        } else {
                            Swal.fire('Atención', 'Ocurrió un error al guardar la información de la cuenta', 'warning');
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
        }
    });
}

function armarJsonCuenta(){
    let idCuenta = $("#idCuenta").val();
    let numeroCuenta = $("#numeroCuenta").val();
    let tipoCuenta = $("#tipoCuenta").val();
    let saldoInicial = $("#saldoInicialCuenta").val();
    let idCliente = $("#clienteCuenta").val();

    let cuenta = {
        idCuenta: idCuenta,
        numeroCuenta: numeroCuenta,
        tipoCuenta: tipoCuenta,
        saldoInicial: saldoInicial,
        cliente: {
            idCliente: idCliente
        }
    }
    return cuenta;
}

function listarCuentas(){
    let listaCuentas = getAllCuentas();
    let html = "";
    listaCuentas.done(function (obj, textStatus, jqXHR) {
        let listado = listaCuentas.responseJSON;
        if(listado.length > 0){
            listado.forEach((cuenta)=> {
                html = `
               <tr style="cursor: pointer;">
                    <td>${cuenta.idCuenta}</td>
                    <td>${cuenta.numeroCuenta}</td>
                    <td>${cuenta.cliente.persona.nombre}</td>
                    <td><button type="button" class="btn btn-outline-secondary" style="margin-right:5px;" title="Ver Información de la cuenta" onclick="verCuenta(${cuenta.idCuenta});">
                            <i class="bi bi-eye"></i></button>
                        <button type="button" class="btn btn-outline-danger" title="Eliminar la cuenta" onclick="eliminarCuenta(${cuenta.idCuenta});"><i class="bi bi-x-circle"></i></button>
                    </td>
               </tr>
                `
                $('#tablaCuentas tbody').append(html);
            });
        }else{
            html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
            $('#tablaCuentas tbody').append(html);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
    });
}

async function verCuenta(idCuenta) {
    let cuenta = await getCuentaById(idCuenta);
    $("#idCuenta").val(cuenta.data.idCuenta);
    $("#numeroCuenta").val(cuenta.data.numeroCuenta);
    $("#tipoCuenta").val(cuenta.data.tipoCuenta);
    $("#saldoInicialCuenta").val(cuenta.data.saldoInicial);
    $("#clienteCuenta").val(cuenta.data.cliente.idCliente);
}

function eliminarCuenta(idCuenta){
    Swal.fire({
        title: 'Está seguro que desea eliminar la cuenta?',
        showCancelButton: true,
        confirmButtonText: 'Eliminar',
    }).then((result) => {
        if (result.isConfirmed) {
            let eliminarCuenta = deleteCuenta(idCuenta);
            eliminarCuenta.done(function (eliminarCuentaData) {
                if (eliminarCuenta.status === 200) {
                    Swal.fire('Información', eliminarCuentaData.message, 'success');
                    setTimeout(function () {
                        location.reload();
                    }, 1500);
                }else {
                    Swal.fire('Atención', 'Ocurrió un error al eliminar la cuenta', 'warning');
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

function getAllCuentas(){
    return $.ajax({
        type: "GET",
        url: "./cuenta/listar",
        dataType: "json"
    });
}

function saveCuenta(data){
    return $.ajax({
        type: 'POST', url: './cuenta', headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }, data: JSON.stringify(data), ContentType: 'application/json', dataType: 'json'
    });
}

function updateCuenta(data){
    return $.ajax({
        type: 'PUT', url: './cuenta', headers: {
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

function getCuentaById(idCuenta) {
    return $.ajax({
        type: "GET",
        url: "./cuenta/" + idCuenta,
    });
}

function deleteCuenta(idCuenta) {
    return $.ajax({
        type: "DELETE",
        url: "./cuenta/"+ idCuenta,
    });
}