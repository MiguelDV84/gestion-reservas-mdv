export const el = (html) => {
  const t = document.createElement('template');
  t.innerHTML = html.trim();
  return t.content.firstElementChild;
};
export const setContent = (node, html) => { node.innerHTML = html; return node };
export const notify = (text, type='notice') => {
  const n = el(`<div class="${type}">${text}</div>`);
  const c = document.getElementById('content');
  c.prepend(n); setTimeout(()=>n.remove(), 2500);
};
export const bindForm = (form, handler) => {
  form.addEventListener('submit', async (e)=>{
    e.preventDefault();
    const fd = new FormData(form);
    const data = Object.fromEntries(fd.entries());
    try{ await handler(data); }catch(err){ notify(err.message||String(err),'error') }
  })
};
export const loading = (msg='Cargando...') => el(`<div class="small">${msg}</div>`);

