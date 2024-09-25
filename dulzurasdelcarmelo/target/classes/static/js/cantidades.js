'use strict'

const EURO = new Intl.NumberFormat('es-ES', {
    style: 'currency',
    currency: 'EUR',
});

window.addEventListener('DOMContentLoaded', function() {
    const cantidadInputs = document.querySelectorAll('input[name="cantidad"]');

    cantidadInputs.forEach(input => input.addEventListener('blur', function() {
        // Recogemos la información del producto y la cantidad
        const cantidad = parseInt(input.value, 10); // Convertimos la cantidad a número entero
        const id = +input.dataset.id; // ID del producto
        const precioUnitario = parseFloat(input.dataset.precio); // Convertimos el precio a número flotante
        
        // Validar si el precioUnitario es un número válido
        if (isNaN(precioUnitario)) {
            alert("Error: El precio no es válido.");
            return;
        }

        // Validar si la cantidad es válida
        if (isNaN(cantidad) || cantidad <= 0) {
            alert("Por favor, ingrese una cantidad válida.");
            return;
        }

        // Calcular el nuevo precio total
        const nuevoPrecioTotal = (cantidad * precioUnitario).toFixed(2); // Calcula el nuevo precio total

        // Actualizar el campo del precio total en la fila correspondiente
        const precioTotalElemento = input.closest('tr').querySelector('.precioTotal'); 
        precioTotalElemento.innerText = nuevoPrecioTotal; // Actualiza el precio total en la fila

        // Actualizar el subtotal general
        let subtotal = 0;
        const preciosTotales = document.querySelectorAll('.precioTotal');
        preciosTotales.forEach(precio => {
            subtotal += parseFloat(precio.innerText); // Aseguramos que estamos sumando números válidos
        });

        const subtotalElemento = document.getElementById('subtotal');
        if (subtotalElemento) {
            subtotalElemento.innerText = subtotal.toFixed(2); // Actualizar el subtotal con dos decimales
        }

        // Actualizar el atributo 'value' del input
        input.setAttribute('value', cantidad); // Actualiza el valor visible en el DOM
    }));
});




/**
 * 
 */