const API_BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api/notas'

async function request(path = '', options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  const contentType = response.headers.get('content-type') ?? ''
  const body = contentType.includes('application/json') ? await response.json() : null

  if (!response.ok) {
    const message = body?.message ?? 'No se pudo completar la petición'
    throw new Error(message)
  }

  return body
}

export function getNotas() {
  return request()
}

export function createNota(nota) {
  return request('', {
    method: 'POST',
    body: JSON.stringify(nota),
  })
}

export function updateNota(id, nota) {
  return request(`/${id}`, {
    method: 'PUT',
    body: JSON.stringify(nota),
  })
}

export function deleteNota(id) {
  return request(`/${id}`, {
    method: 'DELETE',
  })
}