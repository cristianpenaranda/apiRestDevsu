/**
 * @author Cristian Peñaranda
 **/

window.onload = function() {
    listarMovimientos();
};

function consultarCuenta(){
    let numeroCuenta = $("#numeroCuentaMov").val();
    if(numeroCuenta === ""){
        Swal.fire('Atención', 'Por favor ingrese el número de la cuenta', 'warning');
    }else{
        let cuenta = getCuentaByNumeroCuenta(numeroCuenta);
        cuenta.done(function (cuentaData) {
            if (cuenta.status === 200) {
                verCuentaMov(cuentaData.data);
            } else if (cuenta.status === 204){
                Swal.fire('Atención', "El número de cuenta no se encuentra registrado.", 'warning');
            } else {
                Swal.fire('Atención', 'Ocurrió un error.', 'warning');
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

function listarMovimientos(){
    let listaMovimientos = getAllMovimientos();
    let html = "";
    listaMovimientos.done(function (obj, textStatus, jqXHR) {
        let listado = listaMovimientos.responseJSON;
        if(listado.length > 0){
            listado.forEach((movimiento)=> {
                html = `
               <tr style="cursor: pointer;">
                    <td>${movimiento.fecha}</td>
                    <td>${movimiento.cuenta.cliente.persona.nombre}</td>
                    <td>${movimiento.cuenta.numeroCuenta}</td>
                    <td>${movimiento.cuenta.tipoCuenta=="A" ? "AHORRO" : "CORRIENTE"}</td>
                    <td>${movimiento.cuenta.saldoInicial}</td>
                    <td>${movimiento.cuenta.estado}</td>
                    <td>${movimiento.valor}</td>
                    <td>${movimiento.saldo}</td>
                    <td><button type="button" class="btn btn-outline-secondary" style="margin-right:5px;" title="Ver Información de el movimiento" onclick="verMovimiento(${movimiento.idMovimiento});">
                            <i class="bi bi-eye"></i></button>
                        <button type="button" class="btn btn-outline-danger" title="Eliminar el movimiento" onclick="eliminarMovimiento(${movimiento.idMovimiento});"><i class="bi bi-x-circle"></i></button>
                    </td>
               </tr>
                `
                $('#tablaMovimientos tbody').append(html);
            });
        }else{
            html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
            $('#tablaMovimientos tbody').append(html);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
    });
}

function eliminarMovimiento(idMovimiento){
    Swal.fire({
        title: 'Está seguro que desea eliminar el movimiento?',
        showCancelButton: true,
        confirmButtonText: 'Eliminar',
    }).then((result) => {
        if (result.isConfirmed) {
            let eliminarMovimiento = deleteMovimiento(idMovimiento);
            eliminarMovimiento.done(function (eliminarMovimientoData) {
                if (eliminarMovimiento.status === 200) {
                    Swal.fire('Información', eliminarMovimientoData.message, 'success');
                    setTimeout(function () {
                        location.reload();
                    }, 1500);
                }else {
                    Swal.fire('Atención', 'Ocurrió un error al eliminar el movimiento', 'warning');
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

function verCuentaMov(cuenta) {
    $("#idCuenta").val(cuenta.idCuenta);
    $("#idCliente").val(cuenta.cliente.idCliente);
    $("#tipoCuentaMov").val(cuenta.tipoCuenta=="A"?"Ahorro":"Corriente");
    $("#saldoInicialMov").val(cuenta.saldoInicial);
    $("#clienteMov").val(cuenta.cliente.persona.nombre);
}

async function verMovimiento(idMovimiento) {
    let movimiento = await getMovimientoById(idMovimiento);
    $("#numeroCuentaMov").val(movimiento.data.cuenta.numeroCuenta);
    $("#idCuenta").val(movimiento.data.cuenta.idCuenta);
    $("#idMovimiento").val(movimiento.data.idMovimiento);
    $("#tipoCuentaMov").val(movimiento.data.cuenta.tipoCuenta=="A"?"Ahorro":"Corriente");
    $("#saldoInicialMov").val(movimiento.data.cuenta.saldoInicial);
    $("#clienteMov").val(movimiento.data.cuenta.cliente.persona.nombre);
    $("#valorMov").val(movimiento.data.valor);
}

function guardarMovimiento(){
    $('#formMovimientos').validate({
        rules: {
            numeroCuentaMov: 'required',
            valorMov: 'required'
        }, messages: {
            numeroCuentaMov: 'Por favor, indique el número de cuenta',
            valorMov: 'Por favor, indique el valor del movimiento'
        }, errorElement: 'label', errorElement: 'em', errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
        }, highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid').removeClass('is-valid');
        }, unhighlight: function (element, errorClass, validClass) {
            $(element).addClass('is-valid').removeClass('is-invalid');
        }, submitHandler: function (form) {
            let data = armarJsonMovimiento();
            if(data != null) {
                let response = null;
                if (data.idMovimiento != "") {
                    response = updateMovimiento(data);
                } else {
                    response = saveMovimiento(data)
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
                        Swal.fire('Atención', 'Ocurrió un error al guardar la información del movimiento', 'warning');
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

function armarJsonMovimiento(){
    let idMovimiento = $("#idMovimiento").val();
    let idCuenta = $("#idCuenta").val();
    let numeroCuentaMov = $("#numeroCuentaMov").val();
    let idCliente = $("#idCliente").val();
    let valorMov = $("#valorMov").val();

    let movimiento = {
        idMovimiento: idMovimiento,
        valor: valorMov,
        cuenta: {
            idCuenta: idCuenta,
            numeroCuenta: numeroCuentaMov,
            cliente: {
                idCliente: idCliente
            }
        }
    }
    return movimiento;
}

function saveMovimiento(data){
    return $.ajax({
        type: 'POST', url: './movimiento', headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }, data: JSON.stringify(data), ContentType: 'application/json', dataType: 'json'
    });
}

function updateMovimiento(data){
    return $.ajax({
        type: 'PUT', url: './movimiento', headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }, data: JSON.stringify(data), ContentType: 'application/json', dataType: 'json'
    });
}

function getMovimientoById(idMovimiento) {
    return $.ajax({
        type: "GET",
        url: "./movimiento/" + idMovimiento,
    });
}

function getCuentaByNumeroCuenta(numeroCuenta) {
    return $.ajax({
        type: "GET",
        url: "./cuenta/numerocuenta/" + numeroCuenta,
    });
}

function getAllMovimientos(){
    return $.ajax({
        type: "GET",
        url: "./movimiento/listar",
        dataType: "json"
    });
}

function deleteMovimiento(idMovimiento) {
    return $.ajax({
        type: "DELETE",
        url: "./movimiento/"+ idMovimiento,
    });
}