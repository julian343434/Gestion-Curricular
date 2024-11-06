import React, { useState } from 'react';

const AlertasFormulario = () => {
  const [formData, setFormData] = useState({
    tipo_alerta: '',
    descripcion: '',
  });

  const [feedbackMessage, setFeedbackMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFeedbackMessage('');

    const { tipo_alerta, descripcion } = formData;
    console.log('Datos que se enviarán:', { tipo_alerta, descripcion });

    try {
      const response = await fetch('http://localhost:3001/api/alertas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ tipo_alerta, descripcion })
      });

      if (response.ok) {
        setFeedbackMessage('Alerta creada exitosamente.');
        setFormData({ tipo_alerta: '', descripcion: '' });
      } else {
        const errorData = await response.json();
        setFeedbackMessage(`Error: ${errorData.message || 'No se pudo crear la alerta.'}`);
      }
    } catch (error) {
      setFeedbackMessage(`Error de conexión: ${error.message}`);
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      className="w-full max-w-md mx-auto p-8 shadow-lg bg-gradient-to-br from-white to-[#C8C8C8] rounded-lg"
    >
      <h2 className="text-3xl font-bold text-center mb-6 text-[#20647C]">Crear Alerta</h2>

      {/* Tipo de Alerta */}
      <div className="mb-6">
        <label htmlFor="tipo_alerta" className="block text-sm font-medium text-[#5C5C5C] mb-2">
          Tipo de Alerta
        </label>
        <select
          id="tipo_alerta"
          name="tipo_alerta"
          value={formData.tipo_alerta}
          onChange={handleChange}
          required
          className="text-[#818181] mt-1 block w-full px-4 py-3 border border-[#C8C8C8] rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-[#34ACDC] focus:border-[#34ACDC] transition-all duration-200"
        >
          <option value="" disabled>
            Selecciona un tipo de alerta
          </option>
          <option className='text-[#5C5C5C]' value="Cierres viales">Cierres Viales</option>
          <option className='text-[#5C5C5C]' value="Pico y placa">Pico y Placa</option>
          <option className='text-[#5C5C5C]' value="Semaforos dañados">Semáforos Dañados</option>
          <option className='text-[#5C5C5C]' value="Accidentes">Accidentes</option>
          <option className='text-[#5C5C5C]' value="Trancones">Trancones</option>
          <option className='text-[#5C5C5C]' value="Baches">Baches</option>
          <option className='text-[#5C5C5C]' value="Cambios de sentido">Cambios de Sentido</option>
        </select>
      </div>

      {/* Descripción */}
      <div className="mb-6">
        <label htmlFor="descripcion" className="block text-sm font-medium text-[#5C5C5C] mb-2">
          Descripción
        </label>
        <textarea
          id="descripcion"
          name="descripcion"
          value={formData.descripcion}
          onChange={handleChange}
          required
          className="text-[#040404] mt-1 block w-full px-4 py-3 border border-[#C8C8C8] rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-[#34ACDC] focus:border-[#34ACDC] transition-all duration-200"
          rows="4"
        />
      </div>

      {/* Botón Enviar */}
      <button
        type="submit"
        className="w-full bg-gradient-to-r from-[#2C98C0] to-[#34ACDC] text-white px-6 py-3 rounded-md hover:from-[#2484A4] hover:to-[#2C98C0] transition duration-300 font-semibold text-lg shadow-md"
      >
        Enviar
      </button>

      {/* Mensaje de Feedback */}
      {feedbackMessage && (
        <p className={`mt-6 p-3 rounded ${feedbackMessage.includes('Error') ? 'bg-[#F34333]' : 'bg-[#50CCCC]'} text-white text-center font-medium`}>
          {feedbackMessage}
        </p>
      )}
    </form>
  );
};

export default AlertasFormulario;