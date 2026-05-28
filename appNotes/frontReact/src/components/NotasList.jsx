import PropTypes from 'prop-types'

function NotasList({ notas, onEdit, onDelete }) {
  if (notas.length === 0) {
    return <p className="text-slate-400">No hay notas todavía. Crea la primera con el formulario.</p>
  }

  return (
    <ul className="grid gap-4">
      {notas.map((nota) => (
        <li key={nota.id} className="rounded-3xl border border-slate-800 bg-slate-950/60 p-5">
          <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
            <div className="min-w-0">
              <h3 className="text-xl font-semibold text-cyan-300">{nota.titulo}</h3>
              <p className="mt-2 whitespace-pre-wrap text-slate-300">
                {nota.descripcion || 'Sin descripción'}
              </p>
            </div>

            <div className="flex shrink-0 gap-2">
              <button
                type="button"
                onClick={() => onEdit(nota)}
                className="rounded-full border border-slate-700 px-4 py-2 text-sm font-medium text-slate-200 transition hover:border-cyan-400 hover:text-cyan-300"
              >
                Editar
              </button>
              <button
                type="button"
                onClick={() => onDelete(nota.id)}
                className="rounded-full border border-rose-500/40 px-4 py-2 text-sm font-medium text-rose-200 transition hover:bg-rose-500/10"
              >
                Borrar
              </button>
            </div>
          </div>
        </li>
      ))}
    </ul>
  )
}

NotasList.propTypes = {
  notas: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
      titulo: PropTypes.string.isRequired,
      descripcion: PropTypes.string,
    }),
  ).isRequired,
  onEdit: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
}

export default NotasList