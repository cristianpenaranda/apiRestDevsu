/**
 * @author Cristian Peñaranda
 **/
window.onload = function() {
    listarClientesSelect();
};

function buscarReporte(){
    let cliente = $("#clienteBusqueda").val();
    let fechaInicial = $("#fechaInicial").val();
    let fechaFinal = $("#fechaFinal").val();

    if(cliente === ""){
        Swal.fire('Atención', 'Debe seleccionar el cliente.', 'warning');
    }else if(fechaInicial === ""){
        Swal.fire('Atención', 'Debe seleccionar la fecha inicial del reporte.', 'warning');
    }else if(fechaFinal === ""){
        Swal.fire('Atención', 'Debe seleccionar la fecha final del reporte.', 'warning');
    }else{
        let listaReporte = getReporteByIdClienteAndFechas(cliente, fechaInicial, fechaFinal);
        listaReporte.done(function (listaReporteData) {
            if (listaReporte.status === 200) {
                Swal.fire('Información', 'Reporte Generado', 'success');
                cargarListaReporteTabla(listaReporte);
            }else {
                Swal.fire('Atención', 'No hay Resultados.', 'warning');
                $(".otrasFilas").remove();
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 500) {
                Swal.fire('Atención', 'Error en el servidor', 'error');
                $(".otrasFilas").remove();
            } else if (jqXHR.status === 404) {
                Swal.fire('Atención', 'No hay Resultados.', 'warning');
                $(".otrasFilas").remove();
            } else if (jqXHR.status === 403) {
                Swal.fire('Atención', 'Acceso Denegado', 'error');
                $(".otrasFilas").remove();
            }
        });
    }
}

function cargarListaReporteTabla(listaReporte){
    let html = "";
    listaReporte.done(function (obj, textStatus, jqXHR) {
        let listado = listaReporte.responseJSON;
        $(".otrasFilas").remove();
        if(listado.length > 0){
            listado.forEach((reporte)=> {
                html = `
               <tr class="otrasFilas" style="cursor: pointer;">
                    <td>${reporte.movi_fecha}</td>
                    <td>${reporte.pers_nombre}</td>
                    <td>${reporte.cuen_numero_cuenta}</td>
                    <td>${reporte.cuen_tipo_cuenta=="A" ? "AHORRO" : "CORRIENTE"}</td>
                    <td>${reporte.cuen_saldo_inicial}</td>
                    <td>${reporte.cuen_estado}</td>
                    <td>${reporte.movi_valor}</td>
                    <td>${reporte.movi_saldo}</td>
               </tr>
                `
                $('#tablaReporte tbody').append(html);
            });
        }else{
            html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
            $('#tablaReporte tbody').append(html);
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        html += `
           <tr>
                <td colspan="11">Sin resultados.</td>
           </tr>
                `
    });
}

function getAllClientes(){
    return $.ajax({
        type: "GET",
        url: "./cliente/listar",
        dataType: "json"
    });
}

function listarClientesSelect(){
    let listaClientes = getAllClientes();
    listaClientes.done(function (obj, textStatus, jqXHR) {
        if (obj.length > 0) {
            $("#clienteBusqueda").html(`<option value="">Seleccionar</option>`);
            $.each(obj, function (key, value) {
                $("#clienteBusqueda").append('<option value="' + obj[key].idCliente + '">' + obj[key].persona.nombre + '</option>');
            });
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        Swal.fire('Atención', 'No se pudieron cargar los cliente.' + ' ', 'warning');
    });
}

function getReporteByIdClienteAndFechas(idCliente, fechaInicial, fechaFinal) {
    return $.ajax({
        type: "GET",
        url: "./reporte/" + idCliente + "/" + fechaInicial + "/" + fechaFinal,
    });
}