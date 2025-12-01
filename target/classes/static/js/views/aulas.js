import API from '../api.js';
import {el, notify, bindForm} from '../ui.js';

function AulaForm(aula){
  const f = el(`<form class="form">
    <label>Nombre<input name="nombre" required value="${aula?.nombre||''}" /></label>
    <label>Capacidad<input name="capacidad" type="number" min="3" required value="${aula?.capacidad||''}" /></label>
    <label>Es aula ordenador<select name="esAulaOrdenador"><option value="true">Sí</option><option value="false">No</option></select></label>
    <label>Nº ordenadores<input name="numOrdenadores" type="number" min="0" required value="${aula?.numOrdenadores||0}" /></label>
    <div class="actions"><button class="btn" type="submit">Guardar</button></div>
  </form>`);
  if(aula) f.querySelector('select[name=esAulaOrdenador]').value = String(aula.esAulaOrdenador);
  return f;
}

export default function Aulas(){
  const wrap = el(`<div class="panel"><h2>Aulas</h2>
    <div class="panel"><input id="qNombre" placeholder="Buscar por nombre" /> <button class="icon-btn" id="btnOrdenadores">Con ordenadores</button> <button class="icon-btn" id="btnNoOrdenadores">Sin ordenadores</button> <label>Capacidad mínima <input id="qCap" type="number" min="0" style="width:120px"></label> <button class="icon-btn" id="btnFiltrar">Filtrar</button></div>
    <div class="list" id="list"></div>
    <h3>Nueva Aula</h3>
    <div id="form"></div>
  </div>`);
  const list = wrap.querySelector('#list');
  const formWrap = wrap.querySelector('#form');

  const renderList = (data)=>{
    list.innerHTML = '';
    data.forEach(a=>{
      const item = el(`<div class="item">
        <div><strong>${a.nombre}</strong> <span class="badge">cap: ${a.capacidad}</span> <span class="badge">PC: ${a.esAulaOrdenador? 'Sí':'No'}</span></div>
        <div class="actions-row">
          <button class="icon-btn" data-res>Reservas</button>
          <button class="icon-btn ok" data-edit>Editar</button>
          <button class="icon-btn danger" data-del>Eliminar</button>
        </div>
      </div>`);
      item.querySelector('[data-res]').onclick = async()=>{
        const ar = await API.aulas.withReservas(a.id);
        const det = el('<div class="panel"></div>');
        det.innerHTML = `<h3>Reservas ${a.nombre}</h3>` + (ar.reservas?.length? ar.reservas.map(r=>`<div class=\"item\">${r.fechaReserva} • ${r.usuario?.email||''}</div>`).join('') : '<div class="small">Sin reservas</div>');
        list.prepend(det);
      }
      item.querySelector('[data-edit]').onclick = async()=>{
        const detail = await API.aulas.get(a.id);
        formWrap.innerHTML = '';
        const form = AulaForm(detail);
        bindForm(form, async (data)=>{
          data.capacidad = Number(data.capacidad); data.numOrdenadores = Number(data.numOrdenadores); data.esAulaOrdenador = data.esAulaOrdenador==='true';
          await API.aulas.update(a.id, data);
          notify('Aula actualizada');
          load();
        });
        formWrap.appendChild(form);
      };
      item.querySelector('[data-del]').onclick = async()=>{
        if(!confirm('Eliminar aula?')) return;
        await API.aulas.del(a.id);
        notify('Aula eliminada');
        load();
      }
      list.appendChild(item);
    })
  };

  const load = async ()=>{
    list.innerHTML = '<div class="small">Cargando...</div>';
    try{
      const data = await API.aulas.list();
      renderList(data);
    }catch(e){ list.innerHTML = '<div class="error">'+e.message+'</div>' }
  };

  // filtros
  wrap.querySelector('#btnOrdenadores').onclick = async()=> renderList(await API.aulas.listOrdenadores());
  wrap.querySelector('#btnNoOrdenadores').onclick = async()=> renderList(await API.aulas.listNoOrdenadores());
  wrap.querySelector('#btnFiltrar').onclick = async()=>{
    const cap = Number(wrap.querySelector('#qCap').value||0);
    if(cap>0){ renderList(await API.aulas.listCapacidad(cap)); return }
    const q = (wrap.querySelector('#qNombre').value||'').trim();
    if(q){ renderList(await API.aulas.listByNombre(q)); return }
    load();
  };

  // formulario crear
  const createForm = AulaForm();
  bindForm(createForm, async (data)=>{
    data.capacidad = Number(data.capacidad); data.numOrdenadores = Number(data.numOrdenadores); data.esAulaOrdenador = data.esAulaOrdenador==='true';
    await API.aulas.insert(data);
    notify('Aula creada');
    createForm.reset();
    load();
  });
  formWrap.appendChild(createForm);

  load();
  return wrap;
}
