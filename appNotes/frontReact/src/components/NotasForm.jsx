import PropTypes from 'prop-types'

function NotasForm({ formData, editingId, onChange, onSubmit, onCancel }) {
  return (
    <form
      onSubmit={onSubmit}
      className="rounded-3xl border border-slate-800 bg-slate-900/60 p-6 shadow-xl shadow-black/20"
    >
      <h2 className="text-2xl font-semibold">{editingId ? 'Editar nota' : 'Nueva nota'}</h2>

      <div className="mt-5 space-y-4">
        <label className="block">
          <span className="mb-2 block text-sm text-slate-300">Título</span>
          <input
            type="text"
            name="titulo"
            value={formData.titulo}
            onChange={onChange}
            placeholder="Título de la nota"
            className="w-full rounded-2xl border border-slate-700 bg-slate-950 px-4 py-3 text-slate-100 outline-none transition placeholder:text-slate-500 focus:border-cyan-400"
          />
        </label>

        <label className="block">
          <span className="mb-2 block text-sm text-slate-300">Descripción</span>
          <textarea
            name="descripcion"
            value={formData.descripcion}
            onChange={onChange}
            rows="7"
            placeholder="Escribe aquí la descripción"
            className="w-full rounded-2xl border border-slate-700 bg-slate-950 px-4 py-3 text-slate-100 outline-none transition placeholder:text-slate-500 focus:border-cyan-400"
          />
        </label>
      </div>

      <div className="mt-5 flex gap-3">
        <button
          type="submit"
          className="rounded-full bg-cyan-400 px-5 py-2.5 font-semibold text-slate-950 transition hover:bg-cyan-300"
        >
          {editingId ? 'Guardar cambios' : 'Crear nota'}
        </button>

        {editingId ? (
          <button
            type="button"
            onClick={onCancel}
            className="rounded-full border border-slate-700 px-5 py-2.5 font-medium text-slate-200 transition hover:border-slate-500"
          >
            Cancelar
          </button>
        ) : null}
      </div>
    </form>
  )
}

NotasForm.propTypes = {
  formData: PropTypes.shape({
    titulo: PropTypes.string.isRequired,
    descripcion: PropTypes.string.isRequired,
  }).isRequired,
  editingId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  onChange: PropTypes.func.isRequired,
  onSubmit: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
}

export default NotasForm