import { useEffect, useState } from 'react'
import { createNota, deleteNota, getNotas, updateNota } from '../services/notasService'
import NotasForm from './NotasForm'
import NotasList from './NotasList'

const emptyForm = {
  titulo: '',
  descripcion: '',
}

function NotasPage() {
  const [notas, setNotas] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [formData, setFormData] = useState(emptyForm)
  const [editingId, setEditingId] = useState(null)

  useEffect(() => {
    loadNotas()
  }, [])

  async function loadNotas() {
    try {
      setLoading(true)
      setError('')
      const data = await getNotas()
      setNotas(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  function handleChange(event) {
    const { name, value } = event.target
    setFormData((current) => ({ ...current, [name]: value }))
  }

  function startEdit(nota) {
    setEditingId(nota.id)
    setFormData({
      titulo: nota.titulo,
      descripcion: nota.descripcion ?? '',
    })
  }

  function resetForm() {
    setEditingId(null)
    setFormData(emptyForm)
  }

  async function handleSubmit(event) {
    event.preventDefault()

    try {
      setError('')

      if (editingId) {
        await updateNota(editingId, formData)
      } else {
        await createNota(formData)
      }

      await loadNotas()
      resetForm()
    } catch (err) {
      setError(err.message)
    }
  }

  async function handleDelete(id) {
    try {
      setError('')
      await deleteNota(id)
      await loadNotas()
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <main className="min-h-screen bg-slate-950 text-slate-100">
      <section className="mx-auto flex min-h-screen w-full max-w-5xl flex-col gap-8 px-6 py-10">
        <header className="rounded-3xl border border-slate-800 bg-slate-900/70 p-8 shadow-2xl shadow-black/30 backdrop-blur">
          <p className="text-sm uppercase tracking-[0.3em] text-cyan-400">AppNotes</p>
          <h1 className="mt-3 text-4xl font-bold tracking-tight">Notas con React + Spring Boot</h1>
          <p className="mt-3 max-w-2xl text-slate-300">
            Tareas del día, ideas, recordatorios...
          </p>
        </header>

        <section className="grid gap-6 lg:grid-cols-[360px_1fr]">
          <NotasForm
            formData={formData}
            editingId={editingId}
            onChange={handleChange}
            onSubmit={handleSubmit}
            onCancel={resetForm}
          />

          <div className="rounded-3xl border border-slate-800 bg-slate-900/60 p-6 shadow-xl shadow-black/20">
            <div className="mb-5 flex items-center justify-between gap-4">
              <div>
                <h2 className="text-2xl font-semibold">Listado</h2>
                <p className="text-sm text-slate-400">{notas.length} nota(s) cargada(s)</p>
              </div>
              <button
                type="button"
                onClick={loadNotas}
                className="rounded-full border border-slate-700 px-4 py-2 text-sm font-medium text-slate-200 transition hover:border-cyan-400 hover:text-cyan-300"
              >
                Recargar
              </button>
            </div>

            {error ? <p className="mb-4 rounded-2xl border border-rose-500/40 bg-rose-500/10 px-4 py-3 text-rose-200">{error}</p> : null}

            {loading ? (
              <p className="text-slate-400">Cargando notas...</p>
            ) : (
              <NotasList notas={notas} onEdit={startEdit} onDelete={handleDelete} />
            )}
          </div>
        </section>
      </section>
    </main>
  )
}

export default NotasPage