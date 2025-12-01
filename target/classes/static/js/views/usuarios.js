import API from '../api.js';
import {el, bindForm, notify} from '../ui.js';

function UsuarioForm(u){
  const f = el(`<form class="form">
    <label>Nombre<input name="nombre" value="${u?.nombre||''}" required /></label>
    <label>Apellidos<input name="apellidos" value="${u?.apellidos||''}" required /></label>
    <label>Email<input name="email" type="email" value="${u?.email||''}" required /></label>
    <label>Rol<select name="roles"><option value="USER">USER</option><option value="ADMIN">ADMIN</option></select></label>
    <div class="actions"><button class="btn" type="submit">Guardar</button></div>
  </form>`);
  if(u) f.querySelector('select[name=roles]').value = u.roles || 'USER';
  return f;
}

export default function Usuarios(){
  const wrap = el(`<div class="panel"><h2>Usuarios</h2>
    <div class="list" id="list"></div>
    <div class="panel"><strong>Buscar</strong> <input id="byName" placeholder="por nombre" /> <input id="byEmail" placeholder="por email" /></div>
  </div>`);
  const list = wrap.querySelector('#list');

  const render = async ()=>{
    list.innerHTML = '<div class="small">Cargando...</div>';
    try{
      const data = await API.usuarios.list();
      list.innerHTML='';
      data.forEach(u=>{
        const item = el(`<div class="item">
          <div><strong>${u.nombre} ${u.apellidos}</strong> <span class="badge">${u.email}</span> <span class="badge">${u.roles}</span></div>
          <div class="actions-row">
            <button class="icon-btn ok" data-edit>Editar</button>
            <button class="icon-btn danger" data-del>Eliminar</button>
          </div>
        </div>`);
        item.querySelector('[data-edit]').onclick = async ()=>{
          const detail = await API.usuarios.get(u.id);
          const form = UsuarioForm(detail);
          const modal = el(`<div class="panel"></div>`);
          modal.appendChild(form);
          document.getElementById('content').prepend(modal);
          bindForm(form, async (data)=>{
            await API.usuarios.update(u.id, data);
            notify('Usuario actualizado');
            modal.remove();
            render();
          });
        };
        item.querySelector('[data-del]').onclick = async ()=>{
          if(!confirm('Eliminar usuario?')) return;
          await API.usuarios.del(u.id);
          notify('Usuario eliminado');
          render();
        };
        list.appendChild(item);
      })
    }catch(e){ list.innerHTML = '<div class="error">'+e.message+'</div>' }
  };

  wrap.querySelector('#byName').addEventListener('change', async (e)=>{
    const q = e.target.value.trim();
    if(!q){ render(); return }
    const data = await API.usuarios.listByName(q);
    list.innerHTML='';
    data.forEach(u=>{ list.appendChild(el(`<div class="item"><div>${u.nombre} ${u.apellidos} • ${u.email}</div></div>`)) })
  });
  wrap.querySelector('#byEmail').addEventListener('change', async (e)=>{
    const q = e.target.value.trim();
    if(!q){ render(); return }
    try{
      const u = await API.usuarios.getByEmail(q);
      list.innerHTML='';
      list.appendChild(el(`<div class="item"><div>${u.nombre} ${u.apellidos} • ${u.email}</div></div>`));
    }catch(err){ notify('No encontrado','error') }
  });

  render();
  return wrap;
}

